package com.kkinfosis.gamingwallpaper4k.models;

public class CategoryItem {
    private String CategoryId;
    private String CategoryImage;
    private String CategoryImageCount;
    private String CategoryName;

    public CategoryItem(String str, String str2, String str3) {
        this.CategoryId = str;
        this.CategoryName = str2;
        this.CategoryImage = str3;
    }

    public CategoryItem() {
    }

    public String getCategoryName() {
        return this.CategoryName;
    }

    public void setCategoryImage(String str) {
        this.CategoryImage = str;
    }

    public String getCategoryImage() {
        return this.CategoryImage;
    }

    public void setCategoryName(String str) {
        this.CategoryName = str;
    }

    public String getCategoryId() {
        return this.CategoryId;
    }

    public String getCategoryImageCount() {
        return this.CategoryImageCount;
    }

    public void setCategoryImageCount(String str) {
        this.CategoryImageCount = str;
    }

    public void setCategoryId(String str) {
        this.CategoryId = str;
    }
}
