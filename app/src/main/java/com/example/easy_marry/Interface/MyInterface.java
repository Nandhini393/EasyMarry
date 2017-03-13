package com.example.easy_marry.Interface;

import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by android2 on 9/6/16.
 */
public interface MyInterface {
    public void get_list_drawer_items(String str_items, String str_items_id, String strIdentify, HashMap<String, JSONObject> json_obj);

    public String get_matches(String str_id, String str_name, String strFrom, String str_type,String str_sent_int, RecyclerView recycleV);

    public HashMap<String, JSONObject> filter_Check(HashMap<String, JSONObject> strVal,String str_removed_id, String strIdentify);

}
