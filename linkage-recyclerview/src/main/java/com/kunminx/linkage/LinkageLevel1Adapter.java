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

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/27
 */
public class LinkageLevel1Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<TextView> mTextViews = new ArrayList<>();

    public LinkageLevel1Adapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_group, item).addOnClickListener(R.id.layout_group);
        //将左侧item中的TextView添加到集合中
        mTextViews.add((TextView) helper.getView(R.id.tv_group));
        //设置进入页面之后,左边列表的初始状态
        if (mTextViews != null && mTextViews.size() == getData().size()) {
            selectItem(0);
        }

        helper.getView(R.id.layout_group).setSelected(true);
    }

    public void selectItem(int position) {
        for (int i = 0; i < getData().size(); i++) {
            if (position == i) {
                mTextViews.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.colorPurple));
                mTextViews.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                mTextViews.get(i).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTextViews.get(i).setFocusable(true);
                mTextViews.get(i).setFocusableInTouchMode(true);
                mTextViews.get(i).setMarqueeRepeatLimit(-1);
            } else {
                mTextViews.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                mTextViews.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.colorGray));
                mTextViews.get(i).setEllipsize(TextUtils.TruncateAt.END);
                mTextViews.get(i).setFocusable(false);
                mTextViews.get(i).setFocusableInTouchMode(false);
                mTextViews.get(i).setMarqueeRepeatLimit(0);
            }
        }
    }
}
