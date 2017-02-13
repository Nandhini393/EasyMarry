package com.example.easy_marry.PaymentTabs;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easy_marry.Adapter.RecyclerMessengerAdapter;
import com.example.easy_marry.Bean.RecyclerMessengerBean;
import com.example.easy_marry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 21/6/16.
 */
public class NetBankingFragment extends android.support.v4.app.Fragment {
//ImageView img_list, img_grid;

    private List<RecyclerMessengerBean> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerMessengerAdapter mAdapter;
    public NetBankingFragment() {
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
        View v = inflater.inflate(R.layout.pay_net_bank,container, false);

        return v;

    }

}