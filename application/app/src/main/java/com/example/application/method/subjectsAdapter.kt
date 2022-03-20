package com.example.application.method

import android.content.Context
import android.media.Image
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.application.R

class subjectsAdapter (private val nContext:Context,private val listaTeacher: List<subjects>): ArrayAdapter<subjects>(nContext,0,listaTeacher) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(nContext).inflate(R.layout.subjects,parent,false)

        val profe = listaTeacher[position]
        val img = layout.findViewById<ImageView>(R.id.imgSubject)
        img.setImageResource(profe.img)
        val texto = layout.findViewById<TextView>(R.id.materia)
        texto.text = profe.title



        return layout

    }
}