package com.example.easy_marry.genericclasses;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.R;

import java.util.ArrayList;

/**
 * Created by android2 on 14/9/16.
 */
public class GeneralData {
    public static Context context;
    public static int nChildPostion;
    public static String str_name, str_id, str_complete_profile_level, str_image, str_membership;
    public static String SERVER_IP = "http://www.easy-marry.com/api/";

    public static String LOCAL_IP = "http://www.easy-marry.com/api/";
    public static String LOCAL_IP_IMAGE = "http://www.easy-marry.com/api/";
    public static int ncount = 0;
    public static int n_count = 0;
    public static int filter_count = 0;
    public static int filter_count1 = 0;
    public AlertDialog altDialog;

    public static int ycount=0;


    public SharedPreferences prefs;
    public GeneralData(Context con) {
        context = con;
        prefs = con.getSharedPreferences("Registerprefs", Context.MODE_PRIVATE);

    }

    public static ArrayList<ListDrawerBean> someVariable;

    public static ArrayList<ListDrawerBean> getSomeVariable() {
        return someVariable;
    }

    public static void setSomeVariable(ArrayList<ListDrawerBean> someVariable1) {
        someVariable = someVariable1;
    }

    public boolean isConnectingToInternet() {

        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    isConnected = true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network", "NETWORKNAME: " + anInfo.getTypeName());
                            isConnected = true;
                        }
                    }
                }
            }
        }

        Log.i("HHJ", "IsConected : " + isConnected);

        return isConnected;
    }

    public void showAlertDialog(final Context context, String strTitle, String strContent) {
        View itemView1;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        itemView1 = LayoutInflater.from(context)
                .inflate(R.layout.progress_loading_popup, null);
       altDialog = builder.create();
        altDialog.setView(itemView1);
        ProgressBar pb=(ProgressBar)itemView1.findViewById(R.id.progressBar);
        pb.setIndeterminate(true);
        pb.getIndeterminateDrawable().setColorFilter(Color.parseColor("#639639"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        TextView txt_title=(TextView)itemView1.findViewById(R.id.txt_title);
        TextView txt_content=(TextView)itemView1.findViewById(R.id.txt_content);
        txt_title.setText(strTitle);
        txt_content.setText(strContent);
        altDialog.show();
    }
}
