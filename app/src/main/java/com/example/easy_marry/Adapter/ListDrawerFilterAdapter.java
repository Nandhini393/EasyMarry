package com.example.easy_marry.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

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
public class ListDrawerFilterAdapter extends BaseAdapter {
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
    ArrayList<String> al_habits_all = new ArrayList<String>();
    ArrayList<String> al_habits_check = new ArrayList<String>();
    String strEditCheckedVal;

    public ListDrawerFilterAdapter(Context con, ArrayList<ListDrawerBean> alBean, MyInterface interfac, String stridentifyEdit, String str_from) {
        context = con;
        listDrawerBeen = alBean;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myInterface = interfac;
        strEditIdentify = stridentifyEdit;
        StrFrom = str_from;

        Action = stridentifyEdit;
    }

    public ListDrawerFilterAdapter(Context con, ArrayList<ListDrawerBean> alBean, MyInterface interfac, String stridentifyEdit, String str_from, String strCheckedVal) {
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
        View rowView = null;
        final ListDrawerBean listDrawerBean = listDrawerBeen.get(i);
        Log.i("MM", "IFFrom : " + StrFrom);
        if (StrFrom.equalsIgnoreCase("checkbox")) {
            Log.i("MM", "IFAction : " + Action);
            rowView = inflater.inflate(R.layout.drawer_list_filter_contents, viewGroup, false);
            holder.txt_list_items1 = (TextView) rowView.findViewById(R.id.txt_filter);
            holder.cb_items = (CheckBox) rowView.findViewById(R.id.cb_filter);
            holder.cb_items.setTag(i);
            if (strEditIdentify.equalsIgnoreCase("eat_habit")) {
                al_habits_all.add(listDrawerBean.getStr_list_items_id());
            }
            if (strEditCheckedVal != null) {

                if (Action.equalsIgnoreCase("edit")) {
                    Log.i("RJ", "EDIT : 1");
                    Log.i("RJ", "strEditCheckedVal :" + strEditCheckedVal);


                    if (strEditCheckedVal.length() > 1) {

                        Log.i("RJ", "EDIT :2 ");
                        String[] ss = strEditCheckedVal.split(",");
                        Log.i("RJ", "EDIT : 3");
                        for (int j = 0; j < ss.length; j++) {
                            Log.i("RJ", "EDIT : 4 " + ss[j]);
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


            holder.cb_items.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (buttonView.isChecked()) {
                        JSONObject jsoBj = new JSONObject();
                        try {
                            if (strEditIdentify.equalsIgnoreCase("eat_habit")) {
                                al_habits_check.add(listDrawerBean.getStr_list_items_id());
                            }

                            jsoBj.put("id", listDrawerBean.getStr_list_items_id());
                            jsoBj.put("value", listDrawerBean.getStr_list_items());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /*if (al_habits_check.size() == 1) {
                            if (listDrawerBean.getStr_list_items_id() == "4") {
                                for (int i = 0; i < al_habits_all.size(); i++) {
                                    if (al_habits_all.get(i) != "4") {

                                    }
                                }
                            }
                        }*/

                        if (strEditIdentify.equalsIgnoreCase("eat_habit")) {
                            //For al CheckBox at fifth
                            if (al_habits_check.size() == al_habits_all.size() - 1) {
                                for (int i = 0; i < al_habits_all.size(); i++) {
                                    if (al_habits_all.get(i) == "4") {
                                        holder.cb_items.setChecked(true);
                                    }
                                }
                            }
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
                        myInterface.filter_Check(hsMap,listDrawerBean.getStr_list_items_id(),strEditIdentify);
                    }

                }
            });

            // holder.cb_items.setTag(i); // This line is important.
            //  holder.cb_items.setChecked(listDrawerBeen.get(i).isSelected());
        } else if (StrFrom.equalsIgnoreCase("radio")) {
            Log.i("MM", "IF : ");
            rowView = inflater.inflate(R.layout.drawer_list_filter_radio_contents, viewGroup, false);
            holder.txt_list_items2 = (TextView) rowView.findViewById(R.id.txt_filter);
            holder.rb_items = (RadioButton) rowView.findViewById(R.id.rb_filter);
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
        return rowView;
    }


    class ViewHolder {
        public TextView txt_list_items1, txt_list_items2;
        public CheckBox cb_items;
        public RadioButton rb_items;

    }
}
