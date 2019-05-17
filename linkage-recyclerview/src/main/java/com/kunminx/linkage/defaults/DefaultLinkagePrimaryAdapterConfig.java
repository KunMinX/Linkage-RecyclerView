package com.kunminx.linkage.defaults;
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
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kunminx.linkage.R;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;

/**
 * Create by KunMinX at 19/5/8
 */
public class DefaultLinkagePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private Context mContext;
    private OnPrimaryItemBindListener mListener;

    public void setListener(OnPrimaryItemBindListener listener) {
        mListener = listener;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.default_adapter_linkage_primary;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, String title, int position) {

        ((TextView) holder.mGroupTitle).setText(title);

        if (mListener != null) {
            mListener.onBindViewHolder(holder, title, position);
        }
    }

    @Override
    public void onItemSelected(boolean selected, View itemView) {
        TextView textView = (TextView) itemView;
        textView.setBackgroundColor(mContext.getResources().getColor(selected ? R.color.colorPurple : R.color.colorWhite));
        textView.setTextColor(ContextCompat.getColor(mContext, selected ? R.color.colorWhite : R.color.colorGray));
        textView.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        textView.setFocusable(selected);
        textView.setFocusableInTouchMode(selected);
        textView.setMarqueeRepeatLimit(selected ? -1 : 0);
    }

    public interface OnPrimaryItemBindListener {
        /**
         * Note: Please do not override rootView click listener in here, because of linkage selection rely on it.
         *
         * @param primaryHolder primaryHolder
         * @param title         groupTitle
         * @param position      position
         */
        void onBindViewHolder(LinkagePrimaryViewHolder primaryHolder, String title, int position);
    }
}
