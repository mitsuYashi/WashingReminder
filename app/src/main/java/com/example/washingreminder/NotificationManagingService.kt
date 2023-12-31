package com.example.washingreminder

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationManagingService(private val context: Context, private val requestPermission: Unit): BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent : Intent?) {
        if (context == null) return
        createNotificationChannel(context)
        val notification = buildNotification(context)

        notification.build().let {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, it)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channelId"
        private const val NOTIFICATION_ID = 1
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            return
        }
//        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//            return
//        }
        requestPermission
    }


    private fun createNotificationChannel(context: Context) {
        checkNotificationPermission()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "洗濯タイミングの通知"
            val descriptionText = "設定された洗濯の日付になったら通知を出します"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = descriptionText
            }

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(context: Context): NotificationCompat.Builder {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("洗濯をしてください")
            .setContentText("洗濯の日になりました。アプリを起動して天気を確認しよう！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
    }

}