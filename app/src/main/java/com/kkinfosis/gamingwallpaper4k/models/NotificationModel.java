package com.kkinfosis.gamingwallpaper4k.models;

public class NotificationModel {
    String largeIcon;
    String notificationDate;
    int notificationId;
    String notificationMessage;
    String notificationTitle;

    public NotificationModel(String str, String str2, int i, String str3) {
        this.notificationTitle = str;
        this.notificationMessage = str2;
        this.notificationId = i;
        this.largeIcon = str3;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public String getNotificationMessage() {
        return this.notificationMessage;
    }

    public void setNotificationMessage(String str) {
        this.notificationMessage = str;
    }

    public int getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(int i) {
        this.notificationId = i;
    }

    public String getNotificationDate() {
        return this.notificationDate;
    }

    public void setNotificationDate(String str) {
        this.notificationDate = str;
    }

    public String getLargeIcon() {
        return this.largeIcon;
    }

    public void setLargeIcon(String str) {
        this.largeIcon = str;
    }
}
