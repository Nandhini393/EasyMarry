package com.example.easy_marry.GCM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kundan on 10/22/2015.
 */
public class PushNotificationService extends GcmListenerService {

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        //createNotification(mTitle, push_msg);
        Log.i("MSG", "Message : " + message + "Data : " + data);
        processUserMessage("Utician", message);
    }

    /**
     * Processing user specific push message
     * It will be displayed with / without image in push notification tray
     */
    private void processUserMessage(String title, String data) {


        try {
            /*JSONObject datObj = new JSONObject(data);

            String imageUrl = datObj.getString("image");

            JSONObject mObj = datObj.getJSONObject("message");
            Message message = new Message();
            message.setMessage(mObj.getString("message"));
            message.setId(mObj.getString("message_id"));
            message.setCreatedAt(mObj.getString("created_at"));

            JSONObject uObj = datObj.getJSONObject("user");
            User user = new User();
            user.setId(uObj.getString("user_id"));
            user.setEmail(uObj.getString("email"));
            user.setName(uObj.getString("name"));
            message.setUser(user);*/

            // verifying whether the app is in background or foreground
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                // app is in foreground, broadcast the push message
              /*  Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("type", Config.PUSH_TYPE_USER);
                pushNotification.putExtra("message", data);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);*/

                // play notification sound
                //NotificationUtils notificationUtils = new NotificationUtils();
              //  notificationUtils.playNotificationSound();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());


           //nand     SplashActivity.sharedPreferences = getSharedPreferences("regid", Context.MODE_PRIVATE);

               // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                Intent resultIntent = null;
                /*if (SplashActivity.sharedPreferences.getString("utype", null).equalsIgnoreCase("user")) {
                    resultIntent = new Intent(getApplicationContext(), UserNotification.class);
                } else if (SplashActivity.sharedPreferences.getString("utype", null).equalsIgnoreCase("provider")) {
                    resultIntent = new Intent(getApplicationContext(), ProviderNotification.class);
                } else {
                    resultIntent = new Intent(getApplicationContext(), AdminNotification.class);
                }*/

                //nand

                if (resultIntent != null) {
                    showNotificationMessage(getApplicationContext(), title, data, timeStamp, resultIntent);
                }




               /* Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                showNotificationMessage(getApplicationContext(), title, data, timeStamp, resultIntent);*/

            } else {

                // app is in background. show the message in notification try
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

                // check for push notification image attachment

               /* if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, user.getName() + " : " + message.getMessage(), message.getCreatedAt(), resultIntent);
                } else {
                    // push notification contains image
                    // show it with the image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message.getMessage(), message.getCreatedAt(), resultIntent, imageUrl);
                }*/
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

                showNotificationMessage(getApplicationContext(), title, data, timeStamp, resultIntent);
            }
        } catch (Exception e) {
            Log.e("GCM", "json parsing error: " + e.getMessage());
//            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
