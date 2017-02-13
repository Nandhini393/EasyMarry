package com.example.easy_marry;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by android3 on 3/11/16.
 */
public class Daly_Recmmendation extends Activity {
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ProgressBar mProgressBar;
    private TextView textViewShowTime; // will show the time
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        textViewShowTime = (TextView) findViewById(R.id.tvTimeCount);


        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        Daly_Recmmendation.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int time = 5;
                //totalTimeCountInMilliseconds = 60 * time * 1000;
                totalTimeCountInMilliseconds = 24 * 60 * 60;

                timeBlinkInMilliseconds = 30 * 1000;
                startTimer();

            }


        });


    }

    //loading screen
    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setProgress((int) (leftTimeInMilliseconds / 1000));
                textViewShowTime.setTextAppearance(getApplicationContext(),
                        R.style.normalColor);


                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style


                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

               /* textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));*/
                textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60) + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);
            }

        }.start();

    }
}
