package com.kkinfosis.gamingwallpaper4k.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.adapters.PremiumWalllpaperAdpter;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerFavorite;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerFavorite.DatabaseManager;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentFavorite;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentRecent;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentRecentRandom;
import com.kkinfosis.gamingwallpaper4k.models.RecentItem;
import com.kkinfosis.gamingwallpaper4k.models.Pojo;
import com.kkinfosis.gamingwallpaper4k.utilities.SharedPrefrenceHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

@SuppressLint("WrongConstant")
public class NewActivitySlideImage extends AppCompatActivity implements SensorEventListener {
    private boolean checkImage = false;
    public DatabaseHandlerFavorite databaseHandlerFavorite;
    private DatabaseManager databaseManager;
    Handler handler;
    String image_cat_name;
    String image_url;
    private long lastUpdate;
    List<RecentItem> listRecentItem;
    private Menu menu;
    DisplayImageOptions options;
    int position;
    Runnable runnable;
    ImageButton save;
    private SensorManager sensorManager;
    ImageButton set_as_wallpaper;
    ImageButton share;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    Toolbar toolbar;
    int total_images;
    ViewPager viewPager;

    public class SaveImageJPG extends AsyncTask<String, String, String> {
        Bitmap bmImg = null;
        private Context context;
        File file;
        URL myFileUrl;
        private ProgressDialog pDialog;

        public SaveImageJPG(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pDialog = new ProgressDialog(this.context);
            this.pDialog.setMessage(NewActivitySlideImage.this.getResources().getString(R.string.downloading));
            this.pDialog.setIndeterminate(false);
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }

