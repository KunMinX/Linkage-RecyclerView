package com.kunminx.linkage.contract;
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

import com.kunminx.linkage.adapter.LinkageLevelSecondaryAdapter;
import com.kunminx.linkage.bean.LinkageItem;

/**
 * Create by KunMinX at 19/5/8
 */
public interface ILevelSecondaryAdapterConfig {

    /**
     * setContext
     *
     * @param context context
     */
    void setContext(Context context);

    /**
     * get grid layout res id
     *
     * @return grid layout res id
     */
    int getGridLayoutId();

    /**
     * get linear layout res id
     *
     * @return linear layout res id
     */
    int getLinearLayoutId();

    /**
     * get header layout res id
     *
     * @return header layout res id
     */
    int getHeaderLayoutId();

    /**
     * get textView id of layout
     *
     * @return textView id of layout
     */
    int getTextViewId();

    /**
     * get rootView id of layout
     *
     * @return rootView id of layout
     */
    int getRootViewId();

    /**
     * get headerView id of layout
     *
     * @return headerView id of layout
     */
    int getHeaderViewId();

    /**
     * if is grid layout now
     *
     * @return is grid layout
     */
    boolean isGridMode();

    /**
     * set Grid Mode
     *
     * @param isGridMode isGridMode
     */
    void setGridMode(boolean isGridMode);

    /**
     * get SpanCount of grid mode
     */
    int getSpanCount();

    /**
     * achieve the onBindViewHolder logic on outside
     *
     * @param holder   LevelSecondaryViewHolder
     * @param item     linkageItem of this position
     * @param position holder.getAdapterPosition()
     */
    void onBindViewHolder(LinkageLevelSecondaryAdapter.LevelSecondaryViewHolder holder, LinkageItem item, int position);

}
