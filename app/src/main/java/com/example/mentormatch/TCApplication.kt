package com.example.mentormatch


import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TCApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationChannel= NotificationChannel(
            "water_notification",
            "Water",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}