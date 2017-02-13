package com.example.easy_marry.EditProfiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.easy_marry.Horoscope.AddHoroscope;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.DailyRecommList;
import com.example.easy_marry.Feedback;
import com.example.easy_marry.Horoscope.EditHoroscope;
import com.example.easy_marry.MailInboxTabs.MailboxNewTabs;
import com.example.easy_marry.TermsAndConditions;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.QuickSearch;
import com.example.easy_marry.R;
import com.example.easy_marry.Rounded_Imageview;
import com.example.easy_marry.Settings;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.imageCache.RealPathUtil;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by android2 on 17/6/16.
 */
public class EditProfile extends Activity implements View.OnClickListener {
    TextView txt_basic_profile, txt_edit_religion, txt_edit_prof_info, txt_edit_loc, txt_edit_family_det, txt_edit_abt_you, txt_edit_hobbies;
    Button btn_upload, btn_view_phone_num,btn_view_horo;
    String strrealpath;
    Rounded_Imageview rnd_image;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;
    Context context;
    ImageView img_edit_abt_you, img_edit_hobbies;
    Button btn_rate;
    protected ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout myTabsDrawer;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    TextView txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
    LinearLayout ll_parent_menu, ll_my_profile,ll_uchildr,ll_gothram,ll_pchildr;
    GeneralData gD;
    int n_count = 0;
   // ImageView img_edit_my_profile, img_edit;

    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level, pg_menu_profile_comp_level;
    TextView txt_level_percent, txt_name, txt_id, txt_menu_level_percent,txt_terms;
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id,
            str_mob_num,str_cont_num,str_par_mob_num,str_conv_time,str_rate;

    EditText ed_abt_you, ed_hobbies;
    TextView txt_dailyrecomm_name, txt_dailyrecomm_gender, txt_dailyrecomm_age, txt_daily_recomm_height, txt_dailyrecomm_weight,
            txt_dailyrecomm_marital_stat, txt_dailyrecomm_mother_ton, txt_dailyrecomm_phy_stat, txt_dailyrecomm_body_type,
            txt_dailyrecomm_prof_by, txt_dailyrecomm_drink, txt_dailyrecommm_smoke, txt_dailyrecomm_eat,
            txt_per_religion, txt_per_gothram, txt_caste, txt_sub_caste, txt_zodiac, txt_star, txt_dhosham,
            txt_qualif, txt_qualif_det, txt_occupation, txt_empl_in, txt_annual_income,
            txt_country, txt_state, txt_city, txt_citizen,
            txt_fam_val, txt_fam_type, txt_fam_status, txt_father_occup, txt_mother_occup,
            txt_click_partner_basic,txt_click_partner_religious,
            txt_click_partner_professional,txt_click_partner_location,txt_click_partner_family_det,txt_dailyrecomm_child;

    TextView txt_groom_age, txt_groom_height, txt_groom_weight, txt_groom_marital_status, txt_groom_mother_tongue,
            txt_groom_phy_stat, txt_groom_body_type, txt_groom_child, txt_groom_eating, txt_groom_drinking, txt_groom_smoking,
            txt_groom_religion, txt_groom_gothram, txt_groom_caste, txt_groom_sub_caste, txt_groom_zodiac, txt_groom_star, txt_groom_dhosham,
            txt_groom_qualif, txt_groom_qualif_det, txt_groom_occupation, txt_groom_empl_in, txt_groom_annual_income,
            txt_groom_country, txt_groom_state, txt_groom_city, txt_groom_citizen, txt_groom_fam_val, txt_groom_fam_type, txt_groom_fam_status, txt_groom_father_occup, txt_groom_mother_occup;
JSONObject  json_partner_edit_basic,json_partner_edit_religious,json_partner_edit_professional,
        json_partner_edit_family,json_partner_edit_locaiton,json_edit_basic_pro,
        json_edit_religious,json_edit_family_details,json_edit_locaiton,json_edit_professional,json_edit_contact;
    IntentFilter filter1;
    TextView txt_copyright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        context = this;
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        ll_uchildr=(LinearLayout)findViewById(R.id.ll_uchild);
        ll_pchildr=(LinearLayout)findViewById(R.id.ll_pchild);
        ll_gothram=(LinearLayout)findViewById(R.id.ll_gothram);

//Edit(basic)
        txt_terms=(TextView)findViewById(R.id.txt_terms);
        txt_copyright=(TextView)findViewById(R.id.txt_copyright);
        txt_dailyrecomm_name = (TextView) findViewById(R.id.txt_daily_recom_name);
        txt_dailyrecomm_gender = (TextView) findViewById(R.id.txt_daily_recom_gender);
        txt_dailyrecomm_age = (TextView) findViewById(R.id.txt_daily_recom_age);

        txt_daily_recomm_height = (TextView) findViewById(R.id.txt_daily_recom_height);
        txt_dailyrecomm_weight = (TextView) findViewById(R.id.txt_daily_recom_weight);
        txt_dailyrecomm_marital_stat = (TextView) findViewById(R.id.txt_daily_recom_martial_status);
        txt_dailyrecomm_child=(TextView)findViewById(R.id.txt_daily_recom_child);

        txt_dailyrecomm_mother_ton = (TextView) findViewById(R.id.txt_daily_recom_mother_ton);
        txt_dailyrecomm_phy_stat = (TextView) findViewById(R.id.txt_daily_recom_physical_stat);
        txt_dailyrecomm_body_type = (TextView) findViewById(R.id.txt_daily_recom_body_type);

