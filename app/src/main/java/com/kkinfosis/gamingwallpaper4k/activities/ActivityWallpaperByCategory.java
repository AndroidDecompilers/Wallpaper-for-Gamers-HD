package com.kkinfosis.gamingwallpaper4k.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.media.ExifInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.adapters.AdapterWallpaperByCategory;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerImages;
import com.kkinfosis.gamingwallpaper4k.json.JsonConfig;
import com.kkinfosis.gamingwallpaper4k.json.JsonUtils;
import com.kkinfosis.gamingwallpaper4k.models.ItemWallpaperByCategory;
import com.kkinfosis.gamingwallpaper4k.utilities.ItemOffsetDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("WrongConstant")
public class ActivityWallpaperByCategory extends AppCompatActivity {
    AdapterWallpaperByCategory adapterWallpaperByCategory;
    List<ItemWallpaperByCategory> arrayItemCategory;
    int counter = 1;
    public DatabaseHandlerImages databaseHandlerImages;
    ArrayList<String> image_cat_name;
    ArrayList<String> image_id;
    List<ItemWallpaperByCategory> itemWallpaperByCategories;
    ArrayList<String> list_image;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    String[] str_image_cat_name;
    String[] str_image_id;
    String[] str_list_image;
    SwipeRefreshLayout swipeRefreshLayout = null;
    JsonUtils util;

    public interface ClickListener {
        void onClick(View view, int i);

        void onLongClick(View view, int i);
    }

    private class MyTask extends AsyncTask<String, Void, String> {
        private MyTask() {
        }

        /* synthetic */ MyTask(ActivityWallpaperByCategory activityWallpaperByCategory, Object anonymousClass1) {
            this();
        }


        protected void onPreExecute() {
            super.onPreExecute();
            ActivityWallpaperByCategory.this.progressBar.setVisibility(0);
        }

        protected String doInBackground(String... strArr) {
            return JsonUtils.getJSONString(strArr[0]);
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            ActivityWallpaperByCategory.this.progressBar.setVisibility(8);
            int i = 0;
            if (str == null || str.length() == 0) {
                Toast.makeText(ActivityWallpaperByCategory.this.getApplicationContext(), ActivityWallpaperByCategory.this.getResources().getString(R.string.network_error), 0).show();
                ActivityWallpaperByCategory.this.finish();
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    ItemWallpaperByCategory itemWallpaperByCategory = new ItemWallpaperByCategory();
//                    ActivityWallpaperByCategory.this.databaseHandlerImages.AddtoFavoriteCateList(new ItemWallpaperByCategory(jSONObject.getString(JsonConfig.CATEGORY_ITEM_CATNAME), jSONObject.getString(JsonConfig.CATEGORY_ITEM_IMAGEURL), jSONObject.getString("cid")));
                    itemWallpaperByCategory.setItemCategoryName(jSONObject.getString("category_name"));
                    itemWallpaperByCategory.setItemImageurl(jSONObject.getString("image"));
                    itemWallpaperByCategory.setItemCatId(jSONObject.getString("cid"));
                    ActivityWallpaperByCategory.this.itemWallpaperByCategories.add(itemWallpaperByCategory);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            while (i < ActivityWallpaperByCategory.this.itemWallpaperByCategories.size()) {
                ItemWallpaperByCategory itemWallpaperByCategory2 = (ItemWallpaperByCategory) ActivityWallpaperByCategory.this.itemWallpaperByCategories.get(i);
                ActivityWallpaperByCategory.this.list_image.add(itemWallpaperByCategory2.getItemImageurl());
                ActivityWallpaperByCategory.this.str_list_image = (String[]) ActivityWallpaperByCategory.this.list_image.toArray(ActivityWallpaperByCategory.this.str_list_image);
                ActivityWallpaperByCategory.this.image_cat_name.add(itemWallpaperByCategory2.getItemCategoryName());
                ActivityWallpaperByCategory.this.str_image_cat_name = (String[]) ActivityWallpaperByCategory.this.image_cat_name.toArray(ActivityWallpaperByCategory.this.str_image_cat_name);
                ActivityWallpaperByCategory.this.image_id.add(itemWallpaperByCategory2.getItemCatId());
                ActivityWallpaperByCategory.this.str_image_id = (String[]) ActivityWallpaperByCategory.this.image_id.toArray(ActivityWallpaperByCategory.this.str_image_id);
                i++;
            }
            ActivityWallpaperByCategory.this.setAdapterToListView();
        }
    }

    class RecyclerTouchListener implements OnItemTouchListener {
        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            this.gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

