package com.kunminx.linkage.manager;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Create by KunMinX at 19/5/15
 */
public class RecyclerViewScrollHelper {

    public static void smoothScrollToPosition(RecyclerView recyclerView, int snapMode, int position) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            LinearSmoothScroller mScroller = null;
            if (snapMode == LinearSmoothScroller.SNAP_TO_START) {
                mScroller = new TopSmoothScroller(recyclerView.getContext());
            } else if (snapMode == LinearSmoothScroller.SNAP_TO_END) {
                mScroller = new BottomSmoothScroller(recyclerView.getContext());
            } else {
                mScroller = new LinearSmoothScroller(recyclerView.getContext());
            }
            mScroller.setTargetPosition(position);
            manager.startSmoothScroll(mScroller);
        }
    }

    public static class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }

    public static class BottomSmoothScroller extends LinearSmoothScroller {
        BottomSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_END;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_END;
        }
    }
}
