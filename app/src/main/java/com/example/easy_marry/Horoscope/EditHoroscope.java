package com.example.easy_marry.Horoscope;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
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
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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
import com.example.easy_marry.Adapter.CustomViewHoroAdapter;
import com.example.easy_marry.Adapter.Custom_GridView_Adapter;
import com.example.easy_marry.Adapter.GridGalleryAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.DailyRecommList;
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Feedback;
import com.example.easy_marry.MailInboxTabs.MailboxNewTabs;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.QuickSearch;
import com.example.easy_marry.R;
import com.example.easy_marry.Rounded_Imageview;
import com.example.easy_marry.Settings;
import com.example.easy_marry.TermsAndConditions;
import com.example.easy_marry.customgallery.GalleryAlbumActivity;
import com.example.easy_marry.genericclasses.FileUtils;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by android2 on 16/6/16.
 */
public class EditHoroscope extends Activity implements View.OnClickListener {

    String str_span = "Email your horoscope to horoscope@easy-marry.com or send it to any of our offices mentioning your Matrimony ID";
    TextView txt_span;
    Button btn_rate;
    DrawerLayout myTabsDrawer;
    LinearLayout ll_parent_menu, ll_my_profile;
    LinearLayout myTabsSliderLayout, matchesLayout, mailboxLayout, dailyrecomLayout, editProLayout, upgradeLayout, settingsLayout, feedbackLayout, searchLayout, addhoroLAyout;
    private ViewPager viewPager;
    TextView txt_mymatches, txt_mailbox, txt_dailyrecom, txt_edit_profile, txt_upgrade_acc, txt_settings, txt_feedback, txt_search, txt_add_horo;
    ImageView img_menu, img_messenger, img_mymatches_grey, img_mailbox_grey, img_dailyrecom_grey, img_editprof_grey, img_upgradeacc_grey, img_settings_grey, img_feedback_grey, img_search_grey, img_horo_grey;
    Rounded_Imageview img_user_image;
    //  ImageView img_edit_my_profile;
    Context ctx;
    ImageLoader imgLoader;
    ProgressBar pg_profile_comp_level;
    TextView txt_level_percent, txt_name, txt_id, txt_terms;
    GeneralData gD;
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id, str_rate, mCurrentPhotoPath = "",
            strMultipleImagePath = "", strMulImages, str_edit_from;
    Button btn_choose_file, btn_add_horoscope;
    boolean isImageUpload = false;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 3;
    ArrayList<File> AlFileList;
    ArrayList<String> AlFileName;
    GridView gV;
    Custom_GridView_Adapter cGAdapter;
    TextView txt_title_txt,txt_horo_images_error;
    ArrayList<ImageItem> alBitmap;
    ArrayList<ImageItem> alDeleteBmap;
    String str_horo_id, str_horo_images;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };
    String[] strGalleryURL;
    ArrayList<String> alGalleryPath;
    private GridGalleryAdapter gridAdapter;
    CustomViewHoroAdapter cGAdapter1;
    //  ArrayList<ImageItem> alImage;
    Thread thread;
    IntentFilter filter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_horoscope_new_edit);
        txt_span = (TextView) findViewById(R.id.txt_span3);
        ctx = this;
        gD = new GeneralData(ctx);
        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        str_rate = gD.prefs.getString("Rating", null);
        str_horo_id = gD.prefs.getString("horo_id", null);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        Log.e("HOR", "horo id-"+str_horo_id);
        // rate btn
        txt_title_txt = (TextView) findViewById(R.id.txt_title);
        btn_add_horoscope = (Button) findViewById(R.id.btn_add_horo);
        gV = (GridView) findViewById(R.id.gridview);
        btn_choose_file = (Button) findViewById(R.id.btn_choose_file);
        txt_terms = (TextView) findViewById(R.id.txt_terms);
        btn_rate = (Button) findViewById(R.id.btn_rate);
        // img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);
