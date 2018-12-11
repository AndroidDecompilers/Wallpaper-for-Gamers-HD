package com.kkinfosis.gamingwallpaper4k.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kkinfosis.gamingwallpaper4k.models.RecentItem;
import com.balysv.materialripple.MaterialRippleLayout;
import com.kkinfosis.gamingwallpaper4k.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class AdapterFavorite extends Adapter<AdapterFavorite.ViewHolder> {
    private List<RecentItem> arrayPojo;
    private Context context;
    private RecentItem pojo;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView txt_cat;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item);
            this.txt_cat = (TextView) view.findViewById(R.id.txt_cat);
        }
    }

    public AdapterFavorite(Context context, List<RecentItem> list) {
        this.context = context;
        this.arrayPojo = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lsv_item_grid_wallpaper, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        this.pojo = this.arrayPojo.get(i);
        RequestManager with = Glide.with(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://appsapionline.com/GamingWallpaper/wallpapers/");
        try {
            stringBuilder.append(URLEncoder.encode(pojo.getImageurl(), "UTF-8"));
            with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_thumbnail)
                    .centerCrop().into(viewHolder.imageView);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewHolder.txt_cat.setText(this.pojo.getCategoryName());
    }

    public int getItemCount() {
        return this.arrayPojo.size();
    }
}
