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
import android.widget.Switch;
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
public class Notification extends Activity {
    Switch sw_daily_recom, sw_view_profile, sw_match_prof, sw_shorlist, sw_all_notify, sw_sms;
    ImageView img_back;
    Context context;
    GeneralData gD;
    String str_user_id, str_daily_recomm_notify, str_matches_notify, str_shortlist_notify, str_view_profile_notify, str_all_notify;
    String str_all_not, str_recom, str_viewprof, str_match_prof, str_shorlist;
    static int n_count = 0;
    IntentFilter filter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        context = this;
        gD = new GeneralData(context);
        str_user_id = gD.prefs.getString("userid", null);
        img_back=(ImageView)findViewById(R.id.img_not_back);
        sw_daily_recom = (Switch) findViewById(R.id.sw_daily_recom);
        sw_view_profile = (Switch) findViewById(R.id.sw_view_prof);
        sw_match_prof = (Switch) findViewById(R.id.sw_new_match);
        sw_shorlist = (Switch) findViewById(R.id.sw_shortlist);
        sw_all_notify = (Switch) findViewById(R.id.sw_notification);
        sw_sms = (Switch) findViewById(R.id.sw_alert);

        str_daily_recomm_notify = gD.prefs.getString("daily_recomm_notify", null);
        str_matches_notify = gD.prefs.getString("matches_notify", null);
        str_shortlist_notify = gD.prefs.getString("shortlist_notify", null);
        str_view_profile_notify = gD.prefs.getString("view_profile_notify", null);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        img_back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});


        sw_all_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    if (buttonView.isChecked()) {
                        sw_daily_recom.setChecked(true);
                        sw_view_profile.setChecked(true);
                        sw_match_prof.setChecked(true);
                        sw_shorlist.setChecked(true);
                        sw_all_notify.setChecked(true);

                        str_all_not = "1";
                        str_recom = "";
                        str_viewprof = "";
                        str_match_prof = "";
                        str_shorlist = "";

                        notificationOnOffCall();


                    } else {
                        if (sw_view_profile.isChecked() && sw_match_prof.isChecked() && sw_shorlist.isChecked() && sw_daily_recom.isChecked()) {

                            sw_all_notify.setChecked(false);
                            sw_daily_recom.setChecked(false);
                            sw_view_profile.setChecked(false);
                            sw_match_prof.setChecked(false);
                            sw_shorlist.setChecked(false);

                            str_all_not = "0";
                            str_recom = "";
                            str_viewprof = "";
                            str_match_prof = "";
                            str_shorlist = "";

                            notificationOnOffCall();

                        }
                    }
                }
                else{
                    Toast.makeText(Notification.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sw_daily_recom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (!sw_daily_recom.isChecked()) {
                    sw_daily_recom.setChecked(false);
                    if (sw_all_notify.isChecked()) {
                        sw_all_notify.setChecked(false);
                    }
                    str_all_not = "";
                    str_recom = "0";

                    notificationOnOffCall();


                } else {
                    sw_daily_recom.setChecked(true);
                    if (sw_view_profile.isChecked() && sw_match_prof.isChecked() && sw_shorlist.isChecked()) {
                        sw_all_notify.setChecked(true);
                    }
                   str_all_not = "";
                    str_recom = "1";

                    notificationOnOffCall();

                }
                }
                else{
                    Toast.makeText(Notification.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });

        sw_view_profile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (!sw_view_profile.isChecked()) {
                    sw_view_profile.setChecked(false);
                    if (sw_all_notify.isChecked()) {
                        sw_all_notify.setChecked(false);
                    }
                    str_all_not = "";
                    str_viewprof = "0";
                    notificationOnOffCall();

                } else {
                    sw_view_profile.setChecked(true);
                    if (sw_daily_recom.isChecked() && sw_match_prof.isChecked() && sw_shorlist.isChecked()) {
                        sw_all_notify.setChecked(true);
                    }
                    str_all_not = "";
                    str_viewprof = "1";
                    notificationOnOffCall();

                }
                }
                else{
                    Toast.makeText(Notification.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        sw_match_prof.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (!sw_match_prof.isChecked()) {
                    sw_match_prof.setChecked(false);

                    if (sw_all_notify.isChecked()) {
                        sw_all_notify.setChecked(false);
                    }
                    str_all_not = "";
                    str_match_prof = "0";
                    notificationOnOffCall();

                } else {
                    sw_match_prof.setChecked(true);

                    if (sw_daily_recom.isChecked() && sw_view_profile.isChecked() && sw_shorlist.isChecked()) {
                        sw_all_notify.setChecked(true);
                    }
                    str_all_not = "";
                    str_match_prof = "1";
                    notificationOnOffCall();

                }
                }
                else{
                    Toast.makeText(Notification.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
        sw_shorlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                if (!sw_shorlist.isChecked()) {
                    sw_shorlist.setChecked(false);

                    if (sw_all_notify.isChecked()) {
                        sw_all_notify.setChecked(false);
                    }
                    str_all_not = "";
                    str_shorlist = "0";
                    notificationOnOffCall();

                } else {
                    sw_shorlist.setChecked(true);

                    if (sw_daily_recom.isChecked() && sw_view_profile.isChecked() && sw_match_prof.isChecked()) {
                        sw_all_notify.setChecked(true);
                    }
                    str_all_not = "";
                    str_shorlist = "1";
                    notificationOnOffCall();

                }
                }
                else{
                    Toast.makeText(Notification.this, "Unable to connect to internet, your progress can't be saved", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void notificationOnOffCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "notification.php",
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
                                str_daily_recomm_notify=json_resp.getString("recommendnotification");
                                str_matches_notify=json_resp.getString("matchesnotification");
                                str_shortlist_notify=json_resp.getString("shortlistnotification");
                                str_view_profile_notify=json_resp.getString("viewednotification");
                                Log.e("NN:note", "view_key1-" + str_view_profile_notify);
                                Log.e("NN:note", "match_key1-" + str_matches_notify);
                                Log.e("NN:note", "daily_key1-" + str_daily_recomm_notify);
                                Log.e("NN:note", "short_key1-" + str_shortlist_notify);
                                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                                prefEdit.putString("daily_recomm_notify", str_daily_recomm_notify);
                                prefEdit.putString("matches_notify", str_matches_notify);
                                prefEdit.putString("shortlist_notify", str_shortlist_notify);
                                prefEdit.putString("view_profile_notify", str_view_profile_notify);
                                prefEdit.commit();
                                Toast.makeText(Notification.this, "Upadated!!!!!", Toast.LENGTH_SHORT).show();
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

                Log.e("NN:note", "aal-" + str_all_not);
                Log.e("NN:note", "short-" + str_shorlist);
                Log.e("NN:note", "view-" + str_viewprof);
                Log.e("NN:note", "match-" + str_match_prof);
                Log.e("NN:note", "daily-" + str_recom);
                Log.e("NN:note", "user_id-" + str_user_id);
                Log.e("NN:note", "view_key-" + str_view_profile_notify);
                Log.e("NN:note", "match_key-" + str_matches_notify);
                Log.e("NN:note", "daily_key-" + str_daily_recomm_notify);
                Log.e("NN:note", "short_key-" + str_shortlist_notify);


                if (str_all_not!=null) {
                    params.put("allnotify", str_all_not);
                }
                if (str_recom!=null) {
                    params.put("recommendnotify", str_recom);
                }
                if (str_match_prof!=null) {
                    params.put("matchesnotify", str_match_prof);
                }
                if (str_shorlist!=null) {
                    params.put("shortlistnotify", str_shorlist);
                }
                if (str_viewprof!=null) {
                    params.put("viewednotify", str_viewprof);
                }
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

                Log.i("LK", "connected");
                if(str_daily_recomm_notify.equalsIgnoreCase("1")&&str_shortlist_notify.equalsIgnoreCase("1")&&
                        str_view_profile_notify.equalsIgnoreCase("1")&&str_matches_notify.equalsIgnoreCase("1")){
                    str_all_not = "1";
                    str_recom = "";
                    str_viewprof = "";
                    str_match_prof = "";
                    str_shorlist = "";
                    sw_daily_recom.setChecked(true);
                    sw_view_profile.setChecked(true);
                    sw_match_prof.setChecked(true);
                    sw_shorlist.setChecked(true);
                    sw_all_notify.setChecked(true);
                    notificationOnOffCall();

                }
                else if(str_daily_recomm_notify.equalsIgnoreCase("0")&&str_shortlist_notify.equalsIgnoreCase("0")&&
                        str_view_profile_notify.equalsIgnoreCase("0")&&str_matches_notify.equalsIgnoreCase("0")){
                    str_all_not = "0";
                    str_recom = "";
                    str_viewprof = "";
                    str_match_prof = "";
                    str_shorlist = "";
                    sw_daily_recom.setChecked(false);
                    sw_view_profile.setChecked(false);
                    sw_match_prof.setChecked(false);
                    sw_shorlist.setChecked(false);
                    sw_all_notify.setChecked(false);
                    notificationOnOffCall();
                }

                else {
                    if (str_matches_notify.equalsIgnoreCase("1")) {

                        sw_match_prof.setChecked(true);
                        //sw_all_notify.setChecked(false);
                    } else {
                        sw_match_prof.setChecked(false);
                        sw_all_notify.setChecked(false);
                    }
                    if (str_daily_recomm_notify.equalsIgnoreCase("1")) {

                        sw_daily_recom.setChecked(true);
                        // sw_all_notify.setChecked(false);
                    } else {
                        sw_daily_recom.setChecked(false);
                        sw_all_notify.setChecked(false);
                    }
                    if (str_view_profile_notify.equalsIgnoreCase("1")) {

                        sw_view_profile.setChecked(true);
                        //sw_all_notify.setChecked(false);
                    } else {
                        sw_view_profile.setChecked(false);
                        sw_all_notify.setChecked(false);
                    }
                    if (str_shortlist_notify.equalsIgnoreCase("1")) {

                        sw_shorlist.setChecked(true);
                        //sw_all_notify.setChecked(false);
                    } else {
                        sw_shorlist.setChecked(false);
                        sw_all_notify.setChecked(false);
                    }
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
