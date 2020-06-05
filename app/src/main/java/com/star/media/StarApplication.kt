package com.star.media

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.multidex.MultiDexApplication
import com.starmedia.adsdk.StarMedia


class StarApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()


        initPieWebView()

        StarMedia.debugMode(true)

        StarMedia.setUsePlatformCache(true)

//        StarMedia.insertCustomPlatformConfig(assets.open("custom_platforms.json").use {
//            it.reader().readText()
//        })

        StarMedia.initialMedia(this, "2370")

        val userId = "fc6bcd61-961c-40e4-9d96-552028c859"
        StarMedia.initialSearch(this, "2370", userId, listOf("88888", "66666"))
    }

    private fun initPieWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            webviewSetPath(this)
        }
    }


    @RequiresApi(api = 28)
    fun webviewSetPath(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName(context)
            if (!context.packageName.equals(processName)) { //判断不等于默认进程名称
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    fun getProcessName(context: Context): String? {
        if (context == null) return null
        val manager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName
            }
        }
        return null
    }
}