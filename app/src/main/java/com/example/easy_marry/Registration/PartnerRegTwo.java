package com.example.easy_marry.Registration;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
public class PartnerRegTwo extends Activity implements MyInterface{

    EditText et_religion, et_gowthram, et_caste, et_subcaste, et_zodiac, et_star, et_mother_ton, et_marital_status, et_childrens, et_search_profile_for;
    RadioButton rb_dhosham_yes,rb_count_child_no,rb_count_child_yes, rb_dhosham_no, rb_dhosham_doesnt_matter, rb_other_comm_yes, rb_other_comm_no;
    Context c;
    Button btn_next;
    JSONArray json_religion, json_caste, json_gowthram,json_zodiac, json_marital_status, json_mother_ton;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    String str_religion_id="",str_countchild_id="no", str_gowthram_id="", str_caste_id="", str_subcaste_id="", str_zodiac_id="", str_star_id="",
            str_dhosham_id="2", str_mother_ton_id="", str_marital_id="", str_child_id, str_other_comm_id;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout,ll_partner_child;
    ArrayList<ListDrawerBean> return_beanArrayList;

    String str_user_id="";
    GeneralData gD;
    TextView txt_skip,txt_drawer_error_msg;
    ImageView img_back;
    TextInputLayout tll_gothram;
    IntentFilter filter1;
    TextInputLayout tll_caste,tll_subcaste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_partner2);
        c = this;
        gD = new GeneralData(c);
        str_user_id = gD.prefs.getString("userid", null);

        tll_gothram=(TextInputLayout)findViewById(R.id.input_layout_gothram);
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_skip = (TextView) findViewById(R.id.txt_skip);
        btn_next = (Button) findViewById(R.id.btn_next);

        tll_caste=(TextInputLayout)findViewById(R.id.input_layout_caste);
        tll_subcaste=(TextInputLayout)findViewById(R.id.input_layout_subcaste);

        et_religion = (EditText) findViewById(R.id.et_religion);
        et_gowthram = (EditText) findViewById(R.id.et_gothram);
        et_caste = (EditText) findViewById(R.id.et_caste);
        et_subcaste = (EditText) findViewById(R.id.et_subcaste);
        et_zodiac = (EditText) findViewById(R.id.et_zodiac);
        et_star = (EditText) findViewById(R.id.et_star);
        et_mother_ton = (EditText) findViewById(R.id.et_mother_ton);
        et_marital_status = (EditText) findViewById(R.id.et_marital_status);
      //  et_childrens = (EditText) findViewById(R.id.et_no_of_children);

        rb_count_child_no = (RadioButton) findViewById(R.id.radio_yes);
        rb_count_child_yes = (RadioButton) findViewById(R.id.radio_no);


        ll_partner_child=(LinearLayout)findViewById(R.id.ll_part_child);

        rb_dhosham_yes = (RadioButton) findViewById(R.id.radio_dhosam_yes);
        rb_dhosham_no = (RadioButton) findViewById(R.id.radio_dhosam_no);

        rb_dhosham_doesnt_matter = (RadioButton) findViewById(R.id.radio_dhosam_doesnt_matter);

        txt_drawer_error_msg=(TextView)findViewById(R.id.txt_draw_error);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);

        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        et_religion.setFocusable(false);
        et_gowthram.setFocusable(false);
        et_caste.setFocusable(false);
        et_subcaste.setFocusable(false);
        et_zodiac.setFocusable(false);
        et_star.setFocusable(false);
        et_mother_ton.setFocusable(false);
        et_marital_status.setFocusable(false);

        et_caste.setEnabled(false);
        et_subcaste.setEnabled(false);
        et_star.setEnabled(false);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartnerRegTwo.this, Matches.class));
                finish();
            }
        });
        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartnerRegTwo.this, PartnerRegThree.class));
                finish();
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                Log.e("str_dhosham_id", str_dhosham_id);
                Log.e("str_caste_id", str_caste_id);
