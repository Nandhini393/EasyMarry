package com.example.easy_marry.Bean;

/**
 * Created by android2 on 16/6/16.
 */
public class RecyclerMessengerBean {
    int str_userimage;
    String str_id;
    String str_username;


    public RecyclerMessengerBean(int str_image, String str_id, String str_name){
        this.str_userimage=str_image;
        this.str_id=str_id;
        this.str_username=str_name;

    }

    public int getStr_userimage() {
        return str_userimage;
    }

    public void setStr_userimage(int str_userimage) {
        this.str_userimage = str_userimage;
    }

    public String getStr_id() {
        return str_id;
    }

    public void setStr_id(String str_id) {
        this.str_id = str_id;
    }



    public String getStr_username() {
        return str_username;
    }

    public void setStr_username(String str_username) {
        this.str_username = str_username;
    }


}
