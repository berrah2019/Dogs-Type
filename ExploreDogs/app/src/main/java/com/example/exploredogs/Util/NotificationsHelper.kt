package com.example.exploredogs.Util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.exploredogs.R

import com.example.exploredogs.views.MainActivity

class NotificationsHelper(val context: Context) {
    private val chanel_id = "Dog chanel id"
    private val notifications_id = 21

    @SuppressLint("ResourceAsColor")
    fun create_notification() {
        createChanelNotifications()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.dog)
        val notification = NotificationCompat.Builder(context, chanel_id)
            .setSmallIcon(R.drawable.ic_abdou)

            .setLargeIcon(icon)
            .setContentTitle("Dog retreived")
            .setContentText("this notification has some content")
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(icon)
                    .bigLargeIcon(null)

            ).setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(notifications_id, notification)
    }

    private fun createChanelNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = chanel_id
            val description_Text = "Chanel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val chanel = NotificationChannel(chanel_id, name, importance).apply {
                description = description_Text
            }

            val notificationmManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmManager.createNotificationChannel(chanel)
        }
    }
}