package com.kkinfosis.gamingwallpaper4k.firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    private void sendRegistrationToServer(String str) {
    }

    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Refreshed token: ");
        stringBuilder.append(token);
        Log.e(str, stringBuilder.toString());
    }
}
