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
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kunminx.linkage.R;
import com.kunminx.linkage.adapter.LinkageLevelPrimaryAdapter;
import com.kunminx.linkage.contract.ILevelPrimaryAdapterConfig;

/**
 * Create by KunMinX at 19/5/8
 */
public class DefaultLevelPrimaryAdapterConfig implements ILevelPrimaryAdapterConfig {

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.default_adapter_linkage_level_primary;
    }

    @Override
    public int getTextViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    @Override
    public void onBindViewHolder(LinkageLevelPrimaryAdapter.LevelPrimaryViewHolder holder, String title, int position) {
        holder.getView(R.id.layout_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

    @Override
    public void onItemSelected(boolean selected, TextView itemView) {
        itemView.setBackgroundColor(mContext.getResources().getColor(selected ? R.color.colorLightBlue : R.color.colorWhite));
        itemView.setTextColor(ContextCompat.getColor(mContext, selected ? R.color.colorWhite : R.color.colorGray));
    }
}
