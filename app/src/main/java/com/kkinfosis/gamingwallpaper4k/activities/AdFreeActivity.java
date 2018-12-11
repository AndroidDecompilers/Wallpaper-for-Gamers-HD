package com.kkinfosis.gamingwallpaper4k.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kkinfosis.gamingwallpaper4k.R;

public class AdFreeActivity extends AppCompatActivity {
    Button rewardedadbtn;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_ad_free);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.rewardedadbtn = (Button) findViewById(R.id.rewardedadbtn);
        this.rewardedadbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
