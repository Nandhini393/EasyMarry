package com.example.easy_marry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_marry.genericclasses.GeneralData;
import com.example.easy_marry.swibetabs.Matches;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by android2 on 7/11/16.
 */
public class DailyRecommSkip extends Activity {
    TextView txt_span;
    LinearLayout ll_go_matches;
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
    ImageView img_back;
    int str_remain_min;
    GeneralData gD;
    Context ctx;
    int str_remain_min1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_recomm_skip);
        txt_span=(TextView)findViewById(R.id.txt_dspan);
        ll_go_matches=(LinearLayout)findViewById(R.id.ll_matching);
        String str_span_text = "View your next set of Daily Recommendation In:";
img_back=(ImageView)findViewById(R.id.menu);
        ctx=this;
        gD= new GeneralData(ctx);

        Log.e("LOI_","remain sec to min"+ String.valueOf(str_remain_min));
        SpannableString spannableString1 = new SpannableString(str_span_text);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#fb7b09")), 21, 43, 0);
        txt_span.setText(spannableString1);
        ll_go_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyRecommSkip.this, Matches.class));
                finish();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyRecommSkip.this, Matches.class));
                finish();
            }
        });

        textViewShowTime = (TextView) findViewById(R.id.tvTimeCount);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);



   /* final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //String str_current_time=new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
                Calendar calander = Calendar.getInstance();
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
                timeBlinkInMilliseconds = 30 * 1000;

                someHandler.postDelayed(this, 1000);
                startTimer();
            }
        },10);*/
    /* final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //String str_current_time=new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
                Calendar calander = Calendar.getInstance();
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
                someHandler.postDelayed(this, 1000);

            }
        },10);*/

      DailyRecommSkip.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DailyRecommSkip.this, "Hii**", Toast.LENGTH_SHORT).show();
                Calendar calander = Calendar.getInstance();
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
                timeBlinkInMilliseconds = 30 * 1000;
                startTimer();
            }
        });

    }

    private void startTimer() {
        Toast.makeText(DailyRecommSkip.this, "Hii", Toast.LENGTH_SHORT).show();
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;


                //i++;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setMax(60);
               // mProgressBar.setProgress((int)leftTimeInMilliseconds / 1000);
                mProgressBar.setProgress(Integer.parseInt(String.format("%02d", seconds % 60)));

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

                // nand hide
              /* textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60) + ":" + String.format("%02d", seconds % 60));*/

                textViewShowTime.setText(String.format("%02d", seconds / 3600)
                        + ":" + String.format("%02d", (seconds % 3600) / 60));


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
