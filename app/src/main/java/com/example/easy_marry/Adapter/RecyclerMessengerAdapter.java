package com.example.easy_marry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Bean.RecyclerMessengerBean;
import com.example.easy_marry.ChatNow;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.Rounded_Imageview;
import com.example.easy_marry.imageCache.ImageLoader;

import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class RecyclerMessengerAdapter extends RecyclerView.Adapter<RecyclerMessengerAdapter.MyViewHolder> {

    private List<RecyclerMessengerBean> recyclerBeanList;
Context c;

    ImageLoader imgLoader;
    private static MyClickListener myClickListener;

    public RecyclerMessengerAdapter(List<RecyclerMessengerBean> myList, Context con) {
        this.recyclerBeanList = myList;

        this.c=con;
        imgLoader = new ImageLoader(c);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messenger_tab_contents, parent, false);
        final MyViewHolder holder= new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=holder.getAdapterPosition();
                Toast.makeText(c, ""+pos, Toast.LENGTH_SHORT).show();
                c.startActivity(new Intent(c, ChatNow.class));
                //myInterface.get_matches("hai");
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerMessengerBean recyclerBean = recyclerBeanList.get(position);


       imgLoader.DisplayImage(String.valueOf(recyclerBean.getStr_userimage()), holder.img_user);
       // holder.img_user.setBackgroundResource(recyclerBean.getStr_userimage());
        holder.txt_userid.setText(recyclerBean.getStr_id());
        holder.txt_username.setText(recyclerBean.getStr_username());

    }

    @Override
    public int getItemCount() {

        return recyclerBeanList.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Rounded_Imageview img_user;
        public TextView txt_userid;
        public TextView txt_username;

        public MyViewHolder(View view) {
            super(view);
            img_user=(Rounded_Imageview) view.findViewById(R.id.user_image);
            txt_userid=(TextView)view.findViewById(R.id.txt_nam_id);
            txt_username=(TextView)view.findViewById(R.id.txt_place);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
