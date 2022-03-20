package com.example.application.method

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.application.R

class teacherAdapter(private val nContext:Context,private val listaTeacher: List<teacher>): ArrayAdapter<teacher>(nContext,0,listaTeacher) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(nContext).inflate(R.layout.item_teacher,parent,false)

        val profe = listaTeacher[position]
        val name = layout.findViewById<TextView>(R.id.Nombre)
        val data = layout.findViewById<TextView>(R.id.txtData)
        val img = layout.findViewById<ImageView>(R.id.imagenTeacher)
        val imgCali = layout.findViewById<ImageView>(R.id.starCalifica)


        if(profe.calificado){
            imgCali.setImageResource(R.drawable.ic_star)
        }
        else{
            imgCali.setImageResource(R.drawable.ic_star_gray)
        }

        name.text = profe.names
        data.text = profe.subject
        img.setImageResource(profe.img)

        return layout

    }

}