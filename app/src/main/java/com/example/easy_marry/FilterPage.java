package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.easy_marry.Adapter.ListDrawerFilterAdapter;
import com.example.easy_marry.Adapter.SpinnerAdapter;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by android2 on 16/11/16.
 */
public class FilterPage extends Activity implements MyInterface {
    ImageView img_back, img_tick1, img_tick2, img_cross1, img_cross2, img_search;
    Context context;
    GeneralData gD;
    EditText et_search;
    ListView list_drawer;
    DrawerLayout myTabsDrawer;
    Button btn_search;
    ArrayList<ListDrawerBean> beanArrayListNew;
    LinearLayout ll_drawer_from_to, ll_drawer_rad_chk, llay_MainSliderParent, ll_children;
    TextView txt_reset, txt_age, txt_height, txt_marital_status, txt_mother_ton, txt_religion,
            txt_caste, txt_zodiac, txt_star, txt_country, txt_state, txt_city,
            txt_education, txt_occupation, txt_phy_stat, txt_dhosam, txt_eat_habit, txt_drink_habit, txt_smoke_habit, txt_income, txt_filter_header;
    String str_user_id, str_age_from_id = "", str_age_to_id = "", str_height_from_id = "", str_height_to_id = "", str_marital_id = "", str_child_id = "No", str_mother_ton_id = "", str_religion_id = "",
            str_caste_id = "", str_zodiac_id = "", str_star_id = "", str_country_id = "", str_state_id = "", str_city_id = "", str_education_id = "", str_occup_id = "", str_phy_stat_id = "",
            str_dhosam_id = "2", str_eat_id = "", str_drink_id = "", str_smoke_id = "", str_income_id = "", str_age = "", str_height = "";
    ListDrawerFilterAdapter drawerFilterAdapter;
    JSONArray jsonArray_age, jsonArray_height, jsonArray_marital_stat, jsonArray_mother_ton, jsonArray_religion, jsonArray_zodiac, jsonArray_country,
            jsonArray_common, jsonArray_edu, jsonArray_occup, jsonArray_phy_stat, jsonArray_dhosam, jsonArray_eat,
            jsonArray_drink, jsonArray_smoke, jsonArray_income, jsonArray_caste, jsonArray_star, jsonArray_state, jsonArray_city;
    Spinner sp_from, sp_to;
    int sp;
    String str_id, str_values, str_from = "from_to", str_click, str_list_from, str_hFrom, str_hTO;
    String strSpinsp_to = "";
    String strSpinsp_from = "";
    String str_sp_height_from, str_sp_height_to;
    ArrayList<JSONObject> alCheckedItems = new ArrayList<JSONObject>();
    RadioButton rb_dhosham_yes, rb_dhosham_no, rb_dhosham_doesnt_matter, rb_count_child_no, rb_count_child_yes;
    HashMap<String, JSONObject> hsFilterGmap;
    ArrayList<ListDrawerBean> return_beanArrayList;
    String strSpinType = "";
    RelativeLayout rlayMain;
    boolean isOpen = false;
    String[] str_val = {""};
    String str_from1_age = "";
    String str_to_age = "";
    String str_from1_height = "";
    String str_to_height = "";
    ArrayList<ListDrawerBean> tasks;
    String g_mother_ton, g_married, g_religion, g_caste, g_zodiac, g_star,
            g_country, g_state, g_city, g_education, g_occupation, g_phy_stat, g_eat, g_drink, g_smoke, g_income, str_age_to_first, str_age_from_first;
    int my_count;
    String str_registerSelectJSON, str_RemoveVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_page);
        context = this;
        gD = new GeneralData(context);

        img_back = (ImageView) findViewById(R.id.back);
        str_user_id = gD.prefs.getString("userid", null);
        str_registerSelectJSON = gD.prefs.getString("register_select_json", null);

        //registerSelect();

        btn_search = (Button) findViewById(R.id.btn_search);
        txt_reset = (TextView) findViewById(R.id.txt_reset);
        txt_age = (TextView) findViewById(R.id.txt_filter_age);
        txt_height = (TextView) findViewById(R.id.txt_filter_height);
        txt_marital_status = (TextView) findViewById(R.id.txt_filter_marital_status);
        // txt_child = (TextView) findViewById(R.id.txt_filter_marital_status);
        txt_mother_ton = (TextView) findViewById(R.id.txt_filter_mother_ton);
        txt_religion = (TextView) findViewById(R.id.txt_filter_religion);
        txt_caste = (TextView) findViewById(R.id.txt_filter_caste);
        txt_zodiac = (TextView) findViewById(R.id.txt_filter_zodiac);
        txt_star = (TextView) findViewById(R.id.txt_filter_star);
        txt_country = (TextView) findViewById(R.id.txt_filter_country);
        txt_state = (TextView) findViewById(R.id.txt_filter_state);
        txt_city = (TextView) findViewById(R.id.txt_filter_city);
        txt_education = (TextView) findViewById(R.id.txt_filter_education);
        txt_occupation = (TextView) findViewById(R.id.txt_filter_occupation);
        txt_phy_stat = (TextView) findViewById(R.id.txt_filter_phy_stat);
        // txt_dhosam = (TextView) findViewById(R.id.txt_filter_dhosam);
        txt_eat_habit = (TextView) findViewById(R.id.txt_filter_eat);
        txt_drink_habit = (TextView) findViewById(R.id.txt_filter_drink);
        txt_smoke_habit = (TextView) findViewById(R.id.txt_filter_smoke);
        txt_income = (TextView) findViewById(R.id.txt_filter_annual_income);
        txt_filter_header = (TextView) findViewById(R.id.txt_fil_head);


        rb_dhosham_yes = (RadioButton) findViewById(R.id.radio_dhosam_yes);
        rb_dhosham_no = (RadioButton) findViewById(R.id.radio_dhosam_no);
        rb_dhosham_doesnt_matter = (RadioButton) findViewById(R.id.radio_dhosam_doesnt_matter);
        rlayMain = (RelativeLayout) findViewById(R.id.rlay_Main);
        rb_count_child_no = (RadioButton) findViewById(R.id.radio_no);
        rb_count_child_yes = (RadioButton) findViewById(R.id.radio_yes);


        myTabsDrawer = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        ll_drawer_from_to = (LinearLayout) findViewById(R.id.ll_slide_from_to);
        ll_drawer_rad_chk = (LinearLayout) findViewById(R.id.ll_slide_radio_check);
        llay_MainSliderParent = (LinearLayout) findViewById(R.id.llaySliderParent);

        img_tick1 = (ImageView) findViewById(R.id.filter_tick);
        img_tick2 = (ImageView) findViewById(R.id.filter_tick2);
        img_cross1 = (ImageView) findViewById(R.id.filter_cancel);
        img_cross2 = (ImageView) findViewById(R.id.filter_cancel2);
        img_search = (ImageView) findViewById(R.id.filter_search);

        et_search = (EditText) findViewById(R.id.et_filter_search2);
        list_drawer = (ListView) findViewById(R.id.drawer_listview);

        sp_from = (Spinner) findViewById(R.id.sp_from);
        sp_to = (Spinner) findViewById(R.id.sp_to);

        ll_children = (LinearLayout) findViewById(R.id.ll_children);


        llay_MainSliderParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_drawer_from_to.setClickable(false);
                ll_drawer_from_to.setEnabled(false);
                ll_drawer_from_to.setFocusableInTouchMode(false);
                ll_drawer_from_to.setFocusable(false);
                ll_drawer_rad_chk.setClickable(false);
                ll_drawer_rad_chk.setEnabled(false);
                ll_drawer_rad_chk.setFocusableInTouchMode(false);
                ll_drawer_rad_chk.setFocusable(false);
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        myTabsDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        /*str_dhosam_id = gD.prefs.getString("dhosam_id", null);
        txt_dhosam.setText(gD.prefs.getString("dhosam", null));
        Log.e("Na", "dhosam-->" + str_dhosam_id);
        Log.e("Na", "dhosam-->" + gD.prefs.getString("dhosam", null));*/
        registerSelect();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterPage.this,Matches.class));
                finish();
            }
        });
        Log.e("NN", "GEneral_data_count" + GeneralData.filter_count);
        if (GeneralData.filter_count == 0) {
            registerSelect();
            EditDisplayUserCall();
            GeneralData.filter_count = 1;
        }

        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_incomplete_reg_popup, null);
                final android.support.v7.app.AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.show();
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView txt_title = (TextView) itemView1.findViewById(R.id.txt_title);
                TextView txt_content = (TextView) itemView1.findViewById(R.id.txt_content);
                txt_title.setText("Reset Search");
                txt_content.setText("Are you sure you want to rest?");
                Button btn_continue = (Button) itemView1.findViewById(R.id.btn_cont_reg);
                btn_continue.setText("Yes");
                Button btn_exit = (Button) itemView1.findViewById(R.id.btn_exit);
                btn_exit.setText("No");
                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();

                    }

                });

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialog loading = ProgressDialog.show(context, "Loading", "Please wait...", false, false);
                        SharedPreferences.Editor prefEdit = gD.prefs.edit();
                        prefEdit.putString("height", null);
                        prefEdit.putString("height_id", null);
                        prefEdit.putString("age", null);
                        prefEdit.putString("age_id", null);
                        prefEdit.putString("zodiac_id", null);
                        prefEdit.putString("zodiac", null);
                        prefEdit.putString("star", null);
                        prefEdit.putString("star_id", null);
                        prefEdit.putString("mari", null);
                        prefEdit.putString("mari_id", null);
                        prefEdit.putString("religion_id", null);
                        prefEdit.putString("religion", null);
                        prefEdit.putString("caste_id", null);
                        prefEdit.putString("caste", null);
                        prefEdit.putString("mother", null);
                        prefEdit.putString("mother_id", null);
                        prefEdit.putString("country_id", null);
                        prefEdit.putString("country", null);
                        prefEdit.putString("state_id", null);
                        prefEdit.putString("state", null);
                        prefEdit.putString("city", null);
                        prefEdit.putString("city_id", null);
                        prefEdit.putString("edu", null);
                        prefEdit.putString("edu_id", null);
                        prefEdit.putString("occup", null);
                        prefEdit.putString("occup_id", null);
                        prefEdit.putString("phy_id", null);
                        prefEdit.putString("phy", null);
                        prefEdit.putString("eat_id", null);
                        prefEdit.putString("eat", null);
                        prefEdit.putString("smoke_id", null);
                        prefEdit.putString("smoke", null);
                        prefEdit.putString("drink_id", null);
                        prefEdit.putString("drink", null);
                        prefEdit.putString("income", null);
                        prefEdit.putString("income_id", null);
                        prefEdit.putString("dhosam_id", null);
                        prefEdit.putString("child_id", null);
                        prefEdit.putString("filter_page_id", null);
                        prefEdit.putString("filter_json", null);
                        prefEdit.commit();
                        startActivity(new Intent(FilterPage.this, FilterPage.class));
                        finish();
                        GeneralData.filter_count = 0;
                        GeneralData.filter_count1 = 0;
                        loading.dismiss();
                        altDialog.dismiss();
                    }
                });
            }
        });
        if (str_from1_height != null) {
            str_from1_height = gD.prefs.getString("height_from", null);
        }
        if (str_to_height != null) {
            str_to_height = gD.prefs.getString("height_to", null);
        }
        if (str_from1_age != null) {
            str_from1_age = gD.prefs.getString("age_from", null);
        }
        if (str_to_age != null) {
            str_to_age = gD.prefs.getString("age_to", null);
        }


        if (str_zodiac_id != null) {
            str_zodiac_id = gD.prefs.getString("zodiac_id", null);
            txt_zodiac.setText(gD.prefs.getString("zodiac", null));
            //txt_zodiac.setTag(gD.prefs.getString("zodiac_tag", null));
        } else {
            str_zodiac_id = "";
        }

        if (str_star_id != null) {
            str_star_id = gD.prefs.getString("star_id", null);
            txt_star.setText(gD.prefs.getString("star", null));
            //txt_star.setTag(gD.prefs.getString("star_tag", null));

        } else {
            str_star_id = "";
        }
        if (str_marital_id != null) {
            str_marital_id = gD.prefs.getString("mari_id", null);
            txt_marital_status.setText(gD.prefs.getString("mari", null));
            //txt_marital_status.setTag(gD.prefs.getString("mari_tag", null));
        } else {
            str_marital_id = "";
        }

        if (gD.prefs.getString("mari", null) != null) {
            if (!gD.prefs.getString("mari", null).equalsIgnoreCase("Unmarried")) {
                ll_children.setVisibility(View.VISIBLE);
            } else {
                ll_children.setVisibility(View.GONE);
            }
        }

        if (str_religion_id != null) {
            str_religion_id = gD.prefs.getString("religion_id", null);
            txt_religion.setText(gD.prefs.getString("religion", null));
            //txt_religion.setTag(gD.prefs.getString("religion_tag", null));
        } else {
            str_religion_id = "";
        }
        if (str_caste_id != null) {
            str_caste_id = gD.prefs.getString("caste_id", null);
            txt_caste.setText(gD.prefs.getString("caste", null));
            // txt_caste.setTag(gD.prefs.getString("caste_tag", null));
        } else {
            str_caste_id = "";
        }
        if (str_mother_ton_id != null) {
            str_mother_ton_id = gD.prefs.getString("mother_id", null);
            txt_mother_ton.setText(gD.prefs.getString("mother", null));
            //txt_mother_ton.setTag(gD.prefs.getString("mother_tag", null));
        } else {
            str_mother_ton_id = "";
        }
        if (str_country_id != null) {
            str_country_id = gD.prefs.getString("country_id", null);
            txt_country.setText(gD.prefs.getString("country", null));
            // txt_country.setTag(gD.prefs.getString("country_tag", null));
        } else {
            str_country_id = "";
        }
        if (str_state_id != null) {
            str_state_id = gD.prefs.getString("state_id", null);
            txt_state.setText(gD.prefs.getString("state", null));
            //txt_state.setTag(gD.prefs.getString("state_tag", null));
        } else {
            str_state_id = "";
        }
        if (str_city_id != null) {
            str_city_id = gD.prefs.getString("city_id", null);
            txt_city.setText(gD.prefs.getString("city", null));
            // txt_city.setTag(gD.prefs.getString("city_tag", null));
        } else {
            str_city_id = "";
        }
        if (str_education_id != null) {
            str_education_id = gD.prefs.getString("edu_id", null);
            txt_education.setText(gD.prefs.getString("edu", null));
            // txt_education.setTag(gD.prefs.getString("edu_tag", null));
        } else {
            str_education_id = "";
        }
        if (str_occup_id != null) {
            str_occup_id = gD.prefs.getString("occup_id", null);
            txt_occupation.setText(gD.prefs.getString("occup", null));
            //txt_occupation.setTag(gD.prefs.getString("occup_tag", null));
        } else {
            str_occup_id = "";
        }
        if (str_phy_stat_id != null) {
            str_phy_stat_id = gD.prefs.getString("phy_id", null);
            txt_phy_stat.setText(gD.prefs.getString("phy", null));
            //txt_phy_stat.setTag(gD.prefs.getString("phy_tag", null));
        } else {
            str_phy_stat_id = "";
        }

        if (str_eat_id != null) {
            str_eat_id = gD.prefs.getString("eat_id", null);
            txt_eat_habit.setText(gD.prefs.getString("eat", null));
            // txt_eat_habit.setTag(gD.prefs.getString("eat_tag", null));
        } else {
            str_eat_id = "";
        }
        if (str_smoke_id != null) {
            str_smoke_id = gD.prefs.getString("smoke_id", null);
            txt_smoke_habit.setText(gD.prefs.getString("smoke", null));
            // txt_smoke_habit.setTag(gD.prefs.getString("smoke_tag", null));
        } else {
            str_smoke_id = "";
        }
        if (str_drink_id != null) {
            str_drink_id = gD.prefs.getString("drink_id", null);
            txt_drink_habit.setText(gD.prefs.getString("drink", null));
            // txt_drink_habit.setTag(gD.prefs.getString("drink_tag", null));

        } else {
            str_drink_id = "";
        }
        if (str_income_id != null) {
            str_income_id = gD.prefs.getString("income_id", null);
            txt_income.setText(gD.prefs.getString("income", null));
            //  txt_income.setTag(gD.prefs.getString("income_tag", null));
        } else {
            str_income_id = "";
        }
        if (str_age != null) {
            txt_age.setText(gD.prefs.getString("age", null));
            //  txt_age.setTag(gD.prefs.getString("age_tag", null));
            str_age = gD.prefs.getString("age_id", null);

        } else {
            str_age = "";
        }
        if (str_height != null) {
            //Log.e("LO", str_age);

            txt_height.setText(gD.prefs.getString("height", null));
            //txt_height.setTag(gD.prefs.getString("height_tag", null));
            str_height = gD.prefs.getString("height_id", null);
        } else {
            str_height = "";
        }

        final SharedPreferences.Editor prefEdit = gD.prefs.edit();
        final SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
        prefEdit.putString("dhosam_id", str_dhosam_id);
        prefEdit.putString("child_id", str_child_id);
        prefEdit.commit();

        if (gD.prefs.getString("dhosam_id", null).equalsIgnoreCase("1")) {
            rb_dhosham_yes.setChecked(true);
            str_dhosam_id = "1";

        } else if (gD.prefs.getString("dhosam_id", null).equalsIgnoreCase("2")) {
            rb_dhosham_no.setChecked(true);
            str_dhosam_id = "2";

        } else {
            rb_dhosham_doesnt_matter.setChecked(true);
            str_dhosam_id = "3";
        }

        if (gD.prefs.getString("child_id", null).equalsIgnoreCase("no")) {
            rb_count_child_no.setChecked(true);
            str_child_id = "No";
        } else if (gD.prefs.getString("child_id", null).equalsIgnoreCase("yes")) {
            rb_count_child_yes.setChecked(true);
            str_child_id = "Yes";

        }


        rb_dhosham_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_yes.isChecked()) {
                    str_dhosam_id = "1";
                    rb_dhosham_no.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                    prefEdit1.putString("dhosam_id", str_dhosam_id);

                }
            }
        });
        rb_dhosham_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_no.isChecked()) {
                    str_dhosam_id = "2";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_doesnt_matter.setChecked(false);
                    prefEdit1.putString("dhosam_id", str_dhosam_id);

                }
            }
        });
        rb_dhosham_doesnt_matter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_dhosham_doesnt_matter.isChecked()) {
                    str_dhosam_id = "3";
                    rb_dhosham_yes.setChecked(false);
                    rb_dhosham_no.setChecked(false);
                    prefEdit1.putString("dhosam_id", str_dhosam_id);

                }
            }
        });

        rb_count_child_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_count_child_yes.isChecked()) {
                    str_child_id = "Yes";
                    rb_count_child_no.setChecked(false);
                    prefEdit1.putString("child_id", str_child_id);

                }
            }
        });
        rb_count_child_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rb_count_child_no.isChecked()) {
                    str_child_id = "No";
                    rb_count_child_yes.setChecked(false);
                    prefEdit1.putString("child_id", str_child_id);

                }
            }
        });
        prefEdit1.commit();
        if (myTabsDrawer != null && myTabsDrawer instanceof DrawerLayout) {

            myTabsDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) {

                    Log.i("HH", "Suspect.......1!");
                }

                @Override
                public void onDrawerOpened(View view) {

                }

                @Override
                public void onDrawerClosed(View view) {
                    Log.i("HH", "Suspect.......!");

                }

                @Override
                public void onDrawerStateChanged(int i) {
                    Log.i("HH", "isOPEN : " + isOpen);
                    if (isOpen) {
                        myTabsDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

                    } else {
                        myTabsDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    }


                }
            });
        }


