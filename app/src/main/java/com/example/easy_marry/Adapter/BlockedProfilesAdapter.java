package com.example.easy_marry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.R;
import com.example.easy_marry.imageCache.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class BlockedProfilesAdapter extends RecyclerView.Adapter<BlockedProfilesAdapter.MyViewHolder> {

    private List<RecyclerBean> recyclerBeanList;
Context c;
    View itemView1;
    MyInterface myInterface;
  ImageLoader imgLoader;
    private static MyClickListener myClickListener;
    RecyclerView recycleV;
    String strFrom;
    public BlockedProfilesAdapter(List<RecyclerBean> myList, Context con, MyInterface interfac,String str_from,RecyclerView recyclerView) {
        this.recyclerBeanList = myList;
this.myInterface=interfac;
      imgLoader = new ImageLoader(c);
        this.c=con;
        this.strFrom = str_from;
        this.recycleV = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blocked_profiles_content, parent, false);
        final MyViewHolder holder= new MyViewHolder(itemView);
        holder.ll_block_profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                String str_partner_id=recyclerBean.getStr_id();
                String str_resp=myInterface.get_matches(str_partner_id,"", strFrom, "unblock_profile", "",recycleV);
                if(str_resp.equalsIgnoreCase("success")){
                    removeAt(holder.getAdapterPosition());
                }
            }
        });
        return holder;
    }
    public void removeAt(int position) {
        recyclerBeanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, recyclerBeanList.size());
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RecyclerBean recyclerBean = recyclerBeanList.get(position);
      //holder.img_user.setBackgroundResource(recyclerBean.getStr_userimage());
        holder.txt_userid.setText(recyclerBean.getStr_easy_marry_id());
        holder.txt_username.setText(recyclerBean.getStr_username());
        holder.txt_details.setText(recyclerBean.getStr_userdetails());


       /* Picasso.with(c)
                .load(recyclerBean.getStr_userimage())
                .placeholder(R.drawable.default_use)
                .into(holder.img_user);*/

        imgLoader.DisplayImage(recyclerBean.getStr_userimage(), holder.img_user);



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
        public TextView txt_userid;
        public TextView txt_username;
        public TextView txt_details;
        public LinearLayout ll_block_profiles;
       // public LinearLayout btn_interest,ll_chat_now;
        public MyViewHolder(View view) {
            super(view);
           img_user=(ImageView)view.findViewById(R.id.img_user);
            txt_userid=(TextView)view.findViewById(R.id.txt_user_id);
            txt_username=(TextView)view.findViewById(R.id.txt_username);
            txt_details=(TextView)view.findViewById(R.id.txt_details);
            ll_block_profiles=(LinearLayout)view.findViewById(R.id.ll_unblock_profile);

           /* btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
            ll_chat_now=(LinearLayout)view.findViewById(R.id.ll_chat_now);*/
            ll_block_profiles.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           // myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
