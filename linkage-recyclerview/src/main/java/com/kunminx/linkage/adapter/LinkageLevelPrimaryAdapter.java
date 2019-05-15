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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.adapter.viewholder.LevelPrimaryViewHolder;
import com.kunminx.linkage.contract.ILevelPrimaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/29
 */
public class LinkageLevelPrimaryAdapter extends RecyclerView.Adapter<LevelPrimaryViewHolder> {

    private List<String> mStrings;
    private List<View> mGroupTitleViews = new ArrayList<>();
    private Context mContext;

    private ILevelPrimaryAdapterConfig mConfig;
    private OnLinkageListener mLinkageListener;
    private OnItemClickListener mItemClickListener;

    public List<String> getStrings() {
        return mStrings;
    }

    public ILevelPrimaryAdapterConfig getConfig() {
        return mConfig;
    }

    public LinkageLevelPrimaryAdapter(List<String> strings, ILevelPrimaryAdapterConfig config,
                                      OnLinkageListener linkageListener, OnItemClickListener onItemClickListener) {
        mStrings = strings;
        if (mStrings == null) {
            mStrings = new ArrayList<>();
        }
        mConfig = config;
        mLinkageListener = linkageListener;
        mItemClickListener = onItemClickListener;
    }

    public void refreshList(List<String> list) {
        mStrings.clear();
        if (list != null) {
            mStrings.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LevelPrimaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mConfig.setContext(mContext);
        View view = LayoutInflater.from(mContext).inflate(mConfig.getLayoutId(), parent, false);
        return new LevelPrimaryViewHolder(view, mConfig);
    }

    @Override
    public void onBindViewHolder(@NonNull final LevelPrimaryViewHolder holder, int position) {

        // for textView MARQUEE available.
        holder.mLayout.setSelected(true);

        mConfig.onBindViewHolder(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinkageListener != null) {
                    mLinkageListener.onLinkageClick(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });

        if (!mGroupTitleViews.contains(holder.mGroupTitle)) {
            mGroupTitleViews.add(holder.mGroupTitle);
        }
        if (mGroupTitleViews != null && mGroupTitleViews.size() == mStrings.size()) {
            selectItem(0);
        }
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public void selectItem(int position) {
        for (int i = 0; i < mStrings.size(); i++) {
            mConfig.onItemSelected(position == i, mGroupTitleViews.get(i));
        }
    }

    /**
     * only for linkage logic of level primary adapter. not use for outside logic
     * users can archive onLinkageClick in configs instead.
     */
    public interface OnLinkageListener {
        void onLinkageClick(LevelPrimaryViewHolder holder, String title, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String title, int position);
    }
}
