package com.example.easy_marry.swibetabs;

import android.app.ProgressDialog;
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
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.DailyRecommSkip;
import com.example.easy_marry.FilterPage;
import com.example.easy_marry.FilterPageTest;
import com.example.easy_marry.Horoscope.AddHoroscope;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.DailyRecommList;
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Feedback;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MailInboxTabs.MailboxNewTabs;
import com.example.easy_marry.TermsAndConditions;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.MessengarTabs.MessangerTabs;
import com.example.easy_marry.QuickSearch;
import com.example.easy_marry.R;
import com.example.easy_marry.Rounded_Imageview;
import com.example.easy_marry.Settings;
import com.example.easy_marry.imageCache.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android2 on 14/6/16.
 */
public class Matches extends AppCompatActivity implements View.OnClickListener, MyInterface, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    Button btn_rate;
    protected ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout myTabsDrawer;
    LinearLayout ll_parent_menu, ll_my_profile;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    private ViewPager viewPager;
    TextView txt_name, txt_id, txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_messenger, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
    //ImageView img_edit_my_profile;
    Context ctx;
    // private CoordinatorLayout coordinatorLayout;
    GeneralData gD;
    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level;
    TextView txt_level_percent;
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id, str_membership, str_rate, str_filter_json;
    TextView txt_terms;
    String strResp = "success";
    boolean isClosed = false;
    IntentFilter filter1;
    static int nAttempt = 0;
    String strResp1 = "";
    String interest_msg = "";
    ImageView img_filter;
    public static String internet_con = "";
    ArrayList<ListDrawerBean> myList;
    int posi;
    static String str_partner_int_id;
