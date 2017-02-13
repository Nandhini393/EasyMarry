package com.example.easy_marry.MembershipTabs;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.easy_marry.Adapter.RecyclerMemberAdapter;
import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Bean.RecyclerMemberBean;
import com.example.easy_marry.Interface.MembershipInterface;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.PaymentMode;
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
public class ThreeMonthsFragment extends Fragment {


//ImageView img_list, img_grid;

    private List<RecyclerMemberBean> memberBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerMemberAdapter mAdapter;
    Context cntx;
    JSONArray three_month_json;
String strType;
    MembershipInterface mCallback;
    public ThreeMonthsFragment(Context context, JSONArray jsonArrayThreeMonths, String strFrom) {
        // Required empty public constructor
        cntx = context;
        three_month_json=jsonArrayThreeMonths;
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
        View v = inflater.inflate(R.layout.three_months_mem, container, false);
        Log.e("3monthJSON",""+three_month_json);

        recyclerView = (RecyclerView) v.findViewById(R.id.member_recycler);
        threeMonthMembership(three_month_json);

        return v;
    }
    

   
    private void threeMonthMembership(JSONArray threeMonthJsonArray) {
        try {
            Log.i("HHN", "strResp : " + threeMonthJsonArray);
            ArrayList<RecyclerMemberBean> beanArrayList = new ArrayList<RecyclerMemberBean>();
           
            
                JSONArray json_three_month = threeMonthJsonArray;


                if (json_three_month.length() > 0) {
                    for (int i = 0; i < json_three_month.length(); i++) {

                        RecyclerMemberBean drawerBean = new RecyclerMemberBean();
                        JSONObject matchesJSONObj = json_three_month.getJSONObject(i);
                        drawerBean.setStr_plan_name(matchesJSONObj.getString("membership_plan"));
                        drawerBean.setStr_amount(matchesJSONObj.getString("membership_price"));
                        drawerBean.setStr_desc(matchesJSONObj.getString("description"));
                        drawerBean.setStr_per_month(matchesJSONObj.getString("monthlyrate"));
                        beanArrayList.add(drawerBean);
                    }
                }

                mCallback = (MembershipInterface) cntx;

                mAdapter = new RecyclerMemberAdapter(beanArrayList, getContext(), mCallback, strType);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}