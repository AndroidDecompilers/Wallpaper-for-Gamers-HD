package com.kkinfosis.gamingwallpaper4k.models;

public class ItemWallpaperByCategory {
    private String ItemCatId;
    private String ItemCategoryName;
    private String ItemImageUrl;

    public ItemWallpaperByCategory() {
    }



    public ItemWallpaperByCategory(String ItemCategoryName, String ItemImageUrl, String ItemCatId) {
        this.ItemCategoryName = ItemCategoryName;
        this.ItemImageUrl = ItemImageUrl;
        this.ItemCatId = ItemCatId;
    }

    public String getItemCategoryName() {
        return this.ItemCategoryName;
    }

    public void setItemCategoryName(String str) {
        this.ItemCategoryName = str;
    }

    public String getItemImageurl() {
        return this.ItemImageUrl;
    }

    public void setItemImageurl(String str) {
        this.ItemImageUrl = str;
    }

    public String getItemCatId() {
        return this.ItemCatId;
    }

    public void setItemCatId(String str) {
        this.ItemCatId = str;
    }
}
