package com.example.easy_marry.swibetabs;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import com.example.easy_marry.Adapter.GridAdapter;
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.EditProfiles.EditProfile;
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
public class MatchesFragment extends Fragment {
//ImageView img_list, img_grid;

    private List<RecyclerBean> movieList = new ArrayList<>();
    private List<GridBean> gridBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    GridView grid;
    String Str_user_id;

    static SwipeRefreshLayout mySwipeRefresh,mySwipeRefresh1;
    private RecyclerAdapter mAdapter;
    GridAdapter adapter;
    MyInterface mCallback, myInterf;
    RelativeLayout rl_matches_list;
    LinearLayout ll_no_internet_con;
    ImageView img_reload;
    Context context;
    String strType;
    String str_filterJSON;
    IntentFilter in_filter;
    long currentVisiblePosition = 0;
    public MatchesFragment(String str_user_id, Context ctx, String strFrom, String str_filter_josn) {
        // Required empty public constructor
        Str_user_id = str_user_id;
        str_filterJSON = str_filter_josn;
        this.context = ctx;
        this.strType = strFrom;

      /*  in_filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(breceive, in_filter);*/
    }

    public MatchesFragment() {
        // Required empty public constructor


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
/*context=container.getContext();*/

        View v = inflater.inflate(R.layout.matches_tab_one, container, false);

        final ImageView img_list = (ImageView) v.findViewById(R.id.img_list);
        final ImageView img_grid = (ImageView) v.findViewById(R.id.img_grid);
        mySwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        mySwipeRefresh1 = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout1);
        recyclerView = (RecyclerView) v.findViewById(R.id.tab_one_list);
        rl_matches_list = (RelativeLayout) v.findViewById(R.id.rl_match_list);
        ll_no_internet_con = (LinearLayout) v.findViewById(R.id.ll_no_internet);
        grid = (GridView) v.findViewById(R.id.tab_one_grid);
        img_reload = (ImageView) v.findViewById(R.id.img_reload);

        Log.e("NH:", "matches-->" + Matches.internet_con);

