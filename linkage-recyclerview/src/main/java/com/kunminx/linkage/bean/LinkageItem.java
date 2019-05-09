package com.kunminx.linkage.bean;

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


/**
 * Create by KunMinX at 19/4/27
 */
public class LinkageItem extends BaseLinkageItem<LinkageItem.ItemInfo> {

    public LinkageItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public LinkageItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo {
        private String group;
        private String title;
        private String content;
        private String imgUrl;
        private String cost;

        public ItemInfo(String title, String group) {
            this.title = title;
            this.group = group;
        }

        public ItemInfo(String title, String group, String content) {
            this(title, group);
            this.content = content;
        }

        public ItemInfo(String title, String group, String content, String imgUrl) {
            this(title, group, content);
            this.imgUrl = imgUrl;
        }

        public ItemInfo(String title, String group, String content, String imgUrl, String cost) {
            this(title, group, content, imgUrl);
            this.cost = cost;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }
    }
}
