package com.kunminx.linkagelistview.ui;
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
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;
import com.kunminx.linkagelistview.R;
import com.kunminx.linkagelistview.bean.CustomGroupedItem;
import com.kunminx.linkagelistview.bean.CustomSampleBean;
import com.kunminx.linkagelistview.databinding.FragmentRxmagicBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 本页面用于展示另一种自定义 items 的思路：
 * 当后台数据和本库 BaseGroupedItem 结构不匹配时，可将后台数据的实体类作为本库 item 的字段，
 * 并在获取后台数据时，遍历后台数据，和实例化本库 item，从而拿到可用的 items
 * <p>
 * 本示例对应的自定义 bean 是 CustomSampleBean，是普通的 POJO，遍历后可装载到 CustomGroupedItem
 * <p>
 * Create by KunMinX at 19/5/8
 */
public class CustomJsonSampleFragment extends Fragment {

  private static final int SPAN_COUNT_FOR_GRID_MODE = 2;
  private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
  private static final int MARQUEE_REPEAT_NONE_MODE = 0;
  private FragmentRxmagicBinding mBinding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_rxmagic, container, false);
    mBinding = FragmentRxmagicBinding.bind(view);
    setHasOptionsMenu(true);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initLinkageData(mBinding.linkage);
  }

  private void initLinkageData(LinkageRecyclerView linkage) {
    Gson gson = new Gson();
    List<CustomSampleBean> items = gson.fromJson(getString(R.string.custom_json),
            new TypeToken<List<CustomSampleBean>>() {
            }.getType());

    List<CustomGroupedItem<CustomSampleBean>> list = new ArrayList<>();
    for (CustomSampleBean bean : items) {
      CustomGroupedItem<CustomSampleBean> item;
      if (bean.sort.isEmpty()) {
        CustomGroupedItem.ItemInfo<CustomSampleBean> info
                = new CustomGroupedItem.ItemInfo<>(bean.name, bean.group, bean);
        item = new CustomGroupedItem<>(info);
      } else {
        item = new CustomGroupedItem<>(true, bean.sort);
      }
      list.add(item);
    }

    linkage.init(list, new CustomJsonLinkagePrimaryAdapterConfig(), new CustomJsonLinkageSecondaryAdapterConfig());
  }

  private static class CustomJsonLinkagePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private Context mContext;

    public void setContext(Context context) {
      mContext = context;
    }

    @Override
    public int getLayoutId() {
      return com.kunminx.linkage.R.layout.default_adapter_linkage_primary;
    }

    @Override
    public int getGroupTitleViewId() {
      return com.kunminx.linkage.R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
      return com.kunminx.linkage.R.id.layout_group;
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
      TextView tvTitle = ((TextView) holder.getGroupTitle());
      tvTitle.setText(title);

      tvTitle.setBackgroundColor(mContext.getResources().getColor(
              selected ? com.kunminx.linkage.R.color.colorPurple : com.kunminx.linkage.R.color.colorWhite));
      tvTitle.setTextColor(ContextCompat.getColor(mContext,
              selected ? com.kunminx.linkage.R.color.colorWhite : com.kunminx.linkage.R.color.colorGray));
      tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
      tvTitle.setFocusable(selected);
      tvTitle.setFocusableInTouchMode(selected);
      tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);
    }

    @Override
    public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
      //TODO
    }
  }

  private static class CustomJsonLinkageSecondaryAdapterConfig implements
          ILinkageSecondaryAdapterConfig<CustomGroupedItem.ItemInfo<CustomSampleBean>> {

    private Context mContext;

    public void setContext(Context context) {
      mContext = context;
    }

    @Override
    public int getLinearLayoutId() {
      return R.layout.adapter_custom_json_secondary_linear;
    }

    @Override
    public int getHeaderLayoutId() {
      return com.kunminx.linkage.R.layout.default_adapter_linkage_secondary_header;
    }

    @Override
    public int getFooterLayoutId() {
      return 0;
    }

    @Override
    public int getHeaderTextViewId() {
      return R.id.secondary_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
      return SPAN_COUNT_FOR_GRID_MODE;
    }

    @Override
    public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                 BaseGroupedItem<CustomGroupedItem.ItemInfo<CustomSampleBean>> item) {

      ((TextView) holder.getView(R.id.tv_name)).setText(item.info.getT().name);
      ((TextView) holder.getView(R.id.tv_type)).setText(item.info.getT().type);
      ((TextView) holder.getView(R.id.tv_date)).setText(item.info.getT().date);
    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<CustomGroupedItem.ItemInfo<CustomSampleBean>> item) {
      ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);

    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<CustomGroupedItem.ItemInfo<CustomSampleBean>> item) {
      ILinkageSecondaryAdapterConfig.super.onBindFooterViewHolder(holder, item);
    }
  }
}