//              Log.e("str_child_id", str_child_id);
                Log.e("str_marital_id", str_marital_id);
//              Log.e("str_child_id", str_child_id);
                Log.e("str_mother_ton_id", str_mother_ton_id);
                Log.e("str_star_id", str_star_id);
                Log.e("str_zodiac_id", str_zodiac_id);

                Log.e("str_subcaste_id", str_subcaste_id);
                Log.e("str_religion_id", str_religion_id);
                //Log.e("str_gowthram_id", str_gowthram_id);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register7_screen.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                               // Toast.makeText(PartnerRegTwo.this, response, Toast.LENGTH_LONG).show();
                                try {
                                    Log.i("HH", "strResp : " + response);
                                    ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                    JSONObject jsobj = new JSONObject(response);

                                    Log.i("HH", "strResp : " + response);
                                    if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                        String strName=jsobj.getJSONObject("Response").getString("Name");
                                        String strUserId=jsobj.getJSONObject("Response").getString("userid");
                                        String strCompleteLevel=jsobj.getJSONObject("Response").getString("completeLevel");
                                        String strMembership=jsobj.getJSONObject("Response").getString("membership");
                                        String strScreenId=jsobj.getJSONObject("Response").getString("screen_id");
                                        String strProfileImage=GeneralData.LOCAL_IP_IMAGE + "upload/" +jsobj.getJSONObject("Response").getString("profileImage");
                                        String strEasyMarryId=jsobj.getJSONObject("Response").getString("easymarryid");
                                        String strMemPlan=jsobj.getJSONObject("Response").getString("membershipplan");
                                        String strMemValid=jsobj.getJSONObject("Response").getString("membershipvalidity");
                                        String strRating = jsobj.getJSONObject("Response").getString("Rating");

                           /* GeneralData.str_name = jsobj.getJSONObject("Response").getString("Name");
                            GeneralData.str_id = jsobj.getJSONObject("Response").getString("userid");
                            GeneralData.str_complete_profile_level = jsobj.getJSONObject("Response").getString("completeLevel");
                            GeneralData.str_image = GeneralData.LOCAL_IP_IMAGE + "images/" + jsobj.getJSONObject("Response").getString("profileImage");
                            GeneralData.str_membership = jsobj.getJSONObject("Response").getString("membership");
                          */


                                        Log.e("NNp2:profileimage",strProfileImage);
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

                                        Log.e("NN:name",strName);
                                        Log.e("NN:userid",strUserId);
                                        Log.e("NN:completelevel",strCompleteLevel);
                                        Log.e("NN:membership",strMembership);
                                        Log.e("NN:profileimage",strProfileImage);
                                        Log.e("NN:screenid",strScreenId);
                                        Log.e("NN:easymarryid",strEasyMarryId);
                                        Log.e("NN:memplan",strMemPlan);
                                        Log.e("NN:memvalid",strMemValid);

                                        Intent i = new Intent(PartnerRegTwo.this, PartnerRegThree.class);
                                        i.putExtra("user_id", str_user_id);
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
                                //Toast.makeText(PartnerRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                      //  Toast.makeText(PartnerRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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

                        if (str_user_id.length() > 0) {
                            params.put("userid", str_user_id);
                        }
                        if (str_caste_id.length() > 0) {
                            params.put("partnercaste", str_caste_id);
                        }
                        if (str_dhosham_id.length() > 0) {
                            params.put("partnerdhosham", str_dhosham_id);
                        }
                        if (str_gowthram_id.length() > 0) {
                            params.put("partnergothram", str_gowthram_id);
                        }
                        if (str_religion_id.length() > 0) {
                            params.put("partnerreligion", str_religion_id);
                        }
                        if (str_subcaste_id.length() > 0) {
                            params.put("partnersubcaste", str_subcaste_id);
                        }
                        if (str_zodiac_id.length() > 0) {
                            params.put("partnerzodiac", str_zodiac_id);
                        }
                        if (str_star_id.length() > 0) {
                            params.put("partnerstar", str_star_id);
                        }
                        if (str_mother_ton_id.length() > 0) {
                            params.put("partnermothertongue", str_mother_ton_id);
                        }

                        if (str_marital_id.length() > 0) {
                            params.put("partnermaritalstatus", str_marital_id);
                        }
                        if (str_countchild_id.length() > 0) {
                            params.put("partnernoofchild", str_countchild_id);
                        }

                   //     params.put("partnermaritalstatus", str_child_id);





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
                RequestQueue requestQueue = Volley.newRequestQueue(PartnerRegTwo.this);
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
        rb_dhosham_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_yes.isChecked()) {
                    str_dhosham_id = "1";
                    rb_dhosham_no.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                }
            }
        });
        rb_dhosham_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_no.isChecked()) {
                    str_dhosham_id = "2";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                }
            }
        });
        rb_dhosham_doesnt_matter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_doesnt_matter.isChecked()) {
                    str_dhosham_id="3";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_no.setChecked(false);
                }
            }
        });



        rb_count_child_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_count_child_yes.isChecked()) {
                    str_countchild_id="yes";
                    rb_count_child_no.setChecked(false);

                }
            }
        });
        rb_count_child_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_count_child_no.isChecked()) {
                    str_countchild_id="no";
                    rb_count_child_yes.setChecked(false);

                }
            }
        });



        et_mother_ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_mother_ton, "mothertongue");
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


                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "mothertongue");
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
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


                }

            }
        });
        et_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {

                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_religion, "religion");


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


                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "religion");
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
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


                }
            }
        });


        et_caste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {

                    myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                //final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_caste, "caste");
                return_beanArrayList = restCallForSubcaste(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "caste", "religionid");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "caste");
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
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


                }
            }
        });
        et_subcaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (et_subcaste.getText().toString().trim().equalsIgnoreCase("Not Specified")) {
                        et_subcaste.setEnabled(false);
                        et_subcaste.setTextColor(Color.parseColor("#000000"));
                    } else {
                        myDrawerLayout.openDrawer(myLinearLayout);
                        et_search_profile_for.setText("");
                        return_beanArrayList = restCallForSubcaste(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");


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

                                listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "subcaste");
                                listView.setVisibility(View.VISIBLE);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged(); // data set changed
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                }
                else{
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


                }
            }
        });
        et_zodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_zodiac, "zodiac");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "zodiac");
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
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


                }
            }
        });
        et_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                return_beanArrayList = restCallForSubcaste(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "star", "rasiid");


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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "star");
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
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


                }
            }
        });
        et_marital_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_marital_status, "marital_status");

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

                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "marital_status");
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
                    Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion=new JSONArray();
                    json_caste=new JSONArray();
                    json_gowthram =new JSONArray();
                    json_zodiac=new JSONArray();
                    json_marital_status=new JSONArray();
                    json_mother_ton=new JSONArray();


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
            Toast.makeText(PartnerRegTwo.this, "list is empty", Toast.LENGTH_SHORT).show();
        }
        return beanArrayList;

    }

    public ArrayList<ListDrawerBean> restCallForSubcaste(String str_url, final String str_id, final String str_type, final String stridType) {


        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(PartnerRegTwo.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            //   ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray state = jsobj.getJSONArray("Response");

                                if (state.length() > 0) {
                                    if(str_type.equalsIgnoreCase("subcaste")){
                                        tll_subcaste.setVisibility(View.VISIBLE);
                                    }
                                    for (int i = 0; i < state.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = state.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        return_beanArrayList.add(drawerBean);
                                    }
                                }
                                else{
                                    if(str_type.equalsIgnoreCase("subcaste")){
                                        tll_subcaste.setVisibility(View.VISIBLE);
                                        et_subcaste.setText("Not Specified");
                                    }
                                }
                                listDrawerAdapter = new ListDrawerAdapter(c, return_beanArrayList, (MyInterface) c, str_type);
                                listView.setVisibility(View.VISIBLE);
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
                      //  Toast.makeText(PartnerRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                               // Toast.makeText(PartnerRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(PartnerRegTwo.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("religion")) {
            et_religion.setText(str_items);
            str_religion_id = str_items_id;
            if (str_religion_id.equalsIgnoreCase("1")) {
                tll_gothram.setVisibility(View.VISIBLE);
                et_gowthram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                        boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                        if (isConnected) {
                            myDrawerLayout.openDrawer(myLinearLayout);
                            et_search_profile_for.setText("");

                            final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_gowthram, "gowthram");
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


                                    listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "gowthram");
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
                            Toast.makeText(PartnerRegTwo.this, "No response from server", Toast.LENGTH_LONG).show();
                            txt_drawer_error_msg.setVisibility(View.VISIBLE);

                            json_religion=new JSONArray();
                            json_caste=new JSONArray();
                            json_gowthram =new JSONArray();
                            json_zodiac=new JSONArray();
                            json_marital_status=new JSONArray();
                            json_mother_ton=new JSONArray();


                        }
                    }
                });


            }
            else {
                tll_gothram.setVisibility(View.GONE);
            }
            et_caste.setEnabled(true);
            et_subcaste.setEnabled(false);
            et_subcaste.setText("");
            et_caste.setText("");
        } else if (strIdentify.equalsIgnoreCase("caste")) {
            et_caste.setText(str_items);
            str_caste_id = str_items_id;
            et_subcaste.setEnabled(true);
            et_subcaste.setText("");
            restCallForSubcaste(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");
        } else if (strIdentify.equalsIgnoreCase("subcaste")) {
            et_subcaste.setText(str_items);
            str_subcaste_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("zodiac")) {
            et_zodiac.setText(str_items);
            str_zodiac_id = str_items_id;
            et_star.setEnabled(true);
        }
        else if (strIdentify.equalsIgnoreCase("star")) {
            et_star.setText(str_items);
            str_star_id = str_items_id;
        }
        else if (strIdentify.equalsIgnoreCase("mothertongue")) {
            et_mother_ton.setText(str_items);
            str_mother_ton_id = str_items_id;
        }
        else if (strIdentify.equalsIgnoreCase("marital_status")) {
            et_marital_status.setText(str_items);
            str_marital_id = str_items_id;
            if (str_marital_id.equalsIgnoreCase("2") || str_marital_id.equalsIgnoreCase("3") || str_marital_id.equalsIgnoreCase("4")) {
                    ll_partner_child.setVisibility(View.VISIBLE);
            } else {
                ll_partner_child.setVisibility(View.GONE);
            }
        }
        else if(strIdentify.equalsIgnoreCase("gowthram")){
            et_gowthram.setText(str_items);
            str_gowthram_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PartnerRegTwo.this, Matches.class));
        finish();

    }

    @Override
    public String get_matches(String str_id,String str_partner_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
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

                                        JSONArray json_array_religion = jsobj.getJSONArray("religion");

                                        json_religion = jsobj.getJSONArray("religion");
                                        json_caste = jsobj.getJSONArray("Caste");
                                        json_zodiac = jsobj.getJSONArray("Zodiac");
                                        json_gowthram = jsobj.getJSONArray("Gowthram");
                                        json_marital_status = jsobj.getJSONArray("martialstatus");
                                        json_mother_ton = jsobj.getJSONArray("mothertongue");
                             /*   str_profile_for_id = profile_created_by.getJSONObject(0).getString("id");
                                str_religion_id = jsonArray_religion.getJSONObject(0).getString("id");
                                str_mother_tonngue_id = jsonArray_mother_tongue.getJSONObject(0).getString("id");*/


                                        if (json_religion.length() > 0) {
                                            for (int i = 0; i < json_array_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_religion.getJSONObject(i);
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
                                //  Toast.makeText(PartnerRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        // Toast.makeText(PartnerRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(PartnerRegTwo.this);
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
