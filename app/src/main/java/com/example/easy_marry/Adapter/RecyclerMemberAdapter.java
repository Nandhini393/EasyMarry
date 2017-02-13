package com.example.easy_marry.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.RecyclerMemberBean;
import com.example.easy_marry.Interface.MembershipInterface;
import com.example.easy_marry.R;

import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class RecyclerMemberAdapter extends RecyclerView.Adapter<RecyclerMemberAdapter.MyViewHolder> {

    private List<RecyclerMemberBean> recyclerBeanList;
    MembershipInterface threeMonthsInterface;
    Context context;
    String str_from;

    public RecyclerMemberAdapter(List<RecyclerMemberBean> myList, Context con, MembershipInterface memInterf, String str_type) {

        this.recyclerBeanList = myList;
        this.threeMonthsInterface = memInterf;
        context = con;
        str_from = str_type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final RecyclerMemberBean recyclerBean = recyclerBeanList.get(position);
        holder.btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("NN", String.valueOf(position));

                threeMonthsInterface.getMemberShip(String.valueOf(position), str_from);
            }
        });
        holder.txt_package_name.setText(recyclerBean.getStr_plan_name());
        holder.txt_amount.setText("Rs."+ recyclerBean.getStr_amount());
        holder.txt_per_month.setText("Rs."+ recyclerBean.getStr_per_month()+"/Month");
        String[] strDesc = recyclerBean.getStr_desc().split(",");

        for (int i = 0; i < strDesc.length; i++) {
            //need to inflate layout here and add all the layouts in the llayNeedinfate


            if (strDesc[i].length() > 0) {
                LinearLayout.LayoutParams llayMainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llayMainParams.setMargins(0, 5, 0, 5);
                LinearLayout llayMain = new LinearLayout(context);
                llayMain.setOrientation(LinearLayout.HORIZONTAL);
                llayMain.setLayoutParams(llayMainParams);

                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20, context.getResources().getDisplayMetrics());

                LinearLayout.LayoutParams ImagViewParams = new LinearLayout.LayoutParams(width, height);
                ImageView imvTick = new ImageView(context);
                imvTick.setImageResource(R.drawable.small_tick_orange);
                imvTick.setLayoutParams(ImagViewParams);
                ImagViewParams.gravity = Gravity.LEFT;
                ImagViewParams.leftMargin=10;

                LinearLayout.LayoutParams TextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                TextViewParams.leftMargin = 8;
                TextView tv = new TextView(context);
                tv.setGravity(Gravity.LEFT);
                tv.setTextColor(Color.BLACK);
                tv.setText(strDesc[i]);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv.setLayoutParams(TextViewParams);

                llayMain.addView(imvTick);
                llayMain.addView(tv);
                holder.llayNeedinfate.addView(llayMain);
            }

        }
        //holder.txt_per_month.setText(recyclerBean.getStr_per_month());
    }

    @Override
    public int getItemCount() {
        return recyclerBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_package_name;
        public TextView txt_amount;
        public TextView txt_per_month;
        public Button btn_subscribe;
        public LinearLayout header, content;
        public ImageView img_arrow;
        private int flag = 0;
        LinearLayout llayNeedinfate;

        public MyViewHolder(View view) {
            super(view);
            txt_package_name = (TextView) view.findViewById(R.id.txt_package);
            txt_amount = (TextView) view.findViewById(R.id.txt_amount);
            txt_per_month = (TextView) view.findViewById(R.id.txt_per_month);
            btn_subscribe = (Button) view.findViewById(R.id.btn_subscribe);
            header = (LinearLayout) view.findViewById(R.id.classic_header);
            content = (LinearLayout) view.findViewById(R.id.classic_content);
            img_arrow = (ImageView) view.findViewById(R.id.down_arrow);
            llayNeedinfate = (LinearLayout) view.findViewById(R.id.llayNeedtoInflate);

            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == 0) {
                        flag = 1; // 1 => Button ON
                        content.setVisibility(View.VISIBLE);
                        img_arrow.setBackgroundResource(R.drawable.up_bold);
                    } else {
                        flag = 0; // 0 => Button OFF
                        content.setVisibility(View.GONE);
                        img_arrow.setBackgroundResource(R.drawable.down_bold);
                    }
                }
            });

        }
    }
}