package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
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
import com.example.easy_marry.Adapter.ListDrawerFilterAdapter;
import com.example.easy_marry.Adapter.SpinnerAdapter;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.genericclasses.GeneralData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by android2 on 16/11/16.
 */
public class FilterPageTest extends Activity implements MyInterface {
    ImageView img_back, img_tick1, img_tick2, img_cross1, img_cross2, img_search;
    Context context;
    GeneralData gD;
    EditText et_search;
    ListView list_drawer;
    DrawerLayout myTabsDrawer;
    Button btn_search;
    LinearLayout ll_drawer_from_to, ll_drawer_rad_chk, llay_MainSliderParent, ll_children;
    TextView txt_reset, txt_age, txt_height, txt_marital_status, txt_child, txt_mother_ton, txt_religion,
            txt_caste, txt_zodiac, txt_star, txt_country, txt_state, txt_city,
            txt_education, txt_occupation, txt_phy_stat, txt_dhosam, txt_eat_habit, txt_drink_habit, txt_smoke_habit, txt_income, txt_filter_header;

    String str_user_id, str_age_from_id, str_age_to_id, str_height_from_id, str_height_to_id, str_marital_id, str_child_id = "No", str_mother_ton_id, str_religion_id,
            str_caste_id, str_zodiac_id, str_star_id, str_country_id, str_state_id, str_city_id, str_education_id, str_occup_id, str_phy_stat_id,
            str_dhosam_id = "2", str_eat_id, str_drink_id, str_smoke_id, str_income_id,str_age,str_height;
    ListDrawerFilterAdapter drawerFilterAdapter;

    JSONArray jsonArray_age, jsonArray_height, jsonArray_marital_stat, jsonArray_mother_ton, jsonArray_religion, jsonArray_zodiac, jsonArray_country,
            jsonArray_common, jsonArray_edu, jsonArray_occup, jsonArray_phy_stat, jsonArray_dhosam, jsonArray_eat, jsonArray_drink, jsonArray_smoke, jsonArray_income;
    Spinner sp_from, sp_to;
    int sp;
    String str_id, str_values, str_from = "from_to", str_click, str_list_from;

    String strSpinsp_to = "";
    String strSpinsp_from = "";
    String str_sp_height_from, str_sp_height_to;
    ArrayList<JSONObject> alCheckedItems = new ArrayList<JSONObject>();
    RadioButton rb_dhosham_yes, rb_dhosham_no, rb_dhosham_doesnt_matter, rb_count_child_no, rb_count_child_yes;
    HashMap<String, JSONObject> hsFilterGmap;
    ArrayList<ListDrawerBean> return_beanArrayList;
    String strSpinType = "";

