package com.assesment.imagepickerapplication.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

/**
 * Created by Arman Reyaz on 10/13/2021.
 */
val PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)

fun Context.checkCameraPermission(): Boolean {

    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    )
        return true
    else {
        ActivityCompat.shouldShowRequestPermissionRationale(
            this as Activity,
            Manifest.permission.CAMERA
        )
    }
    return false
}

fun Context.showToast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Context.checkReadPermission(): Boolean {

    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    )
        return true
    else {
        ActivityCompat.shouldShowRequestPermissionRationale(
            this as Activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
    return false
}


 fun Context.shouldRequestPermissionsAtRuntime(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

 fun Context.arePermissionsGranted(): Boolean {
    return PERMISSIONS.all { ContextCompat.checkSelfPermission(this, it) == PERMISSION_GRANTED }
}

 fun Context.callRequestPermission() {
    //requestPermissions(PERMISSIONS, 0)
    requestPermissions(this as Activity,PERMISSIONS, 0)

}