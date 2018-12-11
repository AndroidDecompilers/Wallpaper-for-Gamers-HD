package com.kkinfosis.gamingwallpaper4k.models;

public class RecentItem {
    private String CategoryName;
    private String ImageUrl;

    public RecentItem(String str, String str2) {
        this.CategoryName = str;
        this.ImageUrl = str2;
    }

    public RecentItem() {

    }

    public String getCategoryName() {
        return this.CategoryName;
    }

    public void setCategoryName(String str) {
        this.CategoryName = str;
    }

    public String getImageurl() {
        return this.ImageUrl;
    }

    public void setImageurl(String str) {
        this.ImageUrl = str;
    }
}
