package com.example.easy_marry;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.easy_marry.Bean.ListDrawerBean;
import com.example.easy_marry.Bean.RecyclerBean;
import com.example.easy_marry.genericclasses.GeneralData;

import java.util.ArrayList;

/**
 * Created by android2 on 12/12/16.
 */
public class MyApplication extends Application {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        Log.e("EFA", "on1->"+String.valueOf(GeneralData.ycount));
    }

}