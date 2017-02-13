package com.example.easy_marry.swibetabs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.easy_marry.Adapter.GridAdapterDiscover;
import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android2 on 18/6/16.
 */
public class DiscoverFragment extends android.support.v4.app.Fragment {

    JSONArray jsonArrayLocation, jsonArrayEducation, jsonArrayOccupation, jsonArrayFamilyValue;
    LinearLayout my_layout, my_layout2, my_layout3, my_layout4, llay_Need, llay_Need1,ll_edu,ll_edu_err,ll_prof,ll_prof_err,ll_fam_val,ll_fam_val_err;
    ImageLoader imgLoader;
    String str_user_id;

    public DiscoverFragment(String user_id) {
        // Required empty public constructor
        str_user_id = user_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.discover, container, false);
        imgLoader = new ImageLoader(getContext());
        my_layout = (LinearLayout) v.findViewById(R.id.add_disc_items);
        my_layout2 = (LinearLayout) v.findViewById(R.id.add_disc_items2);
        my_layout3 = (LinearLayout) v.findViewById(R.id.add_disc_items3);
        my_layout4 = (LinearLayout) v.findViewById(R.id.add_disc_items4);

        ll_edu = (LinearLayout) v.findViewById(R.id.ll_education);
        ll_edu_err = (LinearLayout) v.findViewById(R.id.ll_err_edu);

        ll_prof = (LinearLayout) v.findViewById(R.id.ll_prof);
        ll_prof_err = (LinearLayout) v.findViewById(R.id.ll_err_prof);

        ll_fam_val = (LinearLayout) v.findViewById(R.id.ll_fam_val);
        ll_fam_val_err = (LinearLayout) v.findViewById(R.id.ll_err_fam_val);

        llay_Need = (LinearLayout) v.findViewById(R.id.llayNeed);
        llay_Need1 = (LinearLayout) v.findViewById(R.id.llayNeed);

