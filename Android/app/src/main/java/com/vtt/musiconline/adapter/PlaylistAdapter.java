package com.vtt.musiconline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vtt.musiconline.model.Playlist;
import com.vtt.musiconline.R;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private List<Playlist> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public PlaylistAdapter(Context context, List<Playlist> data,ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
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
        Playlist playlist = mData.get(position);
        holder.tvName.setText(playlist.getNamePlaylist());
        holder.tvDes.setText(playlist.getNamePlaylist());
        Glide.with(context)
                .load(playlist.getBackgroundImagePlaylist())
//                .thumbnail(Glide.with(context).load(R.drawable.image))
//                .error(Glide.with(context).load(R.drawable.image))
                .into(holder.ivPlaylist);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.onItemPlayListClick(playlist, position);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public Playlist getItem(int id) {
        return mData.get(id);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlaylist;
        TextView tvName, tvDes;

        ViewHolder(View itemView) {
            super(itemView);
            ivPlaylist = itemView.findViewById(R.id.iv_ava);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDes = itemView.findViewById(R.id.tv_des);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemPlayListClick(Playlist playlist, int position);
    }
}
