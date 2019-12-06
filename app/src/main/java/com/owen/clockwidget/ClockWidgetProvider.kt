package com.owen.clockwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.app.PendingIntent
import android.os.Bundle
import android.util.Log


/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2019/12/5
 */

class ClockWidgetProvider : AppWidgetProvider() {

    val TAG = "ClockWidgetProvider"

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.i(TAG, "小部件提供程序接收到广播")
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(TAG, "小部件更新")
        // 一个App Widget提供程序为一个App Widget提供支持，但是一个App Widget却可以在多个地方添加，
        // 每一个显示的App Widget视图有一个id，如果多个地方添加将会有多个id，这里循环对每一个视图进行更新
        appWidgetIds.forEach { appWidgetId ->
            // 创建App Widget点击事件意图（说明：如果不需要实现点击事件，可以不定义此项）
            val timePendingIntent: PendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }
            // 如果有多个控件可点击，可以创建多个点击事件意图
            val datePendingIntent:PendingIntent = Intent(Intent.ACTION_QUICK_CLOCK).let { intent->
                PendingIntent.getActivity(context, 1, intent, 0)
            }

            // 获取App Widget的视图布局
            val views: RemoteViews = RemoteViews(context.packageName,R.layout.clock_widget).apply {
                // 设置点击事件意图(说明：如果App Widget内部有多个控件点击事件，可以在此添加多个控件的点击事件)
                setOnClickPendingIntent(R.id.tv_time, timePendingIntent)
                setOnClickPendingIntent(R.id.tv_date, datePendingIntent)
            }

            Log.e(TAG, "updatePeriodMillis: ${appWidgetManager.getAppWidgetInfo(appWidgetId).updatePeriodMillis}")


            // 通知AppWidgetManager更新当前的App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
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
}