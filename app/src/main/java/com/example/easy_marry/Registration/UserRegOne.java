package com.example.easy_marry.Registration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.example.easy_marry.Adapter.ListDrawerCountryCodeAdapter;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.GCM.GCMClientManager;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.TermsAndConditions;
import com.example.easy_marry.VerifyMobileNumber;
import com.example.easy_marry.genericclasses.GeneralData;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android3 on 30/5/16.
 */
public class UserRegOne extends Activity implements MyInterface {
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    EditText et_get_profile, et_search_profile_for, et_get_dob, et_get_name, et_get_phone,
            et_get_phone_code, et_get_religion, et_get_mother_tongue, et_get_email, et_get_pass;
    ListView listView;
    Context c;
    ImageView img_back, img_facebook_login;
    Button btn_submit;
    RadioButton radioMale, radioFemale;

    Calendar calendar;
    ListDrawerAdapter listDrawerAdapter;
    ListDrawerCountryCodeAdapter listDrawerCountryCodeAdapter;
    boolean isCheckedMale, isCheckedFemale;
    ProgressDialog dialog;
    private CallbackManager callbackManager;
    // int year, month, day;
    String str_profile_for_id, str_mother_tonngue_id, str_religion_id, PROJECT_NUMBER = "29989263680";
    JSONArray jsonArray_my_profile, jsonArray_religion, jsonArray_mother_tongue;
    String str_gender_type = "male", strGCMid;
    TextView txt_error_profile, txt_error_name, txt_error_dob, txt_error_email, txt_error_pass;
    TextView txt_error_mobile;
    CheckBox cb_terms;
    String[] country_code = {"+93", "+358", "+355", "+213", "+1 684", "+376",
            "+244", "+1 264", "+672", "+1 268", "+54", "+374", "+297", "+61",
            "+43", "+994", "+1 242", "+973", "+880", "+1 246", "+375", "+32",
            "+501", "+229", "+1 441", "+975", "+591", "+387", "+267", "+55",
            "+246", "+1 284", "+673", "+359", "+226", "+95", "+257", "+885",
            "+237", "+1", "+238", "+1 345", "+236", "+235", "+56", "+86",
            "+61", "+61", "+57", "+269", "+682", "+506", "+385", "+53", "+357",
            "+420", "+243", "+45", "+253", "+1 767", "+1 809", "+593", "+20",
            "+503", "+240", "+291", "+372", "+251", "+298", "+500", "+679",
            "+358", "+33", "+689", "+241", "+220", "+970", "+995", "+49",
            "+233", "+350", "+30", "+299", "+1 473", "+1 671", "+502", "+224",
            "+245", "+592", "+509", "+39", "+504", "+852", "+36", "+354",
            "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225",
            "+1 876", "+81", "+962", "+7", "+254", "+686", "+381", "+965",
            "+996", "+856", "+371", "+961", "+266", "+231", "+218", "+423",
            "+370", "+352", "+853", "+389", "+261", "+265", "+60", "+960",
            "+223", "+356", "+692", "+222", "+230", "+262", "+52", "+691",
            "+373", "+377", "+976", "+382", "+1 664", "+212", "+258", "+264",
            "+674", "+977", "+31", "+599", "+687", "+64", "+505", "+227",
            "+234", "+683", "+672", "+850", "+1 670", "+47", "+968", "+92",
            "+680", "+507", "+675", "+595", "+51", "+63", "+870", "+48",
            "+351", "+1", "+974", "+242", "+40", "+7", "+250", "+290",
            "+1 869", "+1 758", "+508", "+1 784", "+685", "+378", "+239",
            "+966", "+44", "+221", "+381", "+248", "+232", "+65", "+421",
            "+386", "+677", "+252", "+27", "+82", "+34", "+94", "+249", "+597",
            "+268", "+46", "+41", "+963", "+886", "+992", "+255", "+66",
            "+670", "+228", "+690", "+676", "+1 868", "+216", "+90", "+993",
            "+1 649", "+668", "+256", "+380", "+971", "+44", "+1", "+598",
            "+1 340", "+998", "+678", "+58", "+84", "+681", "+970", "+967",
            "+260 ", "+263"};
    String[] country_name = {"Afghanistan", "Aland Islands", "Albania",
            "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
            "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
            "Bosnia and Herzegovina", "Botswana", "Brazil",
            "British Indian Ocean Territory", "British Virgin Islands",
            "Brunei", "Bulgaria", "Burkina Faso", "Burma (Myanmar)", "Burundi",
            "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
            "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia",
            "Comoros", "Cook Islands", "Costa Rica", "Croatia", "Cuba",
            "Cyprus", "Czech Republic", "Democratic Republic of the Congo",
            "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador",
            "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
            "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji",
            "Finland", "France", "French Polynesia", "Gabon", "Gambia",
            "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
            "Greenland", "Grenada", "Guam", "Guatemala", "Guinea",
            "Guinea-Bissau", "Guyana", "Haiti", "Holy See (Vatican City)",
            "Honduras", "Hong Kong", "Hungary", "Iceland", "India",
            "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel",
            "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan",
            "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos",
            "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia",
            "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
            "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",
            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro",
            "Montserrat", "Morocco", "Mozambique", "Namibia", "Nauru", "Nepal",
            "Netherlands", "Netherlands Antilles", "New Caledonia",
            "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue",
            "Norfolk Island", "North Korea", "Northern Marianas Islands",
            "Norway", "Oman", "Pakistan", "Palau", "Panama",
            "Papua New Guinea", "Paraguay", "Peru", "Philippines",
            "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "Republic of the Congo", "Romania", "Russia", "Rwanda",
            "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia",
            "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines",
            "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia",
            "Scotland", "Senegal", "Serbia", "Seychelles", "Sierra Leone",
            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia",
            "South Africa", "South Korea", "Spain", "Sri Lanka", "Sudan",
            "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria",
            "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste",
            "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia",
            "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu",
            "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
            "United States", "Uruguay", "US Virgin Islands", "Uzbekistan",
            "Vanuatu", "Venezuela", "Vietnam", "Wallis and Futuna",
            "Western Bank", "Yemen", "Zambia", "Zimbabwe"};
    String fid = "";
    Spinner sp_get_code;
    GeneralData gD;
    EditText ed_CountryCode;
    String str_country_code;
    String strValue;
    TextView txt_terms, txt_drawer_error_msg;
    //int nAttempt = 0;
    IntentFilter filter1;
    String year1, month1, day1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_user1);
        c = this;
        gD = new GeneralData(c);
        txt_terms = (TextView) findViewById(R.id.txt_terms);
        dialog = new ProgressDialog(UserRegOne.this);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);

        cb_terms = (CheckBox) findViewById(R.id.chk_remember);
        txt_drawer_error_msg = (TextView) findViewById(R.id.txt_draw_error);
        txt_error_dob = (TextView) findViewById(R.id.txt_error_dob);
        txt_error_email = (TextView) findViewById(R.id.txt_error_email);
        txt_error_pass = (TextView) findViewById(R.id.txt_error_pwd);
        txt_error_profile = (TextView) findViewById(R.id.txt_error_profile);
        txt_error_name = (TextView) findViewById(R.id.txt_error_name);
        listView = (ListView) findViewById(R.id.drawer_listview);
        et_get_profile = (EditText) findViewById(R.id.et_profile_for);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);
        radioMale = (RadioButton) findViewById(R.id.radio_male);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        radioFemale = (RadioButton) findViewById(R.id.radio_female);
        et_search_profile_for = (EditText) findViewById(R.id.et_search_profile);
        et_get_dob = (EditText) findViewById(R.id.et_dob);
        et_get_name = (EditText) findViewById(R.id.et_name);
        et_get_email = (EditText) findViewById(R.id.et_email);
        et_get_pass = (EditText) findViewById(R.id.et_password);
        img_back = (ImageView) findViewById(R.id.img_back);


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -18); // to get previous year add -1
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(cal.getTime());
        Log.e("NN:date_u", formattedDate);

        String[] separated1 = formattedDate.split(" ");
        String date = separated1[0];
        Log.e("NN:date_u", date);

        String[] separated = formattedDate.split("-");
        year1 = separated[0];
        month1 = separated[1];
        day1 = separated[2];
        Log.e("NN:date_u", year1);
        Log.e("NN:date_u", month1);
        Log.e("NN:date_u", day1);


        // img_facebook_login = (ImageView) findViewById(R.id.img_fb_login);

        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegOne.this, TermsAndConditions.class));
            }
        });
        //facebook integration
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            //  profilePictureView.setProfileId(object.getString("id"));
                                            Log.i("LAT", "OBJECT : " + object.toString());
                                                 /*   fid = object.getString("id").toString();
                                                    et_user_name.setText(object.getString("name"));
                                                    // et_full_usernaem.setText(object.getString("email"));*/
                                            // Log.e("FBname", object.getString("name"));

                                            Bundle bFacebookData = getFacebookData(object);

                                               /* fid = bFacebookData.getString("idFacebook");
                                                et_user_name.setText(bFacebookData.getString("first_name") + "" + bFacebookData.getString("last_name"));
                                                et_user_email.setText(bFacebookData.getString("email"));*/
                                            if (fid != null) {
                                                fid = bFacebookData.getString("idFacebook");
                                                //   et_yourname.setText(bFacebookData.getString("first_name") + "" + bFacebookData.getString("last_name"));
                                                Log.i("fid", bFacebookData.getString("idFacebook"));
                                                // Log.i("HH", "user_name" + bFacebookData.getString("first_name") + "" + bFacebookData.getString("last_name"));
                                                //Log.i("user_email", bFacebookData.getString("email"));

                                                // signUpCall("social", fid);
                                                LoginManager.getInstance().logOut();
                                            }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                                /*Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email,gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();*/

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // Toast.makeText(Registration_page.this, "Registration Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {

                    }


                });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    str_gender_type = "male";
                }
                //Do something

                else {
                    // Toast.makeText(UserRegOne.this, "select gender", Toast.LENGTH_SHORT).show();
                }
                //do something else

            }
        });
        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (compoundButton.isChecked()) {
                    str_gender_type = "female";
                }
                //Do something

                else {
                    //  Toast.makeText(UserRegOne.this, "select gender", Toast.LENGTH_SHORT).show();
                }
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search_profile_for, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        et_get_dob.setFocusable(false);
        et_get_profile.setFocusable(false);
        //  et_get_mother_tongue.setFocusable(false);
        // et_get_religion.setFocusable(false);
        et_get_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });


        et_get_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");
                et_search_profile_for.setHint("Profile for");
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();


                if (isConnected) {
                    Log.i("LK", "connected");
                  /*  if (nAttempt == 0) {*/
                    final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_my_profile, "profile");

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
                            if (!filteredList.isEmpty()) {
                                listView.setVisibility(View.VISIBLE);
                                listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "profile");
                                listView.setVisibility(View.VISIBLE);
                                listView.setAdapter(listDrawerAdapter);
                                listDrawerAdapter.notifyDataSetChanged(); // data set changed
                            } else {
                                listView.setVisibility(View.GONE);
                                txt_drawer_error_msg.setVisibility(View.VISIBLE);
                                txt_drawer_error_msg.setGravity(Gravity.TOP);
                                txt_drawer_error_msg.setTextColor(Color.parseColor("#000000"));
                                txt_drawer_error_msg.setText("No result found");
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    //}
                    // nAttempt=1;
                } else {
                    txt_drawer_error_msg.setVisibility(View.VISIBLE);
                }


            }
        });

       /* et_get_phone_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listDrawerCountryCodeAdapter = new ListDrawerCountryCodeAdapter(UserRegOne.this, country_name, country_code, (MyInterface) c);
                listView.setAdapter(listDrawerCountryCodeAdapter);
                listDrawerCountryCodeAdapter.notifyDataSetChanged();
                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String strVal = s.toString().toLowerCase();
                        Log.i("AB", "Val : " + strVal);

                        final ArrayList<String> filteredList = new ArrayList<>();
                        String[] stockArr = new String[filteredList.size()];
                        for (int i = 0; i < country_name.length; i++) {

                            String text = country_name[i].toLowerCase();
                            Log.i("AB", "text : " + text);

                            if (text.contains(strVal)) {
                                filteredList.add(country_name[i]);

                                stockArr = filteredList.toArray(stockArr);
                            }
                        }

                        listDrawerCountryCodeAdapter = new ListDrawerCountryCodeAdapter(UserRegOne.this, stockArr, country_code, (MyInterface) c);
                        listView.setAdapter(listDrawerCountryCodeAdapter);
                        listDrawerCountryCodeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });*/
   /*     et_get_mother_tongue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_mother_tongue, "mothertongue");
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


                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "mothertongue");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });*/
      /*  et_get_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myDrawerLayout.openDrawer(myLinearLayout);
                et_search_profile_for.setText("");

                final ArrayList<ListDrawerBean> beanArrayList = LoadLayout(jsonArray_religion, "religion");


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


                        listDrawerAdapter = new ListDrawerAdapter(c, filteredList, (MyInterface) c, "religion");
                        listView.setAdapter(listDrawerAdapter);
                        listDrawerAdapter.notifyDataSetChanged(); // data set changed
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });*/
        et_get_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_name.getVisibility() == View.VISIBLE) {
                    txt_error_name.setVisibility(View.GONE);
                    et_get_name.setPadding(0, 10, 0, 8);
                    et_get_name.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_get_profile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_profile.getVisibility() == View.VISIBLE) {
                    txt_error_profile.setVisibility(View.GONE);
                    et_get_profile.setPadding(0, 10, 0, 8);
                    et_get_profile.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_get_dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_dob.getVisibility() == View.VISIBLE) {
                    txt_error_dob.setVisibility(View.GONE);
                    et_get_dob.setPadding(0, 10, 0, 8);
                    et_get_dob.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_get_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_email.getVisibility() == View.VISIBLE) {
                    txt_error_email.setVisibility(View.GONE);
                    et_get_email.setPadding(0, 10, 0, 8);
                    et_get_email.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_get_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_pass.getVisibility() == View.VISIBLE) {
                    txt_error_pass.setVisibility(View.GONE);
                    et_get_pass.setPadding(0, 10, 0, 8);
                    et_get_pass.setBackgroundResource(R.drawable.edit_single);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_get_pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO do something
                    ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                    if (isConnected) {
                        Log.i("LK", "connected");
                        //Toast.makeText(SignInPage.this, "Internet connected", Toast.LENGTH_SHORT).show();
             /*       if (nAttempt == 0) {*/
                        if (et_get_profile.getText().toString().length() == 0) {
                            // Toast.makeText(UserRegOne.this, "select profile for", Toast.LENGTH_SHORT).show();
                            et_get_profile.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_profile.setText("select profile for");
                            txt_error_profile.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_profile.setVisibility(View.VISIBLE);
                        } else if (et_get_name.getText().toString().length() == 0) {
                            // Toast.makeText(UserRegOne.this, "Enter name", Toast.LENGTH_SHORT).show();
                            et_get_name.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_name.setText("Enter name");
                            txt_error_name.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_name.setVisibility(View.VISIBLE);

                        } else if (et_get_dob.getText().toString().length() == 0) {
                            et_get_dob.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_dob.setText("Enter date of birth");
                            txt_error_dob.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_dob.setVisibility(View.VISIBLE);
                        } /*else if (et_get_religion.getText().toString().length() == 0) {
                    Toast.makeText(UserRegOne.this, "select Religion", Toast.LENGTH_SHORT).show();
                }*/ /*else if ( .getText().toString().length() == 0) {
                    Toast.makeText(UserRegOne.this, "select Mother tongue", Toast.LENGTH_SHORT).show();
                }*/ else if (et_get_email.getText().toString().length() == 0) {

                            et_get_email.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_email.setText("Enter email");
                            txt_error_email.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_email.setVisibility(View.VISIBLE);
                        } else if (!isValidEmail(et_get_email.getText().toString().trim())) {

                            et_get_email.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_email.setText("Invalid E-mail");
                            txt_error_email.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_email.setVisibility(View.VISIBLE);
                        } else if (et_get_pass.getText().toString().length() == 0) {

                            et_get_pass.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_pass.setText("Enter password");
                            txt_error_pass.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_pass.setVisibility(View.VISIBLE);


                        } else if (!(et_get_pass.getText().toString().length() > 6)) {

                            et_get_pass.setBackgroundResource(R.drawable.edit_single_red);
                            txt_error_pass.setText("Password length should be more than 6");
                            txt_error_pass.setTextColor(Color.parseColor("#ff0000"));
                            txt_error_pass.setVisibility(View.VISIBLE);
                        } else if (!cb_terms.isChecked()) {

                            Toast.makeText(UserRegOne.this, "Accept our terms and conditions", Toast.LENGTH_SHORT).show();
                        } else {
                            signUpCall("emailid");

                        }
                        // }
                        //  nAttempt = 1;
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setCancelable(true);

                        View itemView1 = LayoutInflater.from(c)
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


                    handled = true;
                }
                return handled;
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    Log.i("LK", "connected");
                    //Toast.makeText(SignInPage.this, "Internet connected", Toast.LENGTH_SHORT).show();
             /*       if (nAttempt == 0) {*/
                    if (et_get_profile.getText().toString().length() == 0) {
                        // Toast.makeText(UserRegOne.this, "select profile for", Toast.LENGTH_SHORT).show();
                        et_get_profile.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_profile.setText("select profile for");
                        txt_error_profile.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_profile.setVisibility(View.VISIBLE);
                    } else if (et_get_name.getText().toString().length() == 0) {
                        // Toast.makeText(UserRegOne.this, "Enter name", Toast.LENGTH_SHORT).show();
                        et_get_name.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_name.setText("Enter name");
                        txt_error_name.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_name.setVisibility(View.VISIBLE);

                    } else if (et_get_dob.getText().toString().length() == 0) {
                        et_get_dob.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_dob.setText("Enter date of birth");
                        txt_error_dob.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_dob.setVisibility(View.VISIBLE);
                    } /*else if (et_get_religion.getText().toString().length() == 0) {
                    Toast.makeText(UserRegOne.this, "select Religion", Toast.LENGTH_SHORT).show();
                }*/ /*else if ( .getText().toString().length() == 0) {
                    Toast.makeText(UserRegOne.this, "select Mother tongue", Toast.LENGTH_SHORT).show();
                }*/ else if (et_get_email.getText().toString().length() == 0) {

                        et_get_email.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_email.setText("Enter email");
                        txt_error_email.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_email.setVisibility(View.VISIBLE);
                    } else if (!isValidEmail(et_get_email.getText().toString().trim())) {

                        et_get_email.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_email.setText("Invalid E-mail");
                        txt_error_email.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_email.setVisibility(View.VISIBLE);
                    } else if (et_get_pass.getText().toString().length() == 0) {

                        et_get_pass.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_pass.setText("Enter password");
                        txt_error_pass.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_pass.setVisibility(View.VISIBLE);


                    } else if (!(et_get_pass.getText().toString().length() > 6)) {

                        et_get_pass.setBackgroundResource(R.drawable.edit_single_red);
                        txt_error_pass.setText("Password length should be more than 6");
                        txt_error_pass.setTextColor(Color.parseColor("#ff0000"));
                        txt_error_pass.setVisibility(View.VISIBLE);
                    } else if (!cb_terms.isChecked()) {

                        Toast.makeText(UserRegOne.this, "Accept our terms and conditions", Toast.LENGTH_SHORT).show();
                    } else {
                        signUpCall("emailid");

                    }
                    // }
                    //  nAttempt = 1;
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(true);

                    View itemView1 = LayoutInflater.from(c)
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

        myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                strGCMid = registrationId;

                Log.d("RegID", registrationId);
//send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });

    }


    private ArrayList<ListDrawerBean> LoadLayout(JSONArray providerServicesMonth, String stridentifyEdit) {
        ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();
        JSONObject jsobj = null;

        if (providerServicesMonth != null) {
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

                listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, stridentifyEdit);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(listDrawerAdapter);
                listDrawerAdapter.notifyDataSetChanged();

                //txt_drawer_error_msg.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(UserRegOne.this, "list is empty", Toast.LENGTH_SHORT).show();
        }


        return beanArrayList;

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

        if (strIdentify.equalsIgnoreCase("profile")) {
            et_get_profile.setText(str_items);
            str_profile_for_id = str_items_id;

            if (txt_error_profile.getVisibility() == View.VISIBLE) {
                txt_error_profile.setVisibility(View.GONE);

                et_get_profile.setPadding(0, 0, 0, 8);

                et_get_profile.setBackgroundResource(R.drawable.edit_single);
            }

        } /*else if (strIdentify.equalsIgnoreCase("mothertongue")) {
            et_get_mother_tongue.setText(str_items);
            str_mother_tonngue_id = str_items_id;
        }*/ /*else if (strIdentify.equalsIgnoreCase("religion")) {
            et_get_religion.setText(str_items);
            str_religion_id = str_items_id;
        }*/

        myDrawerLayout.closeDrawers();


    }

    @Override
    public String get_matches(String str_id, String str_partner_name, String strFrom, String str_from, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal, String strRemovedVal) {
        return null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub


      /*  Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1);*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year1), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        Calendar c = Calendar.getInstance();
       /* c.add(Calendar.DATE, -21);*/
        c.set(1945, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, myDateListener, Integer.parseInt(year1), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.DAY_OF_MONTH, -c.get(Calendar.DAY_OF_MONTH) + 1);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
//return new DatePickerDialog(ctx, myDateListener, year, month, day);

        return null;


//        return new DatePickerDialog(this, myDateListener, year, month, day);

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        et_get_dob.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    private Bundle getFacebookData(JSONObject object) {

        Bundle bundle = null;
        try {
            bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        //for twitter callback result.

        callbackManager.onActivityResult(requestCode, responseCode, intent);


    }

    /**
     * |
     * Check the Given Email Address as Valid or Not
     *
     * @param strEmail
     * @return isvalidEmail
     */
    public boolean isValidEmail(CharSequence strEmail) {
        boolean isvalid = false;
        try {
            isvalid = false;
            try {
                if (strEmail == null) {
                    return isvalid;
                } else {
                    isvalid = android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isvalid;
    }

    public void signUpCall(final String str_from) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        //   Toast.makeText(UserRegOne.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                if (str_from.equalsIgnoreCase("emailid")) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                    builder.setCancelable(true);

                                    View itemView1 = LayoutInflater.from(c)
                                            .inflate(R.layout.custom_get_phone_popup, null);
                                    final AlertDialog altDialog = builder.create();
                                    altDialog.setView(itemView1);
                                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    final EditText et_get_num = (EditText) itemView1.findViewById(R.id.et_phone_num);
                                    txt_error_mobile = (TextView) itemView1.findViewById(R.id.txt_error_mobile);
                                    ed_CountryCode = (EditText) itemView1.findViewById(R.id.ed_countryCode);
                                    ed_CountryCode.setEnabled(false);
                                    Button btn_verify = (Button) itemView1.findViewById(R.id.btn_verify);
                                    sp_get_code = (Spinner) itemView1.findViewById(R.id.spin_phone_code);

                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(UserRegOne.this, android.R.layout.simple_spinner_item, country_name);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    sp_get_code.setAdapter(spinnerArrayAdapter);
                                    sp_get_code.setSelection(95);
                                    ed_CountryCode.setText("(" + country_code[95] + ")");
                                    sp_get_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            Log.e("SP", "spin country pos->" + sp_get_code.getSelectedItemPosition());
                                            strValue = String.valueOf(country_code[position]);
                                            Log.e("SP", "spin code pos->" + strValue);
                                            ed_CountryCode.setText("(" + strValue + ")");


                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                    et_get_num.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (txt_error_mobile.getVisibility() == View.VISIBLE) {
                                                txt_error_mobile.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {
                                            if (et_get_num.getText().toString().length() > 10) {
                                                txt_error_mobile.setVisibility(View.VISIBLE);
                                                txt_error_mobile.setText("Phone number can't be more than 10");
                                                txt_error_mobile.setTextColor(Color.parseColor("#ff0000"));
                                            }
                                        }
                                    });
                                    btn_verify.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (et_get_num.getText().toString().length() == 0) {
                                                txt_error_mobile.setVisibility(View.VISIBLE);
                                                txt_error_mobile.setText("Enter Mobile Number");
                                                txt_error_mobile.setTextColor(Color.parseColor("#ff0000"));
                                            } else if (et_get_num.getText().toString().length() < 10) {
                                                txt_error_mobile.setVisibility(View.VISIBLE);
                                                txt_error_mobile.setText("Enter valid mobile number");
                                                txt_error_mobile.setTextColor(Color.parseColor("#ff0000"));
                                            } else if (et_get_num.getText().toString().length() > 10) {
                                                txt_error_mobile.setVisibility(View.VISIBLE);
                                                txt_error_mobile.setText("Phone number can't be more than 10");
                                                txt_error_mobile.setTextColor(Color.parseColor("#ff0000"));
                                            } else {
                                                str_country_code = strValue + "-" + et_get_num.getText().toString().trim();
                                                Log.e("NN-code", str_country_code);
                                                signUpCall("phone");

                                            }
                                        }
                                    });
                                    altDialog.show();
                                } else if (str_from.equalsIgnoreCase("phone")) {
                                    Toast.makeText(UserRegOne.this, "Mobile number added!!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(UserRegOne.this, VerifyMobileNumber.class);


                                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                    prefEdit.putString("profile_for", str_profile_for_id);
                                    prefEdit.putString("name", et_get_name.getText().toString().trim());
                                    prefEdit.putString("gender", str_gender_type);
                                    prefEdit.putString("dob", et_get_dob.getText().toString().trim());
                                    prefEdit.putString("email", et_get_email.getText().toString().trim());
                                    prefEdit.putString("password", et_get_pass.getText().toString().trim());
                                    prefEdit.putString("mobile", str_country_code);
                                    prefEdit.commit();
                                    startActivity(i);
                                    finish();
                                }


                            } else if (jsobj.getString("Errortype").equalsIgnoreCase("Age")) {
                             /*   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        c);

                                alertDialogBuilder.setTitle("Invalid Age");
                                alertDialogBuilder
                                        .setMessage("you must be 18 or above to register")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                            }
                                        });


                                AlertDialog alertDialog = alertDialogBuilder.create();

                                alertDialog.show();*/

                                View itemView1;
                                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                builder.setCancelable(true);
                                itemView1 = LayoutInflater.from(c)
                                        .inflate(R.layout.custom_incomplete_partner_det_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                TextView txt_head = (TextView) itemView1.findViewById(R.id.txt_title);
                                TextView txt_content = (TextView) itemView1.findViewById(R.id.txt_content);
                                txt_head.setText("Invalid Age");
                                txt_content.setText("You must be 18 or above to register");
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        altDialog.cancel();
                                    }
                                });
                                altDialog.show();


                            } else if (jsobj.getString("Errortype").equalsIgnoreCase("Email")) {
                               /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        c);

                                alertDialogBuilder.setTitle("Email Exists");
                                alertDialogBuilder
                                        .setMessage("Entered email already exists")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                            }
                                        });


                                AlertDialog alertDialog = alertDialogBuilder.create();

                                alertDialog.show();*/


                                View itemView1;
                                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                builder.setCancelable(true);
                                itemView1 = LayoutInflater.from(c)
                                        .inflate(R.layout.custom_incomplete_partner_det_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                TextView txt_head = (TextView) itemView1.findViewById(R.id.txt_title);
                                TextView txt_content = (TextView) itemView1.findViewById(R.id.txt_content);
                                txt_head.setText("Email Exists");
                                txt_content.setText("Entered email already exists");
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        altDialog.cancel();
                                    }
                                });
                                altDialog.show();


                            } else if (jsobj.getString("Errortype").equalsIgnoreCase("Mobile")) {
                                txt_error_mobile.setVisibility(View.VISIBLE);
                                txt_error_mobile.setText("Mobile number already exists!!");
                                txt_error_mobile.setTextColor(Color.parseColor("#ff0000"));
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
                if (str_from.equalsIgnoreCase("emailid")) {
                    params.put("dob", et_get_dob.getText().toString().trim());
                    params.put("email", et_get_email.getText().toString().trim());
                    params.put("verify", "0");

                } else if (str_from.equalsIgnoreCase("phone")) {
                    params.put("mobile", str_country_code);
                    params.put("verify", "0");

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

//3Secs
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegOne.this);
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
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
            context = GeneralData.context;
            if (isConnected) {
                Log.i("LK", "connected");
              /*  if (nAttempt == 0) {*/

                //Toast.makeText(UserRegOne.this, "Internet connected", Toast.LENGTH_SHORT).show();
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
                                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                    prefEdit.putString("register_select_json", response);
                                    prefEdit.commit();
                                    if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                        JSONArray profile_created_by = jsobj.getJSONArray("profile");
                                        jsonArray_my_profile = jsobj.getJSONArray("profile");
                                        Log.e("NN_check", String.valueOf(jsonArray_my_profile));
                                        jsonArray_religion = jsobj.getJSONArray("religion");
                                        jsonArray_mother_tongue = jsobj.getJSONArray("mothertongue");

                                        if (profile_created_by.length() > 0) {
                                            for (int i = 0; i < profile_created_by.length(); i++) {

                                                ListDrawerBean drawerBean = new ListDrawerBean();
                                                JSONObject providersServiceJSONobject = profile_created_by.getJSONObject(i);
                                                drawerBean.setStr_list_items_id(providersServiceJSONobject.getString("id"));
                                                drawerBean.setStr_list_items(providersServiceJSONobject.getString("value"));
                                                beanArrayList.add(drawerBean);
                                            }
                                        }

                                        listDrawerAdapter = new ListDrawerAdapter(c, beanArrayList, (MyInterface) c, "profile");
                                        listView.setVisibility(View.VISIBLE);
                                        listView.setAdapter(listDrawerAdapter);
                                        //txt_drawer_error_msg.setVisibility(View.GONE);

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


                RequestQueue requestQueue = Volley.newRequestQueue(UserRegOne.this);
                requestQueue.add(stringRequest);



             /*   }
                nAttempt = 1;*/

            } else {


                listView.setVisibility(View.GONE);
                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(c)
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
