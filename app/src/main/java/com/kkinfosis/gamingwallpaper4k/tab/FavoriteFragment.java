package com.kkinfosis.gamingwallpaper4k.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkinfosis.gamingwallpaper4k.activities.MainActivity;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentFavorite;
import com.kkinfosis.gamingwallpaper4k.utilities.AppBarLayoutBehavior;
import com.kkinfosis.gamingwallpaper4k.R;

@SuppressLint("WrongConstant")
public class FavoriteFragment extends Fragment {
    public static int int_items = 1;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private MainActivity mainActivity;
    private Toolbar toolbar;

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return i != 0 ? null : new FragmentFavorite();
        }

        public int getCount() {
            return FavoriteFragment.int_items;
        }

        public CharSequence getPageTitle(int i) {
            return i != 0 ? null : FavoriteFragment.this.getResources().getString(R.string.tab_favorite);
        }
    }

    private void setUpIcons() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }


    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.tab_layout, viewGroup, false);
        ((LayoutParams) ((AppBarLayout) inflate.findViewById(R.id.tab_appbar_layout)).getLayoutParams()).setBehavior(new AppBarLayoutBehavior());
        tabLayout = (TabLayout) inflate.findViewById(R.id.tabs);
        viewPager = (ViewPager) inflate.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
            }
        });
        this.toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        setupToolbar();
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(0);
        tabLayout.post(new Runnable() {
            public void run() {
                FavoriteFragment.tabLayout.setupWithViewPager(FavoriteFragment.viewPager);
                FavoriteFragment.this.setUpIcons();
            }
        });
        tabLayout.setVisibility(8);
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            public void onTabSelected(Tab tab) {
                int selectedTabPosition = FavoriteFragment.tabLayout.getSelectedTabPosition();
                if (selectedTabPosition == 0) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.recentwhite);
                }
                if (selectedTabPosition == 1) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.layerswhite);
                }
                if (selectedTabPosition == 2) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.trendingwhite);
                }
            }

            @SuppressLint({"ResourceAsColor"})
            public void onTabUnselected(Tab tab) {
                int selectedTabPosition = FavoriteFragment.tabLayout.getSelectedTabPosition();
                if (selectedTabPosition == 0) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.recent);
                }
                if (selectedTabPosition == 1) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.layers);
                }
                if (selectedTabPosition == 2) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.trending);
                }
            }

            @SuppressLint({"ResourceAsColor"})
            public void onTabReselected(Tab tab) {
                int selectedTabPosition = FavoriteFragment.tabLayout.getSelectedTabPosition();
                if (selectedTabPosition == 0) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.recent);
                }
                if (selectedTabPosition == 1) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.layers);
                }
                if (selectedTabPosition == 2) {
                    FavoriteFragment.tabLayout.getTabAt(selectedTabPosition).setIcon((int) R.drawable.trending);
                }
            }
        });
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mainActivity.setupNavigationDrawer(this.toolbar);
    }

    private void setupToolbar() {
        this.toolbar.setTitle(getString(R.string.app_name));
        this.toolbar.setSubtitle((int) R.string.drawer_favorite);
        this.mainActivity.setSupportActionBar(this.toolbar);
    }
}
