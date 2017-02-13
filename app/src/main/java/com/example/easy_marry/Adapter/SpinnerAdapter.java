package com.example.easy_marry.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Bean.SpinnerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;

import java.util.ArrayList;

/**
 * To load the Spinner values using this Adapter
 * Created by Rajaji on 14-04-2016.
 */
public class SpinnerAdapter extends ArrayAdapter<ListDrawerBean> {

    // Your sent context
    private Context context;
    private String type;
    // Your custom values for the spinner (User)
    // private SpinnerBean[] values;
    ArrayList<ListDrawerBean> alSpinBean;
    MyInterface my_interface;
    String strFrom;
    String strVal;

    String StrAction, StrAValues;

    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<ListDrawerBean> alSpin, MyInterface inter, String type, String SpinnerFrom) {
        super(context, textViewResourceId, alSpin);
        this.context = context;
        this.alSpinBean = alSpin;
        this.my_interface = inter;
        this.type = type;
        this.strFrom = SpinnerFrom;
    }

    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<ListDrawerBean> alSpin, MyInterface inter, String type, String SpinnerFrom, String strAction, String Values) {
        super(context, textViewResourceId, alSpin);
        this.context = context;
        this.alSpinBean = alSpin;
        this.my_interface = inter;
        this.type = type;
        this.strFrom = SpinnerFrom;
        StrAction = strAction;
        StrAValues = Values;

    }

    public int getCount() {
        return alSpinBean.size();
    }

    public ListDrawerBean getItem(int position) {
        return alSpinBean.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv;

    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    /*  @Override
      public View getDropDownView(int position, View convertView, ViewGroup parent) {
          TextView txt = new TextView(context);
          txt.setPadding(16, 16, 16, 16);
          txt.setTextSize(18);
          txt.setGravity(Gravity.CENTER_VERTICAL);
         *//* txt.setText(alSpin.get(position));*//*
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down, 0);
        txt.setCompoundDrawablePadding(16);
        *//*txt.setText(alSpinBean.get(i));*//*
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }
*/
    private View initView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        try {
            Holder holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ListDrawerBean sB = alSpinBean.get(position);
            rowView = inflater.inflate(R.layout.spinner_item, null);

            holder.tv = (TextView) rowView.findViewById(R.id.tv_spinName);

           /* if (StrAction.equalsIgnoreCase("edit")) {
                Log.i("RJ", "came to here 1");
                String ss[] = StrAValues.split("-");
                for (int i = 0; i < ss.length; i++) {
                    Log.i("RJ", "came to here 2 :" + i);
                    if (ss[i].trim() == sB.getStr_list_items_id()) {
                        Log.i("RJ", "came to here 3 :" + sB.getStr_list_items_id());
                        holder.tv.setText(sB.getStr_list_items().toString());
                    }
                }
            } else {*/
            holder.tv.setText(sB.getStr_list_items().toString());
            /*}*/


            my_interface.get_list_drawer_items(sB.getStr_list_items().toString(), sB.getStr_list_items_id().toString(), strFrom, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowView;
    }

}