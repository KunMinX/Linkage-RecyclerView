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
import android.widget.TextView;

import com.kunminx.linkage.adapter.LinkageLevelPrimaryAdapter;

/**
 * Create by KunMinX at 19/5/8
 */
public interface ILevelPrimaryAdapterConfig {

    /**
     * setContext
     *
     * @param context context
     */
    void setContext(Context context);

    /**
     * get layout res id
     *
     * @return layout res id
     */
    int getLayoutId();

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
     * achieve the onBindViewHolder logic on outside
     *
     * @param holder   LevelPrimaryViewHolder
     * @param title    title of this position
     * @param position holder.getAdapterPosition()
     */
    void onBindViewHolder(LinkageLevelPrimaryAdapter.LevelPrimaryViewHolder holder, String title, int position);

    /**
     * configurations of textView when selected or not
     *
     * @param selected if selected
     * @param itemView textView which you choose to config the expression.
     */
    void onItemSelected(boolean selected, TextView itemView);

}