        txt_dailyrecomm_prof_by = (TextView) findViewById(R.id.txt_daily_recom_profile_create);
        txt_dailyrecomm_drink = (TextView) findViewById(R.id.txt_daily_recom_drinking);
        txt_dailyrecommm_smoke = (TextView) findViewById(R.id.txt_daily_recom_smoke);
        txt_dailyrecomm_eat = (TextView) findViewById(R.id.txt_daily_recom_eating_habits);

//religious(Personnal)

        txt_per_religion = (TextView) findViewById(R.id.txt_daily_recom_religion);
        txt_per_gothram = (TextView) findViewById(R.id.txt_daily_recom_gothram);
        txt_caste = (TextView) findViewById(R.id.txt_daily_recom_caste);
        txt_sub_caste = (TextView) findViewById(R.id.txt_daily_recom_sub_caste);
        txt_zodiac = (TextView) findViewById(R.id.txt_daily_recom_zodiac);
        txt_star = (TextView) findViewById(R.id.txt_daily_recom_star);
        txt_dhosham = (TextView) findViewById(R.id.txt_daily_recom_dhosham);

//education(Personnal)

        txt_qualif = (TextView) findViewById(R.id.txt_daily_recom_qualif);
        txt_qualif_det = (TextView) findViewById(R.id.txt_daily_recom_qualif_det);
        txt_occupation = (TextView) findViewById(R.id.txt_daily_recom_occupation);
        txt_empl_in = (TextView) findViewById(R.id.txt_daily_recom_empl_in);
        txt_annual_income = (TextView) findViewById(R.id.txt_daily_recom_annual_income);

        //location(Personnal)

        txt_country = (TextView) findViewById(R.id.txt_daily_recom_country);
        txt_state = (TextView) findViewById(R.id.txt_daily_recom_state);
        txt_city = (TextView) findViewById(R.id.txt_daily_recom_city);
        txt_citizen = (TextView) findViewById(R.id.txt_daily_recom_citizen);

//family det (Personal)

        txt_fam_val = (TextView) findViewById(R.id.txt_daily_recom_fam_val);
        txt_fam_type = (TextView) findViewById(R.id.txt_daily_recom_fam_type);
        txt_fam_status = (TextView) findViewById(R.id.txt_daily_recom_fam_stat);
        txt_father_occup = (TextView) findViewById(R.id.txt_daily_recom_father_occ);
        txt_mother_occup = (TextView) findViewById(R.id.txt_daily_recom_mother_occ);


        txt_edit_hobbies = (TextView) findViewById(R.id.txt_daily_recom_hobbie);


        //Partner Pref(Groom)

        txt_groom_age = (TextView) findViewById(R.id.txt_daily_recom_groom_age);

        txt_groom_height = (TextView) findViewById(R.id.txt_daily_recom_groom_height);
        txt_groom_weight = (TextView) findViewById(R.id.txt_daily_recom_groom_weight);
        txt_groom_marital_status = (TextView) findViewById(R.id.txt_daily_recom_groom_martial_status);
        txt_groom_child= (TextView) findViewById(R.id.txt_daily_recom_groom_child);

        txt_groom_mother_tongue = (TextView) findViewById(R.id.txt_daily_recom_groom_mother_ton);
        txt_groom_phy_stat = (TextView) findViewById(R.id.txt_daily_recom_groom_physical_stat);
        txt_groom_body_type = (TextView) findViewById(R.id.txt_daily_recom_groom_body_type);

        //txt_groom_profile_created = (TextView) findViewById(R.id.txt_daily_recom_groom_profile_create);
        txt_groom_eating = (TextView) findViewById(R.id.txt_daily_recom_groom_eating_habits);
        txt_groom_drinking = (TextView) findViewById(R.id.txt_daily_recom_groom_drinking);
        txt_groom_smoking = (TextView) findViewById(R.id.txt_daily_recom_groom_smoke);


//religious(Groom)

        txt_groom_religion = (TextView) findViewById(R.id.txt_daily_recom_groom_religion);
        txt_groom_gothram = (TextView) findViewById(R.id.txt_daily_recom_groom_gothram);
        txt_groom_caste = (TextView) findViewById(R.id.txt_daily_recom_groom_caste);
        txt_groom_sub_caste = (TextView) findViewById(R.id.txt_daily_recom_groom_sub_caste);
        txt_groom_zodiac = (TextView) findViewById(R.id.txt_daily_recom_groom_zodiac);
        txt_groom_star = (TextView) findViewById(R.id.txt_daily_recom_groom_martial_star);
        txt_groom_dhosham = (TextView) findViewById(R.id.txt_daily_recom_groom_dhosham);


        //education(Groom)
        txt_groom_qualif = (TextView) findViewById(R.id.txt_daily_recom_groom_qualif);
       // txt_groom_qualif_det = (TextView) findViewById(R.id.txt_daily_recom_groom_qualif_det);
        txt_groom_occupation = (TextView) findViewById(R.id.txt_daily_recom_groom_occupation);
        txt_groom_annual_income = (TextView) findViewById(R.id.txt_daily_recom_groom_annual_income);

        txt_groom_empl_in = (TextView) findViewById(R.id.txt_daily_recom_groom_emplyin_det);

        //location(Groom)

        txt_groom_country = (TextView) findViewById(R.id.txt_daily_recom_groom_country);
        txt_groom_state = (TextView) findViewById(R.id.txt_daily_recom_groom_state);
        txt_groom_city = (TextView) findViewById(R.id.txt_daily_recom_groom_city);
        txt_groom_citizen = (TextView) findViewById(R.id.txt_daily_recom_groom_citizen);

//family det (Groom)

        txt_groom_fam_val = (TextView) findViewById(R.id.txt_daily_recom_groom_fam_val);
        txt_groom_fam_type = (TextView) findViewById(R.id.txt_daily_recom_groom_fam_type);
        txt_groom_fam_status = (TextView) findViewById(R.id.txt_daily_recom_groom_fam_stat);
        txt_groom_father_occup = (TextView) findViewById(R.id.txt_daily_recom_groom_father_occ);
        txt_groom_mother_occup = (TextView) findViewById(R.id.txt_daily_recom_groom_mother_occ);