                public void onLongPress(MotionEvent motionEvent) {
                    View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (findChildViewUnder != null && clickListener != null) {
                        clickListener.onLongClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
                    }
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View findChildViewUnder = ActivityWallpaperByCategory.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (!(findChildViewUnder == null || this.clickListener == null || !this.gestureDetector.onTouchEvent(motionEvent))) {
                this.clickListener.onClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
            }
            return false;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_wallpaper_by_category);
        Log.d("Farman", ExifInterface.GPS_MEASUREMENT_2D);
        setTitle(JsonConfig.CATEGORY_TITLE);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int i = 0;
        this.databaseHandlerImages = new DatabaseHandlerImages(this);
        this.arrayItemCategory = new ArrayList();
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue, R.color.red);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        this.recyclerView.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset));
        this.itemWallpaperByCategories = new ArrayList();
        this.list_image = new ArrayList();
        this.image_cat_name = new ArrayList();
        this.image_id = new ArrayList();
        this.str_list_image = new String[this.list_image.size()];
        this.str_image_cat_name = new String[this.image_cat_name.size()];
        this.str_image_id = new String[this.image_id.size()];
        this.util = new JsonUtils(getApplicationContext());
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), this.recyclerView, new ClickListener() {
            public void onClick(View view, int i) {
                Intent intent = new Intent(ActivityWallpaperByCategory.this.getApplicationContext(), ActivitySlideImage.class);
                intent.putExtra("POSITION_ID", i);
                intent.putExtra("IMAGE_ARRAY", ActivityWallpaperByCategory.this.str_list_image);
                intent.putExtra("IMAGE_CATNAME", ActivityWallpaperByCategory.this.str_image_cat_name);
                intent.putExtra("ITEMID", ActivityWallpaperByCategory.this.str_image_id);
                ActivityWallpaperByCategory.this.startActivity(intent);
            }

            public void onLongClick(View view, int i) {
                Intent intent = new Intent(ActivityWallpaperByCategory.this.getApplicationContext(), ActivitySlideImage.class);
                intent.putExtra("POSITION_ID", i);
                intent.putExtra("IMAGE_ARRAY", ActivityWallpaperByCategory.this.str_list_image);
                intent.putExtra("IMAGE_CATNAME", ActivityWallpaperByCategory.this.str_image_cat_name);
                intent.putExtra("ITEMID", ActivityWallpaperByCategory.this.str_image_id);
                ActivityWallpaperByCategory.this.startActivity(intent);
            }
        }));
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (JsonUtils.isNetworkAvailable(ActivityWallpaperByCategory.this)) {
                            ActivityWallpaperByCategory.this.swipeRefreshLayout.setRefreshing(false);
                            ActivityWallpaperByCategory.this.clearData();
                            MyTask myTask = new MyTask(ActivityWallpaperByCategory.this, null);
                            String[] strArr = new String[1];
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("https://appsapionline.com/wallpapers/catbyid/");
                            stringBuilder.append(JsonConfig.CATEGORY_ID);
                            strArr[0] = stringBuilder.toString();
                            myTask.execute(strArr);
                            return;
                        }
                        ActivityWallpaperByCategory.this.swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(ActivityWallpaperByCategory.this.getApplicationContext(), ActivityWallpaperByCategory.this.getResources().getString(R.string.refresh_alert), 0).show();
                    }
                }, 3000);
            }
        });
        if (JsonUtils.isNetworkAvailable(this)) {
            MyTask myTask = new MyTask(this, null);
            String[] strArr = new String[1];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://appsapionline.com/wallpapers/catbyid/");
            stringBuilder.append(JsonConfig.CATEGORY_ID);
            strArr[0] = stringBuilder.toString();
            myTask.execute(strArr);
            return;
        }
        this.itemWallpaperByCategories = this.databaseHandlerImages.getFavRow(JsonConfig.CATEGORY_ID);
        if (this.itemWallpaperByCategories.size() == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_first_load), 0).show();
        }
        setAdapterToListView();
        while (i < this.itemWallpaperByCategories.size()) {
            ItemWallpaperByCategory itemWallpaperByCategory = (ItemWallpaperByCategory) this.itemWallpaperByCategories.get(i);
            this.list_image.add(itemWallpaperByCategory.getItemImageurl());
            this.str_list_image = (String[]) this.list_image.toArray(this.str_list_image);
            this.image_cat_name.add(itemWallpaperByCategory.getItemCategoryName());
            this.str_image_cat_name = (String[]) this.image_cat_name.toArray(this.str_image_cat_name);
            this.image_id.add(itemWallpaperByCategory.getItemCatId());
            this.str_image_id = (String[]) this.image_id.toArray(this.str_image_id);
            i++;
        }
    }

    public void clearData() {
        int size = this.itemWallpaperByCategories.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.itemWallpaperByCategories.remove(0);
            }
            this.adapterWallpaperByCategory.notifyItemRangeRemoved(0, size);
        }
    }

    public void setAdapterToListView() {
        this.adapterWallpaperByCategory = new AdapterWallpaperByCategory(this, this.itemWallpaperByCategories);
        this.recyclerView.setAdapter(this.adapterWallpaperByCategory);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }
}
