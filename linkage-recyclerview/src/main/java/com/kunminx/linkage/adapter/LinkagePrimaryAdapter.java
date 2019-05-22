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

import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/29
 */
public class LinkagePrimaryAdapter extends RecyclerView.Adapter<LinkagePrimaryViewHolder> {

    private List<String> mStrings;
    private List<View> mGroupTitleViews = new ArrayList<>();
    private Context mContext;
    private View mView;

    private ILinkagePrimaryAdapterConfig mConfig;
    private OnLinkageListener mLinkageListener;

    public List<String> getStrings() {
        return mStrings;
    }

    public ILinkagePrimaryAdapterConfig getConfig() {
        return mConfig;
    }

    public LinkagePrimaryAdapter(List<String> strings, ILinkagePrimaryAdapterConfig config,
                                 OnLinkageListener linkageListener) {
        mStrings = strings;
        if (mStrings == null) {
            mStrings = new ArrayList<>();
        }
        mConfig = config;
        mLinkageListener = linkageListener;
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
    public LinkagePrimaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mConfig.setContext(mContext);
        mView = LayoutInflater.from(mContext).inflate(mConfig.getLayoutId(), parent, false);
        return new LinkagePrimaryViewHolder(mView, mConfig);
    }

    @Override
    public void onBindViewHolder(@NonNull final LinkagePrimaryViewHolder holder, int position) {

        // for textView MARQUEE available.
        holder.mLayout.setSelected(true);

        mConfig.onBindViewHolder(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinkageListener != null) {
                    mLinkageListener.onLinkageClick(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
                mConfig.onItemClick(v, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });

        if (!mGroupTitleViews.contains(holder.mGroupTitle)) {
            mGroupTitleViews.add(holder.mGroupTitle);
        }
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public void selectItem(int position) {
        for (int i = 0; i < mStrings.size(); i++) {
            // mGroupTitleViews's views are not loaded all by one time if too much,
            // so we need to judge every time to avoid out of index.
            if (i < mGroupTitleViews.size()) {
                mConfig.onItemSelected(position == i, mGroupTitleViews.get(i));
            }
        }
    }

    /**
     * only for linkage logic of level primary adapter. not use for outside logic
     * users can archive onLinkageClick in configs instead.
     */
    public interface OnLinkageListener {
        void onLinkageClick(LinkagePrimaryViewHolder holder, String title, int position);
    }
}
