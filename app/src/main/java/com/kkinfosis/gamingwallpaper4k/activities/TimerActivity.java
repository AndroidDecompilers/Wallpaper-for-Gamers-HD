package com.kkinfosis.gamingwallpaper4k.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kkinfosis.gamingwallpaper4k.R;

import java.io.File;
import java.util.ArrayList;

@SuppressLint("WrongConstant")
public class TimerActivity extends AppCompatActivity {
    public static int time;
    private ImageView back;
    private Button btn_time;
    File[] dirFiles;
    public ArrayList<File> fileArrayList;
    File photoDirectory;
    String root = Environment.getExternalStorageDirectory().toString();
    TextView textmessage;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_timer);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.root);
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.saved_folder_name));
        stringBuilder.append("");
        this.photoDirectory = new File(stringBuilder.toString());
        this.textmessage = (TextView) findViewById(R.id.textmessage);
        this.textmessage.setText(getResources().getString(R.string.text_message));
        this.fileArrayList = new ArrayList();
        if (this.photoDirectory.exists()) {
            this.dirFiles = this.photoDirectory.listFiles();
            if (this.dirFiles.length != 0) {
                for (File add : this.dirFiles) {
                    this.fileArrayList.add(add);
                }
            }
            if (this.dirFiles.length != 0) {
                this.textmessage.setText(getResources().getString(R.string.text_messagecontains));
            } else {
                this.textmessage.setText(getResources().getString(R.string.text_message));
                Toast.makeText(this, "Please Download atleast one image to set wallpaper ", 0).show();
            }
        } else {
            this.textmessage.setText(getResources().getString(R.string.text_message));
            Toast.makeText(this, "Please Download atleast one image to set wallpaper ", 0).show();
        }
        this.btn_time = (Button) findViewById(R.id.btn_time);
        this.back = (ImageView) findViewById(R.id.back);
        this.btn_time.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(TimerActivity.this);
                final ArrayAdapter arrayAdapter = new ArrayAdapter(TimerActivity.this, android.R.layout.simple_list_item_1);
                arrayAdapter.add("30 minutes");
                arrayAdapter.add("1 hour");
                arrayAdapter.add("2 hours");
                arrayAdapter.add("4 hours");
                arrayAdapter.add("1 day");
                arrayAdapter.add("2 days");
                arrayAdapter.add("4 days");
                arrayAdapter.add("1 week");
                builder.setNegativeButton((CharSequence) "cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String charSequence = (String) arrayAdapter.getItem(i);
                        Builder builder = new Builder(TimerActivity.this);
                        builder.setMessage(charSequence);
                        builder.setPositiveButton((CharSequence) "Ok", new DialogInterface.OnClickListener() {

                            @RequiresApi(api = 24)
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (charSequence.equalsIgnoreCase("1 min")) {
                                    TimerActivity.time = (int) 60000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 30 minutes", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("1 hour")) {
                                    TimerActivity.time = (int) 3600000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 1 hour", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("2 hours")) {
                                    TimerActivity.time = (int) 7200000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 2  hours", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("4 hours")) {
                                    TimerActivity.time = (int) 14400000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 4 hours", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("1 day")) {
                                    TimerActivity.time = (int) 86400000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 1 day", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("2 days")) {
                                    TimerActivity.time = (int) 172800000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 2 days", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("4 days")) {
                                    TimerActivity.time = (int) 345600000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 4 days", 0).show();
                                }
                                if (charSequence.equalsIgnoreCase("1 week")) {
                                    TimerActivity.time = (int) 645600000;
                                    Toast.makeText(TimerActivity.this, "Wallpaper will change after every 7 days", 0).show();
                                }
                                Intent intent = new Intent(TimerActivity.this, ServiceForWallpaper.class);
                                ServiceForWallpaper.time = TimerActivity.time;
                                TimerActivity.this.startService(intent);
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
                builder.show();
            }
        });
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TimerActivity.this.finish();
                TimerActivity.this.startActivity(new Intent(TimerActivity.this, MainActivity.class));
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
