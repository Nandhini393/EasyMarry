package com.example.easy_marry.EditProfiles;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easy_marry.Adapter.ListDrawerAdapter;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 17/6/16.
 */
public class EditProfileProfessional extends Activity implements MyInterface {
    Button btn_basic_det_save;
    ImageView img_back;
    View itemView1;
    Context context;
    EditText et_qualification, et_search_profile_for, et_qualif_detail, et_occupation, et_citizen, et_annual_income, et_employed_in, et_country, et_star, et_profile_create;
    String str_qualif_id = "", str_occup_id = "", str_emp_in_id = "",
            str_annual_income_id = "", str_user_id;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    JSONArray json_qualif, json_occup, json_emp_in, json_annual_income;
    GeneralData gD;
    String str_professional_json, str_response_partner, str_add_qualif;
    IntentFilter filter1;
    TextView txt_drawer_error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_profession);
        context = this;
        gD = new GeneralData(context);
        str_user_id = gD.prefs.getString("userid", null);
        str_professional_json = getIntent().getStringExtra("json_edit_professional");
        Log.e("NN:edit_basic", str_professional_json);
        str_response_partner = getIntent().getStringExtra("json_response");
        Log.e("NN:edit_basic_partner", str_response_partner);
        btn_basic_det_save = (Button) findViewById(R.id.btn_basic_det_save);
        img_back = (ImageView) findViewById(R.id.img_ed_back);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        txt_drawer_error_msg = (TextView) findViewById(R.id.txt_draw_error);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        et_qualification = (EditText) findViewById(R.id.et_qualification);
        et_qualif_detail = (EditText) findViewById(R.id.et_qualif_detail);
        et_occupation = (EditText) findViewById(R.id.et_occupation);
        et_annual_income = (EditText) findViewById(R.id.et_annual_income);
        et_employed_in = (EditText) findViewById(R.id.et_employed_in);

        et_qualif_detail.setFocusable(false);
        et_qualification.setFocusable(false);
        et_occupation.setFocusable(false);
        et_annual_income.setFocusable(false);
        et_employed_in.setFocusable(false);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileProfessional.this,EditProfile.class));
                finish();
            }
        });
        btn_basic_det_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (str_response_partner.equalsIgnoreCase("user")) {
                        str_add_qualif = et_qualif_detail.getText().toString().trim();
                        updateUserProfessionDet();
                    } else {
                        updatePartnerProfessionDet();
                    }

                } else {
                    listView.setVisibility(View.GONE);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);

                    View itemView1 = LayoutInflater.from(context)
                            .inflate(R.layout.custom_basic_det_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                    txt_span_text.setText("Unable to connect with internet. Please check your internet connection and try again");
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            altDialog.dismiss();
                        }
                    });
                    altDialog.show();
                }
            }
        });


        et_qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_qualif, "qualification");

                    et_search_profile_for.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String strVal = s.toString().toLowerCase();
                            Log.i("AB", "Val : " + strVal);

                            final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                            for (int i = 0; i < beanArrayList.size(); i++) {

                                String text = beanArrayList.get(i).getStr_list_items().toLowerCase();
                                Log.i("AB", "text : " + text);

                                if (text.contains(strVal)) {
                                    filteredList.add(beanArrayList.get(i));
                                }
                            }

                            listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "qualification");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileProfessional.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_emp_in = new JSONArray();
                    json_annual_income = new JSONArray();

                }
            }
        });
      /*  et_add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_add_info.setFocusable(true);
            }
        });*/
        et_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_occup, "occupation");

                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                        for (int i = 0; i < beanArrayList.size(); i++) {

                            String text = beanArrayList.get(i).getStr_list_items().toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(beanArrayList.get(i));
                            }
                        }

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "occupation");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileProfessional.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_emp_in = new JSONArray();
                    json_annual_income = new JSONArray();

                }
            }
        });
        et_employed_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_emp_in, "emp_in");

                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                        for (int i = 0; i < beanArrayList.size(); i++) {

                            String text = beanArrayList.get(i).getStr_list_items().toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(beanArrayList.get(i));
                            }
                        }

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "emp_in");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileProfessional.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_emp_in = new JSONArray();
                    json_annual_income = new JSONArray();

                }
            }
        });
        et_annual_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_annual_income, "annual_income");

                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                        for (int i = 0; i < beanArrayList.size(); i++) {

                            String text = beanArrayList.get(i).getStr_list_items().toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(beanArrayList.get(i));
                            }
                        }

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "annual_income");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileProfessional.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_emp_in = new JSONArray();
                    json_annual_income = new JSONArray();

                }
            }
        });
    }

    public void getEditBasicDetCall(String str_basic_json, String st_response_partner) {


        try {
            Log.i("HH", "strResp : " + str_basic_json);
            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

            JSONObject myProfessionalJsonObj = new JSONObject(str_basic_json);

            String qualif = myProfessionalJsonObj.getString("Qualification");

            for (int i = 0; i < json_qualif.length(); i++) {
                if (qualif.equals(json_qualif.getJSONObject(i).getString("value"))) {
                    str_qualif_id = json_qualif.getJSONObject(i).getString("id");
                    break;
                }
            }
            et_qualification.setText(qualif);
            String ocuupation = myProfessionalJsonObj.getString("Occupation");
            for (int i = 0; i < json_occup.length(); i++) {
                if (ocuupation.equals(json_occup.getJSONObject(i).getString("value"))) {
                    str_occup_id = json_occup.getJSONObject(i).getString("id");
                    break;
                }
            }
            et_occupation.setText(ocuupation);

            String annual_income = myProfessionalJsonObj.getString("Annual Income");
            for (int i = 0; i < json_annual_income.length(); i++) {
                if (annual_income.equals(json_annual_income.getJSONObject(i).getString("value"))) {
                    str_annual_income_id = json_annual_income.getJSONObject(i).getString("id");
                    break;
                }
            }
            et_annual_income.setText(annual_income);


            String emp_in = myProfessionalJsonObj.getString("Employed in");
            for (int i = 0; i < json_emp_in.length(); i++) {
                if (emp_in.equals(json_emp_in.getJSONObject(i).getString("value"))) {
                    str_emp_in_id = json_emp_in.getJSONObject(i).getString("id");
                    break;
                }
            }
            et_employed_in.setText(emp_in);


            if (st_response_partner.equalsIgnoreCase("partner")) {
                et_qualif_detail.setVisibility(View.GONE);

            } else {
                String qualif_det = myProfessionalJsonObj.getString("Qualification in Detail");
                str_add_qualif = qualif_det;
                et_qualif_detail.setText(str_add_qualif);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        try {
            if (providerServicesMonth.length() > 0) {
                for (int i = 0; i < providerServicesMonth.length(); i++) {
                    ListDrawerBean drawerBean = new ListDrawerBean();
                    jsobj = providerServicesMonth.getJSONObject(i);
                    drawerBean.setStr_list_items_id(jsobj.getString("id"));
                    drawerBean.setStr_list_items(jsobj.getString("value"));
                    beanArrayList.add(drawerBean);
                }
            }

            listDrawerAdapter = new ListDrawerAdapter(context, beanArrayList, (MyInterface) context, stridentifyEdit);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(listDrawerAdapter);
            listDrawerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanArrayList;

    }

    public void updateUserProfessionDet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_user3.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //   Toast.m akeText(UserRegOne.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {


                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);

                                itemView1 = LayoutInflater.from(context)
                                        .inflate(R.layout.custom_basic_det_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                String str_span = "Your professional details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 24, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfileProfessional.this, EditProfile.class));
                                        finish();
                                    }
                                });
                                altDialog.show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(UserRegOne.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Log.e("error_msg", res);

                                //  Toast.makeText(UserRegOne.this, "res : " + res, Toast.LENGTH_LONG).show();

// Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
// Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
// returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (str_qualif_id.length() > 0) {
                    params.put("qualification", str_qualif_id);
                }
                if (str_add_qualif.length() > 0) {
                    params.put("additionalqualification", str_add_qualif);
                }
                if (str_occup_id.length() > 0) {
                    params.put("occupation", str_occup_id);
                }
                if (str_emp_in_id.length() > 0) {
                    params.put("employedin", str_emp_in_id);
                }
                if (str_annual_income_id.length() > 0) {
                    params.put("annualincome", str_annual_income_id);
                }
                if (str_user_id.length() > 0) {
                    params.put("userid", str_user_id);
                }


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
// params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }

        };

