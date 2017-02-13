package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.easy_marry.genericclasses.GeneralData;

/**
 * Created by android2 on 11/10/16.
 */
public class TestClass extends Activity {
    Context context;
    GeneralData gD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_loading_popup);
        context=this;

    }

}
