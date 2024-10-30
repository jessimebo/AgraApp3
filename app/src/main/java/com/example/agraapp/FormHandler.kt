package com.example.agraapp

import Animal
import android.content.Context
import android.util.Log
import android.widget.Toast

class FormHandler(private val context: Context) {

    // función para insertar un animal en la bd
    fun insertar(animal: Animal): Animal? {
        val dbHelper = SQLite(context)
        return try {
            val idGenerado = dbHelper.insertAnimal(animal)
            if (idGenerado != -1L) {
                val animalConId = animal.copy(id = idGenerado.toInt())
                Toast.makeText(context, "Animal guardado correctamente. ID generado: $idGenerado", Toast.LENGTH_LONG).show()
                animalConId
            } else {
                Toast.makeText(context, "Error al guardar el animal", Toast.LENGTH_LONG).show()
                null
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Ocurrió un error al guardar el animal", Toast.LENGTH_LONG).show()
            Log.e("FormHandler", "Error al insertar el animal: ${e.message}")
            null
        }
    }

    // función para actualizar un animal en la bd
    fun actualizar(animal: Animal): Boolean {
        val dbHelper = SQLite(context)

        return try {
            val rowsAffected = dbHelper.updateAnimal(animal.id, animal)
            if (rowsAffected > 0) {
                Toast.makeText(context, "Animal actualizado correctamente", Toast.LENGTH_LONG).show()
                true
            } else {
                Toast.makeText(context, "Error al actualizar el animal", Toast.LENGTH_LONG).show()
                false
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Ocurrió un error al actualizar el animal", Toast.LENGTH_LONG).show()
            Log.e("FormHandler", "Error al actualizar el animal: ${e.message}")
            false
        }
    }

    // función para eliminar un animal de la bd
    fun eliminar(id: Int): Boolean {
        val dbHelper = SQLite(context)
        return try {
            val rowsDeleted = dbHelper.deleteAnimal(id)
            if (rowsDeleted > 0) {
                Toast.makeText(context, "Animal eliminado correctamente", Toast.LENGTH_LONG).show()
                true
            } else {
                Toast.makeText(context, "Error al eliminar el animal", Toast.LENGTH_LONG).show()
                false
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Ocurrió un error al eliminar el animal", Toast.LENGTH_LONG).show()
            Log.e("FormHandler", "Error al eliminar el animal: ${e.message}")
            false
        }
    }
}
