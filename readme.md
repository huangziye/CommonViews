[![](https://jitpack.io/v/huangziye/CommonViews.svg)](https://jitpack.io/#huangziye/CommonViews)

### 添加 `CommonViews` 到项目

- 第一步： 添加 `JitPack` 到项目的根 `build.gradle` 中


```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- 第二步：添加库依赖


```gradle
dependencies {
    implementation 'com.github.huangziye:CommonViews:${latest_version}'
}
```

<br />

### 常用 View（按字母先后顺序排序）

- `GifImageView` 既能支持ImageView控件原生的所有功能，同时还可以播放GIF图片。
- `LoadingView`  有三个不同形状的 View 进行下落回弹并且不断切换，与此同时，底部有阴影在不断的切换大小，最底下是文字说明。


### 常用对话框

- `BaseDialog` 自定义对话框可以继承 `BaseDialog`。
- `LoadingDialog` LoadingDialog 对话框。


<br />

### 使用方式

- **GifImageView**

```xml
<com.hzy.views.GifImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    app:auto_play="false"
    android:src="@drawable/gif"/>
```

其中，**auto_play** 是用来控制是否自动播放，true 表示自动播放，false 表示不自动播放。


- **LoadingView**

```xml
<com.hzy.views.LoadingView
    android:id="@+id/loadingView"
    android:layout_centerInParent="true"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```


<br />

### 效果图

- **GifImageView**

![GifImageView](https://github.com/huangziye/CommonViews/blob/5212298fd271490c500bc5b17c75b08a84905b2b/screenshot/GifImageView.gif)


- **LoadingView**

![LoadingView](https://github.com/huangziye/CommonViews/blob/5212298fd271490c500bc5b17c75b08a84905b2b/screenshot/LoadingView.gif)








<br />

### 关于我


- [简书](https://user-gold-cdn.xitu.io/2018/7/26/164d5709442f7342)

- [掘金](https://juejin.im/user/5ad93382518825671547306b)

- [Github](https://github.com/huangziye)

<br />

### License

```
Copyright 2018, huangziye

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

