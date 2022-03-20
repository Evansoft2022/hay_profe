package com.example.application.method

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.application.R

class messageprivateAdapater (private val nContext:Context,private val listaTeacher: List<messageprivate>): ArrayAdapter<messageprivate>(nContext,0,listaTeacher) {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val messageprivatechat = listaTeacher[position]

        if( messageprivatechat.verify){
            val layout = LayoutInflater.from(nContext).inflate(R.layout.conversationprivate,parent,false)
            val message = layout.findViewById<TextView>(R.id.messageconversation)
            val hour = layout.findViewById<TextView>(R.id.timeConversation)
            hour.text = messageprivatechat.hour
            message.text = messageprivatechat.message
            return layout
        }
        else{
            val layout = LayoutInflater.from(nContext).inflate(R.layout.globo_receptor,parent,false)
            val message = layout.findViewById<TextView>(R.id.globoReceptor)
            val hour = layout.findViewById<TextView>(R.id.timeGlobo)
            hour.text = messageprivatechat.hour
            message.text = messageprivatechat.message
            return layout
        }






    }
}