package com.star.media

import android.os.Bundle
import android.widget.Toast
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarInterstitial
import kotlinx.android.synthetic.main.layout_slotid.*

class InterstitialActivity : BaseActivity() {

    var slotid = "wjxc_0000013368"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial)
        txt_slotid.setText(slotid)

        btn_slotid.setOnClickListener {
            slotid = txt_slotid.text.toString().trim()
            showLoading()

            val starInterstitialViewGroup = StarInterstitial(
                this,
                slotid
            ).apply {
                requestSuccessListener = {
                    hideLoading()
                    this.show()
                }

                requestErrorListener = {
                    hideLoading()
                    Toast.makeText(
                        this@InterstitialActivity,
                        "Inters Error $it",
                        Toast.LENGTH_LONG
                    ).show()
                }

                viewShowListener = {
                    Toast.makeText(
                        this@InterstitialActivity,
                        "Inters Show",
                        Toast.LENGTH_LONG
                    ).show()

                    Logger.e("InterstitialActivity", "Inters Show")
                }

                viewClickListener = {
                    Toast.makeText(
                        this@InterstitialActivity,
                        "Inters Click",
                        Toast.LENGTH_LONG
                    ).show()

                    Logger.e("InterstitialActivity", "Inters Click")
                }

                viewCloseListener = {
                    Toast.makeText(
                        this@InterstitialActivity,
                        "Inters Close",
                        Toast.LENGTH_LONG
                    ).show()

                    Logger.e("InterstitialActivity", "Inters Close")
                }
            }

            starInterstitialViewGroup.load()
        }
    }
}
