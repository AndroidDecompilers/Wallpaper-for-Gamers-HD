package com.kkinfosis.gamingwallpaper4k.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kkinfosis.gamingwallpaper4k.models.CategoryItem;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerCategory extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_mw_category";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_CAT_ID = "cid";
    private static final String KEY_CAT_IMAGE = "category_image";
    private static final String KEY_CAT_NAME = "category_name";
    private static final String KEY_ID = "id";
    private static final String TABLE_NAME = "tbl_category";

    public enum DatabaseManager {
        INSTANCE;
        
        private SQLiteDatabase db;
        DatabaseHandlerCategory dbHelper;
        private boolean isDbClosed;

        public void init(Context context) {
            this.dbHelper = new DatabaseHandlerCategory(context);
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

    public DatabaseHandlerCategory(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE tbl_category(id INTEGER PRIMARY KEY,cid TEXT,category_name TEXT,category_image TEXT,UNIQUE(cid))");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_category");
        onCreate(sQLiteDatabase);
    }

    public void AddtoFavoriteCate(CategoryItem categoryItem) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", categoryItem.getCategoryId());
        contentValues.put("category_name", categoryItem.getCategoryName());
        contentValues.put("category_image", categoryItem.getCategoryImage());
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public List<CategoryItem> getAllData() {
        List<CategoryItem> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM tbl_category ORDER BY id ASC", null);
        if (rawQuery.moveToFirst()) {
            do {
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.setCategoryId(rawQuery.getString(1));
                categoryItem.setCategoryName(rawQuery.getString(2));
                categoryItem.setCategoryImage(rawQuery.getString(3));
                arrayList.add(categoryItem);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public List<CategoryItem> getFavRow(String str) {
        List<CategoryItem> arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  * FROM tbl_category WHERE cid=");
        stringBuilder.append(str);
        Cursor rawQuery = getWritableDatabase().rawQuery(stringBuilder.toString(), null);
        if (rawQuery.moveToFirst()) {
            do {
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.setCategoryId(rawQuery.getString(1));
                categoryItem.setCategoryName(rawQuery.getString(2));
                categoryItem.setCategoryImage(rawQuery.getString(3));
                arrayList.add(categoryItem);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public void RemoveFav(CategoryItem categoryItem) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, "cid = ?", new String[]{String.valueOf(categoryItem.getCategoryId())});
        writableDatabase.close();
    }
}
