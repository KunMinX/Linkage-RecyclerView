## LinkageRecyclerView：一行代码即可接入 “饿了么” 二级联动列表

很高兴和大家见面！

关于上一期的[《Rx钥匙：为无聊而生的 Android 开发者工具》](<https://juejin.im/post/5cc698e3e51d456e845b428e>)，有热心网友追问，能不能提供 Release 包直接下载体验，如你所愿，目前已在 GitHub 项目概述中提供下载链接。

> 尔后又有 “眼尖” 的网友注意到，该项目依赖了我开源的另一个库 **LinkageRecyclerView**。
>
> 没错，二级联动列表，从我构思 RxJava 魔法师的交互设计之日起，便已纳入开发日程。

原本我是想，如果能像饿了么订单一样，将类别和选项相互关联，那么我的用户在寻找操作符时，便可以通过功能类别，轻松地匹配到对应的操作符。

但是，在翻遍了 GitHub 后发现，仿饿了么联动列表的项目不下十个，却没有一个是解耦的、能通过 gradle 配置来远程依赖的第三方库！

![](https://upload-images.jianshu.io/upload_images/57036-4f220ad1580a989d.jpg)

这让我想起了两年前的某个夜晚，当时我在加班到九点即将离开时接到通知说，“今晚就要给详情页面加个多级联动表单，客户明早就要”。

那个表单由多行 Spinner 组成，用过 Spinner 的朋友一定知道，原生 Spinner 的下拉回调有延迟的 bug，为了避免初始化装载已填写的联动数据时发生错乱，我们最好使用 PopupWindow 封装的 RecyclerView 来代替 Spinner。

当时我在网上找了一圈，找到了许多 “看似能用但又没经过封装、没解耦” 的示例代码，哼哧哼哧前前后后捣鼓到 11 点，可算是才把控件给解耦、把需求给完成了。

![](https://upload-images.jianshu.io/upload_images/57036-8ea3be8721e501d1.jpg)

今天的 “饿了么二级联动列表” 也是一样的，说不定哪天就用到了呢，说不定还会遇上 “今晚就做，明早就要” 的坏事呢！不过好在，LinkageRecyclerView 的作者拥有多年的项目重构和架构设计的经验，曾在公司核心项目的重构中率先使用自主设计的 VIABUS 架构，5 天内完成 60 个类的重构。

因此 LinkageRecyclerView 的目标是：一行代码即可接入 “饿了么” 二级联动列表！

|                         Eleme Linear                         |                          Eleme Grid                          |                        RxMagic Dialog                        |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![1.gif](https://upload-images.jianshu.io/upload_images/57036-2c1552029b927cec.gif) | ![2.gif](https://upload-images.jianshu.io/upload_images/57036-268cd9ace45759a0.gif) | ![4.gif](https://upload-images.jianshu.io/upload_images/57036-db0ba8283d65e68b.gif) |

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
  },
 
  ...
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

