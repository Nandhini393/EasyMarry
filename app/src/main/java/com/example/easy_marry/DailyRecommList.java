package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import com.example.easy_marry.Adapter.DailyRecommAdapter;
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Horoscope.AddHoroscope;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MailInboxTabs.MailboxNewTabs;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android2 on 26/9/16.
 */
public class DailyRecommList extends Activity implements View.OnClickListener, MyInterface {


    String str_from_val;
    Context context;
    Button btn_rate;
    DrawerLayout myTabsDrawer;
    LinearLayout ll_parent_menu, ll_my_profile;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    TextView txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
    GeneralData gD;
    ImageView img_my_profile_banner;
    //ImageView img_edit_my_profile;
    // Animation
    //  Animation animFadein;
    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level;
    TextView txt_level_percent, txt_name, txt_id;

    private List<RecyclerBean> movieList = new ArrayList<>();
    // private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;


    /*ImageView img_user;
    TextView txt_uname, txt_easy_marry_id, txt_addr;
    LinearLayout ll_skip, ll_int_yes;*/

    LinearLayout ll_daily_recomm;
    private DailyRecommAdapter dailyRecommAdapter;

    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id,
            interest_msg = "", str_membership, str_skip = "", str_del_id, str_rate;

    ArrayList<RecyclerBean> alBeanDailyRecommand;

