package com.example.easy_marry.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easy_marry.R;

/**
 * Created by Android2 on 8/4/2016.
 */
public class GalleryVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView templeImage,img_play;

    public GalleryVideoHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        templeImage = (ImageView) itemView.findViewById(R.id.img_temp_image);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
