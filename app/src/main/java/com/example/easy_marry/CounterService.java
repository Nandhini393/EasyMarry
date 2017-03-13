package com.example.easy_marry;

/**
 * Created by android2 on 12/3/17.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CounterService extends Service {

    private Intent intent;
    public static final String BROADCAST_ACTION = "com.javacodegeeks.android.androidtimerexample.MainActivity";

    private Handler handler = new Handler();
    private long initial_time;
    long timeInMilliseconds = 0L;
    int str_remain_min1;

    @Override
    public void onCreate() {
        super.onCreate();
        initial_time = SystemClock.uptimeMillis();



        intent = new Intent(BROADCAST_ACTION);
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second


    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 1000); // 1 seconds
        }
    };

    private void DisplayLoggingInfo() {

       timeInMilliseconds = SystemClock.uptimeMillis() - initial_time;

   /*     Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(calander.getTime());
        String [] current_time=time.split(":");
        int hour =Integer.parseInt(current_time[0]);
        int min =Integer.parseInt(current_time[1]);
        int seconds =Integer.parseInt(current_time[2]);
        Log.e("LOI","hour:"+ hour);
        Log.e("LOI","min:"+ min);
        Log.e("LOI","seconds:"+ seconds);
        int str_remain_min1=(hour *60) +( min ) + seconds;

        int minutes = 1440-str_remain_min1;
        Log.e("LOI","remain sec to min"+ String.valueOf(minutes));
        timeInMilliseconds = 60*minutes*1000;

*/

        //Log.d("Hello", "Time**" + timeInMilliseconds);

        int timer = (int) timeInMilliseconds/1000;


        //Log.d("Hello", "Time##" + timer);

        intent.putExtra("time", timer);
        sendBroadcast(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUI);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


}
