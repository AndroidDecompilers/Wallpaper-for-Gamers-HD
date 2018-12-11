package com.kkinfosis.gamingwallpaper4k.activities;

import android.app.Notification;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ServiceForWallpaper extends Service {
    private static final String ANDROID_CHANNEL_ID = "1";
    public static int time;
    ArrayList<File> arrayList = new ArrayList();
    Bitmap bitmap = null;
    private long delay;
    public ArrayList<File> fileArrayList;
    Handler h = new Handler();
    int i = 0;
    Intent intent;
    File photoDirectory;
    String root = Environment.getExternalStorageDirectory().toString();
    int size;
    Timer timer = new Timer();

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public ServiceForWallpaper() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.root);
        stringBuilder.append("/kkinfosisGaming");
        this.photoDirectory = new File(stringBuilder.toString());
        this.delay = 0;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        this.intent = intent;
        this.fileArrayList = new ArrayList();
        if (this.photoDirectory.exists()) {
            File[] listFiles = this.photoDirectory.listFiles();
            if (listFiles.length != 0) {
                for (File add : listFiles) {
                    this.fileArrayList.add(add);
                }
            }
        }
        final Runnable anonymousClass1 = new Runnable() {
            public void run() {
                try {
                    ServiceForWallpaper.this.arrayList = ServiceForWallpaper.this.fileArrayList;
                    synchronized (this) {
                        if (ServiceForWallpaper.this.i == ServiceForWallpaper.this.arrayList.size()) {
                            ServiceForWallpaper.this.i = 0;
                        }
                        ArrayList arrayList = ServiceForWallpaper.this.arrayList;
                        ServiceForWallpaper serviceForWallpaper = ServiceForWallpaper.this;
                        int i = serviceForWallpaper.i;
                        serviceForWallpaper.i = i + 1;
                        File file = (File) arrayList.get(i);
                        try {
                            ServiceForWallpaper.this.bitmap = Media.getBitmap(ServiceForWallpaper.this.getContentResolver(), Uri.fromFile(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            WallpaperManager.getInstance(ServiceForWallpaper.this.getApplicationContext()).setBitmap(ServiceForWallpaper.this.bitmap);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        };
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ServiceForWallpaper.this.h.post(anonymousClass1);
            }
        }, this.delay, (long) time);
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onTaskRemoved(Intent intent) {
        this.delay = (long) TimerActivity.time;
        Intent intent2 = new Intent(getApplicationContext(), getClass());
        intent2.setPackage(getPackageName());
        startService(intent2);
        startForeground(1, new Notification());
        super.onTaskRemoved(intent);
    }
}
