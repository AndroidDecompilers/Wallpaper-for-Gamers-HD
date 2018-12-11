package com.kkinfosis.gamingwallpaper4k.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.kkinfosis.gamingwallpaper4k.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kkinfosis.gamingwallpaper4k.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "Material Wallpaper";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From: ");
        stringBuilder.append(remoteMessage.getFrom());
        Log.e(str, stringBuilder.toString());
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Notification Message Body: ");
        stringBuilder.append(remoteMessage.getNotification().getBody());
        Log.e(str, stringBuilder.toString());
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String str, String str2) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(0, new Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)).build());
    }
}