        ll_my_profile = (LinearLayout) findViewById(R.id.ll_my_profile);
        btn_upload = (Button) findViewById(R.id.btn_add_photo);
        btn_view_horo=(Button)findViewById(R.id.btn_view_horo);
        btn_view_phone_num = (Button) findViewById(R.id.btn_view_phone);
        txt_basic_profile = (TextView) findViewById(R.id.txt_click_basic);
        txt_edit_hobbies = (TextView) findViewById(R.id.txt_click_hobbies);
        rnd_image = (Rounded_Imageview) findViewById(R.id.user_image_upload);
        txt_edit_religion = (TextView) findViewById(R.id.txt_click_religion);
        txt_edit_prof_info = (TextView) findViewById(R.id.txt_click_prof_info);
      //  img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
        txt_edit_loc = (TextView) findViewById(R.id.txt_click_location);
        txt_edit_family_det = (TextView) findViewById(R.id.txt_click_family_det);
        txt_edit_abt_you = (TextView) findViewById(R.id.txt_click_abt_you);
        btn_rate = (Button) findViewById(R.id.btn_rate);
        ed_abt_you = (EditText) findViewById(R.id.et_abt_you);
        ed_hobbies = (EditText) findViewById(R.id.et_my_hobbies);
        img_edit_abt_you = (ImageView) findViewById(R.id.img_edit_abt_you);
        img_edit_hobbies = (ImageView) findViewById(R.id.img_edit_hobbies);

        txt_click_partner_basic = (TextView) findViewById(R.id.txt_click_partner_basic);
        txt_click_partner_family_det = (TextView) findViewById(R.id.txt_click_partner_family_det);
        txt_click_partner_location = (TextView) findViewById(R.id.txt_click_partner_location);
        txt_click_partner_professional = (TextView) findViewById(R.id.txt_click_partner_professional);
        txt_click_partner_religious = (TextView) findViewById(R.id.txt_click_partner_religious);





        gD = new GeneralData(context);

        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        Log.e("NN--editprof", str_image);
        str_rate=gD.prefs.getString("Rating",null);

//toolbar contents
        img_user_image = (Rounded_Imageview) findViewById(R.id.user_image);
        img_menu = (ImageView) findViewById(R.id.menu);
        myTabsDrawer = (DrawerLayout) findViewById(R.id.my_tabs_drawer_layout);
        myTabsSliderLayout = (LinearLayout) findViewById(R.id.llayslider);

//your matches
        img_mymatches_grey = (ImageView) findViewById(R.id.img_my_matches_grey);
        txt_mymatches = (TextView) findViewById(R.id.txt_mymatches);
        matchesLayout = (LinearLayout) findViewById(R.id.my_matches);

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


