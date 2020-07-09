package com.star.media

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarInterstitial
import com.starmedia.adsdk.StarMedia
import com.starmedia.adsdk.StarRewardVideo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeView()
    }

    private fun initializeView() {


        btn_test_banner.setOnClickListener {
            startActivity(Intent(this, BannerActivity::class.java))
        }

        btn_test_native.setOnClickListener {
            startActivity(Intent(this, NativeActivity::class.java))
        }

        btn_test_material.setOnClickListener {
            startActivity(Intent(this, MaterialActivity::class.java))
        }

        btn_test_splash.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }

        btn_test_interstitial.setOnClickListener {
            showLoading()

            val starInterstitialViewGroup = StarInterstitial(
                this,
                "wjxc_0000013368"
            ).apply {
                requestSuccessListener = {
                    hideLoading()
                    this.show()
                }

                requestErrorListener = {
                    hideLoading()
                    Toast.makeText(
                        this@MainActivity,
                        "Inters Error $it",
                        Toast.LENGTH_LONG
                    ).show()
                }

                viewShowListener = {
                    Toast.makeText(
                        this@MainActivity,
                        "Inters Show",
                        Toast.LENGTH_LONG
                    ).show()

                    Logger.e("InterstitialActivity", "Inters Show")
                }

                viewClickListener = {
                    Toast.makeText(
                        this@MainActivity,
                        "Inters Click",
                        Toast.LENGTH_LONG
                    ).show()

                    Logger.e("InterstitialActivity", "Inters Click")
                }

                viewCloseListener = {
                    Toast.makeText(
                        this@MainActivity,
                        "Inters Close",
                        Toast.LENGTH_LONG
                    ).show()

                    Logger.e("InterstitialActivity", "Inters Close")
                }
            }

            starInterstitialViewGroup.load()
        }

        btn_test_rewarded_video.setOnClickListener {
            showLoading()

            val starRewardedVideo = StarRewardVideo(
                this,
                "wjxc_0000013369"
            ).apply {
                requestSuccessListener = {
                    hideLoading()
                    Logger.e("RewardedVideoActivity", "请求激励视频广告成功！")

                    show()
                }

                requestErrorListener = {
                    hideLoading()
                    Logger.e("SplashActivity", "请求激励视频广告失败：$it")
                }

                viewShowListener = {
                    Logger.e("RewardedVideoActivity", "激励视频广告展示！")
                }

                viewClickListener = {
                    Logger.e("RewardedVideoActivity", "激励视频广告点击！")
                }

                viewCloseListener = {
                    Logger.e("RewardedVideoActivity", "激励视频广告关闭！")
                }

                rewardedResultListener = {
                    Logger.e("RewardedVideoActivity", "激励视频是否下发奖励: $it")
                }
            }

            starRewardedVideo.load()
        }

        btn_test_long_video.setOnClickListener {
            startActivity(Intent(this, LongVideoActivity::class.java))
        }

        btn_test_list_video_recycler_view.setOnClickListener {
            startActivity(Intent(this, ListVideoActivity::class.java))
        }

        btn_test_list_video_view_pager.setOnClickListener {
            startActivity(Intent(this, ListVideoVPActivity::class.java))
        }

        btn_test_jsad.setOnClickListener {
            startActivity(Intent(this, JSADActivity::class.java))
        }

        btn_test_unioncontent.setOnClickListener {
            startActivity(Intent(this, TestContentActivity::class.java))
        }

        btn_test_linyuan.setOnClickListener {
            StarMedia.openLyTaskCenter(this, "88888")
        }
    }
}