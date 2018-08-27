package com.vtt.musiconline.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vtt.musiconline.R;
import com.vtt.musiconline.model.ListSong;

import java.util.ArrayList;
import java.util.List;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder> {

    private List<ListSong> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public ListSongAdapter(Activity activity, List<ListSong> data, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(activity);
        this.mData = data;
        this.activity = activity;
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListSong listSong = mData.get(position);
        holder.tvName.setText(listSong.getNameSong());
        holder.tvDes.setText(listSong.getSingerSong());
        Glide.with(activity)
                .load(listSong.getImageSong())
                .into(holder.ivSong);
        holder.itemView.setOnClickListener(v -> {
            if (mClickListener != null) mClickListener.onItemSongClick(listSong);
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public ListSong getItem(int id) {
        return mData.get(id);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSong;
        TextView tvName, tvDes;

        ViewHolder(View itemView) {
            super(itemView);
            ivSong = itemView.findViewById(R.id.iv_ava);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDes = itemView.findViewById(R.id.tv_des);
        }
    }

    public interface ItemClickListener {
        void onItemSongClick(ListSong listSong);
    }

    public void filterList(ArrayList<ListSong> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }
}
