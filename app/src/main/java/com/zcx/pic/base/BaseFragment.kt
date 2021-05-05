package com.zcx.pic.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.zcx.pic.getStatusBarHeight

abstract class BaseFragment : Fragment() {

    private lateinit var activity: BaseActivity
    lateinit var contentView: View
        private set
    private lateinit var unbinder: Unbinder
    private var originStatusBarColor: Int = DEFAULT_STATUS_BAR_COLOR
    private var originFullscreen: Boolean = false
    private var originOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    private var viewDestroyed = false
    private var fakedStatusBarView: View? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        originOrientation = getRequestedOrientation()
    }

    protected fun runOnUiThread(runnable: Runnable) {
        if (isActivityInitialized()) {
            activity.runOnUiThread(runnable)
        }
    }

    private fun isActivityInitialized(): Boolean = ::activity.isInitialized

    protected fun onOrientationChanged(orientation: Int) {}

    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window: Window? = activity.window
        if (window != null) {
            originStatusBarColor = window.statusBarColor
            window.statusBarColor = getStatusBarColor()
            originFullscreen =
                window.attributes?.flags ?: 0 and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
        }

        val view = inflater.inflate(getLayoutId(), container, false)
        unbinder = ButterKnife.bind(this, view)
        this.contentView = view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!fullscreen() && needFakedStatusBarView()) {
            initFakedStatusBarView(view)
        }

        if (fullscreen()) {
            setFullscreen(true)
        }

        initViews(view)
    }

    private fun initFakedStatusBarView(view: View) {
        if (fakedStatusBarView != null) {
            return
        }

        val statusBarHeight: Int = activity.getStatusBarHeight()

        val statusBarView = View(activity)
        this.fakedStatusBarView = statusBarView
        statusBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight
        )

        setFakedStatusBarColor(getFakedStatusBarColor())

        (view.parent as ViewGroup).addView(statusBarView, 0)

        val layoutParams = view.layoutParams as MarginLayoutParams
        layoutParams.setMargins(0, statusBarHeight, 0, 0)
    }

    protected fun setRequestedOrientation(orientation: Int) {
        if (isActivityInitialized()) {
            activity.requestedOrientation = orientation

            if (!viewDestroyed) {
                onOrientationChanged(orientation)
            }
        }
    }

    protected fun requestOrientationPortrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    protected fun requestOrientationLandscape() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    protected fun isOrientationPortrait(): Boolean {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected fun <T : View> findViewById(@IdRes id: Int): T? {
        return if (::contentView.isInitialized) contentView.findViewById(id) else null
    }

    protected open fun initViews(view: View) {}

    override fun onDestroyView() {
        super.onDestroyView()
        if (::unbinder.isInitialized) {
            unbinder.unbind()
        }

        viewDestroyed = true

        setFullscreen(originFullscreen)

        activity.window?.statusBarColor = originStatusBarColor
    }

    open fun handleBackEvent() = false

    open fun fullscreen() = false

    protected fun setFullscreen(fullscreen: Boolean) {
        if (!isActivityInitialized()) {
            return
        }

        if (fullscreen) {
            activity.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setFakedStatusBarViewVisible(!fullscreen)

        if (!viewDestroyed) {
            onFullscreenStateChange(fullscreen)
        }
    }

    protected fun onFullscreenStateChange(fullscreen: Boolean) {

    }

    fun getStatusBarColor(): Int {
        return DEFAULT_STATUS_BAR_COLOR
    }

    protected fun setFakedStatusBarViewVisible(visible: Boolean) {
        if (visible) {
            initFakedStatusBarView(contentView)
        }

        fakedStatusBarView?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    protected open fun needFakedStatusBarView(): Boolean = false

    protected open fun getFakedStatusBarColor(): Int = Color.TRANSPARENT

    open fun setFakedStatusBarColor(color: Int) {
        fakedStatusBarView?.setBackgroundColor(color)
    }

    fun getRequestedOrientation(): Int {
        return if (isActivityInitialized()) activity.requestedOrientation else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    companion object {
        const val DEFAULT_STATUS_BAR_COLOR = Color.WHITE
    }
}