package com.example.washingreminder.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.washingreminder.room.dao.AlarmInfoDao
import com.example.washingreminder.room.dao.PlaceDao
import com.example.washingreminder.room.entity.AlarmInfo
import com.example.washingreminder.room.entity.Place
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Place::class, AlarmInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun alarmInfoDao(): AlarmInfoDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        @OptIn(DelicateCoroutinesApi::class)
        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = androidx.room.Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            val placeDao = INSTANCE!!.placeDao()
                            val alarmInfoDao = INSTANCE!!.alarmInfoDao()
                            GlobalScope.launch {
                                placeDao.deleteInitialization()
                                placeDao.insertFirst()
                                alarmInfoDao.deleteInitialization()
                                alarmInfoDao.insertFirst()
                            }
                        }
                    }).build()
                }
                return INSTANCE!!
            }
        }
    }
}