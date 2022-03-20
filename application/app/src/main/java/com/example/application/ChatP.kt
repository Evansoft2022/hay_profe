package com.example.application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.application.method.messageprivate
import com.example.application.method.messageprivateAdapater
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class ChatP : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var userPhone = ""

    private var database: DatabaseReference = Firebase.database.reference.child("data").child("message").child("conver")
    private var databases: DatabaseReference = Firebase.database.reference.child("data").child("calificate")
    private var readdb: DatabaseReference = FirebaseDatabase.getInstance().getReference("data").child("message").child("conver")
    private var readdbs: DatabaseReference = FirebaseDatabase.getInstance().getReference("data").child("calificate")
    private var totalCalificaciones = 0
    private var listMessages: MutableList<messageprivate> = mutableListOf()
    private var listEnvioMensaje: MutableList<messageprivate> = mutableListOf()
    private var phoneT = ""
    private lateinit var btnCalificacion :Button

    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notifyID = 0

    private var valor = false

    private val date = getCurrentDateTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_p)
        val lista = findViewById<ListView>(R.id.messageconversation)
        val sms = findViewById<TextView>(R.id.txtMessage)
        sms.requestFocus()


        phoneT = intent.getStringExtra("userReceptor").toString()
        val dateInString = date.toString("HH:mm")
        if(dateInString > "15:24"){
            println("Es menor")
        }

        readdbs.addValueEventListener(readCalificaciones())

        sharedPreferences = this.getSharedPreferences("keyEmail", Context.MODE_PRIVATE)
        userPhone = sharedPreferences.getString("keyEmail", "No vay valor").toString()
        var valueIndex = ""
        var notExists = true

        sharedPreferences = this.getSharedPreferences("typeUser", Context.MODE_PRIVATE)
        val typeUserRegister = sharedPreferences.getString("typeUser", "No vay valor").toString()
        if(typeUserRegister == "teacher"){
            val p = findViewById<LinearLayout>(R.id.perfil)
            p.visibility
        }

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
                    adapter = messageprivateAdapater(this@ChatP, listMessages)
                    lista.adapter = adapter
                    lista.setSelection(lista.adapter.count - 1)
                } else {
                    valueIndex = "0"
                    notExists = false
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ChatP, databaseError.message, Toast.LENGTH_LONG).show()
            }
        }
        readdb.child(userPhone).child(phoneT).addValueEventListener(postListener)




        val btn = findViewById<Button>(R.id.btnSend)
        btn.setOnClickListener {

            if(!sms.text.equals("")){
                var n = valueIndex.toInt()
                if(notExists) {
                    n += 1
                }
                writeMessage(phoneT,sms.text.toString(),(n).toString())
                val notify = NotificationCompat.Builder(this,channelID).also {
                    it.setContentTitle("Hay Profe")
                    it.setContentText(sms.text.toString())
                    it.setSmallIcon(R.drawable.logoapp)
                    it.setPriority(NotificationCompat.PRIORITY_HIGH)
                }.build()

                val notifyM : NotificationManagerCompat = NotificationManagerCompat.from(this)
                notifyM.notify(notifyID,notify)
                sms.text=""
                //readdb.child(userPhone).child("12345678").addValueEventListener(search)
            }
            else{
                Toast.makeText(this@ChatP,"No puede enviar un mensaje vacio",Toast.LENGTH_SHORT).show()
            }
        }

        btnCalificacion = findViewById(R.id.btnCalificaciones)
        btnCalificacion.setOnClickListener {

            readdbs.addValueEventListener(searchRegistroCalificate("1468","87654321"))
            if(valor){
                delete()
            }
            else {
                calificacion()
            }
        }

    }

    fun calificacion(){
        databases.child(totalCalificaciones.toString()).child("emisor").setValue("1468")
        databases.child(totalCalificaciones.toString()).child("receptor").setValue("87654321")
    }




    fun createNotify(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =  NotificationChannel(channelID,channelName,importance).apply {
                lightColor = Color.CYAN
                enableLights(true)
            }
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)



        }
    }

    fun writeMessage(userReceptor : String,sms : String, index:String){
        val dateInString = date.toString("HH:mm")
        if (index == "0"){
            database.child(userPhone).child(userReceptor).child("0").child("message").setValue(sms)
            database.child(userPhone).child(userReceptor).child("0").child("userRecptor").setValue(userReceptor)
            database.child(userPhone).child(userReceptor).child("0").child("hour").setValue(dateInString)

            database.child(userReceptor).child(userPhone).child("0").child("message").setValue(sms)
            database.child(userReceptor).child(userPhone).child("0").child("userRecptor").setValue(userReceptor)
            database.child(userReceptor).child(userPhone).child("0").child("hour").setValue(dateInString)
        }
        else {
            database.child(userPhone).child(userReceptor).child(index).child("message")
                .setValue(sms)
            database.child(userPhone).child(userReceptor).child(index).child("userRecptor")
                .setValue(userReceptor)
            database.child(userPhone).child(userReceptor).child(index).child("hour")
                .setValue(dateInString)


            database.child(userReceptor).child(userPhone).child(index).child("message").setValue(sms)
            database.child(userReceptor).child(userPhone).child(index).child("userRecptor").setValue(userReceptor)
            database.child(userReceptor).child(userPhone).child(index).child("hour").setValue(dateInString)
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    fun readCalificaciones():ValueEventListener{
        val dataC = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    readdbs.get().addOnSuccessListener {
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val jobject = JSONArray(json)
                        for (i in 0 until jobject.length()) {
                            totalCalificaciones = i
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        return dataC
    }


    fun delete(){
        databases.child(totalCalificaciones.toString()).removeValue()
    }

    fun searchRegistroCalificate(phoneE:String,phoneR: String):ValueEventListener{
        val data = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readdbs.get().addOnSuccessListener{
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val objects = JSONArray(json)
                        for (i in 0 until objects.length()) {
                            val data = objects.getJSONObject(i)
                            if (phoneR == data.getString("receptor") && phoneE == data.getString("emisor")) {
                                valor = true
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        return data
        //removeValue()

    }

}