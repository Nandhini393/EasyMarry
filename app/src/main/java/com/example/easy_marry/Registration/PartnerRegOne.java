package com.example.easy_marry.Registration;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RadioButton;
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
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 14/10/16.
 */
public class PartnerRegOne extends Activity implements MyInterface {
    Button btn_next;


    EditText et_age, et_height, et_weight, et_phy_status, et_complextion, et_body_type, et_family_status, et_family_type, et_family_values, et_search_profile_for;
    RadioButton rb_dhosham_yes, rb_dhosham_no, rb_dhosham_doesnt_matter, rb_other_comm_yes, rb_other_comm_no;
    JSONArray json_partial_Age, json_height, json_weight, json_body_type, json_physical_status, json_complexion, json_family_type, json_family_value, json_family_status;
    Context c;


    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    String str_age_id="", str_height_id="", str_weight_id="", str_phy_status_id="", str_complextion_id="", str_body_type_id="",
            str_dhosham_id = "1", str_family_values_id="", str_family_type_id="", str_family_status_id="", str_other_comm_id;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    ArrayList<ListDrawerBean> return_beanArrayList;
    String str_user_id="";
    GeneralData gD;
    TextView txt_skip,txt_drawer_error_msg;
    ImageView img_back;
    IntentFilter filter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_partner1);
        btn_next = (Button) findViewById(R.id.btn_next);

        c = this;
        gD = new GeneralData(c);
        str_user_id = gD.prefs.getString("userid", null);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);

        et_age = (EditText) findViewById(R.id.et_age);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_phy_status = (EditText) findViewById(R.id.et_phy_status);
        et_complextion = (EditText) findViewById(R.id.et_complextion);
        et_body_type = (EditText) findViewById(R.id.et_body_type);
        et_family_values = (EditText) findViewById(R.id.et_fam_val);
        et_family_type = (EditText) findViewById(R.id.et_fam_type);
        et_family_status = (EditText) findViewById(R.id.et_fam_status);

        txt_skip = (TextView) findViewById(R.id.txt_skip);
        img_back = (ImageView) findViewById(R.id.img_back);

        txt_drawer_error_msg=(TextView)findViewById(R.id.txt_draw_error);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);

        et_family_status.setFocusable(false);
        et_family_type.setFocusable(false);
        et_family_values.setFocusable(false);
        et_body_type.setFocusable(false);
        et_complextion.setFocusable(false);
        et_phy_status.setFocusable(false);
        et_height.setFocusable(false);
        et_weight.setFocusable(false);
        et_age.setFocusable(false);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartnerRegOne.this, Matches.class));
                finish();
            }
        });
        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartnerRegOne.this, PartnerRegTwo.class));
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent i = new Intent(PartnerRegOne.this, PartnerRegOne.class);

                startActivity(i);*/
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {

                    Log.e("str_age_id", str_age_id);
                    Log.e("str_height_id", str_height_id);
                    Log.e("str_weight_id", str_weight_id);
                    Log.e("str_body_type_id", str_body_type_id);
                    Log.e("str_complextion_id", str_complextion_id);
                    Log.e("str_family_status_id", str_family_status_id);

                    Log.e("str_family_type_id", str_family_type_id);
                    Log.e("str_family_values_id", str_family_values_id);
                    Log.e("str_phy_status_id", str_phy_status_id);


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register6_screen.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //Toast.makeText(PartnerRegOne.this, response, Toast.LENGTH_LONG).show();
                                    try {
                                        Log.i("HH", "strResp : " + response);
                                        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                        JSONObject jsobj = new JSONObject(response);

                                        Log.i("HH", "strResp : " + response);
                                        if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                            String strName = jsobj.getJSONObject("Response").getString("Name");
                                            String strUserId = jsobj.getJSONObject("Response").getString("userid");
                                            String strCompleteLevel = jsobj.getJSONObject("Response").getString("completeLevel");
                                            String strMembership = jsobj.getJSONObject("Response").getString("membership");
                                            String strScreenId = jsobj.getJSONObject("Response").getString("screen_id");
                                            String strRating = jsobj.getJSONObject("Response").getString("Rating");
                                            String strProfileImage = GeneralData.LOCAL_IP_IMAGE + "upload/" + jsobj.getJSONObject("Response").getString("profileImage");
                                            String strEasyMarryId = jsobj.getJSONObject("Response").getString("easymarryid");
                                            String strMemPlan = jsobj.getJSONObject("Response").getString("membershipplan");
                                            String strMemValid = jsobj.getJSONObject("Response").getString("membershipvalidity");

                           /* GeneralData.str_name = jsobj.getJSONObject("Response").getString("Name");
                            GeneralData.str_id = jsobj.getJSONObject("Response").getString("userid");
                            GeneralData.str_complete_profile_level = jsobj.getJSONObject("Response").getString("completeLevel");
                            GeneralData.str_image = GeneralData.LOCAL_IP_IMAGE + "images/" + jsobj.getJSONObject("Response").getString("profileImage");
                            GeneralData.str_membership = jsobj.getJSONObject("Response").getString("membership");
                          */


                                            Log.e("NNp1:profileimage", strProfileImage);
                                            SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                            prefEdit.putString("name", strName);
                                            prefEdit.putString("userid", strUserId);
                                            prefEdit.putString("completelevel", strCompleteLevel);
                                            prefEdit.putString("membership", strMembership);
                                            prefEdit.putString("profileimage", strProfileImage);
                                            prefEdit.putString("screenid", strScreenId);
                                            prefEdit.putString("easymarryid", strEasyMarryId);
                                            prefEdit.putString("memplan", strMemPlan);
                                            prefEdit.putString("memvalid", strMemValid);
                                            prefEdit.putString("Rating", strRating);
                                            prefEdit.commit();

                                            Log.e("NN:name", strName);
                                            Log.e("NN:userid", strUserId);
                                            Log.e("NN:completelevel", strCompleteLevel);
                                            Log.e("NN:membership", strMembership);
                                            Log.e("NN:profileimage", strProfileImage);
                                            Log.e("NN:screenid", strScreenId);
                                            Log.e("NN:easymarryid", strEasyMarryId);
                                            Log.e("NN:memplan", strMemPlan);
                                            Log.e("NN:memvalid", strMemValid);


                                            Intent i = new Intent(PartnerRegOne.this, PartnerRegTwo.class);
                                            //       i.putExtra("user_id", "1");
                                            startActivity(i);
                                            finish();
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Toast.makeText(PartnerRegOne.this, error.toString(), Toast.LENGTH_LONG).show();
                                    NetworkResponse response = error.networkResponse;
                                    if (error instanceof ServerError && response != null) {
                                        try {
                                            String res = new String(response.data,
                                                    HttpHeaderParser.parseCharset(response.headers));

                                            // Toast.makeText(PartnerRegOne.this, "res : " + res, Toast.LENGTH_LONG).show();

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

                            if (str_user_id.length() > 0) {
                                params.put("userid", str_user_id);
                            }
                            if (str_age_id.length() > 0) {
                                params.put("partnerage", str_age_id);
                            }
                            if (str_height_id.length() > 0) {
                                params.put("partnerheight", str_height_id);
                            }
                            if (str_weight_id.length() > 0) {
                                params.put("partnerweight", str_weight_id);
                            }
                            if (str_body_type_id.length() > 0) {
                                params.put("partnerbodytype", str_body_type_id);
                            }
                            if (str_complextion_id.length() > 0) {
                                params.put("partnercomplexion", str_complextion_id);
                            }
                            if (str_family_status_id.length() > 0) {
                                params.put("partnerfamilystatus", str_family_status_id);
                            }
                            if (str_family_type_id.length() > 0) {
                                params.put("partnerfamilytype", str_family_type_id);
                            }
                            if (str_family_values_id.length() > 0) {
                                params.put("partnerfamilyvalue", str_family_values_id);
                            }
                            if (str_phy_status_id.length() > 0) {
                                params.put("partnerphysicalstatus", str_phy_status_id);

                            }

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", String.format("Basic %s", Base64.encodeToString(String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));

                            return params;
                        }

                    };

                    RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);


                    RequestQueue requestQueue = Volley.newRequestQueue(PartnerRegOne.this);
                    requestQueue.add(stringRequest);

                } else {
                    listView.setVisibility(View.GONE);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(false);
                    View itemView1 = LayoutInflater.from(c)
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



        et_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_partial_Age, "partner_age");


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


                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "partner_age");
                        listView.setAdapter(listDrawerAdapter);
                        listView.setVisibility(View.VISIBLE);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });


        et_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_height, "height");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "height");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);

                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });

        et_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_weight, "weight");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "weight");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });

        et_phy_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_physical_status, "physical_status");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "physical_status");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });


        et_complextion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_complexion, "complexion");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "complexion");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });
        et_body_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_body_type, "body_Type");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "body_Type");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });
        et_family_values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_family_value, "family_values");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "family_values");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });

        et_family_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_family_type, "family_type");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "family_type");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });
        et_family_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_family_status, "family_status");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "family_status");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                }
                else{
                    Toast.makeText(PartnerRegOne.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_partial_Age=new JSONArray();
                    json_height=new JSONArray();
                    json_weight =new JSONArray();
                    json_body_type=new JSONArray();
                    json_physical_status=new JSONArray();
                    json_complexion=new JSONArray();
                    json_family_type =new JSONArray();
                    json_family_value=new JSONArray();
                    json_family_status=new JSONArray();

                }
            }
        });


    }


    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        if(providerServicesMonth!=null) {
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

            listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, stridentifyEdit);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(listDrawerAdapter);
            listDrawerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        else{
            Toast.makeText(PartnerRegOne.this, "list is empty", Toast.LENGTH_SHORT).show();
        }
        return beanArrayList;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PartnerRegOne.this, Matches.class));
        finish();
    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("partner_age")) {
            et_age.setText(str_items);
            str_age_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("height")) {
            et_height.setText(str_items);
            str_height_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("weight")) {
            et_weight.setText(str_items);
            str_weight_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("physical_status")) {
            et_phy_status.setText(str_items);
            str_phy_status_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("complexion")) {
            et_complextion.setText(str_items);
            str_complextion_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("body_type")) {
            et_body_type.setText(str_items);
            str_body_type_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("family_values")) {
            et_family_values.setText(str_items);
            str_family_values_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("family_type")) {
            et_family_type.setText(str_items);
            str_family_type_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("family_status")) {
            et_family_status.setText(str_items);
            str_family_status_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id, String str_partner_name,String strFrom, String str_type,  String str_sent_int,RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal) {
        return null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
            context = GeneralData.context;
            if (isConnected) {
                Log.i("LK", "connected");
              /*  if (nAttempt == 0) {*/

                //Toast.makeText(UserRegOne.this, "Internet connected", Toast.LENGTH_SHORT).show();

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

                                        JSONArray json_array_religion = jsobj.getJSONArray("Partner Age");
                                        json_partial_Age = jsobj.getJSONArray("Partner Age");

                                        json_height = jsobj.getJSONArray("Partner height");
                                        json_weight = jsobj.getJSONArray("Partner Weight");
                                        json_body_type = jsobj.getJSONArray("Body Type");
                                        json_physical_status = jsobj.getJSONArray("Physical status");
                                        json_complexion = jsobj.getJSONArray("Complexion");
                                        json_family_type = jsobj.getJSONArray("family type");
                                        json_family_value = jsobj.getJSONArray("family value");
                                        json_family_status = jsobj.getJSONArray("Family Status");
                             /*   str_profile_for_id = profile_created_by.getJSONObject(0).getString("id");
                                str_religion_id = jsonArray_religion.getJSONObject(0).getString("id");
                                str_mother_tonngue_id = jsonArray_mother_tongue.getJSONObject(0).getString("id");*/


                                        if (json_partial_Age.length() > 0) {
                                            for (int i = 0; i < json_array_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_partial_Age.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, "profile");
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
                                //   Toast.makeText(PartnerRegOne.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        // Toast.makeText(PartnerRegOne.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(PartnerRegOne.this);
                requestQueue.add(stringRequest);

             /*   }
                nAttempt = 1;*/

            } else {
                listView.setVisibility(View.GONE);
                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setCancelable(false);
                View itemView1 = LayoutInflater.from(c)
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
