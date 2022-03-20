package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray

class StudentRegister : AppCompatActivity() {

    private var database: DatabaseReference = Firebase.database.reference
    private var readdb :DatabaseReference = FirebaseDatabase.getInstance().getReference("data").child("student")
    private var city_position:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)
        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.spinner)
        var value = 0


        val lee = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    readdb.get().addOnSuccessListener{
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val datajson = JSONArray(json)
                        for (i in 0 until datajson.length()) {
                            value = i
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        readdb.addListenerForSingleValueEvent(lee)
        println(value.toString()+" Valor del index")



        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

         spinner.onItemSelectedListener = object :
           AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>,
                                         view: View, position: Int, id: Long)
             {
                 city_position = languages[position].toString()
             }

             override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
             }
           }
        }

        val btnsaved = findViewById<Button>(R.id.btnSaved)
        btnsaved.setOnClickListener {
            register(value)
        }

    }

    private fun register(value : Int){
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


            val n = value + 1
            for(i in 0..8){
                data(values[i],keys[i],n.toString())
            }
            startActivity(Intent(this,termino1::class.java))
        }
        else{
            Toast.makeText(this,"Debe completar todo el formulario",Toast.LENGTH_LONG).show()
        }


    }

    private fun data(value : String, key:String, position:String){
        database.child("data").child("student").child(position).child(key).setValue(value)
    }


    /*private fun readDB():ValueEventListener
    {
        val lee = object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    readdb.get().addOnSuccessListener{
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val datajson = JSONArray(json)
                        for (i in 0 until datajson.length()) {
                            value = i
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        return lee


    }*/
}