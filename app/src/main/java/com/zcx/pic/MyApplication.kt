package com.zcx.pic

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val displayMetrics = resources.displayMetrics
        DeviceConfig.density = displayMetrics.density
        DeviceConfig.heightPixels = displayMetrics.heightPixels
        DeviceConfig.widthPixels = displayMetrics.widthPixels
    }
}