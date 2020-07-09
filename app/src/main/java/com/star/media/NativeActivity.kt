package com.star.media

import android.os.Bundle
import android.widget.Toast
import com.starmedia.adsdk.StarNativeView
import kotlinx.android.synthetic.main.activity_native.*

class NativeActivity : BaseActivity() {

    private var slotId = "wjxc_0000013377"

    private var starNativeView: StarNativeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_160 -> {
                    slotId = "wjxc_0000013377"
                }
                R.id.btn_170 -> {
                    slotId = "wjxc_0000013360"
                }
                R.id.btn_180 -> {
                    slotId = "wjxc_0000013361"
                }
                R.id.btn_190 -> {
                    slotId = "wjxc_0000013362"
                }
                R.id.btn_200 -> {
                    slotId = "wjxc_0000013363"
                }
                R.id.btn_210 -> {
                    slotId = "wjxc_0000013370"
                }
                R.id.btn_220 -> {
                    slotId = "wjxc_0000013364"
                }
                R.id.btn_230 -> {
                    slotId = "wjxc_0000013371"
                }
                R.id.btn_240 -> {
                    slotId = "wjxc_0000013372"
                }
                R.id.btn_250 -> {
                    slotId = "wjxc_0000013373"
                }
                R.id.btn_260 -> {
                    slotId = "wjxc_0000013374"
                }
            }

            showLoading()
            starNativeView?.apply {
                fl_container.removeAllViews()
                this.destroy()
            }
            starNativeView = StarNativeView(
                this, slotId
            ).apply {
                requestSuccessListener = {
                    hideLoading()
                    if (isFinishing) {
                        this.destroy()
                    } else {
                        fl_container.addView(starNativeView)
                    }
                }

                requestErrorListener = {
                    hideLoading()
                    Toast.makeText(applicationContext, "Error $it", Toast.LENGTH_LONG).show()
                }

                viewShowListener = {
                    Toast.makeText(applicationContext, "Banner Show", Toast.LENGTH_LONG)
                        .show()
                }

                viewClickListener = {
                    Toast.makeText(applicationContext, "Banner Click", Toast.LENGTH_LONG)
                        .show()
                }

                viewCloseListener = {
                    Toast.makeText(applicationContext, "Banner Close", Toast.LENGTH_LONG)
                        .show()
                }
            }

            starNativeView!!.load()
        }

        btn_160.isChecked = true
    }

    override fun onDestroy() {
        starNativeView?.destroy()
        super.onDestroy()
    }
}
