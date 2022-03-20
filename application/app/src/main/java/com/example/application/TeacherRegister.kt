package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray

class TeacherRegister : AppCompatActivity() {
    private var database: DatabaseReference = Firebase.database.reference
    private var readdb :DatabaseReference = FirebaseDatabase.getInstance().getReference("data")
    private var city_position:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_register)
        readSubjects()
    }


    private fun readSubjects(){
        val jsonObject : MutableList<String> = mutableListOf("Elije una materia")
        readdb.child("subject").get().addOnSuccessListener {
                val gson = Gson()
                val json = gson.toJson(it.value)
                val datajson = JSONArray(json)
                for(i in 0 until datajson.length()) {
                    jsonObject.add(datajson[i].toString())
                    println(jsonObject.toString())
                }
            }

        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, jsonObject)
            spinner.adapter = adapter

         spinner.onItemSelectedListener = object :
           AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>,
                                         view: View, position: Int, id: Long)
             {
                 city_position = jsonObject[position].toString()
             }

             override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
             }
           }
        }


        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, jsonObject)
            spinner1.adapter = adapter

         spinner1.onItemSelectedListener = object :
           AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>,
                                         view: View, position: Int, id: Long)
             {
                 city_position = jsonObject[position].toString()
             }

             override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
             }
           }
        }

        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, jsonObject)
            spinner2.adapter = adapter

         spinner2.onItemSelectedListener = object :
           AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>,
                                         view: View, position: Int, id: Long)
             {
                 city_position = jsonObject[position].toString()
             }

             override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
             }
           }
        }
    }


    private fun register(){
        val name = findViewById<TextView>(R.id.txtFirstname)
        val lastname = findViewById<TextView>(R.id.txtLastname)
        val email = findViewById<TextView>(R.id.txtEmail)
        val phone = findViewById<TextView>(R.id.txtPhone)
        val password = findViewById<TextView>(R.id.txtPass)
        val balance = "0.0$"
        val type = "student"
        val status = "active"

        val keys: List<String> = listOf("firstname", "lastname", "email", "phone", "password", "city","balance","type","status")

        val values: List<String> = listOf(name.text.toString(),lastname.text.toString(),
                                            email.text.toString(),phone.text.toString(),
                                            password.text.toString(),city_position,balance,type,status
                                        )
        if(name.text.isNotEmpty() || lastname.text.isNotEmpty() || email.text.isNotEmpty() ||
                    phone.text.isNotEmpty() || password.text.isNotEmpty() || city_position != "Selecciona Ciudad"
        ){
            for(i in 0..8){
                data(values[i],keys[i],readDB().toString())
            }
            startActivity(Intent(this,termino1::class.java))
        }
        else{
            Toast.makeText(this,"Debe completar todo el formulario",Toast.LENGTH_LONG).show()
        }


    }

    private fun data(value : String, key:String, position:String){
        database.child("data").child("teacher").child(position).child(key).setValue(value)
    }


    private fun readDB():Int
    {
        var value = 0
        readdb.get().addOnSuccessListener {
            //it.value
            val gson = Gson()
            val json = gson.toJson(it.value)
            val datajson = JSONArray(json)
            for(i in 0 until datajson.length()){
                value = i
            }
        }
        return value

    }
}
