package com.kkinfosis.gamingwallpaper4k.activities;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kkinfosis.gamingwallpaper4k.R;

import io.fabric.sdk.android.Fabric;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MyApplication extends Application {
    private static MyApplication instance;
    private static FirebaseAnalytics mFirebaseAnalytics;
    private SSLContext sslContext;

    public void handleUncaughtException(Thread thread, Throwable th) {

    }

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        instance = this;
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable th) {
                MyApplication.this.handleUncaughtException(thread, th);
            }
        });
    }

    public SSLContext getSslContext() {
        if (sslContext == null) {
            return sslContext = createSSLContext();
        }
        return sslContext;
    }

    public static FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    public static MyApplication getInstance() {
        return instance;
    }


    private SSLContext createSSLContext() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.sert);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            return context;

        } catch (Exception e) {

        }
        return null;
    }
}