    int nIncrement = 0;
    ProgressDialog skip_loading;
    // timer
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ProgressBar mProgressBar;
    TextView textViewShowTime; // will show the time
    CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    TextView txt_terms, txt_error_msg;
    String date;
    int str_remain_min1;
    ArrayList<RecyclerBean> alBeanDailyRecommand_One;
    TextView txt_copyright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_recomm_list);
        //  str_from_val = getIntent().getStringExtra("str_from");
        context = this;
        gD = new GeneralData(context);
        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_membership = gD.prefs.getString("memplan", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        str_rate = gD.prefs.getString("Rating", null);
        // rate btn
        Log.e("NN--dailyrecom:prof", str_image);
        //str_skip = getIntent().getStringExtra("skip");
        txt_error_msg = (TextView) findViewById(R.id.txt_error_msg);
        ll_daily_recomm = (LinearLayout) findViewById(R.id.ll_daily_recomm);
         date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        /* animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        animFadein.setAnimationListener(this);*/
        /*img_user = (ImageView) findViewById(R.id.img_list_user);
        txt_uname = (TextView) findViewById(R.id.txt_list_name);
        txt_easy_marry_id = (TextView) findViewById(R.id.txt_list_emid);
        txt_addr = (TextView) findViewById(R.id.txt_list_addr);
        ll_skip = (LinearLayout) findViewById(R.id.ll_skip);
        ll_int_yes = (LinearLayout) findViewById(R.id.ll_int_yes);*/

        btn_rate = (Button) findViewById(R.id.btn_rate);
       // img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
        img_my_profile_banner = (ImageView) findViewById(R.id.user_banner_img);

        txt_terms = (TextView) findViewById(R.id.txt_terms);
//toolbar contents
        txt_copyright=(TextView)findViewById(R.id.txt_copyright);
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
        ll_my_profile = (LinearLayout) findViewById(R.id.ll_my_profile);
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

        Calendar calendar= Calendar.getInstance();
        txt_copyright.setText("copyright@"+calendar.get(Calendar.YEAR));
        //recyclerView = (RecyclerView) findViewById(R.id.tab_one_list);
        dailyRecommListCall();
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calander.getTime());
        String [] current_time=time.split(":");
        int hour =Integer.parseInt(current_time[0]);
        int min =Integer.parseInt(current_time[1]);
        int seconds =Integer.parseInt(current_time[2]);
        Log.e("LOI","hour:"+ hour);
        Log.e("LOI","min:"+ min);
        Log.e("LOI","seconds:"+ seconds);
        str_remain_min1=(hour *60) +( min ) + seconds;

        // timer

        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyRecommList.this, TermsAndConditions.class));
            }
        });

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myTabsDrawer.openDrawer(myTabsSliderLayout);
            }
        });

      /*  img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyRecommList.this, EditProfile.class));

            }
        });*/
        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DailyRecommList.this, DailyRecommList.class);
                i.putExtra("str_from", "header");
                startActivity(i);
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


        //imgLoader = new ImageLoader(context);
        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);

        imgLoader = new ImageLoader(context);
        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);


        if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

            imgLoader.DisplayImage(str_image, img_user_image);

           /* Picasso.with(context)
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


        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_rate.equalsIgnoreCase("0")) {
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
                                Toast.makeText(DailyRecommList.this, "Please enter the rating star", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                rateNowCall(str_user_id, String.valueOf(ratingBar.getRating()));
                                altDialog.dismiss();
                            }
                        }
                    });
                    altDialog.show();

                } else {
                    Toast.makeText(DailyRecommList.this, "You have already rated...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

        if (vv instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) vv;
            ImageView imV = (ImageView) ll.getChildAt(0);
            imV.setBackgroundResource(R.drawable.daily_recom_orange);
            TextView tvName = (TextView) ll.getChildAt(1);
            tvName.setTextColor(Color.parseColor("#fb7b09"));
        }


//internet checking
        if (gD.isConnectingToInternet()) {
            Log.i("LK", "connected");

            if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

                imgLoader.DisplayImage(str_image, img_user_image);

               /* Picasso.with(context)
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
        } else {
            Log.i("LK", " not connected");
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);

            View itemView1 = LayoutInflater.from(context)
                    .inflate(R.layout.user_current_loc_addr_alert, null);
            final AlertDialog altDialog = builder.create();
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

                startActivity(new Intent(DailyRecommList.this, Matches.class));
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


                startActivity(new Intent(DailyRecommList.this, MailboxNewTabs.class));
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
                Intent i = new Intent(DailyRecommList.this, DailyRecommList.class);
                //i.putExtra("str_from", "daily_recomm");
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

                startActivity(new Intent(DailyRecommList.this, EditProfile.class));

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

                startActivity(new Intent(DailyRecommList.this, MembershipTabs.class));
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

                startActivity(new Intent(DailyRecommList.this, Settings.class));

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
                startActivity(new Intent(DailyRecommList.this, Feedback.class));

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

                startActivity(new Intent(DailyRecommList.this, QuickSearch.class));

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
                startActivity(new Intent(DailyRecommList.this, AddHoroscope.class));


                break;
        }
    }

    public void dailyRecommListCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "recommendation.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("NN", "strResp_dail_list : " + response);
                            alBeanDailyRecommand = new ArrayList<RecyclerBean>();

                            alBeanDailyRecommand_One = new ArrayList<RecyclerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp_dail_list : " + response);

                            if (jsobj.getInt("code") == 1) {
                                txt_error_msg.setVisibility(View.GONE);
                                Log.i("HH", "Hellooooooooooo  *** success");
                                JSONArray json_matched_profile = jsobj.getJSONArray("Response");


                                if (json_matched_profile.length() > 0) {
                                    ll_daily_recomm.removeAllViews();
                                    for (int i = 0; i < json_matched_profile.length(); i++) {

                                        RecyclerBean drawerBean = new RecyclerBean();
                                        JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                                        drawerBean.setStr_id(matchesJSONObj.getString("userid"));
                                        drawerBean.setStr_easy_marry_id(matchesJSONObj.getString("easymarryid"));
                                        drawerBean.setStr_username(matchesJSONObj.getString("name"));
                                        drawerBean.setStr_userdetails(matchesJSONObj.getString("age") + ","
                                                + matchesJSONObj.getString("height") +
                                                "\n" + matchesJSONObj.getString("religious") + "," + matchesJSONObj.getString("caste") +
                                                "\n" + matchesJSONObj.getString("city")
                                                + "," + matchesJSONObj.getString("state")
                                                + "," + matchesJSONObj.getString("country")
                                                + "\n" + matchesJSONObj.getString("qualification")
                                                + "\n" + matchesJSONObj.getString("occupation"));
                                        drawerBean.setStr_membership(matchesJSONObj.getString("membership"));


                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                                        Log.i("NN", strImageURL);
                                        drawerBean.setStr_userimage(strImageURL);
                                        alBeanDailyRecommand.add(drawerBean);

                                        alBeanDailyRecommand_One.add(drawerBean);
                                        createChild(strImageURL, matchesJSONObj.getString("name"), matchesJSONObj.getString("easymarryid"), matchesJSONObj.getString("age") + ","
                                                + matchesJSONObj.getString("height") +
                                                "\n" + matchesJSONObj.getString("religious") + "," + matchesJSONObj.getString("caste") +
                                                "\n" + matchesJSONObj.getString("city")
                                                + "," + matchesJSONObj.getString("state")
                                                + "," + matchesJSONObj.getString("country")
                                                + "\n" + matchesJSONObj.getString("qualification")
                                                + "\n" + matchesJSONObj.getString("occupation"), i, matchesJSONObj.getString("userid"));

                                        // str_del_id = String.valueOf(i);
                                        Log.e("NN:", "i" + i);

                                    }

                                    Log.e("NN:", "al_size" + alBeanDailyRecommand.size());
                                } else {
                                    txt_error_msg.setVisibility(View.VISIBLE);
                                }


                            }
                            else   if (jsobj.getInt("code") == 0) {
                                startActivity(new Intent(DailyRecommList.this,DailyRecommSkip.class));
                                finish();
                            }
                            else   if (jsobj.getInt("code") == 4) {
                                txt_error_msg.setVisibility(View.VISIBLE);
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

                Log.e("NN", str_user_id);
                Log.e("NN", date);
                params.put("userid", str_user_id);
                params.put("recommenddate", date);


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

    public void rateNowCall(final String str_my_id, final String str_rating) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "rating.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            //  Log.i("HH", "strResp : " + response);
                            JSONObject jsobj = new JSONObject(response);
                            //     Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(DailyRecommList.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
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

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

    }

    @Override
    public String get_matches(String str_id, String str_partner_name, String strFrom, String str_type, String str_sent_int,RecyclerView recycleV) {

        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal) {
        return null;
    }

    private int createChild(final String str_image, final String str_part_name, final String str_emid, String str_addr, final int str_id, final String str_part_id) {

        LayoutInflater inflater = LayoutInflater.from(DailyRecommList.this);

        final View view1 = inflater.inflate(R.layout.daily_recomm_content_new, null);
        Log.e("UI", "c-"+String.valueOf(str_id));

        final ImageView img_user = (ImageView) view1.findViewById(R.id.img_list_user);
        final TextView txt_uname = (TextView) view1.findViewById(R.id.txt_list_name);
        final TextView txt_easy_marry_id = (TextView) view1.findViewById(R.id.txt_list_emid);
        final TextView txt_addr = (TextView) view1.findViewById(R.id.txt_list_addr);
        TextView txt_view_full_prof = (TextView) view1.findViewById(R.id.txt_view_full_pro);
        final LinearLayout ll_skip = (LinearLayout) view1.findViewById(R.id.ll_skip);
        final LinearLayout ll_int_yes = (LinearLayout) view1.findViewById(R.id.ll_int_yes);
        final ProgressBar mProgressBar;
        final TextView textViewShowTime; // will show the time
        CountDownTimer countDownTimerr; // built in android class
        long totalTimeCountInMilliseconds; // total count down time in
        final long timeBlinkInMilliseconds; // start time of start blinking
        final boolean blink = true;

        imgLoader.DisplayImage(str_image, img_user);
        txt_easy_marry_id.setText(str_emid);
        txt_uname.setText(str_part_name);
        txt_addr.setText(str_addr);

        textViewShowTime = (TextView) view1.findViewById(R.id.tvTimeCount_DR);
        mProgressBar = (ProgressBar) view1.findViewById(R.id.progressbar_DS);
        txt_view_full_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DailyRecommList.this, DailyRecomViewProfie.class);
                i.putExtra("str_from", "daily_recomm");
                i.putExtra("user_id", str_part_id);
                i.putExtra("user_name", str_part_name);
                str_del_id = str_part_id;
                startActivity(i);
            }
        });

        ll_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nIncrement++;
                Log.e("UI", "b-"+String.valueOf(str_id));
                Log.e("UI", "b-"+String.valueOf(alBeanDailyRecommand));
                if (alBeanDailyRecommand.size() > nIncrement) {
                    Log.e("PK:", String.valueOf(str_id));
                   // skip_loading.dismiss();
                    ll_daily_recomm.removeViewAt(str_id);
                    //ll_daily_recomm.removeViewAt(ll_daily_recomm.indexOfChild(view1));
                    // ll_daily_recomm.removeViewAt(str_id);
                    Log.e("UI", "dai-"+String.valueOf(ll_daily_recomm.indexOfChild(view1)));
                    Log.e("UI", "a"+String.valueOf(str_id));
                    skipCall(str_user_id, str_part_id,str_id);
                } else {
                  //  skip_loading.dismiss();
                    ll_skip.setEnabled(false);
                    startActivity(new Intent(DailyRecommList.this, DailyRecommSkip.class));
                    finish();
                    //Toast.makeText(DailyRecommList.this, "you have reached your limit for today", Toast.LENGTH_SHORT).show();
                }

               /* skip_loading = ProgressDialog.show(DailyRecommList.this, "Removing from daily recomm ...", "Please wait...", false, false);
                nIncrement++;
                Log.e("PK:", String.valueOf(alBeanDailyRecommand.size()));
                if (alBeanDailyRecommand.size() > nIncrement) {
                    Log.e("PK:", String.valueOf(str_id));
                    skip_loading.dismiss();
                    ll_daily_recomm.removeViewAt(str_id);
                   // skipCall(str_user_id, str_part_id,str_id);
                } else {
                    skip_loading.dismiss();
                    ll_skip.setEnabled(false);
                    startActivity(new Intent(DailyRecommList.this, DailyRecommSkip.class));
                    finish();
                    //Toast.makeText(DailyRecommList.this, "you have reached your limit for today", Toast.LENGTH_SHORT).show();
                }
*/
            }
        });
        ll_int_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_send_int_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                final EditText ed_interest = (EditText) itemView1.findViewById(R.id.ed_interest);
                Button btn_sent_interest = (Button) itemView1.findViewById(R.id.btn_sent);

                if (str_membership.equalsIgnoreCase("free")) {
                    ed_interest.setEnabled(false);
                    interest_msg = "Hi " + str_part_name + ",I'm " + str_name + " .Please accept my request";
                    ed_interest.setText(interest_msg);
                    ed_interest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DailyRecommList.this, "you can't edit this text as you are basic member", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);

                    // interest_msg=ed_interest.getText().toString().trim();
                    // ed_interest.setText(interest_msg);


                }
                btn_sent_interest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialog loading = ProgressDialog.show(DailyRecommList.this, "Sending quick interest ...", "Please wait...", false, false);
                        sendInterestCall(str_user_id, str_part_id, ed_interest.getText().toString().trim(), "1");
                        altDialog.dismiss();
                        ll_daily_recomm.removeViewAt(str_id);
                        loading.dismiss();
                    }
                });
                altDialog.show();

            }
        });


        ll_daily_recomm.addView(view1);

        int minutes = 1440-str_remain_min1;
        totalTimeCountInMilliseconds = 60 * minutes * 1000;

        timeBlinkInMilliseconds = 30 * 1000;

        countDownTimerr = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setMax(60);
                mProgressBar.setProgress(Integer.parseInt(String.format("%02d", seconds % 60)));
                textViewShowTime.setTextAppearance(getApplicationContext(),
                        R.style.normalColor);


              /*  textViewShowTime.setText("Raja");
                mProgressBar.setProgress(80);*/


                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style


                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);

                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);

                    }
                    // toggle the value of blink
                }

               /* textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));*/

               /* textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60) + ":" + String.format("%02d", seconds % 60));
*/

                textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);


            }

        }.start();

        return 0;
    }

    /*private void startTimer() {

        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setProgress((int) (leftTimeInMilliseconds / 1000));
                textViewShowTime.setTextAppearance(getApplicationContext(),
                        R.style.normalColor);


                textViewShowTime.setText("Raja");
                mProgressBar.setProgress(80);


                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style


                    if (blink) {

                        textViewShowTime.setVisibility(View.VISIBLE);

                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

               *//* textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));*//*
                textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60) + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);

            }

        }.start();

    }*/

    @Override
    protected void onResume() {


        if (alBeanDailyRecommand != null) {
            Log.i("NV", "Size : " + alBeanDailyRecommand.size());

            // if (str_skip != null) {

            ProgressDialog loading = ProgressDialog.show(DailyRecommList.this, "Removing from daily recomm ...", "Please wait...", false, false);
            nIncrement++;

            if (alBeanDailyRecommand.size() > nIncrement) {
                Log.i("NV", "Step : 1");
                Log.i("NV", "Step : 1 str_del_id : " + str_del_id);
                for (int i = 0; i < alBeanDailyRecommand.size(); i++) {
                    String strID = alBeanDailyRecommand.get(i).getStr_id();
                    if (strID.equals(str_del_id)) {
                        ll_daily_recomm.removeViewAt(i);
                        break;
                    }
                }


            } else {
                Log.i("NV", "Step : 2");
                startActivity(new Intent(DailyRecommList.this, DailyRecommSkip.class));
                finish();
                //Toast.makeText(DailyRecommList.this, "you have reached your limit for today", Toast.LENGTH_SHORT).show();
            }
            loading.dismiss();

            //}
        }
        super.onResume();
    }

    public String sendInterestCall(final String str_my_id, final String str_partner_id, final String str_interest_msg, final String status) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "interest.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            //   Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            //  Log.i("HH", "strResp :int " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                Toast.makeText(context, "Interest sent", Toast.LENGTH_SHORT).show();

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

                Log.e("NN:fromid", str_my_id);
                Log.e("NN:toid", str_partner_id);
                Log.e("NN:status", status);
                Log.e("NN:message", str_interest_msg);

                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);
                params.put("status", status);
                params.put("message", str_interest_msg);


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

        return null;
    }
    public String skipCall(final String str_my_id, final String str_partner_id, final int str_id) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "skip.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            //   Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HHD", "strResp :int " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                //skip_loading.dismiss();
                                Log.e("PK1:", String.valueOf(str_id));
                                ll_daily_recomm.removeViewAt(str_id);
                                Toast.makeText(context, "Removing from daily recommendation..", Toast.LENGTH_SHORT).show();

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

                Log.e("NN:fromid", str_my_id);
                Log.e("NN:toid", str_partner_id);

                params.put("userid", str_my_id);
                params.put("skipid", str_partner_id);



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

        return null;
    }

}
