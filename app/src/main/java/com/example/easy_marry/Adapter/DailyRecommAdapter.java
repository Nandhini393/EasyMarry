package com.example.easy_marry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.ChatNow;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class DailyRecommAdapter extends RecyclerView.Adapter<DailyRecommAdapter.MyViewHolder> {

    private List<RecyclerBean> recyclerBeanList;
    Context c;
    View itemView1;
    MyInterface myInterface;
    ImageLoader imgLoader;
    //RecyclerBean recyclerBean;

    private static MyClickListener myClickListener;
    String strFrom;
    RecyclerView recycleV;


    public DailyRecommAdapter(ArrayList<RecyclerBean> myList, Context con, MyInterface interfac, String strType, RecyclerView recyclerView) {
        this.recyclerBeanList = myList;
        this.myInterface = interfac;
        this.c = con;
        imgLoader = new ImageLoader(c);
        this.strFrom = strType;
        this.recycleV = recyclerView;
    }


    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_recomm_content_new, parent, false);
        final MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    public void removeAt(int position) {
        recyclerBeanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, recyclerBeanList.size());
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        RecyclerBean recyclerBean = recyclerBeanList.get(pos);

        imgLoader.DisplayImage(recyclerBean.getStr_userimage(), holder.img_user);

       /* Picasso.with(c)
                .load(recyclerBean.getStr_userimage())
                .placeholder(R.drawable.default_use)
                .into(holder.img_user);
*/

        holder.txt_easy_marry_id.setText(recyclerBean.getStr_easy_marry_id());
        holder.txt_name.setText(recyclerBean.getStr_username());
        holder.txt_addr.setText(recyclerBean.getStr_userdetails());

    }

    @Override
    public int getItemCount() {

        return recyclerBeanList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img_user;
        public TextView txt_name,txt_easy_marry_id,txt_addr;
        public LinearLayout ll_skip,ll_int_yes;


        public MyViewHolder(View view) {
            super(view);
            img_user = (ImageView) view.findViewById(R.id.img_list_user);
            txt_name = (TextView) view.findViewById(R.id.txt_list_name);
            txt_easy_marry_id = (TextView) view.findViewById(R.id.txt_list_emid);
            txt_addr = (TextView) view.findViewById(R.id.txt_list_addr);
            ll_skip = (LinearLayout) view.findViewById(R.id.ll_skip);
            ll_int_yes = (LinearLayout) view.findViewById(R.id.ll_int_yes);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
