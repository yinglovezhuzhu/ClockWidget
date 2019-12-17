package com.owen.clockwidget

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.TextClock
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textClock = findViewById<TextClock>(R.id.timeText)

        val ss = SpannableString("HH:mm:ss")

        ss.setSpan(ForegroundColorSpan(Color.RED), 0, 6, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        ss.setSpan(ForegroundColorSpan(Color.GREEN), 6, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        ss.setSpan(RelativeSizeSpan(0.5f), 6, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        textClock.format24Hour = ss
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
