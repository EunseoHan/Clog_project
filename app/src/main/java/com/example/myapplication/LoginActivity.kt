package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    lateinit var find_password : TextView
    lateinit var find_phone_button : Button
    lateinit var sign_in : TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        find_password = findViewById(R.id.find_password)
        find_phone_button = findViewById(R.id.find_phone_button)
        sign_in = findViewById(R.id.sign_in)

        find_password.setOnClickListener {
            val intent = Intent(this, LoginFindPasswordActivity::class.java)
            startActivity(intent)
        }

        find_phone_button.setOnClickListener {
            val intent = Intent(this, MypageMainActivity::class.java)
            startActivity(intent)
        }

        sign_in.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}