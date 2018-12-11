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

public class AdapterRecent extends Adapter<AdapterRecent.ViewHolder> {
    public static List<RecentItem> staticarrayRecentItem;
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

    public AdapterRecent(Context context, List<RecentItem> list) {
        this.context = context;
        this.arrayRecentItem = list;
        staticarrayRecentItem = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lsv_item_grid_wallpaper, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        this.recentItem = (RecentItem) this.arrayRecentItem.get(i);
        RequestManager with = Glide.with(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
        stringBuilder.append(this.recentItem.getImageurl().replace(" ", "%20"));
        with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_thumbnail).into(viewHolder.imageView);
        viewHolder.txt_cat.setText(this.recentItem.getCategoryName());
    }

    public int getItemCount() {
        return this.arrayRecentItem.size();
    }
}
