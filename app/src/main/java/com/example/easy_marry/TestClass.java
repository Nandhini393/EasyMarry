package com.example.easy_marry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.genericclasses.GeneralData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by android2 on 11/10/16.
 */
public class TestClass extends Activity {

    private Button startButton;
    private Button pauseButton;
    private TextView timerValue;
    Intent intent;
    int str_remain_min1;

    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        timerValue = (TextView) findViewById(R.id.timerValue);

        startButton = (Button) findViewById(R.id.startButton);

        intent = new Intent(TestClass.this, CounterService.class);
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(CounterService.BROADCAST_ACTION));


        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                intent = new Intent(TestClass.this, CounterService.class);
                startService(intent);
                registerReceiver(broadcastReceiver, new IntentFilter(CounterService.BROADCAST_ACTION));
            }
        });

        pauseButton = (Button) findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                unregisterReceiver(broadcastReceiver);
                stopService(intent);
            }
        });

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
       int time = intent.getIntExtra("time", 0);
        Log.d("Hello", "Time " + time);

        int mins = time / 60;
        int secs = time % 60;
        timerValue.setText("" + mins + ":" + String.format("%02d", secs));


        //totalTimeCountInMilliseconds=time*60000;

       //startTimer();

      /*  Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calander.getTime());
        String [] current_time=time.split(":");
        int hour =Integer.parseInt(current_time[0]);
        int min =Integer.parseInt(current_time[1]);
        int seconds =Integer.parseInt(current_time[2]);
        Log.e("LOI","hour:"+ hour);
        Log.e("LOI","min:"+ min);
        Log.e("LOI","seconds:"+ seconds);
        str_remain_min1=(hour *60) +( min ) + seconds;
        int minutes = 1440-str_remain_min1;
        Log.e("LOI","remain sec to min"+ String.valueOf(minutes));
        totalTimeCountInMilliseconds = 60*minutes*1000;
        timeBlinkInMilliseconds = 30 * 1000;  */

        //startTimer();

    }
    private void startTimer() {

        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;


                //i++;
                //Setting the Progress Bar to decrease wih the timer
               // mProgressBar.setMax(60);
                // mProgressBar.setProgress((int)leftTimeInMilliseconds / 1000);
               // mProgressBar.setProgress(Integer.parseInt(String.format("%02d", seconds % 60)));

                timerValue.setTextAppearance(getApplicationContext(),
                        R.style.normalColor);


                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    timerValue.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style


                    if (blink) {

                        timerValue.setVisibility(View.VISIBLE);

                        // if blink is true, textview will be visible
                    } else
                    {
                        timerValue.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

               /* textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));*/

                // nand hide
              /* textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60) + ":" + String.format("%02d", seconds % 60));*/

                timerValue.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60));


                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                timerValue.setText("Time up!");
                timerValue.setVisibility(View.VISIBLE);

            }

        }.start();

    }

}