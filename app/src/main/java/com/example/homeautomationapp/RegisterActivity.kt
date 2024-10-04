package com.example.homeautomationapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        val dbHelper = UserDatabaseHelper(this)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                val result = dbHelper.addUser(name, username, password)
                if (result) {
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()  // Cierra la actividad de registro
                } else {
                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}