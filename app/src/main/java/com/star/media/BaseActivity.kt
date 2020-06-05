package com.star.media

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper())

    val loadingFragment = LoadingFragment()

    fun showLoading() {
        if (!loadingFragment.isAdded) {
            loadingFragment.isCancelable = true
            loadingFragment.show(supportFragmentManager, "loading")
        }
    }

    fun hideLoading() {
        handler.postDelayed({
            try {
                if (loadingFragment.isVisible) {
                    loadingFragment.dismiss()
                } else if (loadingFragment.isAdded && !loadingFragment.isHidden) {
                    hideLoading()
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }, 200)
    }
}