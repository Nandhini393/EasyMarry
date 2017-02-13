package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Horoscope.AddHoroscope;
import com.example.easy_marry.MailInboxTabs.MailboxNewTabs;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 20/6/16.
 */
public class Settings extends Activity implements View.OnClickListener {
    LinearLayout ll_notify,ll_account;
    Button btn_rate;
    View itemView1;
    DrawerLayout myTabsDrawer;
    LinearLayout ll_parent_menu,ll_my_profile;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    TextView txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
   // ImageView img_edit_my_profile;
    Context ctx;
    GeneralData gD;
    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level;
    TextView txt_level_percent,txt_name, txt_id,txt_terms;
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id,str_rate;
    TextView txt_copyright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ll_notify=(LinearLayout)findViewById(R.id.notify);
        ll_account=(LinearLayout)findViewById(R.id.account);


        ctx = this;
        gD = new GeneralData(ctx);

        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        str_rate=gD.prefs.getString("Rating",null);

        Log.e("NN--sett",str_image);

        // rate btn
        txt_copyright=(TextView)findViewById(R.id.txt_copyright);
        btn_rate = (Button) findViewById(R.id.btn_rate);
        txt_terms=(TextView)findViewById(R.id.txt_terms);
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);
//toolbar contents
        img_user_image = (Rounded_Imageview) findViewById(R.id.user_image);
        img_menu = (ImageView) findViewById(R.id.menu);
        myTabsDrawer = (DrawerLayout) findViewById(R.id.my_tabs_drawer_layout);
        myTabsSliderLayout = (LinearLayout) findViewById(R.id.llayslider);
     //   img_edit_my_profile=(ImageView)findViewById(R.id.img_edit_my_profile);
//your matches
        img_mymatches_grey = (ImageView) findViewById(R.id.img_my_matches_grey);
        txt_mymatches = (TextView) findViewById(R.id.txt_mymatches);
        matchesLayout = (LinearLayout) findViewById(R.id.my_matches);
        ll_my_profile=(LinearLayout)findViewById(R.id.ll_my_profile);

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

     /*   img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,EditProfile.class));
                finish();
            }
        });*/


        ll_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,Notification.class));

            }
        });
        ll_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,Account.class));

            }
        });
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTabsDrawer.openDrawer(myTabsSliderLayout);
            }
        });
        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Settings.this,DailyRecomViewProfie.class);
                i.putExtra("str_from","header");
                startActivity(i);
                finish();
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
        imgLoader = new ImageLoader(ctx);
        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Settings.this, TermsAndConditions.class));
            }
        });
        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent=(TextView)findViewById(R.id.txt_level_percent);
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
        Calendar calendar= Calendar.getInstance();
        txt_copyright.setText("copyright@"+calendar.get(Calendar.YEAR));


        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_rate.equalsIgnoreCase("0")){
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
                                Toast.makeText(Settings.this, "Please enter the rating star", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Settings.this, "You have already rated...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

        if (vv instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) vv;
            ImageView imV = (ImageView) ll.getChildAt(0);
            imV.setBackgroundResource(R.drawable.settings_orange);
            TextView tvName = (TextView) ll.getChildAt(1);
            tvName.setTextColor(Color.parseColor("#fb7b09"));
        }
        //internet checking
       /* if (gD.isConnectingToInternet()) {
            Log.i("LK", "connected");

            if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

                 imgLoader.DisplayImage(str_image, img_user_image);

               *//* Picasso.with(ctx)
                        .load(str_image)
                        .placeholder(R.drawable.default_use)
                        .into(img_user_image);
*//*

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
                                Toast.makeText(Settings.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
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

                        //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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

                startActivity(new Intent(Settings.this, Matches.class));
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


                startActivity(new Intent(Settings.this, MailboxNewTabs.class));
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
                Intent i= new Intent(Settings.this,DailyRecommList.class);
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

                startActivity(new Intent(Settings.this, EditProfile.class));

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

                startActivity(new Intent(Settings.this, MembershipTabs.class));
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

                startActivity(new Intent(Settings.this, Settings.class));

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
                startActivity(new Intent(Settings.this, Feedback.class));

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

                startActivity(new Intent(Settings.this, QuickSearch.class));

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
                startActivity(new Intent(Settings.this, AddHoroscope.class));

                break;
        }
    }

}
