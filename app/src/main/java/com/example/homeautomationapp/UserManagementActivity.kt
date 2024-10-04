package com.example.homeautomationapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class UserManagementActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var userListAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        dbHelper = SQLiteHelper(this)

        val userListView = findViewById<ListView>(R.id.listViewUsers)
        val usernameEditText = findViewById<EditText>(R.id.editTextNewUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextNewPassword)
        val addUserButton = findViewById<Button>(R.id.buttonAddUser)
        val deleteUserButton = findViewById<Button>(R.id.buttonDeleteUser)

        userListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dbHelper.getAllUsers())
        userListView.adapter = userListAdapter

        addUserButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            dbHelper.addUser(username, password)
            refreshUserList()
        }

        deleteUserButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            dbHelper.deleteUser(username)
            refreshUserList()
        }
    }

    private fun refreshUserList() {
        userListAdapter.clear()
        userListAdapter.addAll(dbHelper.getAllUsers())
        userListAdapter.notifyDataSetChanged()
    }
}
