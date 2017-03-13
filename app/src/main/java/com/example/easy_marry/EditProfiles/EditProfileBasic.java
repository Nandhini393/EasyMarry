package com.example.easy_marry.EditProfiles;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 17/6/16.
 */
public class EditProfileBasic extends Activity implements MyInterface {
    Button btn_basic_det_save;
    ImageView img_back;
    View itemView1;
    Context context;
    EditText et_search_profile_for, et_age, et_name, et_height, et_weight,
            et_marital_stat, et_mother_ton, et_body_type, et_phy_stat, et_profile_create, et_get_dob, et_childrens;
    RadioButton rb_eat_all, rb_eat_veg, rb_eat_nv, rb_eat_egg,
            rb_drink_yes, rb_drink_no, rb_drink_social,
            rb_smoke_yes, rb_smoke_no, rb_smoke_social,
            rb_male, rb_female, rb_count_child_no, rb_count_child_yes;
    String str_eat = "4", str_drink = "2", str_smoke = "2", str_gender = "male", str_child = "no";
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout, ll_gender, ll_partner_child;
    JSONArray json_height, json_weight, json_marital_stat, json_mother_ton, json_body_type,
            json_phy_stat, json_profile_create, json_par_age, json_par_height, json_par_weight;
    String str_basic_json, str_profile_id = "", str_age_id = "", str_weight_id = "", str_name = "", str_dob = "", str_uchild = "",
            str_height_id = "", str_marital_id = "", str_mother_ton_id = "", str_bodytype_id = "", str_phy_stat_id = "", str_user_id = "", str_from;
    GeneralData gD;
    int year, month, day;
    IntentFilter filter1;
    TextView txt_drawer_error_msg;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_basic);
        context = this;
        gD = new GeneralData(context);
        str_user_id = gD.prefs.getString("userid", null);
        str_basic_json = getIntent().getStringExtra("json_basic_pref");
        str_from = getIntent().getStringExtra("json_response");
        Log.e("NN:edit_basic", str_basic_json);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        txt_drawer_error_msg = (TextView) findViewById(R.id.txt_draw_error);

        ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
        btn_basic_det_save = (Button) findViewById(R.id.btn_basic_det_save);
        img_back = (ImageView) findViewById(R.id.img_ed_back);
        listView = (ListView) findViewById(R.id.drawer_listview);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        et_phy_stat = (EditText) findViewById(R.id.et_phy_status);
        et_profile_create = (EditText) findViewById(R.id.et_prof_create);

        et_childrens = (EditText) findViewById(R.id.et_no_of_children);
        ll_partner_child = (LinearLayout) findViewById(R.id.ll_part_child);

        et_name = (EditText) findViewById(R.id.et_username);
        et_get_dob = (EditText) findViewById(R.id.et_dob);
        et_age = (EditText) findViewById(R.id.et_age);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_marital_stat = (EditText) findViewById(R.id.et_marital_status);
        et_mother_ton = (EditText) findViewById(R.id.et_mothertongue);
        et_body_type = (EditText) findViewById(R.id.et_body_type);


        et_age.setFocusable(false);
        et_get_dob.setFocusable(false);
        et_height.setFocusable(false);
        et_weight.setFocusable(false);
        et_marital_stat.setFocusable(false);
        et_mother_ton.setFocusable(false);
        et_body_type.setFocusable(false);
        et_phy_stat.setFocusable(false);
        et_profile_create.setFocusable(false);
       // et_name.setCursorVisible(false);
        rb_male = (RadioButton) findViewById(R.id.radio_male);
        rb_female = (RadioButton) findViewById(R.id.radio_female);

        rb_eat_all = (RadioButton) findViewById(R.id.radio_eating_all);
        rb_eat_veg = (RadioButton) findViewById(R.id.radio_eating_veg);
        rb_eat_nv = (RadioButton) findViewById(R.id.radio_eating_nonveg);
        rb_eat_egg = (RadioButton) findViewById(R.id.radio_eating_egg);

        rb_drink_yes = (RadioButton) findViewById(R.id.radio_drink_yes);
        rb_drink_no = (RadioButton) findViewById(R.id.radio_drink_no);
        rb_drink_social = (RadioButton) findViewById(R.id.radio_drink_social);

        rb_smoke_yes = (RadioButton) findViewById(R.id.radio_smoke_yes);
        rb_smoke_no = (RadioButton) findViewById(R.id.radio_smoke_no);
        rb_smoke_social = (RadioButton) findViewById(R.id.radio_smoke_social);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileBasic.this, EditProfile.class));
                finish();
            }
        });
        et_get_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
        rb_count_child_no = (RadioButton) findViewById(R.id.radio_no);
        rb_count_child_yes = (RadioButton) findViewById(R.id.radio_yes);

        btn_basic_det_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (str_from.equalsIgnoreCase("user")) {
                        str_name = et_name.getText().toString().trim();
                        SharedPreferences.Editor prefEdit = gD.prefs.edit();
                        prefEdit.putString("name", str_name);
                        prefEdit.commit();
                        str_dob = et_get_dob.getText().toString().trim();
                        str_uchild = et_childrens.getText().toString().trim();
                        if(str_name.length()==0){
                            Toast.makeText(EditProfileBasic.this, "Enter name", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_height.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select height", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_weight.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select weight", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_marital_stat.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select marital status", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_mother_ton.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select mother tongue", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_phy_stat.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select physical status", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_body_type.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select body type", Toast.LENGTH_SHORT).show();
                        }
                        else if(et_profile_create.getText().toString().trim().equalsIgnoreCase("-select-")){
                            Toast.makeText(EditProfileBasic.this, "Select profile created for", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            updateUserBasicDet();
                        }


                    } else {
                        updatePartnerBasicDet();
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


        rb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    str_gender = "male";
                }
                //Do something

                else {
                    // Toast.makeText(UserRegOne.this, "select gender", Toast.LENGTH_SHORT).show();
                }
                //do something else

            }
        });
        rb_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    str_gender = "female";
                }
                //Do something

                else {
                    //  Toast.makeText(UserRegOne.this, "select gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rb_eat_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_all.isChecked()) {
                    str_eat = "4";
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
            }
        });
        rb_eat_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_veg.isChecked()) {
                    str_eat = "1";
                    rb_eat_all.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
            }
        });
        rb_eat_nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_nv.isChecked()) {
                    str_eat = "2";
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_egg.setChecked(false);

                }
            }
        });
        rb_eat_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_eat_egg.isChecked()) {
                    str_eat = "3";
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);

                }
            }
        });


        rb_drink_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_drink_yes.isChecked()) {
                    str_drink = "1";
                    rb_drink_no.setChecked(false);
                    rb_drink_social.setChecked(false);

                }
            }
        });
        rb_drink_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_drink_no.isChecked()) {
                    str_drink = "2";
                    rb_drink_yes.setChecked(false);
                    rb_drink_social.setChecked(false);

                }
            }
        });
        rb_drink_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_drink_social.isChecked()) {

                    str_drink = "3";

                    rb_drink_no.setChecked(false);
                    rb_drink_yes.setChecked(false);

                }
            }
        });

        rb_smoke_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_smoke_yes.isChecked()) {

                    str_smoke = "1";

                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(false);

                }
            }
        });
        rb_smoke_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_smoke_no.isChecked()) {
                    str_smoke = "2";
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_social.setChecked(false);

                }
            }
        });
        rb_smoke_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_smoke_social.isChecked()) {
                    str_smoke = "3";
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(false);

                }
            }
        });

        rb_count_child_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_count_child_yes.isChecked()) {
                    str_child = "yes";
                    rb_count_child_no.setChecked(false);

                }
            }
        });
        rb_count_child_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_count_child_no.isChecked()) {
                    str_child = "no";
                    rb_count_child_yes.setChecked(false);

                }
            }
        });
        et_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    final ArrayList<ListDrawerBean> beanArrayList;
                    if (str_from.equalsIgnoreCase("user")) {
                        beanArrayList = LoadLayout(json_height, "height");
                    } else {
                        beanArrayList = LoadLayout(json_par_height, "height");
                    }

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
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });
        et_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");

                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_par_age, "age");

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
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });

        et_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    final ArrayList<ListDrawerBean> beanArrayList;
                    if (str_from.equalsIgnoreCase("user")) {
                        beanArrayList = LoadLayout(json_weight, "weight");
                    } else {
                        beanArrayList = LoadLayout(json_par_weight, "weight");
                    }


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
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });

        et_phy_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");

                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_phy_stat, "physical_status");

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

                            listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "physical_status");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });
        et_body_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                            listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "body_Type");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });
        et_mother_ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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


                            listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "mothertongue");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });
        et_marital_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");

                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_marital_stat, "marital_status");

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

                            listDrawerAdapter = new ListDrawerAdapter(context, filteredList, (MyInterface) context, "marital_status");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });
        et_profile_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");

                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_profile_create, "profile");

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
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(EditProfileBasic.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_marital_stat = new JSONArray();
                    json_mother_ton = new JSONArray();
                    json_body_type = new JSONArray();
                    json_phy_stat = new JSONArray();
                    json_profile_create = new JSONArray();
                    json_par_age = new JSONArray();
                    json_par_height = new JSONArray();
                    json_par_weight = new JSONArray();
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub


        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1);


        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, myDateListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
