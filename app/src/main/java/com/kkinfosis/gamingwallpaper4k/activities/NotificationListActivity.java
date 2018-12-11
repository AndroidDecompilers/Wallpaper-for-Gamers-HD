package com.kkinfosis.gamingwallpaper4k.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kkinfosis.gamingwallpaper4k.adapters.NotificationExpandbleListAdapter;
import com.kkinfosis.gamingwallpaper4k.models.NotificationHeader;
import com.kkinfosis.gamingwallpaper4k.models.NotificationModel;
import com.kkinfosis.gamingwallpaper4k.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationListActivity extends AppCompatActivity {
    HashMap<NotificationHeader, ArrayList<NotificationModel>> _listDataChild = new HashMap();
    ExpandableListView expandableListView;
    ArrayList<NotificationHeader> headerlist = new ArrayList();
    ArrayList<String> itemList = new ArrayList();
    TextView mTvNoNotification;
    NotificationExpandbleListAdapter notificationExpandbleListAdapter;
    ArrayList<NotificationModel> notificationModelArrayList = new ArrayList();
    String playerId = "";
    ProgressBar progressBar;
    LinearLayout progressbarContainer;
    Toolbar toolbar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_notification_list);
        initView();
        getNotificationList();
    }

    private void initView() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar.setTitle((CharSequence) "Notification");
//        OneSignal.idsAvailable(new IdsAvailableHandler() {
//            public void idsAvailable(String str, String str2) {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("User:");
//                stringBuilder.append(str);
//                Log.d("farman", stringBuilder.toString());
//                if (str2 != null) {
//                    NotificationListActivity.this.playerId = str2;
//                }
//            }
//        });
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        this.progressbarContainer = (LinearLayout) findViewById(R.id.progressbar_container);
        this.mTvNoNotification = (TextView) findViewById(R.id.no_notification);
        this.progressBar = (ProgressBar) findViewById(R.id.progresbar);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                NotificationListActivity.this.onBackPressed();
            }
        });
    }

    private void getNotificationList() {
//        Volley.newRequestQueue(this).add(new StringRequest(0, "https://onesignal.com/api/v1/notifications?app_id=3bb270bf-3b17-4cbf-9e63-637cef75b1d9", new Listener<String>() {
//            public void onResponse(String str) {
//                Log.d("Response", str);
//                try {
//                    JSONArray optJSONArray = new JSONObject(str).optJSONArray("notifications");
//                    if (optJSONArray.length() > 0) {
//                        for (int i = 0; i < optJSONArray.length(); i++) {
//                            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
//                            String optString = optJSONObject.optJSONObject("headings").optString("en");
//                            if (optString == null || optString.equals("")) {
//                                optString = "No Title";
//                            }
//                            String optString2 = optJSONObject.optJSONObject("contents").optString("en");
//                            if (optString2 == null || optString2.equals("")) {
//                                optString = "No Content";
//                            }
//                            String optString3 = optJSONObject.optString("large_icon");
//                            JSONArray optJSONArray2 = optJSONObject.optJSONArray("include_player_ids");
//                            if (optJSONArray2 != null) {
//                                for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
//                                    Log.d("farman", optJSONArray2.optString(i2));
//                                    if (optJSONArray2.optString(i2).equals(NotificationListActivity.this.playerId)) {
//                                        Log.d("farman", "equals");
//                                        NotificationListActivity.this.headerlist.add(new NotificationHeader(optString, ""));
//                                        ArrayList arrayList = new ArrayList();
//                                        arrayList.add(new NotificationModel(optString, optString2, 2, optString3));
//                                        NotificationListActivity.this._listDataChild.put(NotificationListActivity.this.headerlist.get(i), arrayList);
//                                    }
//                                }
//                            }
//                        }
//                        if (NotificationListActivity.this.headerlist.size() > 0) {
//                            NotificationListActivity.this.notificationExpandbleListAdapter = new NotificationExpandbleListAdapter(NotificationListActivity.this, NotificationListActivity.this.headerlist, NotificationListActivity.this._listDataChild);
//                            NotificationListActivity.this.expandableListView.setAdapter(NotificationListActivity.this.notificationExpandbleListAdapter);
//                            NotificationListActivity.this.progressbarContainer.setVisibility(8);
//                            NotificationListActivity.this.expandableListView.setVisibility(0);
//                            return;
//                        }
//                        NotificationListActivity.this.progressBar.setVisibility(8);
//                        NotificationListActivity.this.mTvNoNotification.setVisibility(0);
//                        return;
//                    }
//                    NotificationListActivity.this.progressBar.setVisibility(8);
//                    NotificationListActivity.this.mTvNoNotification.setVisibility(0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new ErrorListener() {
//            public void onErrorResponse(VolleyError volleyError) {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("error => ");
//                stringBuilder.append(volleyError.toString());
//                Log.d("ERROR", stringBuilder.toString());
//            }
//        }) {
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> hashMap = new HashMap();
//                hashMap.put("Authorization", "Basic MzNlMTk5NDQtNjBhMi00MWVhLTg5MDAtY2Y0N2FlMGMxNWU2");
//                return hashMap;
//            }
//        });
    }
}
