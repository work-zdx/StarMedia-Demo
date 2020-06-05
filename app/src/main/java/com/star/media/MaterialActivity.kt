package com.star.media

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.starmedia.adsdk.AdMaterial
import com.starmedia.adsdk.StarAdType
import com.starmedia.adsdk.StarMaterial
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.ad_material_1.view.*
import kotlinx.android.synthetic.main.ad_material_2.view.*
import kotlinx.android.synthetic.main.ad_material_3.view.*
import kotlinx.android.synthetic.main.ad_material_4.view.*

class MaterialActivity : AppCompatActivity() {

    private var adMaterial: AdMaterial? = null

    val SLOT_ID = "wjxc_0000013364"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        btn_load.setOnClickListener {
            StarMaterial(this, SLOT_ID).apply {
                requestSuccessListener = {
                    adMaterial = this
                    showMaterialAd()
                }

                requestErrorListener = { msg ->
                    Toast.makeText(this@MaterialActivity, "Error $msg", Toast.LENGTH_LONG).show()
                }
            }.load()
        }
    }

    private fun showMaterialAd() {
        ad_container.removeAllViews()
        ad_container.addView(
            adMaterial!!.getAdContainer(),
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        when (adMaterial?.getType()) {
            StarAdType.TYPE_160, StarAdType.TYPE_170,
            StarAdType.TYPE_180, StarAdType.TYPE_190,
            StarAdType.TYPE_230, StarAdType.TYPE_240, StarAdType.TYPE_250 -> {
                println("type: TYPE_1IMAGE_2TEXT")
                val view =
                    layoutInflater.inflate(R.layout.ad_material_1, adMaterial!!.getAdContainer())

                bindView(view)
            }
            StarAdType.TYPE_200 -> {
                println("type: TYPE_2IMAGE_2TEXT")

                val view =
                    layoutInflater.inflate(R.layout.ad_material_2, adMaterial!!.getAdContainer())

                bindView2(view)
            }
            StarAdType.TYPE_220 -> {
                println("type: TYPE_3IMAGE")

                val view =
                    layoutInflater.inflate(R.layout.ad_material_3, adMaterial!!.getAdContainer())

                bindView3(view)
            }
            StarAdType.TYPE_210, StarAdType.TYPE_260 -> {
                println("type: TYPE_BIG_IMAGE")

                val view =
                    layoutInflater.inflate(R.layout.ad_material_4, adMaterial!!.getAdContainer())

                bindViewBig(view)
            }
        }
    }

    private fun bindView(view: View) {
        if (adMaterial?.getIcon() != null && adMaterial?.getIcon()?.isNotEmpty() == true) {
            Glide.with(this).load(adMaterial?.getIcon()).into(view.iv_image)
        }

        view.tv_title.text = adMaterial?.getTitle() ?: "这里是标题"

        adMaterial?.registerViewForInteraction(
            listOf(view.iv_image),
            listOf(view.tv_title),
            object : AdMaterial.AdInteractionListener {
                override fun onAdClicked() {
                    Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdCreativeClick() {
                    Toast.makeText(this@MaterialActivity, "创意点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdShow() {
                    Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
                }

            })

        view.tv_platform.text = if (adMaterial?.getPlatform() == "TT") "穿山甲" else "百度"
    }

    private fun bindView2(view: View) {

        if (adMaterial?.getIcon() != null && adMaterial?.getIcon()?.isNotEmpty() == true) {
            Glide.with(this).load(adMaterial?.getIcon()).into(view.iv_2_icon)
        }

        view.tv_2_title.text = adMaterial?.getTitle() ?: "这里是标题"

        val urls = adMaterial?.getImages()

        if (urls != null && urls.isNotEmpty()) {
            val url = urls[0]

            if (url.isNotEmpty()) {
                Glide.with(this).load(url).into(view.iv_2_image)
            }
            adMaterial?.registerViewForInteraction(
                listOf(view.iv_2_image),
                listOf(view.tv_2_title),
                object : AdMaterial.AdInteractionListener {
                    override fun onAdClicked() {
                        Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdCreativeClick() {
                        Toast.makeText(this@MaterialActivity, "创意", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdShow() {
                        Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
                    }

                })
        }

        view.tv_2_desc.text = adMaterial?.getDesc() ?: "这里是描述"
    }

    private fun bindView3(view: View) {
        view.tv_3_title.text = adMaterial?.getTitle() ?: "这里是标题"

        val urls = adMaterial?.getImages()

        if (urls != null && urls.size >= 3) {
            val first = urls[0]

            if (first.isNotEmpty()) {
                Glide.with(this).load(first).into(view.iv_3_first)
            }

            val second = urls[1]

            if (second.isNotEmpty()) {
                Glide.with(this).load(second).into(view.iv_3_second)
            }

            val third = urls[1]

            if (third.isNotEmpty()) {
                Glide.with(this).load(third).into(view.iv_3_third)
            }

            adMaterial?.registerViewForInteraction(
                listOf(view.iv_3_first, view.iv_3_second),
                listOf(view.iv_3_third),
                object : AdMaterial.AdInteractionListener {
                    override fun onAdClicked() {
                        Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdCreativeClick() {
                        Toast.makeText(this@MaterialActivity, "创意点击", Toast.LENGTH_LONG).show()
                    }

                    override fun onAdShow() {
                        Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
                    }

                })

        } else {
            view.cl_material_3image.visibility = View.GONE
            Toast.makeText(this@MaterialActivity, "3图广告返回的图片数量不足！", Toast.LENGTH_LONG).show()
        }

        view.tv_3_platform.text = if (adMaterial?.getPlatform() == "TT") "穿山甲" else "百度"
    }

    private fun bindViewBig(view: View) {
        view.tv_big_title.text = adMaterial?.getTitle() ?: "这里是标题"

        val urls = adMaterial?.getImages()

        if (urls != null && urls.isNotEmpty()) {
            val url = urls[0]

            if (url.isNotEmpty()) {
                Glide.with(this).load(url).into(view.iv_big_image)
            }
        }

        view.tv_big_desc.text = adMaterial?.getDesc() ?: "这里是描述"

        adMaterial?.registerViewForInteraction(
            listOf(view.cl_material_big_image),
            listOf(view.iv_big_image),
            object : AdMaterial.AdInteractionListener {
                override fun onAdClicked() {
                    Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdCreativeClick() {
                    Toast.makeText(this@MaterialActivity, "创意点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdShow() {
                    Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
