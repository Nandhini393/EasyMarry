package com.example.easy_marry.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.ChatNow;
import com.example.easy_marry.DailyRecomViewProfie;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MembershipTabs.MembershipTabs;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by android2 on 16/6/16.
 */
public class GridAdapter extends BaseAdapter {
    Context mContext;
    private ArrayList<GridBean> gridBeen;
    ImageLoader imgLoader;
    MyInterface myInterface;
    String strFrom, str_membership,str_user_id,str_name;
    GeneralData gD;
    private static LayoutInflater inflater=null;
    public GridAdapter(ArrayList<GridBean> myList, Context con, MyInterface interfac, String strType) {
        this.gridBeen = myList;
        this.mContext = con;
        this.myInterface = interfac;
        imgLoader = new ImageLoader(mContext);
        this.strFrom = strType;
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gridBeen.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public class Holder
    {
        public TextView txt_id, txt_name, txt_interest_sent;

        public ImageView imageView, img_membership, img_shortlist, img_chat_grid;

        LinearLayout ll_sent_interest;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_tabs_content, null);
        final GridBean gridBean = gridBeen.get(position);


        holder.txt_id = (TextView) rowView.findViewById(R.id.txt_userid);
        holder.txt_name = (TextView) rowView.findViewById(R.id.txt_username);
        holder.imageView = (ImageView) rowView.findViewById(R.id.img_user);
        holder.img_membership = (ImageView) rowView.findViewById(R.id.img_membership);

        holder.img_shortlist = (ImageView) rowView.findViewById(R.id.img_shortlist);
        holder.txt_interest_sent = (TextView) rowView.findViewById(R.id.txt_interest_sent);
        holder.ll_sent_interest = (LinearLayout) rowView.findViewById(R.id.send_interest);
        holder.img_chat_grid = (ImageView) rowView.findViewById(R.id.img_chat_grid);

        gD = new GeneralData(mContext);


        str_membership = gD.prefs.getString("memplan", null);
        str_user_id = gD.prefs.getString("userid", null);
        str_name = gD.prefs.getString("name", null);
        holder.txt_id.setText(gridBean.getStr_easy_marry_id());
        holder.txt_name.setText(gridBean.getStr_username());
        Log.e("NN:adap_image", "grid->"+gridBean.getStr_userimage());
      //  imgLoader.DisplayImage(gridBean.getStr_userimage(), holder.imageView);

        Picasso.with(mContext)
                .load(gridBean.getStr_userimage())
                .placeholder(R.drawable.default_use)
                .into(holder.imageView);


        String str_get_membership_type = gridBean.getStr_membership();
        if (str_get_membership_type.equalsIgnoreCase("Basic")) {
            holder.img_membership.setVisibility(View.GONE);
        } else if (str_get_membership_type.equalsIgnoreCase("Premium")) {
            holder.img_membership.setVisibility(View.VISIBLE);
        }
        if (strFrom.equalsIgnoreCase("MATCHES")) {
            if (gridBean.getStr_interest_key().equalsIgnoreCase("0")) {
                holder.ll_sent_interest.setBackgroundColor(Color.parseColor("#fb7b09"));
                holder.txt_interest_sent.setText("Send Interest");
            } else {
                holder.ll_sent_interest.setBackgroundColor(Color.parseColor("#639639"));
                holder.ll_sent_interest.setEnabled(false);
                holder.txt_interest_sent.setText("Interest sent");
            }

            if (gridBean.getStr_shorlist_key().equalsIgnoreCase("0")) {
                holder.img_shortlist.setBackgroundResource(R.drawable.rate_orange);

            } else {
                holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);
                // img_shortlist.setEnabled(false);
                Toast.makeText(mContext, "Already shortlisted!!", Toast.LENGTH_SHORT).show();
            }
        }
        holder.ll_sent_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_partner_id = gridBean.getStr_id();
                final String str_partner_name = gridBean.getStr_username();
                Log.e("NN", str_partner_id);
                Log.e("NN", str_partner_name);

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);

                View itemView1 = LayoutInflater.from(mContext)
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
                            Toast.makeText(mContext, "you can't edit this text as you are basic member", Toast.LENGTH_SHORT).show();
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
                        String str_resp=myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "send_interest",ed_interest.getText().toString().trim(),null);

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
               /* myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "send_interest","", null);
                holder.ll_sent_interest.setBackgroundColor(Color.parseColor("#639639"));
                holder.txt_interest_sent.setText("Interest sent");*/

            }
        });
        holder.img_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_partner_id = gridBean.getStr_id();
                String str_partner_name = gridBean.getStr_username();
                String strResp = myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "shortlist", "",null);
                holder.img_shortlist.setBackgroundResource(R.drawable.rate_grey);

                Log.i("ND", "strResp : suspect.........1");


                removeAt(position);

                gridBeen.remove(position);
                notifyDataSetChanged();


                Log.i("ND", "strResp : suspect........ 2.");



                   /* if (strResp.equalsIgnoreCase("success")) {

                        Log.i("ND", "strResp : suspect........." );

                        parent.removeViewAt(position);
                        //gridBeen.remove(position);
                        notifyDataSetChanged();

                    }*/
            }
        });
        holder.img_chat_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_membership.equalsIgnoreCase("free")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setCancelable(true);

                    View itemView1 = LayoutInflater.from(mContext)
                            .inflate(R.layout.upgrade_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);

                    LinearLayout ll_upgrade_now = (LinearLayout) itemView1.findViewById(R.id.ll_upgrade_now);
                    TextView txt_uname = (TextView) itemView1.findViewById(R.id.txt_user_name);
                    ImageView img_user = (ImageView) itemView1.findViewById(R.id.user_image_upload);
                    TextView txt_close=(TextView)itemView1.findViewById(R.id.txt_close);
                    txt_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            altDialog.dismiss();
                        }
                    });
                    imgLoader.DisplayImage(gridBean.getStr_userimage(), img_user);
                    txt_uname.setText(gridBean.getStr_username());
                    ll_upgrade_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, MembershipTabs.class));
                        }
                    });
                    altDialog.show();
                } else {
                    mContext.startActivity(new Intent(mContext, ChatNow.class));
                }
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, position, Toast.LENGTH_SHORT).show();
                   /* Intent i = new Intent(mContext, DailyRecomViewProfie.class);
                    i.putExtra("str_from", "my matches");
                    i.putExtra("user_id", gridBean.getStr_id());
                    mContext.startActivity(i);*/
                String str_partner_id = gridBean.getStr_id();
                String str_partner_name = gridBean.getStr_username();
                myInterface.get_matches(str_partner_id, str_partner_name, strFrom, "view_profile","", null);
            }
        });

        return rowView;
    }
    public void removeAt(int position) {
        gridBeen.remove(position);
        this.notifyDataSetChanged();

    }
}