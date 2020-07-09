package com.star.media

import android.os.Bundle
import android.widget.Toast
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarBannerView
import kotlinx.android.synthetic.main.activity_banner.*

class BannerActivity : BaseActivity() {

    private var bannerAd: StarBannerView? = null

    var slotid = "wjxc_0000013365"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)
        showLoading()
        bannerAd?.apply {
            fl_container.removeAllViews()
            this.destroy()
        }
        bannerAd = StarBannerView(
            this@BannerActivity, slotid
        ).apply {
            requestSuccessListener = {
                hideLoading()
                if (isFinishing) {
                    this.destroy()
                } else {
                    fl_container.addView(this)
                }
            }

            requestErrorListener = {
                hideLoading()
                Toast.makeText(applicationContext, "Error $it", Toast.LENGTH_LONG).show()
                finish()
            }

            viewShowListener = {
                Toast.makeText(applicationContext, "Banner Show", Toast.LENGTH_LONG)
                    .show()
                Logger.e("BannerActivity", "Banner Show")
            }

            viewClickListener = {
                Toast.makeText(applicationContext, "Banner Click", Toast.LENGTH_LONG)
                    .show()
                Logger.e("BannerActivity", "Banner Click")
            }

            viewCloseListener = {
                Toast.makeText(applicationContext, "Banner Close", Toast.LENGTH_LONG)
                    .show()
                Logger.e("BannerActivity", "Banner Close")
                finish()
            }
        }

        bannerAd!!.load()

    }

    override fun onDestroy() {
        bannerAd?.destroy()
        super.onDestroy()
    }
}
