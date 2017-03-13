package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.swibetabs.Matches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android2 on 14/11/16.
 */
public class SearchResults extends Activity implements MyInterface {
    String str_search_res_json;
    RecyclerAdapter mAdapter;
    RecyclerView recyclerView;
    Context context;
    String strResp1 = "";
    String interest_msg="";
    String strResp = "";
    String str_user_id, str_image, str_name, str_prof_compl_level, str_easy_marry_id,str_membership,str_rate;
    GeneralData gD;
    ImageView img_back;
    SwipeRefreshLayout mySwipeRefresh;
    IntentFilter filter1;
    RelativeLayout rl_search_list;
    ImageView img_reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        context=this;
        gD = new GeneralData(context);
        mySwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.activity_main_swipe_refresh_layout);
        str_user_id = gD.prefs.getString("userid", null);
        str_image = gD.prefs.getString("profileimage", null);
        str_name = gD.prefs.getString("name", null);
        str_prof_compl_level = gD.prefs.getString("completelevel", null);
        str_easy_marry_id = gD.prefs.getString("easymarryid", null);
        str_membership=gD.prefs.getString("memplan",null);
        str_rate=gD.prefs.getString("Rating",null);
        img_back=(ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.tab_one_list);
        img_reload=(ImageView)findViewById(R.id.img_reload);
        rl_search_list=(RelativeLayout)findViewById(R.id.rl_search_list);
        str_search_res_json=getIntent().getStringExtra("qsearch_json");
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);

        mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchResults();
            }
        });
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog loading = ProgressDialog.show(context, "Loading", "Please wait...", false, false);
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
                if (isConnected) {
                    loading.dismiss();
                    searchResults();
                    mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            searchResults();
                        }
                    });
                }
                else{
                    loading.dismiss();
                    Toast.makeText(SearchResults.this, "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
public void searchResults(){
    try {
        Log.i("HH", "strResp : " + str_search_res_json);
        ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

        JSONObject jsobj = new JSONObject(str_search_res_json);

        Log.i("HH", "strResp : " + str_search_res_json);
        if (jsobj.getString("status").equalsIgnoreCase("success")) {

            JSONArray json_matched_profile = jsobj.getJSONArray("Response");


            if (json_matched_profile.length() > 0) {
                for (int i = 0; i < json_matched_profile.length(); i++) {

                    RecyclerBean drawerBean = new RecyclerBean();
                    JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                    drawerBean.setStr_easy_marry_id(matchesJSONObj.getString("easymarryid"));
                    drawerBean.setStr_id(matchesJSONObj.getString("userid"));
                    drawerBean.setStr_username(matchesJSONObj.getString("name"));
                    drawerBean.setStr_userdetails(matchesJSONObj.getString("age") + "yrs,"
                            + matchesJSONObj.getString("height") +
                            "\n" + matchesJSONObj.getString("religious") +
                            "\n" + matchesJSONObj.getString("city")
                            + "," + matchesJSONObj.getString("state")
                            + "," + matchesJSONObj.getString("country")
                            + "\n" + matchesJSONObj.getString("qualification")
                            + "\n" + matchesJSONObj.getString("occupation"));
                    drawerBean.setStr_addr(matchesJSONObj.getString("city")
                            + "," + matchesJSONObj.getString("state")
                            + "," + matchesJSONObj.getString("country"));
                    drawerBean.setStr_membership(matchesJSONObj.getString("membership"));
                    drawerBean.setStr_interest_key(matchesJSONObj.getString("interested"));
                    drawerBean.setStr_shorlist_key(matchesJSONObj.getString("shortlisted"));
                    String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                    Log.i("NN", strImageURL);
                    drawerBean.setStr_userimage(strImageURL);
                    beanArrayList.add(drawerBean);
                }
            }



            mAdapter = new RecyclerAdapter(beanArrayList, context, (MyInterface) context, "search", recyclerView);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            rl_search_list.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mySwipeRefresh.setRefreshing(false);


        }
        else if (jsobj.getString("status").equalsIgnoreCase("failure")){
            Toast.makeText(SearchResults.this, "Select aleast one field!!", Toast.LENGTH_SHORT).show();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @Override
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> str_json_obj) {

    }

    @Override
    public String get_matches(final String str_partner_id, final String str_partner_name, String strFrom, String str_type, String str_sent_int, RecyclerView recycleV) {
        if (str_type.equalsIgnoreCase("send_interest")) {
            if (!strFrom.equalsIgnoreCase("id search")) {


                strResp1= sendInterestCall(str_user_id, str_partner_id, str_sent_int, "1");



            }

        } else if (str_type.equalsIgnoreCase("shortlist")) {
            if (!strFrom.equalsIgnoreCase("shortlist")) {
                strResp1 = shortListCall(str_user_id, str_partner_id);
            } else {
                strResp1 = shortListRemoveCall(str_user_id, str_partner_id);
            }


        } else if (str_type.equalsIgnoreCase("view_profile")) {

            viewProfileCall(str_user_id,str_partner_name, str_partner_id);

        }

        /*if(strFrom.equalsIgnoreCase("shortlist"))
        {
            strResp = shortListCall(str_user_id, str_id);
        }*/
        return strResp1;

    }

    @Override
    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String strRemovedVal, String strIdentify) {
        return null;
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

                Log.e("NN:fromid",str_my_id);
                Log.e("NN:toid",str_partner_id);
                Log.e("NN:status",status);
                Log.e("NN:message",str_interest_msg);

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

        return str_partner_id;
    }

    public String shortListCall(final String str_my_id, final String str_partner_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "shortlist_user.php",
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
                                Toast.makeText(context, "Shortlisted!!", Toast.LENGTH_SHORT).show();
                                // strResp = "backcolor";
                                strResp = "success";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        strResp = "failure";
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

                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);


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

        return strResp;
    }

    public String shortListRemoveCall(final String str_my_id, final String str_partner_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "shortlist_remove.php",
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
                                Toast.makeText(context, "Removed!!", Toast.LENGTH_SHORT).show();
                                strResp = "success";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        strResp = "failure";
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

                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);


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

        return strResp;
    }

    // rest call to view other profiles so that it can appear in viewd my profile for other user
    public String viewProfileCall(final String str_my_id, final String str_partner_name, final String str_partner_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "viewprofile.php",
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

                                JSONObject responseJsonObj = jsobj.getJSONObject("Response");
                                String str_sent_interest = responseJsonObj.getString("interested");


                                Log.e("view_profile", "success");
                                Intent i = new Intent(SearchResults.this, DailyRecomViewProfie.class);
                                i.putExtra("str_from", "my matches");
                                i.putExtra("user_id", str_partner_id);
                                i.putExtra("user_name",str_partner_name);
                                i.putExtra("sent_int",str_sent_interest);
                                startActivity(i);

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
                Log.e("v_from", str_my_id);
                Log.e("v_to", str_partner_id);
                params.put("fromid", str_my_id);
                params.put("toid", str_partner_id);


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

        return strResp;
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
                searchResults();

            } else {
                Log.i("LK", "not connected");
                rl_search_list.setVisibility(View.GONE);
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
