package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.easy_marry.Adapter.GridAdapter;
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.EditProfiles.EditProfile;
import com.example.easy_marry.Horoscope.ViewHoroscope;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.MailInboxTabs.MailInboxTabs;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.swibetabs.Matches;
import com.example.easy_marry.swibetabs.NewMatchesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 18/6/16.
 */
public class DailyRecomViewProfie extends Activity {
    TextView txt_phone_num;
    String str_from_val, str_id_search_json;
    Context context;
    String str_other_users_id, str_other_user_name, str_my_id, str_mob_num, str_cont_num, str_par_mob_num, str_conv_time, str_name, str_easy_marry_id;
    ImageView img_my_profile_banner;
    GeneralData gD;
    ImageLoader imgLoader;
    ImageView img_edit_my_profile, img_back, img_mine, img_partner;
    String interest_msg = "", str_membership, str_image, str_sent_interest, str_rate, str_myname, str_partner_id, str_id_seach_int_key;
    LinearLayout ll_int_yes, ll_int_no, ll_skip, ll_yes, ll_daily_recom, ll_view_profile, ll_matched_pref, ll_uchildr, ll_pchildr;
    TextView txt_own_words, txt_per_user_name, txt_per_gender, txt_per_age, txt_per_height, txt_per_weight, txt_per_marital_status, txt_per_mother_tongue,
            txt_per_phy_stat, txt_per_body_type, txt_per_profile_created, txt_per_eating, txt_per_drinking, txt_per_smoking,
            txt_per_religion, txt_per_gothram, txt_caste, txt_sub_caste, txt_zodiac, txt_star, txt_dhosham,
            txt_qualif, txt_qualif_det, txt_occupation, txt_empl_in, txt_annual_income,
            txt_country, txt_state, txt_city, txt_citizen,
            txt_fam_val, txt_fam_type, txt_fam_status, txt_father_occup, txt_mother_occup, txt_hobbie, txt_dailyrecomm_child,
            txt_groom_age, txt_groom_height, txt_groom_weight, txt_groom_marital_status, txt_groom_child, txt_groom_mother_tongue,
            txt_groom_phy_stat, txt_groom_body_type, txt_groom_profile_created, txt_groom_eating, txt_groom_drinking, txt_groom_smoking,
            txt_groom_religion, txt_groom_gothram, txt_groom_caste, txt_groom_sub_caste, txt_groom_zodiac, txt_groom_star, txt_groom_dhosham,
            txt_groom_qualif, txt_groom_qualif_det, txt_groom_occupation, txt_groom_empl_in, txt_groom_annual_income,
            txt_groom_country, txt_groom_state, txt_groom_city, txt_groom_citizen,
            txt_groom_fam_val, txt_groom_fam_type, txt_groom_fam_status, txt_groom_father_occup, txt_groom_mother_occup, txt_groom_hobbie,
            txt_daily_recomm_header_name, txt_daily_recomm_header_Id, txt_matched_pref, txt_toolbar_header_name, txt_view_horo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.daily_recom_view_profile);
        gD = new GeneralData(context);
        str_my_id = gD.prefs.getString("userid", null);


        imgLoader = new ImageLoader(context);
        txt_toolbar_header_name = (TextView) findViewById(R.id.txt_toolbar_title);

        ll_matched_pref = (LinearLayout) findViewById(R.id.ll_matches_pref);
        txt_matched_pref = (TextView) findViewById(R.id.txt_matches_pref);

        ll_uchildr = (LinearLayout) findViewById(R.id.ll_uchild);
        ll_pchildr = (LinearLayout) findViewById(R.id.ll_pchild);

        txt_view_horo = (TextView) findViewById(R.id.txt_view_horo);

        ll_int_yes = (LinearLayout) findViewById(R.id.ll_int_yes);
        ll_int_no = (LinearLayout) findViewById(R.id.ll_int_no);
        ll_skip = (LinearLayout) findViewById(R.id.ll_skip);
        ll_yes = (LinearLayout) findViewById(R.id.ll_yes);
        ll_daily_recom = (LinearLayout) findViewById(R.id.ll_lay_daily_recom);
        ll_view_profile = (LinearLayout) findViewById(R.id.ll_lay_viewprof);

        img_mine = (ImageView) findViewById(R.id.img_mine);
        img_partner = (ImageView) findViewById(R.id.img_partner);

        txt_daily_recomm_header_name = (TextView) findViewById(R.id.txt_daily_recomm_header_name);
        txt_daily_recomm_header_Id = (TextView) findViewById(R.id.txt_daily_recomm_header_matri_Id);
        txt_own_words = (TextView) findViewById(R.id.txt_own_words);

