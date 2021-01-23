package com.kunminx.linkage;
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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.adapter.LinkagePrimaryAdapter;
import com.kunminx.linkage.adapter.LinkageSecondaryAdapter;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;
import com.kunminx.linkage.defaults.DefaultLinkagePrimaryAdapterConfig;
import com.kunminx.linkage.defaults.DefaultLinkageSecondaryAdapterConfig;
import com.kunminx.linkage.manager.RecyclerViewScrollHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/27
 */
public class LinkageRecyclerView<T extends BaseGroupedItem.ItemInfo> extends ConstraintLayout {

    private static final int DEFAULT_SPAN_COUNT = 1;
    private static final int SCROLL_OFFSET = 0;
    public static final int FOR_PRIMARY = 1;
    public static final int FOR_SECONDARY = 2;

    private Context mContext;

    private View mView;
    private RecyclerView mRvPrimary;
    private RecyclerView mRvSecondary;

    private LinkagePrimaryAdapter mPrimaryAdapter;
    private LinkageSecondaryAdapter mSecondaryAdapter;
    private TextView mTvHeader;
    private FrameLayout mHeaderContainer;
    private View mHeaderLayout;

    private List<String> mInitGroupNames;
    private List<BaseGroupedItem<T>> mInitItems;

    private final List<Integer> mHeaderPositions = new ArrayList<>();
    private int mTitleHeight;
    private int mFirstVisiblePosition;
    private String mLastGroupName;
    private LinearLayoutManager mSecondaryLayoutManager;
    private LinearLayoutManager mPrimaryLayoutManager;
    private boolean mScrollSmoothly = true;
    private boolean mPrimaryClicked = false;

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
        mView = LayoutInflater.from(context).inflate(R.layout.layout_linkage_view, this);
        mRvPrimary = (RecyclerView) mView.findViewById(R.id.rv_primary);
        mRvSecondary = (RecyclerView) mView.findViewById(R.id.rv_secondary);
        mHeaderContainer = (FrameLayout) mView.findViewById(R.id.header_container);
    }

    private void setLevel2LayoutManager() {
        if (mSecondaryAdapter.isGridMode()) {
            mSecondaryLayoutManager = new GridLayoutManager(mContext,
                    mSecondaryAdapter.getConfig().getSpanCountOfGridMode());
            ((GridLayoutManager) mSecondaryLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //for header and footer
                    if (((BaseGroupedItem<T>) mSecondaryAdapter.getItems().get(position)).isHeader || position == mInitItems.size() - 1) {
                        return mSecondaryAdapter.getConfig().getSpanCountOfGridMode();
                    }
                    return DEFAULT_SPAN_COUNT;
                }
            });
        } else {
            mSecondaryLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        }
        mRvSecondary.setLayoutManager(mSecondaryLayoutManager);
    }

    private void initRecyclerView(ILinkagePrimaryAdapterConfig primaryAdapterConfig,
                                  ILinkageSecondaryAdapterConfig secondaryAdapterConfig) {

        mPrimaryAdapter = new LinkagePrimaryAdapter(mInitGroupNames, primaryAdapterConfig,
                new LinkagePrimaryAdapter.OnLinkageListener() {
                    @Override
                    public void onLinkageClick(LinkagePrimaryViewHolder holder, String title) {
                        if (isScrollSmoothly()) {
                            RecyclerViewScrollHelper.smoothScrollToPosition(mRvSecondary,
                                    LinearSmoothScroller.SNAP_TO_START,
                                    mHeaderPositions.get(holder.getBindingAdapterPosition()));
                        } else {
                            mSecondaryLayoutManager.scrollToPositionWithOffset(
                                    mHeaderPositions.get(holder.getBindingAdapterPosition()), SCROLL_OFFSET);
                        }
                        mPrimaryAdapter.setSelectedPosition(holder.getBindingAdapterPosition());
                        mPrimaryClicked = true;
                    }
                });

        mPrimaryLayoutManager = new LinearLayoutManager(mContext);
        mRvPrimary.setLayoutManager(mPrimaryLayoutManager);
        mRvPrimary.setAdapter(mPrimaryAdapter);

        mSecondaryAdapter = new LinkageSecondaryAdapter(mInitItems, secondaryAdapterConfig);
        setLevel2LayoutManager();
        mRvSecondary.setAdapter(mSecondaryAdapter);
    }

    private void initLinkageSecondary() {

        // Note: headerLayout is shared by both SecondaryAdapter's header and HeaderView

        if (mTvHeader == null && mSecondaryAdapter.getConfig() != null) {
            ILinkageSecondaryAdapterConfig config = mSecondaryAdapter.getConfig();
            int layout = config.getHeaderLayoutId();
            mHeaderLayout = LayoutInflater.from(mContext).inflate(layout, mHeaderContainer, false);
            mHeaderContainer.addView(mHeaderLayout);
            mTvHeader = mHeaderLayout.findViewById(config.getHeaderTextViewId());
        }

        if (mInitItems.get(mFirstVisiblePosition).isHeader) {
            mTvHeader.setText(mInitItems.get(mFirstVisiblePosition).header);
        }

        mRvSecondary.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mTitleHeight = mHeaderContainer.getMeasuredHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstPosition = mSecondaryLayoutManager.findFirstVisibleItemPosition();
                int firstCompletePosition = mSecondaryLayoutManager.findFirstCompletelyVisibleItemPosition();
                List<BaseGroupedItem<T>> items = mSecondaryAdapter.getItems();

                // Here is the logic of the sticky:

                if (firstCompletePosition > 0 && (firstCompletePosition) < items.size()
                        && items.get(firstCompletePosition).isHeader) {

                    View view = mSecondaryLayoutManager.findViewByPosition(firstCompletePosition);
                    if (view != null && view.getTop() <= mTitleHeight) {
                        mHeaderContainer.setY(view.getTop() - mTitleHeight);
                    }
                } else {
                    mHeaderContainer.setY(0);
                }

                // Here is the logic of group title changes and linkage:

                boolean groupNameChanged = false;

                if (mFirstVisiblePosition != firstPosition && firstPosition >= 0) {

                    if (mFirstVisiblePosition < firstPosition) {
                        mHeaderContainer.setY(0);
                    }

                    mFirstVisiblePosition = firstPosition;

                    String currentGroupName = items.get(mFirstVisiblePosition).isHeader
                            ? items.get(mFirstVisiblePosition).header
                            : items.get(mFirstVisiblePosition).info.getGroup();

                    if (TextUtils.isEmpty(mLastGroupName) || !mLastGroupName.equals(currentGroupName)) {
                        mLastGroupName = currentGroupName;
                        groupNameChanged = true;
                        mTvHeader.setText(mLastGroupName);
                    }
                }

                // the following logic can not be perfect, because tvHeader's title may not
                // always equals to the title of selected primaryItem, while there
                // are several groups which has little items to stick group item to tvHeader.
                //
                // To avoid to this extreme situation, my idea is to add a footer on the bottom,
                // to help wholly execute this logic.
                //
                // Note: 2019.5.22 KunMinX

                if (groupNameChanged) {
                    List<String> groupNames = mPrimaryAdapter.getStrings();
                    for (int i = 0; i < groupNames.size(); i++) {
                        if (groupNames.get(i).equals(mLastGroupName)) {
                            if (mPrimaryClicked) {
                                if (mPrimaryAdapter.getSelectedPosition() == i) {
                                    mPrimaryClicked = false;
                                }
                            } else {
                                mPrimaryAdapter.setSelectedPosition(i);
                                RecyclerViewScrollHelper.smoothScrollToPosition(mRvPrimary,
                                        LinearSmoothScroller.SNAP_TO_END, i);
                            }
                        }
                    }
                }
            }
        });
    }

    private int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }

    /**
     * init LinkageRV by items and configs
     *
     * @param linkageItems
     * @param primaryAdapterConfig
     * @param secondaryAdapterConfig
     */
    public void init(List<BaseGroupedItem<T>> linkageItems,
                     ILinkagePrimaryAdapterConfig primaryAdapterConfig,
                     ILinkageSecondaryAdapterConfig secondaryAdapterConfig) {

        initRecyclerView(primaryAdapterConfig, secondaryAdapterConfig);

        this.mInitItems = linkageItems;

        String lastGroupName = null;
        List<String> groupNames = new ArrayList<>();
        if (mInitItems != null && mInitItems.size() > 0) {
            for (BaseGroupedItem<T> item1 : mInitItems) {
                if (item1.isHeader) {
                    groupNames.add(item1.header);
                    lastGroupName = item1.header;
                }
            }
        }

        if (mInitItems != null) {
            for (int i = 0; i < mInitItems.size(); i++) {
                if (mInitItems.get(i).isHeader) {
                    mHeaderPositions.add(i);
                }
            }
        }

        DefaultGroupedItem.ItemInfo info = new DefaultGroupedItem.ItemInfo(null, lastGroupName);
        BaseGroupedItem<T> footerItem = (BaseGroupedItem<T>) new DefaultGroupedItem(info);
        mInitItems.add(footerItem);

        this.mInitGroupNames = groupNames;
        mPrimaryAdapter.initData(mInitGroupNames);
        mSecondaryAdapter.initData(mInitItems);
        initLinkageSecondary();
    }

    /**
     * simplify init by only items and default configs
     *
     * @param linkageItems
     */
    public void init(List<BaseGroupedItem<T>> linkageItems) {
        init(linkageItems, new DefaultLinkagePrimaryAdapterConfig(), new DefaultLinkageSecondaryAdapterConfig());
    }

    /**
     * bind listeners for primary or secondary adapter
     *
     * @param primaryItemClickListner
     * @param primaryItemBindListener
     * @param secondaryItemBindListener
     * @param headerBindListener
     * @param footerBindListener
     */
    public void setDefaultOnItemBindListener(
            DefaultLinkagePrimaryAdapterConfig.OnPrimaryItemClickListner primaryItemClickListner,
            DefaultLinkagePrimaryAdapterConfig.OnPrimaryItemBindListener primaryItemBindListener,
            DefaultLinkageSecondaryAdapterConfig.OnSecondaryItemBindListener secondaryItemBindListener,
            DefaultLinkageSecondaryAdapterConfig.OnSecondaryHeaderBindListener headerBindListener,
            DefaultLinkageSecondaryAdapterConfig.OnSecondaryFooterBindListener footerBindListener) {

        if (mPrimaryAdapter.getConfig() != null) {
            ((DefaultLinkagePrimaryAdapterConfig) mPrimaryAdapter.getConfig())
                    .setListener(primaryItemBindListener, primaryItemClickListner);
        }
        if (mSecondaryAdapter.getConfig() != null) {
            ((DefaultLinkageSecondaryAdapterConfig) mSecondaryAdapter.getConfig())
                    .setItemBindListener(secondaryItemBindListener, headerBindListener, footerBindListener);
        }
    }

    /**
     * custom linkageRV width in some scene like dialog
     *
     * @param dp
     */
    public void setLayoutHeight(float dp) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        lp.height = dpToPx(getContext(), dp);
        mView.setLayoutParams(lp);
    }

    /**
     * custom primary list width.
     * <p>
     * The reason for this design is that：The width of the first-level list must be an accurate value,
     * otherwise the onBindViewHolder may be called multiple times due to the RecyclerView's own bug.
     * <p>
     * Note 2021.1.20: this bug has been deal with in the newest version of RecyclerView
     *
     * @param dp
     */
    @Deprecated
    public void setPrimaryWidth(float dp) {
        ViewGroup.LayoutParams lpLeft = mRvPrimary.getLayoutParams();
        lpLeft.width = dpToPx(getContext(), dp);
        mRvPrimary.setLayoutParams(lpLeft);

        ViewGroup.LayoutParams lpRight = mRvSecondary.getLayoutParams();
        lpRight.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mRvSecondary.setLayoutParams(lpRight);
    }

    public boolean isGridMode() {
        return mSecondaryAdapter.isGridMode();
    }

    /**
     * custom if secondary list is hope to be grid mode
     *
     * @return
     */
    public void setGridMode(boolean isGridMode) {
        mSecondaryAdapter.setGridMode(isGridMode);
        setLevel2LayoutManager();
        mRvSecondary.requestLayout();
    }

    public boolean isScrollSmoothly() {
        return mScrollSmoothly;
    }

    /**
     * custom if is hope to scroll smoothly while click primary item to linkage secondary list
     *
     * @return
     */
    public void setScrollSmoothly(boolean scrollSmoothly) {
        this.mScrollSmoothly = scrollSmoothly;
    }

    public LinkagePrimaryAdapter getPrimaryAdapter() {
        return mPrimaryAdapter;
    }

    public LinkageSecondaryAdapter getSecondaryAdapter() {
        return mSecondaryAdapter;
    }

    public List<Integer> getHeaderPositions() {
        return mHeaderPositions;
    }

    /**
     * set percent of primary list and secondary list width
     *
     * @param percent
     */
    public void setPercent(float percent) {
        Guideline guideline = (Guideline) mView.findViewById(R.id.guideline);
        guideline.setGuidelinePercent(percent);
    }

    public void setRvPrimaryBackground(int color) {
        mRvPrimary.setBackgroundColor(color);
    }

    public void setRvSecondaryBackground(int color) {
        mRvSecondary.setBackgroundColor(color);
    }

    public View getHeaderLayout() {
        return mHeaderLayout;
    }

    /**
     * addItemDecoration for Primary or Secondary RecyclerView
     *
     * @param forPrimaryOrSecondary
     * @param decoration
     */
    public void addItemDecoration(int forPrimaryOrSecondary, RecyclerView.ItemDecoration decoration) {
        switch (forPrimaryOrSecondary) {
            case FOR_PRIMARY:
                mRvPrimary.removeItemDecoration(decoration);
                mRvPrimary.addItemDecoration(decoration);
            case FOR_SECONDARY:
                mRvSecondary.removeItemDecoration(decoration);
                mRvSecondary.addItemDecoration(decoration);
        }
    }
}
