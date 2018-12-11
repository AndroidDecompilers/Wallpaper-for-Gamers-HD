package com.kkinfosis.gamingwallpaper4k.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.NewActivitySlideImage;
import com.kkinfosis.gamingwallpaper4k.adapters.AdapterFavorite;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerFavorite;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerFavorite.DatabaseManager;
import com.kkinfosis.gamingwallpaper4k.json.JsonUtils;
import com.kkinfosis.gamingwallpaper4k.models.RecentItem;
import com.kkinfosis.gamingwallpaper4k.utilities.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment {
    AdapterFavorite adapterFavorite;
    DatabaseHandlerFavorite databaseHandlerFavorite;
    private DatabaseManager databaseManager;
    ArrayList<String> image_cat_name;
    JsonUtils jsonUtils;
    LinearLayout linearLayout;
    public static List<RecentItem> listItem;
    ArrayList<String> list_image;
    RecyclerView recyclerView;
    String[] str_image_cat_name;
    String[] str_list_image;

    public interface ClickListener {
        void onClick(View view, int i);

        void onLongClick(View view, int i);
    }

    class RecyclerTouchListener implements OnItemTouchListener {
        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            this.gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

                public void onLongPress(MotionEvent motionEvent) {
                    View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (findChildViewUnder != null && clickListener != null) {
                        clickListener.onLongClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
                    }
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View findChildViewUnder = FragmentFavorite.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (!(findChildViewUnder == null || this.clickListener == null || !this.gestureDetector.onTouchEvent(motionEvent))) {
                this.clickListener.onClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
            }
            return false;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_favorite, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        this.linearLayout = (LinearLayout) inflate.findViewById(R.id.lyt_no_favorite);
        this.databaseHandlerFavorite = new DatabaseHandlerFavorite(getActivity());
        this.databaseManager = DatabaseManager.INSTANCE;
        this.databaseManager.init(getActivity());
        this.jsonUtils = new JsonUtils(getActivity());
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.recyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        this.listItem = this.databaseHandlerFavorite.getAllData();
        this.adapterFavorite = new AdapterFavorite(getActivity(), this.listItem);
        this.recyclerView.setAdapter(this.adapterFavorite);
        if (this.listItem.size() == 0) {
            this.linearLayout.setVisibility(View.VISIBLE);
        } else {
            this.linearLayout.setVisibility(View.INVISIBLE);
        }
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), this.recyclerView, new ClickListener() {
            public void onClick(View view, final int i) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(FragmentFavorite.this.getActivity(), NewActivitySlideImage.class);
                        intent.putExtra("POSITION_ID", i);
                        intent.putExtra("IMAGE_ARRAY", FragmentFavorite.this.str_list_image);
                        intent.putExtra("IMAGE_CATNAME", FragmentFavorite.this.str_image_cat_name);
                        intent.putExtra("class", FragmentFavorite.class.getSimpleName());
                        FragmentFavorite.this.startActivity(intent);
                    }
                }, 400);
            }

            public void onLongClick(View view, final int i) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(FragmentFavorite.this.getActivity(), NewActivitySlideImage.class);
                        intent.putExtra("POSITION_ID", i);
                        intent.putExtra("IMAGE_ARRAY", FragmentFavorite.this.str_list_image);
                        intent.putExtra("class", FragmentFavorite.class.getSimpleName());
                        intent.putExtra("IMAGE_CATNAME", FragmentFavorite.this.str_image_cat_name);
                        FragmentFavorite.this.startActivity(intent);
                    }
                }, 400);
            }
        }));
        return inflate;
    }

    public void onDestroyView() {
        if (!this.databaseManager.isDatabaseClosed()) {
            this.databaseManager.closeDatabase();
        }
        super.onDestroyView();
    }

    public void onResume() {
        super.onResume();
        this.listItem = this.databaseHandlerFavorite.getAllData();
        this.adapterFavorite = new AdapterFavorite(getActivity(), this.listItem);
        this.recyclerView.setAdapter(this.adapterFavorite);
        int i = 0;
        if (this.listItem.size() == 0) {
            this.linearLayout.setVisibility(View.VISIBLE);
        } else {
            this.linearLayout.setVisibility(View.INVISIBLE);
        }
        this.list_image = new ArrayList();
        this.image_cat_name = new ArrayList();
        this.str_list_image = new String[this.list_image.size()];
        this.str_image_cat_name = new String[this.image_cat_name.size()];
        while (i < this.listItem.size()) {
            RecentItem pojo = (RecentItem) this.listItem.get(i);
            this.list_image.add(pojo.getImageurl());
            this.str_list_image = (String[]) this.list_image.toArray(this.str_list_image);
            this.image_cat_name.add(pojo.getCategoryName());
            this.str_image_cat_name = (String[]) this.image_cat_name.toArray(this.str_image_cat_name);
            i++;
        }
        if (this.databaseManager == null) {
            this.databaseManager = DatabaseManager.INSTANCE;
            this.databaseManager.init(getActivity());
        } else if (this.databaseManager.isDatabaseClosed()) {
            this.databaseManager.init(getActivity());
        }
    }

    public void onPause() {
        super.onPause();
        if (!this.databaseManager.isDatabaseClosed()) {
            this.databaseManager.closeDatabase();
        }
    }

    private int dpToPx(int i) {
        return Math.round(TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics()));
    }

}
