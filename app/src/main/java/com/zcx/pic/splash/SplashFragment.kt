package com.zcx.pic.splash

import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zcx.pic.R
import com.zcx.pic.Utils
import com.zcx.pic.base.BaseFragment

/**
 * @Description:
 * @Author: zcx
 * @Copyright: 浙江集商优选电子商务有限公司
 * @CreateDate: 2020/8/19
 */
class SplashFragment : BaseFragment() {
    private lateinit var imageIv: ImageView

    override fun getLayoutId() = R.layout.fragment_splash

    override fun initViews(view: View) {
        imageIv = view.findViewById(R.id.imageIv)
        Glide.with(imageIv).load(Utils.getUrl("Fire")).into(imageIv)
        Handler().postDelayed({ setFullscreen(false) }, 2000)
    }

    override fun fullscreen() = true

    override fun getFakedStatusBarColor(): Int {
        return Color.parseColor("#00FFFF")
    }

    override fun needFakedStatusBarView() = true
}