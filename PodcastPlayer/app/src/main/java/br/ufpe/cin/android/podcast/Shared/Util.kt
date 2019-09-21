package br.ufpe.cin.android.podcast.Shared

import android.os.Environment
import kotlinx.android.synthetic.main.itemlista.*
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun isExternalStorageAvailable(ctx: Context): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED &&
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
        ctx,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}

val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
val WRITE_EXTERNAL_STORAGE_REQUEST = 710

fun requestExternalStoragePermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        STORAGE_PERMISSIONS,
        WRITE_EXTERNAL_STORAGE_REQUEST
    )
}