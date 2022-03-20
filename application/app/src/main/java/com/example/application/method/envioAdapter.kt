package com.example.application.method

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.application.R

class envioAdapter(private val nContext:Context,private val listaTeacher: List<messageprivate>): ArrayAdapter<messageprivate>(nContext,0,listaTeacher) {



    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(nContext).inflate(R.layout.globo_receptor,parent,false)

        val messageprivatechat = listaTeacher[position]
        val message = layout.findViewById<TextView>(R.id.globoReceptor)
        //val img = layout.findViewById<ImageView>(R.id.imgprofileconversationprivate)

        //img.setImageResource(messageprivatechat.img)
        message.text = messageprivatechat.message

        return layout

    }

}