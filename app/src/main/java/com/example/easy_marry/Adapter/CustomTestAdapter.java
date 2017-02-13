package com.example.easy_marry.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.R;
import com.example.easy_marry.imageCache.ImageLoader;

import java.util.ArrayList;

/**
 * Created by android3 on 1/9/16.
 */
public class CustomTestAdapter extends BaseAdapter {
    Context ctx;
    public ArrayList<ImageItem> aBtmap;
    ImageLoader imgLoader;
    public CustomTestAdapter(Context context, ArrayList<ImageItem> alBitmap) {
        this.ctx = context;
        this.aBtmap = alBitmap;
        imgLoader = new ImageLoader(ctx);
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
        rowView = inflater.inflate(R.layout.grid_single_test, null);
        holder.tv1 = (TextView) rowView.findViewById(R.id.txt_value);
        holder.tv2 = (TextView) rowView.findViewById(R.id.txt_close);

        ImageItem ImgI = aBtmap.get(position);

       // imgLoader.DisplayImage(ImgI.getImage(), holder.img);
        return rowView;
    }

    public class Holder {
        TextView tv1,tv2;

    }
}
