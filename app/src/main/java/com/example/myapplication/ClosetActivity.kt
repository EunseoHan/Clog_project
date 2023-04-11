package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClosetActivity : AppCompatActivity() {

    lateinit var add_cloth : TextView
    lateinit var ex_outer : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closet)

        add_cloth = findViewById(R.id.add_cloth)
        ex_outer = findViewById(R.id.ex_outer)

        add_cloth.setOnClickListener {
            val intent = Intent(this, AddCloset::class.java)
            startActivity(intent)
        }
        ex_outer.setOnClickListener {
            val intent = Intent(this, EditCloset::class.java)
            startActivity(intent)
        }
    }
}