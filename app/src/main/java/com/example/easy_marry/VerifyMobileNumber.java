package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.GCM.GCMClientManager;
import com.example.easy_marry.Registration.UserRegTwo;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 10/6/16.
 */
public class VerifyMobileNumber extends Activity {
    Button btn_verify;
    String str_span_txt = "An SMS with verification PIN has been sent to";
    String str_span_txt1 = "your mobile 9489847096 to verfiy your number.";
    TextView txt_span1, txt_span2, txt_get_my_phone, txt_edit_phone, txt_error_mobile;
    ImageView img_back;
    View itemView1;
    String str_user_id;
    Context context;
    String str_profile_for, str_name, str_gender, str_dob, str_email, str_pass, str_phone, str_gcmid, PROJECT_NUMBER = "29989263680";

    GeneralData gD;
    boolean isClosed = false;

    Spinner sp_get_code;
    EditText ed_CountryCode;
    String str_country_code;
    String strValue;
    EditText et_otp;
    AnimationDrawable loadingViewAnim;
    TextView loadigText;
    ImageView loadigIcon;
    LinearLayout loadingLayout;
    //AlertDialog altDialog_load;

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
    int nAttempt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobile_number);
        context = this;


        gD = new GeneralData(context);


        str_profile_for =  gD.prefs.getString("profile_for", null);
        str_name =  gD.prefs.getString("name", null);
        str_gender =  gD.prefs.getString("gender", null);
        str_phone = gD.prefs.getString("mobile", null);
        str_dob =  gD.prefs.getString("dob", null);
        str_email =  gD.prefs.getString("email", null);
        str_pass =  gD.prefs.getString("password", null);


        // Log.e("NN:gcm",str_gcmid);
        //str_user_id = gD.prefs.getString("user_id", null);

        txt_get_my_phone = (TextView) findViewById(R.id.txt_phone_num);
        txt_edit_phone = (TextView) findViewById(R.id.txt_edit_phone_num);
        et_otp = (EditText) findViewById(R.id.et_otp);
        img_back = (ImageView) findViewById(R.id.img_ver_back);
        btn_verify = (Button) findViewById(R.id.btn_verifyy);
        txt_span1 = (TextView) findViewById(R.id.txt_span1);
        txt_span2 = (TextView) findViewById(R.id.txt_span2);


        Log.e("NN:phone", str_phone);
        txt_get_my_phone.setText(str_phone);
        img_back.setOnClickListener(new View.OnClickListener() {
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
                Button btn_continue = (Button) itemView1.findViewById(R.id.btn_cont_reg);
                Button btn_exit = (Button) itemView1.findViewById(R.id.btn_exit);
                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }

                });

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();

                    }
                });


            }
        });
        txt_edit_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_edit_phone_otp_popup, null);
                final android.support.v7.app.AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                String str_txt_span = "If you wish to change your mobile number, please change and verify";
                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.txt_span_edit_phone);
                SpannableString spannableString = new SpannableString(str_txt_span);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 21, 41, 0);
                txt_span_text.setText(spannableString);


                final EditText et_get_num = (EditText) itemView1.findViewById(R.id.et_phone_num);
                txt_error_mobile = (TextView) itemView1.findViewById(R.id.txt_error_mobile);
                ed_CountryCode = (EditText) itemView1.findViewById(R.id.ed_countryCode);
                ed_CountryCode.setEnabled(false);
                Button btn_update = (Button) itemView1.findViewById(R.id.btn_update);
                sp_get_code = (Spinner) itemView1.findViewById(R.id.spin_phone_code);

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(VerifyMobileNumber.this, android.R.layout.simple_spinner_item, country_name);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                sp_get_code.setAdapter(spinnerArrayAdapter);


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
                sp_get_code.setSelection(95);
                ed_CountryCode.setText("(" + country_code[95] + ")");

                sp_get_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        strValue = String.valueOf(country_code[position]);
                        ed_CountryCode.setText("(" + strValue + ")");


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btn_update.setOnClickListener(new View.OnClickListener() {
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
                            registerCall("phone","","","",str_country_code,"","","","0");
                            altDialog.dismiss();
                        }
                    }
                });
                altDialog.show();


            }
        });
        SpannableString spannableString = new SpannableString(str_span_txt);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 2, 6, 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 25, 28, 0);
        txt_span1.setText(spannableString);

       /* SpannableString spannableString1 = new SpannableString(str_span_txt1);
        spannableString1.setSpan(new ForegroundColorSpan(Color.BLACK), 11, 22, 0);*/
        txt_span2.setText(str_phone);

        // txt_span2.setText(spannableString1.toString().substring(11, 22).replace("9489847096", "9962410084"));

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    Log.i("LK", "connected");
                    // Toast.makeText(VerifyMobileNumber.this, "Internet Connected", Toast.LENGTH_SHORT).show();
                    if (et_otp.getText().toString().length() == 0) {
                        Toast.makeText(VerifyMobileNumber.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                    } else {
                    /*    View itemView1;
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(true);
                        itemView1 = LayoutInflater.from(context)
                                .inflate(R.layout.custom_image_load, null);
                        altDialog_load = builder.create();
                        altDialog_load.setView(itemView1);
                        altDialog_load.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        loadingLayout = (LinearLayout) itemView1.findViewById(R.id.LinearLayout1);
                        //loadigText = (TextView) itemView1.findViewById(R.id.textView111);
                        loadigIcon = (ImageView) itemView1.findViewById(R.id.imageView111);
                        loadigIcon.setBackgroundResource(R.drawable.loading_anim);
                        loadingViewAnim = (AnimationDrawable) loadigIcon.getBackground();
                        //loadigText.setText("Registering..Please wait");
                        loadingViewAnim.start();
                        altDialog_load.show();*/
                        registerCall("user_reg",str_profile_for,str_name,str_gender,str_phone,str_email,str_pass,str_dob,"1");
                    }

                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);

                    View itemView1 = LayoutInflater.from(context)
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

                //   {"register":{"userid":"85","easymarryid":"EM475632732","screenid":"1"},"code":"1","status":"success"}


            }
        });

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                str_gcmid = registrationId;

                Log.d("RegID", registrationId);
