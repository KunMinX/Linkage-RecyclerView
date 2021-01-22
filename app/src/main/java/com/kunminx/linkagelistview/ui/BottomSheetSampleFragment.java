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


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kunminx.linkagelistview.R;
import com.kunminx.linkagelistview.databinding.FragmentBottomsheetBinding;
import com.kunminx.linkagelistview.ui.dialog.BottomSheetPopup;
import com.lxj.xpopup.XPopup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

/**
 * Create by KunMinX at 19/5/8
 */
public class BottomSheetSampleFragment extends Fragment {

    private FragmentBottomsheetBinding mBinding;
    private BottomSheetDialog mSheetDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
        mBinding = FragmentBottomsheetBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnPreview.setOnClickListener(v -> {
            new XPopup.Builder(getContext())
                    .moveUpToKeyboard(false)
                    .asCustom(new BottomSheetPopup(getContext()))
                    .show();

        });
    }

}
