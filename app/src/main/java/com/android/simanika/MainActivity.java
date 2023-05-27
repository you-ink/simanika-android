package com.android.simanika;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.simanika.MenuFragment.ArticleFragment;
import com.android.simanika.MenuFragment.HomeFragment;
import com.android.simanika.MenuFragment.NotificationFragment;
import com.android.simanika.NoInternet.CheckInternet;
import com.android.simanika.Notification.PusherNotificationManager;
import com.android.simanika.Services.SharedPreference.Preferences;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // number of selected tab. we have 4 tabs, so value must be between 1-4, and the default tab is number 1.
    private int selectedTab = 1;
    BroadcastReceiver broadcastReceiver = null;

    private Pusher pusher;
    private Channel channel;
    private PusherNotificationManager notificationManager;

    private static String API_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Pusher
        PusherOptions options = new PusherOptions().setCluster("ap1");
        pusher = new Pusher("5d8e462327809b06a3fe", options);

        // Inisialisasi PusherNotificationManager
        notificationManager = new PusherNotificationManager(this);

        // Terhubung ke Pusher dan langganan saluran notifikasi
        pusher.connect();
        channel = pusher.subscribe("simanika-channel");
        channel.bind("simanika-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                handleNotification(event.getData());
            }

            @Override
            public void onError(String message, Exception e) {
                SubscriptionEventListener.super.onError(message, e);
            }
        });

        // check internet connection
        CheckInternet.showNoInternetDialog(MainActivity.this);
        broadcastReceiver = new CheckInternet();
        internetStatus();

        checkPreferences();

        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout articleLayout = findViewById(R.id.articleLayout);
        final LinearLayout notificationLayout = findViewById(R.id.notificationLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView articleImage = findViewById(R.id.articleImage);
        final ImageView notificationImage = findViewById(R.id.notificationImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView articleTxt = findViewById(R.id.articleTxt);
        final TextView notificationTxt = findViewById(R.id.notificationTxt);
        final TextView profileTxt = findViewById(R.id.profileTxt);

        // set home layout by default
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if home is already selected or not
                if (selectedTab != 1){

                    // set home fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();

                    // unselect other tab except home tab
                    articleTxt.setVisibility(View.GONE);
                    notificationTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    articleImage.setImageResource(R.drawable.c30_article_icon);
                    notificationImage.setImageResource(R.drawable.c30_notification_icon);
                    profileImage.setImageResource(R.drawable.c30_person_icon);

                    articleLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select home tab
                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.active_home_icon);
                    homeLayout.setBackgroundResource(R.drawable.round_menu_button);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    // set 1st tab as selected tab
                    selectedTab = 1;
                }
            }
        });

        articleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if article is already selected or not
                if (selectedTab != 2){

                    // set article fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ArticleFragment.class, null)
                            .commit();

                    // unselect other tab except article tab
                    homeTxt.setVisibility(View.GONE);
                    notificationTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.c30_home_icon);
                    notificationImage.setImageResource(R.drawable.c30_notification_icon);
                    profileImage.setImageResource(R.drawable.c30_person_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select article tab
                    articleTxt.setVisibility(View.VISIBLE);
                    articleImage.setImageResource(R.drawable.active_article_icon);
                    articleLayout.setBackgroundResource(R.drawable.round_menu_button);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    articleLayout.startAnimation(scaleAnimation);

                    // set 2nd tab as selected tab
                    selectedTab = 2;
                }
            }
        });

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if notification is already selected or not
                if (selectedTab != 3){

                    // set notification fragment
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, NotificationFragment.class, null)
                            .commit();

                    // unselect other tab except notification tab
                    homeTxt.setVisibility(View.GONE);
                    articleTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.c30_home_icon);
                    articleImage.setImageResource(R.drawable.c30_article_icon);
                    profileImage.setImageResource(R.drawable.c30_person_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    articleLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select notification tab
                    notificationTxt.setVisibility(View.VISIBLE);
                    notificationImage.setImageResource(R.drawable.active_notification_icon);
                    notificationLayout.setBackgroundResource(R.drawable.round_menu_button);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    notificationLayout.startAnimation(scaleAnimation);

                    // set 3th tab as selected tab
                    selectedTab = 3;
                }
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if profile is already selected or not
                if (selectedTab != 4){

                    // set profile fragment
//                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
//                            .replace(R.id.fragmentContainer, ProfileFragment.class, null)
//                            .commit();

                    // unselect other tab except profile tab
                    homeTxt.setVisibility(View.GONE);
                    articleTxt.setVisibility(View.GONE);
                    notificationTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.c30_home_icon);
                    articleImage.setImageResource(R.drawable.c30_article_icon);
                    notificationImage.setImageResource(R.drawable.c30_notification_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    articleLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select profile tab
                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.active_person_icon);
                    profileLayout.setBackgroundResource(R.drawable.round_menu_button);

                    // create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    // set 4th tab as selected tab
                    selectedTab = 4;

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void internetStatus(){
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void checkPreferences(){
        if (!Preferences.getLoggedInStatus(getBaseContext())) {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
        }
    }

    private void handleNotification(String data) {
        JSONObject jsonObject = null;
        String title = "Null";
        String message = "Null";
        try {
            jsonObject = new JSONObject(data);

            title = "Notifikasi Baru";
            message = jsonObject.getString("message");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Tampilkan notifikasi
        notificationManager.showNotification(title, message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        internetStatus();
        checkPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Putuskan koneksi dan batalkan langganan saat aktivitas dihancurkan
        pusher.disconnect();
        pusher.unsubscribe("simanika-channel");
    }
}