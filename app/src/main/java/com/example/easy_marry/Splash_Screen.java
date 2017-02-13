package com.example.easy_marry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Splash_Screen extends AppCompatActivity {
    public static Context context;
    Context ctx;
    private CoordinatorLayout coordinatorLayout;
    GeneralData gD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MultiDex.install(this);
        context = this;
ctx=this;

        gD = new GeneralData(context);



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
        GeneralData.filter_count=0;
        GeneralData.filter_count1=0;

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


        RequestQueue requestQueue = Volley.newRequestQueue(Splash_Screen.this);
        requestQueue.add(stringRequest);
        Log.e("GN_Val:", String.valueOf(GeneralData.n_count));
        if(GeneralData.n_count==1){
            gD.showAlertDialog(ctx,"Accessing Your Pofile","Please wait...");
        }

        Thread timethread = new Thread() {

            public void run() {
                try {
                    sleep(3000);
                    Intent nextpage = new Intent(Splash_Screen.this, HomeScreen.class);
                    startActivity(nextpage);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timethread.start();

        /*SharedPreferences sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson1 = new Gson();
        String json1 = sharedPrefs1.getString("MK", null);
        Type type = new TypeToken<ArrayList<ListDrawerBean>>() {}.getType();
        ArrayList<ListDrawerBean> arrayList = gson1.fromJson(json1, type);

        Log.e("MK", "two_arraylist-->"+ String.valueOf(arrayList));
        Log.e("MK", "two-->"+ String.valueOf(json1));*/
    }
}