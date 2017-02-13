package com.example.easy_marry.swibetabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
public class NearByFragment extends Fragment {
    //ImageView img_list, img_grid;
    Context context;


    private List<RecyclerBean> movieList = new ArrayList<>();
    private List<GridBean> gridBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    GridView grid;
    private RecyclerAdapter mAdapter;
    String str_user_id;
    MyInterface mCallback;

    String strType;
    public NearByFragment(String user_id, Context ctx, String strFrom) {
        // Required empty public constructor
        str_user_id = user_id;
        this.context = ctx;
        strType=strFrom;
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

        recyclerView = (RecyclerView) v.findViewById(R.id.tab_one_list);
        matchesListCall();

        return v;

    }


    public void matchesListCall() {
        //matches rest call

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "nearby.php",
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

                                JSONArray json_matched_profile = jsobj.getJSONArray("Response");


                                if (json_matched_profile.length() > 0) {
                                    for (int i = 0; i < json_matched_profile.length(); i++) {

                                        RecyclerBean drawerBean = new RecyclerBean();
                                        JSONObject matchesJSONObj = json_matched_profile.getJSONObject(i);
                                        drawerBean.setStr_id(matchesJSONObj.getString("userid"));
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
                                        String strImageURL = GeneralData.LOCAL_IP_IMAGE + "images/" + matchesJSONObj.getString("profileImage");
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
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
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

                params.put("id", str_user_id);


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