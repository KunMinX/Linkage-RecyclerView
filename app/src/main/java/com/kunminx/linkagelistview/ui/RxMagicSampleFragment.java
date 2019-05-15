package com.kunminx.linkagelistview.ui;
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


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.kunminx.linkagelistview.R;
import com.kunminx.linkagelistview.databinding.FragmentRxmagicBinding;

import java.util.List;

/**
 * Create by KunMinX at 19/5/8
 */
public class RxMagicSampleFragment extends Fragment {

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
        initLinkageDatas(mBinding.linkage);
    }

    private void initLinkageDatas(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<DefaultGroupedItem> items = gson.fromJson(getString(R.string.operators_json),
                new TypeToken<List<DefaultGroupedItem>>() {
                }.getType());

        linkage.init(items);
        linkage.setDefaultOnItemBindListener(
                (primaryClickView, title, position) -> {
                    Snackbar.make(primaryClickView, title, Snackbar.LENGTH_SHORT).show();
                },
                (primaryHolder, title, position) -> {
                    //TODO
                },
                (secondaryHolder, item, position) -> {
                    secondaryHolder.getView(R.id.level_2_item).setOnClickListener(v -> {
                        Snackbar.make(v, item.info.getTitle(), Snackbar.LENGTH_SHORT).show();
                    });
                },
                (headerHolder, item, position) -> {
                    //TODO
                }
        );
    }
}
