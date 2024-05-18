package com.example.mentormatch
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class MatchNotificationService(
    private val context:Context
){
    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(nome: String){
        val notification=NotificationCompat.Builder(context,"water_notification")
            .setContentTitle("Novo Match!")
            .setContentText("Você acabou de dar match com $nome")
            .setSmallIcon(R.drawable.grouplogo)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }


    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}