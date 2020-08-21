package com.zcx.pic.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import coil.api.load
import com.bumptech.glide.Glide
import com.zcx.pic.MainActivity
import com.zcx.pic.R
import com.zcx.pic.Utils
import com.zcx.pic.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * @Description:
 * @Author: zcx
 * @Copyright: 浙江集商优选电子商务有限公司
 * @CreateDate: 2020/8/20
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.fragment_splash

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(imageIv).load(Utils.getUrl("Fire")).into(imageIv)
        handler.postDelayed({
            onBackPressed()
        }, 3000)

        ObjectAnimator.ofFloat(textTv, "translationY", 300f, 0f)
            .apply {
                interpolator = DecelerateInterpolator()
                duration = 1500
            }
            .start()
    }

    override fun onBackPressed() {
        handler.removeCallbacksAndMessages(null)
        startActivity(Intent(this, MainActivity::class.java))
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}