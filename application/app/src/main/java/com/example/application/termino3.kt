package com.example.application

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.VideoView

class termino3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termino3)

        val timer = object: CountDownTimer(9000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                startActivity(
                    //Intent(this, Account::class.java)
                    Intent(this@termino3, termino4::class.java)
                )

            }
        }
        timer.start()
        val path = "android.resource://"+packageName+"/"+R.raw.termino3
        val uri = Uri.parse(path)
        val video = findViewById<VideoView>(R.id.termino3)
        video.setVideoURI(uri)
        video.requestFocus()
        video.start()
    }
}