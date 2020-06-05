package com.star.media

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.star.media.ListVideoActivity.SimpleTestAdapter.Companion.Type_AD
import com.star.media.ListVideoActivity.SimpleTestAdapter.Companion.Type_Video
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarListVideoView
import kotlinx.android.synthetic.main.activity_vp_list_video.*

class ListVideoVPActivity : BaseActivity() {

    private val items = ArrayList<Item>()

    private lateinit var simpleTestAdapter: SimpleTestAdapter

    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vp_list_video)

        initializeView()

        initializeListVideoAD()
    }

    private fun initializeView() {

        for (i in 0 until 3) {
            val item = Item(Type_Video, null, "这是测试$i")
            items.add(item)
        }

        simpleTestAdapter = SimpleTestAdapter(this)

        vp_list_video.adapter = simpleTestAdapter

        vp_list_video.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                simpleTestAdapter.onPageSelected(position)
            }
        })
    }

    private var slotId = "wjxc_0000013378"
//    private var slotId = "wjxc_0000013351" //豹来电

    /**
     * 初始化列表视频广告
     */
    private fun initializeListVideoAD() {

        showLoading()

        loading = true

        val listVideoView = StarListVideoView(
            this, slotId
        ).apply {
            requestErrorListener = {
                Logger.e("ListVideoActivity", "请求列表视频广告失败：$it")
                hideLoading()
                if (!isFinishing) {
                    startMainActivity()
                }

                loading = false
            }

            requestSuccessListener = {
                Logger.e("ListVideoActivity", "请求列表视频广告成功！")
                hideLoading()

                handleListVideoAd(this)

                loading = false

                loadListVideoAdMore()
            }

            viewShowListener = {
                Logger.e("ListVideoActivity", "列表视频广告展示！")
            }

            viewClickListener = {
                Logger.e("ListVideoActivity", "列表视频广告点击！")
            }
        }
        listVideoView.load()
    }

    private fun loadListVideoAdMore() {
        val listVideoView = StarListVideoView(
            this, slotId
        ).apply {
            requestErrorListener = {
                Logger.e("ListVideoActivity", "请求列表视频广告失败：$it")

                loading = false
            }

            requestSuccessListener = {
                Logger.e("ListVideoActivity", "请求列表视频广告成功！")

                handleListVideoAd(this)

                loading = false
            }

            viewShowListener = {
                Logger.e("ListVideoActivity", "列表视频广告展示！")
            }

            viewClickListener = {
                Logger.e("ListVideoActivity", "列表视频广告点击！")
            }
        }
        listVideoView.load()
    }

    /**
     * 播放列表视频
     */
    private fun handleListVideoAd(starListVideoView: StarListVideoView) {
        val item = Item(Type_AD, starListVideoView, "")
        items.add(item)

        for (i in 0 until 3) {
            val videoItem = Item(Type_Video, null, "这是测试$i")
            items.add(videoItem)
        }

        simpleTestAdapter.notifyDataSetChanged()
    }

    /**
     * 跳转到主Activity
     */
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    inner class SimpleTestAdapter(context: Context) : PagerAdapter() {
        private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        private val views: SparseArray<View> = SparseArray()

        override fun getCount(): Int {
            return items.size
        }

        override fun isViewFromObject(view: View, other: Any): Boolean {
            return view === other
        }

        override fun destroyItem(container: ViewGroup, position: Int, other: Any) {
            container.removeView(other as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view = views[position]

            if (view == null) {
                val item = items[position]

                if (item.type == Type_AD) {
                    view = layoutInflater.inflate(R.layout.item_list_video_ad, null, false)

                    initializeView(view, item)
                } else {
                    view = layoutInflater.inflate(R.layout.item_list_video, null, false)
                    initializeImageView(view, item)
                }

                views.put(position, view)
            }
            container.addView(view)

            return view
        }

        private fun initializeView(view: View?, item: Item?) {
            if (view != null && item != null) {
                val fl_item_content: FrameLayout = view.findViewById(R.id.fl_item_content)
                fl_item_content.removeAllViews()
                fl_item_content.addView(item.starListVideoView)
            }
        }

        private fun initializeImageView(view: View?, item: Item?) {
            if (view != null && item != null) {
                val tv_list_video: ImageView = view.findViewById(R.id.iv_list_video)
            }
        }

        fun onPageSelected(position: Int) {
//            val view = views.get(position)
//            if (view != null) {
//                val item = items[position]
//                if (item.type == Type_AD) {
//                    initializeView(view, item)
//                }
//            }
//
//            if (position > 0) {
//                val preItem = items[position - 1]
//                if (preItem.type == Type_AD) {
//                    val preView = views.get(position - 1)
//                    preView?.findViewById<FrameLayout>(R.id.fl_item_content)?.removeAllViews()
//                }
//            } else if (position < items.size - 1) {
//                val nextItem = items[position + 1]
//                if (nextItem.type == Type_AD) {
//                    val nextView = views.get(position + 1)
//                    nextView?.findViewById<FrameLayout>(R.id.fl_item_content)?.removeAllViews()
//                }
//            }
        }
    }

    class Item(type: Int, starListVideoView: StarListVideoView?, message: String) {
        var type = Type_Video
        var starListVideoView: StarListVideoView? = null
        var message: String

        init {
            this.type = type
            this.starListVideoView = starListVideoView
            this.message = message
        }
    }
}