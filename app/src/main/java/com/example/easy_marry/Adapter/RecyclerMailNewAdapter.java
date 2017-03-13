package com.example.easy_marry.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.Bean.RecyclerMailBean;
import com.example.easy_marry.Interface.MailBoxInterface;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.MailInboxTabs.MailInboxTabs;
import com.example.easy_marry.R;
import com.example.easy_marry.imageCache.ImageLoader;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class RecyclerMailNewAdapter extends RecyclerView.Adapter<RecyclerMailNewAdapter.MyViewHolder> {

    private List<RecyclerBean> recyclerBeanList;
    Context ctx;
    MailBoxInterface myInterface;
    String strFROM;
    ImageLoader imgLoader;
    RecyclerView recycleV;
    String str_msg, str_mail_type;

    public RecyclerMailNewAdapter(List<RecyclerBean> myList, Context con, MailBoxInterface interfac, String str_from, RecyclerView recyclerView) {
        this.recyclerBeanList = myList;
        this.myInterface = interfac;
        this.ctx = con;
        imgLoader = new ImageLoader(ctx);
        strFROM = str_from;
        this.recycleV = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = null;
        final MyViewHolder holder;
        Log.i("NDD", "strFROM : " + strFROM);
        if (strFROM.equalsIgnoreCase("inbox")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_list_new_cont, parent, false);
            holder = new MyViewHolder(itemView);


            holder.btn_view_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setCancelable(true);

                    View itemView1 = LayoutInflater.from(ctx)
                            .inflate(R.layout.custom_view_msg_popup, null);
                    final AlertDialog altDialog = builder.create();
                    altDialog.setView(itemView1);
                    altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    int pos = holder.getAdapterPosition();
                    //  Toast.makeText(ctx, "" + viewType, Toast.LENGTH_SHORT).show();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    //str_mail_type=recyclerBean.getStr_mail_type();
                    str_msg = recyclerBean.getStr_msg();
                    TextView txt_view_msg = (TextView) itemView1.findViewById(R.id.txt_view_msg);
                    TextView txt_view_int = (TextView) itemView1.findViewById(R.id.txt_int);
                    final Button btn_accept = (Button) itemView1.findViewById(R.id.btn_accept);
                    final Button btn_reject = (Button) itemView1.findViewById(R.id.btn_reject);
                    txt_view_msg.setText(str_msg);
                    final String str_partner_id = recyclerBean.getStr_id();
                    Log.e("NN:mail", str_partner_id);

                    final String str_reply_status=recyclerBean.getStr_reply_status();
                    Log.e("NN:reply", str_reply_status);

                    if(str_reply_status.equalsIgnoreCase("yes")){
                        btn_reject.setVisibility(View.GONE);
                        btn_accept.setText("Already Accepted");
                        txt_view_int.setVisibility(View.GONE);
                        btn_accept.setBackgroundColor(Color.parseColor("#80639639"));
                        btn_accept.setTextColor(Color.parseColor("#fffafa"));
                    }
                    else if(str_reply_status.equalsIgnoreCase("no")){
                        btn_accept.setVisibility(View.GONE);
                        btn_reject.setText("Already Rejected");
                        txt_view_int.setVisibility(View.GONE);
                        btn_reject.setBackgroundColor(Color.parseColor("#80fb7b09"));
                        btn_reject.setTextColor(Color.parseColor("#fffafa"));
                    }

                    btn_accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(str_reply_status.equalsIgnoreCase("yes")){

                                    Toast.makeText(ctx, "Already interest sent", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String strResp = myInterface.get_matches(str_partner_id, "", "", "interest_yes", recycleV);
                                }

                                altDialog.dismiss();
                            }
                        });


                    btn_reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(str_reply_status.equalsIgnoreCase("no")){

                                Toast.makeText(ctx, "Already interest sent", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String strResp = myInterface.get_matches(str_partner_id, "", "", "interest_no", recycleV);
                            }
                            altDialog.dismiss();
                        }
                    });
                    altDialog.show();


                    holder.txt_read_inbox.setText("Read");
                    holder.txt_read_inbox.setTextColor(Color.parseColor("#fb7b09"));
                    holder.img_mark_as_read.setBackgroundResource(R.drawable.read_orange);


                }
            });

            holder.btn_view_profile_inbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = holder.getAdapterPosition();
                    // Toast.makeText(ctx, "" + viewType, Toast.LENGTH_SHORT).show();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    String str_partner_id = recyclerBean.getStr_id();
                    String str_partner_name = recyclerBean.getStr_username();
                    String str_row_id = recyclerBean.getStr_mail_id();
                    Log.e("NN:mail", str_partner_id);
                    Log.e("NN:mail", str_partner_name);
                    String strResp = myInterface.get_matches(str_partner_id, str_partner_name, str_row_id, "view_profile", recycleV);
                    Log.e("NN:mail", strResp);
                    if (strResp.equalsIgnoreCase("success")) {
                        Log.e("NMB:", strResp);
                        holder.txt_read_inbox.setText("Read");
                        holder.txt_read_inbox.setTextColor(Color.parseColor("#fb7b09"));
                        holder.img_mark_as_read.setBackgroundResource(R.drawable.read_orange);

                    }
                }
            });

            holder.img_trash_inbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);

                    String str_partner_id = recyclerBean.getStr_id();
                    String str_partner_name = recyclerBean.getStr_username();
                    String str_row_id = recyclerBean.getStr_mail_id();
                    Log.e("NN:mail", str_partner_id);
                    Log.e("NN:mail", str_partner_name);
                    String str_resp = myInterface.get_trash_det("trash_sent", str_partner_id, str_row_id, "inbox");
                    Log.e("NM:adapt_mail", str_resp);
                    if (str_resp.equalsIgnoreCase("success")) {
                        removeAt(holder.getAdapterPosition());
                    }
                }
            });
            holder.ll_block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);

                    String str_partner_id = recyclerBean.getStr_id();

                    Log.e("NN:mail", str_partner_id);

                    String str_resp = myInterface.get_trash_det("block_user", str_partner_id, "", "");
                    if (str_resp.equalsIgnoreCase("success")) {
                        holder.ll_block.setBackgroundColor(Color.parseColor("#b6b6b6"));
                        holder.txt_block.setText("Blocked");
                        holder.txt_block.setTextColor(Color.parseColor("#fffafa"));

                    }
                }
            });
            holder.ll_mark_as_read_inbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = holder.getAdapterPosition();
                    // Toast.makeText(ctx, "" + viewType, Toast.LENGTH_SHORT).show();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    String str_partner_id = recyclerBean.getStr_id();
                    String str_partner_name = recyclerBean.getStr_username();
                    String str_row_id = recyclerBean.getStr_mail_id();
                    Log.e("NN:mail", str_partner_id);
                    Log.e("NN:mail", str_partner_name);

                    String strResp = myInterface.get_matches(str_partner_id, str_partner_name, str_row_id, "mark_as_read", recycleV);
                    Log.e("NN:mail", strResp);
                    if (strResp.equalsIgnoreCase("success")) {
                        Log.e("NMB:", strResp);
                        holder.txt_read_inbox.setText("Read");
                        holder.txt_read_inbox.setTextColor(Color.parseColor("#fb7b09"));
                        holder.img_mark_as_read.setBackgroundResource(R.drawable.read_orange);

                    }
                }
            });
        } else if (strFROM.equalsIgnoreCase("sent")) {
            View itemViewa = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_sent, parent, false);
            holder = new MyViewHolder(itemViewa);

            holder.btn_view_profile_sent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Toast.makeText(ctx, "Hii", Toast.LENGTH_SHORT).show();
                    int pos = holder.getAdapterPosition();
                    //  Toast.makeText(ctx, "" + pos, Toast.LENGTH_SHORT).show();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);
                    Log.e("NN:v", String.valueOf(viewType));
                    Log.e("NN:v", String.valueOf(pos));
                    String str_partner_id = recyclerBean.getStr_id();
                    String str_partner_name = recyclerBean.getStr_username();
                    Log.e("NN:mail", str_partner_id);
                    Log.e("NN:mail", str_partner_name);
                    String str_row_id = recyclerBean.getStr_mail_id();
                    myInterface.get_matches(str_partner_id, str_partner_name, str_row_id, "view_profile", recycleV);


                }
            });
            holder.img_trash_sent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);

                    String str_partner_id = recyclerBean.getStr_id();
                    String str_partner_name = recyclerBean.getStr_username();
                    String str_row_id = recyclerBean.getStr_mail_id();
                    Log.e("NN:mail", str_partner_id);
                    Log.e("NN:mail", str_partner_name);
                    String str_resp = myInterface.get_trash_det("trash_sent", str_partner_id, str_row_id, "sentbox");

                    Log.e("NM:adapt_mail", str_resp);
                    if (str_resp.equalsIgnoreCase("success")) {
                        removeAt(holder.getAdapterPosition());
                    }
                }
            });
        } else if (strFROM.equalsIgnoreCase("trash")) {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_trash, parent, false);
            holder = new MyViewHolder(itemView);

            holder.img_trash_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    RecyclerBean recyclerBean = recyclerBeanList.get(pos);

                    String str_partner_id = recyclerBean.getStr_id();
                    String str_partner_name = recyclerBean.getStr_username();
                    String str_row_id = recyclerBean.getStr_mail_id();
                    Log.e("NN:mail", str_partner_id);
                    Log.e("NN:mail", str_partner_name);
                    String str_resp = myInterface.get_trash_det("trash_del", str_partner_id, str_row_id, "inbox");
                    Log.e("NM:adapt_mail", str_resp);
                    if (str_resp.equalsIgnoreCase("success")) {
                        removeAt(holder.getAdapterPosition());
                    }
                }
            });

        } else {
            Log.i("NDD", "i am came to else....!!!");
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mail_inbox_list_cont, parent, false);
            holder = new MyViewHolder(itemView);

        }

        /*itemViewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Toast.makeText(ctx, "" + pos, Toast.LENGTH_SHORT).show();
                // ctx.startActivity(new Intent(ctx, DailyRecomViewProfie.class));
                //myInterface.get_matches("hai");
            }
        });*/

        return holder;
    }

    public void removeAt(int position) {
        recyclerBeanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, recyclerBeanList.size());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerBean recyclerBean = recyclerBeanList.get(position);

       // imgLoader.DisplayImage(recyclerBean.getStr_userimage(), holder.img_user);

        Picasso.with(ctx)
                .load(recyclerBean.getStr_userimage())
                .placeholder(R.drawable.default_use)
                .into(holder.img_user);




        Log.e("IMG", "url->" + recyclerBean.getStr_userimage());
        holder.txt_userid.setText(recyclerBean.getStr_easy_marry_id());
        holder.txt_username.setText(recyclerBean.getStr_username());
        holder.txt_details.setText(recyclerBean.getStr_userdetails());

        String str_date_time = recyclerBean.getStr_date_time();
        Long vv = calDay(str_date_time);
        Log.e("DAYS", "days->" + vv);
        holder.txt_days.setText("(" + vv + " days)");
      /*  if(vv.equals("0")){
            holder.txt_days.setText("(today)");
        }
        else{
            holder.txt_days.setText("(" + vv + " days)");
        }
*/

        if (strFROM.equalsIgnoreCase("inbox")) {
            String str_mail_type = recyclerBean.getStr_mail_type();
            String str_mark_as_read = recyclerBean.getStr_mark_as_read_key();
            String str_block_key = recyclerBean.getStr_block_key();
            String str_acceptReject_key = recyclerBean.getStr_acceptReject();
            Log.i("NN:adap", "int_status-->" + str_acceptReject_key);
            String str_msg=recyclerBean.getStr_msg();
          /*  if (!str_mail_type.equalsIgnoreCase("interest") ) {
                holder.btn_view_msg.setVisibility(View.GONE);
            }
            else*/
            if (str_mail_type.equalsIgnoreCase("interest") && str_acceptReject_key.equalsIgnoreCase("yes")) {
                if (str_acceptReject_key.equalsIgnoreCase("yes")) {
                    holder.btn_view_msg.setVisibility(View.GONE);
                    holder.txt_mail_type.setText("This user has accepted your interest");
                }
            }
            else  if (str_mail_type.equalsIgnoreCase("interest") && str_acceptReject_key.equalsIgnoreCase("no")) {
               if (str_acceptReject_key.equalsIgnoreCase("no")) {
                    holder.btn_view_msg.setVisibility(View.GONE);
                    holder.txt_mail_type.setText("This user has declined your interest");
                }
            }
            else if(str_mail_type.equalsIgnoreCase("interest") && str_acceptReject_key.equalsIgnoreCase("")){
                holder.btn_view_msg.setVisibility(View.VISIBLE);
                if (str_mail_type.equalsIgnoreCase("interest")) {
                    holder.txt_mail_type.setText("This user is interested in your profile");
                }
            }
            else {
                holder.btn_view_msg.setVisibility(View.GONE);
            }
            if (str_mark_as_read.equalsIgnoreCase("1")) {
                holder.txt_read_inbox.setText("Read");
                holder.txt_read_inbox.setTextColor(Color.parseColor("#fb7b09"));
                holder.img_mark_as_read.setBackgroundResource(R.drawable.read_orange);
            } else {
                holder.txt_read_inbox.setText("Mark as read");
                holder.txt_read_inbox.setTextColor(Color.parseColor("#639639"));
                holder.img_mark_as_read.setBackgroundResource(R.drawable.mark_read);
            }
            if (str_mail_type.equalsIgnoreCase("shortlist")) {
                holder.txt_mail_type.setText("This user has shortlisted you");
            } else if (str_mail_type.equalsIgnoreCase("viewed")) {
                holder.txt_mail_type.setText("This user has viewed your profile");
            }
            if (str_block_key.equalsIgnoreCase("0")) {
                holder.ll_block.setBackgroundColor(Color.parseColor("#fb7b09"));
                holder.txt_block.setText("Block");
            } else {
                holder.ll_block.setBackgroundColor(Color.parseColor("#b6b6b6"));
                holder.txt_block.setText("Blocked");
                holder.txt_block.setTextColor(Color.parseColor("#fffafa"));
            }

        } else if (strFROM.equalsIgnoreCase("sent")) {
            String str_mail_type = recyclerBean.getStr_mail_type();
            if (str_mail_type.equalsIgnoreCase("shortlist")) {
                holder.txt_mail_type.setText("You have shorlisted this profile");
            } else if (str_mail_type.equalsIgnoreCase("interest")) {
                holder.txt_mail_type.setText("You have sent interest to this profile");
            } else if (str_mail_type.equalsIgnoreCase("viewed")) {
                holder.txt_mail_type.setText("You have viewed this profile");
            }
        }


    }

    @Override
    public int getItemCount() {

        return recyclerBeanList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }

    public long calDay(String str_date) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(c.getTime());
        Log.e("N_date", "formattedDate : " + formattedDate);
        long elapsedDays = 0;
        try {

            Date date1 = null;
            Date date2 = null;
            try {

                date1 = simpleDateFormat.parse(str_date);
                date2 = simpleDateFormat.parse(formattedDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


            //milliseconds
            //1 minute = 60 seconds
            //1 hour = 60 x 60 = 3600
            //1 day = 3600 x 24 = 86400
            long different = date2.getTime() - date1.getTime();

           /* System.out.println("startDate : " + date1);
            System.out.println("endDate : "+ date2);
            System.out.println("different : " + different);*/
            Log.e("N_date", "startDate : " + date1);
            Log.e("N_date", "endDate : " + date2);
            Log.e("N_date", "different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays,
                    elapsedHours, elapsedMinutes, elapsedSeconds);

            //Log.e("N_date", "%d days, %d hours, %d minutes, %d seconds%n"+elapsedDays+","+elapsedHours+","+elapsedMinutes+","+elapsedSeconds);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return elapsedDays;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img_user;
        public TextView txt_userid;
        public TextView txt_username;
        public TextView txt_details;
        public TextView txt_days;
        public LinearLayout ll_block, ll_mark_as_read_inbox;
        public ImageView img_trash_sent, img_trash_inbox, img_mark_as_read, img_trash_trash;
        public TextView txt_mail_type, txt_read_inbox, txt_block;
        Button btn_view_profile_inbox, btn_view_profile_sent, btn_view_msg;

        public MyViewHolder(View view) {
            super(view);
            if (strFROM.equalsIgnoreCase("inbox")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                btn_view_msg = (Button) view.findViewById(R.id.btn_msg);
                btn_view_profile_inbox = (Button) view.findViewById(R.id.btn_profile);
                txt_mail_type = (TextView) view.findViewById(R.id.txt_mail_type);
                img_trash_inbox = (ImageView) view.findViewById(R.id.img_trash);
                ll_mark_as_read_inbox = (LinearLayout) view.findViewById(R.id.ll_mark_as_read);
                txt_read_inbox = (TextView) view.findViewById(R.id.txt_read);
                img_mark_as_read = (ImageView) view.findViewById(R.id.img_mark_read);
                txt_days = (TextView) view.findViewById(R.id.txt_days);
                ll_block = (LinearLayout) view.findViewById(R.id.ll_block);
                txt_block = (TextView) view.findViewById(R.id.txt_block);
                ll_block.setOnClickListener(this);
                ll_mark_as_read_inbox.setOnClickListener(this);
                img_trash_inbox.setOnClickListener(this);
                view.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("sent")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                btn_view_profile_sent = (Button) view.findViewById(R.id.btn_profile);
                txt_mail_type = (TextView) view.findViewById(R.id.txt_mail_type);
                img_trash_sent = (ImageView) view.findViewById(R.id.img_trash);
                txt_days = (TextView) view.findViewById(R.id.txt_days);
                view.setOnClickListener(this);
                btn_view_profile_sent.setOnClickListener(this);
                img_trash_sent.setOnClickListener(this);
            } else if (strFROM.equalsIgnoreCase("trash")) {
                img_user = (ImageView) view.findViewById(R.id.img_user);
                txt_userid = (TextView) view.findViewById(R.id.txt_user_id);
                txt_username = (TextView) view.findViewById(R.id.txt_username);
                txt_details = (TextView) view.findViewById(R.id.txt_details);
                img_trash_trash = (ImageView) view.findViewById(R.id.img_trash);
                txt_days = (TextView) view.findViewById(R.id.txt_days);
                view.setOnClickListener(this);
            }


        }

        @Override
        public void onClick(View v) {

        }


    }
}
