package com.example.easy_marry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 20/6/16.
 */
public class Account extends Activity {
    ImageView img_back;
    Context context;
    String str_pref;
    String str_delete;
    String str_user_id;
    GeneralData gD;
    LinearLayout txt_delete_account, txt_logout, ll_call_pref, ll_change_pass, ll_blocked_profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        context = this;
        gD = new GeneralData(context);
        str_user_id = gD.prefs.getString("userid", null);
        img_back = (ImageView) findViewById(R.id.img_acc_back);
        txt_delete_account = (LinearLayout) findViewById(R.id.ll_del_acc);
        txt_logout = (LinearLayout) findViewById(R.id.ll_logout);
        ll_call_pref = (LinearLayout) findViewById(R.id.ll_call_pref);
        ll_change_pass = (LinearLayout) findViewById(R.id.ll_change_pass);
        ll_blocked_profiles = (LinearLayout) findViewById(R.id.ll_block_prof);
        ll_blocked_profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, BlockedProfiles.class));
                //finish();
            }
        });
        ll_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, ChangePassword.class));
              //  finish();
            }
        });
        ll_call_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account.this, CallPreference.class));
               // finish();
            }
        });
        txt_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View itemView1;
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_delete_account, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final String[] str_del = {"Do you want to delete your account ?"};
                TextView txt_span1 = (TextView) itemView1.findViewById(R.id.span_text_del);
                final EditText et_del_reason = (EditText) itemView1.findViewById(R.id.et_del_reason);
                final RadioButton rb_married, rb_mar_fixed, rb_other_reason;
                rb_married = (RadioButton) itemView1.findViewById(R.id.radio_married);
                rb_mar_fixed = (RadioButton) itemView1.findViewById(R.id.radio_mar_fixed);
                rb_other_reason = (RadioButton) itemView1.findViewById(R.id.radio_other_reason);
                SpannableString spannableString = new SpannableString(str_del[0]);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 14, 21, 0);
                txt_span1.setText(spannableString);
                str_pref = String.valueOf(rb_married.getTag());
                rb_married.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (rb_married.isChecked()) {
                            str_pref = String.valueOf(rb_married.getTag());
                            et_del_reason.setText("");
                            et_del_reason.setVisibility(View.GONE);
                            rb_mar_fixed.setChecked(false);
                            rb_other_reason.setChecked(false);


                        }
                    }
                });
                rb_mar_fixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (rb_mar_fixed.isChecked()) {
                            str_pref = String.valueOf(rb_mar_fixed.getTag());
                            et_del_reason.setText("");
                            et_del_reason.setVisibility(View.GONE);
                            rb_married.setChecked(false);
                            rb_other_reason.setChecked(false);


                        }
                    }
                });
                rb_other_reason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (rb_other_reason.isChecked()) {
                            str_pref = String.valueOf(rb_other_reason.getTag());
                            et_del_reason.setVisibility(View.VISIBLE);
                            rb_mar_fixed.setChecked(false);
                            rb_married.setChecked(false);


                        }
                    }
                });
                Button btn_cancel, btn_delete;
                btn_cancel = (Button) itemView1.findViewById(R.id.btn_cancel);
                btn_delete = (Button) itemView1.findViewById(R.id.btn_delete);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                    }
                });
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                        boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                        if (isConnected) {
                            str_delete = et_del_reason.getText().toString().trim();
                           // logOutCall();
                           deletePreferenceCall();
                            altDialog.dismiss();
                        }
                        else{
                            Toast.makeText(Account.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                        }
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

                                        JSONArray profile_created_by = jsobj.getJSONArray("Delete preference");

                                        Log.i("RR", "strResp : " + profile_created_by.getJSONObject(0).getString("id"));

                                        rb_married.setTag(profile_created_by.getJSONObject(0).getString("id"));
                                        rb_mar_fixed.setTag(profile_created_by.getJSONObject(1).getString("id"));
                                        rb_other_reason.setTag(profile_created_by.getJSONObject(2).getString("id"));
                                        ///providersServiceJSONobject.getString("value"));
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Account.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        Toast.makeText(Account.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(Account.this);
                requestQueue.add(stringRequest);

                altDialog.show();

            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Include dialog.xml file

                View itemView1;
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                itemView1 = LayoutInflater.from(context)
                        .inflate(R.layout.custom_logout_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                String str_log = "Do you want to log out now ?";
                TextView txt_span1 = (TextView) itemView1.findViewById(R.id.span_text_log);
                SpannableString spannableString = new SpannableString(str_log);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 15, 23, 0);
                txt_span1.setText(spannableString);
                altDialog.show();
                Button btn_logout = (Button) itemView1.findViewById(R.id.btn_logout);
                Button btn_cancel = (Button) itemView1.findViewById(R.id.btn_cancel);
                btn_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                        boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                        if (isConnected) {
                            logOutCall();
                        }
                        else{
                            Toast.makeText(Account.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                    }
                });
               /* dialog.setContentView(R.layout.logout_popup);
                String str_log="Do you want to log out now ?";
                TextView txt_span1=(TextView)dialog.findViewById(R.id.span_text_log) ;
                SpannableString spannableString = new SpannableString(str_log);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#639639")), 15, 23, 0);
                txt_span1.setText(spannableString);
                dialog.show();*/
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void deletePreferenceCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "deleteaccount.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("DEL", "strResp : " + response);
                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("DEL", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                Toast.makeText(Account.this, "Account Deleted!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Account.this,HomeScreen.class));
                                finish();
                                finishAffinity();

                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("login_uname", null);
                                prefEdit.putString("login_pword", null);
                                prefEdit.putString("horo_id", null);

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
                                prefEdit.commit();
                                GeneralData.n_count=0;
                                GeneralData.filter_count=0;
                                GeneralData.filter_count1=0;


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
                Log.i("pref-N1", str_pref);
                Log.i("pref-N2", str_delete);
                Log.i("pref-N3", str_user_id);
                params.put("userid", str_user_id);
                params.put("reasonid", str_pref);
                params.put("comment", str_delete);


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

    public void logOutCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "logout.php",
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

                                Toast.makeText(Account.this, "You have been logged out successfully!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Account.this, HomeScreen.class));
                                finish();
                                finishAffinity();

                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("login_uname", null);
                                prefEdit.putString("login_pword", null);
                                prefEdit.putString("horo_id", null);

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
                                prefEdit.commit();
                                GeneralData.n_count=0;
                                GeneralData.filter_count=0;
                                GeneralData.filter_count1=0;

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

                Log.i("pref-N3", str_user_id);
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


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