        Log.e("NH:", "filter(matchesFRag)-->" + str_filterJSON);
        ((Matches) getActivity()).setFragmentRefreshListener(new Matches.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                if (grid.getVisibility() == View.VISIBLE) {

                    matchesGridCall();
                }
                if (recyclerView.getVisibility() == View.VISIBLE) {

                    matchesListCall();
                }


            }
        });

        if (Matches.internet_con.equalsIgnoreCase("yes")) {

            rl_matches_list.setVisibility(View.VISIBLE);

            if (str_filterJSON != null) {
                filterJsonCallGrid();
                Log.e("NH:", "filter(matchesFRag)1-->" + str_filterJSON);
            } else {
                matchesListCall();
                matchesGridCall();
            }

            mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (str_filterJSON != null) {
                        filterJsonCallList();
                        Log.e("NH:", "filter(matchesFRag)2-->" + str_filterJSON);
                    } else {
                        matchesListCall();
                        // matchesGridCall();
                    }
                    //matchesListCall();
                    //  matchesGridCall();

                }
            });
            mySwipeRefresh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (str_filterJSON != null) {
                        filterJsonCallGrid();
                        Log.e("NH:", "filter(matchesFRag)2-->" + str_filterJSON);
                    } else {
                        matchesGridCall();
                        // matchesGridCall();
                    }
                    //matchesListCall();
                    //  matchesGridCall();

                }
            });
            img_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    img_list.setBackgroundResource(R.drawable.list_green);
                    img_grid.setBackgroundResource(R.drawable.grid_grey);
                    grid.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    if (str_filterJSON != null) {
                        filterJsonCallList();
                        Log.e("NH:", "filter(matchesFRag)3-->" + str_filterJSON);
                    } else {
                        matchesListCall();
                    }


                }
            });
            img_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    img_list.setBackgroundResource(R.drawable.list_grey);
                    img_grid.setBackgroundResource(R.drawable.grid_green);

                    recyclerView.setVisibility(View.GONE);
                    grid.setVisibility(View.VISIBLE);

                    if (str_filterJSON != null) {
                        filterJsonCallGrid();
                        Log.e("NH:", "filter(matchesFRag)4-->" + str_filterJSON);
                    } else {
                        matchesGridCall();
                    }


                }
            });
        } else {
            Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            rl_matches_list.setVisibility(View.GONE);
        }


        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("NN_connn", String.valueOf(Matches.internet_con));
                if (Matches.internet_con.equalsIgnoreCase("yes")) {

                    rl_matches_list.setVisibility(View.VISIBLE);
                    matchesListCall();
                    matchesGridCall();
                    mySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            matchesListCall();
                            //  matchesGridCall();

                        }
                    });
                    mySwipeRefresh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            matchesGridCall();
                            //  matchesGridCall();

                        }
                    });
                } else {

                    rl_matches_list.setVisibility(View.GONE);
                    Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return v;

    }

    public void filterJsonCallGrid() {
        try {
            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();


            JSONObject jsobj = new JSONObject(str_filterJSON);

            Log.i("HH", "strResp : " + str_filterJSON);
            if (jsobj.getString("status").equalsIgnoreCase("success")) {

                JSONArray json_matched_profile = jsobj.getJSONArray("Response");


                if (json_matched_profile.length() > 0) {
                    for (int i = 0; i < json_matched_profile.length(); i++) {

                        GridBean drawerBean = new GridBean();
                        JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                        drawerBean.setStr_id(matchesJSONObj.getString("userid"));
                        drawerBean.setStr_easy_marry_id(matchesJSONObj.getString("easymarryid"));
                        drawerBean.setStr_username(matchesJSONObj.getString("name"));
                        drawerBean.setStr_userdetails(matchesJSONObj.getString("age") + ","
                                + matchesJSONObj.getString("height") +
                                "\n" + matchesJSONObj.getString("religious") +
                                "\n" + matchesJSONObj.getString("city")
                                + "," + matchesJSONObj.getString("state")
                                + "," + matchesJSONObj.getString("country")
                                + "\n" + matchesJSONObj.getString("qualification")
                                + "\n" + matchesJSONObj.getString("occupation"));
                        drawerBean.setStr_membership(matchesJSONObj.getString("membership"));
                        drawerBean.setStr_interest_key(matchesJSONObj.getString("interested"));
                        drawerBean.setStr_shorlist_key(matchesJSONObj.getString("shortlisted"));
                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                        Log.i("NN", strImageURL);
                        drawerBean.setStr_userimage(strImageURL);
                        beanArrayList.add(drawerBean);
                    }

                    Log.i("VAL", "ArrayLIst :" + beanArrayList.size());

                    mCallback = (MyInterface) context;
                    adapter = new GridAdapter(beanArrayList, getActivity(), mCallback, strType);
                    grid.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mySwipeRefresh1.setRefreshing(false);

                }


            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {

                String filldetails = jsobj.getString("filldetails");
                String str_completeness = jsobj.getString("completeness");
                if (filldetails.equalsIgnoreCase("0")) {

                                    /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setTitle("Incomplete Partner Preference");
                                    alertDialogBuilder
                                            .setMessage("Your partner preference is incomplete, kindly fill it up to get your matches")
                                            .setCancelable(false)
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    startActivity(new Intent(context, EditProfile.class));
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();*/
                    View itemView1;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(context)
                            .inflate(R.layout.custom_incomplete_partner_det_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startActivity(new Intent(context, EditProfile.class));
                        }
                    });
                    altDialog.show();

                }

            }


        } catch (Exception ex) {

        }

    }

    public void filterJsonCallList() {
        try {
            ArrayList<RecyclerBean> beanArrayList = new ArrayList<RecyclerBean>();

            JSONObject jsobj = new JSONObject(str_filterJSON);

            Log.i("HH", "strResp : " + str_filterJSON);
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
                        drawerBean.setStr_phone(matchesJSONObj.getString("mobile"));
                        drawerBean.setStr_email(matchesJSONObj.getString("emailid"));
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


            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {

                String filldetails = jsobj.getString("filldetails");
                String str_completeness = jsobj.getString("completeness");
                if (filldetails.equalsIgnoreCase("0")) {

                    View itemView1;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(context)
                            .inflate(R.layout.custom_incomplete_partner_det_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startActivity(new Intent(context, EditProfile.class));
                        }
                    });
                    altDialog.show();
                }

            }


        } catch (Exception ex) {

        }

    }

    public void matchesListCall() {
        //matches rest call
        View itemView1;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        itemView1 = LayoutInflater.from(context)
                .inflate(R.layout.progress_loading_popup, null);
        final AlertDialog altDialog = builder.create();
        altDialog.setView(itemView1);
        ProgressBar pb=(ProgressBar)itemView1.findViewById(R.id.progressBar);
        pb.setIndeterminate(true);
        pb.getIndeterminateDrawable().setColorFilter(Color.parseColor("#639639"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        TextView txt_title=(TextView)itemView1.findViewById(R.id.txt_title);
        TextView txt_content=(TextView)itemView1.findViewById(R.id.txt_content);
        txt_title.setText("Loading");
        txt_content.setText("Please wait...");
        altDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "matches.php",
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
                                        drawerBean.setStr_phone(matchesJSONObj.getString("mobile"));
                                        drawerBean.setStr_email(matchesJSONObj.getString("emailid"));
                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                                        Log.i("NN", strImageURL);
                                        drawerBean.setStr_userimage(strImageURL);
                                        beanArrayList.add(drawerBean);
                                    }
                                }

                                mCallback = (MyInterface) context;
                                // mCallback.get_matches("","","matches","",null);

                                mAdapter = new RecyclerAdapter(beanArrayList, getContext(), mCallback, strType, recyclerView);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);


                            mAdapter.notifyDataSetChanged();
                                mySwipeRefresh.setRefreshing(false);
                                altDialog.dismiss();
                                altDialog.cancel();


                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {

                                String filldetails = jsobj.getString("filldetails");
                                String str_completeness = jsobj.getString("completeness");
                                if (filldetails.equalsIgnoreCase("0")) {

                                    View itemView1;
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setCancelable(true);
                                    itemView1 = LayoutInflater.from(context)
                                            .inflate(R.layout.custom_incomplete_partner_det_popup, null);
                                    final AlertDialog altDialog = builder.create();
                                    altDialog.setView(itemView1);
                                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            startActivity(new Intent(context, EditProfile.class));
                                        }
                                    });

                                    altDialog.dismiss();
                                    altDialog.cancel();
                                    altDialog.show();
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        altDialog.dismiss();
                        altDialog.cancel();
                        rl_matches_list.setVisibility(View.GONE);
                        // Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            altDialog.dismiss();
                            altDialog.cancel();
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

                params.put("userid", Str_user_id);


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

    public void matchesGridCall() {
        //matches rest call
        View itemView1;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        itemView1 = LayoutInflater.from(context)
                .inflate(R.layout.progress_loading_popup, null);
        final AlertDialog altDialog = builder.create();
        altDialog.setView(itemView1);
        ProgressBar pb=(ProgressBar)itemView1.findViewById(R.id.progressBar);
        pb.setIndeterminate(true);
        pb.getIndeterminateDrawable().setColorFilter(Color.parseColor("#639639"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        TextView txt_title=(TextView)itemView1.findViewById(R.id.txt_title);
        TextView txt_content=(TextView)itemView1.findViewById(R.id.txt_content);
        txt_title.setText("Loading");
        txt_content.setText("Please wait...");
        altDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "matches.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            rl_matches_list.setVisibility(View.VISIBLE);

                            ArrayList<GridBean> beanArrayList = new ArrayList<GridBean>();

                            JSONObject jsobj = new JSONObject(response);

                            Log.i("HH", "strResp : " + response);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                altDialog.dismiss();
                                altDialog.cancel();
                                JSONArray json_matched_profile = jsobj.getJSONArray("Response");


                                if (json_matched_profile.length() > 0) {
                                    for (int i = 0; i < json_matched_profile.length(); i++) {

                                        GridBean drawerBean = new GridBean();
                                        JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                                        drawerBean.setStr_id(matchesJSONObj.getString("userid"));
                                        drawerBean.setStr_easy_marry_id(matchesJSONObj.getString("easymarryid"));
                                        drawerBean.setStr_username(matchesJSONObj.getString("name"));
                                        drawerBean.setStr_userdetails(matchesJSONObj.getString("age") + ","
                                                + matchesJSONObj.getString("height") +
                                                "\n" + matchesJSONObj.getString("religious") +
                                                "\n" + matchesJSONObj.getString("city")
                                                + "," + matchesJSONObj.getString("state")
                                                + "," + matchesJSONObj.getString("country")
                                                + "\n" + matchesJSONObj.getString("qualification")
                                                + "\n" + matchesJSONObj.getString("occupation"));
                                        drawerBean.setStr_membership(matchesJSONObj.getString("membership"));
                                        drawerBean.setStr_interest_key(matchesJSONObj.getString("interested"));
                                        drawerBean.setStr_shorlist_key(matchesJSONObj.getString("shortlisted"));
                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "upload/" + matchesJSONObj.getString("profileImage");
                                        Log.i("NN", strImageURL);
                                        drawerBean.setStr_userimage(strImageURL);
                                        beanArrayList.add(drawerBean);
                                    }

                                    Log.i("VAL", "ArrayLIst :" + beanArrayList.size());

                                    mCallback = (MyInterface) context;
                                    adapter = new GridAdapter(beanArrayList, getActivity(), mCallback, strType);
                                    grid.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    mySwipeRefresh1.setRefreshing(false);
                                    altDialog.dismiss();
                                    altDialog.cancel();
                                }


                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {

                                String filldetails = jsobj.getString("filldetails");
                                String str_completeness = jsobj.getString("completeness");
                                altDialog.dismiss();
                                altDialog.cancel();
                                if (filldetails.equalsIgnoreCase("0")) {

                                    /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);
                                    alertDialogBuilder.setCancelable(false);
                                    alertDialogBuilder.setTitle("Incomplete Partner Preference");
                                    alertDialogBuilder
                                            .setMessage("Your partner preference is incomplete, kindly fill it up to get your matches")
                                            .setCancelable(false)
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    startActivity(new Intent(context, EditProfile.class));
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();*/
                                    View itemView1;
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setCancelable(true);
                                    itemView1 = LayoutInflater.from(context)
                                            .inflate(R.layout.custom_incomplete_partner_det_popup, null);
                                    final AlertDialog altDialog = builder.create();
                                    altDialog.setView(itemView1);
                                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            startActivity(new Intent(context, EditProfile.class));
                                        }
                                    });
                                    altDialog.show();

                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        rl_matches_list.setVisibility(View.GONE);
                      altDialog.dismiss();
                       altDialog.cancel();
                        //Toast.makeText(getActivity(), "LL-->"+error.toString(), Toast.LENGTH_LONG).show();
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                //Toast.makeText(getActivity(), "res : " + res, Toast.LENGTH_LONG).show();
                            altDialog.dismiss();
                          altDialog.cancel();
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
                Log.e("NN:mat", Str_user_id);
                params.put("userid", Str_user_id);


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