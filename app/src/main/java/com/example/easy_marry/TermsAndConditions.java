package com.example.easy_marry;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by android2 on 20/10/16.
 */
public class TermsAndConditions extends Activity {
    WebView web_terms;
    ImageView img_back;
    IntentFilter filter1;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions);
        filter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter1);
        web_terms=(WebView)findViewById(R.id.web_terms_and_conditions);
        img_back=(ImageView)findViewById(R.id.img_back);
       ctx=this;
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
            final boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
            if (isConnected) {
                Log.i("LK", "connected");
            //http://www.easy-marry.com/api/mail/terms_condition.html
                web_terms.loadUrl("http://192.168.0.62/api/mail/terms_condition.html");

            } else {
                Log.i("LK", "not connected");
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
}