        matchesLayout.setOnClickListener(this);
        mailboxLayout.setOnClickListener(this);
        dailyrecomLayout.setOnClickListener(this);
        editProLayout.setOnClickListener(this);
        upgradeLayout.setOnClickListener(this);
        settingsLayout.setOnClickListener(this);
        feedbackLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        addhoroLAyout.setOnClickListener(this);
        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(EditProfile.this, TermsAndConditions.class));
            }
        });

        Calendar calendar= Calendar.getInstance();
        txt_copyright.setText("copyright@"+calendar.get(Calendar.YEAR));
        // imgLoader = new ImageLoader(context);
        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);

        imgLoader = new ImageLoader(context);

        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);

        pg_menu_profile_comp_level = (ProgressBar) findViewById(R.id.pg_menu_progress);
        txt_menu_level_percent = (TextView) findViewById(R.id.txt_menu_progress_id);

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);


        if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

            imgLoader.DisplayImage(str_image, img_user_image);

           /* Picasso.with(context)
                    .load(str_image)
                    .placeholder(R.drawable.default_use)
                    .into(img_user_image);*/


            txt_name.setText(str_name);
            txt_id.setText(str_easy_marry_id);
            Log.i("ND", "PLevel : " + str_prof_compl_level);
            txt_level_percent.setText(str_prof_compl_level + "%");
            pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));


            txt_menu_level_percent.setText(str_prof_compl_level + "%");
            pg_menu_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));
        }

        img_edit_abt_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (n_count == 0) {
                    ed_abt_you.setEnabled(true);
                    ed_abt_you.setCursorVisible(true);
                    // Toast.makeText(EditProfile.this, ed_abt_you.getText().toString(), Toast.LENGTH_SHORT).show();
                    img_edit_abt_you.setBackgroundResource(R.drawable.save);
                    n_count = 1;
                } else if (n_count == 1) {
                    if(ed_abt_you.getText().toString().trim().length()==0){
                        Toast.makeText(EditProfile.this, "Enter something about yourself", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ed_abt_you.setEnabled(false);
                        ed_abt_you.setCursorVisible(false);
                        updateAboutUs(ed_abt_you.getText().toString().trim());
                        img_edit_abt_you.setBackgroundResource(R.drawable.edit_orange);
                        n_count = 0;

                    }
                    // Toast.makeText(EditProfile.this, ed_abt_you.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_edit_hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (n_count == 0) {
                    ed_hobbies.setEnabled(true);
                    ed_hobbies.setCursorVisible(true);
                    // Toast.makeText(EditProfile.this, ed_abt_you.getText().toString(), Toast.LENGTH_SHORT).show();
                    img_edit_hobbies.setBackgroundResource(R.drawable.save);
                    n_count = 1;
                } else if (n_count == 1) {
                    // Toast.makeText(EditProfile.this, ed_abt_you.getText().toString(), Toast.LENGTH_SHORT).show();
                    if(ed_hobbies.getText().toString().trim().length()==0){
                        Toast.makeText(EditProfile.this, "Enter hobbies", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ed_hobbies.setEnabled(false);
                        ed_hobbies.setCursorVisible(false);
                        updateHobbies(ed_hobbies.getText().toString().trim());
                        img_edit_hobbies.setBackgroundResource(R.drawable.edit_orange);
                        n_count = 0;
                    }
                }
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this, DailyRecomViewProfie.class);
                i.putExtra("str_from", "header");
                startActivity(i);
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
     /*   img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, EditProfile.class));
            }
        });*/

        txt_basic_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileBasic.class);
                i.putExtra("json_basic_pref",String.valueOf(json_edit_basic_pro));
                i.putExtra("json_response","user");
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_edit_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileReligion.class);
                i.putExtra("json_releigous_pref",String.valueOf(json_edit_religious));
                i.putExtra("json_response","user");
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileLocation.class);
                i.putExtra("json_edit_locaiton", String.valueOf(json_edit_locaiton));
                i.putExtra("json_response","user");
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_edit_family_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileFamilyDetails.class);
                i.putExtra("json_response","user");
                i.putExtra("json_edit_family_details", String.valueOf(json_edit_family_details));
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        txt_edit_prof_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileProfessional.class);

                i.putExtra("json_response","user");
                i.putExtra("json_edit_professional",String.valueOf(json_edit_professional));
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //partner preference

        txt_click_partner_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileBasic.class);
                i.putExtra("json_basic_pref",String.valueOf(json_partner_edit_basic));
                i.putExtra("json_response","partner");
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_click_partner_religious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileReligion.class);

                i.putExtra("json_response","partner");
                i.putExtra("json_releigous_pref",String.valueOf(json_partner_edit_religious));
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_click_partner_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileLocation.class);
                i.putExtra("json_response","partner");

                i.putExtra("json_edit_locaiton", String.valueOf(json_partner_edit_locaiton));
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_click_partner_family_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileFamilyDetails.class);


                i.putExtra("json_edit_family_details", String.valueOf(json_partner_edit_family));
                i.putExtra("json_response","partner");
                startActivity(i);
                finish();
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_click_partner_professional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                Intent i = new Intent(EditProfile.this,EditProfileProfessional.class);


                i.putExtra("json_edit_professional", String.valueOf(json_partner_edit_professional));
                i.putExtra("json_response","partner");
                startActivity(i);
                finish();

                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTabsDrawer.openDrawer(myTabsSliderLayout);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (rnd_image.getDrawable() != null) {
                        update();
                    } else {
                        add_image();
                    }
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_view_horo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i= new Intent(EditProfile.this,EditHoroscope.class);
        startActivity(i);
        //Toast.makeText(EditProfile.this, "Under Development", Toast.LENGTH_SHORT).show();
    }
});
        btn_view_phone_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setCancelable(true);
                View itemView1;
                itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.view_phone_edit_poup, null);
                final android.support.v7.app.AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);

                ImageView img_close_dialog = (ImageView) itemView1.findViewById(R.id.img_close_dialog);
                TextView txt_edit_contact = (TextView) itemView1.findViewById(R.id.txt_contact_det);
                TextView txt_edit_mobile = (TextView) itemView1.findViewById(R.id.txt_mobile_no);
                TextView txt_edit_cont_mum = (TextView) itemView1.findViewById(R.id.txt_cont_num);
                TextView txt_edit_par_mob = (TextView) itemView1.findViewById(R.id.txt_par_mobile_no);
                TextView txt_edit_conv_time = (TextView) itemView1.findViewById(R.id.txt_conv_time);
                ImageView img_edit_cont=(ImageView)itemView1.findViewById(R.id.img_edit_phone);
                txt_edit_contact.setText(str_name+"("+str_easy_marry_id+")");
                txt_edit_mobile.setText(str_mob_num);
                txt_edit_cont_mum.setText(str_cont_num);
                txt_edit_par_mob.setText(str_par_mob_num);
                txt_edit_conv_time.setText(str_conv_time);
                img_edit_cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(EditProfile.this, EditProfilePhoneNumber.class);
                        Log.e("NN:ed_con", String.valueOf(json_edit_contact));
                        i.putExtra("json_edit_contact", String.valueOf(json_edit_contact));
                        startActivity(i);
                        altDialog.dismiss();
                    }
                });
                img_close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                    }
                });
                altDialog.show();
            }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if(str_rate.equalsIgnoreCase("0")){
                    View itemView1;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(context)
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
                                Toast.makeText(EditProfile.this, "Please enter the rating star", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                rateNowCall(str_user_id, String.valueOf(ratingBar.getRating()));
                                altDialog.dismiss();
                            }
                        }
                    });
                    altDialog.show();

                }
                else{
                    Toast.makeText(EditProfile.this, "You have already rated...", Toast.LENGTH_SHORT).show();
                }
                }
                else{
                    Toast.makeText(EditProfile.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set the color for already selected menu
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

        if (vv instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) vv;
            ImageView imV = (ImageView) ll.getChildAt(0);
            imV.setBackgroundResource(R.drawable.edit_profile_orange);
            TextView tvName = (TextView) ll.getChildAt(1);
            tvName.setTextColor(Color.parseColor("#fb7b09"));
        }

        imgLoader.DisplayImage(str_image, rnd_image);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQUEST_CODE_GALLERY) {


                    Uri selectedImageUri = data.getData();
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                            null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor.moveToFirst();

                    String selectedImagePath = cursor.getString(column_index);

                    Bitmap bm;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 200;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                            && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(selectedImagePath, options);
                    rnd_image.setImageBitmap(bm);

                    try {
                        if (requestCode == 1 && resultCode == RESULT_OK) {
                            String realPath;
                            // SDK < API11
                            if (Build.VERSION.SDK_INT < 11) {
                                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                            }
                            // SDK >= 11 && SDK < 19
                            else if (Build.VERSION.SDK_INT < 19) {
                                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                            }
                            // SDK > 19 (Android 4.4)
                            else {
                                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                                Log.i("realll path->", realPath);
                            }
                            Uri uriFromPath = Uri.fromFile(new File(realPath));
                            File sourceFilePath = new File(realPath);
                            strrealpath = realPath;
                            Log.i("BIt", "Value : " + bm);
                            uploadImage(bm);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
                    try {
                        final int IMAGE_MAX_SIZE = 300;

                        // Bitmap bitmap;
                        File file = null;
                        FileInputStream fis;
                        BitmapFactory.Options opts;
                        int resizeScale;
                        Bitmap bmp;
                     //   file = new File(Uri.parse(mCurrentPhotoPath).getPath());
                        // This bit determines only the width/height of the
                        // bitmap
                        // without loading the contents
                        opts = new BitmapFactory.Options();
                        opts.inJustDecodeBounds = true;
                        fis = new FileInputStream(file);
                        BitmapFactory.decodeStream(fis, null, opts);
                        fis.close();

                        // Find the correct scale value. It should be a power of
                        // 2
                        resizeScale = 1;

                        if (opts.outHeight > IMAGE_MAX_SIZE
                                || opts.outWidth > IMAGE_MAX_SIZE) {
                            resizeScale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(opts.outHeight, opts.outWidth)) / Math.log(0.5)));
                        }

                        // Load pre-scaled bitmap
                        opts = new BitmapFactory.Options();
                        opts.inSampleSize = resizeScale;


                        fis = new FileInputStream(file);



                        bmp = BitmapFactory.decodeStream(fis, null, opts);
                        Bitmap getBitmapSize = BitmapFactory.decodeResource(
                                getResources(), R.drawable.default_use);
                   /* rnd_image.setLayoutParams(new RelativeLayout.LayoutParams(
                            200, 200));//(width,height);*/
                        rnd_image.setImageBitmap(bmp);
                        //  rnd_image.setRotation(90);
                        fis.close();

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 70, baos);

                        Log.i("BIt", "Value : " + bmp);
                        uploadImage(bmp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (requestCode == GALLERY_ACTIVITY_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    try{
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA };
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        performCrop(picturePath);

                      /*  //return Image Path to the Main Activity
                        Intent returnFromGalleryIntent = new Intent();
                        returnFromGalleryIntent.putExtra("picturePath",picturePath);
                        setResult(RESULT_OK,returnFromGalleryIntent);
                        finish();*/
                    }catch(Exception e){
                        e.printStackTrace();
                        Intent returnFromGalleryIntent = new Intent();
                        setResult(RESULT_CANCELED, returnFromGalleryIntent);
                        finish();
                    }
                }else{
                    Log.i("NN","RESULT_CANCELED");
                    Intent returnFromGalleryIntent = new Intent();
                    setResult(RESULT_CANCELED, returnFromGalleryIntent);
                    finish();
                }
            }

            if (requestCode == RESULT_CROP ) {
                if(resultCode == Activity.RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");
                    // Set The Bitmap Data To ImageView
                    rnd_image.setImageBitmap(selectedBitmap);
                    Log.i("BIt", "Value : " + selectedBitmap);
                    uploadImage(selectedBitmap);

//                    rnd_image.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
   /* @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_GALLERY) {


            Uri selectedImageUri = intent.getData();
            if(selectedImageUri==null){
                    Log.e("NN","gallery cancelled");
            }
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);

            rnd_image.setImageBitmap(bm);


            try {
                if (requestCode == 1 && responseCode == RESULT_OK) {
                    String realPath;
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, intent.getData());
                    }
                    // SDK >= 11 && SDK < 19
                    else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, intent.getData());
                    }
                    // SDK > 19 (Android 4.4)
                    else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, intent.getData());
                        Log.i("realll path->", realPath);
                    }


                    Uri uriFromPath = Uri.fromFile(new File(realPath));

                    // 1. Get the image path from the gallery------------ vinoth
                    File sourceFilePath = new File(realPath);

                    uploadImage(bm);
                    strrealpath = realPath;
                    Log.i("DD", sourceFilePath.getAbsolutePath());
                    Log.d("HMKCODE", "Real Path: " + realPath);

                    // 2. Upload the multipart content of the image to the server------------ vinoth
                    // ImageUploadMultipartTask imUMT = new ImageUploadMultipartTask(sourceFilePath.getAbsolutePath(), "http://aryvartdev.com/projects/utician/register.php?user_id=",);
                    //imUMT.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);

                rnd_image.setImageBitmap(bitmap);
                uploadImage(bitmap);
                String path = Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }*/

    private void update() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Remove Image"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        // builder.setTitle("Select Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    takePicture();
                } else if (item == 1) {
                    openGallery();
                } else {
                    rnd_image.setImageDrawable(
                            getResources().getDrawable(R.drawable.girl));
                }
            }
        });
        builder.show();
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     //startActivityForResult(intent, REQUEST_CODE_GALLERY);
       startActivityForResult(intent, GALLERY_ACTIVITY_CODE);
    }

    private void add_image() {

        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library"};

            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);

            // builder.setTitle("Select Image");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // camera
                    if (item == 0) {
                        takePicture();
                    } else {
                        openGallery();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
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

                startActivity(new Intent(EditProfile.this, Matches.class));
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


                startActivity(new Intent(EditProfile.this, MailboxNewTabs.class));
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
                Intent i = new Intent(EditProfile.this, DailyRecommList.class);
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

                startActivity(new Intent(EditProfile.this, EditProfile.class));

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

                startActivity(new Intent(EditProfile.this, MembershipTabs.class));
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

                startActivity(new Intent(EditProfile.this, Settings.class));

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
                startActivity(new Intent(EditProfile.this, Feedback.class));

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

                startActivity(new Intent(EditProfile.this, QuickSearch.class));

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
                startActivity(new Intent(EditProfile.this, AddHoroscope.class));

                break;
        }
    }

    public void EditDisplayUserCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "user_data.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Log.i("HH_edit_disp", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH_edit_disp", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONObject responseJsonObj = jsobj.getJSONObject("Response");


                                String str_own_words = responseJsonObj.getString("mywords");
                                ed_abt_you.setText(str_own_words);


                                //Edit My(Basic pref)
                                JSONObject myPrefJsonObj = responseJsonObj.getJSONObject("My Preference");


                                String Hobbie = myPrefJsonObj.getString("Hobbies and Interest");
                                ed_hobbies.setText(Hobbie);

                                JSONObject myBasicPrefJsonObj = myPrefJsonObj.getJSONObject("Basic info");

                                json_edit_basic_pro=myBasicPrefJsonObj;

                                String name = myBasicPrefJsonObj.getString("Name");
                                String gender = myBasicPrefJsonObj.getString("Gender");
                                String age = myBasicPrefJsonObj.getString("Age");
                                String dob = myBasicPrefJsonObj.getString("Dob");
                                String height = myBasicPrefJsonObj.getString("Height");
                                String mother_tongue = myBasicPrefJsonObj.getString("Mother Tongue");
                                String weight = myBasicPrefJsonObj.getString("Weight");
                                String married_status = myBasicPrefJsonObj.getString("Married Status");
                                String no_of_child = myBasicPrefJsonObj.getString("No of Child");
                                String body_type = myBasicPrefJsonObj.getString("Body Type");
                                String eating_habits = myBasicPrefJsonObj.getString("Eating Habits");
                                String drinking_habits = myBasicPrefJsonObj.getString("Drinking Habits");
                                String smoking_habits = myBasicPrefJsonObj.getString("Smoking Habits");
                                String physical_status = myBasicPrefJsonObj.getString("Physical Status");
                                String profile_created = myBasicPrefJsonObj.getString("Profile Created By");

                                if(married_status.equalsIgnoreCase("unmarried")||married_status.equalsIgnoreCase("not available")){
                                    ll_uchildr.setVisibility(View.GONE);
                                }
                                else{
                                    ll_uchildr.setVisibility(View.VISIBLE);
                                    txt_dailyrecomm_child.setText(no_of_child);
                                }



                                txt_dailyrecomm_name.setText(name);
                                txt_dailyrecomm_gender.setText(gender);
                                txt_dailyrecomm_age.setText(age);
                                txt_daily_recomm_height.setText(height);
                                txt_dailyrecomm_weight.setText(weight);
                                txt_dailyrecomm_marital_stat.setText(married_status);

                                txt_dailyrecomm_mother_ton.setText(mother_tongue);
                                txt_dailyrecomm_phy_stat.setText(physical_status);
                                txt_dailyrecomm_body_type.setText(body_type);
                                txt_dailyrecomm_prof_by.setText(profile_created);
                                txt_dailyrecomm_eat.setText(eating_habits);
                                txt_dailyrecomm_drink.setText(drinking_habits);
                                txt_dailyrecommm_smoke.setText(smoking_habits);

                                //Edit My(Religious)
                                JSONObject myReligiousJsonObj = myPrefJsonObj.getJSONObject("Religious Information");

                                json_edit_religious=myReligiousJsonObj;
                                String religion = myReligiousJsonObj.getString("religious");
                                String gothram = myReligiousJsonObj.getString("Gothram");
                                String caste = myReligiousJsonObj.getString("Caste");
                                String sub_caste = myReligiousJsonObj.getString("Sub Caste");
                                String zodiac_sign = myReligiousJsonObj.getString("Zodaic Sign");
                                String star = myReligiousJsonObj.getString("Star");
                                String dhosham = myReligiousJsonObj.getString("Dhosham");
                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                                if(religion.equalsIgnoreCase("hindu")){
                                    ll_gothram.setVisibility(View.VISIBLE);
                                }
                                else {
                                    ll_gothram.setVisibility(View.GONE);
                                }

                                txt_per_religion.setText(religion);
                                txt_per_gothram.setText(gothram);
                                txt_caste.setText(caste);
                                txt_sub_caste.setText(sub_caste);
                                txt_zodiac.setText(zodiac_sign);
                                txt_star.setText(star);

                                if(dhosham.equalsIgnoreCase("1")){
                                    txt_dhosham.setText("Yes");
                                }
                                if(dhosham.equalsIgnoreCase("2")){
                                    txt_dhosham.setText("No");
                                }
                                if(dhosham.equalsIgnoreCase("3")){
                                    txt_dhosham.setText("Don't know");
                                }
                                if(dhosham.equalsIgnoreCase("Not available")){
                                    txt_dhosham.setText("Not available");
                                }


                                //Edit My(Professional)


                                JSONObject myProfessionalJsonObj = myPrefJsonObj.getJSONObject("Professional Information");

                                json_edit_professional=myProfessionalJsonObj;
                                String qualif = myProfessionalJsonObj.getString("Qualification");
                                String ocuupation = myProfessionalJsonObj.getString("Occupation");
                                String qualif_det = myProfessionalJsonObj.getString("Qualification in Detail");
                                String emp_in = myProfessionalJsonObj.getString("Employed in");
                                String annual_income = myProfessionalJsonObj.getString("Annual Income");
                                txt_qualif.setText(qualif);
                                txt_qualif_det.setText(qualif_det);
                                txt_occupation.setText(ocuupation);
                                txt_empl_in.setText(emp_in);
                                txt_annual_income.setText(annual_income);


                                //Edit My(Location)
                                JSONObject myLocationJsonObj = myPrefJsonObj.getJSONObject("Location");

                                json_edit_locaiton=myLocationJsonObj;
                                String country = myLocationJsonObj.getString("Country");
                                String state = myLocationJsonObj.getString("State");
                                String city = myLocationJsonObj.getString("City");
                                String citizen = myLocationJsonObj.getString("Citizenship");
                                txt_country.setText(country);
                                txt_city.setText(city);
                                txt_state.setText(state);
                                txt_citizen.setText(citizen);

