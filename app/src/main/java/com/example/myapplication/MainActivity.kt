package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    lateinit var pic_plus : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pic_plus = findViewById<ImageView>(R.id.pic_plus)

        pic_plus.setOnClickListener{
            val intent = Intent(this, AddCloset::class.java)
            startActivity(intent)
        }

    }


}