//        myTabsDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, findViewById(R.id.my_list_drawer_layout));

        //myTabsDrawer.getParent().requestDisallowInterceptTouchEvent(true);

       /* DisplayMetrics dm = getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;

        int weight = dm.widthPixels;
        int lay_wid = (int) (weight / 3);

        Log.e("NN:fil", String.valueOf(densityDpi));
        Log.e("NN:fil", String.valueOf(weight));
        Log.e("NN:fil", String.valueOf(lay_wid));

        //For slider dynamic Width


        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) llay_MainSliderParent.getLayoutParams();
        lp.width = lay_wid + 230;
        llay_MainSliderParent.setLayoutParams(lp);*/

        txt_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isOpen = true;
                str_from = "from_to";
                ll_drawer_from_to.setVisibility(View.VISIBLE);
                ll_drawer_rad_chk.setVisibility(View.GONE);
                str_list_from = "age";
                txt_filter_header.setText("AGE");
                myTabsDrawer.openDrawer(llay_MainSliderParent);
                LoadLayout(jsonArray_age, "from_to", "age");


                txt_age.setText(gD.prefs.getString("age", null));
                //   txt_age.setTag(gD.prefs.getString("age_tag", null));
                str_age = gD.prefs.getString("age_id", null);
                if (str_age != null) {

                    if (txt_age.getText().toString().trim().length() > 0) {
                        //LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", txt_marital_status.getTag().toString().trim());
                        //  Log.e("JJ", gD.prefs.getString("age_id", null));
                        LoadLayout_Edit(jsonArray_age, "from_to", gD.prefs.getString("age_id", null), "age");

                        //LoadLayout(jsonArray_age, "from_to", "age");
                        Log.e("NM", "hii");
                    }

                } else {
                    LoadLayout(jsonArray_age, "from_to", "age");
                }
                rlayMain.setFocusable(false);
                rlayMain.setEnabled(false);
                rlayMain.setClickable(false);


            }
        });
        txt_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "from_to";
                ll_drawer_from_to.setVisibility(View.VISIBLE);
                ll_drawer_rad_chk.setVisibility(View.GONE);
                str_list_from = "height";
                txt_filter_header.setText("HEIGHT");
                myTabsDrawer.openDrawer(llay_MainSliderParent);
                LoadLayout(jsonArray_height, "from_to", "height");


                txt_height.setText(gD.prefs.getString("height", null));
                //   txt_height.setTag(gD.prefs.getString("height_tag", null));
                str_height = gD.prefs.getString("height_id", null);
                if (str_height != null) {
                    if (txt_height.getText().toString().trim().length() > 0) {
                        //LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", txt_marital_status.getTag().toString().trim());
                        Log.e("JJ", gD.prefs.getString("height_id", null));
                        LoadLayout_Edit(jsonArray_height, "from_to", gD.prefs.getString("height_id", null), "height");
                        //LoadLayout(jsonArray_age, "from_to", "age");
                        Log.e("NM", "hii");
                    }
                } else {
                    LoadLayout(jsonArray_height, "from_to", "height");
                }

                rlayMain.setFocusable(false);
                rlayMain.setEnabled(false);
                rlayMain.setClickable(false);


            }
        });
        txt_marital_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "marital_status";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);


                str_marital_id = gD.prefs.getString("mari_id", null);
                txt_marital_status.setText(gD.prefs.getString("mari", null));
                // txt_marital_status.setTag(gD.prefs.getString("mari_tag", null));

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_marital_stat, "rad_chk", "marital_stat");

                if (txt_marital_status.getText().toString().trim().length() > 0) {

                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            try {
                                for (int i = 0; i < jsonArray_marital_stat.length(); i++) {
                                    JSONObject jsEach = jsonArray_marital_stat.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_married.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_marital_id = sB1.getStr_list_items_id();
                                        Log.e("BN", g_married);
                                        if (str_marital_id != null) {
                                            LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", str_marital_id, "");
                                        } else {
                                            LoadLayout(jsonArray_marital_stat, "rad_chk", "marital_stat");
                                        }

                                    }

                                }
                            } catch (Exception e) {

                            }
                            GeneralData.filter_count1 = 1;
                        } else {
                            if (str_marital_id != null) {
                                LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", str_marital_id, "");
                            } else {
                                LoadLayout(jsonArray_marital_stat, "rad_chk", "marital_stat");
                            }
                            //LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", str_marital_id, "");
                        }
                        my_count = 1;
                    } else {

                        if (str_marital_id != null) {
                            LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", str_marital_id, "");
                        } else {
                            LoadLayout(jsonArray_marital_stat, "rad_chk", "marital_stat");
                        }
                    }

                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        txt_mother_ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "mother_ton";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_mother_ton, "rad_chk", "mother_ton");

                str_mother_ton_id = gD.prefs.getString("mother_id", null);
                txt_mother_ton.setText(gD.prefs.getString("mother", null));
                //txt_mother_ton.setTag(gD.prefs.getString("mother_tag", null));

                if (txt_mother_ton.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            try {
                                for (int i = 0; i < jsonArray_mother_ton.length(); i++) {
                                    JSONObject jsEach = jsonArray_mother_ton.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_mother_ton.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_mother_ton_id = sB1.getStr_list_items_id();
                                        Log.e("BN", str_mother_ton_id);
                                        if (str_mother_ton_id != null) {
                                            LoadLayout_Edit(jsonArray_mother_ton, "rad_chk", str_mother_ton_id, "");
                                        } else {
                                            LoadLayout(jsonArray_mother_ton, "rad_chk", "mother_ton");
                                        }

                                    }

                                }
                            } catch (Exception e) {

                            }
                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_mother_ton_id != null) {
                                LoadLayout_Edit(jsonArray_mother_ton, "rad_chk", str_mother_ton_id, "");
                            } else {
                                LoadLayout(jsonArray_mother_ton, "rad_chk", "mother_ton");
                            }
                        }
                        my_count = 1;
                    } else {

                        if (str_mother_ton_id != null) {
                            LoadLayout_Edit(jsonArray_mother_ton, "rad_chk", str_mother_ton_id, "");
                        } else {
                            LoadLayout(jsonArray_mother_ton, "rad_chk", "mother_ton");
                        }
                    }

                }

                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        txt_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "religion";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_religion.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_religion, "rad_chk", "religion");
                str_religion_id = gD.prefs.getString("religion_id", null);
                txt_religion.setText(gD.prefs.getString("religion", null));
                //txt_religion.setTag(gD.prefs.getString("religion_tag", null));
                if (txt_religion.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            try {
                                for (int i = 0; i < jsonArray_religion.length(); i++) {
                                    JSONObject jsEach = jsonArray_religion.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_religion.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_religion_id = sB1.getStr_list_items_id();
                                        Log.e("BN", str_religion_id);

                                        if (str_religion_id != null) {
                                            LoadLayout_Edit(jsonArray_religion, "rad_chk", str_religion_id, "");
                                        } else {
                                            LoadLayout(jsonArray_religion, "rad_chk", "religion");
                                        }


                                    }

                                }
                            } catch (Exception e) {

                            }
                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_religion_id != null) {
                                LoadLayout_Edit(jsonArray_religion, "rad_chk", str_religion_id, "");
                            } else {
                                LoadLayout(jsonArray_religion, "rad_chk", "religion");
                            }
                        }
                        my_count = 1;
                    } else {

                        if (str_religion_id != null) {
                            LoadLayout_Edit(jsonArray_religion, "rad_chk", str_religion_id, "");
                        } else {
                            LoadLayout(jsonArray_religion, "rad_chk", "religion");
                        }
                    }

                }


                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        txt_caste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "caste";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);


                //jsonArray_caste=jsonArray_common;
                str_caste_id = gD.prefs.getString("caste_id", null);
                txt_caste.setText(gD.prefs.getString("caste", null));

                if (str_religion_id != null) {
                    if (str_caste_id != null) {
                        Log.e("BN_caste", str_caste_id);
                    } else {
                        Log.e("BN_caste", "caste_id is empty..");
                    }
                } else {
                    Log.e("BN_caste", "religion_id is empty..");
                }

                if (txt_caste.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            if (str_religion_id != null) {
                                if (str_caste_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid", str_caste_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your religion", Toast.LENGTH_SHORT).show();
                            }


                            GeneralData.filter_count1 = 1;
                        } else {
                            if (str_religion_id != null) {
                                if (str_caste_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid", str_caste_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your religion", Toast.LENGTH_SHORT).show();
                            }
                            //LoadLayout_Edit(jsonArray_caste, "rad_chk", str_caste_id, "");
                        }

                        /*else {
                           LoadLayout_Edit(jsonArray_caste, "rad_chk", str_caste_id, "");
                        }
                        my_count = 1;*/
                    } /*else {
                        LoadLayout_Edit(jsonArray_caste, "rad_chk", str_caste_id, "");
                    }*/

                }

                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        txt_zodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "zodiac";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_zodiac.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_zodiac, "rad_chk", "zodiac");

                str_zodiac_id = gD.prefs.getString("zodiac_id", null);
                txt_zodiac.setText(gD.prefs.getString("zodiac", null));
                //txt_zodiac.setTag(gD.prefs.getString("zodiac_tag", null));

                if (txt_zodiac.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            try {
                                for (int i = 0; i < jsonArray_zodiac.length(); i++) {
                                    JSONObject jsEach = jsonArray_zodiac.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_zodiac.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_zodiac_id = sB1.getStr_list_items_id();
                                        Log.e("BN", str_zodiac_id);

                                        if (str_zodiac_id != null) {
                                            LoadLayout_Edit(jsonArray_zodiac, "rad_chk", str_zodiac_id, "");
                                        } else {
                                            LoadLayout(jsonArray_zodiac, "rad_chk", "zodiac");
                                        }

                                    }

                                }
                            } catch (Exception e) {

                            }
                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_zodiac_id != null) {
                                LoadLayout_Edit(jsonArray_zodiac, "rad_chk", str_zodiac_id, "");
                            } else {
                                LoadLayout(jsonArray_zodiac, "rad_chk", "zodiac");
                            }
                        }
                        my_count = 1;
                    } else {

                        if (str_zodiac_id != null) {
                            LoadLayout_Edit(jsonArray_zodiac, "rad_chk", str_zodiac_id, "");
                        } else {
                            LoadLayout(jsonArray_zodiac, "rad_chk", "zodiac");
                        }
                    }

                }

                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        txt_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "star";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);


                Log.e("BN_", "com_ary2->" + jsonArray_star);
                str_star_id = gD.prefs.getString("star_id", null);
                txt_star.setText(gD.prefs.getString("star", null));
                Log.e("BN", str_star_id);
                // txt_star.setTag(gD.prefs.getString("star_tag", null));


                if (txt_star.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            if (str_zodiac_id != null) {
                                if (str_star_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "rasiid", str_star_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "rasiid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your Zodiac", Toast.LENGTH_SHORT).show();
                            }

                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_zodiac_id != null) {
                                if (str_star_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "rasiid", str_star_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "rasiid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your Zodiac", Toast.LENGTH_SHORT).show();
                            }
                        }
                        my_count = 1;
                    }

                }

                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        txt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "country";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_country.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_country, "rad_chk", "country");
                str_country_id = gD.prefs.getString("country_id", null);
                txt_country.setText(gD.prefs.getString("country", null));
                //txt_country.setTag(gD.prefs.getString("country_tag", null));

                if (txt_country.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {
                            try {
                                for (int i = 0; i < jsonArray_country.length(); i++) {
                                    JSONObject jsEach = jsonArray_country.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_country.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_country_id = sB1.getStr_list_items_id();
                                        Log.e("BN", str_country_id);

                                        if (str_country_id != null) {
                                            LoadLayout_Edit(jsonArray_country, "rad_chk", str_country_id, "");
                                        } else {
                                            LoadLayout(jsonArray_country, "rad_chk", "country");
                                        }

                                    }

                                }
                            } catch (Exception e) {

                            }
                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_country_id != null) {
                                LoadLayout_Edit(jsonArray_country, "rad_chk", str_country_id, "");
                            } else {
                                LoadLayout(jsonArray_country, "rad_chk", "country");
                            }
                        }
                        my_count = 1;
                    } else {

                        if (str_country_id != null) {
                            LoadLayout_Edit(jsonArray_country, "rad_chk", str_country_id, "");
                        } else {
                            LoadLayout(jsonArray_country, "rad_chk", "country");
                        }
                    }

                }


                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        txt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "state";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);


                str_state_id = gD.prefs.getString("state_id", null);
                txt_state.setText(gD.prefs.getString("state", null));
                //txt_state.setTag(gD.prefs.getString("state_tag", null));

                if (str_state_id != null) {
                    Log.e("BN", str_state_id);
                }
                else{
                    Log.e("BN", "state_id is null");
                }

                Log.e("BN_", "com_ary2->" + jsonArray_state);
                if (txt_state.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {

                            if (str_country_id != null) {
                                if (str_state_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "countryid", str_state_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "countryid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your country", Toast.LENGTH_SHORT).show();
                            }

                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_country_id != null) {
                                if (str_state_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "countryid", str_state_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "countryid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your country", Toast.LENGTH_SHORT).show();
                            }
                        }
                        my_count = 1;
                    }
                }



                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_count = 0;
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "city";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);


                str_city_id = gD.prefs.getString("city_id", null);
                txt_city.setText(gD.prefs.getString("city", null));
                // txt_city.setTag(gD.prefs.getString("city_tag", null));
                if (str_city_id != null) {
                    Log.e("BN", str_city_id);
                }
                else{
                    Log.e("BN", "str_city_id is null");
                }

                Log.e("BN_", "com_ary2->" + jsonArray_city);
                if (txt_city.getText().toString().trim().length() > 0) {
                    if (my_count == 0) {
                        if (GeneralData.filter_count1 == 0) {

                            if (str_state_id != null) {
                                if (str_city_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "stateid", str_city_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "stateid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your state", Toast.LENGTH_SHORT).show();
                            }



                            GeneralData.filter_count1 = 1;
                        } else {

                            if (str_state_id != null) {
                                if (str_city_id != null) {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "stateid", str_city_id);
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                } else {
                                    return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "stateid", "");
                                    myTabsDrawer.openDrawer(llay_MainSliderParent);
                                }
                            } else {
                                Toast.makeText(FilterPage.this, "select your state", Toast.LENGTH_SHORT).show();
                            }

                        }
                        my_count = 1;
                    }

                }


                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        txt_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "education";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_education.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_edu, "rad_chk", "edu");
                str_education_id = gD.prefs.getString("edu_id", null);
                txt_education.setText(gD.prefs.getString("edu", null));
                //    txt_education.setTag(gD.prefs.getString("edu_tag", null));
                if (txt_education.getText().toString().trim().length() > 0) {
                    if (str_education_id != null) {
                        LoadLayout_Edit(jsonArray_edu, "rad_chk", str_education_id, "");
                    } else {
                        LoadLayout(jsonArray_edu, "rad_chk", "edu");
                    }



                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

        txt_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "occupation";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_occupation.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_occup, "rad_chk", "occup");
                str_occup_id = gD.prefs.getString("occup_id", null);
                txt_occupation.setText(gD.prefs.getString("occup", null));
                //txt_occupation.setTag(gD.prefs.getString("occup_tag", null));

                if (txt_occupation.getText().toString().trim().length() > 0) {
                    if (str_occup_id != null) {
                        LoadLayout_Edit(jsonArray_occup, "rad_chk", str_occup_id, "");
                    } else {
                        LoadLayout(jsonArray_occup, "rad_chk", "occup");
                    }

                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        txt_phy_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "phy_status";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_country.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_phy_stat, "rad_chk", "phy_stat");
                str_phy_stat_id = gD.prefs.getString("phy_id", null);
                txt_phy_stat.setText(gD.prefs.getString("phy", null));
                //  txt_phy_stat.setTag(gD.prefs.getString("phy_tag", null));

                if (txt_phy_stat.getText().toString().trim().length() > 0) {

                    LoadLayout_Edit(jsonArray_phy_stat, "rad_chk", str_phy_stat_id, "");
                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });

      /*  txt_dhosam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen=true;
                str_from = "rad_chk";
                str_click = "radio";
                str_list_from = "dhosam";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);
                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_dhosam, "rad_chk");
                //  myTabsDrawer.closeDrawer(llay_MainSliderParent);


            }
        });*/
        txt_eat_habit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "eat_habit";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_eat_habit.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);
                str_eat_id = gD.prefs.getString("eat_id", null);
                txt_eat_habit.setText(gD.prefs.getString("eat", null));
                //   txt_eat_habit.setTag(gD.prefs.getString("eat_tag", null));

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_eat, "rad_chk", "eat_habit");

                if (txt_eat_habit.getText().toString().trim().length() > 0) {
                    LoadLayout_Edit(jsonArray_eat, "rad_chk", str_eat_id, "");
                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        txt_drink_habit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "drink_habit";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_drink_habit.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_drink, "rad_chk", "drink_habit");
                str_drink_id = gD.prefs.getString("drink_id", null);
                txt_drink_habit.setText(gD.prefs.getString("drink", null));
                //txt_drink_habit.setTag(gD.prefs.getString("drink_tag", null));
                if (txt_drink_habit.getText().toString().trim().length() > 0) {
                    LoadLayout_Edit(jsonArray_drink, "rad_chk", str_drink_id, "");
                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        txt_smoke_habit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "smoke_habit";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_smoke_habit.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_smoke, "rad_chk", "smoke_habit");
                str_smoke_id = gD.prefs.getString("smoke_id", null);
                txt_smoke_habit.setText(gD.prefs.getString("smoke", null));
                //txt_smoke_habit.setTag(gD.prefs.getString("smoke_tag", null));

                if (txt_smoke_habit.getText().toString().trim().length() > 0) {
                    LoadLayout_Edit(jsonArray_smoke, "rad_chk", str_smoke_id, "");
                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
        txt_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                str_list_from = "income";
                et_search.setText("");
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                Log.i("RJ", "Length : " + txt_country.getText());

                myTabsDrawer.openDrawer(llay_MainSliderParent);

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_income, "rad_chk", "income");
                str_income_id = gD.prefs.getString("income_id", null);
                txt_income.setText(gD.prefs.getString("income", null));
                //txt_income.setTag(gD.prefs.getString("income_tag", null));
                if (txt_income.getText().toString().trim().length() > 0) {
                    LoadLayout_Edit(jsonArray_income, "rad_chk", str_income_id, "");
                }
                et_search.addTextChangedListener(new TextWatcher() {
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click);
                        list_drawer.setAdapter(drawerFilterAdapter);
                        drawerFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });


        img_tick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                isOpen = false;
                if (str_from.equalsIgnoreCase("rad_chk")) {

                    if (!hsFilterGmap.isEmpty()) {
                        myTabsDrawer.closeDrawer(llay_MainSliderParent);
                        if (hsFilterGmap != null) {
                            Iterator myVeryOwnIterator = hsFilterGmap.keySet().iterator();
                            String id = "", val = "";
                            while (myVeryOwnIterator.hasNext()) {
                                String key = (String) myVeryOwnIterator.next();
                                String value = String.valueOf(hsFilterGmap.get(key));
                                JSONObject jsobj = null;
                                try {
                                    jsobj = new JSONObject(value);
                                    id += jsobj.getString("id") + ",";
                                    val += jsobj.getString("value") + ",";
                                    Log.e("NN:f", id);
                                    Log.e("NN:f", val);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (id.contains(",")) {
                                id = id.substring(0, id.length() - 1);
                            }
                            if (val.contains(",")) {
                                val = val.substring(0, val.length() - 1);
                            }

                            //  Toast.makeText(context, "Jid: " + id + " Jval: " + val, Toast.LENGTH_LONG).show();
                       /* if (str_list_from.equalsIgnoreCase("dhosam")) {

                            prefEdit.putString("dhosam_id", str_id);
                            prefEdit.putString("dhosam", str_values);
                            prefEdit.commit();
                            str_dhosam_id = str_id;
                            txt_dhosam.setText(str_values);
                            Log.e("Na", "dhosam-->" + str_dhosam_id);
                            Log.e("Na", "dhosam-->" + str_values);
                        } else*/
                            if (str_list_from.equalsIgnoreCase("zodiac")) {

                                prefEdit.putString("zodiac_id", id);
                                prefEdit.putString("zodiac", val);
                                //prefEdit.putString("zodiac_tag", id);

                                str_zodiac_id = id;
                                txt_zodiac.setText(val);
                                // txt_zodiac.setTag(id);
                                Log.e("Na", "zodiac-->" + str_zodiac_id);
                                Log.e("Na", "zodiac-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("star")) {

                                prefEdit.putString("star_id", id);
                                prefEdit.putString("star", val);
                                //prefEdit.putString("star_tag", id);
                                str_star_id = id;
                                txt_star.setText(val);
                                // txt_star.setTag(id);
                                Log.e("Na", "star-->" + str_star_id);
                                Log.e("Na", "star-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("marital_status")) {
                                prefEdit.putString("mari_id", id);
                                prefEdit.putString("mari", val);
                                //prefEdit.putString("mari_tag", id);


                                str_marital_id = id;
                                txt_marital_status.setText(val);
                                // txt_marital_status.setTag(id);
                                Log.e("Na", "mari-->" + str_marital_id);
                                Log.e("Na", "mari-->" + val);

                                if (!val.equalsIgnoreCase("Unmarried")) {
                                    ll_children.setVisibility(View.VISIBLE);
                                } else {
                                    ll_children.setVisibility(View.GONE);
                                }


                            } else if (str_list_from.equalsIgnoreCase("religion")) {

                                prefEdit.putString("religion_id", id);
                                prefEdit.putString("religion", val);
                                // prefEdit.putString("religion_tag", id);
                                str_religion_id = id;
                                txt_religion.setText(val);
                                // txt_religion.setTag(id);
                                Log.e("Na", "reli-->" + str_religion_id);
                                Log.e("Na", "reli-->" + val);
                                Log.e("Na", "reli_shared-->" + gD.prefs.getString("religion_id", null));
                                Log.e("Na", "caste_sharedId-->" + gD.prefs.getString("caste_id", null));
                                Log.e("Na", "caste_sharedValue-->" + gD.prefs.getString("caste", null));

                                if (str_RemoveVal != null) {
                                    Log.e("NN:fc1", "str_RemoveVal=>" + str_RemoveVal);
                                    for (int i = 0; i < beanArrayListNew.size(); i++) {
                                        ListDrawerBean lb = beanArrayListNew.get(i);
                                        Log.e("NN:fc1", "arrayList=>" + lb.getStr_list_items());
                                    }

                                    ArrayList<ListDrawerBean> al = new ArrayList<ListDrawerBean>(beanArrayListNew);
                                    for (int i = 0; i < al.size(); i++) {

                                        ListDrawerBean lb = al.get(i);
                                        Log.e("NN:fc1", "religion_idl=>" + lb.getStr_religion_id());
                                        if (lb.getStr_religion_id().equalsIgnoreCase(str_RemoveVal)) {
                                            al.remove(lb);

                                        }

                                        ArrayList<ListDrawerBean> treelist = new ArrayList<ListDrawerBean>(al);
                                        beanArrayListNew = treelist;

                                        Log.e("NN:fc1", "arrayListAfterREmoved=>" + lb.getStr_list_items());
                                    }

                                }

                                restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid", "");

                            } else if (str_list_from.equalsIgnoreCase("caste")) {
                                prefEdit.putString("caste_id", id);
                                prefEdit.putString("caste", val);
                                // prefEdit.putString("caste_tag", id);

                                str_caste_id = id;
                                txt_caste.setText(val);
                                // txt_caste.setTag(id);
                                Log.e("Na", "caste-->" + str_caste_id);
                                Log.e("Na", "caste-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("mother_ton")) {
                                prefEdit.putString("mother_id", id);
                                prefEdit.putString("mother", val);
                                // prefEdit.putString("mother_tag", id);
                                str_mother_ton_id = id;
                                txt_mother_ton.setText(val);
                                Log.e("Na", "mother_ton-->" + str_mother_ton_id);
                                Log.e("Na", "mother_ton-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("country")) {

                          /*     txt_state.setText("");
                                txt_city.setText("");
                                prefEdit.putString("state_id", null);
                                prefEdit.putString("state", null);

                                prefEdit.putString("city_id", null);
                                prefEdit.putString("city", null);*/

                                prefEdit.putString("country_id", id);
                                prefEdit.putString("country", val);
                                // prefEdit.putString("country_tag", id);
                                str_country_id = id;
                                txt_country.setText(val);
                                //  txt_country.setTag(id);
                                Log.e("Na", "country-->" + str_country_id);
                                Log.e("Na", "country-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("state")) {
                                prefEdit.putString("state_id", id);
                                prefEdit.putString("state", val);
                                //prefEdit.putString("state_tag", id);
                                str_state_id = id;
                                txt_state.setText(val);
                                //txt_state.setTag(id);
                                Log.e("Na", "state-->" + str_state_id);
                                Log.e("Na", "state-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("city")) {
                                prefEdit.putString("city_id", id);
                                prefEdit.putString("city", val);
                                //  prefEdit.putString("city_tag", id);
                                str_city_id = id;
                                txt_city.setText(val);
                                //  txt_city.setTag(id);
                                Log.e("Na", "city-->" + str_city_id);
                                Log.e("Na", "city-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("education")) {
                                prefEdit.putString("edu_id", id);
                                prefEdit.putString("edu", val);
                                //  prefEdit.putString("edu_tag", id);
                                str_education_id = id;
                                txt_education.setText(val);
                                // txt_education.setTag(id);
                                Log.e("Na", "edu-->" + str_education_id);
                                Log.e("Na", "edu-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("occupation")) {
                                prefEdit.putString("occup_id", id);
                                prefEdit.putString("occup", val);
                                //prefEdit.putString("occup_tag", id);
                                str_occup_id = id;
                                txt_occupation.setText(val);
                                // txt_occupation.setTag(id);
                                Log.e("Na", "occup-->" + str_occup_id);
                                Log.e("Na", "occup-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("phy_status")) {
                                prefEdit.putString("phy_id", id);
                                prefEdit.putString("phy", val);
                                // prefEdit.putString("phy_tag", id);
                                str_phy_stat_id = id;
                                txt_phy_stat.setText(val);
                                //  txt_phy_stat.setTag(id);
                                Log.e("Na", "phy_stat-->" + str_phy_stat_id);
                                Log.e("Na", "phy_stat-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("eat_habit")) {
                                prefEdit.putString("eat_id", id);
                                prefEdit.putString("eat", val);
                                // prefEdit.putString("eat_tag", id);
                                str_eat_id = id;
                                txt_eat_habit.setText(val);
                                // txt_eat_habit.setTag(id);
                                Log.e("Na", "eat-->" + str_eat_id);
                                Log.e("Na", "eat-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("drink_habit")) {
                                prefEdit.putString("drink_id", id);
                                prefEdit.putString("drink", val);
                                //prefEdit.putString("drink_tag", id);
                                str_drink_id = id;
                                txt_drink_habit.setText(val);
                                //txt_drink_habit.setTag(id);
                                Log.e("Na", "drink-->" + str_drink_id);
                                Log.e("Na", "drink-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("smoke_habit")) {
                                prefEdit.putString("smoke_id", id);
                                prefEdit.putString("smoke", val);
                                // prefEdit.putString("smoke_tag", id);
                                str_smoke_id = id;
                                txt_smoke_habit.setText(val);
                                //txt_smoke_habit.setTag(id);
                                Log.e("Na", "smoke-->" + str_smoke_id);
                                Log.e("Na", "smoke-->" + val);
                            } else if (str_list_from.equalsIgnoreCase("income")) {
                                prefEdit.putString("income_id", id);
                                prefEdit.putString("income", val);
                                //prefEdit.putString("income_tag", id);
                                str_income_id = id;
                                txt_income.setText(val);
                                // txt_income.setTag(id);
                                Log.e("Na", "income-->" + str_income_id);
                                Log.e("Na", "income-->" + val);
                            }

                            prefEdit.commit();

                        }
                    } else {
                        if (str_list_from.equalsIgnoreCase("zodiac")) {

                            Toast.makeText(FilterPage.this, "select atleast one zodiac", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("star")) {

                            Toast.makeText(FilterPage.this, "select atleast one star", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("marital_status")) {

                            Toast.makeText(FilterPage.this, "select atleast one married status", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("religion")) {

                            Toast.makeText(FilterPage.this, "select atleast one religion", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("caste")) {

                            Toast.makeText(FilterPage.this, "select atleast one caste", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("mother_ton")) {

                            Toast.makeText(FilterPage.this, "select atleast one mother tongue", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("country")) {

                            Toast.makeText(FilterPage.this, "select atleast one country", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("state")) {

                            Toast.makeText(FilterPage.this, "select atleast one state", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("city")) {

                            Toast.makeText(FilterPage.this, "select atleast one city", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("education")) {

                            Toast.makeText(FilterPage.this, "select atleast one education", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("occupation")) {

                            Toast.makeText(FilterPage.this, "select atleast one occupation", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("phy_status")) {

                            Toast.makeText(FilterPage.this, "select atleast one physical status", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("eat_habit")) {

                            Toast.makeText(FilterPage.this, "select atleast one eating habit", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("drink_habit")) {

                            Toast.makeText(FilterPage.this, "select atleast one drinking habit", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("smoke_habit")) {

                            Toast.makeText(FilterPage.this, "select atleast one smoking habit", Toast.LENGTH_SHORT).show();

                        } else if (str_list_from.equalsIgnoreCase("income")) {

                            Toast.makeText(FilterPage.this, "select atleast one income", Toast.LENGTH_SHORT).show();

                        }
                        Log.e("NN:f", "hash is empty");
                    }


                }
            }
        });

        sp_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListDrawerBean lB = (ListDrawerBean) parent.getItemAtPosition(position);
                String strItems = lB.getStr_list_items();
                String strItems_Id = lB.getStr_list_items_id();
                Log.e("RJ", "sp_from :   Items : " + strItems + ", Id : " + strItems_Id);
                if (str_list_from.equalsIgnoreCase("height")) {
                    strSpinsp_from = String.valueOf(sp_from.getSelectedItemId());
                    str_sp_height_from = strItems;
                    str_from1_height = String.valueOf(position);
                    str_height_from_id = strItems_Id;
                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                    prefEdit.putString("height_from", String.valueOf(str_from1_height));
                    prefEdit.commit();
                    Log.e("LO", "height_from" + str_from1_height);
                } else {
                    strSpinsp_from = strItems;
                    str_from1_age = String.valueOf(position);
                    str_age_from_id = strItems_Id;
                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                    prefEdit.putString("age_from", String.valueOf(str_age_from_id));
                    prefEdit.commit();
                    Log.e("LO", "age_from" + str_from1_age);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListDrawerBean lB = (ListDrawerBean) parent.getItemAtPosition(position);
                String strItems = lB.getStr_list_items();
                String strItems_Id = lB.getStr_list_items_id();
                Log.e("LO", String.valueOf(position));

                Log.e("RJ", "sp_to :   Items : " + strItems + ", Id : " + strItems_Id);
                //    strSpinsp_to = strItems;
                if (str_list_from.equalsIgnoreCase("height")) {
                    strSpinsp_to = String.valueOf(sp_to.getSelectedItemId());
                    str_sp_height_to = strItems;
                    str_to_height = String.valueOf(position);
                    str_height_to_id = strItems_Id;
                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                    prefEdit.putString("height_to", String.valueOf(str_to_height));
                    prefEdit.commit();
                    Log.e("LO", "height_to" + str_to_age);

                } else
                {
                    strSpinsp_to = strItems;
                    str_to_age = String.valueOf(position);
                    str_age_to_id = strItems_Id;
                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                    prefEdit.putString("age_to", String.valueOf(str_to_age));
                    prefEdit.commit();
                    Log.e("LO", "age_to" + str_to_age);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        img_tick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                Log.i("HH", "i came to here.....");

                //myTabsDrawer.closeDrawer(llay_MainSliderParent);

                if (str_from.equalsIgnoreCase("from_to")) {
                    if (str_list_from.equalsIgnoreCase("age")) {
                        str_values = "";
                        str_age = "";
                        if (Integer.parseInt(strSpinsp_to) > Integer.parseInt(strSpinsp_from)) {
                            isOpen = false;
                            str_values = strSpinsp_from + "yrs - " + strSpinsp_to + "yrs";
                            str_age = str_age_from_id + "-" + str_age_to_id;
                            myTabsDrawer.closeDrawer(llay_MainSliderParent);
                            prefEdit.putString("age", str_values);
                            prefEdit.putString("age_id", str_age);
                            // prefEdit.putString("age_tag", str_age);
                            txt_age.setTag(str_age);
                            txt_age.setText(str_values);

                            Log.e("NN:fil_spa", str_values);
                            Log.e("NN:fil_spa", str_age);

                        } else {
                            isOpen = true;
                            Toast.makeText(FilterPage.this, "Check the Age limit !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        str_values = "";
                        str_height = "";
                        if (Integer.parseInt(strSpinsp_to) > Integer.parseInt(strSpinsp_from)) {
                            isOpen = false;
                            str_values = str_sp_height_from + " - " + str_sp_height_to;
                            str_height = str_height_from_id + "-" + str_height_to_id;
                            myTabsDrawer.closeDrawer(llay_MainSliderParent);
                            prefEdit.putString("height", str_values);
                            prefEdit.putString("height_id", str_height);
                            prefEdit.putString("height_tag", str_height);

                            txt_height.setText(str_values);
                            txt_height.setTag(str_height);
                            Log.e("NN:fil_sph", str_values);
                            Log.e("NN:fil_sph", str_height);
                        } else {
                            isOpen = true;
                            Toast.makeText(FilterPage.this, "Check the height limit!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    prefEdit.commit();
                }

            }
        });
        img_cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = false;
                myTabsDrawer.closeDrawer(llay_MainSliderParent);

            }
        });

        img_cross2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = false;
                myTabsDrawer.closeDrawer(llay_MainSliderParent);

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Log.e("GH", String.valueOf(tasks));

                Log.e("NF:", "AGE-->" + str_age);
                Log.e("NF:", "HEIGHT-->" + str_height);
                Log.e("NF:", "MARI-->" + str_marital_id);
                Log.e("NF:", "CHILD-->" + str_child_id);
                Log.e("NF:", "MOTHER_TON-->" + str_mother_ton_id);
                Log.e("NF:", "REL-->" + str_religion_id);
                Log.e("NF:", "CASTE-->" + str_caste_id);
                Log.e("NF:", "ZODIAC-->" + str_zodiac_id);
                Log.e("NF:", "STAR-->" + str_star_id);
                Log.e("NF:", "COUNTRY-->" + str_country_id);
                Log.e("NF:", "STATE-->" + str_state_id);
                Log.e("NF:", "CITY-->" + str_city_id);
                Log.e("NF:", "EDU-->" + str_education_id);
                Log.e("NF:", "OCCUP-->" + str_occup_id);
                Log.e("NF:", "PHY-->" + str_phy_stat_id);
                Log.e("NF:", "DHOSAM-->" + str_dhosam_id);
                Log.e("NF:", "EAT-->" + str_eat_id);
                Log.e("NF:", "DRINK-->" + str_drink_id);
                Log.e("NF:", "SMOKE-->" + str_smoke_id);
                Log.e("NF:", "INCOME-->" + str_income_id);


                FilterCall();


            }
        });

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

        str_id = str_items_id;
        str_values = str_items;
        Log.e("NN:fa", str_id);
        Log.e("NN:fa", str_values);
        Log.e("NN:fa", strIdentify);
        try {
            // alCheckedItems.add(new JSONObject(str_json_obj));
            if (str_json_obj != null) {
                hsFilterGmap = str_json_obj;
                Log.e("NN:fc", String.valueOf(hsFilterGmap));
                strSpinType = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public String get_matches(String str_id, String str_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal, String str_removedVal, String strIdentify) {
        try {
            Log.e("NN:UN", "strIdentify=>" +strIdentify);
            // alCheckedItems.add(new JSONObject(str_json_obj));
            if (strVal != null) {
                str_RemoveVal = str_removedVal;
                //  Log.e("NN:fc1", "E_val"+String.valueOf(hsFilterGmap));
                Log.e("NN:fc1", "E1_val=>" + String.valueOf(strVal));
                for (Map.Entry<String, JSONObject> entry1 : strVal.entrySet()) {
                    hsFilterGmap = strVal;
                    Log.e("NN:fc1", "E1_key" + String.valueOf(entry1.getKey()));
                    Log.e("NN:fc1", String.valueOf(hsFilterGmap));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit, String str_list_name) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        try {

            Log.i("RJ", "RESP : " + providerServicesMonth.toString());
            if (providerServicesMonth.length() > 0) {
                for (int i = 0; i < providerServicesMonth.length(); i++) {
                    ListDrawerBean drawerBean = new ListDrawerBean();
                    jsobj = providerServicesMonth.getJSONObject(i);
                    drawerBean.setStr_list_items_id(jsobj.getString("id"));
                    drawerBean.setStr_list_items(jsobj.getString("value"));
                    beanArrayList.add(drawerBean);
                }
            }

            if (stridentifyEdit.equalsIgnoreCase("from_to")) {
                if (str_list_name.equalsIgnoreCase("age")) {
                    SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from");
                    sp_from.setAdapter(spinadapter);

                    SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to");
                    sp_to.setAdapter(spinadapters);
                } else if (str_list_name.equalsIgnoreCase("height")) {
                    SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from");
                    sp_from.setAdapter(spinadapter);

                    SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to");
                    sp_to.setAdapter(spinadapters);
                }
            } else {
                if (str_list_name.equalsIgnoreCase("eat_habit")) {
                    drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "eat_habit", str_click);
                    list_drawer.setAdapter(drawerFilterAdapter);
                    drawerFilterAdapter.notifyDataSetChanged();
                } else {
                    drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "", str_click);
                    list_drawer.setAdapter(drawerFilterAdapter);
                    drawerFilterAdapter.notifyDataSetChanged();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanArrayList;

    }

    private ArrayList<ListDrawerBean> LoadLayout_Edit(JSONArray providerServicesMonth, String stridentifyEdit, String StrVariable, String strSpinFrom) {
        Log.e("Na1", "StrVariable->" + StrVariable);
        Log.e("Na1", "json->" + providerServicesMonth);
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
        Log.e("JJ", strSpinFrom);
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

            if (stridentifyEdit.equalsIgnoreCase("from_to")) {
                Log.e("JJ", "StrVariable" + StrVariable);
                if (strSpinFrom.equalsIgnoreCase("age")) {
                    SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from", "edit", StrVariable);
                    sp_from.setAdapter(spinadapter);
                    sp_from.setSelection(Integer.parseInt(str_from1_age));
                    SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to", "edit", StrVariable);
                    sp_to.setAdapter(spinadapters);
                    sp_to.setSelection(Integer.parseInt(str_to_age));
                } else if (strSpinFrom.equalsIgnoreCase("height")) {
                    SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from", "edit", StrVariable);
                    sp_from.setAdapter(spinadapter);
                    sp_from.setSelection(Integer.parseInt(str_from1_height));

                    SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to", "edit", StrVariable);
                    sp_to.setAdapter(spinadapters);
                    sp_to.setSelection(Integer.parseInt(str_to_height));
                }

            } else {
                drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", str_click, StrVariable);
                list_drawer.setAdapter(drawerFilterAdapter);
                drawerFilterAdapter.notifyDataSetChanged();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanArrayList;

    }

    public ArrayList<ListDrawerBean> restCallForStateCity(String str_url, final String str_id, final String stridType, final String strVar) {
        Log.e("HH", "strVar : " + strVar);
        Log.e("HH", "stridType : " + stridType);
        Log.e("HH", "str_url : " + str_url);
        Log.e("HH", "str_id : " + str_id);
        return_beanArrayList = new ArrayList<ListDrawerBean>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(UserRegThree.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            //   ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONArray state = jsobj.getJSONArray("Response");
                                // jsonArray_common = state;
                                if (stridType.equalsIgnoreCase("religionid")) {
                                    jsonArray_caste = state;
                                    beanArrayListNew = new ArrayList<ListDrawerBean>();

                                    if (jsonArray_caste.length() > 0) {
                                        for (int i = 0; i < jsonArray_caste.length(); i++) {
                                            ListDrawerBean drawerBean = new ListDrawerBean();
                                            JSONObject providersServiceJSONobject = jsonArray_caste.getJSONObject(i);
                                            drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                            drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                            drawerBean.setStr_religion_id(providersServiceJSONobject.getString("religionid"));
                                            beanArrayListNew.add(drawerBean);

                                        }
                                        Log.e("BN_1", "beanArrayList>" + beanArrayListNew.toString());
                                    }
                                    Log.e("BN_1", "com_ary->" + jsonArray_caste);
                                    if (str_caste_id == null) {
                                        for (int i = 0; i < jsonArray_caste.length(); i++) {
                                            JSONObject jsEach = jsonArray_caste.getJSONObject(i);
                                            ListDrawerBean sB1 = new ListDrawerBean();
                                            sB1.setStr_list_items_id(jsEach.getString("id"));
                                            sB1.setStr_list_items(jsEach.getString("value"));
                                            sB1.setStr_religion_id(jsEach.getString("religionid"));
                                            if (g_caste.equalsIgnoreCase(sB1.getStr_list_items())) {
                                                Log.e("BN", "val->" + sB1.getStr_list_items());
                                                Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                                Log.e("BN", "equall");
                                                str_caste_id = sB1.getStr_list_items_id();

                                                SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                                prefEdit1.putString("caste_id", str_caste_id);
                                                prefEdit1.commit();
                                                Log.e("BN", str_caste_id);

                                                //LoadLayout_Edit(jsonArray_caste, "rad_chk", str_caste_id, "");
                                            }

                                        }
                                    }
                                    if (strVar.equalsIgnoreCase("")) {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayListNew, (MyInterface) context, "edit", "checkbox", str_caste_id);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    } else {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayListNew, (MyInterface) context, "edit", "checkbox", strVar);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    }


                                }


                                if (stridType.equalsIgnoreCase("rasiid")) {
                                    jsonArray_star = state;
                                    ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                    if (jsonArray_star.length() > 0) {
                                        for (int i = 0; i < jsonArray_star.length(); i++) {
                                            ListDrawerBean drawerBean = new ListDrawerBean();
                                            JSONObject providersServiceJSONobject = jsonArray_star.getJSONObject(i);
                                            drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                            drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                            beanArrayList.add(drawerBean);
                                        }
                                    }
                                    Log.e("BN_1", "com_ary->" + jsonArray_star);
                                    if (str_star_id == null) {
                                        for (int i = 0; i < jsonArray_star.length(); i++) {
                                            JSONObject jsEach = jsonArray_star.getJSONObject(i);
                                            ListDrawerBean sB1 = new ListDrawerBean();
                                            sB1.setStr_list_items_id(jsEach.getString("id"));
                                            sB1.setStr_list_items(jsEach.getString("value"));
                                            if (g_star.equalsIgnoreCase(sB1.getStr_list_items())) {
                                                Log.e("BN", "val->" + sB1.getStr_list_items());
                                                Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                                Log.e("BN", "equall");
                                                str_star_id = sB1.getStr_list_items_id();
                                                SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                                prefEdit1.putString("star_id", str_star_id);
                                                prefEdit1.commit();
                                                Log.e("BN", str_star_id);

                                            }

                                        }
                                    }
                                    if (strVar.equalsIgnoreCase("")) {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", "checkbox", str_star_id);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    } else {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", "checkbox", strVar);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    }

                                }
                                if (stridType.equalsIgnoreCase("countryid")) {
                                    jsonArray_state = state;
                                    ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                    if (jsonArray_state.length() > 0) {
                                        for (int i = 0; i < jsonArray_state.length(); i++) {
                                            ListDrawerBean drawerBean = new ListDrawerBean();
                                            JSONObject providersServiceJSONobject = jsonArray_state.getJSONObject(i);
                                            drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                            drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                            beanArrayList.add(drawerBean);
                                        }
                                    }
                                    Log.e("BN_1", "com_ary->" + jsonArray_state);
                                    if (str_state_id == null) {
                                        for (int i = 0; i < jsonArray_state.length(); i++) {
                                            JSONObject jsEach = jsonArray_state.getJSONObject(i);
                                            ListDrawerBean sB1 = new ListDrawerBean();
                                            sB1.setStr_list_items_id(jsEach.getString("id"));
                                            sB1.setStr_list_items(jsEach.getString("value"));
                                            if (g_state.equalsIgnoreCase(sB1.getStr_list_items())) {
                                                Log.e("BN", "val->" + sB1.getStr_list_items());
                                                Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                                Log.e("BN", "equall");
                                                str_state_id = sB1.getStr_list_items_id();
                                                SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                                prefEdit1.putString("state_id", str_state_id);
                                                prefEdit1.commit();
                                                Log.e("BN", str_state_id);

                                            }

                                        }
                                        return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "stateid", "");
                                    }
                                    if (strVar.equalsIgnoreCase("")) {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", "checkbox", str_state_id);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    }

                                    else {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", "checkbox", strVar);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    }
                                }
                                if (stridType.equalsIgnoreCase("stateid")) {
                                    jsonArray_city = state;
                                    ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                                    if (jsonArray_city.length() > 0) {
                                        for (int i = 0; i < jsonArray_city.length(); i++) {
                                            ListDrawerBean drawerBean = new ListDrawerBean();
                                            JSONObject providersServiceJSONobject = jsonArray_city.getJSONObject(i);
                                            drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                            drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                            beanArrayList.add(drawerBean);
                                        }
                                    }
                                    Log.e("BN_1", "com_ary->" + jsonArray_city);
                                    if (str_city_id == null) {
                                        for (int i = 0; i < jsonArray_city.length(); i++) {
                                            JSONObject jsEach = jsonArray_city.getJSONObject(i);
                                            ListDrawerBean sB1 = new ListDrawerBean();
                                            sB1.setStr_list_items_id(jsEach.getString("id"));
                                            sB1.setStr_list_items(jsEach.getString("value"));
                                            if (g_city.equalsIgnoreCase(sB1.getStr_list_items())) {
                                                Log.e("BN", "val->" + sB1.getStr_list_items());
                                                Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                                Log.e("BN", "equall");
                                                str_city_id = sB1.getStr_list_items_id();
                                                SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                                prefEdit1.putString("city_id", str_city_id);
                                                prefEdit1.commit();
                                                Log.e("BN", str_city_id);

                                            }

                                        }
                                    }
                                    if (strVar.equalsIgnoreCase("")) {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", "checkbox", str_city_id);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
                                    }

                                    else {
                                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "edit", "checkbox", strVar);
                                        list_drawer.setAdapter(drawerFilterAdapter);
                                        drawerFilterAdapter.notifyDataSetChanged();
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
                        //  Toast.makeText(UserRegThree.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(UserRegThree.this, "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(FilterPage.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }

    public void FilterCall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "filter.php",
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
                                Intent i = new Intent(FilterPage.this, Matches.class);
                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("filter_json", response);
                                prefEdit.commit();
                                startActivity(i);
                                finish();
                                Toast.makeText(FilterPage.this, "Suceess!!", Toast.LENGTH_SHORT).show();
                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(FilterPage.this, "No data available", Toast.LENGTH_SHORT).show();
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

                                // Toast.makeText(UserRegOne.this, "res : " + res, Toast.LENGTH_LONG).show();

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
                if (str_age.length() > 0) {
                    params.put("age", str_age);
                }
                if (str_height.length() > 0) {
                    params.put("height", str_height);
                }
                if (str_marital_id.length() > 0) {
                    params.put("maritalstatus", str_marital_id);
                }
                if (str_child_id.length() > 0) {
                    params.put("noofchild", str_child_id);
                }
                if (str_religion_id.length() > 0) {
                    params.put("religion", str_religion_id);
                }
                if (str_caste_id.length() > 0) {
                    params.put("caste", str_caste_id);
                }
                if (str_zodiac_id.length() > 0) {
                    params.put("zodiac", str_zodiac_id);
                }
                if (str_star_id.length() > 0) {
                    params.put("star", str_star_id);
                }
                if (str_country_id.length() > 0) {
                    params.put("country", str_country_id);
                }
                if (str_state_id.length() > 0) {
                    params.put("state", str_state_id);
                }
                if (str_city_id.length() > 0) {
                    params.put("city", str_city_id);
                }
                if (str_education_id.length() > 0) {
                    params.put("education", str_education_id);
                }
                if (str_occup_id.length() > 0) {
                    params.put("occupation", str_occup_id);
                }
                if (str_dhosam_id.length() > 0) {
                    params.put("chevvaidhosham", str_dhosam_id);
                }
                if (str_drink_id.length() > 0) {
                    params.put("drinkinghabit", str_drink_id);
                }
                if (str_smoke_id.length() > 0) {
                    params.put("smokinghabit", str_smoke_id);
                }
                if (str_income_id.length() > 0) {
                    params.put("annualincome", str_income_id);
                }
                if (str_eat_id.length() > 0) {
                    params.put("eatinghabit", str_eat_id);
                }
                if (str_phy_stat_id.length() > 0) {
                    params.put("physicalstatus", str_phy_stat_id);
                }
                if (str_mother_ton_id.length() > 0) {
                    params.put("mothertongue", str_mother_ton_id);
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

//30Secs
        RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(FilterPage.this);
        requestQueue.add(stringRequest);

    }

    public void EditDisplayUserCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "user_data.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                JSONObject responseJsonObj = jsobj.getJSONObject("Response");

                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
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

                                String[] p_age = partner_age.split("-");
                                String str_from_age_p = p_age[0];
                                String to_age = p_age[1];
                                Log.e("AA", "p_age_from->" + str_from_age_p);
                                Log.e("AA", "p_age_to->" + to_age);
                                String[] p_to_age = to_age.split(" ");
                                String str_to_age_p = p_to_age[0];
                                Log.e("AA", "p_age_to_final->" + str_to_age_p);

                                if (partner_married_status.equalsIgnoreCase("unmarried") || partner_married_status.equalsIgnoreCase("not available")) {
                                    ll_children.setVisibility(View.GONE);
                                    //ll_pchildr.setVisibility(View.GONE);
                                } else {
                                    ll_children.setVisibility(View.VISIBLE);
                                    if (no_of_pchild.equalsIgnoreCase("yes")) {
                                        rb_count_child_yes.setChecked(true);
                                    } else {
                                        rb_count_child_no.setChecked(true);
                                    }

                                    //ll_pchildr.setVisibility(View.VISIBLE);
                                    //txt_groom_child.setText(no_of_pchild);
                                }
                                String[] str1 = partner_height.split("-");
                                String str_hFrom_p = str1[0];
                                String str_hTO_p = str1[1];


                                txt_age.setText(partner_age);
                                txt_height.setText(partner_height);
                                txt_marital_status.setText(partner_married_status);
                                txt_mother_ton.setText(partner_mother_tongue);
                                txt_phy_stat.setText(partner_physical_status);
                                txt_eat_habit.setText(partner_eating_habits);
                                txt_drink_habit.setText(partner_drinking_habits);
                                txt_smoke_habit.setText(partner_smoking_habits);

                                g_mother_ton = partner_mother_tongue;
                                g_married = partner_married_status;
                                g_phy_stat = partner_physical_status;
                                g_eat = partner_eating_habits;
                                g_drink = partner_drinking_habits;
                                g_smoke = partner_smoking_habits;

                                prefEdit.putString("age", partner_age);
                                prefEdit.putString("height", partner_height);
                                prefEdit.putString("mother", partner_mother_tongue);
                                prefEdit.putString("mari", partner_married_status);
                                prefEdit.putString("phy", partner_physical_status);
                                prefEdit.putString("eat", partner_eating_habits);
                                prefEdit.putString("drink", partner_drinking_habits);
                                prefEdit.putString("smoke", partner_smoking_habits);


                                for (int i = 0; i < jsonArray_age.length(); i++) {
                                    JSONObject jsEach = jsonArray_age.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (str_from_age_p.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");

                                        str_age_from_first = sB1.getStr_list_items_id();
                                     /*   SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("mother_id", str_age_from_id);
                                        prefEdit1.commit();*/
                                        Log.e("BN", str_age_from_first);
                                    }

                                }

                                for (int i = 0; i < jsonArray_age.length(); i++) {
                                    JSONObject jsEach = jsonArray_age.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (str_to_age_p.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");

                                        str_age_to_first = sB1.getStr_list_items_id();
                                     /*   SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("mother_id", str_age_from_id);
                                        prefEdit1.commit();*/
                                        Log.e("BN", str_age_to_first);
                                    }

                                }

                                SharedPreferences.Editor prefEditP = gD.prefs.edit();
                                str_age = str_age_from_first + "-" + str_age_to_first;
                                Log.e("BN", str_age);
                                prefEditP.putString("age_id", str_age_from_first + "-" + str_age_to_first);
                                prefEditP.commit();


                                for (int i = 0; i < jsonArray_height.length(); i++) {
                                    JSONObject jsEach = jsonArray_height.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (str_hFrom_p.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");

                                        str_hFrom = sB1.getStr_list_items_id();
                                     /*   SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("mother_id", str_age_from_id);
                                        prefEdit1.commit();*/
                                        Log.e("BN", str_hFrom);
                                    }

                                }
                                for (int i = 0; i < jsonArray_height.length(); i++) {
                                    JSONObject jsEach = jsonArray_height.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (str_hTO_p.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");

                                        str_hTO = sB1.getStr_list_items_id();
                                     /*   SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("mother_id", str_age_from_id);
                                        prefEdit1.commit();*/
                                        Log.e("BN", str_hTO);
                                    }

                                }

                                Log.e("LP", str_hFrom);
                                Log.e("LP", str_hTO);
                                SharedPreferences.Editor prefEditH = gD.prefs.edit();
                                str_height = str_hFrom + "-" + str_hTO;
                                Log.e("LP", str_height);
                                prefEditH.putString("height_id", str_hFrom + "-" + str_hTO);
                                prefEditH.commit();

                                for (int i = 0; i < jsonArray_marital_stat.length(); i++) {
                                    JSONObject jsEach = jsonArray_marital_stat.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_married.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_marital_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("mari_id", str_marital_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_marital_id);

                                    }

                                }

                                for (int i = 0; i < jsonArray_mother_ton.length(); i++) {
                                    JSONObject jsEach = jsonArray_mother_ton.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_mother_ton.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");

                                        str_mother_ton_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("mother_id", str_mother_ton_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_mother_ton_id);


                                    }

                                }

                                for (int i = 0; i < jsonArray_phy_stat.length(); i++) {
                                    JSONObject jsEach = jsonArray_phy_stat.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_phy_stat.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_phy_stat_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("phy_id", str_phy_stat_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_phy_stat_id);

                                    }

                                }

                                for (int i = 0; i < jsonArray_eat.length(); i++) {
                                    JSONObject jsEach = jsonArray_eat.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_eat.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_eat_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("eat_id", str_eat_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_eat_id);

                                    }

                                }
                                for (int i = 0; i < jsonArray_drink.length(); i++) {
                                    JSONObject jsEach = jsonArray_drink.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_drink.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_drink_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("drink_id", str_drink_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_drink_id);

                                    }

                                }
                                for (int i = 0; i < jsonArray_smoke.length(); i++) {
                                    JSONObject jsEach = jsonArray_smoke.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_smoke.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_smoke_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("smoke_id", str_smoke_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_smoke_id);

                                    }

                                }

                                //edit partner religious info

                                JSONObject partnerReligiousJsonObj = partnerPrefJsonObj.getJSONObject("Religious Information");
                                String partner_religion = partnerReligiousJsonObj.getString("religious");
                                String partner_gothram = partnerReligiousJsonObj.getString("Gothram");
                                String partner_caste = partnerReligiousJsonObj.getString("Caste");
                                String partner_sub_caste = partnerReligiousJsonObj.getString("Sub Caste");
                                String partner_zodiac_sign = partnerReligiousJsonObj.getString("Zodaic Sign");
                                String partner_star = partnerReligiousJsonObj.getString("Star");
                                String partner_dhosham = partnerReligiousJsonObj.getString("Dhosham");
                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                                txt_religion.setText(partner_religion);
                                txt_caste.setText(partner_caste);
                                txt_zodiac.setText(partner_zodiac_sign);
                                txt_star.setText(partner_star);

                                g_religion = partner_religion;
                                g_caste = partner_caste;
                                g_zodiac = partner_zodiac_sign;
                                g_star = partner_star;
                                prefEdit.putString("religion", partner_religion);
                                prefEdit.putString("caste", partner_caste);
                                prefEdit.putString("zodiac", partner_zodiac_sign);
                                prefEdit.putString("star", partner_star);

                                for (int i = 0; i < jsonArray_religion.length(); i++) {
                                    JSONObject jsEach = jsonArray_religion.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_religion.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_religion_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("religion_id", str_religion_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_religion_id);

                                    }

                                }

                                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid", "");


                                for (int i = 0; i < jsonArray_zodiac.length(); i++) {
                                    JSONObject jsEach = jsonArray_zodiac.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));
                                    if (g_zodiac.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_zodiac_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("zodiac_id", str_zodiac_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_zodiac_id);

                                    }

                                }

                                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "rasiid", "");


                                //edit professional(Groom)

                                JSONObject partnerProfessionalJsonObj = partnerPrefJsonObj.getJSONObject("Professional Information");

                                Log.i("HH", "partnerProfessionalJsonObj : " + partnerProfessionalJsonObj);
                                String partner_qualif = partnerProfessionalJsonObj.getString("Qualification");
                                String partner_ocuupation = partnerProfessionalJsonObj.getString("Occupation");
                                // String partner_qualif_det = partnerProfessionalJsonObj.getString("Qualification in Detail");
                                String partner_emp_in = partnerProfessionalJsonObj.getString("Employed in");
                                String partner_annual_income = partnerProfessionalJsonObj.getString("Annual Income");
                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");

                                txt_education.setText(partner_qualif);
                                txt_occupation.setText(partner_ocuupation);
                                txt_income.setText(partner_annual_income);

                                g_education = partner_qualif;
                                g_occupation = partner_ocuupation;
                                g_income = partner_annual_income;
                                prefEdit.putString("edu", partner_qualif);
                                prefEdit.putString("occup", partner_ocuupation);
                                prefEdit.putString("income", partner_annual_income);


                                for (int i = 0; i < jsonArray_edu.length(); i++) {
                                    JSONObject jsEach = jsonArray_edu.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));


                                    if (g_education.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_education_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("edu_id", str_education_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_education_id);

                                    }


                                }

                                for (int i = 0; i < jsonArray_occup.length(); i++) {
                                    JSONObject jsEach = jsonArray_occup.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));

                                    if (g_occupation.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_occup_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("occup_id", str_occup_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_occup_id);

                                    }

                                }

                                for (int i = 0; i < jsonArray_income.length(); i++) {
                                    JSONObject jsEach = jsonArray_income.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));

                                    if (g_income.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_income_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("income_id", str_income_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_income_id);

                                    }

                                }
                                //partner(location)

                                JSONObject partnerLocationJsonObj = partnerPrefJsonObj.getJSONObject("Location");

                                Log.i("HH", "partnerLocationJsonObj : " + partnerLocationJsonObj);
                                String partner_country = partnerLocationJsonObj.getString("Country");
                                String partner_state = partnerLocationJsonObj.getString("State");
                                String partner_city = partnerLocationJsonObj.getString("City");
                                String partner_citizen = partnerLocationJsonObj.getString("Citizenship");


                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");
                                txt_country.setText(partner_country);
                                txt_city.setText(partner_city);
                                txt_state.setText(partner_state);


                                g_country = partner_country;
                                g_city = partner_city;
                                g_state = partner_state;
                                prefEdit.putString("country", partner_country);
                                prefEdit.putString("city", partner_city);
                                prefEdit.putString("state", partner_state);

                                prefEdit.commit();


                                for (int i = 0; i < jsonArray_country.length(); i++) {
                                    JSONObject jsEach = jsonArray_country.getJSONObject(i);
                                    ListDrawerBean sB1 = new ListDrawerBean();
                                    sB1.setStr_list_items_id(jsEach.getString("id"));
                                    sB1.setStr_list_items(jsEach.getString("value"));

                                    if (g_country.equalsIgnoreCase(sB1.getStr_list_items())) {
                                        Log.e("BN", "val->" + sB1.getStr_list_items());
                                        Log.e("BN", "id->" + sB1.getStr_list_items_id());
                                        Log.e("BN", "equall");
                                        str_country_id = sB1.getStr_list_items_id();
                                        SharedPreferences.Editor prefEdit1 = gD.prefs.edit();
                                        prefEdit1.putString("country_id", str_country_id);
                                        prefEdit1.commit();
                                        Log.e("BN", str_country_id);

                                    }

                                }
                                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "countryid", "");

                                //partner(family det)

                                JSONObject partnerFamDetJsonObj = partnerPrefJsonObj.getJSONObject("Family Details");
                                Log.i("HH", "partnerFamDetJsonObj : " + partnerFamDetJsonObj);

                                String partner_fam_val = partnerFamDetJsonObj.getString("Family Values");
                                String partner_fam_type = partnerFamDetJsonObj.getString("Family Type");
                                String partner_fam_status = partnerFamDetJsonObj.getString("Family Status");
                                String partner_father_occ = partnerFamDetJsonObj.getString("Father Occupation");
                                String partner_mother_occ = partnerFamDetJsonObj.getString("Mother Occupation");

                                // String strImageURL = "http://www.easy-marry.com/images/" + matchesJSONObj.getString("profileImage");


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
        RequestQueue requestQueue = Volley.newRequestQueue(FilterPage.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

    public void registerSelect() {

        try {
            Log.i("HH", "strResp : " + str_registerSelectJSON);
            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

            JSONObject jsobj = new JSONObject(str_registerSelectJSON);

            Log.i("HH", "strResp : " + str_registerSelectJSON);
            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                jsonArray_age = jsobj.getJSONArray("Age");
                jsonArray_height = jsobj.getJSONArray("Height");
                jsonArray_marital_stat = jsobj.getJSONArray("martialstatus");
                jsonArray_mother_ton = jsobj.getJSONArray("mothertongue");
                jsonArray_religion = jsobj.getJSONArray("religion");
                jsonArray_zodiac = jsobj.getJSONArray("Zodiac");
                jsonArray_country = jsobj.getJSONArray("country");
                jsonArray_edu = jsobj.getJSONArray("qualification");
                jsonArray_occup = jsobj.getJSONArray("occupation");
                jsonArray_phy_stat = jsobj.getJSONArray("Physical status");
                jsonArray_dhosam = jsobj.getJSONArray("Chevvai dhosam");
                jsonArray_eat = jsobj.getJSONArray("Food habit");
                jsonArray_drink = jsobj.getJSONArray("Drinking habit");
                jsonArray_smoke = jsobj.getJSONArray("Smoking habit");
                jsonArray_income = jsobj.getJSONArray("Income");

                for (int i = 0; i < jsonArray_zodiac.length(); i++) {
                    JSONObject jsEach = jsonArray_zodiac.getJSONObject(i);
                    ListDrawerBean sB1 = new ListDrawerBean();
                    sB1.setStr_list_items_id(jsEach.getString("id"));
                    sB1.setStr_list_items(jsEach.getString("value"));
                    beanArrayList.add(sB1);
                }
                if (str_from.equalsIgnoreCase("from_to")) {
                    SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from");
                    sp_from.setAdapter(spinadapter);

                    SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to");
                    sp_to.setAdapter(spinadapters);
                } else {
                    drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "height", str_click);
                    list_drawer.setAdapter(drawerFilterAdapter);
                    drawerFilterAdapter.notifyDataSetChanged();
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
