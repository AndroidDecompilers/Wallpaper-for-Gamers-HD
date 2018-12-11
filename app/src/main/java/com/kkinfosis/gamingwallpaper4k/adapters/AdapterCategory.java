package com.kkinfosis.gamingwallpaper4k.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.ActivityWallpaperByCategory;
import com.kkinfosis.gamingwallpaper4k.json.JsonConfig;
import com.kkinfosis.gamingwallpaper4k.models.CategoryItem;

import java.util.List;


@SuppressLint("WrongConstant")
public class AdapterCategory extends Adapter<ViewHolder> {
    private Activity context;
    CategoryItem categoryItem;
    private List<CategoryItem> recyclerItemList;
    private int row;

    public class ItemViewHolder extends ViewHolder {
        public ImageView img_cat;
        public CardView cardView;
        public TextView txt;

        public ItemViewHolder(View view) {
            super(view);
            this.txt = (TextView) view.findViewById(R.id.category_name);
            this.img_cat = (ImageView) view.findViewById(R.id.category_image);
            this.cardView = (CardView) view.findViewById(R.id.cardView);
        }
    }


    public AdapterCategory(Activity activity, List<CategoryItem> list) {
        this.context = activity;
        this.recyclerItemList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lsv_item_category, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            this.categoryItem = (CategoryItem) this.recyclerItemList.get(i);
            itemViewHolder.txt.setText(this.categoryItem.getCategoryName());
            Glide.with(context).load("https://appsapionline.com/GamingWallpaper/wallpapers/" + this.categoryItem.getCategoryImage().replace(" ", "%20"))
                    .override((int) context.getResources().getDimension(R.dimen.category_width), (int) context.getResources().getDimension(R.dimen.category_height)).into(itemViewHolder.img_cat);
            itemViewHolder.cardView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Log.d("Farman", "1");
                    if (AdapterCategory.this.recyclerItemList.get(i) instanceof CategoryItem) {
                        AdapterCategory.this.categoryItem = (CategoryItem) AdapterCategory.this.recyclerItemList.get(i);
                        String categoryId = AdapterCategory.this.categoryItem.getCategoryId();
                        JsonConfig.CATEGORY_ID = AdapterCategory.this.categoryItem.getCategoryId();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(categoryId);
                        Log.e("cat_id", stringBuilder.toString());
                        JsonConfig.CATEGORY_TITLE = AdapterCategory.this.categoryItem.getCategoryName();
                        AdapterCategory.this.context.startActivity(new Intent(AdapterCategory.this.context, ActivityWallpaperByCategory.class));
                    }
                }
            });
            return;
        }
    }

    public int getItemCount() {
        return this.recyclerItemList.size();
    }

    public int getItemViewType(int i) {
        return 2;
    }

}
