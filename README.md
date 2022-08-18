## 真香警告：即使不用饿了么订餐，也请务必收藏好该库！

## 由来

Linkage-RecyclerView 是一款基于 “MVP 架构(依赖倒置原则)” 开发的二级联动列表控件。它是因 [“RxJava 魔法师”](https://github.com/KunMinX/RxJava2-Operators-Sample) 项目需求而存在。

最初寻遍 GitHub 也未找到合适开源库，于是决定另起炉灶，编写 **高度解耦、轻松配置、可通过 Maven 仓库远程依赖** 的真正第三方库。

Linkage-RecyclerView 个性化配置十分简单，依托于 MVP “配置解耦” 特性，使用者无需研究内部逻辑，仅通过实现 Config 类即可完成功能定制和扩展。

此外，在不设置自定义配置情况下，Linkage-RecyclerView 最少只需 **一行代码即可运行**。

|                       RxJava Magician                        |                         Eleme Linear                         |                          Eleme Grid                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![7.gif](https://upload-images.jianshu.io/upload_images/57036-b4d61e70b43a07bb.gif) | ![2.gif](https://upload-images.jianshu.io/upload_images/57036-04b42bddcdd6cf39.gif) | ![3.gif](https://upload-images.jianshu.io/upload_images/57036-ada31ea077f0144d.gif) |

&nbsp;

## 目标

Linkage-RecyclerView 目标是：**一行代码即可接入二级联动列表**。

除一键接入省去 99% 不必要复杂重复工作外，您还可从该项目获得内容包括：

1. 整洁代码风格和标准资源命名规范。
2. **MVP 架构在编写第三方库的最佳实践：使用者无需了解内部逻辑，简单配置即可使用**。
3. 优秀代码分层和封装思想，在不做任何个性化配置情况下，一行代码即可接入。
4. 主体工程基于前沿 Jetpack 组件。
5. AndroidX 和 Material Design 2 全面使用。
6. ConstraintLayout 约束布局最佳实践。
7. 绝不使用 Dagger，绝不使用奇技淫巧、编写艰深晦涩代码。

如果您正考虑 [**如何为项目挑选合适架构**](https://juejin.cn/post/7106042518457810952)，这项目值得你参考！

&nbsp;

## 简单使用：

1.在 build.gradle 添加依赖。

```groovy
implementation 'com.kunminx.linkage:linkage-recyclerview:2.7.0'
```

提示：本库托管于 Maven Central，请自行在根目录 build.gradle 添加 `mavenCentral()`。

2.依据默认分组实体类 `DefaultGroupedItem` 结构准备一串数据。此处以 JSON 为例：

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

3.布局中引入 LinkageRecyclerView.

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

4.得到数据后，最少只需一行代码即可完成初始化。

```java
linkage.init(items);
```

&nbsp;

**温馨提示：**

1.实际开发中，项目数据或与本库数据 “结构上存在差异”，故通常做法是，从后端拿到和解析项目 JSON 数据，并对该数据进行遍历。在遍历过程中，实例化并装载 “本库实体类对象” 到列表中，以获本库所能使用的列表数据。（具体可参见 CustomJsonSampleFragment 示例）

2.注意：如使用 JSON，请在 ProGuard Rules 中为该实体类配置混淆白名单：

```java
-keep class com.kunminx.linkage.bean.** {*;}
```

&nbsp;

## 更多

更多自定义操作详见 [Wiki 解析](https://github.com/KunMinX/Linkage-RecyclerView/wiki/1.%E7%AE%80%E5%8D%95%E4%BD%BF%E7%94%A8)

&nbsp;

## Thanks to

[material-components-android](https://github.com/material-components/material-components-android)

[AndroidX](https://developer.android.google.cn/jetpack/androidx)

&nbsp;

## My Pages

Email：[kunminx@gmail.com](mailto:kunminx@gmail.com)

Juejin：[KunMinX 在掘金](https://juejin.cn/post/6882949076267057166)


&nbsp;

## License

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