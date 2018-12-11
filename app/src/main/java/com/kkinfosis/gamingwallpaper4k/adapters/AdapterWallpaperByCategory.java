package com.kkinfosis.gamingwallpaper4k.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkinfosis.gamingwallpaper4k.models.ItemWallpaperByCategory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kkinfosis.gamingwallpaper4k.R;

import java.util.List;

public class AdapterWallpaperByCategory extends Adapter<AdapterWallpaperByCategory.ViewHolder> {
    ItemWallpaperByCategory ItemWallpaperByCategory;
    private List<ItemWallpaperByCategory> arrayItemWallpaperByCategory;
    private Context context;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView txt_cat;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item);
            this.txt_cat = (TextView) view.findViewById(R.id.txt_cat);
        }
    }

    public AdapterWallpaperByCategory(Context context, List<ItemWallpaperByCategory> list) {
        this.context = context;
        this.arrayItemWallpaperByCategory = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lsv_item_grid_wallpaper, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        this.ItemWallpaperByCategory = (ItemWallpaperByCategory) this.arrayItemWallpaperByCategory.get(i);
        RequestManager with = Glide.with(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
        stringBuilder.append(this.ItemWallpaperByCategory.getItemImageurl().replace(" ", "%20"));
        with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_thumbnail).override((int) context.getResources().getDimension(R.dimen.image_width), (int) context.getResources().getDimension(R.dimen.image_height))
                .centerCrop().into(viewHolder.imageView);
        viewHolder.txt_cat.setVisibility(View.GONE);
    }

    public int getItemCount() {
        return this.arrayItemWallpaperByCategory.size();
    }
}
