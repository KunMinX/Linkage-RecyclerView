package com.kunminx.linkage.contract;
/*
 * Copyright (c) 2018-present. KunMinX
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

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;

/**
 * Create by KunMinX at 19/5/8
 */
public interface ILinkageSecondaryAdapterConfig<T extends BaseGroupedItem.ItemInfo> {

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
     * <p>
     * Note: Secondary adapter's Header and HeaderView must share the same set of views
     *
     * @return header layout res id
     */
    int getHeaderLayoutId();

    /**
     * get footer layout res id
     * <p>
     * Note: Footer is to avoid the extreme situation that
     * 'last group has too little items to sticky to avoid another issue'.
     *
     * @return footer layout res id
     */
    int getFooterLayoutId();

    /**
     * get the id of textView for bind title of HeaderView
     * <p>
     * Note: Secondary adapter's Header and HeaderView must share the same set of views
     *
     * @return
     */
    int getHeaderTextViewId();

    /**
     * get SpanCount of grid mode
     */
    int getSpanCountOfGridMode();

    /**
     * achieve the onBindViewHolder logic on outside
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkageSecondaryViewHolder
     * @param item     linkageItem of this position
     */
    void onBindViewHolder(LinkageSecondaryViewHolder holder, BaseGroupedItem<T> item);

    /**
     * achieve the onBindHeaderViewHolder logic on outside
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkageSecondaryHeaderViewHolder
     * @param item     header of this position
     */
    void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<T> item);

    /**
     * achieve the onBindFooterViewHolder logic on outside
     * and we suggest you get position by holder.getAdapterPosition
     *
     * @param holder   LinkageSecondaryFooterViewHolder
     * @param item     footer of this position
     */
    void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder, BaseGroupedItem<T> item);
}
