package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MypageMainActivity : AppCompatActivity() {

    lateinit var Editprofile :Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_main)

        Editprofile = findViewById(R.id.Editprofile)

        Editprofile.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            startActivity(intent)
        }
    }
}