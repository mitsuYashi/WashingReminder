package com.example.washingreminder

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.washingreminder.databinding.ActivityMainBinding

import java.util.Calendar
import com.example.washingreminder.room.AppDatabase
import com.example.washingreminder.utils.LocationUtil.initialBootLocation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        db接続
        val db = AppDatabase.getInstance(this)

//        位置情報取得
        GlobalScope.launch(Dispatchers.Main) {
            val placeDao = db.placeDao()
            initialBootLocation(placeDao, this@MainActivity)
        }

//      通知呼び出し
        val permissionHandler = PermissionHandler(this)
        val notificationManagingService =
            NotificationManagingService(this, permissionHandler.requestPermission())
//        val alarmScheduler = AlarmScheduler(this)
        notificationManagingService.showNotification()
//        alarmScheduler.scheduleAlarm(17, 45)
    }

}