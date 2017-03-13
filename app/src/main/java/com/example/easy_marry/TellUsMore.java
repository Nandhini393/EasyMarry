package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android3 on 11/6/16.
 */
public class TellUsMore extends Activity implements MyInterface {
    Button btn_complete_reg;
    String str_country_id, str_state_id, str_city_id;
    TextView txt_span1, txt_span2;
    ImageView img_back;
    ListView listView;
    Context context;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    ListDrawerAdapter listDrawerAdapter;
    EditText et_get_martial_status, et_search_profile_for, et_get_country, et_get_state, et_get_city, et_get_height, et_get_weight, et_get_qualif, et_get_add_qualif, et_get_occup, et_get_fam_type, et_get_fam_val, et_get_abt_u;
    JSONArray jsonArray_marital_status, jsonArray_country, jsonArray_qualif, jsonArray_occupation, jsonArray_fam_type, jsonArray_fam_val, jsonArray_height, jsonArray_weight;
    RadioButton rb_other_comm_yes, rb_other_comm_no, rb_phy_normal, rb_phy_challenged, rb_phy_doesntmatter, rb_dhosham_yes, rb_dhosham_no;
    ArrayList<ListDrawerBean> return_beanArrayList;
    String str_other_comm = "Yes";
    String str_phy_status = "1";
    String str_dhosham = "1";
    String str_maritial_id, str_user_id, str_qualif_id, str_add_qualif, str_occup_id, str_fam_type_id, str_fam_val_id, str_abt_you, str_height_id, str_weight_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tell_us_more);
        context = this;

        rb_other_comm_yes = (RadioButton) findViewById(R.id.radio_comm_yes);
        rb_other_comm_no = (RadioButton) findViewById(R.id.radio_comm_no);
        rb_phy_normal = (RadioButton) findViewById(R.id.radio_normal);
        rb_phy_challenged = (RadioButton) findViewById(R.id.radio_physic);
        rb_phy_doesntmatter = (RadioButton) findViewById(R.id.radio_doesnt);
        rb_dhosham_yes = (RadioButton) findViewById(R.id.radio_dhosam_yes);
        rb_dhosham_no = (RadioButton) findViewById(R.id.radio_dhosam_no);

        listView = (ListView) findViewById(R.id.drawer_listview);
        img_back = (ImageView) findViewById(R.id.img_back);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        btn_complete_reg = (Button) findViewById(R.id.complete_reg);
        et_get_martial_status = (EditText) findViewById(R.id.et_martial_status);
        et_get_country = (EditText) findViewById(R.id.et_country);
        et_get_state = (EditText) findViewById(R.id.et_state);
        et_get_city = (EditText) findViewById(R.id.et_city);
        et_get_height = (EditText) findViewById(R.id.et_height);
        et_get_weight = (EditText) findViewById(R.id.et_weight);
        et_get_qualif = (EditText) findViewById(R.id.et_qualification);
        et_get_add_qualif = (EditText) findViewById(R.id.et_addqualification);
        et_get_occup = (EditText) findViewById(R.id.et_occupation);
        et_get_fam_type = (EditText) findViewById(R.id.et_family_type);
        et_get_fam_val = (EditText) findViewById(R.id.et_family_values);
        et_get_abt_u = (EditText) findViewById(R.id.et_about_you);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        str_user_id = getIntent().getStringExtra("user_id");
        Log.e("nn-user_id", str_user_id);
        et_get_martial_status.setFocusable(false);
        et_get_country.setFocusable(false);
        et_get_state.setFocusable(false);
        et_get_city.setFocusable(false);
        et_get_qualif.setFocusable(false);
        et_get_occup.setFocusable(false);
        et_get_fam_type.setFocusable(false);
        et_get_fam_val.setFocusable(false);
        et_get_height.setFocusable(false);
        et_get_weight.setFocusable(false);

        rb_other_comm_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_other_comm = "Yes";
                }
            }
        });
        rb_other_comm_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_other_comm = "No";
                }
            }
        });
        rb_phy_normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_phy_status = "1";
                }
            }
        });
        rb_phy_challenged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_phy_status = "2";
                }
            }
        });
        rb_phy_doesntmatter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_phy_status = "3";
                }
            }
        });
        rb_dhosham_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_dhosham = "1";
                }
            }
        });
        rb_dhosham_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_dhosham = "2";
                }
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"register_select.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(TellUsMore.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray profile_created_by = jsobj.getJSONArray("martialstatus");
                                jsonArray_marital_status = jsobj.getJSONArray("martialstatus");
                                jsonArray_country = jsobj.getJSONArray("country");
                                jsonArray_qualif = jsobj.getJSONArray("qualification");
                                jsonArray_occupation = jsobj.getJSONArray("occupation");
                                jsonArray_fam_type = jsobj.getJSONArray("family type");
                                jsonArray_fam_val = jsobj.getJSONArray("family value");
                                jsonArray_height = jsobj.getJSONArray("Height");
                                jsonArray_weight = jsobj.getJSONArray("weight");

                                if (jsonArray_marital_status.length() > 0) {
                                    for (int i = 0; i < profile_created_by.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = jsonArray_marital_status.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        beanArrayList.add(drawerBean);
                                    }
                                }

                                listDrawerAdapter = new ListDrawerAdapter(context, beanArrayList, (MyInterface) context, "martialstatus");
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
                        Toast.makeText(TellUsMore.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(TellUsMore.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(TellUsMore.this);
        requestQueue.add(stringRequest);


        btn_complete_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("NN", "button clicked");

                if (et_get_martial_status.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select martial status", Toast.LENGTH_SHORT).show();
                } else if (et_get_country.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select country", Toast.LENGTH_SHORT).show();
                } else if (et_get_state.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select state", Toast.LENGTH_SHORT).show();
                } else if (et_get_city.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select city", Toast.LENGTH_SHORT).show();
                } else if (et_get_height.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select height", Toast.LENGTH_SHORT).show();
                } else if (et_get_weight.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select weight", Toast.LENGTH_SHORT).show();
                } else if (et_get_qualif.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select qualification", Toast.LENGTH_SHORT).show();
                } else if (et_get_add_qualif.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select additional qualification", Toast.LENGTH_SHORT).show();
                } else if (et_get_occup.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select occupation", Toast.LENGTH_SHORT).show();
                } else if (et_get_fam_type.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select family type", Toast.LENGTH_SHORT).show();
                } else if (et_get_fam_val.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Select family values", Toast.LENGTH_SHORT).show();
                } else if (et_get_abt_u.getText().toString().length() == 0) {
                    Toast.makeText(TellUsMore.this, "Enter About You", Toast.LENGTH_SHORT).show();
                } else {

                    str_add_qualif = et_get_add_qualif.getText().toString();
                    str_abt_you = et_get_abt_u.getText().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"register-updateaccount.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(TellUsMore.this, response, Toast.LENGTH_LONG).show();
                                    try {
                                        Log.i("HH", "strResp : " + response);
                                        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                        JSONObject jsobj = new JSONObject(response);

                                        Log.i("HH", "strResp : " + response);
                                        if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                            Intent i = new Intent(TellUsMore.this, PartnerPreference.class);
                                            i.putExtra("user_id", str_user_id);
                                            startActivity(i);
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(TellUsMore.this, error.toString(), Toast.LENGTH_LONG).show();
                                    NetworkResponse response = error.networkResponse;
                                    if (error instanceof ServerError && response != null) {
                                        try {
                                            String res = new String(response.data,
                                                    HttpHeaderParser.parseCharset(response.headers));

                                            Toast.makeText(TellUsMore.this, "res : " + res, Toast.LENGTH_LONG).show();

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

                            params.put("userid", str_user_id);
                            params.put("martialstatus", str_maritial_id);
                            params.put("othercommunity", str_other_comm);
                            params.put("countryliving", str_country_id);
                            params.put("state", str_state_id);
                            params.put("city", str_city_id);
                            params.put("height", str_height_id);
                            params.put("weight", str_weight_id);
                            params.put("physicalstatus", str_phy_status);
                            params.put("qualification", str_qualif_id);
                            params.put("additionalqualification", str_add_qualif);
                            params.put("employeein", "1");
                            params.put("occupation", str_occup_id);
                            params.put("dhosham", str_dhosham);
                            params.put("familtype", str_fam_type_id);
                            params.put("familyvalue", et_get_fam_val.getText().toString());
                            params.put("aboutyou", str_abt_you);


                            Log.e("tell_str_user_id", str_user_id);
                            Log.e("tell_str_maritial_id", str_maritial_id);
                            Log.e("tell_str_other_comm", str_other_comm);
                            Log.e("tell_str_country_id", str_country_id);
                            Log.e("tell_str_state_id", str_state_id);
                            Log.e("tell_str_city_id", str_city_id);
                            Log.e("tell_str_height_id", str_height_id);
                            Log.e("tell_str_weight_id", str_weight_id);
                            Log.e("tell_str_phy_statu", str_phy_status);
                            Log.e("tell_str_qualif_id", str_qualif_id);
                            Log.e("tell_str_add_qualif", str_add_qualif);
                            Log.e("tell_str_occup_id", str_occup_id);
                            Log.e("tell_str_dhosham", str_dhosham);
                            Log.e("tell_str_fam_type_id", str_fam_type_id);
                            Log.e("tell_str_fam_val_id", et_get_fam_val.getText().toString());
                            Log.e("tell_str_abt_you", str_abt_you);


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


                    RequestQueue requestQueue = Volley.newRequestQueue(TellUsMore.this);
                    requestQueue.add(stringRequest);


                    // altDialog.dismiss();
                }

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_get_martial_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_marital_status, "martialstatus");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "martialstatus");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });


        et_get_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_height, "height");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "height");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });


        et_get_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_weight, "weight");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "weight");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        et_get_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_country, "country");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "country");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        et_get_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP+"city.php", str_state_id, "city", "stateid");

                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                        for (int i = 0; i < return_beanArrayList.size(); i++) {

                            String text = return_beanArrayList.get(i).getStr_list_items().toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(return_beanArrayList.get(i));
                            }
                        }

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "city");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        et_get_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_search_profile_for.setText("");

                Log.i("TS", "Country_Id : " + str_country_id);

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP+"state.php", str_country_id, "state", "countryid");

                myDrawerLayout.openDrawer(myLinearLayout);

                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<ListDrawerBean> filteredList = new ArrayList<ListDrawerBean>();
                        for (int i = 0; i < return_beanArrayList.size(); i++) {

                            String text = return_beanArrayList.get(i).getStr_list_items().toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(return_beanArrayList.get(i));
                            }
                        }

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "state");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        et_get_qualif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_qualif, "qualification");

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
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        et_get_occup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_occupation, "occupation");

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
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        et_get_fam_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_fam_type, "familytype");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "familytype");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        et_get_fam_val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_fam_val, "familyvalue");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "familyvalue");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

    }

    public ArrayList<ListDrawerBean> restCallForStateCity(String str_url, final String str_id, final String str_type, final String stridType) {

        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(TellUsMore.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            //   ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray state = jsobj.getJSONArray("Response");

                                if (state.length() > 0) {
                                    for (int i = 0; i < state.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = state.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        return_beanArrayList.add(drawerBean);
                                    }
                                }

                                listDrawerAdapter = new ListDrawerAdapter(context, return_beanArrayList, (MyInterface) context, str_type);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TellUsMore.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(TellUsMore.this, "res : " + res, Toast.LENGTH_LONG).show();

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

                params.put(stridType, str_id);


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


        RequestQueue requestQueue = Volley.newRequestQueue(TellUsMore.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
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
            listView.setAdapter(listDrawerAdapter);
            listDrawerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanArrayList;

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("martialstatus")) {
            et_get_martial_status.setText(str_items);
            str_maritial_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("country")) {
            et_get_country.setText(str_items);
            str_country_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("state")) {
            et_get_state.setText(str_items);
            str_state_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("height")) {
            et_get_height.setText(str_items);
            str_height_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("weight")) {
            et_get_weight.setText(str_items);
            str_weight_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("city")) {
            et_get_city.setText(str_items);
            str_city_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("qualification")) {
            et_get_qualif.setText(str_items);
            str_qualif_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("occupation")) {
            et_get_occup.setText(str_items);
            str_occup_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("familytype")) {
            et_get_fam_type.setText(str_items);
            str_fam_type_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("familyvalue")) {
            et_get_fam_val.setText(str_items);
            str_fam_val_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id,String str_partner_name, String streFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
    }
}
