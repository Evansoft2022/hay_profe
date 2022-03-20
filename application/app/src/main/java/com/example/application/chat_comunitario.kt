package com.example.application

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.application.method.messageprivate
import com.example.application.method.messageprivateAdapater
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class chat_comunitario : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var userPhone = ""

    private var database: DatabaseReference = Firebase.database.reference.child("data").child("message").child("comunitario")
    private var readdb: DatabaseReference = FirebaseDatabase.getInstance().getReference("data").child("message").child("comunitario")
    private var listMessages: MutableList<messageprivate> = mutableListOf()
    private var listEnvioMensaje: MutableList<messageprivate> = mutableListOf()
    private var phoneT = ""

    private val date = getCurrentDateTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_comunitario)

        sharedPreferences = this.getSharedPreferences("keyEmail", Context.MODE_PRIVATE)
        userPhone = sharedPreferences.getString("keyEmail", "No vay valor").toString()
        var valueIndex = ""
        var notExists = true
        val lista = findViewById<ListView>(R.id.messageconversation)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {


                    readdb.get().addOnSuccessListener {
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val jobject = JSONArray(json)
                        println(jobject)
                        listMessages.clear()
                        for (i in 0 until jobject.length()) {
                            val data = jobject.getJSONObject(i)
                            println(data)
                            valueIndex = i.toString()
                            val message_fire = data.getString("message")
                            val user_echo = data.getString("userRecptor")
                            val hours = data.getString("hour")
                            var verify = true
                            if( user_echo == phoneT){
                                verify = false
                            }
                            val message = messageprivate(message_fire, user_echo, hours,verify)
                            listMessages.add(message)
                        }
                    }
                    val adapter: Adapter
                    adapter = messageprivateAdapater(this@chat_comunitario, listMessages)
                    lista.adapter = adapter
                    lista.setSelection(lista.adapter.count - 1)
                } else {
                    println("Estoy entrando acá")
                    valueIndex = "0"
                    notExists = false
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@chat_comunitario, databaseError.message, Toast.LENGTH_LONG).show()
            }
        }
        readdb.addValueEventListener(postListener)

        val btn = findViewById<Button>(R.id.btnSend)
        btn.setOnClickListener {
            val sms = findViewById<TextView>(R.id.txtMessage)
            if(!sms.text.equals("")){
                var n = valueIndex.toInt()
                if(notExists) {
                    n += 1
                }
                writeMessage(phoneT,sms.text.toString(),(n).toString())
                sms.text=""
            }
            else{
                Toast.makeText(this@chat_comunitario,"No puede enviar un mensaje vacio",Toast.LENGTH_SHORT).show()
            }
        }



    }


    fun writeMessage(userReceptor : String,sms : String, index:String){
        val dateInString = date.toString("HH:mm")
        if (index == "0"){
            database.child("0").child("message").setValue(sms)
            database.child("0").child("userRecptor").setValue(userReceptor)
            database.child("0").child("hour").setValue(dateInString)
        }
        else {
            database.child(index).child("message")
                .setValue(sms)
            database.child(index).child("userRecptor")
                .setValue(userReceptor)
            database.child(index).child("hour")
                .setValue(dateInString)
        }
    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}