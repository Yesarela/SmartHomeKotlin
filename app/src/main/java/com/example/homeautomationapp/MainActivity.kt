package com.example.homeautomationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        val dbHelper = UserDatabaseHelper(this)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = dbHelper.getUser(username, password)
            if (user != null) {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("username", user.name)  // Pasa el nombre del usuario
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        // Solo mostrar botón de registrar si es el admin
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "admin") {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Solo el administrador puede registrar usuarios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}