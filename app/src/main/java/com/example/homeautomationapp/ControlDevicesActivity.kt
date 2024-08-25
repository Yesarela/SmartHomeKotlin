package com.example.homeautomationapp.com.example.homeautomationapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.homeautomationapp.R

class ControlDevicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_devices)

        val listView = findViewById<ListView>(R.id.listViewControlOptions)
        val controlOptions = intent.getStringArrayExtra("controlOptions")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, controlOptions ?: emptyArray())
        listView.adapter = adapter
    }
}
