package com.kunminx.linkage;
/*
 * Copyright (c) 2018-2019. KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.bean.LinkageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/29
 */
public class LinkageLevelTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mLayoutId;
    private int mTitleLayoutId;
    private Context mContext;
    private OnItemClickListener mListener;
    private List<LinkageItem> mItems;
    private static final int IS_HEADER = 0;
    private static final int IS_NORMAL = 1;

    public LinkageLevelTwoAdapter(int layoutId, int titleLayoutId, List<LinkageItem> items, OnItemClickListener listener) {
        mLayoutId = layoutId;
        mTitleLayoutId = titleLayoutId;
        mItems = items;
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mListener = listener;
    }

    public void refreshList(List<LinkageItem> list) {
        mItems.clear();
        if (list != null) {
            mItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).isHeader) {
            return IS_HEADER;
        } else {
            return IS_NORMAL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(mTitleLayoutId, parent, false);
            return new LevelTwoTitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            return new LevelTwoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LinkageItem linkageItem = mItems.get(holder.getAdapterPosition());
        if (linkageItem.isHeader) {
            LevelTwoTitleViewHolder titleViewHolder = (LevelTwoTitleViewHolder) holder;
            titleViewHolder.mTvHeader.setText(linkageItem.header);
        } else {
            final LevelTwoViewHolder viewHolder = (LevelTwoViewHolder) holder;
            viewHolder.mTvTitle.setText(linkageItem.t.getTitle());
            viewHolder.mTvContent.setText(linkageItem.t.getContent());
            viewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(viewHolder, viewHolder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class LevelTwoViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLayout;
        private TextView mTvTitle;
        private TextView mTvContent;

        public LevelTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.level_2_item);
            mTvTitle = (TextView) itemView.findViewById(R.id.level_2_title);
            mTvContent = (TextView) itemView.findViewById(R.id.level_2_content);
        }
    }

    class LevelTwoTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvHeader;

        public LevelTwoTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvHeader = (TextView) itemView.findViewById(R.id.level_2_header);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(LevelTwoViewHolder holder, int position);
    }
}
