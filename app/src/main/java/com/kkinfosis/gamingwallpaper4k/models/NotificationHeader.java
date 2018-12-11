package com.kkinfosis.gamingwallpaper4k.models;

public class NotificationHeader {
    String date;
    String notificationTitle;

    public NotificationHeader(String str, String str2) {
        this.notificationTitle = str;
        this.date = str2;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }
}
