package com.example.easy_marry.swibetabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
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
 * Created by android2 on 14/6/16.
 */
public class ViewMyProfileFragment extends Fragment {
    //ImageView img_list, img_grid;
    Context context;
    ScrollView my_scrollView;
    private List<RecyclerBean> movieList = new ArrayList<>();
    private List<GridBean> gridBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    GridView grid;
    TextView txt_upgrade;
    private RecyclerAdapter mAdapter;
    String str_user_id;
    MyInterface mCallback;
   static SwipeRefreshLayout mySwipeRefresh;
    String strType;
    LinearLayout ll_no_internet_con,ll_view_match_list,ll_empty;
    ImageView img_reload;
    GeneralData gD;
    //String str_internet;
    public ViewMyProfileFragment(String user_id, Context ctx, String strFrom) {
        // Required empty public constructor
        str_user_id = user_id;
        this.context = ctx;
        strType=strFrom;

    }
    public ViewMyProfileFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.view_my_profile_tab, container, false);

        gD = new GeneralData(getActivity());
//my_scrollView=(ScrollView)v.findViewById(R.id.my_scroll);

        txt_upgrade = (TextView) v.findViewById(R.id.txt_view_my_prof_tab_upgrade);
        txt_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MembershipTabs.class));
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.tab_one_list);
        mySwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        ll_view_match_list=(LinearLayout)v.findViewById(R.id.ll_view_list);
        ll_no_internet_con=(LinearLayout)v.findViewById(R.id.ll_no_internet);
        ll_empty=(LinearLayout)v.findViewById(R.id.ll_empty);
        img_reload=(ImageView)v.findViewById(R.id.img_reload);
        Log.e("NH:", "view_profile-->"+Matches.internet_con);

        ((Matches)getActivity()).setFragmentRefreshListener(new Matches.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

                matchesListCall();
                ViewMyProfileFragment.mySwipeRefresh.setRefreshing(false);
                // Refresh Your Fragment
            }
        });
        gD.showAlertDialog(context,"Loading","Please wait...");
        if(Matches.internet_con.equalsIgnoreCase("yes")){
            gD.altDialog.dismiss();
            matchesListCall();
            mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    matchesListCall();

                }
            });
        }
        else{
            gD.altDialog.dismiss();
            Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            ll_view_match_list.setVisibility(View.GONE);
        }

        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gD.showAlertDialog(context,"Loading","Please wait...");
                Log.e("NN_connn", String.valueOf(Matches.internet_con));
                if (Matches.internet_con.equalsIgnoreCase("yes")) {
                    gD.altDialog.dismiss();
                    ll_view_match_list.setVisibility(View.VISIBLE);
                    matchesListCall();
                    mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            matchesListCall();
                            //  matchesGridCall();

                        }
                    });
                } else {
                    gD.altDialog.dismiss();
                    ll_view_match_list.setVisibility(View.GONE);
                    Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return v;

    }


    public void matchesListCall() {
        //matches rest call
        gD.showAlertDialog(context,"Loading","Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "viewedprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            ll_view_match_list.setVisibility(View.VISIBLE);
                            Log.i("HH", "strResp : " + response);
                            ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                gD.altDialog.dismiss();
                                JSONArray json_matched_profile = jsobj.getJSONArray("Response");


                                if (json_matched_profile.length() > 0) {
                                    for (int i = 0; i < json_matched_profile.length(); i++) {
                                        RecyclerBean drawerBean = new RecyclerBean();
                                        JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                                        drawerBean.setStr_id(matchesJSONObj.getString("userid"));
                                        drawerBean.setStr_easy_marry_id(matchesJSONObj.getString("easymarryid"));
                                        drawerBean.setStr_username(matchesJSONObj.getString("name"));
                                        drawerBean.setStr_userdetails(matchesJSONObj.getString("age") + "yrs,"
                                                + matchesJSONObj.getString("height") +
                                                "\n" + matchesJSONObj.getString("religious") +
                                                "\n" + matchesJSONObj.getString("city")
                                                + "," + matchesJSONObj.getString("state")
                                                + "," + matchesJSONObj.getString("country")
                                                + "\n" + matchesJSONObj.getString("qualification")
                                                + "\n" + matchesJSONObj.getString("occupation"));
                                        drawerBean.setStr_membership(matchesJSONObj.getString("membership"));

                                       drawerBean.setStr_interest_key(matchesJSONObj.getString("interested"));
                                  //  drawerBean.setStr_shorlist_key(matchesJSONObj.getString("shortlisted"));
                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                                        Log.i("NN", strImageURL);
                                        drawerBean.setStr_userimage(strImageURL);
                                        beanArrayList.add(drawerBean);
                                    }
                                }

                                mCallback = (MyInterface) context;

                                mAdapter = new RecyclerAdapter(beanArrayList, getContext(), mCallback, strType, recyclerView);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                mySwipeRefresh.setRefreshing(false);
                            }
                            else if(jsobj.getString("status").equalsIgnoreCase("failure")){
                                gD.altDialog.dismiss();
                                ll_view_match_list.setVisibility(View.GONE);
                                ll_no_internet_con.setVisibility(View.GONE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ll_view_match_list.setVisibility(View.GONE);
                        gD.altDialog.dismiss();
                       // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                gD.altDialog.dismiss();
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                Toast.makeText(getContext(), "res : " + res, Toast.LENGTH_LONG).show();

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


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


}