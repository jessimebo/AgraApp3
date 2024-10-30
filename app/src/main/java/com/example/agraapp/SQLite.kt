package com.example.agraapp

import Animal
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite(
    context: Context,
    name: String = "agradatabase",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 3
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE animal (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                nombre TEXT, 
                raza TEXT, 
                edad INTEGER, 
                especie TEXT, 
                genero TEXT, 
                desparasitacion TEXT, 
                vacunas TEXT
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS animal")
        onCreate(db)
    }

    fun insertAnimal(animal: Animal): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", animal.nombre)
            put("raza", animal.raza)
            put("edad", animal.edad)
            put("especie", animal.especie)
            put("genero", animal.genero)
            put("desparasitacion", animal.desparasitacion)
            put("vacunas", animal.vacunas)
        }
        val idGenerado = db.insert("animal", null, values)
        db.close()
        return idGenerado
    }

    fun updateAnimal(id: Int, animal: Animal): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", animal.nombre)
            put("raza", animal.raza)
            put("edad", animal.edad)
            put("especie", animal.especie)
            put("genero", animal.genero)
            put("desparasitacion", animal.desparasitacion)
            put("vacunas", animal.vacunas)
        }
        val rowsAffected = db.update("animal", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteAnimal(id: Int): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete("animal", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    fun getAnimalById(id: Int): Animal? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM animal WHERE id = ?", arrayOf(id.toString()))
        val animal: Animal? = if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
            val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
            val especie = cursor.getString(cursor.getColumnIndexOrThrow("especie"))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
            val desparasitacion = cursor.getString(cursor.getColumnIndexOrThrow("desparasitacion"))
            val vacunas = cursor.getString(cursor.getColumnIndexOrThrow("vacunas"))
            Animal(id, nombre, raza, edad, especie, genero, desparasitacion, vacunas)
        } else {
            null
        }
        cursor.close()
        db.close()
        return animal
    }

    fun getAllAnimals(): ArrayList<Animal> {
        val animalList = ArrayList<Animal>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM animal", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
                val especie = cursor.getString(cursor.getColumnIndexOrThrow("especie"))
                val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
                val desparasitacion = cursor.getString(cursor.getColumnIndexOrThrow("desparasitacion"))
                val vacunas = cursor.getString(cursor.getColumnIndexOrThrow("vacunas"))

                val animal = Animal(id, nombre, raza, edad, especie, genero, desparasitacion, vacunas)
                animalList.add(animal)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return animalList
    }
}
