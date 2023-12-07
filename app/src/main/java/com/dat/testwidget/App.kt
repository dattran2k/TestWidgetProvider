package com.dat.testwidget

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import java.util.Timer
import java.util.TimerTask


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val handler = Handler()
        val timer = Timer()

        val task: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    sendBroadcast(Intent("android.appwidget.action.APPWIDGET_UPDATE"))
                    sendBroadcast(Intent("com.dat.testwidget.APPWIDGET_UPDATE"))
                }
            }
        }
        timer.scheduleAtFixedRate(task, 0, 1000) // Executes the task every 5 seconds.

    }
    private fun createClockTickIntent(context: Context): PendingIntent? {

        Log.e("", "createClockTickIntent")
        val intent = Intent(ExampleAppWidgetProvider.CLOCK_WIDGET_UPDATE)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}