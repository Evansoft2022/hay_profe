package com.example.application

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ListView
import android.widget.Toast
import com.example.application.method.messageprivate
import com.example.application.method.messageprivateAdapater
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray

class chatTP : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var userPhone = ""

    private var database: DatabaseReference = Firebase.database.reference.child("data").child("message").child("conver")
    private var readdb: DatabaseReference = FirebaseDatabase.getInstance().getReference("data").child("message").child("conver")
    private var listMessages: MutableList<messageprivate> = mutableListOf()
    private var listEnvioMensaje: MutableList<messageprivate> = mutableListOf()
    private var phoneT = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_tp)
        val lista = findViewById<ListView>(R.id.messageconversation)
        var valueIndex = ""
        var notExists = true


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    readdb.child(userPhone).child(phoneT).get().addOnSuccessListener {
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val jobject = JSONArray(json)
                        listMessages.clear()
                        for (i in 0 until jobject.length()) {
                            val data = jobject.getJSONObject(i)
                            valueIndex = i.toString()

                            val message_fire = data.getString("message")
                            val user_echo = data.getString("userRecptor")
                            val hours = data.getString("hour")
                            var verify = true
                            if( user_echo == phoneT){
                                verify = false
                            }
                            println(verify.toString()+" VALOR")
                            val message = messageprivate(message_fire, user_echo, hours,verify)
                            listMessages.add(message)
                        }
                    }
                    val adapter: Adapter
                    adapter = messageprivateAdapater(this@chatTP, listMessages)

                    lista.adapter = adapter
                    lista.setSelection(lista.getAdapter().getCount() - 1);
                } else {
                    valueIndex = "0"
                    println("ENTRE A ELSE")
                    notExists = false
                    Toast.makeText(this@chatTP, "No existe", Toast.LENGTH_LONG).show()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@chatTP, databaseError.message, Toast.LENGTH_LONG).show()
            }
        }

        for( i in 1..4) {
            println(notExists)
            if (!notExists) {
                userPhone = "87654321"
                phoneT = "1468"
                readdb.child(userPhone).child(phoneT).addValueEventListener(postListener)
            } else if (!notExists) {
                userPhone = "1468"
                phoneT = "87654321"
                readdb.child(userPhone).child(phoneT).addValueEventListener(postListener)
            } else {
                Toast.makeText(this, "No exite", Toast.LENGTH_SHORT).show()
            }
        }

    }
}