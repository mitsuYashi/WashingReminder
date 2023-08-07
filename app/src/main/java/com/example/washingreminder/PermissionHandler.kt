package com.example.washingreminder

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts

class PermissionHandler(private val activity: MainActivity) {
    fun requestPermission() {
        val requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {isGranted: Boolean ->
            if (isGranted) {
                println("Permission granted")
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                println("Permission denied")
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

}