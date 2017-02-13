package com.example.easy_marry.Bean;

import android.graphics.Bitmap;

/**
 * Created by Android2 on 7/13/2016.
 */
public class ImageItem {
    public Bitmap bmpImage;
    private String image;
    public String str_img_id;

    public String getStr_img_id() {
        return str_img_id;
    }

    public void setStr_img_id(String str_img_id) {
        this.str_img_id = str_img_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getBmpImage() {
        return bmpImage;
    }

    public void setBmpImage(Bitmap bmpImage) {
        this.bmpImage = bmpImage;
    }


}