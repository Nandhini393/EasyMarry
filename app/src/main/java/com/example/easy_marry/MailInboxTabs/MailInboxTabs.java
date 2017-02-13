package com.example.easy_marry.MailInboxTabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Horoscope.AddHoroscope;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.DailyRecommList;
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Feedback;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.QuickSearch;
import com.example.easy_marry.R;
import com.example.easy_marry.Rounded_Imageview;
import com.example.easy_marry.Settings;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.swibetabs.Matches;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 14/6/16.
 */
public class MailInboxTabs extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    LinearLayout img_option;
    LinearLayout ll_option_menu;
    TextView txt_op_new, txt_op_read, txt_op_accept, txt_op_rep, txt_op_not_int, txt_op_trash, txt_op_inbox, txt_op_sent, txt_op_disp;
    private int flag = 0;

    private Toolbar toolbar;
    Button btn_rate;
    protected ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout myTabsDrawer;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    TextView txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
    Context context;

    ImageView img_edit_my_profile;
    LinearLayout ll_parent_menu, ll_my_profile;
    public static String strFrom;
    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level;
    TextView txt_level_percent, txt_name, txt_id;

    GeneralData gD;
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_inbox);


        // rate btn

        btn_rate = (Button) findViewById(R.id.btn_rate);

        context = this;
        gD = new GeneralData(context);

        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);

        img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
        ll_my_profile = (LinearLayout) findViewById(R.id.ll_my_profile);
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
        img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MailInboxTabs.this, EditProfile.class));
            }
        });

        img_option = (LinearLayout) findViewById(R.id.option_menu);
        ll_option_menu = (LinearLayout) findViewById(R.id.ll_option);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // setupViewPager(viewPager);
        setupViewPagerOne(viewPager, "Inbox");

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        strFrom = "inbox";

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_option_menu.setVisibility(View.GONE);
            }
        });
        txt_op_new = (TextView) findViewById(R.id.txt_op_new);
        txt_op_read = (TextView) findViewById(R.id.txt_op_read);
        txt_op_accept = (TextView) findViewById(R.id.txt_op_accept);
        txt_op_rep = (TextView) findViewById(R.id.txt_op_reply);
        txt_op_not_int = (TextView) findViewById(R.id.txt_op_not_int);
        txt_op_trash = (TextView) findViewById(R.id.txt_op_trash);
        txt_op_inbox = (TextView) findViewById(R.id.txt_op_inbox);
        txt_op_sent = (TextView) findViewById(R.id.txt_op_sent);
        txt_op_disp = (TextView) findViewById(R.id.option_text);


        txt_op_new.setOnClickListener(this);
        txt_op_read.setOnClickListener(this);
        txt_op_accept.setOnClickListener(this);
        txt_op_rep.setOnClickListener(this);
        txt_op_not_int.setOnClickListener(this);
        txt_op_trash.setOnClickListener(this);
        txt_op_inbox.setOnClickListener(this);
        txt_op_sent.setOnClickListener(this);


        matchesLayout.setOnClickListener(this);
        mailboxLayout.setOnClickListener(this);
        dailyrecomLayout.setOnClickListener(this);
        editProLayout.setOnClickListener(this);
        upgradeLayout.setOnClickListener(this);
        settingsLayout.setOnClickListener(this);
        feedbackLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        addhoroLAyout.setOnClickListener(this);

        imgLoader = new ImageLoader(context);
        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);

        if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

            //  imgLoader.DisplayImage(str_image, img_user_image);

            Picasso.with(context)
                    .load(str_image)
                    .placeholder(R.drawable.default_use)
                    .into(img_user_image);


            txt_name.setText(str_name);
            txt_id.setText(str_easy_marry_id);
            Log.i("ND", "PLevel : " + str_prof_compl_level);
            txt_level_percent.setText(str_prof_compl_level + "%");
            pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));
        }



        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTabsDrawer.openDrawer(myTabsSliderLayout);
            }
        });
        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MailInboxTabs.this, DailyRecomViewProfie.class);
                i.putExtra("str_from", "header");
                startActivity(i);
            }
        });
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View itemView1;
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.rate_us_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                RatingBar ratingBar = (RatingBar) itemView1.findViewById(R.id.rate_app);
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                TextView txt_span = (TextView) itemView1.findViewById(R.id.rate_span);

                String str_span_text = "How was your experience with our app?";

                SpannableString spannableString1 = new SpannableString(str_span_text);
                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 12, 23, 0);
                txt_span.setText(spannableString1);
                altDialog.show();


               /* final Dialog dialog = new Dialog(Matches.this);

                dialog.setContentView(R.layout.rate_us_popup);
                RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(0).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                stars.getDrawable(1).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
                TextView txt_span = (TextView) dialog.findViewById(R.id.rate_span);

                String str_span_text = "How was your experience with our app?";

                SpannableString spannableString1 = new SpannableString(str_span_text);
                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 12, 23, 0);
                txt_span.setText(spannableString1);

                dialog.show();*/
            }
        });
        img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {
                    flag = 1; // 1 => Button ON
                    ll_option_menu.setVisibility(View.VISIBLE);
                } else {
                    flag = 0; // 0 => Button OFF
                    ll_option_menu.setVisibility(View.GONE);
                }


            }
        });


        //set the color for already selected menu
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

        if (vv instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) vv;
            ImageView imV = (ImageView) ll.getChildAt(0);
            imV.setBackgroundResource(R.drawable.mail_box_orange);
            TextView tvName = (TextView) ll.getChildAt(1);
            tvName.setTextColor(Color.parseColor("#fb7b09"));
        }

        if (gD.isConnectingToInternet()) {
            Log.i("LK", "connected");

            if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

                //  imgLoader.DisplayImage(str_image, img_user_image);

                Picasso.with(context)
                        .load(str_image)
                        .placeholder(R.drawable.default_use)
                        .into(img_user_image);


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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new InboxFragment(), "Inbox");
        adapter.addFragment(new InboxFragment(), "Sent");
        viewPager.setAdapter(adapter);
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
            case R.id.txt_op_new:

                txt_op_new.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_new.getText().toString());

                strFrom = "new";
                setupViewPagerOne(viewPager, "New");
                tabLayout.setupWithViewPager(viewPager);
                break;


            case R.id.txt_op_read:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_read.getText().toString());

                strFrom = "readnotreplied";

                setupViewPagerOne(viewPager, "Read not replied");
                tabLayout.setupWithViewPager(viewPager);

                break;
            case R.id.txt_op_accept:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_accept.getText().toString());
                strFrom = "accepted";
                setupViewPagerOne(viewPager, "Accepted");
                tabLayout.setupWithViewPager(viewPager);
                break;
            case R.id.txt_op_reply:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_rep.getText().toString());
                strFrom = "replied";
                setupViewPagerOne(viewPager, "Replied");
                tabLayout.setupWithViewPager(viewPager);
                break;
            case R.id.txt_op_not_int:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_not_int.getText().toString());
                strFrom = "notinterest";
                setupViewPagerOne(viewPager, "Not interested");
                tabLayout.setupWithViewPager(viewPager);
                break;
            case R.id.txt_op_trash:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_trash.getText().toString());
                strFrom = "trash";
                setupViewPagerOne(viewPager, "Trash");
                tabLayout.setupWithViewPager(viewPager);

                break;
            case R.id.txt_op_inbox:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_inbox.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_disp.setText("" + txt_op_inbox.getText().toString());
                strFrom = "inbox";
                setupViewPagerOne(viewPager, "Inbox");
                tabLayout.setupWithViewPager(viewPager);

                break;
            case R.id.txt_op_sent:

                txt_op_new.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_accept.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_read.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_not_int.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_rep.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_trash.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_sent.setTextColor(Color.parseColor("#fb7b09"));
                txt_op_inbox.setTextColor(Color.parseColor("#9e9e9c"));
                txt_op_disp.setText("" + txt_op_sent.getText().toString());
                strFrom = "sent";
                setupViewPagerOne(viewPager, "Sent");
                tabLayout.setupWithViewPager(viewPager);

                break;
            case R.id.my_matches:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_orange);
                txt_mymatches.setTextColor(Color.parseColor("#fb7b09"));

                Intent matches = new Intent(MailInboxTabs.this, Matches.class);
                startActivity(matches);

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

                Intent i = new Intent(MailInboxTabs.this, DailyRecommList.class);
                i.putExtra("str_from", "daily_recomm");
                startActivity(i);

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
                startActivity(new Intent(MailInboxTabs.this, DailyRecommList.class));

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

                startActivity(new Intent(MailInboxTabs.this, EditProfile.class));

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
                startActivity(new Intent(MailInboxTabs.this, MembershipTabs.class));

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
                startActivity(new Intent(MailInboxTabs.this, Settings.class));

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
                startActivity(new Intent(MailInboxTabs.this, Feedback.class));

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

                startActivity(new Intent(MailInboxTabs.this, QuickSearch.class));

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
                startActivity(new Intent(MailInboxTabs.this, AddHoroscope.class));

                break;
        }

    }

    private void setupViewPagerOne(ViewPager viewPager, String str_type) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InboxFragment(), str_type);
        //adapter.addFragment(new InboxFragment(), "sent");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            Log.i("NDD", "Title : " + title);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}

