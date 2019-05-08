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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.contract.ILevelOneAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/29
 */
public class LinkageLevelOneAdapter extends RecyclerView.Adapter<LinkageLevelOneAdapter.LevelOneViewHolder> {

    private List<String> mStrings;
    private List<TextView> mTextViews = new ArrayList<>();
    private Context mContext;

    private ILevelOneAdapterConfig mConfig;


    public LinkageLevelOneAdapter(List<String> strings, ILevelOneAdapterConfig config) {
        mStrings = strings;
        if (mStrings == null) {
            mStrings = new ArrayList<>();
        }
        mConfig = config;
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
    public LevelOneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(mConfig.getLayoutId(), parent, false);
        return new LevelOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LevelOneViewHolder holder, int position) {

        holder.mTvGroup.setText(mStrings.get(holder.getAdapterPosition()));
        if (!mTextViews.contains(holder.mTvGroup)) {
            mTextViews.add(holder.mTvGroup);
        }
        if (mTextViews != null && mTextViews.size() == mStrings.size()) {
            selectItem(0);
        }
        holder.mLayout.setSelected(true);
        mConfig.onBindViewHolder(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());

        /*holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public void selectItem(int position) {
        for (int i = 0; i < mStrings.size(); i++) {
            if (position == i) {
                mConfig.onItemSelected(true, mTextViews.get(i));
//                mTextViews.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.colorPurple));
//                mTextViews.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                mTextViews.get(i).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTextViews.get(i).setFocusable(true);
                mTextViews.get(i).setFocusableInTouchMode(true);
                mTextViews.get(i).setMarqueeRepeatLimit(-1);
            } else {
                mConfig.onItemSelected(false, mTextViews.get(i));
//                mTextViews.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
//                mTextViews.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.colorGray));
                mTextViews.get(i).setEllipsize(TextUtils.TruncateAt.END);
                mTextViews.get(i).setFocusable(false);
                mTextViews.get(i).setFocusableInTouchMode(false);
                mTextViews.get(i).setMarqueeRepeatLimit(0);
            }
        }
    }

    public class LevelOneViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvGroup;
        private LinearLayout mLayout;

        LevelOneViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvGroup = (TextView) itemView.findViewById(mConfig.getTextViewId());
            mLayout = (LinearLayout) itemView.findViewById(mConfig.getRootViewId());
        }
    }
}
