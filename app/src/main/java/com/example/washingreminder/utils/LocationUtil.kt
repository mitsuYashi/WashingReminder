package com.example.washingreminder.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.washingreminder.room.dao.PlaceDao
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationUtil {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    public suspend fun getLocation(activity: Activity): Pair<Double, Double> {
        return suspendCoroutine { continuation ->
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) { // 位置情報の許可を取得する
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )
            } else {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        val lat = location?.latitude ?: 0.0
                        val lng = location?.longitude ?: 0.0
                        continuation.resume(Pair(lat, lng))
                    }
            }
        }
    }

    public suspend fun checkSavedLocation(placeDao: PlaceDao): Boolean {
        val (savedLat, savedLng) = placeDao.getOne()
        return savedLat.toDouble() != 0.0 && savedLng.toDouble() != 0.0
    }


    public suspend fun getSavedLocation(placeDao: PlaceDao): Pair<Double, Double> {
        val (id, savedLat, savedLng) = placeDao.getOne()
        return Pair(savedLat, savedLng)
    }

    public suspend fun initialBootLocation(placeDao: PlaceDao, activity: Activity) {
        if (!checkSavedLocation(placeDao)) {
            updateLocation(placeDao, activity)
        }
    }

    public suspend fun updateLocation(placeDao: PlaceDao, activity: Activity) {
        val (lat, lng) = getLocation(activity)
        placeDao.update(lat, lng)
    }

}