//Edit My(Family Det)
                                JSONObject myFamDetJsonObj = myPrefJsonObj.getJSONObject("Family Details");

                                json_edit_family_details=myFamDetJsonObj;

                                String fam_val = myFamDetJsonObj.getString("Family Values");
                                String fam_type = myFamDetJsonObj.getString("Family Type");
                                String fam_status = myFamDetJsonObj.getString("Family Status");
                                String father_occ = myFamDetJsonObj.getString("Father Occupation");
                                String mother_occ = myFamDetJsonObj.getString("Mother Occupation");
                                txt_fam_val.setText(fam_val);
                                txt_fam_type.setText(fam_type);
                                txt_fam_status.setText(fam_status);
                                txt_father_occup.setText(father_occ);
                                txt_mother_occup.setText(mother_occ);

                                JSONObject myContactJsonObj = myPrefJsonObj.getJSONObject("Contact Details");

                                json_edit_contact=myContactJsonObj;

                                String mob_num = myContactJsonObj.getString("Mobile Number");
                                String cont_num = myContactJsonObj.getString("Contact Number");
                                String par_mob_num = myContactJsonObj.getString("Parent Contact");
                                String conv_time = myContactJsonObj.getString("Convenient Time to Call");
                                str_mob_num=mob_num;
                                str_cont_num=cont_num;
                                str_par_mob_num=par_mob_num;
                                str_conv_time=conv_time;


                                //partner(Basic pref)
                                JSONObject partnerPrefJsonObj = responseJsonObj.getJSONObject("Partner Preference");

                                JSONObject partnerBasicPrefJsonObj = partnerPrefJsonObj.getJSONObject("Basic info");
                                json_partner_edit_basic=partnerBasicPrefJsonObj;
                                String partner_age = partnerBasicPrefJsonObj.getString("Groom Age");
                                String partner_height = partnerBasicPrefJsonObj.getString("Height");
                                String partner_mother_tongue = partnerBasicPrefJsonObj.getString("Mother Tongue");
                                String partner_weight = partnerBasicPrefJsonObj.getString("Weight");
                                String partner_married_status = partnerBasicPrefJsonObj.getString("Married Status");
                                String no_of_pchild = partnerBasicPrefJsonObj.getString("No of Child");
                                String partner_body_type = partnerBasicPrefJsonObj.getString("Body Type");
                                String partner_eating_habits = partnerBasicPrefJsonObj.getString("Eating Habits");
                                String partner_drinking_habits = partnerBasicPrefJsonObj.getString("Drinking Habits");
                                String partner_smoking_habits = partnerBasicPrefJsonObj.getString("Smoking Habits");
                                String partner_physical_status = partnerBasicPrefJsonObj.getString("Physical Status");
                                //  String partner_profile_created = partnerBasicPrefJsonObj.getString("Profile Created By");

                                if(partner_married_status.equalsIgnoreCase("unmarried")||partner_married_status.equalsIgnoreCase("not available")){
                                    ll_pchildr.setVisibility(View.GONE);
                                }
                                else{
                                    ll_pchildr.setVisibility(View.VISIBLE);
                                    txt_groom_child.setText(no_of_pchild);
                                }

                                txt_groom_age.setText(partner_age);
                                txt_groom_height.setText(partner_height);
                                txt_groom_weight.setText(partner_weight);
                                txt_groom_marital_status.setText(partner_married_status);
                                txt_groom_mother_tongue.setText(partner_mother_tongue);
                                txt_groom_phy_stat.setText(partner_physical_status);
                                txt_groom_body_type.setText(partner_body_type);

                                //  txt_groom_profile_created.setText(partner_profile_created);
                                txt_groom_eating.setText(partner_eating_habits);
                                txt_groom_drinking.setText(partner_drinking_habits);
                                txt_groom_smoking.setText(partner_smoking_habits);


                                //edit partner religious info

                                JSONObject partnerReligiousJsonObj = partnerPrefJsonObj.getJSONObject("Religious Information");
                                json_partner_edit_religious=partnerReligiousJsonObj;
                                String partner_religion = partnerReligiousJsonObj.getString("religious");
                                String partner_gothram = partnerReligiousJsonObj.getString("Gothram");
                                String partner_caste = partnerReligiousJsonObj.getString("Caste");
                                String partner_sub_caste = partnerReligiousJsonObj.getString("Sub Caste");
                                String partner_zodiac_sign = partnerReligiousJsonObj.getString("Zodaic Sign");
                                String partner_star = partnerReligiousJsonObj.getString("Star");
                                String partner_dhosham = partnerReligiousJsonObj.getString("Dhosham");

                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                                txt_groom_religion.setText(partner_religion);
                                txt_groom_gothram.setText(partner_gothram);
                                txt_groom_caste.setText(partner_caste);
                                txt_groom_sub_caste.setText(partner_sub_caste);
                                txt_groom_zodiac.setText(partner_zodiac_sign);
                                txt_groom_star.setText(partner_star);

                                Log.i("HH", "grom_dhosam-->" + partner_dhosham);
                                if(partner_dhosham.equalsIgnoreCase("1")){
                                    txt_groom_dhosham.setText("Yes");
                                }
                                if(partner_dhosham.equalsIgnoreCase("2")){
                                    txt_groom_dhosham.setText("No");
                                }
                                 if(partner_dhosham.equalsIgnoreCase("3")){
                                    txt_groom_dhosham.setText("Doesn't matter");
                                }
                                if(partner_dhosham.equalsIgnoreCase("Not available")){
                                    txt_groom_dhosham.setText("Not available");
                                }

                               // txt_groom_dhosham.setText(partner_dhosham);


                                //edit professional(Groom)

                                JSONObject partnerProfessionalJsonObj = partnerPrefJsonObj.getJSONObject("Professional Information");

                                json_partner_edit_professional=partnerProfessionalJsonObj;
                                Log.i("HH", "partnerProfessionalJsonObj : " + partnerProfessionalJsonObj);
                                String partner_qualif = partnerProfessionalJsonObj.getString("Qualification");
                                String partner_ocuupation = partnerProfessionalJsonObj.getString("Occupation");
                                // String partner_qualif_det = partnerProfessionalJsonObj.getString("Qualification in Detail");
                                String partner_emp_in = partnerProfessionalJsonObj.getString("Employed in");
                                String partner_annual_income = partnerProfessionalJsonObj.getString("Annual Income");
                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");

                                txt_groom_qualif.setText(partner_qualif);
                                txt_groom_empl_in.setText(partner_emp_in);
                                txt_groom_occupation.setText(partner_ocuupation);
                                txt_groom_annual_income.setText(partner_annual_income);


                                //partner(location)

                                JSONObject partnerLocationJsonObj = partnerPrefJsonObj.getJSONObject("Location");
                                json_partner_edit_locaiton=partnerLocationJsonObj;
                                Log.i("HH", "partnerLocationJsonObj : " + partnerLocationJsonObj);
                                String partner_country = partnerLocationJsonObj.getString("Country");
                                String partner_state = partnerLocationJsonObj.getString("State");
                                String partner_city = partnerLocationJsonObj.getString("City");
                                String partner_citizen = partnerLocationJsonObj.getString("Citizenship");


                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                                txt_groom_country.setText(partner_country);
                                txt_groom_city.setText(partner_city);
                                txt_groom_state.setText(partner_state);
                                txt_groom_citizen.setText(partner_citizen);

