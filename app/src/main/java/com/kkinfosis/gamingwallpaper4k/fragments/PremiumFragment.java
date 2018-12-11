package com.kkinfosis.gamingwallpaper4k.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.NewActivitySlideImage;
import com.kkinfosis.gamingwallpaper4k.adapters.PremiumWalllpaperAdpter;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint("WrongConstant")
public class PremiumFragment extends Fragment {
    PremiumWalllpaperAdpter AdapterRecent;
    int adLeaveCheck = 1;
    String[] allArrayImage;
    String[] allArrayImageCatName;
    ArrayList<String> allListImage;
    ArrayList<String> allListImageCatName;
    ArrayList<RecentItem> arrayListItemCategory;
    int counter = 1;
    public DatabaseHandlerRecent databaseHandlerRecent;
    boolean isRewardAdPlaying = false;
    private RecentItem recentItem;
    JsonUtils jsonUtils;
    List<RecentItem> listRecentItem;
    int mPosition;
    private ProgressBar progressBar;
    ProgressDialog progressDialog;
    private OnQueryTextListener queryTextListener;
    RecyclerView recyclerView;
    private SearchView searchView = null;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    SwipeRefreshLayout swipeRefreshLayout = null;
    private boolean dataInited = false;

    public void initData() {
        if (!dataInited) {
            if (JsonUtils.isNetworkAvailable(getActivity())) {
                new MyTask().execute(new String[]{"https://appsapionline.com/wallpapers/prem"});
            } else {
                this.listRecentItem = this.databaseHandlerRecent.getAllData();
                if (this.listRecentItem.size() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.network_first_load), 0).show();
                }
                setAdapterToListView();
                int i = 0;
                while (i < this.listRecentItem.size()) {
                    this.recentItem = (RecentItem) this.listRecentItem.get(i);
                    this.allListImage.add(this.recentItem.getImageurl());
                    this.allArrayImage = (String[]) this.allListImage.toArray(this.allArrayImage);
                    this.allListImageCatName.add(this.recentItem.getCategoryName());
                    this.allArrayImageCatName = (String[]) this.allListImageCatName.toArray(this.allArrayImageCatName);
                    i++;
                }
            }
            dataInited = true;
        }
    }

    public interface ClickListener {
        void onClick(View view, int i);

        void onLongClick(View view, int i);
    }

    public class MyTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            PremiumFragment.this.progressBar.setVisibility(0);
        }

        protected String doInBackground(String... strArr) {
            return JsonUtils.getJSONString(strArr[0]);
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            PremiumFragment.this.progressBar.setVisibility(8);
            int i = 0;
            if (str == null || str.length() == 0) {
                Toast.makeText(PremiumFragment.this.getActivity(), PremiumFragment.this.getResources().getString(R.string.network_error), 0).show();
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    RecentItem recentItem = new RecentItem();
                    recentItem.setCategoryName(jSONObject.getString("category_name"));
                    recentItem.setImageurl(jSONObject.getString(JsonConfig.LATEST_IMAGE_URL));
                    PremiumFragment.this.listRecentItem.add(recentItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            while (i < PremiumFragment.this.listRecentItem.size()) {
                PremiumFragment.this.recentItem = (RecentItem) PremiumFragment.this.listRecentItem.get(i);
                PremiumFragment.this.allListImage.add(PremiumFragment.this.recentItem.getImageurl());
                PremiumFragment.this.allArrayImage = (String[]) PremiumFragment.this.allListImage.toArray(PremiumFragment.this.allArrayImage);
                PremiumFragment.this.allListImageCatName.add(PremiumFragment.this.recentItem.getCategoryName());
                PremiumFragment.this.allArrayImageCatName = (String[]) PremiumFragment.this.allListImageCatName.toArray(PremiumFragment.this.allArrayImageCatName);
                i++;
            }
            PremiumFragment.this.arrayListItemCategory.addAll(PremiumFragment.this.listRecentItem);
            PremiumFragment.this.setAdapterToListView();
        }
    }

    class RecyclerTouchListener implements OnItemTouchListener {
        private FragmentRecent.ClickListener clickListener;
        private GestureDetector gestureDetector;

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FragmentRecent.ClickListener clickListener) {
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
            View findChildViewUnder = PremiumFragment.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (!(findChildViewUnder == null || this.clickListener == null || !this.gestureDetector.onTouchEvent(motionEvent))) {
                this.clickListener.onClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
            }
            return false;
        }
    }

    public void onRewardedVideoCompleted() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = 0;
        View inflate = layoutInflater.inflate(R.layout.fragment_premium, viewGroup, false);
        setHasOptionsMenu(true);
        this.arrayListItemCategory = new ArrayList();
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progressBar);
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue, R.color.red);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.recyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        this.databaseHandlerRecent = new DatabaseHandlerRecent(getActivity());
        this.listRecentItem = new ArrayList();
        this.allListImage = new ArrayList();
        this.allListImageCatName = new ArrayList();
        this.allArrayImage = new String[this.allListImage.size()];
        this.allArrayImageCatName = new String[this.allListImageCatName.size()];
        this.jsonUtils = new JsonUtils(getActivity());
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), this.recyclerView, new FragmentRecent.ClickListener() {
            public void onClick(View view, int i) {
                PremiumFragment.this.mPosition = i;
                goToNextPage();
            }

            public void onLongClick(View view, int i) {
                PremiumFragment.this.mPosition = i;
                goToNextPage();
            }
        }));
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                PremiumFragment.this.listRecentItem.clear();
                PremiumFragment.this.allListImage.clear();
                PremiumFragment.this.allListImageCatName.clear();
                if (JsonUtils.isNetworkAvailable(PremiumFragment.this.getActivity())) {
                    PremiumFragment.this.swipeRefreshLayout.setRefreshing(false);
                    PremiumFragment.this.clearData();
                    new MyTask().execute(new String[]{"https://appsapionline.com/wallpapers/prem"});
                    return;
                }
                PremiumFragment.this.swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(PremiumFragment.this.getActivity(), PremiumFragment.this.getResources().getString(R.string.refresh_alert), 0).show();
            }
        });

        return inflate;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());
        this.progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.setMessage("Please wait...");
        this.progressDialog.setCancelable(false);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.searchforcategories, menu);
        MenuItem findItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Event.SEARCH);
        if (findItem != null) {
            this.searchView = (SearchView) findItem.getActionView();
        }
        if (this.searchView != null) {
            this.searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            this.queryTextListener = new OnQueryTextListener() {
                public boolean onQueryTextChange(String str) {
                    Log.i("onQueryTextChange", str);
                    PremiumFragment.this.filter(str);
                    return true;
                }

                public boolean onQueryTextSubmit(String str) {
                    Log.i("onQueryTextSubmit", str);
                    PremiumFragment.this.filter(str);
                    return true;
                }
            };
            this.searchView.setOnQueryTextListener(this.queryTextListener);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            return false;
        }
        this.searchView.setOnQueryTextListener(this.queryTextListener);
        return super.onOptionsItemSelected(menuItem);
    }

    public void filter(String str) {
        CharSequence toLowerCase = str.toLowerCase(Locale.getDefault());
        this.listRecentItem.clear();
        if (toLowerCase.length() == 0) {
            this.listRecentItem.addAll(this.arrayListItemCategory);
        } else {
            Iterator it = this.arrayListItemCategory.iterator();
            while (it.hasNext()) {
                RecentItem recentItem = (RecentItem) it.next();
                if (recentItem.getCategoryName().toLowerCase(Locale.getDefault()).contains(toLowerCase)) {
                    this.listRecentItem.add(recentItem);
                }
            }
        }
        setAdapterToListView();
    }

    public void clearData() {
        int size = this.listRecentItem.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.listRecentItem.remove(0);
            }
            this.AdapterRecent.notifyItemRangeRemoved(0, size);
        }
    }

    public void setAdapterToListView() {
        this.AdapterRecent = new PremiumWalllpaperAdpter(getActivity(), this.listRecentItem);
        this.recyclerView.setAdapter(this.AdapterRecent);
    }

    private void goToNextPage() {
        RecentItem recentItem = (RecentItem) this.listRecentItem.get(this.mPosition);
        List arrayList = new ArrayList();
        arrayList.add(recentItem);
        this.AdapterRecent.setSelectedItem(arrayList);
        Intent intent = new Intent(getActivity(), NewActivitySlideImage.class);
        intent.putExtra("POSITION_ID", this.mPosition);
        intent.putExtra("IMAGE_ARRAY", this.allArrayImage);
        intent.putExtra("IMAGE_CATNAME", this.allArrayImageCatName);
        intent.putExtra("migratedFrom", "premium");
        startActivity(intent);
    }
}
