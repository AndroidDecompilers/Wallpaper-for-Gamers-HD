package com.kkinfosis.gamingwallpaper4k.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kkinfosis.gamingwallpaper4k.models.NotificationHeader;
import com.kkinfosis.gamingwallpaper4k.models.NotificationModel;
import com.kkinfosis.gamingwallpaper4k.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationExpandbleListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private HashMap<NotificationHeader, ArrayList<NotificationModel>> _listDataChild;
    private List<NotificationHeader> _listDataHeader;

    public long getChildId(int i, int i2) {
        return (long) i2;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public NotificationExpandbleListAdapter(Context context, List<NotificationHeader> list, HashMap<NotificationHeader, ArrayList<NotificationModel>> hashMap) {
        this._context = context;
        this._listDataHeader = list;
        this._listDataChild = hashMap;
    }

    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    public int getChildrenCount(int i) {
        return ((ArrayList) this._listDataChild.get(this._listDataHeader.get(i))).size();
    }

    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    public Object getChild(int i, int i2) {
        return (NotificationModel) ((ArrayList) this._listDataChild.get(this._listDataHeader.get(i))).get(i2);
    }

    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        CharSequence notificationTitle = ((NotificationHeader) this._listDataHeader.get(i)).getNotificationTitle();
        if (view == null) {
            view = ((LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_group, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_group_name);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_date);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_expand);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(notificationTitle);
        textView2.setText(((NotificationHeader) this._listDataHeader.get(i)).getDate());
        View findViewById = view.findViewById(R.id.divider);
        if (z) {
            imageView.setImageResource(R.drawable.ic_collapse);
            findViewById.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(R.drawable.ic_expand);
            findViewById.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        NotificationModel notificationModel = (NotificationModel) getChild(i, i2);
        if (view == null) {
            view = ((LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_child_name);
        View findViewById = view.findViewById(R.id.divider);
        ImageView imageView = (ImageView) view.findViewById(R.id.large_icon);
        if (i2 == ((ArrayList) this._listDataChild.get(this._listDataHeader.get(i))).size() - 1) {
            findViewById.setVisibility(View.VISIBLE);
        }
        Glide.with(_context).load(notificationModel.getLargeIcon()).into(imageView);
        textView.setText(notificationModel.getNotificationMessage());
        return view;
    }
}