        protected String doInBackground(String... strArr) {
            String path;
            Exception e;
            try {
                this.myFileUrl = new URL(strArr[0]);
                HttpsURLConnection httpURLConnection = (HttpsURLConnection) this.myFileUrl.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setSSLSocketFactory(MyApplication.getInstance().getSslContext().getSocketFactory());
                httpURLConnection.connect();
                this.bmImg = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                path = this.myFileUrl.getPath();
                path = path.substring(path.lastIndexOf(47) + 1);
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(externalStorageDirectory.getAbsolutePath());
                stringBuilder.append("/");
                stringBuilder.append(NewActivitySlideImage.this.getResources().getString(R.string.saved_folder_name));
                stringBuilder.append("/");
                File file = new File(stringBuilder.toString());
                file.mkdirs();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Image__");
                stringBuilder2.append(path);
                this.file = new File(file, stringBuilder2.toString());
                if (this.file.exists()) {
                    path = "exists";
                    try {
                        Log.e("SAVEDORNOT", "EXISTS");
                    } catch (Exception e3) {
                        e = e3;
                    }
                } else {
                    path = "saved";
                    Log.e("SAVEDORNOT", "NOEXISTS");
                }
                OutputStream fileOutputStream = new FileOutputStream(this.file);
                this.bmImg.compress(CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                MediaScannerConnection.scanFile(NewActivitySlideImage.this, new String[]{this.file.toString()}, null, new OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
            } catch (Exception e4) {
                e = e4;
                path = null;
                e.printStackTrace();
                return path;
            }
            return path;
        }

        protected void onPostExecute(String str) {
            if (str.equals("saved")) {
                Toast.makeText(NewActivitySlideImage.this.getApplicationContext(), NewActivitySlideImage.this.getResources().getString(R.string.saved_successfully), 0).show();
            } else if (str.equals("exists")) {
                Toast.makeText(NewActivitySlideImage.this.getApplicationContext(), "Image already saved", 0).show();
            }
            this.pDialog.dismiss();
        }
    }

    public class SaveImagePNG extends AsyncTask<String, String, String> {
        Bitmap bmImg = null;
        private Context context;
        File file;
        URL myFileUrl;
        private ProgressDialog pDialog;

        public SaveImagePNG(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pDialog = new ProgressDialog(this.context);
            this.pDialog.setMessage(NewActivitySlideImage.this.getResources().getString(R.string.downloading));
            this.pDialog.setIndeterminate(false);
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }

        protected String doInBackground(String... strArr) {
            String path;
            Exception e;
            try {
                this.myFileUrl = new URL(strArr[0]);
                HttpsURLConnection httpURLConnection = (HttpsURLConnection) this.myFileUrl.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setSSLSocketFactory(MyApplication.getInstance().getSslContext().getSocketFactory());
                httpURLConnection.connect();
                this.bmImg = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                path = this.myFileUrl.getPath();
                path = path.substring(path.lastIndexOf(47) + 1);
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(externalStorageDirectory.getAbsolutePath());
                stringBuilder.append("/");
                stringBuilder.append(NewActivitySlideImage.this.getResources().getString(R.string.saved_folder_name));
                stringBuilder.append("/");
                File file = new File(stringBuilder.toString());
                file.mkdirs();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Image__");
                stringBuilder2.append(path);
                this.file = new File(file, stringBuilder2.toString());
                if (this.file.exists()) {
                    path = "exists";
                    try {
                        Log.e("SAVEDORNOT", "EXISTS");
                    } catch (Exception e3) {
                        e = e3;
                    }
                } else {
                    path = "saved";
                    Log.e("SAVEDORNOT", "NOEXISTS");
                }
                OutputStream fileOutputStream = new FileOutputStream(this.file);
                this.bmImg.compress(CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                MediaScannerConnection.scanFile(NewActivitySlideImage.this, new String[]{this.file.toString()}, null, new OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
            } catch (Exception e4) {
                e = e4;
                path = null;
                e.printStackTrace();
                return path;
            }
            return path;
        }

        protected void onPostExecute(String str) {
            if (str.equals("saved")) {
                Toast.makeText(NewActivitySlideImage.this.getApplicationContext(), NewActivitySlideImage.this.getResources().getString(R.string.saved_successfully), 0).show();
            } else if (str.equals("exists")) {
                Toast.makeText(NewActivitySlideImage.this.getApplicationContext(), "Image already saved", 0).show();
            }
            this.pDialog.dismiss();
        }
    }

    private class SetImagesAs extends AsyncTask<String, Void, Bitmap> {
        private SetImagesAs() {
        }

        /* synthetic */ SetImagesAs(NewActivitySlideImage newActivitySlideImage, Object anonymousClass1) {
            this();
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... strArr) {
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(strArr[0]).openConnection();
                httpsURLConnection.setSSLSocketFactory(MyApplication.getInstance().getSslContext().getSocketFactory());
                return BitmapFactory.decodeStream(httpsURLConnection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            File file = new File(NewActivitySlideImage.this.getExternalCacheDir(), "toshare.jpg");
            if (VERSION.SDK_INT < 23) {
                try {
                    WallpaperManager.getInstance(NewActivitySlideImage.this.getApplicationContext()).setBitmap(bitmap);
                    Toast.makeText(NewActivitySlideImage.this, "Wallpaper set scucessfully", 0).show();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            Intent intent = new Intent("android.intent.action.ATTACH_DATA");
            Uri uriForFile = FileProvider.getUriForFile(NewActivitySlideImage.this, "com.kkinfosis.gamingwallpaper4k.provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                OutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(CompressFormat.JPEG, 60, fileOutputStream);
                fileOutputStream.close();
                intent.setDataAndType(uriForFile, "image/jpg");
                intent.putExtra("mimeType", "image/jpg");
                NewActivitySlideImage.this.startActivityForResult(Intent.createChooser(intent, "Set As"), 200);
            } catch (Exception unused) {

            }
        }
    }

    public class ShareTask extends AsyncTask<String, String, String> {
        Bitmap bmImg = null;
        private Context context;
        File file;
        String image_url;
        URL myFileUrl;
        String myFileUrl1;
        private ProgressDialog pDialog;

        public ShareTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pDialog = new ProgressDialog(this.context);
            this.pDialog.setMessage(NewActivitySlideImage.this.getResources().getString(R.string.please_wait));
            this.pDialog.setIndeterminate(false);
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }

        protected String doInBackground(String... strArr) {
            try {
                this.myFileUrl = new URL(strArr[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) this.myFileUrl.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                this.bmImg = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String path = this.myFileUrl.getPath();
                path = path.substring(path.lastIndexOf(47) + 1);
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(externalStorageDirectory.getAbsolutePath());
                stringBuilder.append("/");
                stringBuilder.append(NewActivitySlideImage.this.getResources().getString(R.string.saved_folder_name));
                stringBuilder.append("/");
                File file = new File(stringBuilder.toString());
                file.mkdirs();
                this.file = new File(file, path);
                OutputStream fileOutputStream = new FileOutputStream(this.file);
                this.bmImg.compress(CompressFormat.JPEG, 75, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String str) {
            StrictMode.setVmPolicy(new Builder().build());
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file://");
            stringBuilder.append(this.file.getAbsolutePath());
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(stringBuilder.toString()));
            NewActivitySlideImage.this.startActivity(Intent.createChooser(intent, "Share Image"));
            this.pDialog.dismiss();
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;


        public ImagePagerAdapter() {
            this.inflater = NewActivitySlideImage.this.getLayoutInflater();
        }

        public int getCount() {
            return NewActivitySlideImage.this.listRecentItem.size();
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = this.inflater.inflate(R.layout.view_pager_item_crop, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.catnammmme)).setText(((RecentItem) NewActivitySlideImage.this.listRecentItem.get(i)).getCategoryName());
            final ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
            final ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.loading);
            Glide.with(NewActivitySlideImage.this)
                    .load("https://appsapionline.com/GamingWallpaper/wallpapers/" + ((RecentItem) NewActivitySlideImage.this.listRecentItem.get(i)).getImageurl().replace(" ", "%20"))
                    .placeholder((int) R.drawable.ic_slider)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(8);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(8);
                            imageView.setImageDrawable(resource);
                            imageView.setVisibility(View.VISIBLE);
                            return true;
                        }
                    }).preload();

            viewGroup.addView(inflate, 0);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_slider_image);
        if (getIntent().hasExtra("migratedFrom") && getIntent().getStringExtra("migratedFrom").equals("premium")) {
            this.listRecentItem = PremiumWalllpaperAdpter.selectedItem;
        }
        if (getIntent().getExtras().getString("class") != null)
            if (getIntent().getExtras().getString("class").equals(FragmentRecent.class.getSimpleName())) {
                listRecentItem = FragmentRecent.arrayListItemCategorys;
            } else if (getIntent().getExtras().getString("class").equals(FragmentRecentRandom.class.getSimpleName())) {
                listRecentItem = FragmentRecentRandom.listRecentItem;
            } else if (getIntent().getExtras().getString("class").equals(FragmentFavorite.class.getSimpleName())) {
                listRecentItem = FragmentFavorite.listItem;
            }
        this.sharedPrefrenceHelper = new SharedPrefrenceHelper(this);
        this.position = getIntent().getIntExtra("POSITION_ID", 0);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.databaseHandlerFavorite = new DatabaseHandlerFavorite(this);
        this.databaseManager = DatabaseManager.INSTANCE;
        this.databaseManager.init(getApplicationContext());
        this.options = new DisplayImageOptions.Builder().showImageForEmptyUri((int) R.drawable.ic_thumbnail).showImageOnFail((int) R.drawable.ic_thumbnail).resetViewBeforeLoading(true).cacheOnDisc(false).imageScaleType(ImageScaleType.NONE).bitmapConfig(Config.RGB_565).considerExifParams(false).build();
        bundle = new Bundle();


        this.set_as_wallpaper = (ImageButton) findViewById(R.id.fab_set_as_wallpaper);
        this.share = (ImageButton) findViewById(R.id.fab_share);
        this.save = (ImageButton) findViewById(R.id.fab_save);
        this.set_as_wallpaper.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NewActivitySlideImage.this.position = NewActivitySlideImage.this.viewPager.getCurrentItem();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                stringBuilder.append(((RecentItem) NewActivitySlideImage.this.listRecentItem.get(NewActivitySlideImage.this.position)).getImageurl().replace(" ", "%20"));
                String stringBuilder2 = stringBuilder.toString();
                new SetImagesAs(NewActivitySlideImage.this, null).execute(new String[]{stringBuilder2});
            }
        });
        this.share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NewActivitySlideImage.this.position = NewActivitySlideImage.this.viewPager.getCurrentItem();
                NewActivitySlideImage.this.image_url = ((RecentItem) NewActivitySlideImage.this.listRecentItem.get(NewActivitySlideImage.this.position)).getImageurl();
                List favRow = NewActivitySlideImage.this.databaseHandlerFavorite.getFavRow(NewActivitySlideImage.this.image_url);
                if (favRow.size() == 0) {
                    NewActivitySlideImage.this.addtoFav(NewActivitySlideImage.this.position);
                } else if (((Pojo) favRow.get(0)).getImageurl().equals(NewActivitySlideImage.this.image_url)) {
                    NewActivitySlideImage.this.RemoveFav(NewActivitySlideImage.this.position);
                }
            }
        });
        this.save.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NewActivitySlideImage.this.position = NewActivitySlideImage.this.viewPager.getCurrentItem();
                String[] strArr;
                StringBuilder stringBuilder;
                if (((RecentItem) NewActivitySlideImage.this.listRecentItem.get(NewActivitySlideImage.this.position)).getImageurl().endsWith(".png")) {
                    SaveImagePNG saveImagePNG = new SaveImagePNG(NewActivitySlideImage.this);
                    strArr = new String[1];
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                    stringBuilder.append(((RecentItem) NewActivitySlideImage.this.listRecentItem.get(NewActivitySlideImage.this.position)).getImageurl());
                    strArr[0] = stringBuilder.toString();
                    saveImagePNG.execute(strArr);
                    return;
                }
                SaveImageJPG saveImageJPG = new SaveImageJPG(NewActivitySlideImage.this);
                strArr = new String[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                stringBuilder.append(((RecentItem) NewActivitySlideImage.this.listRecentItem.get(NewActivitySlideImage.this.position)).getImageurl());
                strArr[0] = stringBuilder.toString();
                saveImageJPG.execute(strArr);
            }
        });
        this.position = getIntent().getIntExtra("POSITION_ID", 0);
        this.total_images = this.listRecentItem.size() - 1;
        this.viewPager = (ViewPager) findViewById(R.id.image_slider);
        this.handler = new Handler();
        this.viewPager.setAdapter(new ImagePagerAdapter());
        this.viewPager.setCurrentItem(this.position);
        this.sensorManager = (SensorManager) getSystemService("sensor");
        this.lastUpdate = System.currentTimeMillis();
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                i = NewActivitySlideImage.this.viewPager.getCurrentItem();
                NewActivitySlideImage.this.image_url = ((RecentItem) NewActivitySlideImage.this.listRecentItem.get(i)).getImageurl();
                List favRow = NewActivitySlideImage.this.databaseHandlerFavorite.getFavRow(NewActivitySlideImage.this.image_url);
                if (favRow.size() == 0) {
                    NewActivitySlideImage.this.menu.getItem(0).setIcon(ContextCompat.getDrawable(NewActivitySlideImage.this.getApplicationContext(), R.drawable.ic_toolbar_unlove));
                    NewActivitySlideImage.this.share.setBackgroundResource(R.drawable.ic_toolbar_unlove);
                } else if (((Pojo) favRow.get(0)).getImageurl().equals(NewActivitySlideImage.this.image_url)) {
                    NewActivitySlideImage.this.menu.getItem(0).setIcon(ContextCompat.getDrawable(NewActivitySlideImage.this.getApplicationContext(), R.drawable.ic_toolbar_love));
                    NewActivitySlideImage.this.share.setBackgroundResource(R.drawable.ic_toolbar_love);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_images, menu);
        this.menu = menu;
        FirstFav();
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 16908332) {
            switch (itemId) {
                case R.id.menu_fav:
                    setVisible(false);
                    this.position = this.viewPager.getCurrentItem();
                    this.image_url = ((RecentItem) this.listRecentItem.get(this.position)).getImageurl();
                    List favRow = this.databaseHandlerFavorite.getFavRow(this.image_url);
                    if (favRow.size() == 0) {
                        addtoFav(this.position);
                    } else if (((Pojo) favRow.get(0)).getImageurl().equals(this.image_url)) {
                        RemoveFav(this.position);
                    }
                    return true;
                case R.id.menu_save:
                    this.position = this.viewPager.getCurrentItem();
                    String[] strArr;
                    StringBuilder stringBuilder;
                    if (((RecentItem) this.listRecentItem.get(this.position)).getImageurl().endsWith(".png")) {
                        SaveImagePNG saveImagePNG = new SaveImagePNG(this);
                        strArr = new String[1];
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                        stringBuilder.append(((RecentItem) this.listRecentItem.get(this.position)).getImageurl());
                        strArr[0] = stringBuilder.toString();
                        saveImagePNG.execute(strArr);
                    } else {
                        SaveImageJPG saveImageJPG = new SaveImageJPG(this);
                        strArr = new String[1];
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                        stringBuilder.append(((RecentItem) this.listRecentItem.get(this.position)).getImageurl());
                        strArr[0] = stringBuilder.toString();
                        saveImageJPG.execute(strArr);
                    }
                    return true;
                case R.id.menu_set_as_wallpaper:
                    this.position = this.viewPager.getCurrentItem();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                    stringBuilder2.append(((RecentItem) this.listRecentItem.get(this.position)).getImageurl().replace(" ", "%20"));
                    String stringBuilder3 = stringBuilder2.toString();
                    new SetImagesAs(this, null).execute(new String[]{stringBuilder3});
                    return true;
                default:
                    return super.onOptionsItemSelected(menuItem);
            }
        }
        onBackPressed();
        return true;
    }

    public void addtoFav(int i) {
        this.image_cat_name = ((RecentItem) this.listRecentItem.get(i)).getCategoryName();
        this.image_url = ((RecentItem) this.listRecentItem.get(i)).getImageurl();
        this.databaseHandlerFavorite.AddtoFavorite(new Pojo(this.image_cat_name, this.image_url));
        Toast.makeText(getApplicationContext(), "Added to Favorite", 0).show();
        this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_love));
        this.share.setBackgroundResource(R.drawable.ic_toolbar_love);
    }

    public void RemoveFav(int i) {
        this.image_url = ((RecentItem) this.listRecentItem.get(i)).getImageurl();
        this.databaseHandlerFavorite.RemoveFav(new Pojo(this.image_url));
        Toast.makeText(getApplicationContext(), "Removed from Favorite", 0).show();
        this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_unlove));
        this.share.setBackgroundResource(R.drawable.ic_toolbar_unlove);
    }

    public void FirstFav() {
        String imageurl = ((RecentItem) this.listRecentItem.get(this.viewPager.getCurrentItem())).getImageurl();
        List favRow = this.databaseHandlerFavorite.getFavRow(imageurl);
        if (favRow.size() == 0) {
            this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_unlove));
            this.share.setBackgroundResource(R.drawable.ic_toolbar_unlove);
        } else if (((Pojo) favRow.get(0)).getImageurl().equals(imageurl)) {
            this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_love));
            this.share.setBackgroundResource(R.drawable.ic_toolbar_love);
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1) {
            getAccelerometer(sensorEvent);
        }
    }

    private void getAccelerometer(SensorEvent sensorEvent) {
        float[] fArr = sensorEvent.values;
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        f = (((f * f) + (f2 * f2)) + (f3 * f3)) / 96.17039f;
        long currentTimeMillis = System.currentTimeMillis();
        if (f >= 2.0f && currentTimeMillis - this.lastUpdate >= 200) {
            this.lastUpdate = currentTimeMillis;
            if (this.checkImage) {
                this.position = this.viewPager.getCurrentItem();
                this.viewPager.setCurrentItem(this.position);
            } else {
                this.position = this.viewPager.getCurrentItem();
                this.position++;
                if (this.position == this.total_images) {
                    this.position = this.total_images;
                }
                this.viewPager.setCurrentItem(this.position);
            }
            this.checkImage ^= true;
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.databaseManager == null) {
            this.databaseManager = DatabaseManager.INSTANCE;
            this.databaseManager.init(getApplicationContext());
        } else if (this.databaseManager.isDatabaseClosed()) {
            this.databaseManager.init(getApplicationContext());
        }
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(1), 3);
    }

    protected void onPause() {
        super.onPause();
        if (!this.databaseManager.isDatabaseClosed()) {
            this.databaseManager.closeDatabase();
        }
        this.sensorManager.unregisterListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.runnable);
        this.sensorManager.unregisterListener(this);
        if (this.databaseManager != null) {
            this.databaseManager.closeDatabase();
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }


    public int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
    }
}
