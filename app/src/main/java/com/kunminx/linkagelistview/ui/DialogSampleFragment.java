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


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.kunminx.linkagelistview.R;
import com.kunminx.linkagelistview.databinding.AdapterVipBinding;
import com.kunminx.linkagelistview.databinding.FragmentDialogBinding;
import com.kunminx.linkagelistview.ui.adapter.SimpleBaseBindingAdapter;

import java.util.List;

/**
 * Create by KunMinX at 19/5/8
 */
public class DialogSampleFragment extends Fragment {

    private FragmentDialogBinding mBinding;
    private static float DIALOG_HEIGHT = 400;
    private AlertDialog mDialog;

    private SimpleBaseBindingAdapter<String, AdapterVipBinding> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        mBinding = FragmentDialogBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnPreview.setOnClickListener(v -> {
            View view2 = View.inflate(getContext(), R.layout.layout_linkage, null);
            LinkageRecyclerView linkage = view2.findViewById(R.id.linkage);
            initLinkageData(linkage);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            mDialog = builder.setView(linkage).show();
            linkage.setLayoutHeight(DIALOG_HEIGHT);
        });
    }

    private void initLinkageData(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<DefaultGroupedItem> items = gson.fromJson(getString(R.string.operators_json),
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
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
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
