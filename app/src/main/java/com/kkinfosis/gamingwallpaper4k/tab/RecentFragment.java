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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.MainActivity;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentCategory;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentRecent;
import com.kkinfosis.gamingwallpaper4k.fragments.FragmentRecentRandom;
import com.kkinfosis.gamingwallpaper4k.fragments.PremiumFragment;
import com.kkinfosis.gamingwallpaper4k.utilities.AppBarLayoutBehavior;
import com.kkinfosis.gamingwallpaper4k.utilities.SharedPrefrenceHelper;

public class RecentFragment extends Fragment {
    public static int double_tab = 4;
    public static int single_tab = 1;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private MainActivity mainActivity;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    private Toolbar toolbar;

    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentRecent fragmentRecent = new FragmentRecent();
    FragmentRecentRandom fragmentRecentRandom = new FragmentRecentRandom();
    PremiumFragment premiumFragment = new PremiumFragment();

    int[] iconArrayUnnselectd = new int[]{R.drawable.recent, R.drawable.layers, R.drawable.trending, R.drawable.premium};
    int[] iconArraynselectd = new int[]{R.drawable.recentwhite, R.drawable.layerswhite, R.drawable.trendingwhite, R.drawable.premiumwhite};

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return fragmentRecent;
                case 1:
                    return fragmentCategory;
                case 2:
                    return fragmentRecentRandom;
                case 3:
                    return premiumFragment;
                default:
                    return null;
            }
        }

        public int getCount() {
            return RecentFragment.double_tab;
        }

        public CharSequence getPageTitle(int i) {
            switch (i) {
                case 0:
                    return RecentFragment.this.getResources().getString(R.string.tab_recent);
                case 1:
                    return RecentFragment.this.getResources().getString(R.string.tab_category);
                case 2:
                    return RecentFragment.this.getResources().getString(R.string.tab_favorite);
                case 3:
                    return RecentFragment.this.getResources().getString(R.string.tab_premium);
                default:
                    return null;
            }
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.tab_layout1, viewGroup, false);
        ((LayoutParams) ((AppBarLayout) inflate.findViewById(R.id.tab_appbar_layout)).getLayoutParams()).setBehavior(new AppBarLayoutBehavior());
        tabLayout = (TabLayout) inflate.findViewById(R.id.tabs);
        viewPager = (ViewPager) inflate.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                switch (i) {
                    case 1:
                        fragmentCategory.initData();
                        return;
                    case 2:
                        fragmentRecentRandom.initData();
                        return;
                    case 3:
                        premiumFragment.initData();
                        return;
                }

            }
        });
        this.toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        setupToolbar();
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            public void run() {
                RecentFragment.tabLayout.setupWithViewPager(RecentFragment.viewPager);
                RecentFragment.this.setUpIcons();
            }
        });
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            public void onTabSelected(Tab tab) {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    Tab tab1 = tabLayout.getTabAt(i);
                    if (tab == tab1) {
                        tab1.setIcon(iconArraynselectd[i]);
                    } else {
                        tab1.setIcon(iconArrayUnnselectd[i]);
                    }
                }
            }

            @SuppressLint({"ResourceAsColor"})
            public void onTabUnselected(Tab tab) {
            }

            @SuppressLint({"ResourceAsColor"})
            public void onTabReselected(Tab tab) {
            }
        });
        viewPager.setOffscreenPageLimit(4);
        return inflate;
    }

    public void setUpIcons() {
        tabLayout.getTabAt(0).setIcon((int) R.drawable.recent);
        tabLayout.getTabAt(1).setIcon((int) R.drawable.layers);
        tabLayout.getTabAt(2).setIcon((int) R.drawable.trending);
        tabLayout.getTabAt(3).setIcon((int) R.drawable.premium);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mainActivity.setupNavigationDrawer(this.toolbar);
        this.sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());
    }

    private void setupToolbar() {
        this.toolbar.setTitle(getString(R.string.app_name));
        Log.d("Log", "Tab Layout is Enabled");
        this.mainActivity.setSupportActionBar(this.toolbar);
    }

}