//3Secs
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileProfessional.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

    public void updatePartnerProfessionDet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_partner3.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //   Toast.makeText(UserRegOne.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {


                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);

                                itemView1 = LayoutInflater.from(context)
                                        .inflate(R.layout.custom_basic_det_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                String str_span = "Your partner professional details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 33, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfileProfessional.this, EditProfile.class));
                                        finish();
                                    }
                                });
                                altDialog.show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(UserRegOne.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Log.e("error_msg", res);

                                //  Toast.makeText(UserRegOne.this, "res : " + res, Toast.LENGTH_LONG).show();

// Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
// Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
// returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (str_qualif_id.length() > 0) {
                    params.put("partnerqualification", str_qualif_id);
                }

                if (str_occup_id.length() > 0) {
                    params.put("partneroccupation", str_occup_id);
                }
                if (str_annual_income_id.length() > 0) {
                    params.put("partnerincome", str_annual_income_id);
                }
                if (str_user_id.length() > 0) {
                    params.put("userid", str_user_id);
                }


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
// params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }

        };

//3Secs
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileProfessional.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("qualification")) {
            et_qualification.setText(str_items);
            str_qualif_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("occupation")) {
            et_occupation.setText(str_items);
            str_occup_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("emp_in")) {
            et_employed_in.setText(str_items);
            str_emp_in_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("annual_income")) {
            et_annual_income.setText(str_items);
            str_annual_income_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id, String str_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

            if (isConnected) {
                Log.i("LK", "connected");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register_select.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Toast.makeText(UserRegOne.this, response, Toast.LENGTH_LONG).show();
                                try {
                                    Log.i("HH", "strResp : " + response);
                                    ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                    JSONObject jsobj = new JSONObject(response);

                                    Log.i("HH", "strResp : " + response);
                                    if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                        JSONArray json_array_religion = jsobj.getJSONArray("qualification");
                                        json_qualif = jsobj.getJSONArray("qualification");
                                        json_occup = jsobj.getJSONArray("occupation");
                                        json_emp_in = jsobj.getJSONArray("Employment");
                                        json_annual_income = jsobj.getJSONArray("Income");
                                        getEditBasicDetCall(str_professional_json, str_response_partner);

                                        if (json_qualif.length() > 0) {
                                            for (int i = 0; i < json_array_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_qualif.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(context, beanArrayList, (MyInterface) context, "qualification");
                                        listView.setVisibility(View.VISIBLE);
                                        listView.setAdapter(listDrawerAdapter);

                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //    Toast.makeText(PartnerRegThree.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        // Toast.makeText(PartnerRegThree.this, "res : " + res, Toast.LENGTH_LONG).show();

// Now you can use any deserializer to make sense of data
                                        JSONObject obj = new JSONObject(res);
                                    } catch (UnsupportedEncodingException e1) {
// Couldn't properly decode data to string
                                        e1.printStackTrace();
                                    } catch (JSONException e2) {
// returned data is not JSONObject?
                                        e2.printStackTrace();
                                    }
                                }

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

//username=sathish@ansjad.com&password=testing

               /* params.put("username", edt_email.getText().toString().trim());
                params.put("password", edt_password.getText().toString().trim());*/

// params.put("username", "dineshW@adjhd.com");
// params.put("password", "123456");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(
                                "Authorization",
                                String.format("Basic %s", Base64.encodeToString(
                                        String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
// params.put("Content-Type", "application/json; charset=utf-8");
                        return params;
                    }

                };

//30Secs
                RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);


                RequestQueue requestQueue = Volley.newRequestQueue(EditProfileProfessional.this);
                requestQueue.add(stringRequest);

            } else {
                listView.setVisibility(View.GONE);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                View itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_basic_det_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                txt_span_text.setText("Unable to connect with internet. Please check your internet connection and try again");
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                    }
                });
                altDialog.show();

            }
        }
    };
}
