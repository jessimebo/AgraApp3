package com.example.agraapp

import Animal
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imageView: ImageView
    private lateinit var buttonLoadImage: Button

    private lateinit var nameInput: TextInputEditText
    private lateinit var razaInput: TextInputEditText
    private lateinit var edadInput: TextInputEditText
    private lateinit var especieInput: TextInputEditText

    private lateinit var radioGroupGenero: RadioGroup
    private lateinit var radioButtonHembra: RadioButton
    private lateinit var radioButtonMacho: RadioButton

    private lateinit var desparasitacionInput: TextInputEditText
    private lateinit var vacunasInput: TextInputEditText

    private lateinit var guardarButton: Button
    private lateinit var listarButton: Button
    private lateinit var diarioButton: Button

    private lateinit var formHandler: FormHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initViews()

        setSupportActionBar(toolbar)

        formHandler = FormHandler(this)


        guardarButton.setOnClickListener {
            insertarAnimal()
        }


        listarButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }


        diarioButton.setOnClickListener {
            Toast.makeText(this, "Ir al diario de incidencias", Toast.LENGTH_SHORT).show()
        }
    }


    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        imageView = findViewById(R.id.myImageView)
        buttonLoadImage = findViewById(R.id.buttonLoadImage)

        nameInput = findViewById(R.id.nameInput)
        razaInput = findViewById(R.id.razaInput)
        edadInput = findViewById(R.id.edadInput)
        especieInput = findViewById(R.id.especieInput)

        radioGroupGenero = findViewById(R.id.radioGroupGenero)
        radioButtonHembra = findViewById(R.id.radioButtonHembra)
        radioButtonMacho = findViewById(R.id.radioButtonMacho)

        desparasitacionInput = findViewById(R.id.desparasitacionInput)
        vacunasInput = findViewById(R.id.vacunasInput)

        guardarButton = findViewById(R.id.guardarButton)
        listarButton = findViewById(R.id.listarButton)
        diarioButton = findViewById(R.id.diarioButton)
    }

    // función para insertar un nuevo animal en la base de datos
    private fun insertarAnimal() {
        val nombre = nameInput.text.toString()
        val raza = razaInput.text.toString()
        val edadStr = edadInput.text.toString()
        val especie = especieInput.text.toString()
        val generoId = radioGroupGenero.checkedRadioButtonId
        val genero = when (generoId) {
            R.id.radioButtonHembra -> "Hembra"
            R.id.radioButtonMacho -> "Macho"
            else -> ""
        }
        val desparasitacion = desparasitacionInput.text.toString()
        val vacunas = vacunasInput.text.toString()
        val edad = edadStr.toIntOrNull()

        if (nombre.isBlank() || raza.isBlank() || edad == null || especie.isBlank() || genero.isBlank()) {
            Toast.makeText(this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val animal = Animal(
            id = 0,
            nombre = nombre,
            raza = raza,
            edad = edad,
            especie = especie,
            genero = genero,
            desparasitacion = desparasitacion,
            vacunas = vacunas
        )

        // Insertar el animal en la base de datos
        try {
            val animalConId = formHandler.insertar(animal)
            if (animalConId != null) {
                Toast.makeText(this, "Animal guardado con ID: ${animalConId.id}", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al guardar el animal", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al insertar el animal: ${e.message}")
            Toast.makeText(this, "Ocurrió un error inesperado al guardar el animal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        nameInput.text?.clear()
        razaInput.text?.clear()
        edadInput.text?.clear()
        especieInput.text?.clear()
        radioGroupGenero.clearCheck()
        desparasitacionInput.text?.clear()
        vacunasInput.text?.clear()
    }
}
