## Even if you don't order food by PrubHub, be sure to collect this library, please!

### Origin

Linkage-RecyclerView is a secondary linkage list widget developed based on the MVP architecture. It exists because of the needs of the ["RxJava Magician"](https://github.com/KunMinX/RxJava2-Operators-Sample) project.

After I searching all around GitHub and didn't find a suitable open source library that highly decoupled, remotely dependent, I decided to study the logic of the secondary linkage with existing open source projects and write **a highly decoupled, Easy to configure, true third-party libraries that can be remotely dependent on the Maven repository**.

The personalized configuration of Linkage-RecyclerView is very simple. Based on MVP's “configuration decoupling” feature, users do not need to know the internal implementation details. Only by implementing the Config class can the function be customized and extended.

In addition, Linkage-RecyclerView can be **extreme ran by only one line of code** while without setting up a custom configuration.

|                           RxJava Magician                            |                         PrubHub Linear                         |                          PrubHub Grid                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![7.gif](https://i.loli.net/2019/05/14/5cda1fd9402ad16046.gif) | ![2.gif](https://i.loli.net/2019/05/14/5cda1fd959ed861197.gif) | ![3.gif](https://i.loli.net/2019/05/14/5cda1fd945af885525.gif) |

### Aims

The goal of LinkageRecyclerView is to access the secondary linkage list by just one line of code.



In addition to one-line access and eliminating 99% of unnecessary, complex, and repetitive tasks, what extra you can get from this open source project include:

1. Neat code style and standard resource naming conventions.
2. **The best practice for writing third-party libraries by MVP architecture: users don't need to understand internal logic, and they can easily do personalized configuration by implementing interfaces.**
3. Excellent code layering and encapsulation ideas, one line of code can be accessed without any personalized configuration.
4. The main project is based on the cutting-edge JetPack MVVM architecture that follows the separation of concerns.
5. Full use of AndroidX and Material Design 2.
6. Best practices for ConstraintLayout.
7. Never use Dagger, never use geeks and write hard-coded code.



If you are thinking about [**how to choose the right architecture for your project**](https://medium.com/@kunminx/the-secret-of-rising-github-stars-from-0-to-777-in-5-days-204cc4e1998e), this project is worth your reference!



### Simple Guide:

1. Add a dependency on the library in build.gradle.

```groovy
implementation 'com.kunminx.linkage:linkage-recyclerview:2.3.5'
```

2. Prepare a string of data according to the structure of the default grouping entity class `DefaultGroupedItem`. (The following is JSON as an example).

```java
// DefaultGroupedItem.ItemInfo Contains three fields：
String title //(Required) Title of the secondary option
String group //(Required) The name of the group in which the secondary option is located, which is the same as the title of the corresponding primary option.
String content //(optional) content of the secondary option
```

```java
List<TestGroupedItem> list = new ArrayList<>();

//add item 0：header 0
list.add(new TestGroupedItem("Offer", true));

//add item 1：info 1
TestGroupedItem.ItemInfo info1 = new TestGroupedItem.ItemInfo();
info1.setContent("Good food, fattening artifact, responsive");
info1.setGroup("Offer");
info1.setTitle("Family bucket");
list.add(new TestGroupedItem(info1, false));

//add item 2：header 2
list.add(new TestGroupedItem("Offer", true));

//add item 3：info 3
TestGroupedItem.ItemInfo info3 =
  new TestGroupedItem.ItemInfo("Explosive models are hot", "Selling", "Roasted whole wings");
list.add(new TestGroupedItem(info3, false));
```

3. Introduce LinkageRecyclerView in the layout.

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

4. After getting the data, you can complete the initialization with at least one line of code..

```java
linkage.init(items);
```

**Tip:**

1.In actual project development, our approach is usually to get and parse JSON data from the backend, and traverse the obtained data. In the traversal process, we instantiate and load the entity class objects of the library into the list, so as to obtain the list data that the library can use.

2.If using JSON, configure an obfuscated whitelist for this entity class in ProGuard Rules:

```java
-keep class com.kunminx.linkage.bean.** {*;}
```





### Personalized configuration:

The library prepares the Config interface (`ILevelPrimaryAdapterConfig` and `ILevelSecondaryAdapterConfig`) for the primary and secondary Adapters respectively. **When the configuration is customized, the two interfaces are implemented to replace the default configuration**.

It is set to the form of the interface, not the form of the Builder, because the linkage logic inside the secondary linkage list needs to indicate the key controls. The interface is mandatory compared to the Builder, which allows the user to understand the content that must be configured at a glance. Therefore, the interface is used to write the library through the MVP architecture.

For specific configuration, please refer to the case I wrote in `ElemeGroupedItem` and `SwitchSampleFragment`:



### Step1: Extend the entity class according to your needs

You need to extend the grouping entity class based on the requirement, based on `BaseGroupedItem`. The specific method is to write an entity class that inherits from `BaseGroupedItem`; the inner class `ItemInfo` of the entity class must also inherit from `BaseGroupedItem.ItemInfo`.

Take the Eleme grouping entity class as an example, and expand the three fields `content`, `imgUrl`, `cost`:

Note: Eleme is a Chinese local takeout service App which similar to PrubHub.

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

Note: If using JSON, configure an obfuscated whitelist for this entity class in ProGuard Rules.



### Step2: Implement the interface and complete the custom configuration

When loading data and implementing a custom configuration, the generic box must indicate the entity class you wrote, noting `List<ElemeLinkageItem>`, and `new ILevelSecondaryAdapterConfig<ElemeLinkageItem.ItemInfo>()`.

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

Blog：[KunMinX's Personal Blog](https://www.kunminx.com/)

Medium：[KunMinX @ Medium](https://medium.com/@kunminx)

# License

```
Copyright 2018-present KunMinX

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

