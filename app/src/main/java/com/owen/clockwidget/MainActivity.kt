package com.owen.clockwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val appWidgetManager: AppWidgetManager = getSystemService(AppWidgetManager::class.java)
//        val myProvider = ComponentName(this, ClockWidgetReceiver::class.java)
//
//        val successCallback: PendingIntent? = if (appWidgetManager.isRequestPinAppWidgetSupported) {
//            // Create the PendingIntent object only if your app needs to be notified
//            // that the user allowed the widget to be pinned. Note that, if the pinning
//            // operation fails, your app isn't notified.
//            Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).let { intent ->
//                // Configure the intent so that your app's broadcast receiver gets
//                // the callback successfully. This callback receives the ID of the
//                // newly-pinned widget (EXTRA_APPWIDGET_ID).
//                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            }
//        } else {
//            null
//        }
//
//        successCallback?.also { pendingIntent ->
//            appWidgetManager.requestPinAppWidget(myProvider, null, pendingIntent)
//        }
    }
}
