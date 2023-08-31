package com.example.washingreminder.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "hour") val hour: Int = 8,
    @ColumnInfo(name = "minute") val minute: Int = 0,
    @ColumnInfo(name = "interval") val interval: Int = 3
)