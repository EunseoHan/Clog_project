package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClosetActivity : AppCompatActivity() {

    lateinit var add_cloth : TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closet)

        add_cloth = findViewById(R.id.add_cloth)

        add_cloth.setOnClickListener {
            val intent = Intent(this, AddCloset::class.java)
            startActivity(intent)
        }
    }
}