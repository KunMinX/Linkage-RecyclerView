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


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.adapter.LinkageLevelPrimaryAdapter;
import com.kunminx.linkage.adapter.LinkageLevelSecondaryAdapter;
import com.kunminx.linkage.bean.LinkageItem;
import com.kunminx.linkage.contract.ILevelPrimaryAdapterConfig;
import com.kunminx.linkage.contract.ILevelSecondaryAdapterConfig;
import com.kunminx.linkage.defaults.DefaultLevelPrimaryAdapterConfig;
import com.kunminx.linkage.defaults.DefaultLevelSecondaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/27
 */
public class LinkageRecyclerView extends RelativeLayout {

    private Context mContext;

    private RecyclerView mRvLevel1;
    private RecyclerView mRvLevel2;
    private LinearLayout mLinkageLayout;

    private LinkageLevelPrimaryAdapter mLevel1Adapter;
    private LinkageLevelSecondaryAdapter mLevel2Adapter;
    private TextView mTvLevel2Header;

    private List<String> mGroupNames;
    private List<LinkageItem> mItems;

    private List<Integer> mHeaderPositions = new ArrayList<>();
    private int mTitleHeight;
    private int mFirstPosition = 0;
    private LinearLayoutManager mLevel2LayoutManager;

    private List<Integer> getHeaderPositions() {
        return mHeaderPositions;
    }

    public LinkageRecyclerView(Context context) {
        super(context);
    }

    public LinkageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public LinkageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linkage_view, this);
        mRvLevel1 = (RecyclerView) view.findViewById(R.id.rv_level_1);
        mRvLevel2 = (RecyclerView) view.findViewById(R.id.rv_level_2);
        mTvLevel2Header = (TextView) view.findViewById(R.id.tv_level_2_header);
        mLinkageLayout = (LinearLayout) view.findViewById(R.id.linkage_layout);
    }

    private void setLevel2LayoutManager() {
        if (mLevel2Adapter.isGridMode()) {
            mLevel2LayoutManager = new GridLayoutManager(mContext, 3);
            ((GridLayoutManager) mLevel2LayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mLevel2Adapter.getItems().get(position).isHeader) {
                        return 3;
                    }
                    return 1;
                }
            });
        } else {
            mLevel2LayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        }
        mRvLevel2.setLayoutManager(mLevel2LayoutManager);
    }

    private void initRecyclerView(ILevelPrimaryAdapterConfig primaryAdapterConfig, ILevelSecondaryAdapterConfig secondaryAdapterConfig) {

        mLevel1Adapter = new LinkageLevelPrimaryAdapter(mGroupNames, primaryAdapterConfig, new LinkageLevelPrimaryAdapter.OnLinkageListener() {
            @Override
            public void onLinkageClick(LinkageLevelPrimaryAdapter.LevelPrimaryViewHolder holder, String title, int position) {
                mLevel1Adapter.selectItem(position);
                mLevel2LayoutManager.scrollToPositionWithOffset(mHeaderPositions.get(position), 0);
            }
        });

        mRvLevel1.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        mRvLevel1.setAdapter(mLevel1Adapter);


        mLevel2Adapter = new LinkageLevelSecondaryAdapter(mItems, secondaryAdapterConfig);
        setLevel2LayoutManager();
        mRvLevel2.setAdapter(mLevel2Adapter);
    }

    private void initLinkageLevel2() {

        if (mItems.get(mFirstPosition).isHeader) {
            mTvLevel2Header.setText(mItems.get(mFirstPosition).header);
        }

        mRvLevel2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mTitleHeight = mTvLevel2Header.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mItems.get(mFirstPosition).isHeader) {
                    View view = mLevel2LayoutManager.findViewByPosition(mFirstPosition);
                    if (view != null) {
                        if (view.getTop() >= mTitleHeight) {
                            mTvLevel2Header.setY(view.getTop() - mTitleHeight);
                        } else {
                            mTvLevel2Header.setY(0);
                        }
                    }
                }

                int firstPosition = mLevel2LayoutManager.findFirstVisibleItemPosition();
                if (mFirstPosition != firstPosition && firstPosition >= 0) {
                    mFirstPosition = firstPosition;
                    mTvLevel2Header.setY(0);

                    if (mItems.get(mFirstPosition).isHeader) {
                        mTvLevel2Header.setText(mItems.get(mFirstPosition).header);
                    } else {
                        mTvLevel2Header.setText(mItems.get(mFirstPosition).t.getGroup());
                    }
                }

                for (int i = 0; i < mGroupNames.size(); i++) {
                    if (mGroupNames.get(i).equals(mTvLevel2Header.getText().toString())) {
                        mLevel1Adapter.selectItem(i);
                    }
                }

                if (mLevel2LayoutManager.findLastCompletelyVisibleItemPosition() == mItems.size() - 1) {
                    mLevel1Adapter.selectItem(mGroupNames.size() - 1);
                }
            }
        });
    }

    private int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }

    public boolean isGridMode() {
        return mLevel2Adapter.isGridMode();
    }

    public void setGridMode(boolean isGridMode) {
        mLevel2Adapter.setGridMode(isGridMode);
        setLevel2LayoutManager();
        mRvLevel2.requestLayout();
    }

    public void init(List<LinkageItem> linkageItems) {
        init(linkageItems, new DefaultLevelPrimaryAdapterConfig(), new DefaultLevelSecondaryAdapterConfig());
    }

    public void init(List<LinkageItem> linkageItems, ILevelPrimaryAdapterConfig primaryAdapterConfig, ILevelSecondaryAdapterConfig secondaryAdapterConfig) {
        initRecyclerView(primaryAdapterConfig, secondaryAdapterConfig);

        this.mItems = linkageItems;

        List<String> groupNames = new ArrayList<>();
        if (mItems != null && mItems.size() > 0) {
            for (LinkageItem item1 : mItems) {
                if (item1.isHeader) {
                    groupNames.add(item1.header);
                }
            }
        }
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                if (mItems.get(i).isHeader) {
                    getHeaderPositions().add(i);
                }
            }
        }

        this.mGroupNames = groupNames;
        mLevel1Adapter.refreshList(mGroupNames);
        mLevel2Adapter.refreshList(mItems);
        initLinkageLevel2();
    }

    public void setLayoutHeight(float dp) {
        ViewGroup.LayoutParams lp = mLinkageLayout.getLayoutParams();
        lp.height = dpToPx(getContext(), dp);
        mLinkageLayout.setLayoutParams(lp);
    }

}