//Basic Pref(PErsonnal)
        txt_per_user_name = (TextView) findViewById(R.id.txt_daily_recom_name);
        txt_per_gender = (TextView) findViewById(R.id.txt_daily_recom_gender);
        txt_per_age = (TextView) findViewById(R.id.txt_daily_recom_age);

        txt_per_height = (TextView) findViewById(R.id.txt_daily_recom_height);
        txt_per_weight = (TextView) findViewById(R.id.txt_daily_recom_weight);
        txt_per_marital_status = (TextView) findViewById(R.id.txt_daily_recom_martial_status);
        txt_dailyrecomm_child = (TextView) findViewById(R.id.txt_daily_recom_child);

        txt_per_mother_tongue = (TextView) findViewById(R.id.txt_daily_recom_mother_ton);
        txt_per_phy_stat = (TextView) findViewById(R.id.txt_daily_recom_physical_stat);
        txt_per_body_type = (TextView) findViewById(R.id.txt_daily_recom_body_type);

        txt_per_profile_created = (TextView) findViewById(R.id.txt_daily_recom_profile_create);
        txt_per_eating = (TextView) findViewById(R.id.txt_daily_recom_eating_habits);
        txt_per_drinking = (TextView) findViewById(R.id.txt_daily_recom_drinking);
        txt_per_smoking = (TextView) findViewById(R.id.txt_daily_recom_smoke);


        //Partner Pref(Groom)

        txt_groom_age = (TextView) findViewById(R.id.txt_daily_recom_groom_age);

        txt_groom_height = (TextView) findViewById(R.id.txt_daily_recom_groom_height);
        txt_groom_weight = (TextView) findViewById(R.id.txt_daily_recom_groom_weight);
        txt_groom_marital_status = (TextView) findViewById(R.id.txt_daily_recom_groom_martial_status);
        txt_groom_child = (TextView) findViewById(R.id.txt_daily_recom_groom_child);

        txt_groom_mother_tongue = (TextView) findViewById(R.id.txt_daily_recom_groom_mother_ton);
        txt_groom_phy_stat = (TextView) findViewById(R.id.txt_daily_recom_groom_physical_stat);
        txt_groom_body_type = (TextView) findViewById(R.id.txt_daily_recom_groom_body_type);

        // txt_groom_profile_created = (TextView) findViewById(R.id.txt_daily_recom_groom_profile_create);
        txt_groom_eating = (TextView) findViewById(R.id.txt_daily_recom_groom_eating_habits);
        txt_groom_drinking = (TextView) findViewById(R.id.txt_daily_recom_groom_drinking);
        txt_groom_smoking = (TextView) findViewById(R.id.txt_daily_recom_groom_smoke);


//religious(Personnal)

        txt_per_religion = (TextView) findViewById(R.id.txt_daily_recom_religion);
        txt_per_gothram = (TextView) findViewById(R.id.txt_daily_recom_gothram);
        txt_caste = (TextView) findViewById(R.id.txt_daily_recom_caste);
        txt_sub_caste = (TextView) findViewById(R.id.txt_daily_recom_sub_caste);
        txt_zodiac = (TextView) findViewById(R.id.txt_daily_recom_zodiac);
        txt_star = (TextView) findViewById(R.id.txt_daily_recom_star);
        txt_dhosham = (TextView) findViewById(R.id.txt_daily_recom_dhosham);


//religious(Groom)

        txt_groom_religion = (TextView) findViewById(R.id.txt_daily_recom_groom_religion);
        txt_groom_gothram = (TextView) findViewById(R.id.txt_daily_recom_groom_gothram);
        txt_groom_caste = (TextView) findViewById(R.id.txt_daily_recom_groom_caste);
        txt_groom_sub_caste = (TextView) findViewById(R.id.txt_daily_recom_groom_sub_caste);
        txt_groom_zodiac = (TextView) findViewById(R.id.txt_daily_recom_groom_zodiac);
        txt_groom_star = (TextView) findViewById(R.id.txt_daily_recom_groom_martial_star);
        txt_groom_dhosham = (TextView) findViewById(R.id.txt_daily_recom_groom_dhosham);

