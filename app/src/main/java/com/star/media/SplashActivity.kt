package com.star.media

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.starmedia.adsdk.Logger
import com.starmedia.adsdk.StarMedia
import com.starmedia.adsdk.StarSplashView
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.layout_slotid.*
import java.util.*

class SplashActivity : BaseActivity() {

    private var permissionDenied = ArrayList<String>()

    companion object {
        private const val REQUEST_PERMISSION = 0x81
        private const val BATCH_REQUEST_PERMISSION = 0x82
        private const val REQUEST_SETTINGS_CODE = 0x83
    }

    /**
     * 检查权限
     */
    private fun checkPermissions() {
        Logger.e("MainActivity", "checkPermissions ${permissionDenied.size}")
        if (permissionDenied.isNotEmpty()) {
            if (permissionDenied.size == 1) {
                requestSinglePermission(permissionDenied[0])
            } else {
                batchRequestPermissions(permissionDenied)
            }
        }
    }

    /**
     * 请求权限
     */
    private fun requestSinglePermission(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowPermissionRationale(permission)) {
                Snackbar.make(
                    cl_main_content, "需要权限哦~",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("确定") {
                        requestPermission(permission, REQUEST_PERMISSION)
                    }
                    .show()
            } else {
                requestPermission(permission, REQUEST_PERMISSION)
            }
        }
    }

    /**
     * 批量请求权限
     */
    private fun batchRequestPermissions(permissions: ArrayList<String>) {
        var shouldShowRationale = false

        for (permission in permissions) {
            if (this.shouldShowPermissionRationale(permission)) {
                shouldShowRationale = true
                break
            }
        }

        if (shouldShowRationale) {
            Snackbar.make(
                cl_main_content, "需要权限哦~",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("确定") {
                    batchRequestPermissions(
                        permissions.toTypedArray(),
                        BATCH_REQUEST_PERMISSION
                    )
                }
                .show()
        } else {
            batchRequestPermissions(permissions.toTypedArray(), BATCH_REQUEST_PERMISSION)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION) {
            val permission = permissionDenied[0]
            if (grantResults.containsOnly(PackageManager.PERMISSION_GRANTED)) {

            } else {
                if (this.shouldShowPermissionRationale(permission)) {
                    Snackbar.make(
                        cl_main_content, "需要权限哦~",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("确定") {
                            requestPermission(permission, REQUEST_PERMISSION)
                        }
                        .show()
                } else {
                    Snackbar.make(
                        cl_main_content, "需要权限哦~",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("确定") {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", applicationContext.packageName, null)
                            intent.data = uri
                            startActivityForResult(intent, REQUEST_SETTINGS_CODE)
                        }
                        .show()
                }
            }
        } else if (requestCode == BATCH_REQUEST_PERMISSION) {
            for (i in permissions.indices) {
                val result = grantResults[i]
                val permission = permissions[i]

                if (permissionDenied.contains(permission) && result == PackageManager.PERMISSION_GRANTED) {
                    permissionDenied.remove(permission)
                }
            }

            if (permissionDenied.isNotEmpty()) {
                var shouldShowRationale = false
                for (permit in permissionDenied) {
                    if (this.shouldShowPermissionRationale(permit)) {
                        shouldShowRationale = true
                        break
                    }
                }

                if (shouldShowRationale) {
                    Snackbar.make(
                        cl_main_content, "需要权限哦~",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("确定") {
                            batchRequestPermissions(
                                permissionDenied.toTypedArray(),
                                BATCH_REQUEST_PERMISSION
                            )
                        }
                        .show()
                } else {
                    Snackbar.make(
                        cl_main_content, "需要权限哦~",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("确定") {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", applicationContext.packageName, null)
                            intent.data = uri
                            startActivityForResult(intent, REQUEST_SETTINGS_CODE)
                        }
                        .show()
                }
            } else {

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SETTINGS_CODE) {

            val grantedPermissions = arrayListOf<String>()

            for (permission in permissionDenied) {
                if (this.isPermissionGranted(permission)) {
                    grantedPermissions.add(permission)
                }
            }

            for (permission in grantedPermissions) {
                if (permissionDenied.contains(permission)) {
                    permissionDenied.remove(permission)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                checkPermissions()
            }
        }
    }


    private lateinit var splashHandler: SplashHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashHandler = SplashHandler(this)

        initializeSplashAD()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StarMedia.checkMediaPermission(this) {
                if (!it) {
                    val alertDialog = AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setCancelable(false)
                        .setMessage("软件运行需要一定的权限!")
                        .setPositiveButton("确定") { dialog, _ ->
                            dialog.dismiss()
                            StarMedia.loadAllPermissions {
                                permissionDenied.addAll(it)
                                runOnUiThread {
                                    checkPermissions()
                                }
                            }
                        }
                        .create()
                    alertDialog.show()
                }
            }
        }
    }

    var slotid = "wjxc_0000013367"

    /**
     * 初始化开屏广告
     */
    private fun initializeSplashAD() {
        txt_slotid.setText(slotid)

        btn_slotid.setOnClickListener {
            slotid = txt_slotid.text.toString().trim()

            showLoading()

            val splashViewGroup = StarSplashView(
                this, slotid, ll_splash_ad
            ).apply {
                requestSuccessListener = {
                    hideLoading()
                    ll_splash_ad.addView(this)
                    Logger.e("SplashActivity", "请求开屏广告成功！")
                }

                requestErrorListener = {
                    hideLoading()
                    Logger.e("SplashActivity", "请求开屏广告失败：$it")
                    if (!isFinishing) {
                        startMainActivity()
                    }
                }

                viewShowListener = {
                    Logger.e("SplashActivity", "开屏广告展示！")
                    splashHandler.removeMessages(0x80)
                }

                viewClickListener = {
                    Logger.e("SplashActivity", "开屏广告点击！")
                }

                viewCloseListener = {
                    Logger.e("SplashActivity", "开屏广告关闭！")
                    if (!isFinishing) {
                        startMainActivity()
                    }
                }
            }
            splashViewGroup.load()
        }
    }

    /**
     * 跳转到主Activity
     */
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        splashHandler.removeCallbacksAndMessages(null)
        finish()
    }

    private class SplashHandler(var splashActivity: SplashActivity) : Handler() {
        override fun handleMessage(message: Message) {
            when (message.what) {
                0x80 -> {
                    splashActivity.startMainActivity()
                }
            }
        }
    }
}