package com.example.application.optionItem

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.application.Account
import com.example.application.MainActivity
import com.example.application.R
import com.example.application.method.photo
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import java.util.*

class Profile : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var readdb : DatabaseReference = FirebaseDatabase.getInstance().getReference("data")
    private lateinit var toggle : ActionBarDrawerToggle
    private var database: DatabaseReference = Firebase.database.reference
    private var position = 0
    //private var lastname_user = ""
    private  var valorTipo = ""
    //private lateinit var currentPhotoPath: String

    private val SELECT_ACTIVITY = 50
    private var imgURL : Uri? = null
    private val p = photo()


    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        sharedPreferences = this.getSharedPreferences("keyEmail", Context.MODE_PRIVATE)
        val value = sharedPreferences.getString("keyEmail","No vay valor")

        val img = findViewById<ImageView>(R.id.imgProfileCircle)
        val imgUri = p.getImage(this@Profile,SELECT_ACTIVITY.toLong())
        img.setImageURI(imgUri)

        val correoElectronico = findViewById<TextView>(R.id.txtNameProfile)
        //val phoneText = findViewById<TextView>(R.id.txtPhoneProfile)
        val balanceP = findViewById<TextView>(R.id.txtBalanceProfile)

        sharedPreferences = this.getSharedPreferences("typeUser",Context.MODE_PRIVATE)
        val typeUser = sharedPreferences.getString("typeUser","No hay valor")

        valorTipo = typeUser.toString()

        val cimg = findViewById<CircleImageView>(R.id.imgProfileCircle)
        cimg.setOnClickListener {


            p.savePhotoFromGalery(this@Profile,SELECT_ACTIVITY)

        }
        println(value+" TYPE USER")


        readdb.child(typeUser.toString()).get().addOnSuccessListener {
            val gson = Gson()
            val json = gson.toJson(it.value)
            val datajson = JSONArray(json)
            println(datajson)
            for (i in 0 until datajson.length()) {

                val jsonObject = datajson.getJSONObject(i)
                println(jsonObject)
                val correo = jsonObject.getString("phone")
                val name = jsonObject.getString("firstname")
                println(name)

                //val lastname = jsonObject.getString("lastname")
                val balance = jsonObject.getString("balance")

                if(value == correo.toString()){
                    correoElectronico.text = name.toString()
                    //phoneText.text = phone.toString()
                    balanceP.text = balance.toString()
                    //lastname_user = lastname.toString()
                    position = i
                    break
                }
            }
        }




        val drawableLayout : DrawerLayout = findViewById(R.id.nav_profile)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this,drawableLayout,R.string.open,R.string.close)
        drawableLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_menu -> startActivity(Intent(this, Account::class.java))
                R.id.Chat_menu -> startActivity(Intent(this,chat_pri::class.java))
                /*R.id.list_menu -> Toast.makeText(this,"Chat",Toast.LENGTH_LONG).show()*/
                R.id.profile_menu -> startActivity(Intent(this,Profile::class.java))
                R.id.exit -> startActivity(Intent(this, MainActivity::class.java))
            }
            true
        }

        val btnUpdateData = findViewById<Button>(R.id.btnUpdate)
        btnUpdateData.setOnClickListener {
            if(updateData(correoElectronico.text.toString()) ){
                Toast.makeText(this,"Actualizacion exitosa",Toast.LENGTH_LONG).show()
                correoElectronico.text = correoElectronico.text.toString()
            }

        }


    }

    private fun updateData(value:String):Boolean{
        println(valorTipo + "Dato Recibido")
        database.child("data").child(valorTipo).child(position.toString()).child("name").setValue(value)
        //database.child("data").child(valorTipo).child(position.toString()).child("phone").setValue(value_2)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cimg = findViewById<CircleImageView>(R.id.imgProfileCircle)
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imgURL = data!!.data
                cimg.setImageURI(imgURL)
                imgURL?.let {
                    p.saveGallery(this@Profile,SELECT_ACTIVITY.toLong(),it)
                println("Guarde Img")
                }
            }
        }
    }

    /*private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)


        }
    }*/


}