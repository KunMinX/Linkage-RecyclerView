package com.kunminx.linkage.adapter.viewholder;
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


import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 19/5/15
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private View mConvertView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mHeaderViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mHeaderViews.put(viewId, view);
        }
        return (T) view;
    }
}
