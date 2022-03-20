package com.example.application.optionItem

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import com.example.application.R
import com.example.application.method.envioAdapter
import com.example.application.method.messageprivate
import com.example.application.method.messageprivateAdapater
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class chat_pri : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var database: DatabaseReference = Firebase.database.reference
    private var readdb: DatabaseReference = FirebaseDatabase.getInstance().getReference("data")
    private var listMessages: MutableList<messageprivate> = mutableListOf()
    private var listEnvioMensaje: MutableList<messageprivate> = mutableListOf()
    //private val date = getCurrentDateTime()
    private val lista = findViewById<ListView>(R.id.messageconversation)


    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_pri)

        sharedPreferences = this.getSharedPreferences("keyEmail", Context.MODE_PRIVATE)
        //val dateInString = date.toString("HH:mm")
        val value = sharedPreferences.getString("keyEmail", "No vay valor").toString()


        /*database.child("data").child("message").child("conver").child(value).child("87654321").child("0").child("message").setValue("Hola")
        database.child("data").child("message").child("conver").child(value).child("87654321").child("0").child("user").setValue("12345678")
        database.child("data").child("message").child("conver").child(value).child("87654321").child("0").child("hour").setValue(dateInString)*/


        /*val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    //readData(value,"12345678")
                    Toast.makeText(this@chat_pri, "Si existe", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@chat_pri, "No existe", Toast.LENGTH_LONG).show()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@chat_pri, databaseError.message, Toast.LENGTH_LONG).show()
            }
        }
        readdb.child("message").child("conver").child(value).child("12345678")
            .addValueEventListener(postListener)*/
    }
    /*readdb.child("message").child("conver").child(value).child("87654321").get().addOnSuccessListener {
            val gson = Gson()
            val json = gson.toJson(it.value)
            val jobject = JSONArray(json)
            for (i in 0 until jobject.length()) {
                val data = jobject.getJSONObject(i)
                val message_fire = data.getString("message")
                val user_echo = data.getString("user")
                val hours = data.getString("hour")
                val message = messageprivate(message_fire, user_echo, hours)
                listMessages.add(message)

            }
            val adapter: Adapter

            adapter = messageprivateAdapater(this@chat_pri, listMessages)

            lista.adapter = adapter
            lista.setSelection(lista.getAdapter().getCount() - 1);
        }*/


    /*val btnSend = findViewById<Button>(R.id.btnSendMessage)
        btnSend.setOnClickListener {

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        Toast.makeText(this@chat_pri,"Si existe registro",Toast.LENGTH_LONG).show()
                        send(value, "12345678")
                        val adapter = envioAdapter(this@chat_pri, listMessages)
                        lista.adapter = adapter
                        Toast.makeText(this@chat_pri, "Message send", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this@chat_pri,"No creado",Toast.LENGTH_LONG).show()
                        val text = findViewById<TextView>(R.id.txtMessage)
                        val m = text.text.toString()
                        database.child("data").child("message").child("conver").child(value).child("12345678").child("0").child("message").setValue(m)
                        database.child("data").child("message").child("conver").child(value).child("12345678").child("0").child("user").setValue(value)
                        database.child("data").child("message").child("conver").child(value).child("12345678").child("0").child("hour").setValue(dateInString)
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@chat_pri,databaseError.message,Toast.LENGTH_LONG).show()
                }
            }
            readdb.child("message").child("conver").child(value).child("12345678").addListenerForSingleValueEvent(postListener)*/
    /*send(value, "12345678")
            val adapter = envioAdapter(this, listMessages)
            lista.adapter = adapter
            Toast.makeText(this, "Message send", Toast.LENGTH_LONG).show()*/


    /*fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }*/



    /*fun send(user: String, receptor: String) {

        val text = findViewById<TextView>(R.id.txtMessage)
        val dateInString = date.toString("HH:mm")
        val m = text.text.toString()
        readdb.child("message").child("conver").child(user).child(receptor).get()
            .addOnSuccessListener {
                var value = 0
                val gson = Gson()
                val json = gson.toJson(it.value)
                println(json)
                val objets = JSONArray(json)
                for (i in 0 until objets.length()) {
                    value = i + 1
                }
                database.child("data").child("message").child("conver").child(user).child(receptor).child(value.toString()).child("message").setValue(m)
                database.child("data").child("message").child("conver").child(user).child(receptor).child(value.toString()).child("user").setValue(user)
                database.child("data").child("message").child("conver").child(user).child(receptor).child(value.toString()).child("hour").setValue(dateInString)
            }

            text.text = ""
            text.hint = "Enviar Mensaje Nuevo!"

    }






    fun readData(user : String, teacher : String) {
        readdb.child("message").child("conver").child(user).child(teacher).get()
            .addOnSuccessListener {
                val gson = Gson()
                val json = gson.toJson(it.value)
                val jobject = JSONArray(json)
                for (i in 0 until jobject.length()) {
                    val data = jobject.getJSONObject(i)
                    val message_fire = data.getString("message")
                    val user_echo = data.getString("user")
                    val hours = data.getString("hour")
                    val message = messageprivate(message_fire, user_echo, hours)
                    listMessages.add(message)

                }
                val adapter: Adapter

                adapter = messageprivateAdapater(this@chat_pri, listMessages)

                lista.adapter = adapter
                lista.setSelection(lista.getAdapter().getCount() - 1);
            }
    }*/





}