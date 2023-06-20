package com.android.simanika.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.simanika.MainActivity;
import com.android.simanika.R;
import com.android.simanika.Services.SharedPreference.Preferences;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class BackgroundNotificationService extends Service {

    private static final String CHANNEL_ID = "BackgroundNotificationChannel";
    public static int NOTIFICATION_ID = 1;
    private static final String PUSHER_API_KEY = "5d8e462327809b06a3fe";
    private static final String PUSHER_CHANNEL_NAME = "simanika-channel";
    private static final String PUSHER_EVENT_NAME = "simanika-event";

    private Pusher pusher;

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        connectToPusher();

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.loginlogo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager = getSystemService(NotificationManager.class);

        startForeground(NOTIFICATION_ID-1, notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE).build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnectFromPusher();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Background Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void connectToPusher() {
        PusherOptions options = new PusherOptions().setCluster("ap1");
        pusher = new Pusher(PUSHER_API_KEY, options);
        Channel channel = pusher.subscribe(PUSHER_CHANNEL_NAME);

        channel.bind(PUSHER_EVENT_NAME, new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                handlePusherMessage(event.getData());
            }

            @Override
            public void onError(String message, Exception e) {
                SubscriptionEventListener.super.onError(message, e);
            }
        });

        pusher.connect();
    }

    private void disconnectFromPusher() {
        if (pusher != null) {
            pusher.unsubscribe(PUSHER_CHANNEL_NAME);
            pusher.disconnect();
        }
    }

    private void handlePusherMessage(String message) {
        Log.d("Pusher", "Received message: " + message);

        JSONObject jsonObject = null;
        String user_id = null;
        try {
            jsonObject = new JSONObject(message);
            user_id = jsonObject.getString("user_id");
            message = jsonObject.getString("message");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (Integer.parseInt(Preferences.getLoggedInUserStatus(getBaseContext())) != 1) {
            if (user_id.equals(Preferences.getLoggedInUserId(getBaseContext()))) {
                showNotification(message);
            }
        } else if (Preferences.getLoggedInUserDivisiId(getBaseContext()) != null && !Preferences.getLoggedInUserDivisiId(getBaseContext()).equals("1")) {
            if (user_id.isEmpty() || user_id.equals(Preferences.getLoggedInUserId(getBaseContext()))) {
                showNotification(message);
            }
        } else {
            showNotification(message);
        }
    }

    private void showNotification(String message) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Update konten notifikasi
        notificationBuilder.setContentTitle("Notifikasi Baru")
                .setContentText(message)
                .setContentIntent(pendingIntent);

        // Munculkan notifikasi
        Notification notification = notificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        NOTIFICATION_ID++;
    }
}
