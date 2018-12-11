package com.kkinfosis.gamingwallpaper4k.models;

public class Pojo {
    private String CategoryName;
    private String ImageUrl;
    private int id;

    public Pojo(String str) {
        this.ImageUrl = str;
    }

    public Pojo(String str, String str2) {
        this.CategoryName = str;
        this.ImageUrl = str2;
    }

    public Pojo() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
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
