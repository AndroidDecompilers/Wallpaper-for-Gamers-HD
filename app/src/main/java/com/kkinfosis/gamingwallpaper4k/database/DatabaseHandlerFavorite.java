package com.kkinfosis.gamingwallpaper4k.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kkinfosis.gamingwallpaper4k.models.RecentItem;
import com.kkinfosis.gamingwallpaper4k.models.Pojo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerFavorite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_mw_favorite";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE_CATNAME = "imagecatname";
    private static final String KEY_IMAGE_URL = "imageurl";
    private static final String TABLE_NAME = "tbl_favorite";

    public enum DatabaseManager {
        INSTANCE;

        private SQLiteDatabase db;
        DatabaseHandlerFavorite dbHelper;
        private boolean isDbClosed;

        public void init(Context context) {
            this.dbHelper = new DatabaseHandlerFavorite(context);
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

    public DatabaseHandlerFavorite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE tbl_favorite(id INTEGER PRIMARY KEY,imagecatname TEXT,imageurl TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_favorite");
        onCreate(sQLiteDatabase);
    }

    public void AddtoFavorite(Pojo pojo) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IMAGE_CATNAME, pojo.getCategoryName());
        contentValues.put(KEY_IMAGE_URL, pojo.getImageurl());
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public List<RecentItem> getAllData() {
        List<RecentItem> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM tbl_favorite ORDER BY id DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                RecentItem pojo = new RecentItem();
                pojo.setCategoryName(rawQuery.getString(1));
                pojo.setImageurl(rawQuery.getString(2));
                arrayList.add(pojo);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public List<Pojo> getFavRow(String str) {
        List<Pojo> arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM tbl_favorite WHERE imageurl='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        Cursor rawQuery = getWritableDatabase().rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            do {
                Pojo pojo = new Pojo();
                pojo.setId(Integer.parseInt(rawQuery.getString(0)));
                pojo.setCategoryName(rawQuery.getString(1));
                pojo.setImageurl(rawQuery.getString(2));
                arrayList.add(pojo);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public void RemoveFav(Pojo pojo) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, "imageurl = ?", new String[]{String.valueOf(pojo.getImageurl())});
        writableDatabase.close();
    }
}
