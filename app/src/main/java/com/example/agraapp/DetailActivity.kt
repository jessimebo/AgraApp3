package com.example.agraapp

import Animal
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLite
    private lateinit var formHandler: FormHandler

    private lateinit var editTextNombre: EditText
    private lateinit var editTextRaza: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextEspecie: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextDesparasitacion: EditText
    private lateinit var editTextVacunas: EditText

    private lateinit var btnBorrar: Button
    private lateinit var btnGuardar: Button

    private var animalId: Int = -1
    private lateinit var selectedAnimal: Animal  // Objeto Animal obtenido de la base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        dbHelper = SQLite(this)
        formHandler = FormHandler(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()

        // Obtener el ID del animal desde el Intent
        animalId = intent.getIntExtra("animalId", -1)
        if (animalId <= 0) {
            Log.e("DetailActivity", "No se recibió un ID válido")
            Toast.makeText(this, "Error al cargar los datos del animal", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Obtener el Animal desde la base de datos
        try {
            selectedAnimal = dbHelper.getAnimalById(animalId) ?: run {
                Toast.makeText(this, "Animal no encontrado en la base de datos", Toast.LENGTH_SHORT)
                    .show()
                finish()
                return
            }
        } catch (e: Exception) {
            Log.e("DetailActivity", "Error al obtener el animal: ${e.message}")
            Toast.makeText(this, "Error al cargar los datos del animal", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        editTextNombre.setText(selectedAnimal.nombre)
        editTextRaza.setText(selectedAnimal.raza)
        editTextEdad.setText(selectedAnimal.edad.toString())
        editTextEspecie.setText(selectedAnimal.especie)
        editTextGenero.setText(selectedAnimal.genero)
        editTextDesparasitacion.setText(selectedAnimal.desparasitacion)
        editTextVacunas.setText(selectedAnimal.vacunas)

        btnGuardar.setOnClickListener {
            actualizarAnimal()
        }

        btnBorrar.setOnClickListener {
            eliminarAnimal()
        }
    }

    private fun initViews() {
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextRaza = findViewById(R.id.editTextRaza)
        editTextEdad = findViewById(R.id.editTextEdad)
        editTextEspecie = findViewById(R.id.editTextEspecie)
        editTextGenero = findViewById(R.id.editTextGenero)
        editTextDesparasitacion = findViewById(R.id.editTextDesparasitacion)
        editTextVacunas = findViewById(R.id.editTextVacunas)
        btnBorrar = findViewById(R.id.btnBorrar)
        btnGuardar = findViewById(R.id.btnGuardar)
    }

    // Función para actualizar el Animal
    private fun actualizarAnimal() {
        val nombre = editTextNombre.text.toString()
        val raza = editTextRaza.text.toString()
        val edad = editTextEdad.text.toString().toIntOrNull() ?: 0
        val especie = editTextEspecie.text.toString()
        val genero = editTextGenero.text.toString()
        val desparasitacion = editTextDesparasitacion.text.toString()
        val vacunas = editTextVacunas.text.toString()

        val updatedAnimal = selectedAnimal.copy(
            nombre = nombre,
            raza = raza,
            edad = edad,
            especie = especie,
            genero = genero,
            desparasitacion = desparasitacion,
            vacunas = vacunas
        )

        try {
            val result = formHandler.actualizar(updatedAnimal)
            if (result) {
                Toast.makeText(this, "Animal actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar el animal", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("DetailActivity", "Error al actualizar el animal: ${e.message}")
            Toast.makeText(this, "Error inesperado al actualizar el animal", Toast.LENGTH_SHORT)
                .show()
        }
    }

        // Función para eliminar el Animal
        private fun eliminarAnimal() {
            try {
                val result = formHandler.eliminar(animalId)
                if (result) {
                    Toast.makeText(this, "Animal eliminado correctamente", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al eliminar el animal", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("DetailActivity", "Error al eliminar el animal: ${e.message}")
                Toast.makeText(this, "Error inesperado al eliminar el animal", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // botón de volver
        override fun onSupportNavigateUp(): Boolean {
            finish()
            return true
        }
    }

