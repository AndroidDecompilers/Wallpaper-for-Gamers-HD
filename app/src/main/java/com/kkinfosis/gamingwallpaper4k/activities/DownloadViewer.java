package com.kkinfosis.gamingwallpaper4k.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.fragments.DownloadedWalpapers;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Kundan Singh on 10-12-2018.
 */
public class DownloadViewer extends AppCompatActivity {

    ViewPager viewPager;

    public int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_image);
        findViewById(R.id.fab_share).setVisibility(View.GONE);
        viewPager = findViewById(R.id.image_slider);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        findViewById(R.id.fab_save).setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager()));
        viewPager.setCurrentItem(getIntent().getExtras().getInt("pos"));
        findViewById(R.id.fab_set_as_wallpaper).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int position = viewPager.getCurrentItem();
                File file = DownloadedWalpapers.data.get(position);
                if (Build.VERSION.SDK_INT < 23) {
                    try {
                        WallpaperManager.getInstance(getApplicationContext()).setStream(new FileInputStream(file));
                        Toast.makeText(DownloadViewer.this, "Wallpaper set scucessfully", Toast.LENGTH_SHORT).show();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }

                Intent intent = new Intent("android.intent.action.ATTACH_DATA");
                Uri uriForFile = FileProvider.getUriForFile(DownloadViewer.this, "com.kkinfosis.gamingwallpaper4k.provider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    intent.setDataAndType(uriForFile, "image/jpg");
                    intent.putExtra("mimeType", "image/jpg");
                    startActivityForResult(Intent.createChooser(intent, "Set As"), 200);
                } catch (Exception unused) {
                }
            }
        });
    }

    class AdapterFragment extends FragmentStatePagerAdapter {

        public AdapterFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return DownloadedWalpapers.data.size();
        }

    }

    public static class ImageFragment extends Fragment {

        public static Fragment getInstance(int pos) {
            Fragment fragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("pos", pos);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.imageview_layout_fragment, container, false);
            int pos = getArguments().getInt("pos");
            ImageView viewImage = view.findViewById(R.id.viewImage);
            TextView catnammmme = view.findViewById(R.id.catnammmme);
            catnammmme.setText("Download " + pos);
            Glide.with(getActivity()).load(DownloadedWalpapers.data.get(pos)).into(viewImage);
            return view;
        }
    }
}
