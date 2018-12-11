package com.kkinfosis.gamingwallpaper4k.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkinfosis.gamingwallpaper4k.models.RecentItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kkinfosis.gamingwallpaper4k.R;

import java.util.List;
import java.util.Random;

public class PremiumWalllpaperAdpter extends Adapter<PremiumWalllpaperAdpter.ViewHolder> {
    public static List<RecentItem> selectedItem;
    public List<RecentItem> arrayRecentItem;
    private Context context;
    RecentItem recentItem;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView txt_cat;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item);
            this.txt_cat = (TextView) view.findViewById(R.id.txt_cat);
        }
    }

    public PremiumWalllpaperAdpter(Context context, List<RecentItem> list) {
        this.context = context;
        this.arrayRecentItem = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lsv_item_grid_wallpaper, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        this.recentItem = (RecentItem) this.arrayRecentItem.get(i);
        new Random().nextInt(8);
        RequestManager with = Glide.with(context);
        with.load("https://appsapionline.com/GamingWallpaper/wallpapers/" + this.recentItem.getImageurl().replace(" ", "%20"))
                .placeholder((int) R.drawable.ic_thumbnail).centerCrop().into(viewHolder.imageView);
        viewHolder.txt_cat.setText(this.recentItem.getCategoryName());
    }

    public int getItemCount() {
        return this.arrayRecentItem.size();
    }

    public void setSelectedItem(List<RecentItem> list) {
        selectedItem = list;
    }
}
