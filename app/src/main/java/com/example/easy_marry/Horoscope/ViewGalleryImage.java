package com.example.easy_marry.Horoscope;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.easy_marry.Adapter.CustomPagerAdapter;
import com.example.easy_marry.Adapter.GalleryVideoAdapter;
import com.example.easy_marry.Bean.GalleryVideoBean;
import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.Interface.GalleryVideoInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Android2 on 8/26/2016.
 */
public class ViewGalleryImage extends Activity {
    ImageView img_gallery_image;
    String str_images, str_img_pos,str_image_path;
    ImageLoader imgLoader;
    Context context;
    ProgressDialog dialog;
    LinearLayout ll_gallery_parent;
    private PagerAdapter mPagerAdapter;
    int nIndex = 0;
    ArrayList<ImageItem> myList;
    String[] strGalleryURL;
    ArrayList<String> alGalleryPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gallery_image);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        dialog = new ProgressDialog(ViewGalleryImage.this);
        str_images = getIntent().getStringExtra("images");
        str_img_pos = getIntent().getStringExtra("image_pos");
        str_image_path = getIntent().getStringExtra("image_path");
        context = this;
        imgLoader = new ImageLoader(context);
        try {
            strGalleryURL =str_images.split(",");
            Log.i("SSS", "galleryimg : " + strGalleryURL);
            alGalleryPath = new ArrayList<String>();
            for (int j = 0; j < strGalleryURL.length; j++) {
                alGalleryPath.add(GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[j]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         myList = new ArrayList<ImageItem>();
        for (int k = 0; k < alGalleryPath.size(); k++) {
            Log.e("OO", "images--" + String.valueOf(strGalleryURL[k]));
            try {
                URL url = new URL(alGalleryPath.get(k));
                final String strImageURl = alGalleryPath.get(k);
                Log.e("OO", "images path--" + strImageURl);
                ImageItem imageItems = new ImageItem();
                imageItems.setImage(strImageURl);
                imageItems.setStr_img_id(str_img_pos);
                myList.add(imageItems);
                Log.e("OO", "images--" + String.valueOf(myList));
                /*GalleryVideoAdapter rcAdapter = new GalleryVideoAdapter(ctx, alBitmap,(GalleryVideoInterface)ctx);
                gd_view_horo.setAdapter(rcAdapter);*/

                //   gaggeredGridLayoutManager.scrollToPosition(1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CreateVIew(myList,viewPager);
       // ArrayList<ImageItem> myList= new ArrayList<ImageItem>();
        /*ImageItem GVB = new ImageItem();
        GVB.setImage(str_image_path);
        GVB.setStr_img_id(str_img_pos);
        myList.add(GVB);*/
      //  CreateVIew(myList,viewPager);
        //ll_gallery_parent = (LinearLayout) findViewById(R.id.ll_gallery_parent);

    }

    private void CreateVIew(ArrayList<ImageItem> beanArrayList, ViewPager viewPager) {
        ArrayList<ImageItem> orderedAarraylist = new ArrayList<ImageItem>();

        Log.i("ND", "str_GalId : " + str_image_path);

        for (int i = 0; i < beanArrayList.size(); i++) {
            Log.i("ND", "Value : " + beanArrayList.get(i).getImage());
            ImageItem GVB = beanArrayList.get(i);
            String strID = GVB.getImage();

            if (strID.equals(String.valueOf(str_image_path))) {
                nIndex = i;
                break;
            }

        }

        for (int j = nIndex; j < beanArrayList.size(); j++) {
            orderedAarraylist.add(beanArrayList.get(j));

        }

        if (nIndex != 0) {
            for (int k = 0; k < nIndex; k++) {
                orderedAarraylist.add(beanArrayList.get(k));
            }

        }

        CustomPagerAdapter rcAdapter = new CustomPagerAdapter(context, orderedAarraylist);
        viewPager.setAdapter(rcAdapter);
    }



}
