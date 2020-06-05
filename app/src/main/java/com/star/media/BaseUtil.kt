package com.star.media

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * 判断是否有相应权限
 */
fun AppCompatActivity.isPermissionGranted(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

/**
 * 是否需要向用户解释请求权限的目的
 */
fun AppCompatActivity.shouldShowPermissionRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

/**
 * 请求权限
 */
fun AppCompatActivity.requestPermission(permission: String, requestId: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(permission), requestId)
}

/**
 * 批量请求权限
 */
fun AppCompatActivity.batchRequestPermissions(permissions: Array<String>, requestId: Int) {
    ActivityCompat.requestPermissions(this, permissions, requestId)
}

fun IntArray.containsOnly(num: Int): Boolean = filter { it == num }.isNotEmpty()