package com.example.easy_marry.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.ChatNow;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;
import com.example.easy_marry.swibetabs.MatchesFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<RecyclerBean> recyclerBeanList;
    Context c;
    View itemView1;
    MyInterface myInterface;
    ImageLoader imgLoader;
    //RecyclerBean recyclerBean;
    GeneralData gD;
    private static MyClickListener myClickListener;
    String strFrom, str_membership,str_user_id,str_name;
    RecyclerView recycleV;


    public RecyclerAdapter(ArrayList<RecyclerBean> myList, Context con, MyInterface interfac, String strType, RecyclerView recyclerView) {
        this.recyclerBeanList = myList;
        this.myInterface = interfac;
        this.c = con;
        imgLoader = new ImageLoader(c);
        this.strFrom = strType;
        this.recycleV = recyclerView;
    }


    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tabs_content, parent, false);
        final MyViewHolder holder = new MyViewHolder(itemView);
        gD = new GeneralData(c);
        str_membership = gD.prefs.getString("memplan", null);
        str_user_id = gD.prefs.getString("userid", null);
        str_name = gD.prefs.getString("name", null);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                //Toast.makeText(c, "" + pos, Toast.LENGTH_SHORT).show();
                RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                Log.e("NN", recyclerBean.getStr_id());
                //myInterface.get_matches(recyclerBean.getStr_id());
                String str_id = recyclerBean.getStr_id();
                String str_partner_name = recyclerBean.getStr_username();
                Log.e("NN_nn", str_id);
                SharedPreferences.Editor prefEdit = gD.prefs.edit();
                prefEdit.putString("sent_int_key1", String.valueOf(pos));
                Log.e("NN", "pos->"+pos);
                prefEdit.commit();
                myInterface.get_matches(str_id, str_partner_name, strFrom, "view_profile", "",recycleV);
                //notifyItemChanged(pos);



           /*     Intent i = new Intent(c, DailyRecomViewProfie.class);
                i.putExtra("str_from", "my matches");
                i.putExtra("user_id", str_id);
                c.startActivity(i);*/


                //myInterface.get_matches("hai");

            }
        });
        holder.ll_chat_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str_membership.equalsIgnoreCase("free")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(true);

                    itemView1 = LayoutInflater.from(c)
                            .inflate(R.layout.upgrade_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    TextView txt_uname = (TextView) itemView1.findViewById(R.id.txt_user_name);
                    ImageView img_user = (ImageView) itemView1.findViewById(R.id.user_image_upload);
                    LinearLayout ll_upgrade_now = (LinearLayout) itemView1.findViewById(R.id.ll_upgrade_now);
                    TextView txt_close=(TextView)itemView1.findViewById(R.id.txt_close);
                    txt_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            altDialog.dismiss();
                        }
                    });
                    imgLoader.DisplayImage(recyclerBean.getStr_userimage(), img_user);
                    txt_uname.setText(recyclerBean.getStr_username());

                    ll_upgrade_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            c.startActivity(new Intent(c, MembershipTabs.class));
                        }
                    });
                    altDialog.show();
                } else {
                    c.startActivity(new Intent(c, ChatNow.class));
                }


            }
        });