//education(Personnal)

        txt_qualif = (TextView) findViewById(R.id.txt_daily_recom_qualif);
        txt_qualif_det = (TextView) findViewById(R.id.txt_daily_recom_qualif_det);
        txt_occupation = (TextView) findViewById(R.id.txt_daily_recom_occupation);
        txt_empl_in = (TextView) findViewById(R.id.txt_daily_recom_empl_in);
        txt_annual_income = (TextView) findViewById(R.id.txt_daily_recom_annual_income);


        //education(Groom)

        txt_groom_qualif = (TextView) findViewById(R.id.txt_daily_recom_groom_qualif);
        //   txt_groom_qualif_det = (TextView) findViewById(R.id.txt_daily_recom_groom_qualif_det);
        txt_groom_occupation = (TextView) findViewById(R.id.txt_daily_recom_groom_occupation);
        txt_groom_annual_income = (TextView) findViewById(R.id.txt_daily_recom_groom_annual_income);


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


//hobbie and interest

        txt_hobbie = (TextView) findViewById(R.id.txt_daily_recom_hobbie);
        str_from_val = getIntent().getStringExtra("str_from");
        str_id_search_json = getIntent().getStringExtra("id_search_json");
        str_other_users_id = getIntent().getStringExtra("user_id");
        str_rate = gD.prefs.getString("Rating", null);
        str_myname = gD.prefs.getString("name", null);
        str_other_user_name = getIntent().getStringExtra("user_name");
        str_membership = gD.prefs.getString("memplan", null);
        str_image = gD.prefs.getString("profileimage", null);
        Log.i("HHN-1", "strResp : " + str_id_search_json);
        img_edit_my_profile = (ImageView) findViewById(R.id.img_edit_my_profile);
        img_my_profile_banner = (ImageView) findViewById(R.id.user_banner_img);
        img_back = (ImageView) findViewById(R.id.img_back);


        if (str_id_search_json != null) {
            idSearchProfileDetails(str_id_search_json, "partner_pref");
            txt_toolbar_header_name.setText("Profile");
            ll_daily_recom.setVisibility(View.GONE);
            ll_view_profile.setVisibility(View.VISIBLE);
            ll_matched_pref.setVisibility(View.VISIBLE);
        } else {

            if (str_from_val.equalsIgnoreCase("header")) {
                dashBoardMyMatchesRestCall(GeneralData.LOCAL_IP + "profile.php", "");
                txt_toolbar_header_name.setText("My Profile");
                ll_daily_recom.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.GONE);
                ll_matched_pref.setVisibility(View.GONE);
                // img_my_profile_banner.setBackgroundResource(R.drawable.my_profile_banner);
            } else if (str_from_val.equalsIgnoreCase("daily_recomm")) {
                dashBoardMyMatchesRestCall(GeneralData.LOCAL_IP + "viewprofile.php", "partner_pref");
                txt_toolbar_header_name.setText("Daily Recommendation");
                ll_daily_recom.setVisibility(View.VISIBLE);
                ll_view_profile.setVisibility(View.GONE);
                ll_matched_pref.setVisibility(View.VISIBLE);
                //img_my_profile_banner.setBackgroundResource(R.drawable.girl_banner);
            }

        }
        if (str_other_users_id != null || str_sent_interest != null) {
            if (str_from_val.equalsIgnoreCase("my matches")) {
                txt_toolbar_header_name.setText("Profile");
                dashBoardMyMatchesRestCall(GeneralData.LOCAL_IP + "viewprofile.php", "partner_pref");
                ll_daily_recom.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.VISIBLE);
                ll_matched_pref.setVisibility(View.VISIBLE);
                // img_my_profile_banner.setBackgroundResource(R.drawable.girl_banner);
            }
        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("MESSAGE", "");
                setResult(2, intent);
                finish();
                //myTabsDrawer.openDrawer(myTabsSliderLayout);
            }
        });


        txt_view_horo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str_from_val.equalsIgnoreCase("header")) {
                    Intent ii = new Intent(DailyRecomViewProfie.this, ViewHoroscope.class);
                    ii.putExtra("user_id", str_my_id);
                    startActivity(ii);
                } else if (str_id_search_json != null) {
                    Intent i = new Intent(DailyRecomViewProfie.this, ViewHoroscope.class);
                    i.putExtra("user_id", str_partner_id);
                    startActivity(i);
                } else {
                    Intent i = new Intent(DailyRecomViewProfie.this, ViewHoroscope.class);
                    i.putExtra("user_id", str_other_users_id);
                    startActivity(i);
                }

            }
        });
        txt_phone_num = (TextView) findViewById(R.id.txt_view_phone);
        txt_phone_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View itemView1;
                itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.view_phone_poup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);

                ImageView img_close_dialog = (ImageView) itemView1.findViewById(R.id.img_close_dialog);

                TextView txt_edit_contact = (TextView) itemView1.findViewById(R.id.txt_contact_det);
                TextView txt_edit_mobile = (TextView) itemView1.findViewById(R.id.txt_mobile_no);
                TextView txt_edit_cont_mum = (TextView) itemView1.findViewById(R.id.txt_cont_num);
                TextView txt_edit_par_mob = (TextView) itemView1.findViewById(R.id.txt_par_mobile_no);
                TextView txt_edit_conv_time = (TextView) itemView1.findViewById(R.id.txt_conv_time);

                txt_edit_contact.setText(str_name + "(" + str_easy_marry_id + ")");

             /*   // Define the blur effect radius
                float radius = txt_edit_mobile.getTextSize() / 5;

                // Initialize a new BlurMaskFilter instance
                BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
                // Set the TextView layer type
                txt_edit_mobile.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                // Finally, apply the blur effect on TextView text
                txt_edit_mobile.getPaint().setMaskFilter(filter);*/

                txt_edit_mobile.setText(str_mob_num);
                txt_edit_cont_mum.setText(str_cont_num);
                txt_edit_par_mob.setText(str_par_mob_num);
                txt_edit_conv_time.setText(str_conv_time);

                img_close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                    }
                });
                altDialog.show();

            }
        });

        if (str_from_val.equalsIgnoreCase("my matches")) {

            str_sent_interest = getIntent().getStringExtra("sent_int");
            if (str_sent_interest.equalsIgnoreCase("0")) {
                ll_daily_recom.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.VISIBLE);
            } else {
                ll_daily_recom.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.GONE);
            }
        }
        if (str_id_search_json != null) {

            Log.e("NN", "id_search_int_key->" + str_id_seach_int_key);
            if (str_id_seach_int_key.equalsIgnoreCase("0")) {
                ll_daily_recom.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.VISIBLE);
            } else {
                ll_daily_recom.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.GONE);
            }
            // str_id_seach_int_key

        }

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
                    interest_msg = "Hi " + str_name + ",I'm " + str_myname + " .Please accept my request";
                    ed_interest.setText(interest_msg);
                    ed_interest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DailyRecomViewProfie.this, "you can't edit this text as you are basic member", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);
                       /*interest_msg=ed_interest.getText().toString().trim();
                       ed_interest.setText(interest_msg);
*/

                }
                btn_sent_interest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (str_id_search_json != null) {
                            sendInterestCall(str_my_id, str_partner_id, ed_interest.getText().toString().trim(), "1");
                        } else {
                            sendInterestCall(str_my_id, str_other_users_id, ed_interest.getText().toString().trim(), "1");
                        }

                        altDialog.dismiss();

                    }
                });
                altDialog.show();


            }
        });
        ll_yes.setOnClickListener(new View.OnClickListener() {
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
                    interest_msg = "Hi " + str_name + ",I'm " + str_myname + " .Please accept my request";
                    ed_interest.setText(interest_msg);
                    ed_interest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DailyRecomViewProfie.this, "you can't edit this text as you are basic member", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);
                       /*interest_msg=ed_interest.getText().toString().trim();
                       ed_interest.setText(interest_msg);
*/

                }
                btn_sent_interest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendInterestCall(str_my_id, str_other_users_id, ed_interest.getText().toString().trim(), "1");
                        altDialog.dismiss();
                        finish();
                    }
                });
                altDialog.show();


            }
        });
        ll_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent i = new Intent(DailyRecomViewProfie.this, DailyRecommList.class);
                //i.putExtra("skip", "1");
                // startActivity(i);
                finish();
            }
        });
        ll_int_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dashBoardMyMatchesRestCall(String str_url, final String str_from) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        idSearchProfileDetails(response, str_from);
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
                Log.e("N_view", "from_id-" + str_my_id);
                Log.e("N_view", "to_id-" + str_other_users_id);
