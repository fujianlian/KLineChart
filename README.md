# KLineChart

Android仿火币K线图实现（包含MA,BOLL,MACD,KDJ,RSI,WR指标）

本项目是在 [tifezh的KChartView](https://github.com/tifezh/KChartView) 基础上进行修改的，新增了KDJ、WR指标、最大值和最小值展示，对UI展示进行了修改。

建议使用之前先查看[常见问题](./problem.md)

## 项目运行效果

![gif](https://github.com/fujianlian/KLineChart/raw/master/img/effect.gif)

![截图](https://github.com/fujianlian/KLineChart/raw/master/img/1.png)

## app下载

![二维码](https://github.com/fujianlian/KLineChart/raw/master/img/qrcode.png)

## 配置使用

```xml
<com.github.fujianlian.klinechart.KLineChartView
    android:id="@+id/kLineChartView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

主图和附图初始化
```java
// KLineChartView
private void initView() {
    ...
    // 依次添加副图子视图
    addChildDraw(mMACDDraw);
    addChildDraw(mKDJDraw);
    addChildDraw(mRSIDraw);
    addChildDraw(mWRDraw);
    // 设置成交量视图
    setVolDraw(mVolumeDraw);
    // 设置主视图
    setMainDraw(mMainDraw);
}
```
BaseKLineChartView
```java
// 主图显示隐藏调用
public void changeMainDrawType(Status status) {
    if (mainDraw != null && mainDraw.getStatus() != status) {
        mainDraw.setStatus(status);
        invalidate();
    }
}

// 主视图当前子视图
public enum Status {
    MA, BOLL, NONE
}

// 设置子视图，position依据初始化添加先后顺序下标
public void setChildDraw(int position) {
        if (mChildDrawPosition != position) {
            if (!isShowChild) {
                isShowChild = true;
                initRect();
            }
            mChildDraw = mChildDraws.get(position);
            mChildDrawPosition = position;
            isWR = position == 5;
            invalidate();
        }
    }

// 子视图隐藏
public void hideChildDraw() {
        mChildDrawPosition = -1;
        isShowChild = false;
        mChildDraw = null;
        initRect();
        invalidate();
    }
```

KLineChartView
```java
// 是否显示为分时图
public void setMainDrawLine(boolean isLine) {
        mMainDraw.setLine(isLine);
    }
```

## 自定义颜色
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#3F51B5</color>
    <color name="colorPrimaryDark">#303F9F</color>
    <color name="colorAccent">#FF4081</color>

    <color name="chart_red">#26BF66</color>
    <color name="chart_green">#FD6433</color>
    <color name="chart_line">#C9933E</color>
    <color name="chart_line_background">#1aC9933E</color>

    <color name="chart_ma5">#DA8AE5</color>
    <color name="chart_ma10">#39B0E8</color>
    <color name="chart_ma30">#FFC76D</color>
    <color name="chart_white">#ffffff</color>

    <color name="chart_background">#202326</color>
    <color name="chart_bac">#00FFFFFF</color>
    <color name="chart_point_bac">#202326</color>
    <color name="chart_grid_line">#1AFFFFFF</color>
    <color name="chart_text">#818596</color>
    <color name="chart_selector">#202326</color>
    <color name="chart_tab_background">#30343C</color>
    <color name="chart_tab_indicator">#FF6601</color>
</resources>
```
布局时修改KLineChartView的相关颜色即可



## 具体使用参照KLineChartDemo

