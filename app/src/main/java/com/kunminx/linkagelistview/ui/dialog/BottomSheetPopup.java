package com.kunminx.linkagelistview.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.kunminx.linkagelistview.R;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.List;

/**
 * Create by KunMinX at 2020/11/7
 */
public class BottomSheetPopup extends BottomPopupView {

    public BottomSheetPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_linkage;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        LinkageRecyclerView linkage = findViewById(R.id.linkage);
        initLinkageData(linkage);
    }

    private void initLinkageData(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<DefaultGroupedItem> items = gson.fromJson(getContext().getString(R.string.operators_json),
                new TypeToken<List<DefaultGroupedItem>>() {
                }.getType());

        linkage.init(items);
        linkage.setScrollSmoothly(false);
        linkage.setDefaultOnItemBindListener(
                (primaryHolder, primaryClickView, title) -> {
                    Snackbar.make(primaryClickView, title, Snackbar.LENGTH_SHORT).show();
                },
                (primaryHolder, title) -> {
                    //TODO
                },
                (secondaryHolder, item) -> {
                    secondaryHolder.getView(R.id.level_2_item).setOnClickListener(v -> {
                        if (isShow()) {
                            dismiss();
                        }
                    });
                },
                (headerHolder, item) -> {
                    //TODO
                },
                (footerHolder, item) -> {
                    //TODO
                }
        );
    }
}
