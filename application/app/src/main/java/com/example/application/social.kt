package com.example.application

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.application.method.photo
import com.example.application.method.subjects
import com.example.application.method.subjectsAdapter
import com.example.application.optionItem.Profile
import com.example.application.optionItem.chat_pri
import com.example.application.optionItem.listTeacher
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class social : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)

        val matematicas = subjects("Matematicas", R.drawable.mate)
        val estadistica = subjects("Estadistica",R.drawable.estadistica)
        val fisica = subjects("Fisica",R.drawable.fisica)
        val geografia = subjects("Geografia",R.drawable.geografia)
        val literatura = subjects("Literatura",R.drawable.literatura)


        val listSubjects = mutableListOf(matematicas,estadistica,fisica,geografia,literatura)


        val adapter = subjectsAdapter(this,listSubjects)
        val lista = findViewById<ListView>(R.id.listaSubjects)
        lista.adapter = adapter
        lista.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItemText = parent.getItemAtPosition(position)
            val posiSubject = listSubjects[position]
            selectSubjects(posiSubject.title)

        }

        sharedPreferences = this.getSharedPreferences("key", Context.MODE_PRIVATE)
        var value = sharedPreferences.getString("email","No vay valor")


        val drawableLayout : DrawerLayout = findViewById(R.id.drawableLyout)
        val navView : NavigationView = findViewById(R.id.nav_view)
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




    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return  true
        }

        return super.onOptionsItemSelected(item)
    }



    fun selectSubjects(subjects : String){
        val intenTeacher = Intent(this,listTeacher::class.java)
        intenTeacher.putExtra("subject",subjects)
        startActivity(intenTeacher)
    }


}