    RelativeLayout rlayMain;

GridView gd_lang;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_page_test);
        context = this;
        gD = new GeneralData(context);

     if(GeneralData.ycount==0){
        Log.e("EF", "on1->"+String.valueOf(GeneralData.ycount));
     }
        else if(GeneralData.ycount==1){

         Log.e("EF", "on2->"+String.valueOf(GeneralData.ycount));
     }
        else{
         Log.e("EF", "on8->"+String.valueOf(GeneralData.ycount));
     }

        str_user_id = gD.prefs.getString("userid", null);
        gd_lang=(GridView)findViewById(R.id.grid_mar);
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

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        myTabsDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        /*str_dhosam_id = gD.prefs.getString("dhosam_id", null);
        txt_dhosam.setText(gD.prefs.getString("dhosam", null));
        Log.e("Na", "dhosam-->" + str_dhosam_id);
        Log.e("Na", "dhosam-->" + gD.prefs.getString("dhosam", null));*/


       Toast.makeText(FilterPageTest.this, "hiiii", Toast.LENGTH_SHORT).show();
        //get Value


      /*  SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson1 = new Gson();
        String json1 = sharedPrefs1.getString("MK", null);
        Type type1 = new TypeToken<HashMap<String, JSONObject>>() {}.getType();
        HashMap<String, JSONObject> arrayList = gson1.fromJson(json1, type1);

        Log.e("MK1", "hiiiiii");
        Log.e("MK1", "two_arraylist-->"+ String.valueOf(arrayList));
        Log.e("MK1", "two-->"+ String.valueOf(json1));

*/





        str_zodiac_id = gD.prefs.getString("zodiac_id", null);
        txt_zodiac.setText(gD.prefs.getString("zodiac", null));
        txt_zodiac.setTag(gD.prefs.getString("zodiac_tag", null));


        str_star_id = gD.prefs.getString("star_id", null);
        txt_star.setText(gD.prefs.getString("star", null));
        txt_star.setTag(gD.prefs.getString("star_tag", null));


        str_marital_id = gD.prefs.getString("mari_id", null);
        txt_marital_status.setText(gD.prefs.getString("mari", null));
        txt_marital_status.setTag(gD.prefs.getString("mari_tag", null));

        if (gD.prefs.getString("mari", null) != null) {
            if (!gD.prefs.getString("mari", null).equalsIgnoreCase("Unmarried")) {
                ll_children.setVisibility(View.VISIBLE);
            } else {
                ll_children.setVisibility(View.GONE);
            }
        }


        str_religion_id = gD.prefs.getString("religion_id", null);
        txt_religion.setText(gD.prefs.getString("religion", null));
        txt_religion.setTag(gD.prefs.getString("religion_tag", null));

        str_caste_id = gD.prefs.getString("caste_id", null);
        txt_caste.setText(gD.prefs.getString("caste", null));
        txt_caste.setTag(gD.prefs.getString("caste_tag", null));

        str_mother_ton_id = gD.prefs.getString("mother_id", null);
        txt_mother_ton.setText(gD.prefs.getString("mother", null));
        txt_mother_ton.setTag(gD.prefs.getString("mother_tag", null));

        str_country_id = gD.prefs.getString("country_id", null);
        txt_country.setText(gD.prefs.getString("country", null));
        txt_country.setTag(gD.prefs.getString("country_tag", null));

        str_state_id = gD.prefs.getString("state_id", null);
        txt_state.setText(gD.prefs.getString("state", null));
        txt_state.setTag(gD.prefs.getString("state_tag", null));

        str_city_id = gD.prefs.getString("city_id", null);
        txt_city.setText(gD.prefs.getString("city", null));
        txt_city.setTag(gD.prefs.getString("city_tag", null));

        str_education_id = gD.prefs.getString("edu_id", null);
        txt_education.setText(gD.prefs.getString("edu", null));
        txt_education.setTag(gD.prefs.getString("edu_tag", null));

        str_occup_id = gD.prefs.getString("occup_id", null);
        txt_occupation.setText(gD.prefs.getString("occup", null));
        txt_occupation.setTag(gD.prefs.getString("occup_tag", null));

        str_phy_stat_id = gD.prefs.getString("phy_id", null);
        txt_phy_stat.setText(gD.prefs.getString("phy", null));
        txt_phy_stat.setTag(gD.prefs.getString("phy_tag", null));

        str_eat_id = gD.prefs.getString("eat_id", null);
        txt_eat_habit.setText(gD.prefs.getString("eat", null));
        txt_eat_habit.setTag(gD.prefs.getString("eat_tag", null));

        str_smoke_id = gD.prefs.getString("smoke_id", null);
        txt_smoke_habit.setText(gD.prefs.getString("smoke", null));
        txt_smoke_habit.setTag(gD.prefs.getString("smoke_tag", null));

        str_drink_id = gD.prefs.getString("drink_id", null);
        txt_drink_habit.setText(gD.prefs.getString("drink", null));
        txt_drink_habit.setTag(gD.prefs.getString("drink_tag", null));

        str_income_id = gD.prefs.getString("income_id", null);
        txt_income.setText(gD.prefs.getString("income", null));
        txt_income.setTag(gD.prefs.getString("income_tag", null));


        txt_age.setText(gD.prefs.getString("age", null));
        txt_age.setTag(gD.prefs.getString("age_tag", null));
        str_age = gD.prefs.getString("age_id", null);


        txt_height.setText(gD.prefs.getString("height", null));
        txt_height.setTag(gD.prefs.getString("height_tag", null));
        str_height = gD.prefs.getString("height_id", null);

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

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int densityDpi = dm.densityDpi;

        int weight = dm.widthPixels;
        int lay_wid = (int) (weight / 3);

        Log.e("NN:fil", String.valueOf(densityDpi));
        Log.e("NN:fil", String.valueOf(weight));
        Log.e("NN:fil", String.valueOf(lay_wid));

        //For slider dynamic Width


        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) llay_MainSliderParent.getLayoutParams();
        lp.width = lay_wid + 230;
        llay_MainSliderParent.setLayoutParams(lp);

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
                txt_age.setTag(gD.prefs.getString("age_tag", null));
                str_age = gD.prefs.getString("age_id", null);

                if (txt_age.getText().toString().trim().length() > 0 && txt_age.getTag().toString().length() > 0) {
                    //LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", txt_marital_status.getTag().toString().trim());
                    LoadLayout_Edit(jsonArray_age, "from_to", txt_age.getTag().toString().trim());
                    //LoadLayout(jsonArray_age, "from_to", "age");
                    Log.e("NM", "hii");
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
                LoadLayout(jsonArray_height, "from_to", "height");
                str_list_from = "height";
                txt_filter_header.setText("HEIGHT");
                txt_height.setText(gD.prefs.getString("height", null));
                txt_height.setTag(gD.prefs.getString("height_tag", null));
                str_height = gD.prefs.getString("height_id", null);

                myTabsDrawer.openDrawer(llay_MainSliderParent);


            }
        });
        txt_marital_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                txt_marital_status.setTag(gD.prefs.getString("mari_tag", null));

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_marital_stat, "rad_chk", "marital_stat");

                if (txt_marital_status.getText().toString().trim().length() > 0 && txt_marital_status.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_marital_stat, "rad_chk", txt_marital_status.getTag().toString().trim());
                    Log.e("NM", "hii");
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_mother_ton.setTag(gD.prefs.getString("mother_tag", null));

                if (txt_mother_ton.getText().toString().trim().length() > 0 && txt_mother_ton.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_mother_ton, "rad_chk", txt_mother_ton.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_religion.setTag(gD.prefs.getString("religion_tag", null));

                if (txt_religion.getText().toString().trim().length() > 0 && txt_religion.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_religion, "rad_chk", txt_religion.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "caste";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "caste.php", str_religion_id, "religionid");




              /*  str_caste_id = gD.prefs.getString("caste_id", null);
                txt_caste.setText(gD.prefs.getString("caste", null));
                txt_caste.setTag(gD.prefs.getString("caste_tag", null));


                if (txt_caste.getText().toString().trim().length() > 0 && txt_caste.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_common, "rad_chk", txt_caste.getTag().toString().trim());
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
                });*/
            }
        });

        txt_zodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                txt_zodiac.setTag(gD.prefs.getString("zodiac_tag", null));

                if (txt_zodiac.getText().toString().trim().length() > 0 && txt_zodiac.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_zodiac, "rad_chk", txt_zodiac.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "star";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "star.php", str_zodiac_id, "rasiid");
                str_star_id = gD.prefs.getString("star_id", null);
                txt_star.setText(gD.prefs.getString("star", null));
                txt_star.setTag(gD.prefs.getString("star_tag", null));

                if (txt_star.getText().toString().trim().length() > 0 && txt_star.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_common, "rad_chk", txt_star.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_country.setTag(gD.prefs.getString("country_tag", null));
                if (txt_country.getText().toString().trim().length() > 0 && txt_country.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_country, "rad_chk", txt_country.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "state";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "state.php", str_country_id, "countryid");
                str_state_id = gD.prefs.getString("state_id", null);
                txt_state.setText(gD.prefs.getString("state", null));
                txt_state.setTag(gD.prefs.getString("state_tag", null));

                if (txt_state.getText().toString().trim().length() > 0 && txt_state.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_common, "rad_chk", txt_state.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                isOpen = true;
                str_from = "rad_chk";
                str_click = "checkbox";
                et_search.setText("");
                str_list_from = "city";
                ll_drawer_from_to.setVisibility(View.GONE);
                ll_drawer_rad_chk.setVisibility(View.VISIBLE);
                myTabsDrawer.openDrawer(llay_MainSliderParent);

                return_beanArrayList = restCallForStateCity(GeneralData.LOCAL_IP + "city.php", str_state_id, "stateid");

                str_city_id = gD.prefs.getString("city_id", null);
                txt_city.setText(gD.prefs.getString("city", null));
                txt_city.setTag(gD.prefs.getString("city_tag", null));

                if (txt_city.getText().toString().trim().length() > 0 && txt_city.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_common, "rad_chk", txt_city.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_education.setTag(gD.prefs.getString("edu_tag", null));
                if (txt_education.getText().toString().trim().length() > 0 && txt_education.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_edu, "rad_chk", txt_education.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_occupation.setTag(gD.prefs.getString("occup_tag", null));

                if (txt_occupation.getText().toString().trim().length() > 0 && txt_occupation.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_occup, "rad_chk", txt_occupation.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_phy_stat.setTag(gD.prefs.getString("phy_tag", null));

                if (txt_country.getText().toString().trim().length() > 0 && txt_country.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_phy_stat, "rad_chk", txt_country.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_eat_habit.setTag(gD.prefs.getString("eat_tag", null));

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_eat, "rad_chk", "eat_habit");

                if (txt_eat_habit.getText().toString().trim().length() > 0 && txt_eat_habit.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_eat, "rad_chk", txt_eat_habit.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_drink_habit.setTag(gD.prefs.getString("drink_tag", null));
                if (txt_drink_habit.getText().toString().trim().length() > 0 && txt_drink_habit.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_drink, "rad_chk", txt_drink_habit.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_smoke_habit.setTag(gD.prefs.getString("smoke_tag", null));

                if (txt_smoke_habit.getText().toString().trim().length() > 0 && txt_smoke_habit.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_smoke, "rad_chk", txt_smoke_habit.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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
                txt_income.setTag(gD.prefs.getString("income_tag", null));
                if (txt_income.getText().toString().trim().length() > 0 && txt_income.getTag().toString().length() > 0) {
                    LoadLayout_Edit(jsonArray_income, "rad_chk", txt_income.getTag().toString().trim());
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

                        drawerFilterAdapter = new ListDrawerFilterAdapter(context, filteredList, (MyInterface) context, "", str_click,"");
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

                        Toast.makeText(context, "Jid: " + id + " Jval: " + val, Toast.LENGTH_LONG).show();
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
                            prefEdit.putString("zodiac_tag", id);

                            str_zodiac_id = id;
                            txt_zodiac.setText(val);
                            txt_zodiac.setTag(id);
                            Log.e("Na", "zodiac-->" + str_zodiac_id);
                            Log.e("Na", "zodiac-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("star")) {

                            prefEdit.putString("star_id", id);
                            prefEdit.putString("star", val);
                            prefEdit.putString("star_tag", id);
                            str_star_id = id;
                            txt_star.setText(val);
                            txt_star.setTag(id);
                            Log.e("Na", "star-->" + str_star_id);
                            Log.e("Na", "star-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("marital_status")) {
                            prefEdit.putString("mari_id", id);
                            prefEdit.putString("mari", val);
                            prefEdit.putString("mari_tag", id);


                            str_marital_id = id;
                            txt_marital_status.setText(val);
                            txt_marital_status.setTag(id);
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
                            prefEdit.putString("religion_tag", id);
                            str_religion_id = id;
                            txt_religion.setText(val);
                            txt_religion.setTag(id);
                            Log.e("Na", "reli-->" + str_religion_id);
                            Log.e("Na", "reli-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("caste")) {
                            prefEdit.putString("caste_id", id);
                            prefEdit.putString("caste", val);
                            prefEdit.putString("caste_tag", id);
                            str_caste_id = id;
                            txt_caste.setText(val);
                            txt_caste.setTag(id);
                            Log.e("Na", "caste-->" + str_caste_id);
                            Log.e("Na", "caste-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("mother_ton")) {
                            prefEdit.putString("mother_id", id);
                            prefEdit.putString("mother", val);
                            prefEdit.putString("mother_tag", id);
                            str_mother_ton_id = id;
                            txt_mother_ton.setText(val);
                            txt_mother_ton.setTag(id);
                            Log.e("Na", "moth_ton-->" + str_mother_ton_id);
                            Log.e("Na", "moth_ton-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("country")) {
                            prefEdit.putString("country_id", id);
                            prefEdit.putString("country", val);
                            prefEdit.putString("country_tag", id);
                            str_country_id = id;
                            txt_country.setText(val);
                            txt_country.setTag(id);
                            Log.e("Na", "country-->" + str_country_id);
                            Log.e("Na", "country-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("state")) {
                            prefEdit.putString("state_id", id);
                            prefEdit.putString("state", val);
                            prefEdit.putString("state_tag", id);
                            str_state_id = id;
                            txt_state.setText(val);
                            txt_state.setTag(id);
                            Log.e("Na", "state-->" + str_state_id);
                            Log.e("Na", "state-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("city")) {
                            prefEdit.putString("city_id", id);
                            prefEdit.putString("city", val);
                            prefEdit.putString("city_tag", id);
                            str_city_id = id;
                            txt_city.setText(val);
                            txt_city.setTag(id);
                            Log.e("Na", "city-->" + str_city_id);
                            Log.e("Na", "city-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("education")) {
                            prefEdit.putString("edu_id", id);
                            prefEdit.putString("edu", val);
                            prefEdit.putString("edu_tag", id);
                            str_education_id = id;
                            txt_education.setText(val);
                            txt_education.setTag(id);
                            Log.e("Na", "edu-->" + str_education_id);
                            Log.e("Na", "edu-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("occupation")) {
                            prefEdit.putString("occup_id", id);
                            prefEdit.putString("occup", val);
                            prefEdit.putString("occup_tag", id);
                            str_occup_id = id;
                            txt_occupation.setText(val);
                            txt_occupation.setTag(id);
                            Log.e("Na", "occup-->" + str_occup_id);
                            Log.e("Na", "occup-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("phy_status")) {
                            prefEdit.putString("phy_id", id);
                            prefEdit.putString("phy", val);
                            prefEdit.putString("phy_tag", id);
                            str_phy_stat_id = id;
                            txt_phy_stat.setText(val);
                            txt_phy_stat.setTag(id);
                            Log.e("Na", "phy_stat-->" + str_phy_stat_id);
                            Log.e("Na", "phy_stat-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("eat_habit")) {
                            prefEdit.putString("eat_id", id);
                            prefEdit.putString("eat", val);
                            prefEdit.putString("eat_tag", id);
                            str_eat_id = id;
                            txt_eat_habit.setText(val);
                            txt_eat_habit.setTag(id);
                            Log.e("Na", "eat-->" + str_eat_id);
                            Log.e("Na", "eat-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("drink_habit")) {
                            prefEdit.putString("drink_id", id);
                            prefEdit.putString("drink", val);
                            prefEdit.putString("drink_tag", id);
                            str_drink_id = id;
                            txt_drink_habit.setText(val);
                            txt_drink_habit.setTag(id);
                            Log.e("Na", "drink-->" + str_drink_id);
                            Log.e("Na", "drink-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("smoke_habit")) {
                            prefEdit.putString("smoke_id", id);
                            prefEdit.putString("smoke", val);
                            prefEdit.putString("smoke_tag", id);
                            str_smoke_id = id;
                            txt_smoke_habit.setText(val);
                            txt_smoke_habit.setTag(id);
                            Log.e("Na", "smoke-->" + str_smoke_id);
                            Log.e("Na", "smoke-->" + val);
                        } else if (str_list_from.equalsIgnoreCase("income")) {
                            prefEdit.putString("income_id", id);
                            prefEdit.putString("income", val);
                            prefEdit.putString("income_tag", id);
                            str_income_id = id;
                            txt_income.setText(val);
                            txt_income.setTag(id);
                            Log.e("Na", "income-->" + str_income_id);
                            Log.e("Na", "income-->" + val);
                        }

                        prefEdit.commit();

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
                    str_height_from_id = strItems_Id;
                } else {
                    strSpinsp_from = strItems;
                    str_age_from_id = strItems_Id;
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
                Log.e("RJ", "sp_to :   Items : " + strItems + ", Id : " + strItems_Id);
                //    strSpinsp_to = strItems;
                if (str_list_from.equalsIgnoreCase("height")) {
                    strSpinsp_to = String.valueOf(sp_to.getSelectedItemId());
                    str_sp_height_to = strItems;
                    str_height_to_id = strItems_Id;
                } else {
                    strSpinsp_to = strItems;
                    str_age_to_id = strItems_Id;
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

                isOpen = false;

                myTabsDrawer.closeDrawer(llay_MainSliderParent);

                if (str_from.equalsIgnoreCase("from_to")) {


                    if (str_list_from.equalsIgnoreCase("age")) {
                        str_values = "";
                        str_age = "";
                        if (Integer.parseInt(strSpinsp_to) > Integer.parseInt(strSpinsp_from)) {
                            str_values = strSpinsp_from + "yrs - " + strSpinsp_to + "yrs";
                            str_age = str_age_from_id + "-" + str_age_to_id;
                            myTabsDrawer.closeDrawer(llay_MainSliderParent);
                            prefEdit.putString("age", str_values);
                            prefEdit.putString("age_id", str_age);
                            prefEdit.putString("age_tag", str_age);

                            txt_age.setTag(str_age);
                            txt_age.setText(str_values);

                            Log.e("NN:fil_spa", str_values);
                            Log.e("NN:fil_spa", str_age);

                        } else {
                            Toast.makeText(FilterPageTest.this, "Check the Age limit !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        str_values = "";
                        str_height="";
                        if (Integer.parseInt(strSpinsp_to) > Integer.parseInt(strSpinsp_from)) {
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
                            Toast.makeText(FilterPageTest.this, "Check the height limit !", Toast.LENGTH_SHORT).show();
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
            }
        });
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
                                    drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "height", str_click,"");
                                    list_drawer.setAdapter(drawerFilterAdapter);
                                    drawerFilterAdapter.notifyDataSetChanged();
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


        RequestQueue requestQueue = Volley.newRequestQueue(FilterPageTest.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

        str_id = str_items_id;
        str_values = str_items;
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

        //set value



    }


    @Override
    public String get_matches(String str_id, String str_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {

        for (Map.Entry<String, JSONObject> entry : hsFilterGmap.entrySet()) {
            if (entry.getKey().equals(strVal)) {
                hsFilterGmap.remove(entry.getKey());
            }

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
                SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from");
                sp_from.setAdapter(spinadapter);

                SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to");
                sp_to.setAdapter(spinadapters);
            } else {
                if (str_list_name.equalsIgnoreCase("eat_habit")) {
                    drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "eat_habit", str_click,"");
                    list_drawer.setAdapter(drawerFilterAdapter);
                    drawerFilterAdapter.notifyDataSetChanged();
                } else {
                    drawerFilterAdapter = new ListDrawerFilterAdapter(context, beanArrayList, (MyInterface) context, "", str_click,"");
                    list_drawer.setAdapter(drawerFilterAdapter);
                    drawerFilterAdapter.notifyDataSetChanged();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanArrayList;

    }


    private ArrayList<ListDrawerBean> LoadLayout_Edit(JSONArray providerServicesMonth, String stridentifyEdit, String StrVariable) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;
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
                SpinnerAdapter spinadapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_from", "edit", StrVariable);
                sp_from.setAdapter(spinadapter);

                SpinnerAdapter spinadapters = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, beanArrayList, (MyInterface) context, "category", "sp_to", "edit", StrVariable);
                sp_to.setAdapter(spinadapters);
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

    public ArrayList<ListDrawerBean> restCallForStateCity(String str_url, final String str_id, final String stridType) {

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
                                jsonArray_common = state;

                                if (state.length() > 0) {
                                    for (int i = 0; i < state.length(); i++) {
                                        ListDrawerBean drawerBean = new ListDrawerBean();
                                        JSONObject providersServiceJSONobject = state.getJSONObject(i);
                                        drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                        drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                        return_beanArrayList.add(drawerBean);
                                    }
                                }

                                drawerFilterAdapter = new ListDrawerFilterAdapter(context, return_beanArrayList, (MyInterface) context, "", str_click,"");
                                list_drawer.setAdapter(drawerFilterAdapter);
                                drawerFilterAdapter.notifyDataSetChanged();

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


        RequestQueue requestQueue = Volley.newRequestQueue(FilterPageTest.this);
        requestQueue.add(stringRequest);

        return return_beanArrayList;
    }


}
