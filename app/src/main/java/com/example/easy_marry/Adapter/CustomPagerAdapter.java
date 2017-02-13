package com.example.easy_marry.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.R;
import com.example.easy_marry.imageCache.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by android2 on 2/9/16.
 */
public class CustomPagerAdapter extends PagerAdapter {


    private Context mContext;
    private List<ImageItem> itemList;
int fCount=0;
    ImageLoader imvLoader;

    public CustomPagerAdapter(Context context, List<ImageItem> itemList) {
        mContext = context;
        this.itemList = itemList;
        imvLoader = new ImageLoader(mContext);

    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        ImageItem customPagerEnum = itemList.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_gallery_image_content, collection, false);
        final ImageView imV = (ImageView) layout.findViewById(R.id.gallery_image);
        imvLoader.DisplayImage(itemList.get(position).getImage(), imV);

        imV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              //  Toast.makeText(mContext, "HIIII",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        imV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View itemView1;
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                itemView1 = LayoutInflater.from(mContext)
                        .inflate(R.layout.incomplete_reg_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                TextView txt_title=(TextView)itemView1.findViewById(R.id.txt_title);
                TextView txt_content=(TextView)itemView1.findViewById(R.id.txt_content);
                TextView txt_yes=(TextView)itemView1.findViewById(R.id.txt_yes);
                TextView txt_no=(TextView)itemView1.findViewById(R.id.txt_exit);
                txt_title.setText("Save Image");
                txt_content.setText("Do you want to save this image?");
                txt_yes.setText("Yes");
                txt_no.setText("No");
                txt_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BitmapDrawable drawable = (BitmapDrawable) imV.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();

                        File root = new File(Environment.getExternalStorageDirectory()
                                + File.separator + "EasyMarryHoroscopes" + File.separator);
                        root.mkdirs();

                      /*  File image = new File(Environment.getExternalStorageDirectory()
                                + File.separator + "/test" + String.valueOf(fCount++) +".jpg" );*/

                        File image=  new File(root,String.valueOf("("+fCount++ +")")+"horo.jpg");


                        boolean success = false;

                        // Encode the file as a PNG image.
                        FileOutputStream outStream;
                        try {

                            outStream = new FileOutputStream(image);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

                            outStream.flush();
                            outStream.close();
                            success = true;
                            if (success) {
                                Toast.makeText(mContext, "Image saved..",
                                        Toast.LENGTH_LONG).show();
                                altDialog.dismiss();
                            } else {
                                Toast.makeText(mContext,
                                        "Error during image saving", Toast.LENGTH_LONG).show();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                txt_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                    }
                });
                altDialog.show();




                return true;
            }
        });
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(position);
    }

}
