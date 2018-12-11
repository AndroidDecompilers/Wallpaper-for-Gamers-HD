package com.kkinfosis.gamingwallpaper4k.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kkinfosis.gamingwallpaper4k.models.RecentItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerRecent extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_mw_recent";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE_LATESTNAME = "imagelatestname";
    private static final String KEY_IMAGE_LATESTURL = "imageurl";
    private static final String TABLE_NAME = "tbl_recent";

    public enum DatabaseManager {
        INSTANCE;
        
        private SQLiteDatabase db;
        DatabaseHandlerRecent dbHelper;
        private boolean isDbClosed;

        public void init(Context context) {
            this.dbHelper = new DatabaseHandlerRecent(context);
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

    public DatabaseHandlerRecent(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE tbl_recent(id INTEGER PRIMARY KEY,imagelatestname TEXT,imageurl TEXT,UNIQUE(imageurl))");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_recent");
        onCreate(sQLiteDatabase);
    }

    public void AddtoFavoriteLatest(RecentItem recentItem) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IMAGE_LATESTNAME, recentItem.getCategoryName());
        contentValues.put(KEY_IMAGE_LATESTURL, recentItem.getImageurl());
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public List<RecentItem> getAllData() {
        List<RecentItem> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM tbl_recent ORDER BY id ASC", null);
        if (rawQuery.moveToFirst()) {
            do {
                RecentItem recentItem = new RecentItem();
                recentItem.setCategoryName(rawQuery.getString(1));
                recentItem.setImageurl(rawQuery.getString(2));
                arrayList.add(recentItem);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public List<RecentItem> getFavRow(String str) {
        List<RecentItem> arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM tbl_recent WHERE imageurl='");
        stringBuilder.append(str);
        stringBuilder.append("'");
        Cursor rawQuery = getWritableDatabase().rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            do {
                RecentItem recentItem = new RecentItem();
                recentItem.setCategoryName(rawQuery.getString(1));
                recentItem.setImageurl(rawQuery.getString(2));
                arrayList.add(recentItem);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public void RemoveFav(RecentItem recentItem) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, "imageurl = ?", new String[]{String.valueOf(recentItem.getImageurl())});
        writableDatabase.close();
    }
}
