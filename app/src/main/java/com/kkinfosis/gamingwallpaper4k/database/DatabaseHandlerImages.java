package com.kkinfosis.gamingwallpaper4k.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kkinfosis.gamingwallpaper4k.models.ItemWallpaperByCategory;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerImages extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_mw_images";
    private static final int DATABASE_VERSION = 2;
    private static final String KEY_CATELIST_CATID = "catelistid";
    private static final String KEY_CATELIST_IMAGEURL = "catelistimage";
    private static final String KEY_CATELIST_NAME = "catelistname";
    private static final String KEY_ID = "id";
    private static final String TABLE_NAME = "tbl_images";

    public enum DatabaseManager {
        INSTANCE;
        
        private SQLiteDatabase db;
        DatabaseHandlerImages dbHelper;
        private boolean isDbClosed;

        public void init(Context context) {
            this.dbHelper = new DatabaseHandlerImages(context);
            if (this.isDbClosed) {
                this.isDbClosed = false;
                this.db = this.dbHelper.getWritableDatabase();
            }
        }

        public boolean isDatabaseClosed() {
            return this.isDbClosed;
        }

        public void closeDatabase() {
            if (!this.isDbClosed && this.db != null) {
                this.isDbClosed = true;
                this.db.close();
                this.dbHelper.close();
            }
        }
    }

    public DatabaseHandlerImages(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE tbl_images(id INTEGER PRIMARY KEY,catelistname TEXT,catelistimage TEXT,catelistid TEXT,UNIQUE(catelistimage))");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_images");
        onCreate(sQLiteDatabase);
    }

    public void AddtoFavoriteCateList(ItemWallpaperByCategory itemWallpaperByCategory) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CATELIST_NAME, itemWallpaperByCategory.getItemCategoryName());
        contentValues.put(KEY_CATELIST_IMAGEURL, itemWallpaperByCategory.getItemImageurl());
        contentValues.put(KEY_CATELIST_CATID, itemWallpaperByCategory.getItemCatId());
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public List<ItemWallpaperByCategory> getAllData() {
        List<ItemWallpaperByCategory> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM tbl_images ORDER BY id ASC", null);
        if (rawQuery.moveToFirst()) {
            do {
                ItemWallpaperByCategory itemWallpaperByCategory = new ItemWallpaperByCategory();
                itemWallpaperByCategory.setItemCategoryName(rawQuery.getString(1));
                itemWallpaperByCategory.setItemImageurl(rawQuery.getString(2));
                itemWallpaperByCategory.setItemCatId(rawQuery.getString(3));
                arrayList.add(itemWallpaperByCategory);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public List<ItemWallpaperByCategory> getFavRow(String str) {
        List<ItemWallpaperByCategory> arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM tbl_images WHERE catelistid=");
        stringBuilder.append(str);
        Cursor rawQuery = getWritableDatabase().rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            do {
                ItemWallpaperByCategory itemWallpaperByCategory = new ItemWallpaperByCategory();
                itemWallpaperByCategory.setItemCategoryName(rawQuery.getString(1));
                itemWallpaperByCategory.setItemImageurl(rawQuery.getString(2));
                itemWallpaperByCategory.setItemCatId(rawQuery.getString(3));
                arrayList.add(itemWallpaperByCategory);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public void RemoveFav(ItemWallpaperByCategory itemWallpaperByCategory) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, "catelistid = ?", new String[]{String.valueOf(itemWallpaperByCategory.getItemCatId())});
        writableDatabase.close();
    }
}
