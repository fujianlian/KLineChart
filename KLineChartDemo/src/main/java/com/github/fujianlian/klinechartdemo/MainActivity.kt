package com.github.fujianlian.klinechartdemo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.fujianlian.klinechart.DataHelper
import com.github.fujianlian.klinechart.KLineChartAdapter
import com.github.fujianlian.klinechart.KLineEntity
import com.github.fujianlian.klinechart.draw.Status
import com.github.fujianlian.klinechart.formatter.DateFormatter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.textColor
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var datas: List<KLineEntity>

    private val adapter by lazy { KLineChartAdapter() }

    private val subTexts: ArrayList<TextView> by lazy { arrayListOf(macdText, kdjText, rsiText, wrText) }
    // 主图指标下标
    private var subIndex = 0
    // 副图指标下标
    private var mainIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kLineChartView.adapter = adapter
        kLineChartView.dateTimeFormatter = DateFormatter()
        kLineChartView.setGridRows(4)
        kLineChartView.setGridColumns(4)
        initData()
        initListener()
    }

    private fun initData() {
        kLineChartView.justShowLoading()
        doAsync {
            datas = DataRequest.getALL(this@MainActivity).subList(0, 500)
            DataHelper.calculate(datas)
            runOnUiThread {
                adapter.addFooterData(datas)
                adapter.notifyDataSetChanged()
                kLineChartView.startAnimation()
                kLineChartView.refreshEnd()
            }
        }
    }

    private fun initListener() {
        maText.setOnClickListener {
            if (mainIndex != 0) {
                mainIndex = 0
                maText.textColor = Color.parseColor("#eeb350")
                bollText.textColor = Color.WHITE
                kLineChartView.changeMainDrawType(Status.MA)
            }
        }
        bollText.setOnClickListener {
            if (mainIndex != 1) {
                mainIndex = 1
                bollText.textColor = Color.parseColor("#eeb350")
                maText.textColor = Color.WHITE
                kLineChartView.changeMainDrawType(Status.BOLL)
            }
        }
        mainHide.setOnClickListener {
            if (mainIndex != -1) {
                mainIndex = -1
                bollText.textColor = Color.WHITE
                maText.textColor = Color.WHITE
                kLineChartView.changeMainDrawType(Status.NONE)
            }
        }
        for ((index, text) in subTexts.withIndex()) {
            text.setOnClickListener {
                if (subIndex != index) {
                    if (subIndex != -1) {
                        subTexts[subIndex].textColor = Color.WHITE
                    }
                    subIndex = index
                    text.textColor = Color.parseColor("#eeb350")
                    kLineChartView.setChildDraw(subIndex)
                }
            }
        }
        subHide.setOnClickListener {
            subTexts[subIndex].textColor = Color.WHITE
            subIndex = -1
            kLineChartView.hideChildDraw()
        }
        fenText.setOnClickListener {
            fenText.textColor = Color.parseColor("#eeb350")
            kText.textColor = Color.WHITE
            kLineChartView.setMainDrawLine(true)
        }
        kText.setOnClickListener {
            kText.textColor = Color.parseColor("#eeb350")
            fenText.textColor = Color.WHITE
            kLineChartView.setMainDrawLine(false)
        }
    }
}
