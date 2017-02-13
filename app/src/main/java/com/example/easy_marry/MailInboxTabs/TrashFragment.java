package com.example.easy_marry.MailInboxTabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.easy_marry.Adapter.RecyclerMailNewAdapter;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Bean.RecyclerMailBean;
import com.example.easy_marry.Interface.MailBoxInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by android2 on 21/6/16.
 */
public class TrashFragment extends android.support.v4.app.Fragment {
//ImageView img_list, img_grid;


    private RecyclerView recyclerView;
    private RecyclerMailNewAdapter mAdapter;
    RelativeLayout rl_mail_list;
    Context context;
    String str_from, str_user_id;
    SwipeRefreshLayout mySwipeRefresh;
    MailBoxInterface mCallback;
    TextView txt_mail_error;
    ImageView img_reload;
    LinearLayout ll_mail_empty;

    public TrashFragment() {
        // Required empty public constructor

    }

    public TrashFragment(String str_userid, Context con, String str_type) {
        // Required empty public constructor
        context = con;
        str_from = str_type;
        str_user_id = str_userid;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("NDD", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View v = inflater.inflate(R.layout.inbox_fragment, container, false);
        rl_mail_list = (RelativeLayout) v.findViewById(R.id.rl_mail_list);
        recyclerView = (RecyclerView) v.findViewById(R.id.tab_one_list);
        mySwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        txt_mail_error = (TextView) v.findViewById(R.id.txt_mail_error);
        ll_mail_empty = (LinearLayout) v.findViewById(R.id.ll_mail_empty);
        img_reload = (ImageView) v.findViewById(R.id.img_reload);


        //prepareMovieData();
        final ProgressDialog loading = ProgressDialog.show(context, "Loading", "Please wait...", false, false);
        if (MailboxNewTabs.internet_con.equalsIgnoreCase("yes")) {
            loading.dismiss();
            trashListCall();
            mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    trashListCall();
                    //  matchesGridCall();

                }
            });
        } else {
            loading.dismiss();
            Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            rl_mail_list.setVisibility(View.GONE);
            ll_mail_empty.setVisibility(View.GONE);
        }


        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog loading = ProgressDialog.show(context, "Loading", "Please wait...", false, false);

                if (MailboxNewTabs.internet_con.equalsIgnoreCase("yes")) {
                    loading.dismiss();
                    trashListCall();
                    mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            trashListCall();
                            //  matchesGridCall();

                        }
                    });
                } else {
                    loading.dismiss();
                    Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                    rl_mail_list.setVisibility(View.GONE);
                    ll_mail_empty.setVisibility(View.GONE);
                }
            }
        });

        return v;

    }

    public void trashListCall() {
        //matches rest call
        final ProgressDialog loading = ProgressDialog.show(context, "Loading", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "trash.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            Log.i("HH", "strResp : " + response);
                            ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                rl_mail_list.setVisibility(View.VISIBLE);
                                JSONArray json_matched_profile = jsobj.getJSONArray("Response");


                                if (json_matched_profile.length() > 0) {
                                    for (int i = 0; i < json_matched_profile.length(); i++) {

                                        RecyclerBean drawerBean = new RecyclerBean();
                                        JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                                        drawerBean.setStr_easy_marry_id(matchesJSONObj.getString("easymarryid"));
                                        drawerBean.setStr_id(matchesJSONObj.getString("userid"));
                                        drawerBean.setStr_username(matchesJSONObj.getString("name"));
                                        drawerBean.setStr_mail_id(matchesJSONObj.getString("trashid"));
                                        drawerBean.setStr_date_time(matchesJSONObj.getString("trashedtime"));
                                        drawerBean.setStr_userdetails(matchesJSONObj.getString("age") + "yrs,"
                                                + matchesJSONObj.getString("height") +
                                                "\n" + matchesJSONObj.getString("caste") +
                                                "," + matchesJSONObj.getString("religion"));
                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                                        Log.i("NN", "trash->"+strImageURL);
                                        drawerBean.setStr_userimage(strImageURL);
                                        beanArrayList.add(drawerBean);
                                    }
                                }

                                mCallback = (MailBoxInterface) context;

                                mAdapter = new RecyclerMailNewAdapter(beanArrayList, getContext(), mCallback, str_from, recyclerView);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                mySwipeRefresh.setRefreshing(false);
                                loading.dismiss();

                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                rl_mail_list.setVisibility(View.GONE);
                                ll_mail_empty.setVisibility(View.VISIBLE);
                                txt_mail_error.setText("Your trash is empty....");
                               // Toast.makeText(context, "No data found!!!", Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //   Toast.makeText(getActivity(), "res : " + res, Toast.LENGTH_LONG).show();

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
                Log.e("NN:in", str_user_id);
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


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}