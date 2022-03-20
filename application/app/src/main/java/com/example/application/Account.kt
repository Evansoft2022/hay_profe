package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val student = findViewById<Button>(R.id.btnStudent)
        student.setOnClickListener {
            startActivity(Intent(this,StudentRegister::class.java))
        }

        val teacher = findViewById<Button>(R.id.btnTeacher)
        teacher.setOnClickListener {
            startActivity(Intent(this,TeacherRegister::class.java))
        }
    }
}