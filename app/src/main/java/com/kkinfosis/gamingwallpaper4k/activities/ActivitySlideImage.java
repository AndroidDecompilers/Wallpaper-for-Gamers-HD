package com.kkinfosis.gamingwallpaper4k.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.bumptech.glide.RequestManager;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerFavorite;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerFavorite.DatabaseManager;
import com.kkinfosis.gamingwallpaper4k.models.Pojo;
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
public class ActivitySlideImage extends AppCompatActivity implements SensorEventListener {
    boolean Play_Flag = false;
    private boolean checkImage = false;
    int count = 0;
    public DatabaseHandlerFavorite databaseHandlerFavorite;
    private DatabaseManager databaseManager;
    Handler handler;
    String image_cat_name;
    String image_url;
    Boolean issetaswallpaper = Boolean.valueOf(true);
    private long lastUpdate;
    private Menu menu;
    DisplayImageOptions options;
    int position;
    Runnable runnable;
    ImageButton save;
    private SensorManager sensorManager;
    ImageButton set_as_wallpaper;
    ImageButton share;
    String[] str_image;
    String[] str_image_cat_name;
    String[] str_image_id;
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
            this.pDialog.setMessage(ActivitySlideImage.this.getResources().getString(R.string.downloading));
            this.pDialog.setIndeterminate(false);
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }

        protected String doInBackground(String... strArr) {
            String path;
            Exception e;
            try {
                this.myFileUrl = new URL(strArr[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) this.myFileUrl.openConnection();
                httpURLConnection.setDoInput(true);
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
                stringBuilder.append(ActivitySlideImage.this.getResources().getString(R.string.saved_folder_name));
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
                MediaScannerConnection.scanFile(ActivitySlideImage.this, new String[]{this.file.toString()}, null, new OnScanCompletedListener() {
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
                Toast.makeText(ActivitySlideImage.this.getApplicationContext(), ActivitySlideImage.this.getResources().getString(R.string.saved_successfully), 0).show();
            } else if (str.equals("exists")) {
                Toast.makeText(ActivitySlideImage.this.getApplicationContext(), "Image already saved", 0).show();
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
            this.pDialog.setMessage(ActivitySlideImage.this.getResources().getString(R.string.downloading));
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
                httpURLConnection.setSSLSocketFactory(MyApplication.getInstance().getSslContext().getSocketFactory());
                httpURLConnection.setDoInput(true);
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
                stringBuilder.append(ActivitySlideImage.this.getResources().getString(R.string.saved_folder_name));
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
                MediaScannerConnection.scanFile(ActivitySlideImage.this, new String[]{this.file.toString()}, null, new OnScanCompletedListener() {
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
                Toast.makeText(ActivitySlideImage.this.getApplicationContext(), ActivitySlideImage.this.getResources().getString(R.string.saved_successfully), 0).show();
            } else if (str.equals("exists")) {
                Toast.makeText(ActivitySlideImage.this.getApplicationContext(), "Image already saved", 0).show();
            }
            this.pDialog.dismiss();
        }
    }

    private class SetImagesAs extends AsyncTask<String, Void, Bitmap> {
        private SetImagesAs() {
        }

        /* synthetic */ SetImagesAs(ActivitySlideImage activitySlideImage, Object anonymousClass1) {
            this();
        }

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivitySlideImage.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait ......");
            progressDialog.show();
        }

        protected Bitmap doInBackground(String... strArr) {
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(strArr[0]).openConnection();
                httpsURLConnection.setSSLSocketFactory(MyApplication.getInstance().getSslContext().getSocketFactory());
                httpsURLConnection.connect();
                return BitmapFactory.decodeStream(httpsURLConnection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bitmap.getByteCount());
            stringBuilder.append("");
            Log.d("sizeofbitmap", stringBuilder.toString());
            File file = new File(ActivitySlideImage.this.getExternalCacheDir(), "toshare.jpg");
            Uri uriForFile = FileProvider.getUriForFile(ActivitySlideImage.this, "com.kkinfosis.gamingwallpaper4k.provider", file);
            Intent intent = new Intent("android.intent.action.ATTACH_DATA");
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                OutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(CompressFormat.JPEG, 60, fileOutputStream);
                fileOutputStream.close();
                intent.setDataAndType(uriForFile, "image/jpg");
                intent.putExtra("mimeType", "image/jpg");
                ActivitySlideImage.this.startActivityForResult(Intent.createChooser(intent, "Set As"), 200);
            } catch (Exception unused) {
                Toast.makeText(ActivitySlideImage.this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
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
            this.pDialog.setMessage(ActivitySlideImage.this.getResources().getString(R.string.please_wait));
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
                stringBuilder.append(ActivitySlideImage.this.getResources().getString(R.string.saved_folder_name));
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
            ActivitySlideImage.this.startActivity(Intent.createChooser(intent, "Share Image"));
            this.pDialog.dismiss();
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private LayoutInflater inflater;

        public ImagePagerAdapter() {
            this.inflater = ActivitySlideImage.this.getLayoutInflater();
        }

        public int getCount() {
            return ActivitySlideImage.this.str_image.length;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = this.inflater.inflate(R.layout.view_pager_item_crop, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.catnammmme)).setText(ActivitySlideImage.this.str_image_cat_name[i]);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
            final ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.loading);
            RequestManager with = Glide.with(ActivitySlideImage.this);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
            stringBuilder.append(ActivitySlideImage.this.str_image[i].replace(" ", "%20"));
            with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_slider).into(imageView);
            progressBar.setVisibility(8);
            viewGroup.addView(inflate, 0);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    private void showInterstitialAd() {
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_slider_image);
        Log.d("RTL Mode", "Working in Normal Mode, RTL Mode is Disabled");
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.databaseHandlerFavorite = new DatabaseHandlerFavorite(this);
        this.databaseManager = DatabaseManager.INSTANCE;
        this.databaseManager.init(getApplicationContext());
        this.options = new DisplayImageOptions.Builder().showImageForEmptyUri((int) R.drawable.ic_slider).showImageOnFail((int) R.drawable.ic_slider).resetViewBeforeLoading(true).cacheOnDisc(false).imageScaleType(ImageScaleType.NONE).bitmapConfig(Config.RGB_565).considerExifParams(false).build();
        bundle = new Bundle();
        bundle.putString(Param.ITEM_ID, getResources().getString(R.string.analytics_item_id_2));
        bundle.putString(Param.ITEM_NAME, getResources().getString(R.string.analytics_item_name_2));
        MyApplication.getFirebaseAnalytics().logEvent(Event.SELECT_CONTENT, bundle);
        MyApplication.getFirebaseAnalytics().setAnalyticsCollectionEnabled(true);
        MyApplication.getFirebaseAnalytics().setMinimumSessionDuration(5000);
        MyApplication.getFirebaseAnalytics().setSessionTimeoutDuration(1000000);

        this.set_as_wallpaper = (ImageButton) findViewById(R.id.fab_set_as_wallpaper);
        this.share = (ImageButton) findViewById(R.id.fab_share);
        this.save = (ImageButton) findViewById(R.id.fab_save);
        this.set_as_wallpaper.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivitySlideImage.this.position = ActivitySlideImage.this.viewPager.getCurrentItem();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                stringBuilder.append(ActivitySlideImage.this.str_image[ActivitySlideImage.this.position].replace(" ", "%20"));
                String stringBuilder2 = stringBuilder.toString();
                new SetImagesAs(ActivitySlideImage.this, null).execute(new String[]{stringBuilder2});
                ActivitySlideImage.this.issetaswallpaper = Boolean.valueOf(false);
            }
        });
        this.share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivitySlideImage.this.position = ActivitySlideImage.this.viewPager.getCurrentItem();
                ActivitySlideImage.this.image_url = ActivitySlideImage.this.str_image[ActivitySlideImage.this.position];
                List favRow = ActivitySlideImage.this.databaseHandlerFavorite.getFavRow(ActivitySlideImage.this.image_url);
                if (favRow.size() == 0) {
                    ActivitySlideImage.this.addtoFav(ActivitySlideImage.this.position);
                } else if (((Pojo) favRow.get(0)).getImageurl().equals(ActivitySlideImage.this.image_url)) {
                    ActivitySlideImage.this.RemoveFav(ActivitySlideImage.this.position);
                }
            }
        });
        this.save.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivitySlideImage.this.position = ActivitySlideImage.this.viewPager.getCurrentItem();
                String[] strArr;
                StringBuilder stringBuilder;
                if (ActivitySlideImage.this.str_image[ActivitySlideImage.this.position].endsWith(".png")) {
                    SaveImagePNG saveImagePNG = new SaveImagePNG(ActivitySlideImage.this);
                    strArr = new String[1];
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                    stringBuilder.append(ActivitySlideImage.this.str_image[ActivitySlideImage.this.position]);
                    strArr[0] = stringBuilder.toString();
                    saveImagePNG.execute(strArr);
                } else {
                    SaveImageJPG saveImageJPG = new SaveImageJPG(ActivitySlideImage.this);
                    strArr = new String[1];
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                    stringBuilder.append(ActivitySlideImage.this.str_image[ActivitySlideImage.this.position]);
                    strArr[0] = stringBuilder.toString();
                    saveImageJPG.execute(strArr);
                }
                ActivitySlideImage.this.showInterstitialAd();
            }
        });
        Intent intent = getIntent();
        this.position = intent.getIntExtra("POSITION_ID", 0);
        this.str_image = intent.getStringArrayExtra("IMAGE_ARRAY");
        this.str_image_cat_name = intent.getStringArrayExtra("IMAGE_CATNAME");
        this.str_image_id = intent.getStringArrayExtra("ITEMID");
        this.total_images = this.str_image.length - 1;
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
                if (!ActivitySlideImage.this.issetaswallpaper.booleanValue()) {
                    ActivitySlideImage.this.issetaswallpaper = Boolean.valueOf(true);
                }
                i = ActivitySlideImage.this.viewPager.getCurrentItem();
                ActivitySlideImage.this.image_url = ActivitySlideImage.this.str_image[i];
                List favRow = ActivitySlideImage.this.databaseHandlerFavorite.getFavRow(ActivitySlideImage.this.image_url);
                if (favRow.size() == 0) {
                    ActivitySlideImage.this.menu.getItem(0).setIcon(ContextCompat.getDrawable(ActivitySlideImage.this.getApplicationContext(), R.drawable.ic_toolbar_unlove));
                    ActivitySlideImage.this.share.setBackgroundResource(R.drawable.ic_toolbar_unlove);
                    Log.d("tets1", "funnn1");
                } else if (((Pojo) favRow.get(0)).getImageurl().equals(ActivitySlideImage.this.image_url)) {
                    ActivitySlideImage.this.menu.getItem(0).setIcon(ContextCompat.getDrawable(ActivitySlideImage.this.getApplicationContext(), R.drawable.ic_toolbar_love));
                    ActivitySlideImage.this.share.setBackgroundResource(R.drawable.ic_toolbar_love);
                    Log.d("tets2", "funnn2");
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
                    this.image_url = this.str_image[this.position];
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
                    if (this.str_image[this.position].endsWith(".png")) {
                        SaveImagePNG saveImagePNG = new SaveImagePNG(this);
                        strArr = new String[1];
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                        stringBuilder.append(this.str_image[this.position]);
                        strArr[0] = stringBuilder.toString();
                        saveImagePNG.execute(strArr);
                    } else {
                        SaveImageJPG saveImageJPG = new SaveImageJPG(this);
                        strArr = new String[1];
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                        stringBuilder.append(this.str_image[this.position]);
                        strArr[0] = stringBuilder.toString();
                        saveImageJPG.execute(strArr);
                    }
                    return true;
                case R.id.menu_set_as_wallpaper:
                    this.position = this.viewPager.getCurrentItem();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
                    stringBuilder2.append(this.str_image[this.position].replace(" ", "%20"));
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
        this.image_cat_name = this.str_image_cat_name[i];
        this.image_url = this.str_image[i];
        this.databaseHandlerFavorite.AddtoFavorite(new Pojo(this.image_cat_name, this.image_url));
        Toast.makeText(getApplicationContext(), "Added to Favorite", 0).show();
        this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_love));
        this.share.setBackgroundResource(R.drawable.ic_toolbar_love);
    }

    public void RemoveFav(int i) {
        this.image_url = this.str_image[i];
        this.databaseHandlerFavorite.RemoveFav(new Pojo(this.image_url));
        Toast.makeText(getApplicationContext(), "Removed from Favorite", 0).show();
        this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_unlove));
        this.share.setBackgroundResource(R.drawable.ic_toolbar_unlove);
    }

    public void FirstFav() {
        String str = this.str_image[this.viewPager.getCurrentItem()];
        List favRow = this.databaseHandlerFavorite.getFavRow(str);
        if (favRow.size() == 0) {
            this.menu.getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_toolbar_unlove));
            this.share.setBackgroundResource(R.drawable.ic_toolbar_unlove);
        } else if (((Pojo) favRow.get(0)).getImageurl().equals(str)) {
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
