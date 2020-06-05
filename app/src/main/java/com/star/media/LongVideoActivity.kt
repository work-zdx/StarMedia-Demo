package com.star.media

import android.content.Intent
import android.os.Bundle
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarLongVideo

class LongVideoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_video)

        initializeLongVideoAD()
    }

    /**
     * 初始化长视频广告
     */
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    private fun initializeLongVideoAD() {
        showLoading()

        val longVideoView = StarLongVideo(this, "longVideo").apply {
            requestSuccessListener = {
                hideLoading()
                Logger.e("LongVideoActivity", "请求长视频广告成功！")

                showLongVideo(this)
            }

            requestErrorListener = {
                hideLoading()
                Logger.e("SplashActivity", "请求开屏广告失败：$it")
                if (!isFinishing) {
                    startMainActivity()
                }
            }

            viewShowListener = {
                Logger.e("LongVideoActivity", "长视频广告展示！")
            }

            viewClickListener = {
                Logger.e("LongVideoActivity", "长视频广告点击！")
            }

            viewCloseListener = {
                Logger.e("LongVideoActivity", "长视频广告关闭！")
                if (!isFinishing) {
                    startMainActivity()
                }
            }
        }

        longVideoView.load()
    }

    /**
     * 播放长视频
     */
    private fun showLongVideo(starInterstitial: StarLongVideo) {
        starInterstitial.show()
    }

    /**
     * 跳转到主Activity
     */
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}