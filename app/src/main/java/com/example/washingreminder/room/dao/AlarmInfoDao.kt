package com.example.washingreminder.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.washingreminder.room.entity.AlarmInfo

@Dao
interface AlarmInfoDao {
    @Query("SELECT * FROM alarmInfo LIMIT 1")
    suspend fun getOne(): AlarmInfo

    @Query("INSERT INTO alarmInfo (hour, minute, interval) VALUES (8, 0, 3)")
    suspend fun insertFirst()

    @Query("UPDATE alarmInfo SET hour = :hour, minute = :minute, interval = :interval")
    suspend fun update(hour: Int, minute: Int, interval: Int)

    @Query("DELETE FROM alarmInfo")
    suspend fun deleteInitialization()
}