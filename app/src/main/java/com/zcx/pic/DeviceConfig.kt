package com.zcx.pic

import android.content.Context


object DeviceConfig {
    fun init(context: Context) {
        val displayMetrics = context.resources.displayMetrics
        this.density = displayMetrics.density
        this.heightPixels = displayMetrics.heightPixels
        this.widthPixels = displayMetrics.widthPixels
    }

    @JvmStatic
    var heightPixels = 0
        private set

    @JvmStatic
    var widthPixels = 0
        private set

    @JvmStatic
    var density = 0f
        private set
}