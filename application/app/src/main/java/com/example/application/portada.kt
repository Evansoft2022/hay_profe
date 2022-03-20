package com.example.application

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.VideoView

class portada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portada)
        val timer = object: CountDownTimer(9000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                startActivity(
                    //Intent(this, Account::class.java)
                    Intent(this@portada, social::class.java)
                )

            }
        }
        timer.start()
        val path = "android.resource://"+packageName+"/"+R.raw.portada
        val uri = Uri.parse(path)
        val video = findViewById<VideoView>(R.id.termino5)
        video.setVideoURI(uri)
        video.requestFocus()
        video.start()
    }
}