package com.zcx.pic.utils

import android.view.Window
import android.view.WindowManager

/**
 * @Description:
 * @Author: zcx
 * @Copyright: 浙江集商优选电子商务有限公司
 * @CreateDate: 2020/8/20
 */
fun Window.setFullScreen(fullScreen: Boolean) {
    if (fullScreen) {
        setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}