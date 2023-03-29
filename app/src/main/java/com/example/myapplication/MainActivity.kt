package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    lateinit var home : TextView
    lateinit var weather : TextView
    lateinit var closet : TextView
    lateinit var community : TextView
    lateinit var MyPage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home = findViewById(R.id.home)
        weather = findViewById(R.id.weather)
        closet = findViewById(R.id.closest)
        community = findViewById(R.id.community)
        MyPage = findViewById(R.id.myPage)


        closet.setOnClickListener {
            val intent = Intent(this, AddCloset::class.java)
            startActivity(intent)
        }



    }


}