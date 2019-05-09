## LinkageRecyclerView：一行代码即可接入 “饿了么” 二级联动列表

很高兴和大家见面！

关于上一期的[《Rx钥匙：为无聊而生的 Android 开发者工具》](<https://juejin.im/post/5cc698e3e51d456e845b428e>)，有热心网友追问，能不能提供 Release 包直接下载体验，如你所愿，目前已在 GitHub 项目概述中提供下载链接。

尔后又有 “眼尖” 的网友注意到，该项目依赖了我开源的另一个库 **LinkageRecyclerView**。没错，二级联动列表，从我构思 RxJava 魔法师的交互设计之日起，便已纳入开发日程。

|                         Eleme Linear                         |                          Eleme Grid                          |                        RxMagic Dialog                        |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![7.gif](https://upload-images.jianshu.io/upload_images/57036-b4d61e70b43a07bb.gif) | ![6.gif](https://upload-images.jianshu.io/upload_images/57036-5d66eeeffcbd443f.gif) | ![8.gif](https://upload-images.jianshu.io/upload_images/57036-f460e0bd6af3ccee.gif) |



原本我是想，如果能像 ELEME 订单一样，将类别和选项相互关联，那么我的用户在寻找操作符时，便可以通过功能类别，轻松地匹配到对应的操作符。

但是，在翻遍了 GitHub 后发现，仿 ELEME 联动列表的项目不下十个，却没有一个是解耦的、能通过 gradle 配置来远程依赖的第三方库！

![](https://upload-images.jianshu.io/upload_images/57036-b9ab267618a7f843.png)

二级列表这种需求其实十分常见，万一哪天临时急需，却没有一个可以即插即用的控件，那得多糟心呀！于是我花了五天的时间，先后在多个开源项目之间来回研究，并最终编写和开源了一套真正的、可供使用者依赖的二级联动列表库。

LinkageRecyclerView 的目标是：**一行代码即可接入二级联动列表**。

|                         Eleme Linear                         |                          Eleme Grid                          |                         BottomSheet                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![2.gif](https://upload-images.jianshu.io/upload_images/57036-04b42bddcdd6cf39.gif) | ![3.gif](https://upload-images.jianshu.io/upload_images/57036-5dc85c89ef486d57.gif) | ![5.gif](https://upload-images.jianshu.io/upload_images/57036-2483853731b30a14.gif) |

除了一键接入而省去 99% 不必要的重复工作外，你还可以从这个开源项目获得的内容包括：

1. 整洁的代码风格和标准的资源命名规范。
2. MVP 架构在第三库中的最佳实践：使用者无需了解内部逻辑，通过实现接口即可轻松完成个性化配置和一键调用。
3. 优秀的代码分层和封装思想，在不做任何个性化配置的情况下，一行代码即可接入。
4. 主体工程基于前沿的、遵循关注点分离的 JetPack MVVM 架构。
5. 使用 RxJava 和 lambda 表达式。
6. AndroidX 和 Material Design 2 的全面使用。
7. ConstraintLayout 约束布局的最佳实践。
8. 绝不使用 Dagger，绝不使用奇技淫巧、编写艰深晦涩的代码。


### 简单使用：

1.在 build.gradle 中添加对该库的依赖。

```
implementation 'com.kunminx.linkage:linkage-recyclerview:1.2.0'
```

2.依据联动实体类的结构简单配置 json。

```json
[
  {
    "header": "优惠",
    "isHeader": true
  },
  {
    "isHeader": false,
    "t": {
      "content": "好吃的食物，增肥神器，有求必应\n月售10008 好评率100%",
      "group": "优惠",
      "imgUrl": "https://upload-images.jianshu.io/upload_images/57036-7d50230a36c40a94.png",
      "title": "全家桶"
    }
  },
  {
    "header": "热卖",
    "isHeader": true
  }
]
    
```

3.在布局中引入 LinkageRecyclerView 。

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.kunminx.linkage.LinkageRecyclerView
        android:id="@+id/linkage"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1" />

</LinearLayout>
```

4.在代码中解析 json，并只用一行代码完成初始化。

```java
Gson gson = new Gson();
List<LinkageItem> items = gson.fromJson(getString(R.string.operators_json),
        new TypeToken<List<LinkageItem>>() {
        }.getType());

//一行代码完成初始化
linkage.init(items);
```



### 个性化配置：

该库分别为一级和二级 Adapter 准备了 Config 接口，自定义配置时，即是去实现这两个接口，从而取代默认的配置。

之所以设置成接口的形式，而非 Builder 的形式，是因为二级联动列表内部的联动逻辑需要指明关键的控件。接口相比 Builder 具有强制性，能够让使用者一目了然必须配置的内容，故而采用接口，通过 MVP 架构的方式来编写该库。

关于个性化配置，具体可以参考我在 SwitchSampleFragment 中编写的案例：

```java
private void initLinkageDatas(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<LinkageItem> items = gson.fromJson(getString(R.string.eleme_json),
                new TypeToken<List<LinkageItem>>() {
                }.getType());

        linkage.init(items, new ILevelPrimaryAdapterConfig() {

            private Context mContext;

            public void setContext(Context context) {
                mContext = context;
            }

            @Override
            public int getLayoutId() {
                return R.layout.default_adapter_linkage_level_primary;
            }

            @Override
            public int getTextViewId() {
                return com.kunminx.linkage.R.id.tv_group;
            }

            @Override
            public int getRootViewId() {
                return com.kunminx.linkage.R.id.layout_group;
            }

            @Override
            public void onBindViewHolder(
                LinkageLevelPrimaryAdapter.LevelPrimaryViewHolder holder, 
                String title, int position) {
                
                holder.getView(R.id.layout_group).setOnClickListener(v -> {

                });
            }

            @Override
            public void onItemSelected(boolean selected, TextView itemView) {
                itemView.setBackgroundColor(mContext.getResources().getColor(selected
                        ? com.kunminx.linkage.R.color.colorLightBlue
                        : com.kunminx.linkage.R.color.colorWhite));
                itemView.setTextColor(ContextCompat.getColor(mContext, selected
                        ? com.kunminx.linkage.R.color.colorWhite
                        : com.kunminx.linkage.R.color.colorGray));
            }

        }, new ILevelSecondaryAdapterConfig() {

            private Context mContext;
            private boolean mIsGridMode;

            public void setContext(Context context) {
                mContext = context;
            }

            @Override
            public int getGridLayoutId() {
                return R.layout.adapter_eleme_secondary_grid;
            }

            @Override
            public int getLinearLayoutId() {
                return R.layout.adapter_eleme_secondary_linear;
            }

            @Override
            public int getHeaderLayoutId() {
                return R.layout.default_adapter_linkage_level_secondary_header;
            }

            @Override
            public int getTextViewId() {
                return R.id.iv_goods_name;
            }

            @Override
            public int getRootViewId() {
                return R.id.iv_goods_item;
            }

            @Override
            public int getHeaderViewId() {
                return com.kunminx.linkage.R.id.level_2_header;
            }

            @Override
            public boolean isGridMode() {
                return mIsGridMode;
            }

            @Override
            public void setGridMode(boolean isGridMode) {
                mIsGridMode = isGridMode;
            }

            @Override
            public int getSpanCount() {
                return 2;
            }

            @Override
            public void onBindViewHolder(
                LinkageLevelSecondaryAdapter.LevelSecondaryViewHolder holder, 
                LinkageItem item, int position) {
                
                ((TextView) holder.getView(R.id.iv_goods_name))
                .setText(item.t.getTitle());
                
                Glide.with(mContext).load(item.t.getImgUrl())
                    .into((ImageView) holder.getView(R.id.iv_goods_img));
                
                holder.getView(R.id.iv_goods_item).setOnClickListener(v -> {
                    //TODO
                });

                holder.getView(R.id.iv_goods_add).setOnClickListener(v -> {
                    //TODO
                });
            }
        });
```



# Thanks to

[material-components-android](https://github.com/material-components/material-components-android)

[AndroidX](https://developer.android.google.cn/jetpack/androidx)

# My Pages

Email：[kunminx@gmail.com](mailto:kunminx@gmail.com)

Home：[KunMinX 的个人博客](https://kunminx.github.io/)

Juejin：[KunMinX 在掘金](https://juejin.im/user/58ab0de9ac502e006975d757/posts)

<span id="wechatQrcode">KunMinX's WeChat Public Account（微信公众号）：</span>

![公众号](https://upload-images.jianshu.io/upload_images/57036-dc3af94a5daf478c.jpg)

# License

```
Copyright 2018-2019 KunMinX

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


