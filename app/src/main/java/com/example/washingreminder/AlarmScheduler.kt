package com.example.washingreminder
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class AlarmScheduler(private val context: Context) {

    fun scheduleAlarm(hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // アラームがトリガーされたときに実行されるブロードキャストレシーバーのIntent
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)

        // アラームがトリガーされたときに送信されるPendingIntent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // アラームのトリガー時間を設定
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        // 過去の時間を指定した場合、翌日に設定
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1)
        }

        val dayInterval = 3
        Log.d("AlarmScheduler", "アラームのトリガー時間: ${calendar.time}")
        // アラームを設定
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            (1000 * 60 * 60 * 24 * dayInterval).toLong(),
            pendingIntent
        )
    }

}
