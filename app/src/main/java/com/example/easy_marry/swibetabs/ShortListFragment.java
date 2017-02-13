package com.example.easy_marry.swibetabs;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Interface.MyInterface;
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
public class ShortListFragment extends Fragment {
    //ImageView img_list, img_grid;
    Context context;


    private List<RecyclerBean> movieList = new ArrayList<>();
    private List<GridBean> gridBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    GridView grid;
    private RecyclerAdapter mAdapter;
    String str_user_id;
    MyInterface mCallback;
    static SwipeRefreshLayout mySwipeRefresh;
    String strType;
    RelativeLayout rl_matches_list;
    LinearLayout ll_no_internet_con;
    ImageView img_reload;
    TextView txt_error_msg;
    GeneralData gD;
    public ShortListFragment(String user_id, Context ctx, String strFrom) {
        // Required empty public constructor
        str_user_id = user_id;
        this.context = ctx;
        strType = strFrom;

    }

    public ShortListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.new_matches, container, false);
        gD = new GeneralData(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.tab_one_list);
        mySwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
txt_error_msg=(TextView)v.findViewById(R.id.txt_tab_error_msg) ;
        rl_matches_list=(RelativeLayout)v.findViewById(R.id.rl_match_list);
        ll_no_internet_con=(LinearLayout)v.findViewById(R.id.ll_no_internet);
        img_reload=(ImageView)v.findViewById(R.id.img_reload);
        Log.e("NH:", "shorlisted-->"+Matches.internet_con);

        ((Matches)getActivity()).setFragmentRefreshListener(new Matches.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                ShortListFragment.mySwipeRefresh.setRefreshing(false);
                matchesListCall();

                // Refresh Your Fragment
            }
        });
        gD.showAlertDialog(context,"Loading","please wait...");
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
            rl_matches_list.setVisibility(View.GONE);
        }

        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gD.showAlertDialog(context,"Loading","please wait...");
                Log.e("NN_connn", String.valueOf(Matches.internet_con));
                if (Matches.internet_con.equalsIgnoreCase("yes")) {
                    gD.altDialog.dismiss();
                    rl_matches_list.setVisibility(View.VISIBLE);
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
                    rl_matches_list.setVisibility(View.GONE);
                    Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return v;

    }


    public void matchesListCall() {
        //matches rest call
        gD.showAlertDialog(context,"Loading","please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "shortlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            rl_matches_list.setVisibility(View.VISIBLE);
                            Log.i("HH", "strResp : " + response);
                            ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            String str_resp;
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                str_resp = "success";
                                JSONArray json_matched_profile = jsobj.getJSONArray("Response");
                                gD.altDialog.dismiss();

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

                                        if (matchesJSONObj.has("shortlisted")) {
                                            drawerBean.setStr_shorlist_key(matchesJSONObj.getString("shortlisted"));
                                        }

                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                                        Log.i("NN", strImageURL);
                                        Log.e("NN:s_key1", drawerBean.getStr_shorlist_key());
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
                                rl_matches_list.setVisibility(View.GONE);
                                ll_no_internet_con.setVisibility(View.GONE);
                                txt_error_msg.setText("You have not shorlisted any profiles.");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        rl_matches_list.setVisibility(View.GONE);
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
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