/*        holder.ll_premium_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralData.str_membership.equalsIgnoreCase("basic")) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(true);

                    itemView1 = LayoutInflater.from(c)
                            .inflate(R.layout.upgrade_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    TextView txt_uname=(TextView)itemView.findViewById(R.id.txt_user_name);
                    ImageView img_user=(ImageView)itemView1.findViewById(R.id.user_image_upload);
                    LinearLayout ll_upgrade_now = (LinearLayout) itemView1.findViewById(R.id.ll_upgrade_now);


                    imgLoader.DisplayImage(recyclerBean.getStr_userimage(),img_user);
                    txt_uname.setText(recyclerBean.getStr_username());

                    ll_upgrade_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            c.startActivity(new Intent(c, MembershipTabs.class));
                        }
                    });
                    altDialog.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setCancelable(true);
                    itemView1 = LayoutInflater.from(c)
                            .inflate(R.layout.premium_member_popup, null);

                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    TextView txt_emid=(TextView)itemView1.findViewById(R.id.txt_matri_id);
                    TextView txt_name=(TextView)itemView1.findViewById(R.id.txt_name);
                    TextView txt_phone=(TextView)itemView1.findViewById(R.id.txt_phone);
                    TextView txt_email=(TextView)itemView1.findViewById(R.id.txt_email);
                    TextView txt_addr=(TextView)itemView1.findViewById(R.id.txt_addr);
                    ImageView img_user=(ImageView)itemView1.findViewById(R.id.img_user);
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    imgLoader.DisplayImage(recyclerBean.getStr_userimage(),img_user);
                    txt_emid.setText(recyclerBean.getStr_easy_marry_id());
                    txt_name.setText(recyclerBean.getStr_username());
                    txt_phone.setText(recyclerBean.getStr_phone());
                    txt_email.setText(recyclerBean.getStr_email());
                    txt_addr.setText(recyclerBean.getStr_addr());
                    altDialog.show();
                }

            }
        });*/
        holder.ll_sent_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                final String str_partner_id = recyclerBean.getStr_id();
                final String str_partner_name = recyclerBean.getStr_username();
                Log.e("NN", str_partner_id);
                Log.e("NN", str_partner_name);
//myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "send_interest", recycleV);


                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setCancelable(true);

                View itemView1 = LayoutInflater.from(c)
                        .inflate(R.layout.custom_send_int_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final EditText ed_interest = (EditText) itemView1.findViewById(R.id.ed_interest);
                Button btn_sent_interest = (Button) itemView1.findViewById(R.id.btn_sent);

                if (str_membership.equalsIgnoreCase("free")) {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);
                    String interest_msg = "Hi " + str_partner_name + ",I'm " + str_name + " .Please accept my request";
                    ed_interest.setText(interest_msg);
                    ed_interest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(c, "you can't edit this text as you are basic member", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ed_interest.setEnabled(true);
                    ed_interest.setFocusable(true);
                    /*interest_msg=ed_interest.getText().toString().trim();
                    ed_interest.setText(interest_msg);*/

                }

                btn_sent_interest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str_resp=myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "send_interest",ed_interest.getText().toString().trim(), recycleV);

                        Log.e("NN:adap(newM)",str_resp);
                        if(str_resp.equalsIgnoreCase(str_partner_id)){
                            holder.txt_interest_sent.setText("Interest sent");
                            holder.ll_sent_interest.setBackgroundColor(Color.parseColor("#639639"));
                            holder.ll_sent_interest.setEnabled(false);
                        }

                        // strResp1=str_partner_id;
                       // strResp1= sendInterestCall(str_user_id, str_partner_id, ed_interest.getText().toString().trim(), "1");
                        altDialog.dismiss();
                    }
                });
                altDialog.show();





            }
        });

        holder.ll_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = holder.getAdapterPosition();
                RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                //  recyclerBean = recyclerBeanList.get(pos);
                String str_partner_id = recyclerBean.getStr_id();
                String str_partner_name = recyclerBean.getStr_username();
                String strResp = myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "shortlist", "",recycleV);

               /* holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);
                holder.txt_shortlist.setText("Shortlisted");
                holder.txt_shortlist.setTextColor(Color.parseColor("#808080"));*/
                Log.e("NM:adapt",strResp);
                if (strResp.equalsIgnoreCase("success")) {
                    removeAt(holder.getAdapterPosition());
                }

               /* if (strResp.equalsIgnoreCase("backcolor")) {
                    if (holder.txt_shortlist.getText().toString().equalsIgnoreCase("Shortlist")) {
                        holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);
                        holder.txt_shortlist.setText("Shortlisted");
                       holder.txt_shortlist.setTextColor(Color.parseColor("#808080"));
                    } else {
                        holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);
                        holder.txt_shortlist.setText("Shortlist");
                        holder.txt_shortlist.setTextColor(Color.parseColor("#000000"));
                    }
                }*/
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

        int pos = holder.getAdapterPosition();
        RecyclerBean recyclerBean = recyclerBeanList.get(pos);
        Log.e("NN:adap_image", "recycler->"+recyclerBean.getStr_userimage());
       // imgLoader.DisplayImage(recyclerBean.getStr_userimage(), holder.img_user);

      Picasso.with(c)
                .load(recyclerBean.getStr_userimage())
                .placeholder(R.drawable.default_use)
                .into(holder.img_user);


        holder.txt_easy_marry_id.setText(recyclerBean.getStr_easy_marry_id());
        holder.txt_username.setText(recyclerBean.getStr_username());
        holder.txt_details.setText(recyclerBean.getStr_userdetails());
        String str_get_membership_type = recyclerBean.getStr_membership();
        if (str_get_membership_type.equalsIgnoreCase("Basic")) {
            holder.ll_premium_mem.setVisibility(View.GONE);
        } else if (str_get_membership_type.equalsIgnoreCase("Premium")) {
            holder.ll_premium_mem.setVisibility(View.VISIBLE);
        }
        Log.e("NN:adap", strFrom);

        if (recyclerBean.getStr_interest_key().equalsIgnoreCase("0")) {
            holder.ll_sent_interest.setBackgroundColor(Color.parseColor("#fb7b09"));
            holder.txt_interest_sent.setText("Send Interest");
            holder.ll_sent_interest.setEnabled(true);
        } else  if (recyclerBean.getStr_interest_key().equalsIgnoreCase("1")) {
            // holder.txt_int_msg.setVisibility(View.VISIBLE);
            holder.ll_sent_interest.setBackgroundColor(Color.parseColor("#639639"));
            holder.ll_sent_interest.setEnabled(false);
            holder.txt_interest_sent.setText("Interest Sent");

        }
        if (strFrom.equalsIgnoreCase("shortlist")) {
            Log.e("NN:s_key", recyclerBean.getStr_shorlist_key());
         if (recyclerBean.getStr_shorlist_key().equalsIgnoreCase("1")) {
               /*holder.img_shortlist.setBackgroundResource(R.drawable.rate_orange);
                holder.txt_shortlist.setText("Shortlist");*/
                holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);
                holder.txt_shortlist.setText("Unshortlist");
                holder.txt_shortlist.setTextColor(Color.parseColor("#808080"));
            } /*else {
                holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);
                holder.txt_shortlist.setText("Shortlisted");
               *//* holder.ll_shortlist.setEnabled(false);
                Toast.makeText(c, "Already shortlisted!!", Toast.LENGTH_SHORT).show();*//*
                holder.txt_shortlist.setTextColor(Color.parseColor("#808080"));
            }*/



