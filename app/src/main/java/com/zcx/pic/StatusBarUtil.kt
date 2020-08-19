package com.zcx.pic

import android.content.Context

fun Context.getStatusBarHeight(): Int {
    var height = 0
    val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resId > 0) {
        height = resources.getDimensionPixelOffset(resId)
    }
    return height
}