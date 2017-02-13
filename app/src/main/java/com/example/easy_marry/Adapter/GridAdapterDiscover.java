package com.example.easy_marry.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easy_marry.Bean.GridBean;
import com.example.easy_marry.R;

import java.util.List;

/**
 * Created by android2 on 16/6/16.
 */
public class GridAdapterDiscover extends BaseAdapter {
     Context mContext;
    private List<GridBean> gridBeen;

    public GridAdapterDiscover(List<GridBean> myList, Context c ) {
        this.gridBeen = myList;
        this.mContext=c;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gridBeen.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GridBean gridBean = gridBeen.get(position);
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_discover_contents, null);
            TextView txt_name_age = (TextView) grid.findViewById(R.id.txt_name_age);
            TextView txt_place = (TextView) grid.findViewById(R.id.txt_place);
            ImageView imageView = (ImageView)grid.findViewById(R.id.img_user);
            txt_name_age.setText(gridBean.getStr_id());
            txt_place.setText(gridBean.getStr_username());
            Log.e("NN",gridBean.getStr_userimage());
           // imageView.setBackgroundResource(gridBean.getStr_userimage());

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}