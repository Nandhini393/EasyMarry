package com.example.easy_marry.Bean;

/**
 * Created by android2 on 9/6/16.
 */
public class ListDrawerBean {
    String str_list_items;
    String str_list_items_id;
    String str_list_country;
    String str_list_code;
    String str_religion_id;
    Boolean isChecked;

    public void setChecked(Boolean checked) {

        isChecked = checked;

    }

    public String getStr_religion_id() {
        return str_religion_id;
    }

    public void setStr_religion_id(String str_religion_id) {
        this.str_religion_id = str_religion_id;
    }

    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getStr_list_items_id() {
        return str_list_items_id;
    }

    public void setStr_list_items_id(String str_list_items_id) {
        this.str_list_items_id = str_list_items_id;
    }


    public String getStr_list_code() {
        return str_list_code;
    }

    public void setStr_list_code(String str_list_code) {
        this.str_list_code = str_list_code;
    }

    public String getStr_list_country() {
        return str_list_country;
    }

    public void setStr_list_country(String str_list_country) {
        this.str_list_country = str_list_country;
    }

    public String getStr_list_items() {
        return str_list_items;
    }

    public void setStr_list_items(String str_list_items) {
        this.str_list_items = str_list_items;
    }
}
