package com.example.easy_marry.genericclasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.HomeScreen;
import com.example.easy_marry.Splash_Screen;

/**
 * Created by android2 on 2/9/16.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
        context = GeneralData.context;
        GeneralData gd = new GeneralData(context);
        if (isConnected) {
            Toast.makeText(context, "The device is connected to the internet ", Toast.LENGTH_SHORT).show();
            Log.i("NET", "connected" + isConnected);

            if (GeneralData.context instanceof Splash_Screen) {
                Thread timerThread = new Thread() {
                    public void run() {
                        try {

                            sleep(2500);
                            Intent intent = new Intent(GeneralData.context, HomeScreen.class);
                            GeneralData.context.startActivity(intent);
                            ((Activity) GeneralData.context).finish();

                        } catch (Exception e) {

                        }
                    }
                };
                timerThread.start();
            } /*else {

                try {
                    Class c2 = Class.forName(GeneralData.context.getClass().getName().toString());
                    Intent i = new Intent(GeneralData.context, c2);
                    GeneralData.context.startActivity(i);
                    ((Activity) GeneralData.context).finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }*/


        } else {
            /*Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE);

            snackbar.show();*/
            // GeneralData.showAlertDialog(context,"No Internet Connection. Do you wish to turn it on?");

        }
    }
}