        discover("location");
        discover("education");
        discover("occupation");
        discover("familyvalue");
        return v;

    }

    private void createChild(final String str_name, final String str_userid, String str_gender, String str_profile_image, String str_place, String str_education, String str_occup, String str_fam_val, String str_type) {


        LayoutInflater inflater = LayoutInflater.from(getContext());

        final View view1 = inflater.inflate(R.layout.grid_discover_contents, null);

        ImageView img_user = (ImageView) view1.findViewById(R.id.img_user);
        TextView txt_nam_age = (TextView) view1.findViewById(R.id.txt_name_age);
        TextView txtplace = (TextView) view1.findViewById(R.id.txt_place);
        if (str_type.equalsIgnoreCase("location")) {
            imgLoader.DisplayImage(str_profile_image, img_user);
            txt_nam_age.setText(str_name);
            txtplace.setText(str_place);
            my_layout.addView(view1);
        } else if (str_type.equalsIgnoreCase("education")) {
            imgLoader.DisplayImage(str_profile_image, img_user);
            txt_nam_age.setText(str_name);
            txtplace.setText(str_education);
            my_layout2.addView(view1);
        } else if (str_type.equalsIgnoreCase("occupation")) {
            imgLoader.DisplayImage(str_profile_image, img_user);
            txt_nam_age.setText(str_name);
            txtplace.setText(str_occup);
            my_layout3.addView(view1);
        } else if (str_type.equalsIgnoreCase("familyvalue")) {
            imgLoader.DisplayImage(str_profile_image, img_user);
            txt_nam_age.setText(str_name);
            txtplace.setText(str_fam_val);
            my_layout4.addView(view1);
        }

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DailyRecomViewProfie.class);
                i.putExtra("str_from", "my matches");
                i.putExtra("user_id", str_userid);
                getContext().startActivity(i);
            }
        });

    }

    public void discover(final String str_type) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "discover.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        try {

                            JSONObject jsobj = new JSONObject(response);

                            if (str_type.equalsIgnoreCase("location")) {
                                if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray json_array = jsobj.getJSONArray("location");


                                    if (my_layout.getChildCount() > 0) {
                                        my_layout.removeAllViews();
                                    }
                                    if (json_array.length() > 0) {
                                        for (int i = 0; i < json_array.length(); i++) {


                                            JSONObject matchesJSONObj = json_array.getJSONObject(i);
                                            String str_name = matchesJSONObj.getString("name");
                                            String str_user_id = matchesJSONObj.getString("user_id");
                                            String str_gender = matchesJSONObj.getString("gender");
                                            String str_place = matchesJSONObj.getString("city_name");
                                            String strImageURL = GeneralData.LOCAL_IP_IMAGE + "images/" + matchesJSONObj.getString("profileImage");
                                            createChild(str_name, str_user_id, str_gender, strImageURL, str_place, "", "", "", "location");


                                        }
                                        llay_Need.setVisibility(View.VISIBLE);
                                        llay_Need1.setVisibility(View.GONE);

                                    } else {
                                        if (str_type.equalsIgnoreCase("location")) {

                                            llay_Need.setVisibility(View.GONE);
                                            llay_Need1.setVisibility(View.VISIBLE);

                                        }

                                    }
                                }
                            } else if (str_type.equalsIgnoreCase("education")) {
                                if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray json_array = jsobj.getJSONArray("education");


                                    if (my_layout2.getChildCount() > 0) {
                                        my_layout2.removeAllViews();
                                    }
                                    if (json_array.length() > 0) {


                                        for (int i = 0; i < json_array.length(); i++) {


                                            JSONObject matchesJSONObj = json_array.getJSONObject(i);
                                            String str_name = matchesJSONObj.getString("name");
                                            String str_user_id = matchesJSONObj.getString("user_id");
                                            String str_gender = matchesJSONObj.getString("gender");


                                            String str_education = matchesJSONObj.getString("education_value");

                                            String strImageURL = GeneralData.LOCAL_IP_IMAGE+"images/" + matchesJSONObj.getString("profileImage");
                                            createChild(str_name, str_user_id, str_gender, strImageURL, "", str_education, "", "", "education");


                                        }
                                        ll_edu_err.setVisibility(View.GONE);
                                        ll_edu.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_edu.setVisibility(View.GONE);
                                        ll_edu_err.setVisibility(View.VISIBLE);

                                    }

                                }
                            } else if (str_type.equalsIgnoreCase("occupation")) {
                                if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray json_array = jsobj.getJSONArray("occupation");


                                    if (my_layout3.getChildCount() > 0) {
                                        my_layout3.removeAllViews();
                                    }
                                    if (json_array.length() > 0) {
                                        for (int i = 0; i < json_array.length(); i++) {


                                            JSONObject matchesJSONObj = json_array.getJSONObject(i);
                                            String str_name = matchesJSONObj.getString("name");
                                            String str_user_id = matchesJSONObj.getString("user_id");
                                            String str_gender = matchesJSONObj.getString("gender");
                                            String str_occupation = matchesJSONObj.getString("occupation_value");
                                            String strImageURL = GeneralData.LOCAL_IP_IMAGE+"images/" + matchesJSONObj.getString("profileImage");
                                            createChild(str_name, str_user_id, str_gender, strImageURL, "", "", str_occupation, "", "occupation");


                                        }
                                        ll_prof.setVisibility(View.VISIBLE);
                                        ll_prof_err.setVisibility(View.GONE);
                                    } else {
                                        ll_prof.setVisibility(View.GONE);
                                        ll_prof_err.setVisibility(View.VISIBLE);

                                    }

                                }
                            } else if (str_type.equalsIgnoreCase("familyvalue")) {
                                if (jsobj.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray json_array = jsobj.getJSONArray("familyvalue");


                                    if (my_layout4.getChildCount() > 0) {
                                        my_layout4.removeAllViews();
                                    }
                                    if (json_array.length() > 0) {
                                        for (int i = 0; i < json_array.length(); i++) {


                                            JSONObject matchesJSONObj = json_array.getJSONObject(i);
                                            String str_name = matchesJSONObj.getString("name");
                                            String str_user_id = matchesJSONObj.getString("user_id");
                                            String str_gender = matchesJSONObj.getString("gender");
                                            String str_fam_val = matchesJSONObj.getString("family_value_name");

                                            String strImageURL = GeneralData.LOCAL_IP_IMAGE+"images/" + matchesJSONObj.getString("profileImage");
                                            createChild(str_name, str_user_id, str_gender, strImageURL, "", "", "", str_fam_val, "familyvalue");


                                        }
                                        ll_fam_val.setVisibility(View.VISIBLE);
                                        ll_fam_val_err.setVisibility(View.GONE);
                                    } else {
                                        ll_fam_val.setVisibility(View.GONE);
                                        ll_fam_val_err.setVisibility(View.VISIBLE);


                                    }

                                }
                            }
                            Log.i("HH", "strResp : " + response);


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