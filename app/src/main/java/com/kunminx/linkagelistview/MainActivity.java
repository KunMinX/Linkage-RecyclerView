package com.kunminx.linkagelistview;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.bean.LinkageItem;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkagelistview.databinding.ActivityMainBinding;

import java.util.List;

/**
 * Create by KunMinX at 19/4/27
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setClickProxy(new ClickProxy());

        initLinkageDatas(mBinding.linkage);
    }

    private void initLinkageDatas(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<LinkageItem> items = gson.fromJson(getString(R.string.operators_json),
                new TypeToken<List<LinkageItem>>() {
                }.getType());

        linkage.init(items);
    }

    public class ClickProxy {

        public void showDialog() {

        }

        public void showBottomSheet() {

        }

        public void showGridLayout() {

        }
    }
}
