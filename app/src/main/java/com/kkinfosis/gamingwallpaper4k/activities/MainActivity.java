package com.kkinfosis.gamingwallpaper4k.activities;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.fragments.AboutFragment;
import com.kkinfosis.gamingwallpaper4k.fragments.DownloadedWalpapers;
import com.kkinfosis.gamingwallpaper4k.fragments.GdprFragment;
import com.kkinfosis.gamingwallpaper4k.tab.FavoriteFragment;
import com.kkinfosis.gamingwallpaper4k.tab.RecentFragment;
import com.kkinfosis.gamingwallpaper4k.utilities.SharedPrefrenceHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private static final String COLLAPSING_TOOLBAR_FRAGMENT_TAG = "collapsing_toolbar";
    private static final String SELECTED_TAG = "selected_index";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    private boolean doubleBackToExitPressedOnce = false;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        this.navigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.sharedPrefrenceHelper = new SharedPrefrenceHelper(this);
        if (bundle != null) {
            this.navigationView.getMenu().getItem(bundle.getInt(SELECTED_TAG)).setChecked(true);
            this.navigationView.getMenu().getItem(0).setChecked(true);
            return;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new RecentFragment()).disallowAddToBackStack().commit();
        requestStoragePermission();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_about:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).disallowAddToBackStack().commit();
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_autowallpaper:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    startActivity(new Intent(this, TimerActivity.class));
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_favorite:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof FavoriteFragment)) {
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FavoriteFragment()).disallowAddToBackStack().commit();
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_gdpr:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GdprFragment()).disallowAddToBackStack().commit();
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_downloaded:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DownloadedWalpapers()).disallowAddToBackStack().commit();
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_privacy:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.privacy_policy_url))));
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_rate:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    String packageName = getPackageName();
                    StringBuilder stringBuilder;
                    try {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("market://details?id=");
                        stringBuilder.append(packageName);
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
                    } catch (ActivityNotFoundException unused) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("http://play.google.com/store/apps/details?id=");
                        stringBuilder.append(packageName);
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
                    }
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_recent:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RecentFragment)) {
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecentFragment(), COLLAPSING_TOOLBAR_FRAGMENT_TAG).commit();
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            case R.id.drawer_share:
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(getString(R.string.share_text));
                    stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                    stringBuilder2.append(getPackageName());
                    intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, "Share"));
                }
                this.drawerLayout.closeDrawer((int) GravityCompat.START);
                return true;
            default:
                return false;
        }
    }

    public void setupNavigationDrawer(Toolbar toolbar) {
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_open) {
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
        };
        this.drawerLayout.addDrawerListener(this.actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof FavoriteFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new RecentFragment(), COLLAPSING_TOOLBAR_FRAGMENT_TAG).commit();
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            }

        }

    }

    @TargetApi(16)
    private void requestStoragePermission() {
        Dexter.withActivity(this).withPermissions("android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    Log.d("Log", "permission granted");
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    MainActivity.this.showSettingsDialog();
                }
            }

            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            public void onError(DexterError dexterError) {
                Context applicationContext = MainActivity.this.getApplicationContext();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error occurred! ");
                stringBuilder.append(dexterError.toString());
                Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    private void showSettingsDialog() {
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.permisson_title);
        builder.setMessage((int) R.string.permisson_message);
        builder.setPositiveButton((CharSequence) "GOTO SETTINGS", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                MainActivity.this.openSettings();
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, 101);
    }
}
