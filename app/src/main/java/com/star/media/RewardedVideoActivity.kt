package com.star.media

import android.content.Intent
import android.os.Bundle
import com.starmedia.adsdk.IRewardedVideo
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarRewardVideo
import kotlinx.android.synthetic.main.layout_slotid.*

class RewardedVideoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_video)

        initializeRewardedVideoAD()
    }

    var slotid = "wjxc_0000013369"

    /**
     * 初始化激励视频广告
     */
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    private fun initializeRewardedVideoAD() {
        txt_slotid.setText(slotid)

        btn_slotid.setOnClickListener {
            slotid = txt_slotid.text.toString().trim()

            showLoading()

            val starRewardedVideo = StarRewardVideo(
                this,
                slotid
            ).apply {
                requestSuccessListener = {
                    hideLoading()
                    Logger.e("RewardedVideoActivity", "请求激励视频广告成功！")

                    showRewardedVideo(this)
                }

                requestErrorListener = {
                    hideLoading()
                    Logger.e("SplashActivity", "请求激励视频广告失败：$it")
                    if (!isFinishing) {
                        startMainActivity()
                    }
                }

                viewShowListener = {
                    Logger.e("RewardedVideoActivity", "激励视频广告展示！")
                }

                viewClickListener = {
                    Logger.e("RewardedVideoActivity", "激励视频广告点击！")
                }

                viewCloseListener = {
                    Logger.e("RewardedVideoActivity", "激励视频广告关闭！")
                    if (!isFinishing) {
                        startMainActivity()
                    }
                }

                rewardedResultListener = {
                    Logger.e("RewardedVideoActivity", "激励视频是否下发奖励: $it")
                }
            }

            starRewardedVideo.load()
        }
    }

    /**
     * 播放激励视频
     */
    private fun showRewardedVideo(starRewardedVideo: IRewardedVideo) {
        starRewardedVideo.show()
    }

    /**
     * 跳转到主Activity
     */
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}