package com.example.easy_marry.MembershipTabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easy_marry.Adapter.RecyclerMemberAdapter;
import com.example.easy_marry.Bean.RecyclerMemberBean;
import com.example.easy_marry.Interface.MembershipInterface;
import com.example.easy_marry.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 14/6/16.
 */
public class TillYouMarryFragment extends Fragment {


//ImageView img_list, img_grid;

    private List<RecyclerMemberBean> memberBeen = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerMemberAdapter mAdapter;
    Context cntx;
    JSONArray till_u_marry_json;
    String strType;
    MembershipInterface mCallback;
    public TillYouMarryFragment(Context context, JSONArray jsonArrayTillUMarry, String strFrom) {
        // Required empty public constructor
        cntx = context;
        till_u_marry_json=jsonArrayTillUMarry;
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

        recyclerView = (RecyclerView) v.findViewById(R.id.member_recycler);
        tillUMarryMembership(till_u_marry_json);

        return v;
    }
    private void tillUMarryMembership(JSONArray threeMonthJsonArray) {
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