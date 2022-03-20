package com.example.application.optionItem


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.application.ChatP
import com.example.application.R
import com.example.application.method.teacher
import com.example.application.method.teacherAdapter
import com.google.firebase.database.*
import com.google.gson.Gson
import org.json.JSONArray

class listTeacher : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var readdb : DatabaseReference = FirebaseDatabase.getInstance().getReference("data")
    private val teacher_filter : MutableList<String>  = mutableListOf()
    private var phoneT = ""
    private val listaCalificate : MutableList<Boolean> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_teacher)

        val subjects = intent.getSerializableExtra("subject")
        val listTeacher: MutableList<teacher> = mutableListOf()
        val lista = findViewById<ListView>(R.id.list_teacher)

        val searchCalificate = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    readdb.child("calificate").get().addOnSuccessListener {
                        listaCalificate.clear()
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val jobject = JSONArray(json)
                        for (i in 0 until jobject.length()) {
                            val data = jobject.getJSONObject(i)
                            println(data)
                            if (data.getString("receptor") == "87654321") {
                                listaCalificate.add(true)
                                break
                            }
                            else{
                                listaCalificate.add(false)
                                break
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        readdb.child("calificate").addValueEventListener(searchCalificate)


        val search = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    readdb.child("teacher").get().addOnSuccessListener {
                        val gson = Gson()
                        val json = gson.toJson(it.value)
                        val datajson = JSONArray(json)
                        for (i in 0 until datajson.length()) {
                            val jsonObject = datajson.getJSONObject(i)
                            val subject = jsonObject.getString("subject")
                            val name = jsonObject.getString("name")
                            phoneT = jsonObject.getString("phone")
                            if(subject.toString() == subjects){
                                println(listaCalificate.size)
                                teacher_filter.add(jsonObject.toString())
                                if (listaCalificate.size > 0){
                                    val profe = teacher(name.toString(),subject.toString(),R.drawable.splash_screen,true)
                                    listTeacher.add(profe)
                                }
                                else{
                                    val profe = teacher(name.toString(),subject.toString(),R.drawable.splash_screen,false)
                                    listTeacher.add(profe)
                                }

                            }

                        }
                        val adapter = teacherAdapter(this@listTeacher,listTeacher)

                        lista.adapter = adapter

                    }

                }
                else{
                        Toast.makeText(this@listTeacher,"No hay profesor disponible",Toast.LENGTH_SHORT).show()
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@listTeacher,"No hay profesor disponible",Toast.LENGTH_SHORT).show()
            }

        }

        readdb.child("teacher").addValueEventListener(search)







        lista.setOnItemClickListener { adapterView, view, i, l ->




            //var response = false
            readdb.child("student").get().addOnSuccessListener {
                val gson = Gson()
                val json = gson.toJson(it.value)
                val jobject =  JSONArray(json)
                for(j in 0 until jobject.length()){
                    val data = jobject.getJSONObject(j)
                    val user= data.getString("email")
                    if (user == "cardeiguilou@gmail.com"){
                        val value = data.getString("balance").toString()
                        if ( value.replace("$","") != "0.0"){
                            val getPositionSelect = listTeacher[i]
                            //readCalificate(data.getString("phone"))
                            Toast.makeText(this,getPositionSelect.names,Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,ChatP::class.java)
                            intent.putExtra("userReceptor",phoneT)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,"Debe recargar su billetera virutal",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }



        }
    }

    fun readCalificate(phone : String){

        readdb.child("calificate").get().addOnSuccessListener {
            val gson = Gson()
            val json = gson.toJson(it.value)
            val jobject = JSONArray(json)
            for (i in 0 until jobject.length()) {
                val data = jobject.getJSONObject(i)
                println(data)
                if (data.getString("receptor") == phone) {
                    listaCalificate.add(true)
                    break
                }
            }
            println(listaCalificate.toString()+" LISTA DE BOOLEANOS")

        }
    }





    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }


}

