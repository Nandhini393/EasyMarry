package com.example.easy_marry.Adapter;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.MyInterface;
import com.example.easy_marry.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android2 on 9/6/16.
 */
public class ListDrawerPartnerAdapter extends BaseAdapter {
    String[] result;
    Context context;
    MyInterface myInterface;
    ArrayList<ListDrawerBean> listDrawerBeen;
    private static LayoutInflater inflater = null;
    String strEditIdentify;
    String StrFrom;
    int selectedPosition = 0;
    HashMap<String, JSONObject> hsMap = new HashMap<String, JSONObject>();
    String Action;

    String strEditCheckedVal;

    SparseBooleanArray mChecked = new SparseBooleanArray();
    ArrayList<CheckBox> alCheckBox = new ArrayList<CheckBox>();

    public ListDrawerPartnerAdapter(Context con, ArrayList<ListDrawerBean> alBean, MyInterface interfac, String stridentifyEdit, String str_from) {
        context = con;
        listDrawerBeen = alBean;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myInterface = interfac;
        strEditIdentify = stridentifyEdit;
        StrFrom = str_from;

        Action = stridentifyEdit;
    }

    public ListDrawerPartnerAdapter(Context con, ArrayList<ListDrawerBean> alBean, MyInterface interfac, String stridentifyEdit, String str_from, String strCheckedVal) {
        context = con;
        listDrawerBeen = alBean;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myInterface = interfac;
        strEditIdentify = stridentifyEdit;
        StrFrom = str_from;
        Log.i("MM", "IF1 : " + stridentifyEdit);
        Action = stridentifyEdit;

        strEditCheckedVal = strCheckedVal;
    }

    @Override
    public int getCount() {
        return listDrawerBeen.size();
        //listDrawerBeen.size()
    }

