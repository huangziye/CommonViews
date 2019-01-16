# 常用 View

- `GifImageView` 既能支持ImageView控件原生的所有功能，同时还可以播放GIF图片。
- `LoadingView`  有三个不同形状的 View 进行下落回弹并且不断切换，与此同时，底部有阴影在不断的切换大小，最底下是文字说明。





# 使用方式

- GifImageView

```
<com.hzy.views.GifImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    app:auto_play="false"
    android:src="@drawable/gif"/>
```

其中，**auto_play** 是用来控制是否自动播放，true 表示自动播放，false 表示不自动播放。


- LoadingView

```
<com.hzy.views.LoadingView
    android:id="@+id/loadingView"
    android:layout_centerInParent="true"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```



# 效果图

- GifImageView



- LoadingView


