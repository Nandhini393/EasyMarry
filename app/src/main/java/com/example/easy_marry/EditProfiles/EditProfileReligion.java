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
import android.support.design.widget.TextInputLayout;
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
public class EditProfileReligion extends Activity implements MyInterface {
    Button btn_basic_det_save;
    ImageView img_back;
    View itemView1;
    Context context;

    EditText et_search_profile_for, et_religion, et_gowthram, et_caste, et_subcaste, et_zodiac, et_star;
    RadioButton rb_dhosham_yes, rb_dhosham_no, rb_dhosham_doesnt_matter;
    ArrayList<ListDrawerBean> return_beanArrayList;

    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout,ll_rb_dhosam;
    JSONArray json_religion, json_gowthram, json_zodiac;
    String str_religous_json, str_religion_id = "", str_gowthram_id = "", str_caste_id = "", str_subcaste_id = "", str_zodiac_id = "",
            str_star_id = "", str_dhosham_id = "2", str_user_id = "",str_from;
    GeneralData gD;
    IntentFilter filter1;
    TextView txt_drawer_error_msg;
    TextInputLayout tll_subcaste;
    TextView txt_dhosam_dont_know;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_religion);
        context = this;
        gD = new GeneralData(context);
        ll_rb_dhosam=(LinearLayout)findViewById(R.id.ll_rb_dhosam);
        tll_subcaste=(TextInputLayout)findViewById(R.id.input_layout_sub_caste);
        str_user_id = gD.prefs.getString("userid", null);
        str_religous_json = getIntent().getStringExtra("json_releigous_pref");
        str_from = getIntent().getStringExtra("json_response");
        Log.e("NN:edit_basic", str_religous_json);
        Log.e("NN:str_from", str_from);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        txt_drawer_error_msg = (TextView) findViewById(R.id.txt_draw_error);
        btn_basic_det_save = (Button) findViewById(R.id.btn_basic_det_save);
        img_back = (ImageView) findViewById(R.id.img_ed_back);
        txt_dhosam_dont_know=(TextView)findViewById(R.id.txt_dhosam_dont);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rb_dhosham_yes = (RadioButton) findViewById(R.id.radio_dhosam_yes);
        rb_dhosham_no = (RadioButton) findViewById(R.id.radio_dhosam_no);
        rb_dhosham_doesnt_matter = (RadioButton) findViewById(R.id.radio_dhosam_doesnt_matter);

        et_religion = (EditText) findViewById(R.id.et_religion);
        et_gowthram = (EditText) findViewById(R.id.et_gothram);
        et_caste = (EditText) findViewById(R.id.et_caste);
        et_subcaste = (EditText) findViewById(R.id.et_sub_caste);
        et_zodiac = (EditText) findViewById(R.id.et_zodiac);
        et_star = (EditText) findViewById(R.id.et_star);

        et_religion.setFocusable(false);
        et_gowthram.setFocusable(false);
        et_caste.setFocusable(false);
        et_subcaste.setFocusable(false);
        et_zodiac.setFocusable(false);
        et_star.setFocusable(false);
        rb_dhosham_no.setChecked(true);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileReligion.this,EditProfile.class));
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
                if(str_from.equalsIgnoreCase("user")){
                    Log.e("NM","user_:"+str_dhosham_id);
                    updateUserEditCall();
                }
                else  if(str_from.equalsIgnoreCase("partner")){
                    Log.e("NM","partner_:"+str_dhosham_id);
                    updatePartnerEditCall();
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
                    rb_dhosham_no.setChecked(true);
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                }
            }
        });
        rb_dhosham_doesnt_matter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_doesnt_matter.isChecked()) {
                    str_dhosham_id = "3";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_no.setChecked(false);
                }
            }
        });
        et_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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


                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "religion");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileReligion.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion= new JSONArray();
                    json_gowthram= new JSONArray();
                    json_zodiac= new JSONArray();
                }
            }
        });


        et_caste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "caste");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileReligion.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion= new JSONArray();
                    json_gowthram= new JSONArray();
                    json_zodiac= new JSONArray();
                }
            }
        });
        et_subcaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if(et_subcaste.getText().toString().trim().equalsIgnoreCase("Not Specified")){
                        et_subcaste.setEnabled(false);
                        et_subcaste.setTextColor(Color.parseColor("#000000"));
                    }
                    else {
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

                                listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "subcaste");
                                listView.setVisibility(View.VISIBLE);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged(); // data set changed
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(EditProfileReligion.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion= new JSONArray();
                    json_gowthram= new JSONArray();
                    json_zodiac= new JSONArray();
                }
            }
        });
        et_zodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "zodiac");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileReligion.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion= new JSONArray();
                    json_gowthram= new JSONArray();
                    json_zodiac= new JSONArray();
                }
            }
        });
        et_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                        listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "star");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(EditProfileReligion.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);

                    json_religion= new JSONArray();
                    json_gowthram= new JSONArray();
                    json_zodiac= new JSONArray();
                }
            }
        });

    }

    public void getEditBasicDetCall(String str_basic_json) {


        try {
            Log.i("HH", "strResp : " + str_basic_json);
            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

            JSONObject myReligiousJsonObj = new JSONObject(str_basic_json);

            String religion = myReligiousJsonObj.getString("religious");
            for (int i = 0; i < json_religion.length(); i++) {
                if (religion.equals(json_religion.getJSONObject(i).getString("value"))) {
                    str_religion_id = json_religion.getJSONObject(i).getString("id");
                    Log.i("RRR", "EditReg: str_religion_id" + str_religion_id);
                    break;
                }
            }


            String gothram = myReligiousJsonObj.getString("Gothram");
            for (int i = 0; i < json_gowthram.length(); i++) {
                if (gothram.equals(json_gowthram.getJSONObject(i).getString("value"))) {
                    str_gowthram_id = json_gowthram.getJSONObject(i).getString("id");
                    Log.i("RRR", "EditReg: str_gowthram_id" + str_gowthram_id);
                    break;
                }
            }


            //For caste
            /*Start for Caste*/
            final String caste = myReligiousJsonObj.getString("Caste");
            final String Sub_caste = myReligiousJsonObj.getString("Sub Caste");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "caste.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
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

                                            if (providersServiceJSONobject.getString("value").equalsIgnoreCase(caste)) {
                                                str_caste_id = providersServiceJSONobject.getString("id");
                                                Log.i("RRR", "EditReg: str_caste_id " + str_caste_id);
                                                getSubcaste(Sub_caste);
                                                break;
                                            }
                                        }
                                    }
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("religionid", str_religion_id);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(
                            "Authorization",
                            String.format("Basic %s", Base64.encodeToString(
                                    String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
                    return params;
                }

            };


            String zodiac_sign = myReligiousJsonObj.getString("Zodaic Sign");
            for (int i = 0; i < json_zodiac.length(); i++) {
                if (zodiac_sign.equals(json_zodiac.getJSONObject(i).getString("value"))) {
                    str_zodiac_id = json_zodiac.getJSONObject(i).getString("id");
                    Log.i("RRR", "EditReg: str_zodiac_id " + str_zodiac_id);
                    break;
                }
            }


            final String star = myReligiousJsonObj.getString("Star");

            StringRequest stringRequestStar = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "star.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
                            try {
                                Log.i("HH", "strResp : " + response);
                                //   ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                JSONObject jsobj = new JSONObject(response);

                                Log.i("HH", "strResp : " + response);
                                if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray state = jsobj.getJSONArray("Response");

                                    if (state.length() > 0) {
                                        for (int i = 0; i < state.length(); i++) {
                                            JSONObject providersServiceJSONobject = state.getJSONObject(i);

                                            if (providersServiceJSONobject.getString("value").equalsIgnoreCase(star)) {
                                                str_star_id = providersServiceJSONobject.getString("id");
                                                Log.i("RRR", "EditReg: str_star_id " + str_star_id);
                                                break;
                                            }
                                        }
                                    }

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rasiid", str_zodiac_id);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(
                            "Authorization",
                            String.format("Basic %s", Base64.encodeToString(
                                    String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
                    return params;
                }

            };



            RetryPolicy polic = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(polic);
            stringRequestStar.setRetryPolicy(polic);


            RequestQueue requestQ = Volley.newRequestQueue(EditProfileReligion.this);
            requestQ.add(stringRequest);
            requestQ.add(stringRequestStar);


            String dhosham = myReligiousJsonObj.getString("Dhosham");

            if (dhosham.equalsIgnoreCase("1")) {
                rb_dhosham_yes.setChecked(true);
                rb_dhosham_no.setChecked(false);
                if(str_from.equalsIgnoreCase("user")){
                    txt_dhosam_dont_know.setText("Dont'know");
                }
                else  if(str_from.equalsIgnoreCase("partner")){
                    txt_dhosam_dont_know.setText("Doesn't matter");
                }
                rb_dhosham_doesnt_matter.setChecked(false);
            } else if (dhosham.equalsIgnoreCase("2")) {
                rb_dhosham_yes.setChecked(false);
                rb_dhosham_no.setChecked(true);
                if(str_from.equalsIgnoreCase("user")){
                    txt_dhosam_dont_know.setText("Dont'know");
                }
                else if(str_from.equalsIgnoreCase("partner")){
                    txt_dhosam_dont_know.setText("Doesn't matter");
                }
                rb_dhosham_doesnt_matter.setChecked(false);
            }
            else if (dhosham.equalsIgnoreCase("3"))  {
                rb_dhosham_yes.setChecked(false);
                rb_dhosham_no.setChecked(false);
                if(str_from.equalsIgnoreCase("user")){
                    txt_dhosam_dont_know.setText("Dont'know");
                }
                else if(str_from.equalsIgnoreCase("partner")){
                    txt_dhosam_dont_know.setText("Doesn't matter");
                }
                rb_dhosham_doesnt_matter.setChecked(true);
            }
            et_religion.setText(religion);
            et_gowthram.setText(gothram);
            et_caste.setText(caste);
            et_subcaste.setText(Sub_caste);
            et_zodiac.setText(zodiac_sign);
            et_star.setText(star);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getSubcaste(final String sub_caste) {

        StringRequest stringRequestSubcaste = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "subcaste.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
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

                                        if (providersServiceJSONobject.getString("value").equalsIgnoreCase(sub_caste)) {
                                            str_subcaste_id = providersServiceJSONobject.getString("id");
                                            Log.i("RRR", "EditReg: str_caste_id " + str_caste_id);
                                            break;
                                        }
                                    }
                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("casteid", str_caste_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
                return params;
            }

        };

        RetryPolicy policySubcaste = new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequestSubcaste.setRetryPolicy(policySubcaste);


        RequestQueue requestQueueone = Volley.newRequestQueue(EditProfileReligion.this);
        requestQueueone.add(stringRequestSubcaste);
    }

    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        try {
            if (providerServicesMonth.length() > 0) {

              /*  if(str_from.equalsIgnoreCase("user")){
                    ListDrawerBean drawerBean1 = new ListDrawerBean();
                    drawerBean1.setStr_list_items_id("111");
                    drawerBean1.setStr_list_items("-select-");
                    beanArrayList.add(drawerBean1);
                }
                else  if(str_from.equalsIgnoreCase("partner")){
                    ListDrawerBean drawerBean1 = new ListDrawerBean();
                    drawerBean1.setStr_list_items_id("0");
                    drawerBean1.setStr_list_items("Any");
                    beanArrayList.add(drawerBean1);
                }
*/
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

    public ArrayList<ListDrawerBean> restCallForSubcaste(String str_url, final String str_id, final String str_type, final String stridType) {


        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
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

                                listDrawerAdapter = new ListDrawerAdapter(context, return_beanArrayList, (MyInterface) context, str_type);
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
                        //  Toast.makeText(UserRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(UserRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileReligion.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }

    public ArrayList<ListDrawerBean> restCallForSubcast(String str_url, final String str_id, final String str_type, final String stridType) {


        final ArrayList return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(UserRegTwo.this, response, Toast.LENGTH_LONG).show();
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

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(stridType, str_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "admin", "EasyMarry2016").getBytes(), Base64.DEFAULT)));
                return params;
            }

        };

