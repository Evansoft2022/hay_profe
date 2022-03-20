package com.example.application.method

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

class photo {
    fun savePhotoFromGalery(activity: Activity,code:Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent,code)
    }

    fun saveGallery(context: Context,id:Long,uri: Uri){
        val file = File(context.filesDir,id.toString())
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
        if (bytes != null) {
            file.writeBytes(bytes)
        }
    }

    fun getImage(context: Context,id: Long): Uri{
        val file = File(context.filesDir, id.toString() )
        return if(file.exists()) Uri.fromFile(file)
        else Uri.parse("android.resource://com.example.application/drawable/splash_screen")
    }
}