//send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setCancelable(false);

            View itemView1 = LayoutInflater.from(context)
                    .inflate(R.layout.custom_incomplete_reg_popup, null);
            final android.support.v7.app.AlertDialog altDialog = builder.create();
            altDialog.setView(itemView1);
            altDialog.show();
            altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            Button btn_continue = (Button) itemView1.findViewById(R.id.btn_cont_reg);
            Button btn_exit = (Button) itemView1.findViewById(R.id.btn_exit);
            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    isClosed = true;
                }

            });

            btn_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    altDialog.dismiss();
                    isClosed = false;
                }
            });

        }
        return isClosed;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    public void registerCall(final String str_from, final String strProfilefor, final String strName, final String strGender, final String strPhone, final String strEmail, final String strPass, final String strDob, final String strVerify) {

       if(!str_from.equalsIgnoreCase("phone")){
           gD.showAlertDialog(context,"Registering", "Please wait...");
       }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // altDialog_load.dismiss();
                        //loadingViewAnim.stop();
                        try {
                            Log.i("HH_reg", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH_reg", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                //loadingViewAnim.stop();
                                if (str_from.equalsIgnoreCase("user_reg")) {
                                    //altDialog_load.dismiss();
                                    String strName = jsobj.getJSONObject("Response").getString("Name");
                                    String strUserId = jsobj.getJSONObject("Response").getString("userid");
                                    String strCompleteLevel = jsobj.getJSONObject("Response").getString("completeLevel");
                                    String strMembership = jsobj.getJSONObject("Response").getString("membership");
                                    String strScreenId = jsobj.getJSONObject("Response").getString("screen_id");
                                    String strProfileImage = GeneralData.LOCAL_IP_IMAGE + "upload/" + jsobj.getJSONObject("Response").getString("profileImage");
                                    String strEasyMarryId = jsobj.getJSONObject("Response").getString("easymarryid");
                                    String strMemPlan = jsobj.getJSONObject("Response").getString("membershipplan");
                                    String strMemValid = jsobj.getJSONObject("Response").getString("membershipvalidity");
                                    String strRating = jsobj.getJSONObject("Response").getString("Rating");


                                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                    prefEdit.putString("name", strName);
                                    prefEdit.putString("userid", strUserId);
                                    prefEdit.putString("completelevel", strCompleteLevel);
                                    prefEdit.putString("membership", strMembership);
                                    prefEdit.putString("profileimage", strProfileImage);
                                    prefEdit.putString("screenid", strScreenId);
                                    prefEdit.putString("easymarryid", strEasyMarryId);
                                    prefEdit.putString("memplan", strMemPlan);
                                    prefEdit.putString("memvalid", strMemValid);
                                    prefEdit.putString("Rating", strRating);
                                    prefEdit.commit();


                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setCancelable(true);

                                    itemView1 = LayoutInflater.from(context)
                                            .inflate(R.layout.custom_popup_reg_success, null);
                                    final AlertDialog altDialog = builder.create();
                                    altDialog.setView(itemView1);
                                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    String str_span1 = "You have been registered successfully";
                                    String str_span2 = "Your matrimony id is";
                                    txt_span1 = (TextView) itemView1.findViewById(R.id.span1);
                                    txt_span2 = (TextView) itemView1.findViewById(R.id.span2);
                                    TextView txt_emId = (TextView) itemView1.findViewById(R.id.txt_get_emId);
                                    Button btn_continue = (Button) itemView1.findViewById(R.id.btn_continue);
                                    SpannableString spannableString = new SpannableString(str_span1);
                                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 13, 24, 0);
                                    txt_span1.setText(spannableString);


                                    SpannableString spannableString1 = new SpannableString(str_span2);
                                    spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 0, 17, 0);
                                    spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 17, 20, 0);
                                    txt_span2.setText(spannableString1);
                                    txt_emId.setText(strEasyMarryId);
                                    btn_continue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            startActivity(new Intent(VerifyMobileNumber.this, UserRegTwo.class));
                                            finish();
                                        }
                                    });
                                  gD.altDialog.dismiss();
                                    altDialog.show();
                                }
                                else if(str_from.equalsIgnoreCase("phone")){
                                    Toast.makeText(VerifyMobileNumber.this, "Mobile Numer Updated!!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(VerifyMobileNumber.this, VerifyMobileNumber.class);
                                    SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                    prefEdit.putString("mobile", str_country_code);
                                    prefEdit.commit();
                                    startActivity(i);
                                    finish();

                                }
                            } else if (jsobj.getString("Errortype").equalsIgnoreCase("Mobile")) {

                                if(str_from.equalsIgnoreCase("phone")) {
                                    gD.altDialog.dismiss();
                                    Toast.makeText(VerifyMobileNumber.this, "Mobile number already exists!!", Toast.LENGTH_SHORT).show();
                                }
                                else if(str_from.equalsIgnoreCase("user_reg")) {
                                    //altDialog_load.dismiss();
                                    gD.altDialog.dismiss();
                                    txt_error_mobile.setVisibility(View.VISIBLE);
                                    txt_error_mobile.setText("Mobile number already exists!!");
                                    txt_error_mobile.setTextColor(Color.parseColor("#ff0000"));
                                }
                            } else if (jsobj.getString("Errortype").equalsIgnoreCase("Email")) {
                                //altDialog_load.dismiss();
                                gD.altDialog.dismiss();
                                View itemView1;
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);
                                itemView1 = LayoutInflater.from(context)
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


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gD.altDialog.dismiss();
                        // Toast.makeText(VerifyMobileNumber.this, error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Log.e("error_msg", res);
                                gD.altDialog.dismiss();
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

                if (str_from.equalsIgnoreCase("user_reg")) {
                    Log.e("Reg_final-profilefor", strProfilefor);
                    Log.e("Reg_final-name", strName);
                    Log.e("Reg_final-gender", strGender);
                    Log.e("Reg_final-mobile", strPhone);
                    Log.e("Reg_final-email", strEmail);
                    Log.e("Reg_final-password", strPass);
                    Log.e("Reg_final-dob", strDob);
                    Log.e("Reg_final-verify", strVerify);

                    params.put("profilefor", strProfilefor);
                    params.put("name", strName);
                    params.put("gender", strGender);
                    params.put("mobile", strPhone);
                    params.put("email", strEmail);
                    params.put("password", strPass);
                    params.put("dob", strDob);
                    params.put("verify", strVerify);
                    //params.put("gcmid",str_gcmid);

                }
                else if (str_from.equalsIgnoreCase("phone")) {
                    params.put("mobile", strPhone);
                    params.put("verify",strVerify);

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
        RequestQueue requestQueue = Volley.newRequestQueue(VerifyMobileNumber.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

}
