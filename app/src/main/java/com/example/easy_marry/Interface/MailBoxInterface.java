package com.example.easy_marry.Interface;

import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by android2 on 9/6/16.
 */
public interface MailBoxInterface {
    public String get_trash_det(String str_from_id, String str_to_id, String str_row_id, String str_key);

    public String get_matches(String str_id, String str_name, String strFrom, String str_type, RecyclerView recycleV);



}
