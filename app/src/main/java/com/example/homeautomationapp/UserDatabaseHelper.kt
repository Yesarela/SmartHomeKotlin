package com.example.homeautomationapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 2 // Aumenta la versión para activar onUpgrade
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_NAME = "name" // Agregar columna name
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_NAME TEXT,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)

        // Insertar usuario por defecto (admin)
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, "admin")
            put(COLUMN_NAME, "Admin User") // Nombre del admin
            put(COLUMN_PASSWORD, "admin") // Contraseña por defecto
        }
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(name: String, username: String, password: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name) // Usar la constante COLUMN_NAME
            put(COLUMN_USERNAME, username) // Usar la constante COLUMN_USERNAME
            put(COLUMN_PASSWORD, password) // Usar la constante COLUMN_PASSWORD
        }

        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result != -1L  // Devuelve true si se insertó correctamente
    }

    data class User(val name: String, val username: String, val password: String)

    fun getUser(username: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?", arrayOf(username, password))

        return if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)) // Usar COLUMN_NAME
            val user = User(name, username, password)
            cursor.close()
            db.close()
            user
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun deleteUser(username: String) {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, "$COLUMN_USERNAME=?", arrayOf(username))
        db.close()
    }
}