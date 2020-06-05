package com.star.media

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.starmedia.adsdk.ReqRet
import com.starmedia.adsdk.StarMedia
import com.starmedia.adsdk.bean.JSAD
import kotlinx.android.synthetic.main.activity_jsad.*
import java.io.File

class JSADActivity : AppCompatActivity() {

    var slotId = "e37b59351b17cd"
    var slotName = "wjxc_0000013401"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jsad)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_01 -> {
                    slotId = "e37b59351b17cd"
                    slotName = "wjxc_0000013401"
                }
                R.id.btn_02 -> {
                    slotId = "e4417264a9e02e"
                    slotName = "wjxc_0000013402"
                }
                R.id.btn_03 -> {
                    slotId = "de6c7d8ee72154"
                    slotName = "wjxc_0000013403"
                }
                R.id.btn_04 -> {
                    slotId = "dd19e502730cda"
                    slotName = "wjxc_0000013404"
                }
                R.id.btn_05 -> {
                    slotId = "e136f9cf1720ae"
                    slotName = "wjxc_0000013405"
                }
            }
            btn_load.text = "加载（${slotName}）广告"
        }

        btn_load.text = "加载（${slotName}）广告"

        var script = ""

        btn_load.setOnClickListener {
            if (slotId.isNotBlank()) {
                StarMedia.loadJSAD(slotId, object : ReqRet<JSAD> {
                    override fun onSuccess(result: JSAD) {
                        println(result.toString())

                        script = "" +
                                "(function () {\n" +
                                "    var adElement = document.createElement(\"script\");\n" +
                                "    adElement.setAttribute('src', '${result.src}');\n" +
                                "    adElement.setAttribute('smua', '${result.smua}');\n" +
                                "    adElement.setAttribute('type', 'text/javascript');\n" +
                                "    document.body.appendChild(adElement);\n" +
                                "})()"

//                        web_view.loadDataWithBaseURL(
//                            "https://api.newsyunwang.com",
//                            "<html><body>下边就是广告！</body></html>",
//                            "text/html",
//                            "utf-8",
//                            "https://api.newsyunwang.com"
//                        )

                        web_view.loadUrl("https://cn.bing.com/")
                    }

                    override fun onError(message: String) {
                        Toast.makeText(this@JSADActivity, message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        val webSettings = web_view.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheEnabled(true)
        webSettings.setAppCachePath(File(cacheDir, "webview").absolutePath)
        webSettings.setSupportZoom(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    view.evaluateJavascript(script, null)
                } else {
                    view.loadUrl("javascript:$script")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        web_view.stopLoading()
        web_view.clearHistory()
        web_view.webChromeClient = null
        web_view.clearCache(true)
        web_view.loadUrl("about:blank")
        web_view.pauseTimers()
    }
}
