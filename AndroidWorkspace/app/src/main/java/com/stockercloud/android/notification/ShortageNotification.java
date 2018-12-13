package com.stockercloud.android.notification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.stockercloud.android.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ShortageNotification {

    private static Timer timer; // only need one instance of a timer
    private static TimerTask timerTask;

    private final Activity activity;

    private static final int NOTIFICATION_ID = 1; // currently only supports notifications for shortages
    private static final String NOTIFICATION_CHANNEL_ID = "Stocker Cloud Shortage";

    public ShortageNotification(Activity activity)
    {
        this.activity = activity;
        createNotificationChannel(); // must create a channel before sending notifications
    }

    public void start()
    {
        if (timer == null)
        {
            startTimer();
        }
    }

    private void startTimer()
    {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run()
            {
                retrieveShortages();
            }
        };
        timer.schedule(timerTask, 0, 60000);
    }

    private void retrieveShortages()
    {
        AndroidNetworking.get("https://qj7z8d44vk.execute-api.us-east-2.amazonaws.com/production/getshortages")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray shortages = response.getJSONArray("shortages");

                            if (shortages.length() > 0)
                            {
                                sendNotification(shortages.length());
                            }
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void sendNotification(int shortages)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Shortages")
                .setContentText("There are " + shortages + " shortages")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(activity);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    @TargetApi(28)
    private void createNotificationChannel()
    {
        // used channel ID for both ID and name in the channel constructor
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Shortages from Stocker Cloud");
        NotificationManager manager = activity.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}
