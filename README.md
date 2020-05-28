## 真香警告：即使不用饿了么订餐，也请务必收藏好该库！

### [Here is the English guide](https://github.com/KunMinX/Linkage-RecyclerView/blob/master/README_EN.md)

### 由来

Linkage-RecyclerView 是一款基于 MVP 架构开发的二级联动列表控件。它是因 [“RxJava 魔法师”](https://github.com/KunMinX/RxJava2-Operators-Sample) 这个项目的需求而存在。

在最初寻遍了 GitHub 也没有找到合适的开源库（高度解耦、可远程依赖）之后，我决心研究参考现有开源项目关于二级联动的逻辑，并自己动手编写一个 **高度解耦、轻松配置、可通过 Maven 仓库远程依赖** 的真正的第三方库。

Linkage-RecyclerView 的个性化配置十分简单，依托于 MVP 的 “配置解耦” 特性，使用者无需知道内部的实现细节，仅通过实现 Config 类即可完成功能的定制和扩展。

此外，在不设置自定义配置的情况下，Linkage-RecyclerView 最少只需 **一行代码即可运行起来**。

|                           RxJava Magician                            |                         Eleme Linear                         |                          Eleme Grid                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![7.gif](https://upload-images.jianshu.io/upload_images/57036-b4d61e70b43a07bb.gif) | ![2.gif](https://upload-images.jianshu.io/upload_images/57036-04b42bddcdd6cf39.gif) | ![3.gif](https://upload-images.jianshu.io/upload_images/57036-ada31ea077f0144d.gif) |



### 目标

Linkage-RecyclerView 的目标是：**一行代码即可接入二级联动列表**。



除了一键接入而省去 99% 不必要的、复杂的、重复的工作外，你还可以从这个开源项目获得的内容包括：

1. 整洁的代码风格和标准的资源命名规范。
2. **MVP 架构在编写第三方库中的最佳实践：使用者无需了解内部逻辑，通过实现接口即可轻松完成个性化配置**。
3. 优秀的代码分层和封装思想，在不做任何个性化配置的情况下，一行代码即可接入。
4. 主体工程基于前沿的、遵循关注点分离的 JetPack MVVM 架构。
5. AndroidX 和 Material Design 2 的全面使用。
6. ConstraintLayout 约束布局的最佳实践。
7. 绝不使用 Dagger，绝不使用奇技淫巧、编写艰深晦涩的代码。



如果你正在思考 [**如何为项目挑选合适的架构**](https://juejin.im/post/5cde5d7a51882525e968cdcd#heading-2) 的话，这个项目值得你参考！



### 简单使用：

1.在 build.gradle 中添加对该库的依赖。

```groovy
implementation 'com.kunminx.linkage:linkage-recyclerview:1.9.2'
```

2.依据默认的分组实体类 `DefaultGroupedItem` 的结构准备一串数据（**[以下以  JSON 为例](#)**）。

> 注意这里讲的是 "为例" 哈，看到有些访客被这个 JSON 迷惑，**误以为后端必须适配这个 JSON 格式的数据**。

> 绝不是的。

> 事实上，**Linkage-RecyclerView 实体类的设计十分灵活**，并且装载数据的入参只有 `List<自定义Group类 extends BaseGroupedItem>`，在实际生产项目中，客户端可以自己在数据层对拿到的后端数据进行适配：

> 只需遵照下文 <a href="#custom">"个性化配置"</a> 中交代的步骤，根据实际项目所需数据格式来继承 BaseGroupedItem 并新建一个 group 类，然后在 数据层拿到后端请求来的数据后，遍历后端数据、将后端数据字段逐个注入到 group 对象中 —— 如此在数据层完成适配，再推送结果给 UI 层即可。

👆👆👆 划重点

```java
// DefaultGroupedItem.ItemInfo 包含三个字段：
String title //（必填）二级选项的标题
String group //（必填）二级选项所在分组的名称，要和对应的一级选项的标题相同
String content //（选填）二级选项的内容
```

```json
[
  {
    "header": "优惠",
    "isHeader": true
  },
  {
    "isHeader": false,
    "info": {
      "content": "好吃的食物，增肥神器，有求必应",
      "group": "优惠",
      "title": "全家桶"
    }
  },
  {
    "header": "热卖",
    "isHeader": true
  },
  {
    "isHeader": false,
    "info": {
      "content": "爆款热卖，月销超过 999 件",
      "group": "热卖",
      "title": "烤全翅"
    }
  }
]

```

3.在布局中引入 LinkageRecyclerView 。

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.kunminx.linkage.LinkageRecyclerView
        android:id="@+id/linkage"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

4.在得到数据后，最少只需一行代码即可完成初始化。

```java
List<DefaultGroupedItem> items = gson.fromJson(...);

//一行代码完成初始化
linkage.init(items);
```

注意：如使用 JSON，请在 ProGuard Rules 中为该实体类配置混淆白名单：

```java
-keep class com.kunminx.linkage.bean.** {*;}
```


### <a id="custom">个性化配置</a>：

该库为一级和二级 Adapter 分别准备了 Config 接口（`ILevelPrimaryAdapterConfig` 和 ` ILevelSecondaryAdapterConfig`），**自定义配置时，即是去实现这两个接口，来取代默认的配置**。

之所以设置成接口的形式，而非 Builder 的形式，是因为二级联动列表内部的联动逻辑需要指明关键的控件。接口相比 Builder 具有强制性，能够让使用者一目了然必须配置的内容，故而采用接口，通过 MVP 架构的方式来编写该库。

关于个性化配置，具体可以参考我在 `ElemeGroupedItem` 和 `SwitchSampleFragment` 中编写的案例：

### Step1：根据需求扩展实体类

你需要根据需求，在 `BaseGroupedItem` 的基础上扩展分组实体类，具体的办法是，编写一个实体类，该实体类须继承于 `BaseGroupedItem`；该实体类的内部类 `ItemInfo` 也须继承于 `BaseGroupedItem.ItemInfo`。

以 Eleme 分组实体类为例，扩充 `content`、`imgUrl`、`cost` 三个字段：

```java
public class ElemeGroupedItem extends BaseGroupedItem<ElemeGroupedItem.ItemInfo> {

    public ElemeGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ElemeGroupedItem(ItemInfo item) { super(item); }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private String content;
        private String imgUrl;
        private String cost;

        public ItemInfo(String title, String group, String content) {
            super(title, group);
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

        public String getContent() { return content; }

        public void setContent(String content) { this.content = content; }

        public String getImgUrl() { return imgUrl; }

        public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

        public String getCost() { return cost; }

        public void setCost(String cost) { this.cost = cost; }
    }
}
```

注意：如使用 JSON，请在 ProGuard Rules 中为该实体类配置混淆白名单。


### Step2：实现接口，完成自定义配置

在装载数据和实现自定义配置时，泛型框中须指明你编写的实体类，注意 `List<ElemeLinkageItem>`，以及 `new ILevelSecondaryAdapterConfig<ElemeLinkageItem.ItemInfo>()` 这两处。

```java
private void initLinkageDatas(LinkageRecyclerView linkage) {
    List<ElemeGroupedItem> items = gson.fromJson(...);

    linkage.init(items, new ElemePrimaryAdapterConfig(), new ElemeSecondaryAdapterConfig());
}
    
private class ElemePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private Context mContext;

    public void setContext(Context context) { mContext = context; }

    @Override
    public int getLayoutId() { return R.layout.adapter_linkage_primary; }

    @Override
    public int getGroupTitleViewId() { return R.id.tv_group; }

    @Override
    public int getRootViewId() { return R.id.layout_group; }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
        TextView tvTitle = (TextView) holder.mGroupTitle;
        tvTitle.setText(title);
        tvTitle.setBackgroundColor(mContext.getResources().getColor(
                selected ? R.color.colorPurple : R.color.colorWhite));
        tvTitle.setTextColor(ContextCompat.getColor(mContext, 
                selected ? R.color.colorWhite : R.color.colorGray));
    }
}

private class ElemeSecondaryAdapterConfig implements
        ILinkageSecondaryAdapterConfig<ElemeGroupedItem.ItemInfo> {

    private Context mContext;

    public void setContext(Context context) { mContext = context; }

    @Override
    public int getGridLayoutId() { return 0; }

    @Override
    public int getLinearLayoutId() { return R.layout.adapter_eleme_secondary_linear; }

    @Override
    public int getHeaderLayoutId() { return R.layout.adapter_linkage_secondary_header; }
    
    @Override
    public int getFooterLayoutId() { return 0; }

    @Override
    public int getHeaderTextViewId() { return R.id.secondary_header; }

    @Override
    public int getSpanCountOfGridMode() { return SPAN_COUNT_FOR_GRID_MODE; }

    @Override
    public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                 BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        ((TextView) holder.getView(R.id.iv_goods_name)).setText(item.info.getTitle());
        holder.getView(R.id.iv_goods_item).setOnClickListener(v -> {
            //TODO
        });
    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);
    }
    
    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<DefaultGroupedItem.ItemInfo> item) {
        //TODO
    }
}
```



# Thanks to

[material-components-android](https://github.com/material-components/material-components-android)

[AndroidX](https://developer.android.google.cn/jetpack/androidx)

# My Pages

Email：[kunminx@gmail.com](mailto:kunminx@gmail.com)

Home：[KunMinX 的个人博客](https://www.kunminx.com/)

Juejin：[KunMinX 在掘金](https://juejin.im/user/58ab0de9ac502e006975d757/posts)

[《重学安卓》 专栏](https://xiaozhuanlan.com/kunminx?rel=kunminx)

[![重学安卓小专栏](https://i.loli.net/2019/06/17/5d067596c2dbf49609.png)](https://xiaozhuanlan.com/kunminx?rel=kunminx)


# License

```
Copyright 2018-2020 KunMinX

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

