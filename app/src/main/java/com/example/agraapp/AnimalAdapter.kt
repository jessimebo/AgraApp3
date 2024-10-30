package com.example.agraapp

import Animal
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AnimalAdapter(private val context: Context, private val animalList: List<Animal>) : BaseAdapter() {

    override fun getCount(): Int {
        return animalList.size
    }

    override fun getItem(position: Int): Any {
        return animalList[position]
    }

    override fun getItemId(position: Int): Long {
        return animalList[position].id.toLong()
    }

    // función getView con el Patrón ViewHolder
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val rowView: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            rowView = inflater.inflate(R.layout.list_item, parent, false)

            viewHolder = ViewHolder()
            viewHolder.textViewAnimal = rowView.findViewById(R.id.textViewAnimal)

            // almacenamos el ViewHolder en el tag de la vista
            rowView.tag = viewHolder
        } else {
            // se reútiliza la vista existente y recuperamos el ViewHolder
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        // obtenemos el animal actual
        val animal = animalList[position]

        viewHolder.textViewAnimal.text = formatAnimalInfo(animal)

        return rowView
    }

    private class ViewHolder {
        lateinit var textViewAnimal: TextView
    }

    // función para formatear la información del animal
    private fun formatAnimalInfo(animal: Animal): String {
        return "Nombre: ${animal.nombre}, Edad: ${animal.edad}, Especie: ${animal.especie}"
    }
}
