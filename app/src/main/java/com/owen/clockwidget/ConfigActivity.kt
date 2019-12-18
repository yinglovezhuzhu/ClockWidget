package com.owen.clockwidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_config.*
import java.time.Clock

/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2019/12/17
 */
class ConfigActivity: Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_config)

        val sp = getSharedPreferences("sp_widget_config", Context.MODE_PRIVATE)

        val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)

        sp.edit().putInt("${appWidgetId}_bg_color", Color.BLACK).apply()
        sp.edit().putInt("${appWidgetId}_bg_alpha", 100).apply()


        btnOk.setOnClickListener() {
            setResult(RESULT_OK, Intent().also {
                it.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            })

            ClockWidgetProvider.updateWidget(this)

            finish()
        }

        btnCancel.setOnClickListener() {
            setResult(RESULT_CANCELED)
            finish()
        }

        colorGroup.setOnCheckedChangeListener() { group: RadioGroup, checkedId: Int -> UInt
            when(checkedId) {
                R.id.blue -> {
                    println("选择了蓝色背景")
                    sp.edit().putInt("${appWidgetId}_bg_color", Color.argb(0xff, 0x33, 0xb5, 0xe5)).apply()
                }
                R.id.gray -> {
                    println("选择了灰色背景")
                    sp.edit().putInt("${appWidgetId}_bg_color", Color.argb(0xff, 0xaa, 0xaa, 0xaa)).apply()
                }
                R.id.green -> {
                    println("选择了绿色背景")
                    sp.edit().putInt("${appWidgetId}_bg_color", Color.argb(0xff, 0x99, 0xcc, 0x00)).apply()
                }
                R.id.white -> {
                    println("选择了白色背景")
                    sp.edit().putInt("${appWidgetId}_bg_color", Color.argb(0xff, 0xff, 0xff, 0xff)).apply()
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvBgAlpha.text = "背景透明度：${progress}%"
                sp.edit().putInt("${appWidgetId}_bg_alpha", progress).apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        tvBgAlpha.text = "背景透明度：${seekBar.progress}%"
        sp.edit().putInt("${appWidgetId}_bg_alpha", seekBar.progress).apply()

    }

}