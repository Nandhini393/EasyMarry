package com.example.easy_marry.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.Interface.GalleryVideoInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.imageCache.ImageLoader;

import java.util.ArrayList;

/**
 * Created by android3 on 1/9/16.
 */
public class GalleryVideoAdapter extends BaseAdapter {
    Context ctx;
    public ArrayList<ImageItem> aBtmap;
    ImageLoader imgLoader;
    GalleryVideoInterface galleryInterface;
    public GalleryVideoAdapter(Context context, ArrayList<ImageItem> alBitmap,GalleryVideoInterface gal_inter) {
        this.ctx = context;
        this.aBtmap = alBitmap;
        imgLoader = new ImageLoader(ctx);
        galleryInterface=gal_inter;
    }

    @Override
    public int getCount() {
        return aBtmap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder = new Holder();
        final View rowView;
        LayoutInflater inflater = (LayoutInflater) ctx.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.gallery_video_content, null);
        holder.tv = (TextView) rowView.findViewById(R.id.grid_text);
        holder.img = (ImageView) rowView.findViewById(R.id.img_temp_image);

        final ImageItem ImgI = aBtmap.get(position);
      //  holder.img.setImageBitmap(ImgI.getBmpImage());
        imgLoader.DisplayImage(ImgI.getImage(), holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(ctx, "pos"+position, Toast.LENGTH_SHORT).show();
                galleryInterface.getImageFull(ImgI.getImage(), String.valueOf(position));
            }
        });
        return rowView;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }
}
