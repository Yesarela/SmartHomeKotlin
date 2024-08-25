package com.example.homeautomationapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.homeautomationapp.com.example.homeautomationapp.ControlDevicesActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val logoutButton = findViewById<Button>(R.id.buttonLogout)
        val historyButton = findViewById<Button>(R.id.buttonHistory)
        val controlDevicesButton = findViewById<Button>(R.id.buttonControlDevices)
        val historyListView = findViewById<ListView>(R.id.listViewHistory)

        // Historial de eventos de muestra
        val historyItems = listOf(
            "15/08/2024: Luces del salón encendidas",
            "16/08/2024: Termostato ajustado a 22°C",
            "17/08/2024: Puerta principal cerrada",
            "18/08/2024: Alarma activada"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historyItems)
        historyListView.adapter = adapter

        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        historyButton.setOnClickListener {
            //
        }

        controlDevicesButton.setOnClickListener {
            val controlOptions = listOf(
                "Encender luces del salón",
                "Apagar termostato",
                "Cerrar puerta principal",
                "Desactivar alarma"
            )

            val intent = Intent(this, ControlDevicesActivity::class.java)
            intent.putExtra("controlOptions", controlOptions.toTypedArray())
            startActivity(intent)
        }
    }
}