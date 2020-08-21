package com.zcx.pic.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.zcx.pic.utils.setFullScreen

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var unbinder: Unbinder

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        unbinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::unbinder.isInitialized) {
            unbinder.unbind()
        }
    }

    protected fun setFullScreen(fullScreen: Boolean) {
        window?.setFullScreen(fullScreen)
    }
}