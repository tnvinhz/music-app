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
import com.vtt.musiconline.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public AlbumAdapter(Activity activity, List<Album> data, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(activity);
        this.mData = data;
        this.activity = activity;
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = mData.get(position);
        holder.tvName.setText(album.getNameAlbum());
        holder.tvDes.setText(album.getSingerNameAlbum());
        Glide.with(activity)
                .load(album.getImageAlbum())
//                .thumbnail(Glide.with(context).load(R.drawable.image))
//                .error(Glide.with(context).load(R.drawable.image))
                .into(holder.ivAlbum);
        holder.itemView.setOnClickListener(v -> {
            if (mClickListener != null) mClickListener.onItemAlbumClick(album);
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAlbum;
        TextView tvName, tvDes;

        ViewHolder(View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.iv_ava);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDes = itemView.findViewById(R.id.tv_des);
        }
    }

    public interface ItemClickListener {
        void onItemAlbumClick(Album album);
    }
}
