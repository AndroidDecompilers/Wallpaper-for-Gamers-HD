package com.kkinfosis.gamingwallpaper4k.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.NewActivitySlideImage;
import com.kkinfosis.gamingwallpaper4k.adapters.AdapterRecent;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerRecent;
import com.kkinfosis.gamingwallpaper4k.json.JsonConfig;
import com.kkinfosis.gamingwallpaper4k.json.JsonUtils;
import com.kkinfosis.gamingwallpaper4k.models.RecentItem;
import com.kkinfosis.gamingwallpaper4k.utilities.ItemOffsetDecoration;
import com.kkinfosis.gamingwallpaper4k.utilities.SharedPrefrenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("WrongConstant")
public class FragmentRecentRandom extends Fragment {
    public static List<RecentItem> listRecentItem;
    AdapterRecent AdapterRecent;
    String[] allArrayImage;
    String[] allArrayImageCatName;
    ArrayList<String> allListImage;
    ArrayList<String> allListImageCatName;
    public DatabaseHandlerRecent databaseHandlerRecent;
    private RecentItem recentItem;
    JsonUtils jsonUtils;
    private ProgressBar progressBar;
    RecyclerView recyclerView;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    SwipeRefreshLayout swipeRefreshLayout = null;
    private boolean dataInitied = false;


    public void initData() {
        if (!dataInitied) {
            int i = 0;
            if (JsonUtils.isNetworkAvailable(getActivity())) {
                new MyTask().execute(new String[]{"https://appsapionline.com/wallpapers/recent"});
            } else {
                listRecentItem = this.databaseHandlerRecent.getAllData();
                if (listRecentItem.size() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.network_first_load), 0).show();
                }
                setAdapterToListView();
                while (i < listRecentItem.size()) {
                    this.recentItem = (RecentItem) listRecentItem.get(i);
                    this.allListImage.add(this.recentItem.getImageurl());
                    this.allArrayImage = (String[]) this.allListImage.toArray(this.allArrayImage);
                    this.allListImageCatName.add(this.recentItem.getCategoryName());
                    this.allArrayImageCatName = (String[]) this.allListImageCatName.toArray(this.allArrayImageCatName);
                    i++;
                }
            }
            dataInitied = true;
        }
    }

    public interface ClickListener {
        void onClick(View view, int i);

        void onLongClick(View view, int i);
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            FragmentRecentRandom.this.progressBar.setVisibility(0);
        }

        protected String doInBackground(String... strArr) {
            return JsonUtils.getJSONString(strArr[0]);
        }


        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            FragmentRecentRandom.this.progressBar.setVisibility(8);
            int i = 0;
            if (str == null || str.length() == 0) {
                Toast.makeText(FragmentRecentRandom.this.getActivity(), FragmentRecentRandom.this.getResources().getString(R.string.network_error), 0).show();
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    RecentItem recentItem = new RecentItem();
//                    FragmentRecentRandom.this.databaseHandlerRecent.AddtoFavoriteLatest(new RecentItem(jSONObject.getString("category_name"), jSONObject.getString(JsonConfig.LATEST_IMAGE_URL)));
                    recentItem.setCategoryName(jSONObject.getString("category_name"));
                    recentItem.setImageurl(jSONObject.getString(JsonConfig.LATEST_IMAGE_URL));
                    FragmentRecentRandom.listRecentItem.add(recentItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            while (i < FragmentRecentRandom.listRecentItem.size()) {
                FragmentRecentRandom.this.recentItem = (RecentItem) FragmentRecentRandom.listRecentItem.get(i);
                FragmentRecentRandom.this.allListImage.add(FragmentRecentRandom.this.recentItem.getImageurl());
                FragmentRecentRandom.this.allArrayImage = (String[]) FragmentRecentRandom.this.allListImage.toArray(FragmentRecentRandom.this.allArrayImage);
                FragmentRecentRandom.this.allListImageCatName.add(FragmentRecentRandom.this.recentItem.getCategoryName());
                FragmentRecentRandom.this.allArrayImageCatName = (String[]) FragmentRecentRandom.this.allListImageCatName.toArray(FragmentRecentRandom.this.allArrayImageCatName);
                i++;
            }
            FragmentRecentRandom.this.setAdapterToListView();
        }
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
            View findChildViewUnder = FragmentRecentRandom.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (!(findChildViewUnder == null || this.clickListener == null || !this.gestureDetector.onTouchEvent(motionEvent))) {
                this.clickListener.onClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
            }
            return false;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recent_wallpaper, viewGroup, false);
        setHasOptionsMenu(true);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progressBar);
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue, R.color.red);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.recyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        this.databaseHandlerRecent = new DatabaseHandlerRecent(getActivity());
        listRecentItem = new ArrayList();
        this.allListImage = new ArrayList();
        this.allListImageCatName = new ArrayList();
        this.allArrayImage = new String[this.allListImage.size()];
        this.allArrayImageCatName = new String[this.allListImageCatName.size()];
        this.jsonUtils = new JsonUtils(getActivity());
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), this.recyclerView, new ClickListener() {
            public void onClick(View view, int i) {
                Intent intent = new Intent(FragmentRecentRandom.this.getActivity(), NewActivitySlideImage.class);
                intent.putExtra("POSITION_ID", i);
                intent.putExtra("IMAGE_ARRAY", FragmentRecentRandom.this.allArrayImage);
                intent.putExtra("IMAGE_CATNAME", FragmentRecentRandom.this.allArrayImageCatName);
                intent.putExtra("class", FragmentRecentRandom.class.getSimpleName());
                FragmentRecentRandom.this.startActivity(intent);
            }

            public void onLongClick(View view, int i) {
                Intent intent = new Intent(FragmentRecentRandom.this.getActivity(), NewActivitySlideImage.class);
                intent.putExtra("POSITION_ID", i);
                intent.putExtra("IMAGE_ARRAY", FragmentRecentRandom.this.allArrayImage);
                intent.putExtra("class", FragmentRecentRandom.class.getSimpleName());
                intent.putExtra("IMAGE_CATNAME", FragmentRecentRandom.this.allArrayImageCatName);
                FragmentRecentRandom.this.startActivity(intent);
            }
        }));
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                FragmentRecentRandom.listRecentItem.clear();
                FragmentRecentRandom.this.allListImage.clear();
                FragmentRecentRandom.this.allListImageCatName.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (JsonUtils.isNetworkAvailable(FragmentRecentRandom.this.getActivity())) {
                            FragmentRecentRandom.this.swipeRefreshLayout.setRefreshing(false);
                            FragmentRecentRandom.this.clearData();
                            new MyTask().execute(new String[]{"https://appsapionline.com/wallpapers/recent"});
                            return;
                        }
                        FragmentRecentRandom.this.swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(FragmentRecentRandom.this.getActivity(), FragmentRecentRandom.this.getResources().getString(R.string.refresh_alert), 0).show();
                    }
                }, 3000);
            }
        });

        return inflate;
    }

    public void clearData() {
        int size = listRecentItem.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listRecentItem.remove(0);
            }
            this.AdapterRecent.notifyItemRangeRemoved(0, size);
        }
    }

    public void setAdapterToListView() {
        Collections.shuffle(listRecentItem);
        this.AdapterRecent = new AdapterRecent(getActivity(), listRecentItem);
        this.recyclerView.setAdapter(this.AdapterRecent);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.main, menu);
    }


    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());
    }
}
