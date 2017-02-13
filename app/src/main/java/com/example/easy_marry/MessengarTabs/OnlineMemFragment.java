package com.example.easy_marry.MessengarTabs;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.easy_marry.Adapter.RecyclerAdapter;
import com.example.easy_marry.Adapter.RecyclerMessengerAdapter;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Bean.RecyclerMemberBean;
import com.example.easy_marry.Bean.RecyclerMessengerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 21/6/16.
 */
public class OnlineMemFragment extends android.support.v4.app.Fragment {
//ImageView img_list, img_grid;

    private List<RecyclerMessengerBean> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerMessengerAdapter mAdapter;
    public OnlineMemFragment() {
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
        View v = inflater.inflate(R.layout.online_member,container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.tab_one_list);

        mAdapter = new RecyclerMessengerAdapter(movieList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();
        return v;

    }
    private void prepareMovieData() {
        RecyclerMessengerBean movie = new RecyclerMessengerBean(R.drawable.girl, "Roshini"+" "+"("+"ME12345"+")","chennai \n India");
        movieList.add(movie);

        movie = new RecyclerMessengerBean(R.drawable.girl2, "Apporva"+" "+"("+"ME12345"+")","chennai \n India");;
        movieList.add(movie);

        movie = new RecyclerMessengerBean(R.drawable.girl3, "Sanjana"+" "+"("+"ME12345"+")","chennai \n India");;
        movieList.add(movie);
        movie = new RecyclerMessengerBean(R.drawable.girl4, "Emma"+" "+"("+"ME12345"+")","chennai \n India");;
        movieList.add(movie);


        mAdapter.notifyDataSetChanged();
    }
}