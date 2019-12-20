package com.owen.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.KeyEvent
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.util.*


/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2019/12/5
 */

class ClockWidgetProvider : AppWidgetProvider() {

    val TAG = "ClockWidgetProvider"

    private val mHandler = Handler()


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "小部件提供程序接收到广播")

        if(AppWidgetManager.ACTION_APPWIDGET_UPDATE == intent?.action) {
            val extras = intent.extras
            Log.i(TAG, "Extras size: ${extras?.size()}")
        }
        super.onReceive(context, intent)

        if(Intent.ACTION_TIME_CHANGED == intent?.action) {
            updateWidget(context)
        }
    }


    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(TAG, "小部件更新")
        // 一个App Widget提供程序为一个App Widget提供支持，但是一个App Widget却可以在多个地方添加，
        // 每一个显示的App Widget视图有一个id，如果多个地方添加将会有多个id，这里循环对每一个视图进行更新
        appWidgetIds?.forEach { appWidgetId ->
            // 创建App Widget点击事件意图（说明：如果不需要实现点击事件，可以不定义此项）
            // 通知AppWidgetManager更新当前的App Widget
            appWidgetManager?.updateAppWidget(appWidgetId, buildView(appWidgetId, context))

        }
    }

    override fun onAppWidgetOptionsChanged(context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int, newOptions: Bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.i(TAG, "$appWidgetId 小部件选项更新")
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.i(TAG, "小部件启用")
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.i(TAG, "小部件禁用")
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        appWidgetIds?.forEach {
            Log.i(TAG, "$it 小部件被删除")
        }
    }


    companion object {
        fun buildView(appWidgetId: Int, context: Context?): RemoteViews {
            val c = Calendar.getInstance()
            val sp = context?.getSharedPreferences("sp_widget_config", Context.MODE_PRIVATE)
            val bgColor = sp?.getInt("${appWidgetId}_bg_color", Color.argb(0xff, 0xff, 0xff, 0xff))
            val bgAlpha = sp?.getInt("${appWidgetId}_bg_alpha", 100)
            val timePendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }
            // 如果有多个控件可点击，可以创建多个点击事件意图
            val datePendingIntent:PendingIntent = Intent(Intent.ACTION_QUICK_CLOCK).let { intent->
                PendingIntent.getActivity(context, 1, intent, 0)
            }

            // 获取App Widget的视图布局
            return RemoteViews(context?.packageName, R.layout.clock_widget).apply {
                // 设置点击事件意图(说明：如果App Widget内部有多个控件点击事件，可以在此添加多个控件的点击事件)
                setOnClickPendingIntent(R.id.timeText, timePendingIntent)
                setOnClickPendingIntent(R.id.dateText, datePendingIntent)

                val alpha = (bgAlpha?: 100).div(100f) * 0xFF
                val color = bgColor?: Color.argb(0xff, 0xff, 0xff, 0xff)


                setInt(R.id.content, "setBackgroundColor", Color.argb(alpha.toInt(), color.red, color.green, color.blue))
            }

        }

        fun updateWidget(context: Context?) {
            val appWidgetManager = AppWidgetManager.getInstance(context)

            val componentName = ComponentName(context!!, ClockWidgetProvider::class.java)

            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
            appWidgetIds?.forEach {
                appWidgetManager.updateAppWidget(componentName, buildView(it, context))
            }
        }
    }
}