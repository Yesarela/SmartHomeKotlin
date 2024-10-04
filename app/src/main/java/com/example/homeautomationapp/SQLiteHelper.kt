package com.example.homeautomationapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "home_automation.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_USERS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTable)

        // Insert default admin user
        val values = ContentValues()
        values.put(COLUMN_USERNAME, "admin")
        values.put(COLUMN_PASSWORD, "admin")
        db?.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun getUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        return cursor.moveToFirst().also {
            cursor.close()
        }
    }

    fun addUser(username: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)
        db.insert(TABLE_USERS, null, values)
    }

    fun deleteUser(username: String) {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, "$COLUMN_USERNAME = ?", arrayOf(username))
    }

    fun getAllUsers(): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)
        val users = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }
}