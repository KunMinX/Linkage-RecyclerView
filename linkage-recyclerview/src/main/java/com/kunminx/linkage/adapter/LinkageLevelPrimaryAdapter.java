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
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.contract.ILevelPrimaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/29
 */
public class LinkageLevelPrimaryAdapter extends RecyclerView.Adapter<LinkageLevelPrimaryAdapter.LevelPrimaryViewHolder> {

    private List<String> mStrings;
    private List<TextView> mTextViews = new ArrayList<>();
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews = new SparseArray<>();

    private ILevelPrimaryAdapterConfig mConfig;
    private OnLinkageListener mListener;


    public LinkageLevelPrimaryAdapter(List<String> strings, ILevelPrimaryAdapterConfig config, OnLinkageListener listener) {
        mStrings = strings;
        if (mStrings == null) {
            mStrings = new ArrayList<>();
        }
        mConfig = config;
        mListener = listener;
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
        mConvertView = view;
        return new LevelPrimaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelPrimaryViewHolder holder, int position) {

        holder.mTvGroup.setText(mStrings.get(holder.getAdapterPosition()));
        if (!mTextViews.contains(holder.mTvGroup)) {
            mTextViews.add(holder.mTvGroup);
        }
        if (mTextViews != null && mTextViews.size() == mStrings.size()) {
            selectItem(0);
        }
        holder.mLayout.setSelected(true);
        mConfig.onBindViewHolder(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        if (mListener != null) {
            mListener.onLinkageClick(holder, mStrings.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public void selectItem(int position) {
        for (int i = 0; i < mStrings.size(); i++) {
            if (position == i) {
                mConfig.onItemSelected(true, mTextViews.get(i));
                mTextViews.get(i).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTextViews.get(i).setFocusable(true);
                mTextViews.get(i).setFocusableInTouchMode(true);
                mTextViews.get(i).setMarqueeRepeatLimit(-1);
            } else {
                mConfig.onItemSelected(false, mTextViews.get(i));
                mTextViews.get(i).setEllipsize(TextUtils.TruncateAt.END);
                mTextViews.get(i).setFocusable(false);
                mTextViews.get(i).setFocusableInTouchMode(false);
                mTextViews.get(i).setMarqueeRepeatLimit(0);
            }
        }
    }

    public class LevelPrimaryViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvGroup;
        private LinearLayout mLayout;

        public LevelPrimaryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvGroup = (TextView) itemView.findViewById(mConfig.getTextViewId());
            mLayout = (LinearLayout) itemView.findViewById(mConfig.getRootViewId());
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

    /**
     * only for linkage logic of level primary adapter. not use for outside logic
     * users can archive onLinkageClick in configs instead.
     */
    public interface OnLinkageListener {
        void onLinkageClick(LinkageLevelPrimaryAdapter.LevelPrimaryViewHolder holder, String title, int position);
    }
}
