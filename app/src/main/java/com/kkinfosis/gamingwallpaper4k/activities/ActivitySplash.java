package com.kkinfosis.gamingwallpaper4k.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kkinfosis.gamingwallpaper4k.utilities.Constant;
import com.kkinfosis.gamingwallpaper4k.utilities.SharedPrefrenceHelper;
import com.kkinfosis.gamingwallpaper4k.R;
import com.stepstone.apprating.AppRatingDialog.Builder;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

public class ActivitySplash extends AppCompatActivity implements OnClickListener, RatingDialogListener {
    Button btn_gall;
    Button btn_moreApp;
    SharedPrefrenceHelper sharedPrefrenceHelper;

    public void
    onNegativeButtonClicked() {
        this.sharedPrefrenceHelper.saveBoolean(Constant.SHOW_RATE_CARD, true);
    }

    public void onNeutralButtonClicked() {
    }

    @SuppressLint({"ResourceAsColor"})
    public void
    onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);
        this.btn_gall = (Button) findViewById(R.id.btn_gall);
        this.btn_moreApp = (Button) findViewById(R.id.btn_moreApp);
        this.sharedPrefrenceHelper = new SharedPrefrenceHelper(this);

        this.btn_gall.setOnClickListener(this);
        this.btn_moreApp.setOnClickListener(this);
        if (VERSION.SDK_INT > 19) {
            this.btn_moreApp.setBackgroundResource(R.drawable.ripple_btnmore);
            this.btn_gall.setBackgroundResource(R.drawable.ripple_btngallery);
        }
        getWindow().setFlags(512, 512);
    }


    @Override
    protected void onStart() {
        super.onStart();
        int i = this.sharedPrefrenceHelper.getInt(Constant.APP_USE_COUNT) + 1;
        this.sharedPrefrenceHelper.saveInt(Constant.APP_USE_COUNT, i);
        boolean show = this.sharedPrefrenceHelper.getBoolen(Constant.SHOW_RATE_CARD);
        if (i % 2 == 0 && !show) {
            showDialog();
        }
    }

    @SuppressLint({"ResourceAsColor"})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gall:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return;
            case R.id.btn_moreApp:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Allegiance+Apps")));
                return;
            default:
                return;
        }
    }

    public void onPositiveButtonClicked(int i, String str) {
        if (i >= 3) {
            String packageName = getPackageName();
            StringBuilder stringBuilder;
            try {
                stringBuilder = new StringBuilder();
                stringBuilder.append("market://details?id=");
                stringBuilder.append(packageName);
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
            } catch (ActivityNotFoundException unused) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("http://play.google.com/store/apps/details?id=");
                stringBuilder.append(packageName);
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
            }
        }
    }

    private void showDialog() {
        try {
            new Builder().setPositiveButtonText("Submit")
                    .setNegativeButtonText("Cancel")
                    .setNeutralButtonText("Later")
                    .setNoteDescriptions(Arrays.asList(new String[]{"Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !"}))
                    .setDefaultRating(2).setTitle("One minute from your time.").setDescription("We care about your opinion")
                    .setCommentInputEnabled(true).setDefaultComment("This app is pretty cool !")
                    .setStarColor(R.color.starColor).setNoteDescriptionTextColor(R.color.colorAccent)
                    .setTitleTextColor(R.color.colorPrimary)
                    .setDescriptionTextColor(R.color.colorPrimary)
                    .setHint("Please write your review here ...")
                    .setHintTextColor(R.color.hintTextColor)
                    .setCommentTextColor(R.color.colorAccent)
                    .setCommentBackgroundColor(R.color.whitebg)
                    .setWindowAnimation(R.style.MyDialogFadeAnimation)
                    .setCancelable(false).setCanceledOnTouchOutside(false).create(this).show();
        } catch (Exception e) {

        }
    }
}