//toolbar contents
        img_user_image = (Rounded_Imageview) findViewById(R.id.user_image);
        img_menu = (ImageView) findViewById(R.id.menu);
        myTabsDrawer = (DrawerLayout) findViewById(R.id.my_tabs_drawer_layout);
        myTabsSliderLayout = (LinearLayout) findViewById(R.id.llayslider);
        txt_horo_images_error=(TextView)findViewById(R.id.txt_no_images);
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
        ll_my_profile = (LinearLayout) findViewById(R.id.ll_my_profile);
        //add horo
        img_horo_grey = (ImageView) findViewById(R.id.img_add_horo_grey);
        txt_add_horo = (TextView) findViewById(R.id.txt_add_horo);
        addhoroLAyout = (LinearLayout) findViewById(R.id.add_horo);

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTabsDrawer.openDrawer(myTabsSliderLayout);
            }
        });
      /*  img_edit_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddHoroscope.this, EditProfile.class));
            }
        });*/
        ll_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditHoroscope.this, DailyRecomViewProfie.class);
                i.putExtra("str_from", "header");
                startActivity(i);
            }
        });
        SpannableString spannableString1 = new SpannableString(str_span);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#81ccfe")), 23, 48, 0);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 97, 110, 0);
        txt_span.setText(spannableString1);
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
                startActivity(new Intent(EditHoroscope.this, TermsAndConditions.class));
            }
        });

        imgLoader = new ImageLoader(ctx);

        pg_profile_comp_level = (ProgressBar) findViewById(R.id.pg_profile_complete_level);
        txt_level_percent = (TextView) findViewById(R.id.txt_level_percent);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_id = (TextView) findViewById(R.id.txt_id);


        if (str_image != null && str_name != null && str_easy_marry_id != null && str_prof_compl_level != null) {

            imgLoader.DisplayImage(str_image, img_user_image);

          /*  Picasso.with(ctx)
                    .load(str_image)
                    .placeholder(R.drawable.default_use)
                    .into(img_user_image);*/


            txt_name.setText(str_name);
            txt_id.setText(str_easy_marry_id);
            Log.i("ND", "PLevel : " + str_prof_compl_level);
            txt_level_percent.setText(str_prof_compl_level + "%");
            pg_profile_comp_level.setProgress(Integer.parseInt(str_prof_compl_level));
        }


        /*Background_confirmation bc= new Background_confirmation();
        bc.execute();*/

       // viewHoroscope();

       /* gV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageItem img = alImage.get(position);
                alDeleteBmap.add(img);
                cGAdapter1.aBtmap.remove(position);
                cGAdapter1.notifyDataSetChanged();

                if (alImage.size() == 0) {
                    //edttext empty
                }


            }
        });*/


        btn_choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImageUpload = true;

                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.custom_dialog_box);
                //  dialog.setTitle("Alert Dialog View");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.btnChoosePath)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(EditHoroscope.this, GalleryAlbumActivity.class);
                                startActivity(i);
                                      /*  Intent i = new Intent(ProviderAfterLogin.this, AndroidCustomGalleryActivity.class);
                                        startActivity(i);*/
                                dialog.dismiss();

                            }
                        });
                dialog.findViewById(R.id.btnTakePhoto)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                takePicture();
                                dialog.dismiss();
                            }
                        });

                // show dialog on screen
                dialog.show();
            }

        });
        btn_add_horoscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

                if (isConnected) {
                    Log.i("LK", "connected");
                strMulImages = mCurrentPhotoPath;
                if (alBitmap.size() > 0) {
                    ArrayList<ImageItem> union = new ArrayList();

                    alBitmap.removeAll(new HashSet(alDeleteBmap));

                    Log.i("MYND", "alBitmap.size() : " + alBitmap.size());


                    strMulImages = "";

                    for (int i = 0; i < alBitmap.size(); i++) {
                        strMulImages += alBitmap.get(i).getImage() + ",";

                    }

                    if (strMulImages.contains(",")) {
                        strMulImages = strMulImages.substring(0, strMulImages.length() - 1);
                    }

                    Log.i("MYND", "IF Values : " + strMulImages);


                   // Toast.makeText(EditHoroscope.this, strMulImages, Toast.LENGTH_SHORT).show();

                    uploadEditImage();
                }
            }
            else{
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
            }
        });
        gV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("PP", String.valueOf(alBitmap.get(position)));
                //  Log.e("PP", String.valueOf(alBitmap.get(position)));
                ImageItem img = alBitmap.get(position);
                alDeleteBmap.add(img);
                cGAdapter.aBtmap.remove(position);
                cGAdapter.notifyDataSetChanged();

                if (alBitmap.size() == 0) {
                    //edttext empty
                }
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
                    stars.getDrawable(0).setColorFilter(Color.parseColor("#639639"), PorterDuff.Mode.SRC_ATOP);
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
                            rateNowCall(str_user_id, String.valueOf(ratingBar.getRating()));
                            altDialog.dismiss();
                        }
                    });
                    altDialog.show();
                } else {
                    Toast.makeText(EditHoroscope.this, "You have already rated...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //set the color for already selected menu
        ll_parent_menu = (LinearLayout) findViewById(R.id.ll_parent_menu);

        View vv = ll_parent_menu.getChildAt(gD.nChildPostion);

        if (vv instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) vv;
            ImageView imV = (ImageView) ll.getChildAt(0);
            imV.setBackgroundResource(R.drawable.add_horo_orange);
            TextView tvName = (TextView) ll.getChildAt(1);
            tvName.setTextColor(Color.parseColor("#fb7b09"));
        }
    }


    private void updateUI(final Intent intent) {
        final String strVal = intent.getStringExtra("data");
        Log.i("VAL", "Path : " + strVal);

        strMultipleImagePath = "";

        EditHoroscope.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                alBitmap = new ArrayList<ImageItem>();
                alDeleteBmap = new ArrayList<ImageItem>();

                //   ed_disp_images.setText(strVal.substring(1, strVal.length() - 1));
                strMultipleImagePath = strVal;
                Log.i("MYND", strMultipleImagePath);

                String[] strImagesAr = strVal.substring(1, strVal.length() - 1).split(",");

                for (int i = 0; i < strImagesAr.length; i++) {
                    File fff = new File(strImagesAr[i]);
                    try {
                        ImageItem ImgI = new ImageItem();
                        ImgI.setBmpImage(getThumbnail(Uri.parse("file://" + fff.toString())));
                        ImgI.setImage(strImagesAr[i]);
                        alBitmap.add(ImgI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                cGAdapter = new Custom_GridView_Adapter(ctx, alBitmap);
                gV.setAdapter(cGAdapter);
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

                startActivity(new Intent(EditHoroscope.this, Matches.class));
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


                Intent i = new Intent(EditHoroscope.this, MailboxNewTabs.class);
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
                startActivity(new Intent(EditHoroscope.this, DailyRecommList.class));


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

                startActivity(new Intent(EditHoroscope.this, EditProfile.class));

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

                startActivity(new Intent(EditHoroscope.this, MembershipTabs.class));
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

                startActivity(new Intent(EditHoroscope.this, Settings.class));

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
                startActivity(new Intent(EditHoroscope.this, Feedback.class));

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

                startActivity(new Intent(EditHoroscope.this, QuickSearch.class));

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
                startActivity(new Intent(EditHoroscope.this, AddHoroscope.class));

                break;
        }
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
                                Toast.makeText(EditHoroscope.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onResume() {
        try {

            super.onResume();
            registerReceiver(broadcastReceiver, new IntentFilter("com.aryvart.multiselect"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("MMM", "IOException");
            }
            // Continue onlactiveTakePhotoy if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //  ed_disp_images.setText(mCurrentPhotoPath);
        Log.i("capture paht:", mCurrentPhotoPath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);


            switch (requestCode) {

                case PICK_IMAGE:
                    mCurrentPhotoPath = "";
                    Log.i("HH", "resultCode PICK_IMAGE case 1: " + resultCode);

                    try {
                        if (resultCode == RESULT_OK) {

                            AlFileList = new ArrayList<File>();
                            AlFileName = new ArrayList<String>();


                            Uri uri = data.getData();
                            //If uri null its multiple file/image choosen
                            if (uri == null) {
                                ClipData clipData = data.getClipData();

                                Log.d("HH", "Uri: " + resultCode);

                                if (clipData != null) {
                                    try {
                                        for (int i = 0; i < clipData.getItemCount(); i++) {
                                            String displayName = null;
                                            if (clipData.getItemCount() <= 10) {
                                                //      Toast.makeText(ProviderAfterLogin.this, "count" + clipData.getItemCount(), Toast.LENGTH_SHORT).show();
                                                ClipData.Item item = clipData.getItemAt(i);
                                                Uri uriVal = item.getUri();

                                                String uriString = uriVal.toString();
                                                File myFile = new File(uriString);

                                                AlFileList.add(myFile);

                                                if (uriString.startsWith("content://")) {
                                                    Cursor cursor = null;
                                                    try {
                                                        cursor = ctx.getContentResolver().query(uriVal, null, null, null, null);
                                                        if (cursor != null && cursor.moveToFirst()) {
                                                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                                            File myFileVal = new File(uriString);
                                                            AlFileName.add(uriString);
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    } finally {
                                                        cursor.close();
                                                    }
                                                } else if (uriString.startsWith("file://")) {
                                                    displayName = myFile.getAbsolutePath();
                                                    AlFileName.add(displayName);
                                                }

                                                //  edLicenseUpload.setText(AlFileName.toString().substring(1, AlFileName.toString().length() - 1));

                                                Log.i("HH", "AlFileName" + AlFileName.toString());
                                                Log.i("HH", "AlFileName" + AlFileName);
                                                Log.e("HH", "FileImages's Path:" + myFile.getAbsolutePath());
                                                Log.e("HH", "Images's Path:" + uriString);
                                            } else {
                                                Toast.makeText(EditHoroscope.this, "u reached max 10", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                strMultipleImagePath = AlFileName.toString();
                                Log.i("file path:", strMultipleImagePath);
                            } else {
                                try {
                                    String uriString = uri.toString();
                                    File myFile = new File(uriString);
                                    String path = myFile.getAbsolutePath();

                                    String displayName = null;

                                    AlFileList.add(myFile);

                                    if (uriString.startsWith("content://")) {
                                        Cursor cursor = null;
                                        try {
                                            cursor = ctx.getContentResolver().query(uri, null, null, null, null);
                                            if (cursor != null && cursor.moveToFirst()) {
                                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                                                //  File myFileVal = new File(uriString);
                                                AlFileName.add(uriString);

                                                //  AlFileName.add(uriString);
                                            }
                                        } finally {
                                            cursor.close();
                                        }
                                    } else if (uriString.startsWith("file://")) {
                                        displayName = myFile.getAbsolutePath();
                                        AlFileName.add(displayName);
                                    }

                                    strMultipleImagePath = myFile.getAbsolutePath();
                                    Log.i("file path:", strMultipleImagePath);
                                    // edLicenseUpload.setText(strLicenseFilepath.substring(1, strLicenseFilepath.length() - 1));
                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:

                    Log.i("HH", "resultCode case 2: " + resultCode);

                    if (resultCode == RESULT_OK) {

                        //  else if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {

                        // Get the Uri of the selected file
                        Uri uri = data.getData();
                        Log.d("HH", "File Uri: " + uri.toString());
                        // Get the path
                        String path = null;
                        try {
                            path = FileUtils.getPath(this, uri);


                            strMultipleImagePath = path;

                            //edLicenseUpload.setText(stripExtension(strLicenseFilepath));

                            Log.d("HH", "File Path: " + strMultipleImagePath);

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        // Get the file instance
                        // File file = new File(path);
                        // Initiate the upload
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE:

                    strMulImages = "";

                 /*     FileInputStream fis;
                    File file = null;
                    file = new File(mCurrentPhotoPath);
//
                  fis = new FileInputStream(file);
                    str_capture_file_path = file.getAbsolutePath();
                    fis.close();
*/
                    // Log.i("SRV", "str_capture_file_path : " + file.getAbsolutePath());


                  /*  ImageItem ImgI = new ImageItem();
                    ImgI.setBmpImage(getThumbnail(Uri.parse(file.toString())));
                    ImgI.setImage("file://" + mCurrentPhotoPath);
                    alBitmap.add(ImgI);


                    cGAdapter = new Custom_GridView_Adapter(context, alBitmap);
                    gV.setAdapter(cGAdapter);

*/


                    Log.i("SRV", "str_capture_file_path : " + mCurrentPhotoPath);

                    alBitmap = new ArrayList<ImageItem>();
                    alDeleteBmap = new ArrayList<ImageItem>();


                    File files = new File("file://" + mCurrentPhotoPath);

                    Log.i("SRV", "files : " + files.toString());

                    ImageItem ImgI = new ImageItem();
                    ImgI.setBmpImage(getThumbnail(Uri.parse(files.toString())));
                    ImgI.setImage(mCurrentPhotoPath);
                    alBitmap.add(ImgI);


                    cGAdapter = new Custom_GridView_Adapter(ctx, alBitmap);
                    gV.setAdapter(cGAdapter);


                    //strMultipleImagePath = strVal;
                    break;

                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 600) ? (originalSize / 600) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadEditImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "horoscope.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            Log.i("HH", "strResp : " + s);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(s);

                            Log.i("HH", "strResp : " + s);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                JSONObject jsobj_resp = jsobj.getJSONObject("Response");
                                String str_horoId=jsobj_resp.getString("horoscopeid");
                                String str_horo_images=jsobj_resp.getString("horoscope_image");
                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("horo_id", str_horoId);
                                prefEdit.putString("horo_images", str_horo_images);
                                prefEdit.commit();
                                // str_horo_id=jsobj_resp.getString("horoscopeid");
                                loading.dismiss();
                                Toast.makeText(EditHoroscope.this, "Horoscope Added!!!", Toast.LENGTH_SHORT).show();
                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                loading.dismiss();
                                Toast.makeText(EditHoroscope.this, "Horoscope Added Failed!!!", Toast.LENGTH_SHORT).show();
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
                //String image = getStringImage(bmp);

                //Getting Image Name


                //Creating parameters
                String str_get_image = "";
                for (int j = 0; j < alBitmap.size(); j++) {
                    Log.i("MYND", "Value : " + alBitmap.get(j).getBmpImage());

                    str_get_image += getStringImage(alBitmap.get(j).getBmpImage()) + ",";
                }

                if (str_get_image.contains(",")) {
                    str_get_image = str_get_image.substring(0, str_get_image.length() - 1);
                }

                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                Log.e("N_horo", "userid" + str_user_id);
                Log.e("N_horo", "image" + str_get_image);
                Log.e("N_horo", "horoscopeid" + str_horo_id);

                params.put("userid", str_user_id);
                params.put("horoscopeid", str_horo_id);
                params.put("image", str_get_image);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    //ArrayList<ImageItem>
    private  void viewHoroscope() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "horoscope_image.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            Log.i("HH", "strResp : " + s);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(s);

                            Log.i("HH", "strResp : " + s);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                // String[] strImagesAr = strGallery.split(",");

                                JSONObject jsobj_resp = jsobj.getJSONObject("Response");
                                str_horo_images = jsobj_resp.getString("horoscope_image");
                                strGalleryURL = jsobj_resp.getString("horoscope_image").split(",");
                                alBitmap = new ArrayList<ImageItem>();
                                alDeleteBmap = new ArrayList<ImageItem>();

                                for (int i = 0; i < strGalleryURL.length; i++) {
                                    if (strGalleryURL[i].length() > 0) {

                                        try {

                                            String strGalPath = "";
                                            ImageItem ImgI = new ImageItem();
                                            Log.i("JJ", "images-" + strGalleryURL[i]);

                                            strGalPath = GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[i];
                                            // alGalleryPath.add(GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[i]);
                                            ImgI.setImage(strGalleryURL[i]);

                                            Log.i("JJ", "strGalPath : " + strGalPath);
                                            ImgI.setBmpImage(imgLoader.getBitmap(strGalPath));
                                            Log.i("JJ", "albitmap : " + alBitmap);
                                            alBitmap.add(ImgI);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                cGAdapter = new Custom_GridView_Adapter(ctx, alBitmap);
                                gV.setAdapter(cGAdapter);


                               /* JSONObject jsobj_resp = jsobj.getJSONObject("Response");
                                str_horo_images = jsobj_resp.getString("horoscope_image");
                                Log.e("GH", "horo images-->" + str_horo_images);

                                try {
                                    strGalleryURL = jsobj_resp.getString("horoscope_image").split(",");
                                    Log.i("SSS", "galleryimg : " + strGalleryURL);
                                    alGalleryPath = new ArrayList<String>();
                                    for (int j = 0; j < strGalleryURL.length; j++) {
                                        alGalleryPath.add(GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[j]);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                alBitmap = new ArrayList<ImageItem>();
                                for (int k = 0; k < alGalleryPath.size(); k++) {
                                    Log.e("OO", "images--" + String.valueOf(strGalleryURL[k]));
                                    try {
                                        URL url = new URL(alGalleryPath.get(k));
                                        final String strImageURl = alGalleryPath.get(k);

                                        ImageItem imageItems = new ImageItem();
                                        imageItems.setImage(strImageURl);
                                        alBitmap.add(imageItems);
                                        Log.e("OO", "images--" + String.valueOf(alBitmap));
                                        cGAdapter1 = new CustomViewHoroAdapter(ctx, alBitmap);
                                        gV.setAdapter(cGAdapter1);


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
*/


                                loading.dismiss();
                                //Toast.makeText(AddHoroscope.this, "Horoscope Added!!!", Toast.LENGTH_SHORT).show();
                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                loading.dismiss();
                                //Toast.makeText(AddHoroscope.this, "Horoscope Added Failed!!!", Toast.LENGTH_SHORT).show();
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
                //String image = getStringImage(bmp);

                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                Log.e("N_horo", "userid" + str_user_id);
                params.put("userid", str_user_id);

                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        //return alBitmap;
    }

    public void viewHoroNew(){
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP+"horoscope_image.php",
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

                                JSONObject jsobj_resp = jsobj.getJSONObject("Response");
                                str_horo_images = jsobj_resp.getString("horoscope_image");
                                strGalleryURL = jsobj_resp.getString("horoscope_image").split(",");
                                alBitmap = new ArrayList<ImageItem>();
                                alDeleteBmap = new ArrayList<ImageItem>();

                                for (int i = 0; i < strGalleryURL.length; i++) {
                                    if (strGalleryURL[i].length() > 0) {

                                        try {

                                            String strGalPath = "";
                                            ImageItem ImgI = new ImageItem();
                                            Log.i("JJ", "images-" + strGalleryURL[i]);

                                            strGalPath = GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[i];
                                            // alGalleryPath.add(GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[i]);
                                            ImgI.setImage(strGalleryURL[i]);

                                            Log.i("JJ", "strGalPath : " + strGalPath);
                                            ImgI.setBmpImage(imgLoader.getBitmap(strGalPath));
                                            Log.i("JJ", "albitmap : " + alBitmap);
                                            alBitmap.add(ImgI);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                cGAdapter = new Custom_GridView_Adapter(ctx, alBitmap);
                                gV.setAdapter(cGAdapter);
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

                Log.e("N_horo", "userid" + str_user_id);
                params.put("userid", str_user_id);

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
                gV.setVisibility(View.VISIBLE);
                txt_horo_images_error.setVisibility(View.GONE);
                Log.i("LK", "connected");
                if(str_horo_id!=null) {
                    Log.e("NN--horo", str_horo_id);
                    Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                viewHoroNew();
                                //Your code goes here
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
                else{
                    Log.e("NN--horo", "horo is empty");
                    str_horo_id = "";
                }

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

            } else {
                gV.setVisibility(View.GONE);
                txt_horo_images_error.setVisibility(View.VISIBLE);
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
        }
    };
}