//partner(family det)

                                JSONObject partnerFamDetJsonObj = partnerPrefJsonObj.getJSONObject("Family Details");

                                json_partner_edit_family=partnerFamDetJsonObj;
                                Log.i("HH", "partnerFamDetJsonObj : " + partnerFamDetJsonObj);

                                String partner_fam_val = partnerFamDetJsonObj.getString("Family Values");
                                String partner_fam_type = partnerFamDetJsonObj.getString("Family Type");
                                String partner_fam_status = partnerFamDetJsonObj.getString("Family Status");
                                String partner_father_occ = partnerFamDetJsonObj.getString("Father Occupation");
                                String partner_mother_occ = partnerFamDetJsonObj.getString("Mother Occupation");

                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                                txt_groom_fam_val.setText(partner_fam_val);
                                txt_groom_fam_type.setText(partner_fam_type);
                                txt_groom_fam_status.setText(partner_fam_status);
                                txt_groom_father_occup.setText(partner_father_occ);
                                txt_groom_mother_occup.setText(partner_mother_occ);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(VerifyMobileNumber.this, error.toString(), Toast.LENGTH_LONG).show();
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

                params.put("userid", str_user_id);


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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }


    private void uploadImage(final Bitmap bmp) {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register10_screen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            Log.i("HH", "strResp : " + s);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(s);

                            Log.i("HH", "strResp : " + s);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                //Disimissing the progress dialog
                                String strProfileImage = GeneralData.LOCAL_IP_IMAGE + "upload/" + jsobj.getJSONObject("Response").getString("profileImage");
                                Log.i("HH_edit", "url : " + strProfileImage);
                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("profileimage", strProfileImage);
                                prefEdit.commit();

                                loading.dismiss();
                                Toast.makeText(EditProfile.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();
                                //Showing toast message of the response
                                // Toast.makeText(UserRegSixAddPhoto.this, s, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EditProfile.this, EditProfile.class));
                                finish();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        //Toast.makeText(UserRegSixAddPhoto.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bmp);

                //Getting Image Name


                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("userid", str_user_id);
                params.put("image", image);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
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
                                Toast.makeText(EditProfile.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(context, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void updateAboutUs(final String str_abt_you) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_user6.php",
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
                                Toast.makeText(EditProfile.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();

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

                Log.e("NN:abt",str_user_id);
                Log.e("NN:myownwords",str_abt_you);
                params.put("userid", str_user_id);
                params.put("myownwords", str_abt_you);


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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }
    public void updateHobbies(final String str_hobbies) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_user7.php",
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
                                Toast.makeText(EditProfile.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();

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

                Log.e("NN:abt",str_user_id);
                Log.e("NN:hobbies",str_hobbies);
                params.put("userid", str_user_id);
                params.put("hobbies", str_hobbies);


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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
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
            final ProgressDialog loading = ProgressDialog.show(context, "Loading ", "Please wait...", false, false);
            if (isConnected) {
                loading.dismiss();
                Log.i("LK", "connected");
                EditDisplayUserCall();

            } else {
                loading.dismiss();
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_basic_det_popup, null);
                final android.support.v7.app.AlertDialog altDialog = builder.create();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

