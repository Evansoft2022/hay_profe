package com.example.application.optionItem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.application.ChatP
import com.example.application.MainActivity
import com.example.application.R
import com.example.application.R.id.drawableLyout
import com.example.application.R.id.drawableLyoutStudent
import com.example.application.chat_comunitario
import com.example.application.method.teacher
import com.example.application.method.teacherAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.gson.Gson
import org.json.JSONArray

class listStudent : AppCompatActivity() {

    private var readdb: DatabaseReference = FirebaseDatabase.getInstance().getReference("data").child("student")
    private val teacher_filter: MutableList<String> = mutableListOf()


    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_student)

        val listTeacher: MutableList<teacher> = mutableListOf()
        val lista = findViewById<ListView>(R.id.list_teacher)

        val searchStudent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readdb.get().addOnSuccessListener {
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val datajson = JSONArray(json)
                        for (i in 0 until datajson.length()) {
                            val jsonObject = datajson.getJSONObject(i)
                            val name = jsonObject.getString("firstname")
                            val email = jsonObject.getString("email")
                            //teacher_filter.add(jsonObject.toString())
                            val profe = teacher(name.toString(), email, R.drawable.splash_screen,true)
                            listTeacher.add(profe)


                        }

                        println(listTeacher[0])
                        val adapter = teacherAdapter(this@listStudent, listTeacher)

                        lista.adapter = adapter

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        readdb.addValueEventListener(searchStudent)
        lista.setOnItemClickListener { adapterView, view, i, l ->
            val getPositionSelect = listTeacher[i]
            println(getPositionSelect.subject)
            val intent = Intent(this, ChatP::class.java)
            intent.putExtra("userReceptor", "1468")
            startActivity(intent)
        }

        val drawableLayout : DrawerLayout = findViewById(drawableLyoutStudent)
        val navView : NavigationView = findViewById(R.id.nav_view_student)

        toggle = ActionBarDrawerToggle(this,drawableLayout,R.string.open,R.string.close)
        drawableLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_menu -> println("Home")
                R.id.Chat_menu -> startActivity(Intent(this,chat_comunitario::class.java))
                R.id.profile_menu -> startActivity(Intent(this,Profile::class.java))
                R.id.exit -> startActivity(Intent(this,MainActivity::class.java))
            }
            true

        }



    }
}