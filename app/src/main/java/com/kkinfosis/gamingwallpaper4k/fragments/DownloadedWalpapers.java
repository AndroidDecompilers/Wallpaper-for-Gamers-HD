package com.kkinfosis.gamingwallpaper4k.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kkinfosis.gamingwallpaper4k.R;
import com.kkinfosis.gamingwallpaper4k.activities.DownloadViewer;
import com.kkinfosis.gamingwallpaper4k.activities.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Kundan Singh on 09-12-2018.
 */
public class DownloadedWalpapers extends Fragment {

    Toolbar toolbar;
    MainActivity mainActivity;
    RecyclerView recyclerView;
    public static List<File> data = new ArrayList<>();
    List<File> dataSelect = new ArrayList<>();
    File datasource;
    View empty;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloaded_wallpaper, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        setupToolbar();
        data.clear();
        dataSelect.clear();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        empty = view.findViewById(R.id.empty);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setNestedScrollingEnabled(true);
//        recyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.item_offset));
        datasource = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getResources().getString(R.string.saved_folder_name));
        if (datasource != null && datasource.exists() && datasource.list() != null && datasource.list().length > 0) {
            data.addAll(Arrays.asList(datasource.listFiles()));
            recyclerView.setAdapter(new DownlaodAdapter());
            view.findViewById(R.id.empty).setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
        }


//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener());
        return view;
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView txt_cat;
        View viewP, selected;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item);
            this.txt_cat = (TextView) view.findViewById(R.id.txt_cat);
            viewP = view.findViewById(R.id.linear);
            selected = view.findViewById(R.id.selected);
        }
    }


    class DownlaodAdapter extends RecyclerView.Adapter<ViewHolder> {


        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lsv_item_grid_wallpaper, viewGroup, false));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (getActivity() != null && isAdded()) {
                RequestManager with = Glide.with(getActivity());
                with.load(data.get(i)).placeholder((int) R.drawable.ic_thumbnail)
                        .into(viewHolder.imageView);

                viewHolder.txt_cat.setText("Downlaod " + i);
                viewHolder.viewP.setTag(i);
                viewHolder.viewP.setOnClickListener(clickListner);
                viewHolder.viewP.setOnLongClickListener(lonClickListner);
                if (dataSelect.contains(data.get(i))) {
                    viewHolder.selected.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.selected.setVisibility(View.GONE);
                }

            }
        }

        public int getItemCount() {
            return data.size();
        }

    }


    private void setupToolbar() {
        this.toolbar.setTitle(getString(R.string.app_name));
        this.toolbar.setSubtitle((int) R.string.drawer_downloads);
        this.mainActivity.setupNavigationDrawer(this.toolbar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

    private boolean lonclickActive = false;

    ActionMode.Callback callback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.download_menu_action, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            MenuItem menuItem = menu.findItem(R.id.select);
            if (data.size() == dataSelect.size()) {
                menuItem.setTitle(R.string.select_none);
            } else {
                menuItem.setTitle(R.string.select_all);
                menuItem.setIcon(R.drawable.ic_select_all);
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            int clicked = item.getItemId();
            switch (clicked) {
                case R.id.delete:
                    if (dataSelect.size() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.attiontion);
                        builder.setMessage(getResources().getQuantityString(R.plurals.image, dataSelect.size(), dataSelect.size()));
                        builder.setCancelable(false);
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mode.finish();
                            }
                        });

                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (File file : dataSelect) {
                                    if (file.exists()) {
                                        file.delete();
                                        recyclerView.getAdapter().notifyItemRemoved(data.indexOf(file));
                                        data.remove(file);
                                    }
                                }
                                if (data.size() == 0) {
                                    empty.setVisibility(View.VISIBLE);
                                }
                                mode.finish();
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        Toast.makeText(getActivity(), R.string.delete_select, Toast.LENGTH_SHORT).show();
                        mode.finish();
                    }
                    return true;
                case R.id.select:
                    if (item.getTitle().toString().equals(getString(R.string.select_all))) {
                        dataSelect.clear();
                        dataSelect.addAll(data);
                        item.setTitle(R.string.select_none);
                        item.setIcon(R.drawable.ic_deselect);
                    } else {
                        dataSelect.clear();
                        item.setTitle(R.string.select_all);
                        item.setIcon(R.drawable.ic_select_all);
                    }
                    mode.invalidate();
                    recyclerView.getAdapter().notifyDataSetChanged();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            dataSelect.clear();
            lonclickActive = false;
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    };

    private View.OnClickListener clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            if (lonclickActive) {
                if (dataSelect.contains(data.get(tag))) {
                    dataSelect.remove(data.get(tag));
                } else {
                    dataSelect.add(data.get(tag));
                }
                recyclerView.getAdapter().notifyItemChanged(tag);
            } else {
                Intent intent = new Intent(getActivity(), DownloadViewer.class);
                intent.putExtra("pos", tag);
                startActivity(intent);
            }
        }
    };
    private View.OnLongClickListener lonClickListner = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (!lonclickActive) {
                lonclickActive = true;
                ((AppCompatActivity) getActivity()).startSupportActionMode(callback);
                int id = (int) v.getTag();
                dataSelect.add(data.get(id));
                recyclerView.getAdapter().notifyItemChanged(id);
            } else {
                int id = (int) v.getTag();
                dataSelect.add(data.get(id));
                recyclerView.getAdapter().notifyItemChanged(id);
            }
            return true;
        }
    };

}
