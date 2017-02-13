package com.example.easy_marry.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.R;
import com.example.easy_marry.imageCache.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Android2 on 7/13/2016.
 */
public class GridGalleryAdapter extends ArrayAdapter {
    ImageLoader imgLoader;
    int nScreenHeight;
    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

    public GridGalleryAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        imgLoader = new ImageLoader(context);

        DisplayMetrics dp = ((Activity) context).getResources().getDisplayMetrics();
        int nHeight = dp.heightPixels;

        nScreenHeight = (int) ((float) nHeight / (float) 1.5);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        final ImageItem item = data.get(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);


            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater inflater = LayoutInflater.from(context);
                    View dialogLayout = inflater.inflate(R.layout.imagezoom, null);

                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setView(dialogLayout);
                    alertDialogBuilder.setCancelable(true);

                    final android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                    RelativeLayout llayAlert = (RelativeLayout) dialogLayout.findViewById(R.id.llayalertDialog);
                    ImageView gallery_img = (ImageView) dialogLayout.findViewById(R.id.img_gallery);

                    imgLoader.DisplayImage(item.getImage(), gallery_img);
                    FrameLayout.LayoutParams lparams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, nScreenHeight);
                    llayAlert.setLayoutParams(lparams);



                }
            });


            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        imgLoader.DisplayImage(item.getImage(), holder.image);
        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}
