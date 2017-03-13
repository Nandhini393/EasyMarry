package com.example.easy_marry;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
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
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Horoscope.AddHoroscope;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MailInboxTabs.MailboxNewTabs;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.imageCache.ImageLoader;

import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 18/6/16.
 */
public class QuickSearch extends Activity implements View.OnClickListener, MyInterface {
    ImageView img_back;

    Button btn_rate;
    DrawerLayout myTabsDrawer;
    LinearLayout ll_parent_menu, ll_my_profile;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    private ViewPager viewPager;
    TextView txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_messenger, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
    //ImageView img_edit_my_profile;
    Context ctx;
    EditText et_search_religion, et_search_age, et_search_height, et_search_weight, et_search_caste, et_search_subcaste, et_search_country, et_search_loc, et_search_qualif,
            et_search_occup, et_suggest;
    Button btn_search;
    RadioButton rb_eat_all, rb_eat_veg, rb_eat_nv, rb_eat_egg, rb_drink_yes, rb_drink_no, rb_drink_social, rb_smoke_yes, rb_smoke_no, rb_smoke_social;
    GeneralData gD;
    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level;
    TextView txt_level_percent, txt_name, txt_id, txt_terms, txt_reset, txt_drawer_error_msg;
    ListDrawerAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    EditText et_search_profile_for;
    JSONArray json_religion, json_age, json_height, json_weight, json_caste, json_country, json_qualif, json_occup, json_eating, json_smoking, json_drinking;
    String str_religion_id = "", str_age_id = "", str_height_id = "", str_weight_id = "", str_caste_id = "", str_subcaste_id = "", str_country_id = "", str_loc_id = "",
            str_qualif_id = "", str_occup_id = "";
    ArrayList<ListDrawerBean> return_beanArrayList;
    String str_eat = "4", str_drink = "2", str_smoke = "2";
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id, str_rate;
    IntentFilter filter1;
    TextView txt_copyright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_search);
        ctx = this;
        gD = new GeneralData(ctx);
        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        str_rate = gD.prefs.getString("Rating", null);
        Log.e("NN--search", str_image);

        txt_drawer_error_msg = (TextView) findViewById(R.id.txt_draw_error);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        // rate btn
        btn_rate = (Button) findViewById(R.id.btn_rate);
      //  img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);


        txt_terms = (TextView) findViewById(R.id.txt_terms);
        txt_reset = (TextView) findViewById(R.id.txt_reset);
        //toolbar contents
        img_user_image = (Rounded_Imageview) findViewById(R.id.user_image);
        img_menu = (ImageView) findViewById(R.id.menu);
        //myTabsDrawer = (DrawerLayout) findViewById(R.id.my_tabs_drawer_layout);
        myTabsSliderLayout = (LinearLayout) findViewById(R.id.llayslider);

        //your matches
        img_mymatches_grey = (ImageView) findViewById(R.id.img_my_matches_grey);
        txt_mymatches = (TextView) findViewById(R.id.txt_mymatches);
        matchesLayout = (LinearLayout) findViewById(R.id.my_matches);
        ll_my_profile = (LinearLayout) findViewById(R.id.ll_my_profile);

        //mailbox
        img_mailbox_grey = (ImageView) findViewById(R.id.img_mail_box_grey);
        txt_mailbox = (TextView) findViewById(R.id.txt_mailbox);
        mailboxLayout = (LinearLayout) findViewById(R.id.mail_box);

        //dailyrecomm
        img_dailyrecom_grey = (ImageView) findViewById(R.id.img_daily_recom_grey);
        txt_dailyrecom = (TextView) findViewById(R.id.txt_dailyrecom);
        dailyrecomLayout = (LinearLayout) findViewById(R.id.daily_recom);

        //edit profile
        img_editprof_grey = (ImageView) findViewById(R.id.img_edit_profile_grey);
        txt_edit_profile = (TextView) findViewById(R.id.txt_editprofile);
        editProLayout = (LinearLayout) findViewById(R.id.edit_profile);

        //upgrade account
        img_upgradeacc_grey = (ImageView) findViewById(R.id.img_upgrade_acc_grey);
        txt_upgrade_acc = (TextView) findViewById(R.id.txt_upgrade_acc);
        upgradeLayout = (LinearLayout) findViewById(R.id.upgrade_acc);

        //settings
        img_settings_grey = (ImageView) findViewById(R.id.img_settings_grey);
        txt_settings = (TextView) findViewById(R.id.txt_settings);
        settingsLayout = (LinearLayout) findViewById(R.id.settings);

        //feedback
        img_feedback_grey = (ImageView) findViewById(R.id.img_feedback_grey);
        txt_feedback = (TextView) findViewById(R.id.txt_feedback);
        feedbackLayout = (LinearLayout) findViewById(R.id.feedback);

        //search
        img_search_grey = (ImageView) findViewById(R.id.img_search_grey);
        txt_search = (TextView) findViewById(R.id.txt_search);
        searchLayout = (LinearLayout) findViewById(R.id.search);


        //add horo
        img_horo_grey = (ImageView) findViewById(R.id.img_add_horo_grey);
        txt_add_horo = (TextView) findViewById(R.id.txt_add_horo);
        addhoroLAyout = (LinearLayout) findViewById(R.id.add_horo);

        btn_search = (Button) findViewById(R.id.btn_search);

        et_search_religion = (EditText) findViewById(R.id.et_religion);
        et_search_age = (EditText) findViewById(R.id.et_age);
        et_search_height = (EditText) findViewById(R.id.et_height);
        et_search_weight = (EditText) findViewById(R.id.et_weight);
        et_search_caste = (EditText) findViewById(R.id.et_caste);
        et_search_subcaste = (EditText) findViewById(R.id.et_sub_caste);
        et_search_country = (EditText) findViewById(R.id.et_country);
        et_search_loc = (EditText) findViewById(R.id.et_location);
        et_search_qualif = (EditText) findViewById(R.id.et_qualification);
        et_search_occup = (EditText) findViewById(R.id.et_occupation);

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


        listView = (ListView) findViewById(R.id.drawer_listview);

        Calendar calendar= Calendar.getInstance();
        txt_copyright=(TextView)findViewById(R.id.txt_copyright);
        txt_copyright.setText("copyright@"+calendar.get(Calendar.YEAR));

        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myTabsSliderLayout);
            }
        });
      /*  img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuickSearch.this, EditProfile.class));
            }
        });*/
        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuickSearch.this, TermsAndConditions.class));
            }
        });
        matchesLayout.setOnClickListener(this);
        mailboxLayout.setOnClickListener(this);
        dailyrecomLayout.setOnClickListener(this);
        editProLayout.setOnClickListener(this);
        upgradeLayout.setOnClickListener(this);
        settingsLayout.setOnClickListener(this);
        feedbackLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        addhoroLAyout.setOnClickListener(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        et_search_religion.setFocusable(false);
        et_search_age.setFocusable(false);
        et_search_height.setFocusable(false);
        et_search_weight.setFocusable(false);
        et_search_caste.setFocusable(false);
        et_search_subcaste.setFocusable(false);
        et_search_occup.setFocusable(false);
        et_search_country.setFocusable(false);
        et_search_loc.setFocusable(false);
        et_search_qualif.setFocusable(false);
        str_eat = String.valueOf(rb_eat_all.getTag());
        str_drink = String.valueOf(rb_drink_no.getTag());
        str_smoke = String.valueOf(rb_smoke_no.getTag());
        rb_eat_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_eat_all.isChecked()) {
                    str_eat = String.valueOf(rb_eat_all.getTag());
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
                }
                else{
                    rb_eat_all.setChecked(true);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_eat_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_eat_veg.isChecked()) {
                    str_eat = String.valueOf(rb_eat_veg.getTag());
                    rb_eat_all.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
                }
                else{
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(true);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_eat_nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_eat_nv.isChecked()) {
                    str_eat = String.valueOf(rb_eat_nv.getTag());
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_egg.setChecked(false);
                }
                }
                else{
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(true);
                    rb_eat_egg.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_eat_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_eat_egg.isChecked()) {
                    str_eat = String.valueOf(rb_eat_egg.getTag());
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);

                }
                }
                else{
                    rb_eat_all.setChecked(false);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(true);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_drink_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_drink_yes.isChecked()) {
                    str_drink = String.valueOf(rb_drink_yes.getTag());
                    rb_drink_no.setChecked(false);
                    rb_drink_social.setChecked(false);
                }
                }
                else{
                    rb_drink_yes.setChecked(true);
                    rb_drink_no.setChecked(false);
                    rb_drink_social.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_drink_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_drink_no.isChecked()) {
                    str_drink = String.valueOf(rb_drink_no.getTag());
                    rb_drink_yes.setChecked(false);
                    rb_drink_social.setChecked(false);
                }
                }
                else{
                    rb_drink_yes.setChecked(false);
                    rb_drink_no.setChecked(true);
                    rb_drink_social.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_drink_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_drink_social.isChecked()) {
                    str_drink = String.valueOf(rb_drink_social.getTag());
                    rb_drink_no.setChecked(false);
                    rb_drink_yes.setChecked(false);
                }
                }
                else{
                    rb_drink_yes.setChecked(false);
                    rb_drink_no.setChecked(false);
                    rb_drink_social.setChecked(true);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });

        rb_smoke_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_smoke_yes.isChecked()) {
                    str_smoke = String.valueOf(rb_smoke_yes.getTag());
                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(false);
                }
                }
                else{
                    rb_smoke_yes.setChecked(true);
                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_smoke_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_smoke_no.isChecked()) {
                    str_smoke = String.valueOf(rb_smoke_no.getTag());
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_social.setChecked(false);
                }
                }
                else{
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(true);
                    rb_smoke_social.setChecked(false);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_smoke_social.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_smoke_social.isChecked()) {
                    str_smoke = String.valueOf(rb_smoke_social.getTag());
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(false);
                }
                }
                else{
                    rb_smoke_yes.setChecked(false);
                    rb_smoke_no.setChecked(false);
                    rb_smoke_social.setChecked(true);
                    Toast.makeText(QuickSearch.this, "Unable to connect to internet, your changes can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });


        et_search_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                            listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "religion");
                            listView.setVisibility(View.VISIBLE);
                            listView.setAdapter(listDrawerAdapter);
                            listDrawerAdapter.notifyDataSetChanged(); // data set changed
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_age, "age");

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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "age");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "height");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "weight");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_caste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_caste, "caste");

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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "caste");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_subcaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (et_search_subcaste.getText().toString().trim().equalsIgnoreCase("Not Specified")) {
                        et_search_subcaste.setEnabled(false);
                        et_search_subcaste.setTextColor(Color.parseColor("#000000"));
                    }
                    else
                    {
                    myDrawerLayout.openDrawer(myLinearLayout);
                    et_search_profile_for.setText("");
                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");


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

                            listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "subcaste");
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
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_country, "country");

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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "country");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                et_search_profile_for.setText("");

                Log.i("TS", "Country_Id : " + str_country_id);

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "state", "countryid");

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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "state");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_qualif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(json_qualif, "qualif");

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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "qualif");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        et_search_occup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                        listDrawerAdapter = new ListDrawerAdapter(ctx, filteredList, (MyInterface) ctx, "occupation");
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                } else {
                    Toast.makeText(QuickSearch.this, "No response from server", Toast.LENGTH_LONG).show();
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                    json_religion = new JSONArray();
                    json_age = new JSONArray();
                    json_height = new JSONArray();
                    json_weight = new JSONArray();
                    json_caste = new JSONArray();
                    json_country = new JSONArray();
                    json_qualif = new JSONArray();
                    json_occup = new JSONArray();
                    json_eating = new JSONArray();
                    json_smoking = new JSONArray();
                    json_drinking = new JSONArray();

                }
            }
        });
        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    et_search_religion.setText("");
                    str_religion_id = "";
                    et_search_age.setText("");
                    str_age_id = "";
                    et_search_height.setText("");
                    str_height_id = "";
                    et_search_weight.setText("");
                    str_weight_id = "";
                    et_search_caste.setText("");
                    str_caste_id = "";
                    et_search_subcaste.setText("");
                    str_subcaste_id = "";
                    et_search_country.setText("");
                    str_country_id = "";
                    et_search_loc.setText("");
                    str_loc_id = "";
                    et_search_qualif.setText("");
                    str_qualif_id = "";
                    et_search_occup.setText("");
                    str_occup_id = "";

                    str_eat = "4";
                    rb_eat_all.setChecked(true);
                    rb_eat_veg.setChecked(false);
                    rb_eat_nv.setChecked(false);
                    rb_eat_egg.setChecked(false);
                    str_drink = "2";
                    rb_drink_no.setChecked(true);
                    rb_drink_yes.setChecked(false);
                    str_smoke = "2";
                    rb_smoke_no.setChecked(true);
                    rb_smoke_yes.setChecked(false);

                } else {
                    Toast.makeText(QuickSearch.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    searchPartner();
                } else {
                    listView.setVisibility(View.GONE);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setCancelable(false);

                    View itemView1 = LayoutInflater.from(ctx)
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
              /*  if (et_search_religion.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Religion", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_age.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Age", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_height.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Height", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_weight.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Weight", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_caste.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Caste", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_subcaste.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Subcaste", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_country.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Country", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_loc.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Location", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_qualif.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Qualification", Toast.LENGTH_SHORT).show();
                }
                else if (et_search_occup.getText().toString().length() == 0) {
                    Toast.makeText(QuickSearch.this, "select Qccupation", Toast.LENGTH_SHORT).show();
                }
                else{
                    searchPartner();
                }*/
            }
        });
        imgLoader = new ImageLoader(ctx);

        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);


        if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

            imgLoader.DisplayImage(str_image, img_user_image);

           /* Picasso.with(ctx)
                    .load(str_image)
                    .placeholder(R.drawable.default_use)
                    .into(img_user_image);
*/

            txt_name.setText(str_name);
            txt_id.setText(str_easy_marry_id);
            Log.i("ND", "PLevel : " + str_prof_compl_level);
            txt_level_percent.setText(str_prof_compl_level + "%");
            pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));
        }