/*
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String animal = mDataSet.get(position);
                Toast.makeText(mContext,animal,Toast.LENGTH_SHORT).show();
            }
        });*/
        }

    }

    @Override
    public int getItemCount() {

        return recyclerBeanList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img_user, img_shortlist;
        public TextView txt_easy_marry_id;
        public TextView txt_username;
        public TextView txt_details;
        public TextView txt_interest_sent, txt_shortlist;
        public RelativeLayout ll_sent_interest;
        public LinearLayout ll_chat_now, ll_premium_mem, ll_shortlist;

        public MyViewHolder(View view) {
            super(view);
            img_user = (ImageView) view.findViewById(R.id.img_user);
            txt_easy_marry_id = (TextView) view.findViewById(R.id.txt_user_id);
            txt_username = (TextView) view.findViewById(R.id.txt_username);
            txt_details = (TextView) view.findViewById(R.id.txt_details);

            ll_chat_now = (LinearLayout) view.findViewById(R.id.ll_chat_now);
            txt_interest_sent = (TextView) view.findViewById(R.id.txt_interest_sent);
            //  txt_int_msg = (TextView) view.findViewById(R.id.txt_int_msg);
            ll_sent_interest = (RelativeLayout) view.findViewById(R.id.send_interest);
            ll_premium_mem = (LinearLayout) view.findViewById(R.id.ll_premium_mem);
            ll_shortlist = (LinearLayout) view.findViewById(R.id.ll_shortlist);
            img_shortlist = (ImageView) view.findViewById(R.id.img_shortlist);
            txt_shortlist = (TextView) view.findViewById(R.id.txt_shortlist);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