    @Override
    public Object getItem(int i) {
        return listDrawerBeen.get(i);
        //listDrawerBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder = new ViewHolder();

        final ListDrawerBean listDrawerBean = listDrawerBeen.get(i);
        Log.i("MM", "IFFrom : " + StrFrom);

        if (StrFrom.equalsIgnoreCase("checkbox")) {

            Log.i("MM", "IFAction : " + Action);
            view = inflater.inflate(R.layout.drawer_list_filter_contents, viewGroup, false);
            holder.txt_list_items1 = (TextView) view.findViewById(R.id.txt_filter);
            holder.cb_items = (CheckBox) view.findViewById(R.id.cb_filter);
            holder.cb_items.setTag(i);
            //holder.cb_items.setId(i);
            if (strEditCheckedVal != null) {
                if (Action.equalsIgnoreCase("edit")) {
                    Log.i("RJ", "EDIT : 1");
                    Log.i("RJ", "strEditCheckedVal :" + strEditCheckedVal);

                    if (strEditCheckedVal.length() > 1) {
                        Log.i("RJ", "EDIT :2 ");


                        String[] ss = strEditCheckedVal.split(",");

                        Log.i("RJ", "EDIT : 3");
                        for (int j = 0; j < ss.length; j++) {

                            Log.i("RJ", "EDIT : 4-> " + ss[j]);
                            Log.i("RJ", "EDIT : 4.1-> " + listDrawerBean.getStr_list_items_id());
                            if (listDrawerBean.getStr_list_items_id().equalsIgnoreCase(ss[j])) {

                                holder.cb_items.setChecked(true);

                                JSONObject jsoBj = new JSONObject();
                                try {

                                    jsoBj.put("id", listDrawerBean.getStr_list_items_id());
                                    jsoBj.put("value", listDrawerBean.getStr_list_items());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hsMap.put(listDrawerBean.getStr_list_items_id(), jsoBj);

                                myInterface.get_list_drawer_items(listDrawerBean.getStr_list_items(), listDrawerBean.getStr_list_items_id(), "", hsMap);


                            }
                        }

                    } else {

                        if (listDrawerBean.getStr_list_items_id().equalsIgnoreCase(strEditCheckedVal)) {
                            holder.cb_items.setChecked(true);

                            JSONObject jsoBj = new JSONObject();
                            try {

                                jsoBj.put("id", listDrawerBean.getStr_list_items_id());
                                jsoBj.put("value", listDrawerBean.getStr_list_items());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            hsMap.put(listDrawerBean.getStr_list_items_id(), jsoBj);


                            myInterface.get_list_drawer_items(listDrawerBean.getStr_list_items(), listDrawerBean.getStr_list_items_id(), "", hsMap);


                        }

                    }
                }
            }


            holder.txt_list_items1.setText(listDrawerBean.getStr_list_items());
            holder.cb_items.setTag(i);
            alCheckBox.add(holder.cb_items);
            holder.cb_items.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {

                        if (holder.cb_items.getTag().toString().equalsIgnoreCase("0")) {
                            for (int i = 1; i < alCheckBox.size(); i++) {
                                CheckBox cb = alCheckBox.get(i);
                                cb.setChecked(false);
                            }
                        } else {
                            CheckBox cb = alCheckBox.get(0);
                            if (cb.isChecked()) {
                                cb.setChecked(false);
                            }
                        }

                        //  ArrayList<String> alNames = getCheckedItems(alCheckBox);

                        JSONObject jsoBj = new JSONObject();
                        try {

                            jsoBj.put("id", listDrawerBean.getStr_list_items_id());
                            jsoBj.put("value", listDrawerBean.getStr_list_items());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hsMap.put(listDrawerBean.getStr_list_items_id(), jsoBj);


                        myInterface.get_list_drawer_items(listDrawerBean.getStr_list_items(), listDrawerBean.getStr_list_items_id(), strEditIdentify, hsMap);

                        int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                        listDrawerBeen.get(getPosition).setSelected(buttonView.isChecked()); // Set tvaluehe  of checkbox to maintain its state.

                    } else {
                        JSONObject jsoBj = new JSONObject();
                        try {
                            jsoBj.put("id", listDrawerBean.getStr_list_items_id());
                            jsoBj.put("value", listDrawerBean.getStr_list_items());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //hsMap.put(listDrawerBean.getStr_list_items_id(), jsoBj);
                        hsMap.remove(listDrawerBean.getStr_list_items_id());
                        Log.i("NN", "hash-> : " + hsMap);
                        Log.i("NN", "hash_removed-> : " + listDrawerBean.getStr_list_items_id());
                        myInterface.filter_Check(hsMap, listDrawerBean.getStr_list_items_id());
                    }
                }


            });
            //  holder.cb_items.setChecked((mChecked.get(i) == true ? true : false));

           /* holder.cb_items.setTag(i); // This line is important.
             holder.cb_items.setChecked(listDrawerBeen.get(i).isSelected());*/

        } else if (StrFrom.equalsIgnoreCase("radio")) {
            Log.i("MM", "IF : ");
            view = inflater.inflate(R.layout.drawer_list_filter_radio_contents, viewGroup, false);
            holder.txt_list_items2 = (TextView) view.findViewById(R.id.txt_filter);
            holder.rb_items = (RadioButton) view.findViewById(R.id.rb_filter);
            holder.txt_list_items2.setText(listDrawerBean.getStr_list_items());
            holder.rb_items.setChecked(i == selectedPosition);
            holder.rb_items.setTag(i);
            holder.rb_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPosition = (Integer) view.getTag();
                    notifyDataSetChanged();
                    myInterface.get_list_drawer_items(listDrawerBean.getStr_list_items(), listDrawerBean.getStr_list_items_id(), "", null);

                }
            });
        }
        return view;
    }

    private ArrayList<String> getCheckedItems(ArrayList<CheckBox> alCheckBox) {
        ArrayList<String> alNames = new ArrayList<String>();

        for (int i = 0; i < alCheckBox.size(); i++) {
            if (alCheckBox.get(i).isChecked()) {
                ListDrawerBean sB = listDrawerBeen.get(i);
                alNames.add(sB.getStr_list_items());
            } else {
                alNames.add("0");
            }
        }

        return alNames;
    }

    class ViewHolder {
        public TextView txt_list_items1, txt_list_items2;
        public CheckBox cb_items;
        public RadioButton rb_items;

    }
}
