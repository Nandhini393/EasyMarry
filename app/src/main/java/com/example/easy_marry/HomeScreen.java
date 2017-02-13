package com.example.easy_marry;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.easy_marry.Registration.UserRegOne;
import com.example.easy_marry.Registration.UserRegOneTest;
import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.swibetabs.Matches;
import com.squareup.picasso.Picasso;

/**
 * Created by android3 on 30/5/16.
 */
public class HomeScreen extends Activity {
    Button btn_sign_in, btn_sign_up;
    ImageView img_home_back;
    Context ctx;
    GeneralData gD;
    String str_login_name, str_login_pwd, str_screen_id;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_WIFI_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        ctx = this;
        btn_sign_in = (Button) findViewById(R.id.sign_in);
        img_home_back = (ImageView) findViewById(R.id.img_home_back);
        btn_sign_up = (Button) findViewById(R.id.sign_up);
        gD = new GeneralData(ctx);
        Picasso.with(ctx).load(R.drawable.home_back).into(img_home_back);
        str_login_name = gD.prefs.getString("login_uname", null);
        str_login_pwd = gD.prefs.getString("login_pword", null);
        if (str_login_name != null && str_login_pwd != null) {
            str_screen_id = gD.prefs.getString("screenid", null);
            Log.e("NNM:", "uname" + str_login_name);
            Log.e("NNM:", "pwrd" + str_login_pwd);
            Log.e("NNM:", "scren_id" + str_screen_id);
         // gD.showAlertDialog(ctx,"","Accessing Your Pofile");
            if (Integer.parseInt(str_screen_id) > 5) {
                Log.e("NNM:", "I'm that not tholla.......");
                GeneralData.n_count = 1;
               // gD.altDialog.dismiss();
                startActivity(new Intent(HomeScreen.this, Matches.class));
                finish();
            }
        }
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextpage = new Intent(HomeScreen.this, SignInPage.class);
                startActivity(nextpage);
                finish();

            }
        });
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextpage = new Intent(HomeScreen.this, UserRegOne.class);
                startActivity(nextpage);
                finish();

            }
        });
        if (GeneralData.ncount == 0) {
            verifyStoragePermissions(this);
            GeneralData.ncount = 1;
        }

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        int internetPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        int access_network_Permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE);

        int callPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int smsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);
        int wifiPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || internetPermission != PackageManager.PERMISSION_GRANTED ||
                access_network_Permission != PackageManager.PERMISSION_GRANTED ||
                callPermission != PackageManager.PERMISSION_GRANTED ||
                cameraPermission != PackageManager.PERMISSION_GRANTED ||
                smsPermission != PackageManager.PERMISSION_GRANTED ||
                wifiPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
