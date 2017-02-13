package com.example.easy_marry.Horoscope;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easy_marry.Adapter.GalleryVideoAdapter;
import com.example.easy_marry.Bean.ImageItem;
import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Interface.GalleryVideoInterface;
import com.example.easy_marry.R;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.imageCache.ImageLoader;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by android2 on 12/12/16.
 */
public class ViewHoroscope extends Activity implements GalleryVideoInterface {
   // RecyclerView rv_horoView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    String[] strGalleryURL;
    ArrayList<ImageItem> alBitmap;
    Context ctx;
    ImageLoader imgLoader;
    String str_user_id,str_horoImages;
    GeneralData gD;
    ArrayList<String> alGalleryPath;
    GridView gd_view_horo;
    IntentFilter filter1;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_horoscope_new);

       // rv_horoView = (RecyclerView)findViewById(R.id.rv_view_horo);
        //gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        //rv_horoView.setLayoutManager(gaggeredGridLayoutManager);
        gd_view_horo = (GridView)findViewById(R.id.gv_horo_list);
        img_back=(ImageView)findViewById(R.id.img_back);
        ctx = this;
        imgLoader = new ImageLoader(ctx);
        gD=new GeneralData(ctx);
        str_user_id =  getIntent().getStringExtra("user_id");
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  void viewHoroscope() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GeneralData.LOCAL_IP + "horoscope_image.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            Log.i("HH", "strResp : " + s);
                            ArrayList<ListDrawerBean> beanArrayList = new ArrayList<ListDrawerBean>();

                            JSONObject jsobj = new JSONObject(s);

                            Log.i("HH", "strResp : " + s);
                            if (jsobj.getString("status").equalsIgnoreCase("success")) {
                                gd_view_horo.setVisibility(View.VISIBLE);
                                // String[] strImagesAr = strGallery.split(",");

                             /*   JSONObject jsobj_resp = jsobj.getJSONObject("Response");
                              String  str_horo_images = jsobj_resp.getString("horoscope_image");
                                strGalleryURL = jsobj_resp.getString("horoscope_image").split(",");
                                alBitmap = new ArrayList<ImageItem>();


                                for (int i = 0; i < strGalleryURL.length; i++) {
                                    if (strGalleryURL[i].length() > 0) {

                                        try {

                                            String strGalPath = "";
                                            ImageItem ImgI = new ImageItem();
                                            Log.i("JJ", "images-" + strGalleryURL[i]);

                                            strGalPath = GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[i];
                                            // alGalleryPath.add(GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[i]);
                                            ImgI.setImage(strGalleryURL[i]);

                                            Log.i("JJ", "strGalPath : " + strGalPath);
                                            ImgI.setBmpImage(imgLoader.getBitmap(strGalPath));
                                            Log.i("JJ", "albitmap : " + alBitmap);
                                            alBitmap.add(ImgI);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                */

                              JSONObject jsobj_resp = jsobj.getJSONObject("Response");
                                String str_horo_images = jsobj_resp.getString("horoscope_image");
                                str_horoImages=str_horo_images;
                                Log.e("GH", "horo images-->" + str_horo_images);

                                try {
                                    strGalleryURL = jsobj_resp.getString("horoscope_image").split(",");
                                    Log.i("SSS", "galleryimg : " + strGalleryURL);
                                    alGalleryPath = new ArrayList<String>();
                                    for (int j = 0; j < strGalleryURL.length; j++) {
                                        alGalleryPath.add(GeneralData.LOCAL_IP_IMAGE + "horoscope/" + strGalleryURL[j]);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                alBitmap = new ArrayList<ImageItem>();
                                for (int k = 0; k < alGalleryPath.size(); k++) {
                                    Log.e("OO", "images--" + String.valueOf(strGalleryURL[k]));
                                    try {
                                        URL url = new URL(alGalleryPath.get(k));
                                        final String strImageURl = alGalleryPath.get(k);
                                        Log.e("OO", "images path--" + strImageURl);
                                     //   Bitmap myBitmapAgain = decodeBase64(strImageURl);
                                        ImageItem imageItems = new ImageItem();
                                     imageItems.setImage(strImageURl);
                                       // imageItems.setBmpImage(myBitmapAgain);
                                        alBitmap.add(imageItems);
                                        Log.e("OO", "images--" + String.valueOf(alBitmap));
                                        GalleryVideoAdapter rcAdapter = new GalleryVideoAdapter(ctx, alBitmap,(GalleryVideoInterface)ctx);
                                        gd_view_horo.setAdapter(rcAdapter);
                                     //   gaggeredGridLayoutManager.scrollToPosition(1);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                loading.dismiss();
                                //Toast.makeText(AddHoroscope.this, "Horoscope Added!!!", Toast.LENGTH_SHORT).show();
                            } else if (jsobj.getString("status").equalsIgnoreCase("failure")) {
                                gd_view_horo.setVisibility(View.GONE);
                                loading.dismiss();
                                //Toast.makeText(AddHoroscope.this, "Horoscope Added Failed!!!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        //Toast.makeText(UserRegSixAddPhoto.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //String image = getStringImage(bmp);

                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                Log.e("N_horo", "userid" + str_user_id);
                params.put("userid", str_user_id);

                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        //return alBitmap;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
            context = GeneralData.context;
            if (isConnected) {
                viewHoroscope();

            } else {

                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setCancelable(false);

                View itemView1 = LayoutInflater.from(ctx)
                        .inflate(R.layout.custom_basic_det_popup, null);
                final AlertDialog altDialog = builder.create();
                altDialog.setView(itemView1);
                altDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView txt_span_text = (TextView) itemView1.findViewById(R.id.span_text);
                Button btn_ok = (Button) itemView1.findViewById(R.id.btn_ok);
                txt_span_text.setText("Unable to connect with internet. Please check your internet connection and try again");
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altDialog.dismiss();
                        finish();
                    }
                });
                altDialog.show();

            }
        }
    };

    @Override
    public void getImageFull(String str_imge_path, String str_image_pos) {

        Intent i = new Intent(ViewHoroscope.this,ViewGalleryImage.class);
        i.putExtra("image_path",str_imge_path);
        i.putExtra("images",str_horoImages);
        i.putExtra("image_pos",str_image_pos);
        startActivity(i);

    }
}
