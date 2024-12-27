package com.example.todolist

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room

class Images : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var btnAdd: Button
    private lateinit var database: AppDatabase // Room Database
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        // Инициализация компонентов
        editText = findViewById(R.id.editText)
        btnAdd = findViewById(R.id.btnAdd)

        // Инициализация Room Database
        database = Room.databaseBuilder(this, AppDatabase::class.java, "app_database").allowMainThreadQueries().build()

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("smart_watch_prefs", Context.MODE_PRIVATE)

        btnAdd.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isNotEmpty()) {
                // Сохранение в SharedPreferences
                saveToSharedPreferences("ITEM_TEXT", text)

                // Сохранение в базу данных (Room)
                val item = Item(name = text)
                database.itemDao().insert(item)

                // Переход на экран списка
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun saveToSharedPreferences(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}