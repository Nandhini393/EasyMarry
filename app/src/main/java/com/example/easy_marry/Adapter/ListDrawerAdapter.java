package com.example.easy_marry.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by android2 on 9/6/16.
 */
public class ListDrawerAdapter extends BaseAdapter {
    String[] result;
    Context context;
    MyInterface myInterface;
    ArrayList<ListDrawerBean> listDrawerBeen;
    private static LayoutInflater inflater = null;
    String strEditIdentify;

    public ListDrawerAdapter(Context con, ArrayList<ListDrawerBean> alBean, MyInterface interfac, String stridentifyEdit) {
        context = con;
        listDrawerBeen = alBean;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myInterface = interfac;
        strEditIdentify = stridentifyEdit;
    }

    @Override
    public int getCount() {
        return listDrawerBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return listDrawerBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder view1;
        if (view == null) {
            Log.i("MM", "IF : ");
            view1 = new ViewHolder();

            view = inflater.inflate(R.layout.drawer_list_contents, viewGroup, false);

            // get layout from mobile.xml
            view1.txt_list_items = (TextView) view.findViewById(R.id.list_text);
            view.setTag(view1);

        } else {
            view1 = (ViewHolder) view.getTag();
        }

        final ListDrawerBean listDrawerBean = listDrawerBeen.get(i);
        view1.txt_list_items.setText(listDrawerBean.getStr_list_items());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Toast.makeText(context,listDrawerBean.getStr_list_items(), Toast.LENGTH_LONG).show();
                myInterface.get_list_drawer_items(listDrawerBean.getStr_list_items(),listDrawerBean.getStr_list_items_id(), strEditIdentify,null);

            }
        });
        return view;
    }

    class ViewHolder {
        public TextView txt_list_items;
    }
}
