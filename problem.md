## 展示5分钟、10分钟等分时图

https://github.com/fujianlian/KLineChart/issues/5


## 在NestedScrollView或ScrollView左右滑动冲突问题

```kotlin
// layout为与kChartView引起滑动冲突的view
kChartView.setOnTouchListener { _, event ->
            if (event.pointerCount == 2) {
                layout.requestDisallowInterceptTouchEvent(true)
            } else {
                layout.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
```

或者参考以下 

https://github.com/fujianlian/KLineChart/issues/13

由于滑动冲突的情况比较多，若无法解决，请自查相关滑动冲突解决文章


## 向尾部添加数据时，尾部MA线形成一条竖线？

https://github.com/fujianlian/KLineChart/issues/12


## 关于新增一条数据addHeaderData后指标线不见了

https://github.com/fujianlian/KLineChart/issues/1


## 当数值过小时，macd图 只显示一条横线

https://github.com/fujianlian/KLineChart/issues/6


## 单击显示详情

https://github.com/fujianlian/KLineChart/issues/10

https://github.com/fujianlian/KLineChart/issues/19


## 数据很少从左边开始显示

https://github.com/fujianlian/KLineChart/issues/9


## 时间轴挤在一起了

https://github.com/fujianlian/KLineChart/issues/14
