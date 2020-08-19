package com.zcx.pic

import android.appwidget.AppWidgetManager
import android.content.IntentFilter
import android.os.Bundle
import com.zcx.pic.base.BaseActivity
import com.zcx.pic.splash.SplashFragment

class MainActivity : BaseActivity() {
    private var myAppWidgetProvider: MyAppWidgetProvider? = null

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

        myAppWidgetProvider = MyAppWidgetProvider()

        val filter = IntentFilter()
        filter.addAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        filter.addAction(MyAppWidgetProvider.ACTION_CLICK)
        registerReceiver(myAppWidgetProvider, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myAppWidgetProvider)
    }
}