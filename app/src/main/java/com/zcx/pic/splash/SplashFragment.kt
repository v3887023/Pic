package com.zcx.pic.splash

import android.graphics.Color
import android.os.Handler
import android.view.View
import com.bumptech.glide.Glide
import com.zcx.pic.R
import com.zcx.pic.Utils
import com.zcx.pic.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * @Description:
 * @Author: zcx
 * @Copyright: 浙江集商优选电子商务有限公司
 * @CreateDate: 2020/8/19
 */
class SplashFragment : BaseFragment() {
    override fun getLayoutId() = R.layout.fragment_splash

    override fun initViews(view: View) {
        Glide.with(imageIv).load(Utils.getUrl("Fire")).into(imageIv)
        Handler().postDelayed({ setFullscreen(false) }, 2000)
    }

    override fun fullscreen() = true

    override fun getFakedStatusBarColor(): Int {
        return Color.parseColor("#00FFFF")
    }

    override fun needFakedStatusBarView() = true
}