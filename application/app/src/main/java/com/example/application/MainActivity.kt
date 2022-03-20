package com.example.application


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.application.optionItem.listStudent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONArray
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.core.app.NotificationManagerCompat as NotificationManagerCompat1

class MainActivity : AppCompatActivity() {


    private var readdb :DatabaseReference = FirebaseDatabase.getInstance().getReference("data")
    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notifyID = 0


    lateinit var sharedPreferences: SharedPreferences
    lateinit var currentPhotoPath: String

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Completable.timer(5, TimeUnit.SECONDS,AndroidSchedulers.mainThread()).subscribe {}
        /*createNotify()
        val notify = NotificationCompat.Builder(this,channelID).also {
            it.setContentTitle("Saludo")
            it.setContentText("Hola mariquito como estas?")
            it.setSmallIcon(R.mipmap.ic_launcher_round)
            it.setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()


        val notifyM : NotificationManagerCompat1 = NotificationManagerCompat1.from(this)
        notifyM.notify(notifyID,notify)*/


        val login = findViewById<Button>(R.id.btnLogin)
        val register = findViewById<Button>(R.id.btnRegister)

        val email = findViewById<TextView>(R.id.txtEmaillogin)
        val pass = findViewById<TextView>(R.id.txtPassLogin)


        register.setOnClickListener {
            startActivity(
                Intent(this, Account::class.java)
                //Intent(this, califique  ::class.java)
            )
        }

        login.setOnClickListener {
            val listIndex : MutableList<String> = mutableListOf("teacher","student")
            for(i in 0 until listIndex.size){
                scanner(listIndex[i],email.text.toString(),pass.text.toString())
            }
            email.text = ""
            pass.text = ""
            email.isFocusable
        }

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


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun scanner(index:String,email:String,pass:String){
            readdb.child(index).get().addOnSuccessListener {
                val gson = Gson()
                val json = gson.toJson(it.value)
                val datajson = JSONArray(json)

                for(i in 0 until datajson.length()) {

                    val jsonObject = datajson.getJSONObject(i)
                    println(jsonObject.toString())

                    val correo = jsonObject.getString("email")
                    val password = jsonObject.getString("password")
                    val typeUsers = jsonObject.getString("type")
                    val phone = jsonObject.getString("phone")
                    val status = jsonObject.getString("status")

                    if(correo.toString() == email && password.toString() == pass && status.toString() == "active"){

                        sharedPreferences = this.getSharedPreferences("keyEmail", Context.MODE_PRIVATE)
                        val edit = sharedPreferences.edit()
                        edit.putString("keyEmail",phone.toString())
                        edit.apply()
                        println(typeUsers.toString()+" Valor Type")
                        sharedPreferences = this.getSharedPreferences("typeUser", Context.MODE_PRIVATE)
                        val typeUser = sharedPreferences.edit()
                        typeUser.putString("typeUser", typeUsers.toString())
                        typeUser.apply()

                        if(index == "teacher"){

                            startActivity(
                             Intent(this, listStudent::class.java)
                            )
                        }
                        else{

                            startActivity(
                                Intent(this, social::class.java)
                            )
                        }
                    }
                    else if(status.toString() == "block"){
                        Toast.makeText(this,"Usuario Bloqueado",Toast.LENGTH_LONG).show()
                        break
                    }

                }
            }
    }


}