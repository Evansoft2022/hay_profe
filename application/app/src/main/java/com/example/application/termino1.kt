package com.example.application

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.VideoView

class termino1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termino1)

        val timer = object: CountDownTimer(9000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                startActivity(
                    //Intent(this, Account::class.java)
                    Intent(this@termino1, termino2::class.java)
                )

            }
        }
        timer.start()
        val path = "android.resource://"+packageName+"/"+R.raw.termino1
        val uri = Uri.parse(path)
        val video = findViewById<VideoView>(R.id.video1)
        video.setVideoURI(uri)
        video.requestFocus()
        video.start()





    }
}