/*//internet checking
        if (gD.isConnectingToInternet()) {
            Log.i("LK", "connected");

            if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

               imgLoader.DisplayImage(str_image, img_user_image);

            *//*    Picasso.with(ctx)
                        .load(str_image)
                        .placeholder(R.drawable.default_use)
                        .into(img_user_image);*//*


                txt_name.setText(str_name);
                txt_id.setText(str_easy_marry_id);
                Log.i("ND", "PLevel : " + str_prof_compl_level);
                txt_level_percent.setText(str_prof_compl_level + "%");
                pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));
            }

        } else {
            Log.i("LK", " not connected");
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
            builder.setCancelable(true);

            View itemView1 = LayoutInflater.from(ctx)
                    .inflate(R.layout.user_current_loc_addr_alert, null);
            final android.support.v7.app.AlertDialog altDialog = builder.create();
            altDialog.setView(itemView1);
            altDialog.show();
            TextView txt_msg = (TextView) itemView1.findViewById(R.id.text_address);
            Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    altDialog.dismiss();
                    finish();
                }
            });
            txt_msg.setText("Please check your Internet Connection!!");
        }*/
        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuickSearch.this, DailyRecomViewProfie.class);
                i.putExtra("str_from", "header");
                startActivity(i);
            }
        });
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_rate.equalsIgnoreCase("0")) {
                    View itemView1;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(ctx)
                            .inflate(R.layout.custom_popup_rateus, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final RatingBar ratingBar = (RatingBar) itemView1.findViewById(R.id.rate_app);

                    Button btn_later = (Button) itemView1.findViewById(R.id.btn_later);
                    Button btn_rate_now = (Button) itemView1.findViewById(R.id.btn_rate_now);
                    LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                    stars.getDrawable(0).setColorFilter(Color.parseColor("#40639639"), PorterDuff.Mode.SRC_ATOP);
                    stars.getDrawable(1).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                    TextView txt_span = (TextView) itemView1.findViewById(R.id.rate_span);

                    String str_span_text = "How was your experience with our app?";

                    SpannableString spannableString1 = new SpannableString(str_span_text);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 12, 23, 0);
                    txt_span.setText(spannableString1);
                    btn_later.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            altDialog.dismiss();
                        }
                    });
                    btn_rate_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ratingBar.getRating()==0){
                                Toast.makeText(QuickSearch.this, "Please enter the rating star", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                rateNowCall(str_user_id, String.valueOf(ratingBar.getRating()));
                                altDialog.dismiss();
                            }
                        }
                    });
                    altDialog.show();
                } else {
                    Toast.makeText(QuickSearch.this, "You have already rated...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //set the color for already selected menu
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

        if (vv instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) vv;
            ImageView imV = (ImageView) ll.getChildAt(0);
            imV.setBackgroundResource(R.drawable.search_orange);
            TextView tvName = (TextView) ll.getChildAt(1);
            tvName.setTextColor(Color.parseColor("#fb7b09"));
        }


    }


    @Override
    public void onClick(View v) {
        for (int i = 0; i < ll_parent_menu.getChildCount(); i++) {
            View vv = ll_parent_menu.getChildAt(i);
            if (vv.getId() == v.getId()) {
                Log.i("DDDD", "index : " + i);
                gD.nChildPostion = i;
                break;
            }
        }

        switch (v.getId()) {
            case R.id.my_matches:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_orange);
                txt_mymatches.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(QuickSearch.this, Matches.class));
                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;


            case R.id.mail_box:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_orange);
                txt_mailbox.setTextColor(Color.parseColor("#fb7b09"));

                //mailboxLayout
                //      ll_parent_menu.getChildCount()


                startActivity(new Intent(QuickSearch.this, MailboxNewTabs.class));
                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;


            case R.id.daily_recom:


                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_orange);
                txt_dailyrecom.setTextColor(Color.parseColor("#fb7b09"));
                Intent i = new Intent(QuickSearch.this, DailyRecommList.class);
                // i.putExtra("str_from","daily_recomm");
                startActivity(i);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;

            case R.id.edit_profile:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_orange);
                txt_edit_profile.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(QuickSearch.this, EditProfile.class));

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;


            case R.id.upgrade_acc:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_account_orange);
                txt_upgrade_acc.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(QuickSearch.this, MembershipTabs.class));
                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;


            case R.id.settings:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_orange);
                txt_settings.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(QuickSearch.this, Settings.class));

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;

            case R.id.feedback:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_orange);
                txt_feedback.setTextColor(Color.parseColor("#fb7b09"));
                startActivity(new Intent(QuickSearch.this, Feedback.class));

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;

            case R.id.search:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_orange);
                txt_search.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(QuickSearch.this, QuickSearch.class));

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.GRAY);

                break;

            case R.id.add_horo:


                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.GRAY);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.GRAY);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.GRAY);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.GRAY);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.GRAY);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.GRAY);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.GRAY);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_orange);
                txt_add_horo.setTextColor(Color.parseColor("#fb7b09"));
                startActivity(new Intent(QuickSearch.this, AddHoroscope.class));

                break;
        }

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {
        if (strIdentify.equalsIgnoreCase("religion")) {
            et_search_religion.setText(str_items);
            str_religion_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("age")) {
            et_search_age.setText(str_items);
            str_age_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("height")) {
            et_search_height.setText(str_items);
            str_height_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("weight")) {
            et_search_weight.setText(str_items);
            str_weight_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("caste")) {
            et_search_caste.setText(str_items);
            str_caste_id = str_items_id;
            et_search_subcaste.setEnabled(true);
            et_search_subcaste.setText("");
            restCallForStateCity(GeneralData.LOCAL_IP + "subcaste.php", str_caste_id, "subcaste", "casteid");
        } else if (strIdentify.equalsIgnoreCase("subcaste")) {
            et_search_subcaste.setText(str_items);
            str_subcaste_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("country")) {
            et_search_country.setText(str_items);
            str_country_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("state")) {
            et_search_loc.setText(str_items);
            str_loc_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("qualif")) {
            et_search_qualif.setText(str_items);
            str_qualif_id = str_items_id;
        } else if (strIdentify.equalsIgnoreCase("occupation")) {
            et_search_occup.setText(str_items);
            str_occup_id = str_items_id;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id, String str_partner_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {


        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
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

                listDrawerAdapter = new ListDrawerAdapter(ctx, beanArrayList, (MyInterface) ctx, stridentifyEdit);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(listDrawerAdapter);
                listDrawerAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return beanArrayList;

    }

    public ArrayList<ListDrawerBean> restCallForStateCity(String str_url, final String str_id, final String str_type, final String stridType) {

        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Toast.makeText(QuickSearch.this, response, Toast.LENGTH_LONG).show();
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
                                else{
                                    if(str_type.equalsIgnoreCase("subcaste")){

                                        et_search_subcaste.setText("Not Specified");
                                    }
                                }
                                listDrawerAdapter = new ListDrawerAdapter(ctx, return_beanArrayList, (MyInterface) ctx, str_type);
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
                        Toast.makeText(QuickSearch.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(QuickSearch.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(QuickSearch.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }

    public void rateNowCall(final String str_my_id, final String str_rating) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "rating.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);


                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(QuickSearch.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
                                str_rate=str_rating;
                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("Rating", str_rating);
                                prefEdit.commit();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(ctx, "res : " + res, Toast.LENGTH_LONG).show();

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
                Log.e("NN:userid", str_my_id);
                Log.e("NN:rating", str_rating);
                params.put("userid", str_my_id);
                params.put("rating", str_rating);


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


        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);


    }

    public void searchPartner() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "search.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);


                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(QuickSearch.this, "Search success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(QuickSearch.this, SearchResults.class);
                                i.putExtra("qsearch_json", response);
                                startActivity(i);

                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(QuickSearch.this, "No data available!!!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(ctx, "res : " + res, Toast.LENGTH_LONG).show();

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
                Log.e("NN:s_rel", str_religion_id);
                Log.e("NN:s_userid", str_user_id);
                if (str_religion_id.length() > 0) {
                    params.put("religion", str_religion_id);
                }
                if (str_age_id.length() > 0) {
                    params.put("age", str_age_id);
                }
                if (str_height_id.length() > 0) {
                    params.put("height", str_height_id);
                }
                if (str_weight_id.length() > 0) {
                    params.put("weight", str_weight_id);
                }
                if (str_caste_id.length() > 0) {
                    params.put("caste", str_caste_id);
                }
                if (str_subcaste_id.length() > 0) {
                    params.put("subcaste", str_subcaste_id);
                }
                if (str_country_id.length() > 0) {
                    params.put("country", str_country_id);
                }
                if (str_loc_id.length() > 0) {
                    params.put("location", str_loc_id);
                }
                if (str_qualif_id.length() > 0) {
                    params.put("qualification", str_qualif_id);
                }
                if (str_occup_id.length() > 0) {
                    params.put("occupation", str_occup_id);
                }
                if (str_eat.length() > 0) {
                    params.put("eating_habit", str_eat);
                }
                if (str_drink.length() > 0) {
                    params.put("drinking_habit", str_drink);
                }
                if (str_smoke.length() > 0) {
                    params.put("smoking_habit", str_smoke);
                }
                if (str_user_id.length() > 0) {
                    params.put("userid", str_user_id);
                }


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


        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);


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

                                        //caste,subcaste and loc json array needed

                                        JSONArray json_arr_religion = jsobj.getJSONArray("religion");
                                        json_religion = jsobj.getJSONArray("religion");
                                        json_age = jsobj.getJSONArray("Partner Age");
                                        json_height = jsobj.getJSONArray("Height");
                                        json_weight = jsobj.getJSONArray("weight");
                                        json_caste = jsobj.getJSONArray("Caste");
                                        json_qualif = jsobj.getJSONArray("qualification");
                                        json_occup = jsobj.getJSONArray("occupation");
                                        json_country = jsobj.getJSONArray("country");
                                        // json_loc = jsobj.getJSONArray("state");
                                        json_eating = jsobj.getJSONArray("Food habit");

                                        rb_eat_veg.setTag(json_eating.getJSONObject(0).getString("id"));
                                        rb_eat_nv.setTag(json_eating.getJSONObject(1).getString("id"));
                                        rb_eat_egg.setTag(json_eating.getJSONObject(2).getString("id"));
                                        rb_eat_all.setTag(json_eating.getJSONObject(3).getString("id"));

                                        json_drinking = jsobj.getJSONArray("Drinking habit");

                                        rb_drink_yes.setTag(json_drinking.getJSONObject(0).getString("id"));
                                        rb_drink_no.setTag(json_drinking.getJSONObject(1).getString("id"));
                                        rb_drink_social.setTag(json_drinking.getJSONObject(2).getString("id"));

                                        json_smoking = jsobj.getJSONArray("Smoking habit");

                                        rb_smoke_yes.setTag(json_smoking.getJSONObject(0).getString("id"));
                                        rb_smoke_no.setTag(json_smoking.getJSONObject(1).getString("id"));
                                        rb_smoke_social.setTag(json_smoking.getJSONObject(2).getString("id"));



                             /*   str_profile_for_id = profile_created_by.getJSONObject(0).getString("id");
                                str_religion_id = jsonArray_religion.getJSONObject(0).getString("id");
                                str_mother_tonngue_id = jsonArray_mother_tongue.getJSONObject(0).getString("id");*/


                                        if (json_religion.length() > 0) {
                                            for (int i = 0; i < json_arr_religion.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = json_religion.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(ctx, beanArrayList, (MyInterface) ctx, "religion");
                                        listView.setVisibility(View.VISIBLE);
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
                                Toast.makeText(QuickSearch.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        Toast.makeText(QuickSearch.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(QuickSearch.this);
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
