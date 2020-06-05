package com.star.media

import android.content.Intent
import android.os.Bundle
import com.starmedia.adsdk.StarMedia
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
            startActivity(Intent(this, InterstitialActivity::class.java))
        }

        btn_test_rewarded_video.setOnClickListener {
            startActivity(Intent(this, RewardedVideoActivity::class.java))
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