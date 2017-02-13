package com.example.easy_marry.EditProfiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 17/9/16.
 */
public class EditProfilePhoneNumber extends Activity implements MyInterface {
    Button btn_basic_det_save;
    ImageView img_back;
    View itemView1;
    Context context;
    String str_json_edit_contact, str_user_id = "", str_mob_num = "", str_cont_num = "", str_par_mob_num = "",
            str_conv_time, str_easy_marry_id, str_name, str_mob_country_code, str_con_contry_code, str_paren_country_code;
    GeneralData gD;
    TextView txt_contact_det, txt_error_mob_numb, txt_error_con_numb, txt_error_paren_numb;
    EditText et_mob_num, et_cont_num, et_par_mob_num, et_conv_time;
    ListDrawerCountryCodeAdapter listDrawerAdapter;
    ListView listView;
    DrawerLayout myDrawerLayout;
    LinearLayout myLinearLayout;
    EditText ed_countryCode_mob, ed_countryCode_con, ed_countryCode_parent, et_search;
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


    String mob_code, mob_numb, con_code, con_numb, paren_code, paren_numb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_phone_number);
        context = this;
        gD = new GeneralData(context);
        str_user_id = gD.prefs.getString("userid", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        str_name = gD.prefs.getString("name", null);
        str_json_edit_contact = getIntent().getStringExtra("json_edit_contact");
        et_mob_num = (EditText) findViewById(R.id.et_phone_num);
        et_cont_num = (EditText) findViewById(R.id.et_phone_con);
        et_par_mob_num = (EditText) findViewById(R.id.et_phone_parent);
        et_conv_time = (EditText) findViewById(R.id.et_convenient_time);
        btn_basic_det_save = (Button) findViewById(R.id.btn_save);
        img_back = (ImageView) findViewById(R.id.img_ed_back);
        txt_contact_det = (TextView) findViewById(R.id.txt_contact_details);
        et_search = (EditText) findViewById(R.id.et_search_profile);
        listView = (ListView) findViewById(R.id.drawer_listview);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.my_list_drawer_layout);
        myLinearLayout = (LinearLayout) findViewById(R.id.list_drawer);

        txt_error_mob_numb = (TextView) findViewById(R.id.txt_error_mob_numb);
        txt_error_con_numb = (TextView) findViewById(R.id.txt_error_con_numb);
        txt_error_paren_numb = (TextView) findViewById(R.id.txt_error_paren_numb);


        ed_countryCode_mob = (EditText) findViewById(R.id.ed_countryCode_mob);
        ed_countryCode_mob.setFocusable(false);
        ed_countryCode_con = (EditText) findViewById(R.id.ed_countryCode_con);
        ed_countryCode_con.setFocusable(false);
        ed_countryCode_parent = (EditText) findViewById(R.id.ed_countryCode_parent);
        ed_countryCode_parent.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getEditPhoneNumDetCall(str_json_edit_contact);

        mob_code = ed_countryCode_mob.getText().toString().trim();
        con_code = ed_countryCode_con.getText().toString().trim();
        paren_code = ed_countryCode_parent.getText().toString().trim();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_mob_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_mob_numb.getVisibility() == View.VISIBLE) {
                    txt_error_mob_numb.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_mob_num.getText().toString().trim().length() > 10) {
                    txt_error_mob_numb.setVisibility(View.VISIBLE);
                    txt_error_mob_numb.setText("Phone number can't be more than 10");
                    txt_error_mob_numb.setTextColor(Color.parseColor("#ff0000"));

                }
            }
        });
        et_cont_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_con_numb.getVisibility() == View.VISIBLE) {
                    txt_error_con_numb.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_cont_num.getText().toString().trim().length() > 10) {
                    txt_error_con_numb.setVisibility(View.VISIBLE);
                    txt_error_con_numb.setText("Phone number can't be more than 10");
                    txt_error_con_numb.setTextColor(Color.parseColor("#ff0000"));

                }
            }
        });
        et_par_mob_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (txt_error_paren_numb.getVisibility() == View.VISIBLE) {
                    txt_error_paren_numb.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_par_mob_num.getText().toString().trim().length() > 10) {
                    txt_error_paren_numb.setVisibility(View.VISIBLE);
                    txt_error_paren_numb.setText("Phone number can't be more than 10");
                    txt_error_mob_numb.setTextColor(Color.parseColor("#ff0000"));

                }
            }
        });
        btn_basic_det_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    Log.i("LK", "connected");
                    mob_numb = et_mob_num.getText().toString().trim();
                    con_numb = et_cont_num.getText().toString().trim();
                    paren_numb = et_par_mob_num.getText().toString().trim();
                   if (mob_numb.length() == 0) {

                        txt_error_mob_numb.setVisibility(View.VISIBLE);
                        txt_error_mob_numb.setText("Enter mobile number");
                        txt_error_mob_numb.setTextColor(Color.parseColor("#ff0000"));
                    } else if (con_numb.length() == 0) {

                        txt_error_con_numb.setVisibility(View.VISIBLE);
                        txt_error_con_numb.setText("Enter contact number");
                        txt_error_con_numb.setTextColor(Color.parseColor("#ff0000"));
                    } else if (paren_numb.length() == 0) {

                        txt_error_paren_numb.setVisibility(View.VISIBLE);
                        txt_error_paren_numb.setText("Enter parent contact number");
                        txt_error_paren_numb.setTextColor(Color.parseColor("#ff0000"));
                    } else if (mob_numb.length() < 10) {
                        txt_error_mob_numb.setVisibility(View.VISIBLE);
                        txt_error_mob_numb.setText("Enter valid mobile number");
                        txt_error_mob_numb.setTextColor(Color.parseColor("#ff0000"));
                    } else if (mob_numb.length() > 10) {

                        txt_error_mob_numb.setVisibility(View.VISIBLE);
                        txt_error_mob_numb.setText("Phone number can't be more than 10");
                        txt_error_mob_numb.setTextColor(Color.parseColor("#ff0000"));

                    } else if (con_numb.length() < 10) {
                        txt_error_con_numb.setVisibility(View.VISIBLE);
                        txt_error_con_numb.setText("Enter valid mobile number");
                        txt_error_con_numb.setTextColor(Color.parseColor("#ff0000"));
                    } else if (con_numb.length() > 10) {
                        txt_error_con_numb.setVisibility(View.VISIBLE);
                        txt_error_con_numb.setText("Phone number can't be more than 10");
                        txt_error_con_numb.setTextColor(Color.parseColor("#ff0000"));

                    } else if (paren_numb.length() < 10) {
                        txt_error_paren_numb.setVisibility(View.VISIBLE);
                        txt_error_paren_numb.setText("Enter valid mobile number");
                        txt_error_paren_numb.setTextColor(Color.parseColor("#ff0000"));
                    } else if (paren_numb.length() > 10) {
                        txt_error_paren_numb.setVisibility(View.VISIBLE);
                        txt_error_paren_numb.setText("Phone number can't be more than 10");
                        txt_error_paren_numb.setTextColor(Color.parseColor("#ff0000"));

                    } else if (mob_code.length() == 0) {
                        Toast.makeText(EditProfilePhoneNumber.this, "select country code", Toast.LENGTH_SHORT).show();

                    } else if (con_code.length() == 0) {
                        Toast.makeText(EditProfilePhoneNumber.this, "select country code", Toast.LENGTH_SHORT).show();

                    } else if (paren_code.length() == 0) {
                        Toast.makeText(EditProfilePhoneNumber.this, "select country code", Toast.LENGTH_SHORT).show();

                    } else {
                        str_mob_num = mob_code + "-" + mob_numb;
                        str_cont_num = con_code + "-" + con_numb;
                        str_par_mob_num = paren_code + "-" + paren_numb;
                        str_conv_time = et_conv_time.getText().toString().trim();
                        updateUserLocationDet();
                    }
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);

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
            }
        });
        txt_contact_det.setText("Contact details of " + str_name + " (" + str_easy_marry_id + ")");

       /* spin_phone_code_mob = (Spinner) findViewById(R.id.spin_phone_code_mob);
        spin_phone_code_con = (Spinner) findViewById(R.id.spin_phone_code_con);
        spin_phone_code_parent = (Spinner) findViewById(R.id.spin_phone_code_parent);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditProfilePhoneNumber.this, android.R.layout.simple_spinner_item, country_name);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_phone_code_mob.setAdapter(spinnerArrayAdapter);
        ed_countryCode_mob = (EditText)findViewById(R.id.ed_countryCode_mob);
        ed_countryCode_mob.setEnabled(false);

        spin_phone_code_mob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strValue1 = String.valueOf(country_code[position]);
                ed_countryCode_mob.setText("(" + strValue1 + ")");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(EditProfilePhoneNumber.this, android.R.layout.simple_spinner_item, country_name);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_phone_code_con.setAdapter(spinnerArrayAdapter1);
        ed_countryCode_con = (EditText)findViewById(R.id.ed_countryCode_con);
        ed_countryCode_con.setEnabled(false);

        spin_phone_code_con.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strValue1 = String.valueOf(country_code[position]);
                ed_countryCode_con.setText("(" + strValue2 + ")");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(EditProfilePhoneNumber.this, android.R.layout.simple_spinner_item, country_name);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_phone_code_parent.setAdapter(spinnerArrayAdapter2);
        ed_countryCode_parent = (EditText)findViewById(R.id.ed_countryCode_parent);
        ed_countryCode_parent.setEnabled(false);

        spin_phone_code_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strValue3 = String.valueOf(country_code[position]);
                ed_countryCode_parent.setText("(" + strValue3 + ")");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        ed_countryCode_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                listDrawerAdapter = new ListDrawerCountryCodeAdapter(context, "mob_num", country_name, country_code, (MyInterface) context);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(listDrawerAdapter);
                listDrawerAdapter.notifyDataSetChanged();

            }
        });
        ed_countryCode_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                listDrawerAdapter = new ListDrawerCountryCodeAdapter(context, "con_num", country_name, country_code, (MyInterface) context);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(listDrawerAdapter);
                listDrawerAdapter.notifyDataSetChanged();

            }
        });
        ed_countryCode_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawerLayout.openDrawer(myLinearLayout);
                listDrawerAdapter = new ListDrawerCountryCodeAdapter(context, "parent_num", country_name, country_code, (MyInterface) context);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(listDrawerAdapter);
                listDrawerAdapter.notifyDataSetChanged();

            }
        });

    }

    public void getEditPhoneNumDetCall(String str_basic_json) {


        try {
            Log.i("HH", "strResp : " + str_basic_json);
            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

            JSONObject myContactJsonObj = new JSONObject(str_basic_json);
            String mob_num = myContactJsonObj.getString("Mobile Number");
            String cont_num = myContactJsonObj.getString("Contact Number");
            String par_mob_num = myContactJsonObj.getString("Parent Contact");
            String conv_time = myContactJsonObj.getString("Convenient Time to Call");

            if (!mob_num.equalsIgnoreCase("")) {
                String[] separated1 = mob_num.split("-");
                mob_code = separated1[0];
                mob_numb = separated1[1];
            }
            if (!cont_num.equalsIgnoreCase("")) {
                String[] separated2 = cont_num.split("-");
                con_code = separated2[0];
                con_numb = separated2[1];

            }

            if (!par_mob_num.equalsIgnoreCase("")) {
                String[] separated3 = par_mob_num.split("-");
                paren_code = separated3[0];
                paren_numb = separated3[1];
            }

            str_conv_time = conv_time;
            et_mob_num.setText(mob_numb);
            et_cont_num.setText(con_numb);
            et_par_mob_num.setText(paren_numb);
            ed_countryCode_mob.setText(mob_code);
            ed_countryCode_con.setText(con_code);
            ed_countryCode_parent.setText(paren_code);
            et_conv_time.setText(str_conv_time);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void updateUserLocationDet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "edit_user8.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //   Toast.makeText(UserRegOne.this, response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {


                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setCancelable(true);

                                itemView1 = LayoutInflater.from(context)
                                        .inflate(R.layout.custom_basic_det_popup, null);
                                final AlertDialog altDialog = builder.create();
                                altDialog.setView(itemView1);
                                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                String str_span = "your contact details have been updated successfully";
                                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                SpannableString spannableString1 = new SpannableString(str_span);
                                spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 4, 20, 0);
                                txt_span_text.setText(spannableString1);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(EditProfilePhoneNumber.this, EditProfile.class));
                                        finish();
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

                if (str_mob_num.length() > 0) {
                    params.put("mobilenumber", str_mob_num);
                }
                if (str_cont_num.length() > 0) {
                    params.put("contactnumber", str_cont_num);
                }
                if (str_par_mob_num.length() > 0) {
                    params.put("parentcontact", str_par_mob_num);
                }
                if (str_conv_time.length() > 0) {
                    params.put("convenienttime", str_conv_time);
                }
                if (str_user_id.length() > 0) {
                    params.put("userid", str_user_id);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfilePhoneNumber.this);
        requestQueue.add(stringRequest);

        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

    }

    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> json_obj) {
        if (strIdentify.equalsIgnoreCase("mob_num")) {
            ed_countryCode_mob.setText(str_items);
            mob_code = str_items;
        } else if (strIdentify.equalsIgnoreCase("con_num")) {
            ed_countryCode_con.setText(str_items);
            con_code = str_items;
        } else if (strIdentify.equalsIgnoreCase("parent_num")) {
            ed_countryCode_parent.setText(str_items);
            paren_code = str_items;
        }
        myDrawerLayout.closeDrawers();
    }

    @Override
    public String get_matches(String str_id, String str_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        return null;
    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal) {
        return null;
    }
}
