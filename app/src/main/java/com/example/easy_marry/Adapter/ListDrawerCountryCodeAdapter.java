package com.example.easy_marry.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;

import java.util.ArrayList;

/**
 * Created by android2 on 9/6/16.
 */
public class ListDrawerCountryCodeAdapter extends BaseAdapter {
    String[] country_name;
    Context context;
    String[] country_code;
    MyInterface myInterface;
    private LayoutInflater inflater = null;
String str_From;
    public ListDrawerCountryCodeAdapter(Context c, String str_from,String[] countryname, String[] countrycode, MyInterface interfac) {
        // TODO Auto-generated constructor stub
        country_name = countryname;
        context = c;
        myInterface = interfac;
        country_code = countrycode;
        str_From=str_from;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return country_name.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return country_name[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public class Holder {
        TextView txt_list_code;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.drawer_country_code_contents, null);
        holder.txt_list_code = (TextView) rowView.findViewById(R.id.list_code);
        holder.txt_list_code.setText(country_name[position] + "(" + country_code[position] + ")");
        Log.e("code", country_code[position]);
        Log.e("country", country_name[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Toast.makeText(context, "You Clicked "+country_name[position], Toast.LENGTH_LONG).show();
              //  myInterface.get_list_drawer_items(country_name[position] + "(" + country_code[position] + ")", "", "phone",null);

                myInterface.get_list_drawer_items(country_code[position], "", str_From,null);

            }
        });
        return rowView;
    }

}