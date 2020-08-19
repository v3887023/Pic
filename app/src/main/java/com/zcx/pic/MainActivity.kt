package com.zcx.pic

import android.os.Bundle
import com.zcx.pic.base.BaseActivity
import com.zcx.pic.splash.SplashFragment

class MainActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, SplashFragment())
            .commit()

//        val properties = Properties()
//        properties.load(resources.openRawResource(R.raw.unsplash))
//        val accessKey = properties.getProperty("access_key")
//        Log.d("MainActivity", "accessKey: $accessKey")
    }
}