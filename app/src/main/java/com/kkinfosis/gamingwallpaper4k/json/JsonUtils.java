package com.kkinfosis.gamingwallpaper4k.json;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.kkinfosis.gamingwallpaper4k.activities.MyApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JsonUtils {
    Context context;

    public JsonUtils(Context context) {
        this.context = context;
    }


    public static String getJSONString(String urls) {
        try {
            URL url = new URL(urls);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            httpURLConnection.setSSLSocketFactory(MyApplication.getInstance().getSslContext().getSocketFactory());
            String read;
            StringBuilder reader = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while ((read = bufferedReader.readLine()) != null) {
                reader.append(read);
            }
            return reader.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService("connectivity");
        if (VERSION.SDK_INT >= 21) {
            for (Network networkInfo : connectivityManager.getAllNetworks()) {
                if (connectivityManager.getNetworkInfo(networkInfo).getState().equals(State.CONNECTED)) {
                    return true;
                }
            }
        } else if (connectivityManager != null) {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                for (NetworkInfo networkInfo2 : allNetworkInfo) {
                    if (networkInfo2.getState() == State.CONNECTED) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("NETWORKNAME: ");
                        stringBuilder.append(networkInfo2.getTypeName());
                        Log.d("Network", stringBuilder.toString());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getScreenWidth() {
        Display defaultDisplay = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        point.x = defaultDisplay.getWidth();
        point.y = defaultDisplay.getHeight();
        return point.x;
    }
}
