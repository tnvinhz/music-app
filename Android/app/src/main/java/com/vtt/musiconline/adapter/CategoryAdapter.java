package com.vtt.musiconline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vtt.musiconline.R;
import com.vtt.musiconline.model.Category;
import com.vtt.musiconline.model.Playlist;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public CategoryAdapter(Context context, List<Category> data, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.mClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = mData.get(position);
        holder.tvName.setText(category.getNameCategory());
        Glide.with(context)
                .load(category.getImageCategory())
                .thumbnail(Glide.with(context).load(R.drawable.image))
                .error(Glide.with(context).load(R.drawable.image))
                .into(holder.ivCate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.onItemCateClick(category, position);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public Category getItem(int id) {
        return mData.get(id);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCate;
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            ivCate = itemView.findViewById(R.id.iv_ava);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemCateClick(Category category, int position);
    }
}
