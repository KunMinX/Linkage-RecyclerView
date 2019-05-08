package com.kunminx.linkage.adapter;
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
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.bean.LinkageItem;
import com.kunminx.linkage.contract.ILevelSecondaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/29
 */
public class LinkageLevelSecondaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LinkageItem> mItems;
    private static final int IS_HEADER = 0;
    private static final int IS_LINEAR = 1;
    private static final int IS_GRID = 2;

    private ILevelSecondaryAdapterConfig mConfig;

    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mViews = new SparseArray<>();
    private View mHeaderConvertView;
    private View mConvertView;


    public boolean isGridMode() {
        return mConfig.isGridMode();
    }

    public void setGridMode(boolean isGridMode) {
        mConfig.setGridMode(isGridMode);
    }

    public LinkageLevelSecondaryAdapter(List<LinkageItem> items, ILevelSecondaryAdapterConfig config) {
        mItems = items;
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mConfig = config;
    }

    public void refreshList(List<LinkageItem> list) {
        mItems.clear();
        if (list != null) {
            mItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    //TODO load more data...
    public void refreshListLoadMore(List<LinkageItem> list) {
        if (list != null) {
            mItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).isHeader) {
            return IS_HEADER;
        } else if (mConfig.isGridMode()) {
            return IS_GRID;
        } else {
            return IS_LINEAR;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mConfig.setContext(mContext);
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getHeaderLayoutId(), parent, false);
            mHeaderConvertView = view;
            return new LevelSecondaryTitleViewHolder(view);
        } else if (viewType == IS_GRID) {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getGridLayoutId(), parent, false);
            mConvertView = view;
            return new LevelSecondaryViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getLinearLayoutId(), parent, false);
            mConvertView = view;
            return new LevelSecondaryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final LinkageItem linkageItem = mItems.get(holder.getAdapterPosition());
        if (linkageItem.isHeader) {
            LevelSecondaryTitleViewHolder titleViewHolder = (LevelSecondaryTitleViewHolder) holder;
            titleViewHolder.mTvHeader.setText(linkageItem.header);
        } else {
            final LevelSecondaryViewHolder viewHolder = (LevelSecondaryViewHolder) holder;
            viewHolder.mTvTitle.setText(linkageItem.t.getTitle());

            mConfig.onBindViewHolder(viewHolder, linkageItem, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class LevelSecondaryViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLayout;
        private TextView mTvTitle;

        public LevelSecondaryViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(mConfig.getRootViewId());
            mTvTitle = (TextView) itemView.findViewById(mConfig.getTextViewId());
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

    public class LevelSecondaryTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvHeader;

        public LevelSecondaryTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvHeader = (TextView) itemView.findViewById(mConfig.getHeaderViewId());
        }

        public <T extends View> T getView(int viewId) {
            View view = mHeaderViews.get(viewId);
            if (view == null) {
                view = mHeaderConvertView.findViewById(viewId);
                mHeaderViews.put(viewId, view);
            }
            return (T) view;
        }
    }

}
