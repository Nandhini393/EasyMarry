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

import com.example.easy_marry.Bean.RecyclerMailBean;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MailInboxTabs.MailInboxTabs;
import com.example.easy_marry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class RecyclerMailAdapter extends RecyclerView.Adapter<RecyclerMailAdapter.MyViewHolder> {

    private List<RecyclerMailBean> recyclerBeanList;
    Context ctx;
    MyInterface myInterface;
    View itemView1;
    private static MyClickListener myClickListener;
    String strFROM;


    public RecyclerMailAdapter(List<RecyclerMailBean> myList, Context con) {
        recyclerBeanList = new ArrayList<RecyclerMailBean>();
        this.recyclerBeanList = myList;
        this.ctx = con;
        strFROM = MailInboxTabs.strFrom;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        Log.i("NDD", "strFROM : " + strFROM);

        final MyViewHolder holder;

        if (strFROM.equalsIgnoreCase("inbox")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_list_cont, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("new")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_list_cont, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("sent")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_sent, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("readnotreplied")) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_read_not_reply, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("accepted")) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_accepted, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("replied")) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_replied, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("notinterest")) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_not_interest, parent, false);
            holder = new MyViewHolder(itemView);
        } else if (strFROM.equalsIgnoreCase("trash")) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_trash, parent, false);
            holder = new MyViewHolder(itemView);
            holder.img_move_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(ctx)
                            .inflate(R.layout.move_to_popup, null);
                    final TextView txt_move_to_new=(TextView)itemView1.findViewById(R.id.move_to_new);
                    final TextView txt_move_to_read=(TextView)itemView1.findViewById(R.id.move_to_read);
                    final TextView txt_move_to_accept=(TextView)itemView1.findViewById(R.id.move_to_accept);
                    final TextView txt_move_to_reply=(TextView)itemView1.findViewById(R.id.move_to_reply);
                    final TextView txt_move_to_not_interest=(TextView)itemView1.findViewById(R.id.move_to_not_interest);
                    final TextView txt_move_to_trash=(TextView)itemView1.findViewById(R.id.move_to_trash);
                    txt_move_to_new.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txt_move_to_new.setBackgroundColor(Color.parseColor("#fb7b09"));
                            txt_move_to_new.setTextColor(Color.parseColor("#ffffff"));
                            txt_move_to_read.setBackgroundColor(Color.parseColor("#fb7b09"));
                            txt_move_to_read.setTextColor(Color.parseColor("#ffffff"));
                        }
                    });
                    txt_move_to_read.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txt_move_to_read.setBackgroundColor(Color.parseColor("#fb7b09"));
                            txt_move_to_read.setTextColor(Color.parseColor("#ffffff"));
                            txt_move_to_new.setBackgroundColor(Color.parseColor("#fb7b09"));
                            txt_move_to_new.setTextColor(Color.parseColor("#ffffff"));
                        }
                    });
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.show();
                }
            });
        }
        else {
            Log.i("NDD", "i am came to else....!!!");
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_list_cont, parent, false);
            holder = new MyViewHolder(itemView);

        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Toast.makeText(ctx, "" + pos, Toast.LENGTH_SHORT).show();
                // ctx.startActivity(new Intent(ctx, DailyRecomViewProfie.class));
                //myInterface.get_matches("hai");
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerMailBean recyclerBean = recyclerBeanList.get(position);

        holder.img_user.setBackgroundResource(recyclerBean.getStr_userimage());
        holder.txt_userid.setText(recyclerBean.getStr_id());
        holder.txt_username.setText(recyclerBean.getStr_username());
        holder.txt_details.setText(recyclerBean.getStr_userdetails());
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
        public LinearLayout btn_interest;
        ImageView img_move_to;

        public MyViewHolder(View view) {
            super(view);
            if (strFROM.equalsIgnoreCase("inbox")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("sent")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("new")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("readnotreplied")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("accepted")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("replied")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("notinterest")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("trash")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
               // img_move_to = (ImageView) view.findViewById(R.id.img_move_to);
                // btn_interest=(LinearLayout)view.findViewById(R.id.send_interest);
                view.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }


    }
}
