package com.example.washingreminder.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.washingreminder.room.entity.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place LIMIT 1")
    suspend fun getOne(): Place

    @Query("INSERT INTO place(latitude, longitude) VALUES(0, 0)")
    suspend fun insertFirst()

    @Query("UPDATE place SET latitude = :latitude, longitude = :longitude")
    suspend fun update(latitude: Double, longitude: Double)

    @Query("UPDATE place SET latitude = 0, longitude = 0")
    suspend fun delete()

    @Query("DELETE FROM place")
    suspend fun deleteInitialization()
}