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


import android.view.View;

import androidx.annotation.NonNull;

import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;

/**
 * Create by KunMinX at 19/5/15
 */
public class LinkagePrimaryViewHolder extends BaseViewHolder {

  private final View mGroupTitle;
  private final View mLayout;

  public View getGroupTitle() {
    return mGroupTitle;
  }

  public View getLayout() {
    return mLayout;
  }

  public LinkagePrimaryViewHolder(@NonNull View itemView, ILinkagePrimaryAdapterConfig config) {
    super(itemView);
    mGroupTitle = itemView.findViewById(config.getGroupTitleViewId());
    //need bind root layout by users, because rootLayout may not viewGroup, which can not getChild(0).
    mLayout = itemView.findViewById(config.getRootViewId());
  }
}