//username=sathish@ansjad.com&password=testing
                if (str_from_val.equalsIgnoreCase("header")) {
                    params.put("userid", str_my_id);
                } else {
                    // params.put("userid", str_other_users_id);

                    params.put("fromid", str_my_id);
                    params.put("toid", str_other_users_id);
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


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void idSearchProfileDetails(String idSearchResp, String from) {
        try {
            Log.i("HHN", "strResp : " + idSearchResp);
            JSONObject jsobj = new JSONObject(idSearchResp);


            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                JSONObject responseJsonObj = jsobj.getJSONObject("Response");

                String str_own_words = responseJsonObj.getString("mywords");

                String str_emid = responseJsonObj.getString("easymarryid");
                if (str_id_search_json != null) {
                    str_id_seach_int_key = responseJsonObj.getString("interested");
                }


                str_partner_id = responseJsonObj.getString("userid");


                str_easy_marry_id = str_emid;

                String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + responseJsonObj.getString("profileImage");
                imgLoader.DisplayImage(strImageURL, img_my_profile_banner);
                imgLoader.DisplayImage(strImageURL, img_partner);
                imgLoader.DisplayImage(str_image, img_mine);
                txt_own_words.setText(str_own_words);


                //My(Basic pref)

                JSONObject myPrefJsonObj = responseJsonObj.getJSONObject("My Preference");
                String Hobbie = myPrefJsonObj.getString("Hobbies and Interest");
                txt_hobbie.setText(Hobbie);
                JSONObject myBasicPrefJsonObj = myPrefJsonObj.getJSONObject("Basic info");
                String name = myBasicPrefJsonObj.getString("Name");
                String gender = myBasicPrefJsonObj.getString("Gender");
                String age = myBasicPrefJsonObj.getString("Age");
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

                str_name = name;
                if (from.equalsIgnoreCase("partner_pref")) {
                    String str_overall_match_count = responseJsonObj.getString("Overall Count");
                    String str_matched_count = responseJsonObj.getString("Matched Count");
                    Log.e("CH:", str_overall_match_count);
                    Log.e("CH:", str_matched_count);
                    Log.e("CH:", str_name);
                    txt_matched_pref.setText("Your profile matches with " + str_matched_count + "/" + str_overall_match_count + " " + name + " preference");

                }
                if (married_status.equalsIgnoreCase("unmarried") || married_status.equalsIgnoreCase("not available")) {
                    ll_uchildr.setVisibility(View.GONE);
                } else {
                    ll_uchildr.setVisibility(View.VISIBLE);
                    txt_dailyrecomm_child.setText(no_of_child);
                }


                txt_daily_recomm_header_name.setText(name);
                txt_daily_recomm_header_Id.setText(str_emid);
                txt_per_user_name.setText(name);
                txt_per_gender.setText(gender);
                txt_per_age.setText(age);
                txt_per_height.setText(height);
                txt_per_weight.setText(weight);
                txt_per_marital_status.setText(married_status);

                txt_per_mother_tongue.setText(mother_tongue);
                txt_per_phy_stat.setText(physical_status);
                txt_per_body_type.setText(body_type);
                txt_per_profile_created.setText(profile_created);
                txt_per_eating.setText(eating_habits);
                txt_per_drinking.setText(drinking_habits);
                txt_per_smoking.setText(smoking_habits);


                JSONObject myContactJsonObj = myPrefJsonObj.getJSONObject("Contact Details");


                String mob_num = myContactJsonObj.getString("Mobile Number");
                String cont_num = myContactJsonObj.getString("Contact Number");
                String par_mob_num = myContactJsonObj.getString("Parent Contact");
                String conv_time = myContactJsonObj.getString("Convenient Time to Call");
                str_mob_num = mob_num;
                str_cont_num = cont_num;
                str_par_mob_num = par_mob_num;
                str_conv_time = conv_time;


//partner(Basic pref)
                JSONObject partnerPrefJsonObj = responseJsonObj.getJSONObject("Partner Preference");

                JSONObject partnerBasicPrefJsonObj = partnerPrefJsonObj.getJSONObject("Basic info");

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


                if (partner_married_status.equalsIgnoreCase("unmarried") || partner_married_status.equalsIgnoreCase("not available")) {
                    ll_pchildr.setVisibility(View.GONE);
                } else {
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


                JSONObject myReligiousJsonObj = myPrefJsonObj.getJSONObject("Religious Information");

                String religion = myReligiousJsonObj.getString("religious");
                String gothram = myReligiousJsonObj.getString("Gothram");
                String caste = myReligiousJsonObj.getString("Caste");
                String sub_caste = myReligiousJsonObj.getString("Sub Caste");
                String zodiac_sign = myReligiousJsonObj.getString("Zodaic Sign");
                String star = myReligiousJsonObj.getString("Star");
                String dhosham = myReligiousJsonObj.getString("Dhosham");
                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
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
//partner religious info

                JSONObject partnerReligiousJsonObj = partnerPrefJsonObj.getJSONObject("Religious Information");

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
               // txt_groom_dhosham.setText(partner_dhosham);


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

                JSONObject myProfessionalJsonObj = myPrefJsonObj.getJSONObject("Professional Information");

                String qualif = myProfessionalJsonObj.getString("Qualification");
                String ocuupation = myProfessionalJsonObj.getString("Occupation");
                String qualif_det = myProfessionalJsonObj.getString("Qualification in Detail");
                String emp_in = myProfessionalJsonObj.getString("Employed in");
                String annual_income = myProfessionalJsonObj.getString("Annual Income");

                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                txt_qualif.setText(qualif);
                txt_qualif_det.setText(qualif_det);
                txt_occupation.setText(ocuupation);
                txt_empl_in.setText(emp_in);
                txt_annual_income.setText(annual_income);


                //professional(Groom)

                JSONObject partnerProfessionalJsonObj = partnerPrefJsonObj.getJSONObject("Professional Information");
                String partner_qualif = partnerProfessionalJsonObj.getString("Qualification");
                String partner_ocuupation = partnerProfessionalJsonObj.getString("Occupation");
                // String partner_qualif_det = partnerProfessionalJsonObj.getString("Qualification in Detail");
                String partner_emp_in = partnerProfessionalJsonObj.getString("Employed in");
                String partner_annual_income = partnerProfessionalJsonObj.getString("Annual Income");
                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");

                txt_groom_qualif.setText(partner_qualif);
                // txt_groom_qualif_det.setText(partner_qualif_det);
                txt_groom_occupation.setText(partner_ocuupation);
                txt_groom_annual_income.setText(partner_annual_income);


                JSONObject myLocationJsonObj = myPrefJsonObj.getJSONObject("Location");

                String country = myLocationJsonObj.getString("Country");
                String state = myLocationJsonObj.getString("State");
                String city = myLocationJsonObj.getString("City");
                String citizen = myLocationJsonObj.getString("Citizenship");


                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                txt_country.setText(country);
                txt_city.setText(city);
                txt_state.setText(state);
                txt_citizen.setText(citizen);


                JSONObject myFamDetJsonObj = myPrefJsonObj.getJSONObject("Family Details");
                String fam_val = myFamDetJsonObj.getString("Family Values");
                String fam_type = myFamDetJsonObj.getString("Family Type");
                String fam_status = myFamDetJsonObj.getString("Family Status");
                String father_occ = myFamDetJsonObj.getString("Father Occupation");
                String mother_occ = myFamDetJsonObj.getString("Mother Occupation");

                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                txt_fam_val.setText(fam_val);
                txt_fam_type.setText(fam_type);
                txt_fam_status.setText(fam_status);
                txt_father_occup.setText(father_occ);
                txt_mother_occup.setText(mother_occ);


//partner(location)

                JSONObject partnerLocationJsonObj = partnerPrefJsonObj.getJSONObject("Location");

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


            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                Toast.makeText(DailyRecomViewProfie.this, "" + jsobj.getString("Message"), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


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

                                Toast.makeText(context, "Interest sent", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("MESSAGE", gD.prefs.getString("sent_int_key1", null));
                                Log.i("HH", "strResp :int " + response);
                                setResult(2, intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("MESSAGE","");
        setResult(2, intent);
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE","");
            setResult(2, intent);
            finish();

        }
        return true;

    }

}
