package com.example.agraapp

import Animal
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var animalList: ArrayList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        listView = findViewById(R.id.listView)

        // Cargar la lista de animales desde la base de datos
        cargarListaAnimales()

        if (animalList.isNotEmpty()) {
            val adapter = AnimalAdapter(this, animalList)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedAnimal = animalList[position]
                if (selectedAnimal.id <= 0) {
                    Log.e("ListActivity", "El ID del animal es inválido: ${selectedAnimal.id}")
                    Toast.makeText(
                        this,
                        "ID inválido, no se puede abrir el detalle",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val detailIntent = Intent(this, DetailActivity::class.java).apply {
                        putExtra("animalId", selectedAnimal.id)  // Pasar solo el ID
                    }
                    startActivity(detailIntent)
                }
            }
        } else {
            Toast.makeText(this, "No hay animales disponibles", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarListaAnimales() {
        val dbHelper = SQLite(this)
        try {
            animalList = dbHelper.getAllAnimals()
        } catch (e: Exception) {
            Log.e("ListActivity", "Error al cargar la lista de animales: ${e.message}")
            animalList = arrayListOf()  // se inicializa una lista vacía en caso de error
            Toast.makeText(this, "Error al cargar los animales desde la base de datos", Toast.LENGTH_SHORT).show()
        }

        if (animalList.isEmpty()) {
            Log.d("ListActivity", "No se encontraron animales en la base de datos.")
        } else {
            for (animal in animalList) {
                Log.d("ListActivity", "Animal cargado: ID=${animal.id}, Nombre=${animal.nombre}")
            }
        }
    }

    // Botón volver
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