TextView txt_copyright;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.matches);
            ctx = this;
            gD = new GeneralData(ctx);

           /* myList= new ArrayList<ListDrawerBean>();

            SharedPreferences sharedPrefs2 = PreferenceManager.getDefaultSharedPreferences(ctx);
            Gson gson2 = new Gson();
            String json2 = sharedPrefs2.getString("MK", null);
            Type type = new TypeToken<ArrayList<ListDrawerBean>>() {}.getType();
            ArrayList<ListDrawerBean> arrayList1 = gson2.fromJson(json2, type);

            Log.e("MK1", "two_arraylist-->"+ String.valueOf(arrayList1));
            Log.e("MK1", "two-->"+ String.valueOf(json2));


            //set value

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            ListDrawerBean lb= new ListDrawerBean();
            lb.setStr_list_items("nandhu");
            myList.add(lb);
            String json = gson.toJson(myList);
            Log.e("MK","one-->"+ String.valueOf(json));
            editor.putString("MK", json);
            editor.commit();

            //get Value
            SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(ctx);
            Gson gson1 = new Gson();
            String json1 = sharedPrefs1.getString("MK", null);
            Type type1 = new TypeToken<ArrayList<ListDrawerBean>>() {}.getType();
            ArrayList<ListDrawerBean> arrayList = gson1.fromJson(json1, type1);

            Log.e("MK", "two_arraylist-->"+ String.valueOf(arrayList));
            Log.e("MK", "two-->"+ String.valueOf(json1));
*/


            str_user_id = gD.prefs.getString("userid", null);
            str_image = gD.prefs.getString("profileimage", null);
            str_name = gD.prefs.getString("name", null);
            str_prof_compl_level = gD.prefs.getString("completelevel", null);
            str_easy_marry_id = gD.prefs.getString("easymarryid", null);
            str_membership = gD.prefs.getString("memplan", null);
            str_rate = gD.prefs.getString("Rating", null);
            str_filter_json = gD.prefs.getString("filter_json", null);

            filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(myReceiver, filter1);


            if (str_filter_json != null) {
                Log.i("NN", "filter json->" + str_filter_json);
            }

            Log.i("HH", "strResp 3");
            Log.e("NNm1:profileimage", str_image);
            Log.e("NNm1:member", str_membership);
            // rate btn
      /*  gD.str_name = getIntent().getStringExtra("str_name");
        gD.str_id = getIntent().getStringExtra("str_id");
        gD.str_complete_profile_level = getIntent().getStringExtra("str_profile_complete_level");
        gD.str_image = getIntent().getStringExtra("str_image");
        str_user_id = getIntent().getStringExtra("user_id");*/
            txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
            txt_terms = (TextView) findViewById(R.id.txt_terms);


            //    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        txt_copyright=(TextView)findViewById(R.id.txt_copyright);
            btn_rate = (Button) findViewById(R.id.btn_rate);
            txt_name = (TextView) findViewById(R.id.txt_name);
            txt_id = (TextView) findViewById(R.id.txt_id);
            pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
            //img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
            ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);
            ll_my_profile = (LinearLayout) findViewById(R.id.ll_my_profile);
            img_filter = (ImageView) findViewById(R.id.filter);
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
            imgLoader = new ImageLoader(ctx);



     /*       //Internet Connection
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            final boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

            if (isConnected) {

                Log.i("LK1", "connected");
                internet_con = "yes";
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);

                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
                if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {
                    imgLoader.DisplayImage(str_image, img_user_image);
                    txt_name.setText(str_name);
                    txt_id.setText(str_easy_marry_id);
                    Log.i("ND", "PLevel : " + str_prof_compl_level);
                    txt_level_percent.setText(str_prof_compl_level + "%");
                    pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));

                }

            } else {
                Log.i("LK1", "not connected");
                internet_con = "no";
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);
                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);


            }*/

            Calendar calendar= Calendar.getInstance();
            txt_copyright.setText("copyright@"+calendar.get(Calendar.YEAR));
            img_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Matches.this, FilterPage.class));
                    finish();
                }
            });

            if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

                imgLoader.DisplayImage(str_image, img_user_image);

          /*      Picasso.with(ctx)
                        .load(str_image)
                        .placeholder(R.drawable.default_use)
                        .into(img_user_image);*/

                txt_name.setText(str_name);
                txt_id.setText(str_easy_marry_id);
                Log.i("ND", "PLevel : " + str_prof_compl_level);
                txt_level_percent.setText(str_prof_compl_level + "%");
                pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));
            }

           /* img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Matches.this, EditProfile.class));

                }
            });*/
            txt_terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Matches.this, TermsAndConditions.class));
                }
            });

            img_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*    Picasso.with(ctx)
                            .load(str_image).placeholder(R.drawable.default_use).into(img_user_image);