//return new DatePickerDialog(ctx, myDateListener, year, month, day);

        return null;


//        return new DatePickerDialog(this, myDateListener, year, month, day);

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        et_get_dob.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    public void getEditBasicDetCall(String str_basic_json, String str_from) {


        try {
            Log.i("HH", "strResp : " + str_basic_json);


            String age;

            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

            JSONObject myBasicPrefJsonObj = new JSONObject(str_basic_json);

            //JSONObject myBasicPrefJsonObj = jsobj.getJSONObject("Basic info");

            if (str_from.equalsIgnoreCase("partner")) {
                et_name.setVisibility(View.GONE);
                et_get_dob.setVisibility(View.GONE);

                et_profile_create.setVisibility(View.GONE);
                ll_gender.setVisibility(View.GONE);
              /*  et_childrens.setVisibility(View.GONE);
                ll_partner_child.setVisibility(View.VISIBLE);*/
                age = myBasicPrefJsonObj.getString("Groom Age");
                for (int i = 0; i < json_par_age.length(); i++) {
                    if (age.equals(json_par_age.getJSONObject(i).getString("value"))) {
                        str_age_id = json_par_age.getJSONObject(i).getString("id");
                        break;
                    }
                }
                et_age.setText(age);

                String height = myBasicPrefJsonObj.getString("Height");
                ;
                for (int i = 0; i < json_par_height.length(); i++) {
                    if (height.equals(json_par_height.getJSONObject(i).getString("value"))) {
                        str_height_id = json_par_height.getJSONObject(i).getString("id");
                        break;
                    }
                }
                et_height.setText(height);
                String weight = myBasicPrefJsonObj.getString("Weight");
                for (int i = 0; i < json_par_weight.length(); i++) {
                    if (weight.equals(json_par_weight.getJSONObject(i).getString("value"))) {
                        str_weight_id = json_par_weight.getJSONObject(i).getString("id");
                        break;
                    }
                }
                et_weight.setText(weight);
            } else {
                et_age.setVisibility(View.GONE);
             /*   et_childrens.setVisibility(View.VISIBLE);
                ll_partner_child.setVisibility(View.GONE);*/
                String name = myBasicPrefJsonObj.getString("Name");
                String gender = myBasicPrefJsonObj.getString("Gender");
                String str_childr = myBasicPrefJsonObj.getString("No of Child");
                str_name = name;
                et_name.setText(str_name);
                str_uchild = str_childr;
                et_childrens.setText(str_uchild);
                Log.e("MK", str_uchild);
                /*age = myBasicPrefJsonObj.getString("Age");
                for (int i = 0; i < json_age.length(); i++) {
                    if (age.equals(json_age.getJSONObject(i).getString("value"))) {
                        str_age_id = json_age.getJSONObject(i).getString("id");
                        break;
                    }
                }
                et_age.setText(age);*/

                String dob = myBasicPrefJsonObj.getString("Dob");
                str_dob = dob;
                et_get_dob.setText(str_dob);
                if (gender.equalsIgnoreCase("male")) {
                    rb_male.setChecked(true);
                    rb_female.setChecked(false);
                } else {
                    rb_female.setChecked(true);
                    rb_male.setChecked(false);
                }

                String profile_created = myBasicPrefJsonObj.getString("Profile Created for");
                for (int i = 0; i < json_profile_create.length(); i++) {
                    if (profile_created.equals(json_profile_create.getJSONObject(i).getString("value"))) {
                        str_profile_id = json_profile_create.getJSONObject(i).getString("id");
                        break;
                    }
                }
                et_profile_create.setText(profile_created);
            }


            String height = myBasicPrefJsonObj.getString("Height");
            for (int i = 0; i < json_height.length(); i++) {
                if (height.equals(json_height.getJSONObject(i).getString("value"))) {
                    str_height_id = json_height.getJSONObject(i).getString("id");
                    break;
                }
            }
            et_height.setText(height);
            String weight = myBasicPrefJsonObj.getString("Weight");
            for (int i = 0; i < json_weight.length(); i++) {
                if (weight.equals(json_weight.getJSONObject(i).getString("value"))) {
                    str_weight_id = json_weight.getJSONObject(i).getString("id");
                    break;
                }
            }
            et_weight.setText(weight);
            String mother_tongue = myBasicPrefJsonObj.getString("Mother Tongue");
            for (int i = 0; i < json_mother_ton.length(); i++) {
                if (mother_tongue.equals(json_mother_ton.getJSONObject(i).getString("value"))) {
                    str_mother_ton_id = json_mother_ton.getJSONObject(i).getString("id");
                    break;
                }
            }

            String married_status = myBasicPrefJsonObj.getString("Married Status");
            for (int i = 0; i < json_marital_stat.length(); i++) {
                if (married_status.equals(json_marital_stat.getJSONObject(i).getString("value"))) {
                    str_marital_id = json_marital_stat.getJSONObject(i).getString("id");
                    if (str_marital_id.equalsIgnoreCase("2") || str_marital_id.equalsIgnoreCase("3") || str_marital_id.equalsIgnoreCase("4")) {
                        if (str_from.equalsIgnoreCase("partner")) {
                            ll_partner_child.setVisibility(View.VISIBLE);
                        } else {
                            et_childrens.setVisibility(View.VISIBLE);
                        }

                    } else {
                        et_childrens.setVisibility(View.GONE);
                        ll_partner_child.setVisibility(View.GONE);
                    }
                    break;
                }
            }
            String body_type = myBasicPrefJsonObj.getString("Body Type");
            for (int i = 0; i < json_body_type.length(); i++) {
                if (body_type.equals(json_body_type.getJSONObject(i).getString("value"))) {
                    str_bodytype_id = json_body_type.getJSONObject(i).getString("id");
                    break;
                }
            }
            String eating_habits = myBasicPrefJsonObj.getString("Eating Habits");

            String drinking_habits = myBasicPrefJsonObj.getString("Drinking Habits");
            String no_of_pchild = myBasicPrefJsonObj.getString("No of Child");


            String smoking_habits = myBasicPrefJsonObj.getString("Smoking Habits");

            String physical_status = myBasicPrefJsonObj.getString("Physical Status");
            for (int i = 0; i < json_phy_stat.length(); i++) {
                if (physical_status.equals(json_phy_stat.getJSONObject(i).getString("value"))) {
                    str_phy_stat_id = json_phy_stat.getJSONObject(i).getString("id");
                    break;
                }
            }


            et_marital_stat.setText(married_status);
            et_body_type.setText(body_type);
            et_mother_ton.setText(mother_tongue);
            et_phy_stat.setText(physical_status);


            if (eating_habits.equalsIgnoreCase("all")) {
                rb_eat_all.setChecked(true);
                rb_eat_veg.setChecked(false);
                rb_eat_nv.setChecked(false);
                rb_eat_egg.setChecked(false);
            } else if (eating_habits.equalsIgnoreCase("Non Vegetarian")) {
                rb_eat_all.setChecked(false);
                rb_eat_veg.setChecked(false);
                rb_eat_nv.setChecked(true);
                rb_eat_egg.setChecked(false);
            } else if (eating_habits.equalsIgnoreCase("Vegetarian")) {
                rb_eat_all.setChecked(false);
                rb_eat_veg.setChecked(true);
                rb_eat_nv.setChecked(false);
                rb_eat_egg.setChecked(false);
            } else if (eating_habits.equalsIgnoreCase("Eggetarian")) {
                rb_eat_all.setChecked(false);
                rb_eat_veg.setChecked(false);
                rb_eat_nv.setChecked(false);
                rb_eat_egg.setChecked(true);
            }

            if (smoking_habits.equalsIgnoreCase("yes")) {
                rb_smoke_yes.setChecked(true);
                rb_smoke_no.setChecked(false);
                rb_smoke_social.setChecked(false);

            } else if (smoking_habits.equalsIgnoreCase("no")) {
                rb_smoke_yes.setChecked(false);
                rb_smoke_no.setChecked(true);
                rb_smoke_social.setChecked(false);

            } else if (smoking_habits.equalsIgnoreCase("Occasionally")) {
                rb_smoke_yes.setChecked(false);
                rb_smoke_no.setChecked(false);
                rb_smoke_social.setChecked(true);

            }


            if (drinking_habits.equalsIgnoreCase("yes")) {
                rb_drink_yes.setChecked(true);
                rb_drink_no.setChecked(false);
                rb_drink_social.setChecked(false);

            } else if (drinking_habits.equalsIgnoreCase("no")) {
                rb_drink_yes.setChecked(false);
                rb_drink_no.setChecked(true);
                rb_drink_social.setChecked(false);

            } else if (drinking_habits.equalsIgnoreCase("Occasionally")) {
                rb_drink_yes.setChecked(false);
                rb_drink_no.setChecked(false);
                rb_drink_social.setChecked(true);


            }

            if (no_of_pchild.equalsIgnoreCase("yes")) {
                rb_count_child_yes.setChecked(true);
                rb_count_child_no.setChecked(false);


            } else if (no_of_pchild.equalsIgnoreCase("no")) {
                rb_count_child_yes.setChecked(false);
                rb_count_child_no.setChecked(true);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("profile")) {
            et_profile_create.setText(str_items);
            str_profile_id = str_items_id;


        } else if (strIdentify.equalsIgnoreCase("height")) {
            et_height.setText(str_items);
            str_height_id = str_items_id;


        } else if (strIdentify.equalsIgnoreCase("age")) {
            et_age.setText(str_items);

            str_age_id = str_items_id;

        } else if (strIdentify.equalsIgnoreCase("weight")) {
            et_weight.setText(str_items);
            str_weight_id = str_items_id;

        } else if (strIdentify.equalsIgnoreCase("physical_status")) {
            et_phy_stat.setText(str_items);
            str_phy_stat_id = str_items_id;

        } else if (strIdentify.equalsIgnoreCase("body_Type")) {
            et_body_type.setText(str_items);
            str_bodytype_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("mothertongue")) {
            et_mother_ton.setText(str_items);
            str_mother_ton_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("marital_status")) {
            et_marital_stat.setText(str_items);
            str_marital_id = str_items_id;
            if (str_marital_id.equalsIgnoreCase("2") || str_marital_id.equalsIgnoreCase("3") || str_marital_id.equalsIgnoreCase("4")) {
                if (str_from.equalsIgnoreCase("partner")) {
                    ll_partner_child.setVisibility(View.VISIBLE);
                } else {
                    et_childrens.setVisibility(View.VISIBLE);
                }

            } else {
                et_childrens.setVisibility(View.GONE);
                ll_partner_child.setVisibility(View.GONE);
            }
        } else if (strIdentify.equalsIgnoreCase("profile")) {
            et_profile_create.setText(str_items);
            str_profile_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id, String str_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal, String strRemovedVal, String strIdentify) {
        return null;
    }

    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        try {
            if (providerServicesMonth.length() > 0) {
                if(str_from.equalsIgnoreCase("user")){
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

    public void updateUserBasicDet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_user1.php",
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
                                String str_span = "Your basic details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 18, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfileBasic.this, EditProfile.class));
                                        finish();
                                        altDialog.dismiss();
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

                if (str_name.length() > 0) {
                    params.put("name", str_name);
                }
                if (str_gender.length() > 0) {
                    params.put("gender", str_gender);
                }
                if (str_dob.length() > 0) {
                    params.put("dob", str_dob);
                }
                if (str_profile_id.length() > 0) {
                    params.put("profilefor", str_profile_id);
                }
                if (str_mother_ton_id.length() > 0) {
                    params.put("mothertongue", str_mother_ton_id);
                }
                if (str_marital_id.length() > 0) {
                    params.put("maritalstatus", str_marital_id);
                }
                if (str_eat.length() > 0) {
                    params.put("eatinghabits", str_eat);
                }
                if (str_drink.length() > 0) {
                    params.put("drinkinghabits", str_drink);
                }
                if (str_smoke.length() > 0) {
                    params.put("smokinghabits", str_smoke);
                }
                if (str_phy_stat_id.length() > 0) {
                    params.put("physicalstatus", str_phy_stat_id);
                }
                if (str_height_id.length() > 0) {
                    params.put("height", str_height_id);
                }
                if (str_weight_id.length() > 0) {
                    params.put("weight", str_weight_id);
                }
                if (str_bodytype_id.length() > 0) {
                    params.put("bodytype", str_bodytype_id);
                }
                if (str_uchild.length() > 0) {
                    params.put("noofchild", str_uchild);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileBasic.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

    public void updatePartnerBasicDet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_partner1.php",
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
                                String str_span = "Your partner basic details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 25, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfileBasic.this, EditProfile.class));
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

                Log.e("NN:p_age", str_age_id);
                Log.e("NN:p_moth", str_mother_ton_id);
                Log.e("NN:p_mari", str_marital_id);
                Log.e("NN:p_eat", str_eat);
                Log.e("NN:p_drink", str_drink);
                Log.e("NN:p_smoke", str_smoke);
                Log.e("NN:p_phy", str_phy_stat_id);
                Log.e("NN:p_heigh", str_height_id);
                Log.e("NN:p_weigh", str_weight_id);
                Log.e("NN:p_bty", str_bodytype_id);
                Log.e("NN:p_uid", str_user_id);
                Log.e("NN:p_child", str_child);


                if (str_age_id.length() > 0) {
                    params.put("partnerage", str_age_id);
                }
                if (str_mother_ton_id.length() > 0) {
                    params.put("partnermothertongue", str_mother_ton_id);
                }
                if (str_marital_id.length() > 0) {
                    params.put("partnermaritalstatus", str_marital_id);
                }
                if (str_eat.length() > 0) {
                    params.put("partnereatinghabits", str_eat);
                }
                if (str_drink.length() > 0) {
                    params.put("partnerdrinkinghabits", str_drink);
                }
                if (str_smoke.length() > 0) {
                    params.put("partnersmokinghabits", str_smoke);
                }
                if (str_phy_stat_id.length() > 0) {
                    params.put("partnerphysicalstatus", str_phy_stat_id);
                }
                if (str_height_id.length() > 0) {
                    params.put("partnerheight", str_height_id);
                }
                if (str_weight_id.length() > 0) {
                    params.put("partnerweight", str_weight_id);
                }
                if (str_bodytype_id.length() > 0) {
                    params.put("partnerbodytype", str_bodytype_id);
                }
                if (str_child.length() > 0) {
                    params.put("partnernoofchild", str_child);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfileBasic.this);
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


                                        JSONArray json_array_height = jsobj.getJSONArray("Height");
                                        json_height = jsobj.getJSONArray("Height");
                                        // json_age = jsobj.getJSONArray("Age");
                                        json_weight = jsobj.getJSONArray("weight");
                                        json_marital_stat = jsobj.getJSONArray("martialstatus");
                                        json_mother_ton = jsobj.getJSONArray("mothertongue");
                                        json_phy_stat = jsobj.getJSONArray("Physical status");
                                        json_body_type = jsobj.getJSONArray("Body Type");
                                        json_profile_create = jsobj.getJSONArray("profile");
                                        json_par_age = jsobj.getJSONArray("Partner Age");
                                        json_par_height = jsobj.getJSONArray("Partner height");
                                        json_par_weight = jsobj.getJSONArray("Partner Weight");

                                        getEditBasicDetCall(str_basic_json, str_from);


                             /*   str_profile_for_id = profile_created_by.getJSONObject(0).getString("id");
                                str_religion_id = jsonArray_religion.getJSONObject(0).getString("id");
                                str_mother_tonngue_id = jsonArray_mother_tongue.getJSONObject(0).getString("id");*/


                                        if (json_height.length() > 0) {
                                            for (int i = 0; i < json_array_height.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_height.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(context, beanArrayList, (MyInterface) context, "Height");
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


                RequestQueue requestQueue = Volley.newRequestQueue(EditProfileBasic.this);
                requestQueue.add(stringRequest);


                Log.e("NN:basic_edit-name", et_name.getText().toString().trim());
                Log.e("NN:basic_edit-gender", str_bodytype_id);
                Log.e("NN:basic_edit-age", str_age_id);
                Log.e("NN:basic_edit-pro", str_profile_id);
                Log.e("NN:basic_edit-motherton", str_mother_ton_id);
                Log.e("NN:basic_edit-marit", str_marital_id);
                Log.e("NN:basic_edit-eat", str_eat);
                Log.e("NN:basic_edit-drink", str_drink);
                Log.e("NN:basic_edit-smok", str_smoke);
                Log.e("NN:basic_edit-phy", str_phy_stat_id);
                Log.e("NN:basic_edit-height", str_height_id);
                Log.e("NN:basic_edit-weight", str_weight_id);
                Log.e("NN:basic_edit-body", str_bodytype_id);
                Log.e("NN:basic_edit-userid", str_user_id);
                Log.e("NN:basic_edit-pchild", str_child);
                Log.e("NN:basic_edit-uchild", et_childrens.getText().toString().trim());

             /*   }
                nAttempt = 1;*/

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
