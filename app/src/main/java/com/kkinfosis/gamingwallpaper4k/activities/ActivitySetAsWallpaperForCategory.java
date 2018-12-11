package com.kkinfosis.gamingwallpaper4k.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.github.clans.fab.FloatingActionButton;
import com.kkinfosis.gamingwallpaper4k.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ActivitySetAsWallpaperForCategory extends AppCompatActivity {
    FloatingActionButton fab;
    private CropImageView mCropImageView;
    int position;
    String[] str_cat_name;
    String[] str_image;
    Toolbar toolbar;

    public class SetWallpaperTask extends AsyncTask<String, String, String> {
        Bitmap bmImg = null;
        private Context context;
        private ProgressDialog pDialog;

        protected String doInBackground(String... strArr) {
            return null;
        }

        public SetWallpaperTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pDialog = new ProgressDialog(this.context);
            this.pDialog.setMessage(ActivitySetAsWallpaperForCategory.this.getResources().getString(R.string.please_wait));
            this.pDialog.setIndeterminate(false);
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }

        protected void onPostExecute(String str) {
            this.bmImg = ActivitySetAsWallpaperForCategory.this.mCropImageView.getCroppedImage();
            Bitmap bitmap = this.bmImg;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bitmap.getByteCount());
            stringBuilder.append("");
            Log.d("sizeofbitmap", stringBuilder.toString());
            File file = new File(ActivitySetAsWallpaperForCategory.this.getExternalCacheDir(), "toshare.jpg");
            Context context = this.context;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.context.getApplicationContext().getPackageName());
            stringBuilder2.append(".my.package.name.provider");
            Uri uriForFile = FileProvider.getUriForFile(context, stringBuilder2.toString(), file);
            Intent intent = new Intent("android.intent.action.ATTACH_DATA");
            try {
                OutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(CompressFormat.JPEG, 60, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception unused) {
                intent.setDataAndType(uriForFile, "image/jpg");
                intent.putExtra("mimeType", "image/jpg");
                this.pDialog.dismiss();
                ActivitySetAsWallpaperForCategory.this.startActivityForResult(Intent.createChooser(intent, "Set As"), 200);
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_set_as_wallpaper);
        Intent intent = getIntent();
        this.str_image = intent.getStringArrayExtra("WALLPAPER_IMAGE_URL");
        this.str_cat_name = intent.getStringArrayExtra("WALLPAPER_IMAGE_CATEGORY");
        Log.d("RTL Mode", "Working in Normal Mode, RTL Mode is Disabled");
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.position = intent.getIntExtra("POSITION_ID", 0);
        this.mCropImageView = (CropImageView) findViewById(R.id.CropImageView);
        this.fab = (FloatingActionButton) findViewById(R.id.setAsWallpaper);
        this.fab.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new SetWallpaperTask(ActivitySetAsWallpaperForCategory.this).execute(new String[]{""});
            }
        });
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        ImageLoader instance = ImageLoader.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
        stringBuilder.append(this.str_image[this.position]);
        instance.loadImage(stringBuilder.toString(), new ImageLoadingListener() {
            public void onLoadingCancelled(String str, View view) {
            }

            public void onLoadingFailed(String str, View view, FailReason failReason) {
            }

            public void onLoadingStarted(String str, View view) {
            }

            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                ActivitySetAsWallpaperForCategory.this.mCropImageView.setImageBitmap(bitmap);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }


    public int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
    }
}
