package com.zcx.pic

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        DeviceConfig.init(this)
    }
}