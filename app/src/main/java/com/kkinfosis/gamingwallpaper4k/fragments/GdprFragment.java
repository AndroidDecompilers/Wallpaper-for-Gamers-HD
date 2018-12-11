package com.kkinfosis.gamingwallpaper4k.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentForm.Builder;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class GdprFragment extends Fragment {
    ConsentForm form;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        URL url;
        View inflate = layoutInflater.inflate(R.layout.gdpr_fragment, viewGroup, false);
        ((Toolbar) inflate.findViewById(R.id.toolbar)).setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GdprFragment.this.startActivity(new Intent(GdprFragment.this.getActivity(), MainActivity.class));
            }
        });
        ConsentInformation.getInstance(getActivity()).requestConsentInfoUpdate(new String[]{"pub-7003127697936495"}, new ConsentInfoUpdateListener() {
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
            }

            public void onFailedToUpdateConsentInfo(String str) {
            }
        });
        try {
            url = new URL("https://sites.google.com/view/kkinfosis-pp/home");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        this.form = new Builder(getActivity(), url).withListener(new ConsentFormListener() {
            public void onConsentFormError(String str) {
            }

            public void onConsentFormOpened() {
            }

            public void onConsentFormLoaded() {
                GdprFragment.this.form.show();
            }

            public void onConsentFormClosed(ConsentStatus consentStatus, Boolean bool) {
                Bundle bundle = new Bundle();
                bundle.putString("npa", "1");
                GdprFragment.this.getActivity().finish();
            }
        }).withPersonalizedAdsOption().withNonPersonalizedAdsOption().build();
        this.form.load();
        this.form.show();
        return inflate;
    }
}
