package com.example.easy_marry;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
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
import com.example.easy_marry.Adapter.ListDrawerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
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
public class CallPreference extends Activity {
    ImageView img_back;
    RadioButton rb_anytime, rb_15days, rb_30days, rb_60days, rb_never;
    String str_pref, str_user_id, call_pref_id;
    Context context;
    GeneralData gD;
    static int n_count = 0;
    IntentFilter filter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_preference);
        context = this;
        img_back = (ImageView) findViewById(R.id.img_call_back);
        rb_anytime = (RadioButton) findViewById(R.id.radio_anytime);
        rb_15days = (RadioButton) findViewById(R.id.radio_fifteen);
        rb_30days = (RadioButton) findViewById(R.id.radio_thirty);
        rb_60days = (RadioButton) findViewById(R.id.radio_sixty);
        rb_never = (RadioButton) findViewById(R.id.radio_never);

        gD = new GeneralData(context);

        str_user_id = gD.prefs.getString("userid", null);
        call_pref_id = gD.prefs.getString("call_preference", null);
        Log.e("N_call", call_pref_id);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        rb_anytime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (rb_anytime.isChecked()) {

                        str_pref = String.valueOf(rb_anytime.getTag());

                        rb_anytime.getTag();
                        rb_15days.setChecked(false);
                        rb_30days.setChecked(false);
                        rb_60days.setChecked(false);
                        rb_never.setChecked(false);
                        callPreferenceCall();
                    }
                }
                else{
                    rb_anytime.setChecked(true);
                    rb_15days.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                    Toast.makeText(CallPreference.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_15days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (rb_15days.isChecked()) {
                        str_pref = String.valueOf(rb_15days.getTag());

                        rb_anytime.setChecked(false);
                        rb_30days.setChecked(false);
                        rb_60days.setChecked(false);
                        rb_never.setChecked(false);
                        callPreferenceCall();
                    }
                }
                else{
                    rb_15days.setChecked(true);
                    rb_anytime.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                    Toast.makeText(CallPreference.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_30days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_30days.isChecked()) {
                    str_pref = String.valueOf(rb_30days.getTag());

                    rb_anytime.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                    callPreferenceCall();
                }
                }
                else{
                    rb_30days.setChecked(true);
                    rb_anytime.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                    Toast.makeText(CallPreference.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_60days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_60days.isChecked()) {
                    str_pref = String.valueOf(rb_60days.getTag());

                    rb_anytime.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_never.setChecked(false);
                    callPreferenceCall();
                }
                }
                else{
                    rb_60days.setChecked(true);
                    rb_anytime.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_never.setChecked(false);
                    Toast.makeText(CallPreference.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        rb_never.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (rb_never.isChecked()) {
                    str_pref = String.valueOf(rb_never.getTag());
                    rb_anytime.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_60days.setChecked(false);
                    callPreferenceCall();
                }
                }
                else{
                    rb_never.setChecked(true);
                    rb_anytime.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_60days.setChecked(false);
                    Toast.makeText(CallPreference.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void callPreferenceCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "callpreference.php",
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

                                JSONObject json_resp = jsobj.getJSONObject("Response");
                                call_pref_id=json_resp.getString("preference");
                                Log.e("NN:call", "pref_key1-" + call_pref_id);
                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("call_preference", call_pref_id);
                                prefEdit.commit();
                                Toast.makeText(CallPreference.this, "Successfully Updated!!", Toast.LENGTH_SHORT).show();
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
                Log.i("pref-NN", str_pref);
                params.put("userid", str_user_id);
                params.put("preference", str_pref);


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

                                        JSONArray profile_created_by = jsobj.getJSONArray("Call preference");

                                        Log.i("RR", "strResp : " + profile_created_by.getJSONObject(0).getString("id"));

                                        rb_anytime.setTag(profile_created_by.getJSONObject(0).getString("id"));
                                        rb_15days.setTag(profile_created_by.getJSONObject(1).getString("id"));
                                        rb_30days.setTag(profile_created_by.getJSONObject(2).getString("id"));
                                        rb_60days.setTag(profile_created_by.getJSONObject(3).getString("id"));
                                        rb_never.setTag(profile_created_by.getJSONObject(4).getString("id"));

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
                                Toast.makeText(CallPreference.this, error.toString(), Toast.LENGTH_LONG).show();
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {
                                    try {
                                        String res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));

                                        Toast.makeText(CallPreference.this, "res : " + res, Toast.LENGTH_LONG).show();

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


                RequestQueue requestQueue = Volley.newRequestQueue(CallPreference.this);
                requestQueue.add(stringRequest);

                Log.i("LK", "connected");
                if (call_pref_id.equalsIgnoreCase("1")) {
                    rb_anytime.setChecked(true);
                    rb_15days.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                }
                if (call_pref_id.equalsIgnoreCase("2")) {
                    rb_15days.setChecked(true);
                    rb_anytime.setChecked(false);
                    rb_30days.setChecked(false);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                }
                if (call_pref_id.equalsIgnoreCase("3")) {
                    rb_30days.setChecked(true);
                    rb_60days.setChecked(false);
                    rb_never.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_anytime.setChecked(false);
                }
                if (call_pref_id.equalsIgnoreCase("4")) {
                    rb_60days.setChecked(true);
                    rb_never.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_anytime.setChecked(false);
                    rb_30days.setChecked(false);
                }

                if (call_pref_id.equalsIgnoreCase("5")) {
                    rb_never.setChecked(true);
                    rb_60days.setChecked(false);
                    rb_15days.setChecked(false);
                    rb_anytime.setChecked(false);
                    rb_30days.setChecked(false);
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
        }
    };
}
