package com.star.media

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.star.media.ListVideoActivity.SimpleTestAdapter.Companion.Type_AD
import com.star.media.ListVideoActivity.SimpleTestAdapter.Companion.Type_Video
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarListVideoView
import kotlinx.android.synthetic.main.activity_list_video.*

class ListVideoActivity : BaseActivity() {

    private val items = ArrayList<Item>()

    private lateinit var simpleTestAdapter: SimpleTestAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_video)

        initializeView()

        initializeRewardedVideoAD()
    }

    private fun initializeView() {

        for (i in 0 until 3) {
            val item = Item(Type_Video, null, "这是测试$i")
            items.add(item)
        }

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_list_video.layoutManager = linearLayoutManager
        rv_list_video.isNestedScrollingEnabled = false

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rv_list_video)

        simpleTestAdapter = SimpleTestAdapter(items)
        rv_list_video.adapter = simpleTestAdapter

        rv_list_video.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()

                    if (lastVisiblePosition >= linearLayoutManager.itemCount - 2 && !loading) {
                        loadListVideoAdMore()
                    }
                }

            }
        })
    }

    private var slotId = "wjxc_0000013378"
//    private var slotId = "wjxc_0000013351" //豹来电

    /**
     * 初始化列表视频广告
     */
    private fun initializeRewardedVideoAD() {

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


    internal class SimpleTestAdapter(val datas: List<Item>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            const val Type_AD = 0x80
            const val Type_Video = 0x81
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            if (viewType == Type_AD) {
                val adView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_video_ad, parent, false)
                return ADViewHolder(adView)
            }


            val videoView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_video, parent, false)
            return ViewHolder(videoView)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            val item = datas[position]

            if (viewHolder is ADViewHolder) {
                viewHolder.fl_item_content.removeAllViews()
                viewHolder.fl_item_content.addView(item.starListVideoView)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return datas[position].type
        }

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var tv_list_video: ImageView = itemView.findViewById(R.id.iv_list_video)
        }

        inner class ADViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var fl_item_content: FrameLayout = itemView.findViewById(R.id.fl_item_content)
        }

        override fun getItemCount(): Int {
            return datas.size
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