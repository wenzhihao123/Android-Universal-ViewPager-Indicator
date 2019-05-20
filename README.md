# Android-Universal-ViewPager-Indicator
Android 通用的ViewPager的指示器

### 效果图
![效果图](https://upload-images.jianshu.io/upload_images/2018489-af5b0a216201d875.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/400/format/webp)

### 自定义属性

| 属性 | 说明 | 
| ------ | ------ | 
| `normal_color` | 未选中的指示器颜色 | 
| `selected_color `| 选中的指示器颜色 | 
| `spacing` | 指示器每个item之间的间距 | 
|` orientation` | 设置指示器排列方向，枚举类型，有`horizontal`和`vertical` | 
|` style` | 枚举类型，有如下几种类型 | 

样式说明

| style | 说明 | 
| ------ | ------ | 
| `circle_circle` | 圆点指示器，对应图中第一种样式 | 
| `rect_rect` | 长条指示器，对应图中第二种样式 | 
| `circle_rect` | 指示器选中是长条，未选中是圆点，对应图中第三种样式 | 

如果`style`设置为 `circle_circle`可设置以下属性：

| 属性 | 说明 | 
| ------ | ------ | 
| `circle_circle_radius` | 都是圆点指示器半径大小 | 

如果`style`设置为 `rect_rect`可设置以下属性：

| 属性 | 说明 | 
| ------ | ------ | 
| `rect_rect_itemWidth` | 条形长度 | 
| `rect_rect_itemHeight` | 条形高度 | 
| `rect_rect_corner` | 条形圆角 | 

如果`style`设置为 `circle_rect`可设置以下属性：

| 属性 | 说明 | 
|--|--|
| `circle_rect_radius` | 未选中圆点半径 | 
| `circle_rect_itemWidth` | 选中条形长度 | 
| `circle_rect_itemHeight` | 选中条形高度 | 
| `circle_rect_corner` | 选中条形设置圆角|

### 使用方法
```
<FrameLayout
        android:layout_width="match_parent"
        android:layout_marginTop="10dp"
        android:layout_height="120dp">

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager3"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/darker_gray" />

    <com.wzh.viewpager.indicator.UIndicator
        android:id="@+id/indicator3"
        android:layout_width="match_parent"
        android:layout_height="6dp"
        android:layout_gravity="bottom|center_horizontal"
        android:layout_marginBottom="10dp"
        app:circle_rect_corner="3dp"
        app:circle_rect_itemHeight="4dp"
        app:circle_rect_itemWidth="20dp"
        app:circle_rect_radius="3dp"
        app:normal_color="#99ffffff"
        app:selected_color="#ffffff"
        app:spacing="10dp"
        app:orientation="horizontal"
        app:style="circle_rect" />
</FrameLayout>

```
* 简书: https://www.jianshu.com/p/42811a7c708c 
