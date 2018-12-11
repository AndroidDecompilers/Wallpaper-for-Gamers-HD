package com.kkinfosis.gamingwallpaper4k.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkinfosis.gamingwallpaper4k.activities.MainActivity;
import com.kkinfosis.gamingwallpaper4k.R;

public class AboutFragment extends Fragment {
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.dmca_fragment, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.txt_privacy);
        ((Toolbar) inflate.findViewById(R.id.toolbar)).setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AboutFragment.this.startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AboutFragment.this.getString(R.string.privacy_policy_url))));
            }
        });
        return inflate;
    }
}
