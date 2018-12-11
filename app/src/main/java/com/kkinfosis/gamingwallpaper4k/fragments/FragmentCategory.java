package com.kkinfosis.gamingwallpaper4k.fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.adapters.AdapterCategory;
import com.kkinfosis.gamingwallpaper4k.database.DatabaseHandlerCategory;
import com.kkinfosis.gamingwallpaper4k.json.JsonConfig;
import com.kkinfosis.gamingwallpaper4k.json.JsonUtils;
import com.kkinfosis.gamingwallpaper4k.models.CategoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressLint("WrongConstant")
public class FragmentCategory extends Fragment {
    static List<CategoryItem> arrayCategoryItem;
    private static ArrayList<CategoryItem> arrayListCategoryItem;
    AdapterCategory adapterCategory;
    public DatabaseHandlerCategory databaseHandlerCate;
    ProgressBar progressBar;
    private OnQueryTextListener queryTextListener;
    RecyclerView recyclerView;
    private SearchView searchView = null;
    SwipeRefreshLayout swipeRefreshLayout = null;
    private boolean dataInited = false;

    public void initData() {
        if (!dataInited) {
            if (JsonUtils.isNetworkAvailable(getActivity())) {
                new MyTask().execute(new String[]{"https://appsapionline.com/wallpapers/cats"});
            } else {
                arrayCategoryItem = this.databaseHandlerCate.getAllData();
                if (arrayCategoryItem.size() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.network_first_load), 0).show();
                }
            }
            setAdapterToListView();
            dataInited = true;
        }
    }

    private class MyTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            FragmentCategory.this.progressBar.setVisibility(0);
        }

        protected String doInBackground(String... strArr) {
            return JsonUtils.getJSONString(strArr[0]);
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            int i = 0;
            progressBar.setVisibility(View.GONE);
            if (str == null || str.length() == 0) {
                Toast.makeText(FragmentCategory.this.getActivity(), FragmentCategory.this.getResources().getString(R.string.network_error), 0).show();
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(str);
                while (i < jSONArray.length()) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    CategoryItem categoryItem = new CategoryItem();
                    categoryItem.setCategoryName(jSONObject.getString("category_name"));
                    categoryItem.setCategoryId(jSONObject.getString("cid"));
                    categoryItem.setCategoryImage(jSONObject.getString(JsonConfig.CATEGORY_IMAGE_URL));
                    FragmentCategory.arrayCategoryItem.add(categoryItem);
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FragmentCategory.arrayListCategoryItem.addAll(FragmentCategory.arrayCategoryItem);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public class GridSpacingItemDecoration extends ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int i, int i2, boolean z) {
            this.spanCount = i;
            this.spacing = i2;
            this.includeEdge = z;
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int i = childAdapterPosition % this.spanCount;
            if (this.includeEdge) {
                rect.left = this.spacing - ((this.spacing * i) / this.spanCount);
                rect.right = ((i + 1) * this.spacing) / this.spanCount;
                if (childAdapterPosition < this.spanCount) {
                    rect.top = this.spacing;
                }
                rect.bottom = this.spacing;
                return;
            }
            rect.left = (this.spacing * i) / this.spanCount;
            rect.right = this.spacing - (((i + 1) * this.spacing) / this.spanCount);
            if (childAdapterPosition >= this.spanCount) {
                rect.top = this.spacing;
            }
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_category, viewGroup, false);
        setHasOptionsMenu(true);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progressBar);
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue, R.color.red);
        LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayCategoryItem = new ArrayList();
        arrayListCategoryItem = new ArrayList();
        this.databaseHandlerCate = new DatabaseHandlerCategory(getActivity());
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (JsonUtils.isNetworkAvailable(FragmentCategory.this.getActivity())) {
                    FragmentCategory.this.swipeRefreshLayout.setRefreshing(false);
                    FragmentCategory.this.clearData();
                    new MyTask().execute(new String[]{"https://appsapionline.com/wallpapers/cats"});
                    return;
                }
                FragmentCategory.this.swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(FragmentCategory.this.getActivity(), FragmentCategory.this.getResources().getString(R.string.refresh_alert), 0).show();
            }
        });

        return inflate;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public void onResume() {
        super.onResume();
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
                    FragmentCategory.this.filter(str);
                    return true;
                }

                public boolean onQueryTextSubmit(String str) {
                    Log.i("onQueryTextSubmit", str);
                    FragmentCategory.this.filter(str);
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

    public void clearData() {
        int size = arrayCategoryItem.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                arrayCategoryItem.remove(0);
            }
            this.adapterCategory.notifyItemRangeRemoved(0, size);
        }
    }

    public void setAdapterToListView() {
        this.adapterCategory = new AdapterCategory(getActivity(), arrayCategoryItem);
        this.recyclerView.setAdapter(adapterCategory);
    }


    public void filter(String str) {
        CharSequence toLowerCase = str.toLowerCase(Locale.getDefault());
        arrayCategoryItem.clear();
        if (toLowerCase.length() == 0) {
            arrayCategoryItem.addAll(arrayListCategoryItem);
        } else {
            Iterator it = arrayListCategoryItem.iterator();
            while (it.hasNext()) {
                CategoryItem categoryItem = (CategoryItem) it.next();
                if (categoryItem.getCategoryName().toLowerCase(Locale.getDefault()).contains(toLowerCase)) {
                    arrayCategoryItem.add(categoryItem);
                }
            }
        }
        setAdapterToListView();
    }

    private int dpToPx(int i) {
        return Math.round(TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics()));
    }
}