*/
                    myTabsDrawer.openDrawer(myTabsSliderLayout);
                }
            });

            //messenger

            img_messenger = (ImageView) findViewById(R.id.chat_bub);
            img_messenger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Matches.this, MessangerTabs.class));
                }
            });

            ll_my_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Matches.this, DailyRecomViewProfie.class);
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
                        altDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
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
                                    Toast.makeText(Matches.this, "Please enter the rating star", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    rateNowCall(str_user_id, String.valueOf(ratingBar.getRating()));
                                    altDialog.dismiss();
                                }


                            }
                        });
                        altDialog.show();


                    } else {
                        Toast.makeText(Matches.this, "You have already rated...", Toast.LENGTH_SHORT).show();
                    }
                }
            });

       /* matchesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_orange);
                txt_mymatches.setTextColor(Color.parseColor("#fb7b09"));


            }
        });
        mailboxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.GRAY);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_orange);
                txt_mailbox.setTextColor(Color.parseColor("#fb7b09"));
            }
        });
        dailyrecomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_orange);
                txt_dailyrecom.setTextColor(Color.parseColor("#fb7b09"));
            }
        });*/
            //set the color for already selected menu
            ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

            View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

            if (vv instanceof LinearLayout) {
                LinearLayout ll = (LinearLayout) vv;
                ImageView imV = (ImageView) ll.getChildAt(0);
                imV.setBackgroundResource(R.drawable.my_matches_orange);
                TextView tvName = (TextView) ll.getChildAt(1);
                tvName.setTextColor(Color.parseColor("#fb7b09"));
            }


       /* if (gD.isConnectingToInternet()) {
            Log.i("LK", "connected");
            Snackbar snackbar = Snackbar
                    .make(gD.coordinatorLayout, "Internet Connected", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go Online", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (GeneralData.str_image != null && GeneralData.str_name != null && GeneralData.str_id != null && GeneralData.str_complete_profile_level != null) {
                                imgLoader.DisplayImage(GeneralData.str_image, img_user_image);
                                txt_name.setText(GeneralData.str_name);
                                txt_id.setText(GeneralData.str_id);
                                Log.i("ND", "PLevel : " + GeneralData.str_complete_profile_level);
                                txt_level_percent.setText(GeneralData.str_complete_profile_level + "%");
                                pg_profile_comp_level.setProgress(Integer.parseInt(GeneralData.str_complete_profile_level));
                            }
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.BLUE);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);

            snackbar.show();


        }*/
        } catch (Exception e) {
            Log.i("FFA", e.getMessage());
            e.printStackTrace();
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        /*SharedPreferences.Editor prefEdit = gD.prefs.edit();
        prefEdit.putString("filter_json", null);
        prefEdit.commit();*/
        // str_filter_json="";
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            final boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
            if (isConnected) {

                Log.i("LK", "connected");
                internet_con = "yes";
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);
                viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                    @Override
                    public void transformPage(View page, float position) {
                        // do transformation here
                        //page.setRotationY(position * -30);

                        final float normalizedposition = Math.abs(Math.abs(position) - 1);
                        page.setScaleX(normalizedposition / 2 + 0.5f);
                        page.setScaleY(normalizedposition / 2 + 0.5f);

                    }
                });
                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);
                if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {
                    imgLoader.DisplayImage(str_image, img_user_image);
                    txt_name.setText(str_name);
                    txt_id.setText(str_easy_marry_id);
                    Log.i("ND", "PLevel : " + str_prof_compl_level);
                    txt_level_percent.setText(str_prof_compl_level + "%");
                    pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));

                }
             /*   if (nAttempt != 0) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Internet Connected", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Go Online", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {
                                         imgLoader.DisplayImage(str_image, img_user_image);

                                    *//*    Picasso.with(ctx)
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
                                }
                            });

                    // Changing message text color
                    snackbar.setActionTextColor(Color.BLUE);

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);

                    snackbar.show();
                }*/


            } else {
                viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                    @Override
                    public void transformPage(View page, float position) {
                        // do transformation here
                        //page.setRotationY(position * -30);

                        final float normalizedposition = Math.abs(Math.abs(position) - 1);
                        page.setScaleX(normalizedposition / 2 + 0.5f);
                        page.setScaleY(normalizedposition / 2 + 0.5f);

                    }
                });
                Log.i("LK", "not connected");
                internet_con = "no";
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);
                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);

                   /* rl_matches_list.setVisibility(View.GONE);
                    img_reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isConnected) {
                            rl_matches_list.setVisibility(View.VISIBLE);
                        Intent i= new Intent(Matches.this,Matches.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                           ll_no_internet_con.setVisibility(View.VISIBLE);
                        }
                    }
                });
*/
            }
        }
    };

    private void setupViewPager(final ViewPager viewPager) {
        Log.e("RAJ", "internet avail : " + internet_con);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (str_filter_json != null) {
            Log.i("NN", "filter json->" + str_filter_json);
        }

        /*filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);*/
        adapter.addFragment(new MatchesFragment(str_user_id, ctx, "MATCHES", str_filter_json), "MATCHES");
        // adapter.addFragment(new DiscoverFragment(str_user_id), "DISCOVER");
        adapter.addFragment(new NewMatchesFragment(str_user_id, ctx, "NEW MATCHES"), "NEW MATCHES");
        adapter.addFragment(new ShortListFragment(str_user_id, ctx, "SHORTLIST"), "SHORTLIST");
        adapter.addFragment(new ViewMyProfileFragment(str_user_id, ctx, "VIEW MY PROFILE"), "VIEW MY PROFILE");
        adapter.addFragment(new ShortListedMeFragment(str_user_id, ctx, "SHORLISTED ME"), "SHORLISTED ME");
        // adapter.addFragment(new NearByFragment(str_user_id, ctx, "NEAR YOU"), "NEAR YOU");
        adapter.addFragment(new IdSearchFragment(str_user_id, ctx), "ID SEARCH");

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // do your work
                posi = position;
                if (position == 0) {
                    img_filter.setVisibility(View.VISIBLE);
                    //get_matchesviewPager.getAdapter().notifyDataSetChanged();
                } else {
                    img_filter.setVisibility(View.GONE);
                }
            }
        });
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

                Intent matches = new Intent(Matches.this, Matches.class);
                startActivity(matches);
                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;


            case R.id.mail_box:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_orange);
                txt_mailbox.setTextColor(Color.parseColor("#fb7b09"));

                //mailboxLayout
                //      ll_parent_menu.getChildCount()
                Intent mailbox = new Intent(Matches.this, MailboxNewTabs.class);
                startActivity(mailbox);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;


            case R.id.daily_recom:


                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_orange);
                txt_dailyrecom.setTextColor(Color.parseColor("#fb7b09"));

                Intent i = new Intent(Matches.this, DailyRecommList.class);
                //i.putExtra("str_from", "daily_recomm");
                startActivity(i);


                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;

            case R.id.edit_profile:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_orange);
                txt_edit_profile.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(Matches.this, EditProfile.class));

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;


            case R.id.upgrade_acc:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_account_orange);
                txt_upgrade_acc.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(Matches.this, MembershipTabs.class));
                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;


            case R.id.settings:
                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_orange);
                txt_settings.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(Matches.this, Settings.class));

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;

            case R.id.feedback:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_orange);
                txt_feedback.setTextColor(Color.parseColor("#fb7b09"));
                startActivity(new Intent(Matches.this, Feedback.class));

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;

            case R.id.search:

                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_orange);
                txt_search.setTextColor(Color.parseColor("#fb7b09"));

                startActivity(new Intent(Matches.this, QuickSearch.class));

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_grey);
                txt_add_horo.setTextColor(Color.BLACK);

                break;

            case R.id.add_horo:


                img_mymatches_grey.setBackgroundResource(R.drawable.my_matches_grey);
                txt_mymatches.setTextColor(Color.BLACK);

                img_mailbox_grey.setBackgroundResource(R.drawable.mail_box_grey);
                txt_mailbox.setTextColor(Color.BLACK);

                img_dailyrecom_grey.setBackgroundResource(R.drawable.daily_recom_grey);
                txt_dailyrecom.setTextColor(Color.BLACK);

                img_editprof_grey.setBackgroundResource(R.drawable.edit_profile_grey);
                txt_edit_profile.setTextColor(Color.BLACK);

                img_upgradeacc_grey.setBackgroundResource(R.drawable.upgrade_acc_grey);
                txt_upgrade_acc.setTextColor(Color.BLACK);

                img_settings_grey.setBackgroundResource(R.drawable.settings_grey);
                txt_settings.setTextColor(Color.BLACK);

                img_feedback_grey.setBackgroundResource(R.drawable.feed_back_grey);
                txt_feedback.setTextColor(Color.BLACK);

                img_search_grey.setBackgroundResource(R.drawable.search_grey);
                txt_search.setTextColor(Color.BLACK);

                img_horo_grey.setBackgroundResource(R.drawable.add_horo_orange);
                txt_add_horo.setTextColor(Color.parseColor("#fb7b09"));
                startActivity(new Intent(Matches.this, AddHoroscope.class));

                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
         /*   if(nAttempt==0){
                startActivity(new Intent(Matches.this,Matches.class));
                finish();
                Toast.makeText(Matches.this, "click again to exit", Toast.LENGTH_SHORT).show();
                nAttempt+=1;
                //finish();
            }
            else if(nAttempt==1){
                nAttempt=0;
                finish();
               // Toast.makeText(Matches.this, "click again to exit", Toast.LENGTH_SHORT).show();
                //nAttempt+=1;
            }*/


            final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(false);

            View itemView1 = LayoutInflater.from(ctx)
                    .inflate(R.layout.custom_incomplete_reg_popup, null);
            final AlertDialog altDialog = builder.create();
            altDialog.setView(itemView1);
            altDialog.show();
            altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView txt_title = (TextView) itemView1.findViewById(R.id.txt_title);
            TextView txt_content = (TextView) itemView1.findViewById(R.id.txt_content);
            txt_title.setText("Exit");
            txt_content.setText("Do you wish to leave the app?");
            Button btn_continue = (Button) itemView1.findViewById(R.id.btn_cont_reg);
            btn_continue.setText("Yes");
            Button btn_exit = (Button) itemView1.findViewById(R.id.btn_exit);
            btn_exit.setText("No");
            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    altDialog.dismiss();
                    isClosed = false;
                }

            });

            btn_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                    prefEdit.putString("filter_json", null);
                    prefEdit.commit();
                    finish();
                    finishAffinity();
                    isClosed = true;
                }
            });

        }
        return isClosed;

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

    }

    @Override
    public String get_matches(final String str_partner_id, final String str_partner_name, String strFrom, String str_type,String str_interest, RecyclerView recycleV) {
        Log.i("DDR", "ID : " + str_partner_id);
        /*MatchesFragment fragment = (MatchesFragment) getSupportFragmentManager().findFragmentByTag(strFrom);
        fragment.*/
        Log.i("NNR", "Matches: " + strFrom);
       /* if(strFrom.equalsIgnoreCase("Matches")){
            img_filter.setVisibility(View.VISIBLE);
        }
        else{
            img_filter.setVisibility(View.GONE);
        }
*/


        if (str_type.equalsIgnoreCase("send_interest")) {
            if (!strFrom.equalsIgnoreCase("id search")) {



                strResp1= sendInterestCall(str_user_id, str_partner_id, str_interest, "1");

     /*           final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setCancelable(true);

                View itemView1 = LayoutInflater.from(ctx)
                        .inflate(R.layout.custom_send_int_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final EditText ed_interest = (EditText) itemView1.findViewById(R.id.ed_interest);
                Button btn_sent_interest = (Button) itemView1.findViewById(R.id.btn_sent);

                if (str_membership.equalsIgnoreCase("free")) {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);
                    interest_msg = "Hi " + str_partner_name + ",I'm " + str_name + " .Please accept my request";
                    ed_interest.setText(interest_msg);
                    ed_interest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(Matches.this, "you can't edit this text as you are basic member", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);
                    *//*interest_msg=ed_interest.getText().toString().trim();
                    ed_interest.setText(interest_msg);*//*


                }

                btn_sent_interest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // strResp1=str_partner_id;
                        strResp1= sendInterestCall(str_user_id, str_partner_id, ed_interest.getText().toString().trim(), "1");
                        altDialog.dismiss();
                    }
                });
                altDialog.show();*/


            }

        } else if (str_type.equalsIgnoreCase("shortlist")) {
            if (!strFrom.equalsIgnoreCase("shortlist")) {
                strResp1 = shortListCall(str_user_id, str_partner_name, str_partner_id);
            } else {
                strResp1 = shortListRemoveCall(str_user_id, str_partner_id);
            }


        } else if (str_type.equalsIgnoreCase("view_profile")) {

            viewProfileCall(str_user_id, str_partner_name, str_partner_id);
            Log.e("PPP","hello view profile");

        }

        /*if(strFrom.equalsIgnoreCase("shortlist"))
        {
            strResp = shortListCall(str_user_id, str_id);
        }*/

        return strResp1;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
    }


    public String sendInterestCall(final String str_my_id, final String str_partner_id, final String str_interest_msg, final String status) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "interest.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp :int " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                Toast.makeText(ctx, "Interest sent", Toast.LENGTH_SHORT).show();
                              /*  startActivity(new Intent(Matches.this,Matches.class));
                                finish();*/
                                Log.i("HH", "partner_id->" + str_partner_id);
                                Log.i("HH", "posi->" + posi);
                                strResp = str_partner_id;
                             /*   if(getFragmentRefreshListener()!=null){
                                    if(posi==1){
                                        NewMatchesFragment.mySwipeRefresh.setRefreshing(true);
                                        getFragmentRefreshListener().onRefresh();
                                    }

                                }*/

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        strResp = "failure";
                        //Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
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


        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

        return str_partner_id;
    }

    public String shortListCall(final String str_my_id, String str_partner_name, final String str_partner_id) {
        final ProgressDialog loading = ProgressDialog.show(ctx, "Shortlisting " + str_partner_name, "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "shortlist_user.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(ctx, "Shortlisted!!", Toast.LENGTH_SHORT).show();
                                // strResp = "backcolor";
                                strResp = "success";
                                loading.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        strResp = "failure";
                        // Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
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

                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);


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

        return strResp;
    }

    public String shortListRemoveCall(final String str_my_id, final String str_partner_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "shortlist_remove.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(ctx, "Removed!!", Toast.LENGTH_SHORT).show();
                                strResp = "success";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        strResp = "failure";
                        //Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
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

                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);


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

        return strResp;
    }

    // rest call to view other profiles so that it can appear in viewd my profile for other user
    public String viewProfileCall(final String str_my_id, final String str_partner_name, final String str_partner_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "viewprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONObject responseJsonObj = jsobj.getJSONObject("Response");
                                String str_sent_interest = responseJsonObj.getString("interested");

                                Log.e("view_profile", "success");
                                Intent i = new Intent(Matches.this,DailyRecomViewProfie.class);
                                i.putExtra("str_from", "my matches");
                                i.putExtra("user_id", str_partner_id);
                                i.putExtra("user_name", str_partner_name);
                                i.putExtra("sent_int", str_sent_interest);
                                startActivityForResult(i,2);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
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
                Log.e("v_from", str_my_id);
                Log.e("v_to", str_partner_id);
                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);


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

        return strResp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
             message=data.getStringExtra("MESSAGE");

            if(message!=null){
                Log.i("HH", "message : " + message);
                Log.i("HH", "posi : " + posi);
                if(posi==1){
                    if(getFragmentRefreshListener()!=null){
                     // NewMatchesFragment.mySwipeRefresh.setRefreshing(true);
                        getFragmentRefreshListener().onRefresh();

                    }
                }
                if(posi==0){
                    if(getFragmentRefreshListener()!=null){
                     // MatchesFragment.mySwipeRefresh.setRefreshing(true);
                        getFragmentRefreshListener().onRefresh();

                    }
                }
                if(posi==2){
                    if(getFragmentRefreshListener()!=null){
                       //ShortListFragment.mySwipeRefresh.setRefreshing(true);
                        getFragmentRefreshListener().onRefresh();

                    }
                }
                if(posi==3){
                    if(getFragmentRefreshListener()!=null){
                     //ViewMyProfileFragment.mySwipeRefresh.setRefreshing(true);
                        getFragmentRefreshListener().onRefresh();

                    }
                }
                if(posi==4){
                    if(getFragmentRefreshListener()!=null){
                       //ShortListedMeFragment.mySwipeRefresh.setRefreshing(true);
                        getFragmentRefreshListener().onRefresh();

                    }
                }

            }
            else{
                Log.i("HH", "msg is null ");
            }

            //textView1.setText(message);
        }
    }

    public String rateNowCall(final String str_my_id, final String str_rating) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "rating.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(Matches.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
                                Log.i("HH", "Rating : " + str_rating);
                                str_rate=str_rating;
                                 SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                Log.i("HH", "Rating : " + str_rating);
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

                        //Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
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

        return strResp;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;

    public interface FragmentRefreshListener{
        void onRefresh();
    }
}
