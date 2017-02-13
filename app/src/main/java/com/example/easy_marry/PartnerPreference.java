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
 * Created by android2 on 13/9/16.
 */
public class PartnerPreference extends Activity implements MyInterface {
    EditText et_get_groom_age, et_get_height, et_get_weight,
            et_get_martial_status, et_get_mother_tongue, et_get_body_type, et_search_profile_for, et_get_profile_created;
    Button btn_save_partner_pref;
    RadioButton rb_phy_normal, rb_phy_challenged, rb_phy_doesntmatter, rb_all, rb_veg, rb_non_veg, rb_egg, rb_smoke_yes, rb_smoke_no,
            rb_smoke_social, rb_drink_yes, rb_drink_no, rb_drink_social;

    ImageView img_back;
    String str_phy_status = "1";
    String str_eating = "4";
    String str_drinking = "1";
    String str_smoking = "1";
    ListView listView;
    String str_age_id, str_height_id, str_weight_id, str_profile_id, str_marital_id, str_mother_tong_id, str_body_type_id, str_user_id;
    Context context;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    ListDrawerAdapter listDrawerAdapter;
    JSONArray jsonArray_age, jsonArray_height, jsonArray_weight, jsonArray_marital_status, jsonArray_mother_tongue, jsonArray_body_type, jsonArray_my_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_preference);
        context = this;
        str_user_id = getIntent().getStringExtra("user_id");
        et_get_groom_age = (EditText) findViewById(R.id.et_groom_age);
        et_get_height = (EditText) findViewById(R.id.et_height);
        et_get_weight = (EditText) findViewById(R.id.et_weight);
        et_get_martial_status = (EditText) findViewById(R.id.et_martialstatus);
        et_get_mother_tongue = (EditText) findViewById(R.id.et_mothertongue);
        et_get_body_type = (EditText) findViewById(R.id.et_body_type);
        et_get_profile_created = (EditText) findViewById(R.id.et_profile_created);
        btn_save_partner_pref = (Button) findViewById(R.id.btn_partner_det_save);
        img_back = (ImageView) findViewById(R.id.img_back);
        rb_phy_normal = (RadioButton) findViewById(R.id.radio_normal);
        rb_phy_challenged = (RadioButton) findViewById(R.id.radio_physic);
        rb_phy_doesntmatter = (RadioButton) findViewById(R.id.radio_doesnt);
        listView = (ListView) findViewById(R.id.drawer_listview);
        rb_all = (RadioButton) findViewById(R.id.radio_eat_all);
        rb_veg = (RadioButton) findViewById(R.id.radio_eat_veg);
        rb_non_veg = (RadioButton) findViewById(R.id.radio_eat_non_veg);
        rb_egg = (RadioButton) findViewById(R.id.radio_eat_egg);

        rb_drink_yes = (RadioButton) findViewById(R.id.radio_drink_yes);
        rb_drink_no = (RadioButton) findViewById(R.id.radio_drink_no);
        rb_drink_social = (RadioButton) findViewById(R.id.radio_drink_social);


        rb_smoke_yes = (RadioButton) findViewById(R.id.radio_smoke_yes);
        rb_smoke_no = (RadioButton) findViewById(R.id.radio_smoke_no);
        rb_smoke_social = (RadioButton) findViewById(R.id.radio_smoke_social);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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


        rb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_eating = "4";
                }
            }
        });
        rb_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_eating = "1";
                }
            }
        });
        rb_non_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_eating = "2";
                }
            }
        });
        rb_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_eating = "3";
                }
            }
        });


        rb_drink_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_drinking = "1";
                }
            }
        });
        rb_drink_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_drinking = "2";
                }
            }
        });
        rb_drink_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_drinking = "3";
                }
            }
        });


        rb_smoke_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_smoking = "1";
                }
            }
        });
        rb_smoke_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_smoking = "2";
                }
            }
        });
        rb_smoke_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_smoking = "3";
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
        et_get_profile_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_my_profile, "profile");

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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "profile");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });
        et_get_mother_tongue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_mother_tongue, "mothertongue");
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


                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "mothertongue");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        et_get_groom_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_age, "age");
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


                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "age");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        et_get_body_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_body_type, "bodytype");
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


                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "bodytype");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"register_select.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(PartnerPreference.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray jsonAge = jsobj.getJSONArray("Age");
                                jsonArray_age = jsobj.getJSONArray("Age");
                                jsonArray_height = jsobj.getJSONArray("Height");
                                jsonArray_weight = jsobj.getJSONArray("weight");
                                jsonArray_marital_status = jsobj.getJSONArray("martialstatus");
                                jsonArray_mother_tongue = jsobj.getJSONArray("mothertongue");
                                jsonArray_body_type = jsobj.getJSONArray("Body Type");
                                jsonArray_my_profile = jsobj.getJSONArray("profile");


                                if (jsonArray_age.length() > 0) {
                                    for (int i = 0; i < jsonAge.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = jsonArray_age.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        beanArrayList.add(drawerBean);
                                    }
                                }

                                listDrawerAdapter = new ListDrawerAdapter(context, beanArrayList, (MyInterface) context, "age");
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
                        Toast.makeText(PartnerPreference.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(PartnerPreference.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(PartnerPreference.this);
        requestQueue.add(stringRequest);


        btn_save_partner_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_get_groom_age.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select groom age", Toast.LENGTH_SHORT).show();

                } else if (et_get_height.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select weight", Toast.LENGTH_SHORT).show();

                } else if (et_get_weight.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select weight", Toast.LENGTH_SHORT).show();

                } else if (et_get_martial_status.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select martial status", Toast.LENGTH_SHORT).show();

                } else if (et_get_mother_tongue.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select mother tongue", Toast.LENGTH_SHORT).show();

                } else if (et_get_body_type.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select body type", Toast.LENGTH_SHORT).show();

                } else if (et_get_profile_created.getText().toString().length() == 0) {
                    Toast.makeText(PartnerPreference.this, "Select profile created by", Toast.LENGTH_SHORT).show();

                } else {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"register-partner-preference.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(PartnerPreference.this, response, Toast.LENGTH_LONG).show();
                                    try {
                                        Log.i("HH", "strResp : " + response);
                                        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                        JSONObject jsobj = new JSONObject(response);

                                        Log.i("HH", "strResp : " + response);
                                        if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                            Intent i = new Intent(PartnerPreference.this, SignInPage.class);
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
                                    Toast.makeText(PartnerPreference.this, error.toString(), Toast.LENGTH_LONG).show();
                                    NetworkResponse response = error.networkResponse;
                                    if (error instanceof ServerError && response != null) {
                                        try {
                                            String res = new String(response.data,
                                                    HttpHeaderParser.parseCharset(response.headers));

                                            Toast.makeText(PartnerPreference.this, "res : " + res, Toast.LENGTH_LONG).show();

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
                            params.put("groomage", str_age_id);
                            params.put("height", str_height_id);
                            params.put("weight", str_weight_id);
                            params.put("maritalstatus", str_marital_id);
                            params.put("mothertongue", str_mother_tong_id);
                            params.put("physicalstatus", str_phy_status);
                            params.put("bodytype", str_body_type_id);
                            params.put("profilecreated", str_profile_id);

                            params.put("eatinghabit", str_eating);
                            params.put("drinkinghabit", str_drinking);
                            params.put("smokinghabit", str_smoking);


                            Log.e("tell_str_user_id", str_user_id);
                            Log.e("tell_str_groomage", str_age_id);
                            Log.e("tell_str_height_id", str_height_id);
                            Log.e("tell_str_weight_id", str_weight_id);
                            Log.e("tell_str_physicalstatus", str_phy_status);
                            Log.e("tell_str_maritalstatus", str_marital_id);
                            Log.e("tell_str_mothertongue", str_mother_tong_id);
                            Log.e("tell_str_bodytype", str_body_type_id);
                            Log.e("tell_str_profilecreated", str_profile_id);
                            Log.e("tell_str_eatinghabit", str_eating);
                            Log.e("tell_str_drinkinghabit", str_drinking);
                            Log.e("tell_str_smokinghabit", str_smoking);


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


                    RequestQueue requestQueue = Volley.newRequestQueue(PartnerPreference.this);
                    requestQueue.add(stringRequest);


                }
            }
        });

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
        if (strIdentify.equalsIgnoreCase("age")) {
            et_get_groom_age.setText(str_items);
            str_age_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("height")) {
            et_get_height.setText(str_items);
            str_height_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("weight")) {
            et_get_weight.setText(str_items);
            str_weight_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("martialstatus")) {
            et_get_martial_status.setText(str_items);
            str_marital_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("mothertongue")) {
            et_get_mother_tongue.setText(str_items);
            str_mother_tong_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("bodytype")) {
            et_get_body_type.setText(str_items);
            str_body_type_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("profile")) {
            et_get_profile_created.setText(str_items);
            str_profile_id = str_items_id;
        }

        myDrawerLayout.closeDrawers();

    }

    @Override
    public String get_matches(String str_id, String str_partner_name,String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal) {
        return null;
    }
}
