package com.owen.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.app.PendingIntent
import android.content.ComponentName
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
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
            appWidgetManager?.updateAppWidget(appWidgetId, buildView(context))

        }

        val c = Calendar.getInstance()
        Handler().postDelayed({
            onUpdate(context, appWidgetManager, appWidgetIds)
        }, 60000 - c[Calendar.SECOND] * 1000L)
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
        fun buildView(context: Context?): RemoteViews {
            val c = Calendar.getInstance()
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
                setOnClickPendingIntent(R.id.tv_time, timePendingIntent)
                setOnClickPendingIntent(R.id.tv_date, datePendingIntent)

                setTextViewText(R.id.tv_time, "${c[Calendar.HOUR_OF_DAY]}:${String.format("%02d", c[Calendar.MINUTE])}")
                setTextViewText(R.id.tv_date, "${c[Calendar.YEAR]}/${String.format("%02d", c[Calendar.MONTH])}/${String.format("%02d", c[Calendar.DAY_OF_MONTH])}")
            }
        }

        fun updateWidget(context: Context?) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.updateAppWidget(ComponentName(context!!, ClockWidgetProvider::class.java), buildView(context))
        }
    }
}