//30Secs
        RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileReligion.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }


    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("religion")) {
            et_religion.setText(str_items);
            str_religion_id = str_items_id;
            if (str_religion_id.equalsIgnoreCase("1")) {
                et_gowthram.setVisibility(View.VISIBLE);
                et_gowthram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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


                                listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "gowthram");
                                listView.setVisibility(View.VISIBLE);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged(); // data set changed
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                    }
                });


            } else {
                et_gowthram.setVisibility(View.GONE);
            }
            et_gowthram.setText("");
            et_caste.setText("");
            et_subcaste.setText("");
            str_gowthram_id="";
            str_caste_id="";
            str_subcaste_id="";
        } else if (strIdentify.equalsIgnoreCase("caste")) {
            et_caste.setText(str_items);
            str_caste_id = str_items_id;
            et_subcaste.setText("");
            str_subcaste_id="";
            restCallForSubcaste(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");
        } else if (strIdentify.equalsIgnoreCase("subcaste")) {
            et_subcaste.setText(str_items);
            str_subcaste_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("zodiac")) {
            et_zodiac.setText(str_items);
            str_zodiac_id = str_items_id;
            et_star.setText("");
            str_star_id="";
        } else if (strIdentify.equalsIgnoreCase("star")) {
            et_star.setText(str_items);
            str_star_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("gowthram")) {
            et_gowthram.setText(str_items);
            str_gowthram_id = str_items_id;
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

    public void updateUserEditCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_user2.php",
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
                                String str_span = "Your religious details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 23, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfileReligion.this, EditProfile.class));
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

                if (str_religion_id.length() > 0) {
                    params.put("religion", str_religion_id);
                }
                if (str_gowthram_id.length() > 0) {
                    params.put("gothram", str_gowthram_id);
                }
                if (str_caste_id.length() > 0) {
                    params.put("caste", str_caste_id);
                }
                if (str_subcaste_id.length() > 0) {
                    params.put("subcaste", str_subcaste_id);
                }
                if (str_zodiac_id.length() > 0) {
                    params.put("zodiac", str_zodiac_id);
                }
                if (str_star_id.length() > 0) {
                    params.put("star", str_star_id);
                }
                if (str_dhosham_id.length() > 0) {
                    params.put("dhosham", str_dhosham_id);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileReligion.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }
    public void updatePartnerEditCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_partner2.php",
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
                                String str_span = "Your partner religious details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 30, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfileReligion.this, EditProfile.class));
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
                Log.e("NN","str_dhosham_id"+ str_dhosham_id);

                if (str_religion_id.length() > 0) {
                    params.put("partnerreligion", str_religion_id);
                }
                if (str_gowthram_id.length() > 0) {
                    params.put("partnergothram", str_gowthram_id);
                }
                if (str_caste_id.length() > 0) {
                    params.put("partnercaste", str_caste_id);
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
                if (str_dhosham_id.length() > 0) {
                    params.put("partnerdhosham", str_dhosham_id);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileReligion.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

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

                                        JSONArray json_array_religion = jsobj.getJSONArray("religion");

                                        json_religion = jsobj.getJSONArray("religion");
                                        json_gowthram = jsobj.getJSONArray("Gowthram");
                                        //  json_caste = jsobj.getJSONArray("Caste");
                                        json_zodiac = jsobj.getJSONArray("Zodiac");
                                        getEditBasicDetCall(str_religous_json);

                                        if (json_religion.length() > 0) {
                                            for (int i = 0; i < json_array_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_religion.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(context, beanArrayList, (MyInterface) context, "religion");
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
                                //Toast.makeText(UserRegTwo.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        // Toast.makeText(UserRegTwo.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(EditProfileReligion.this);
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
