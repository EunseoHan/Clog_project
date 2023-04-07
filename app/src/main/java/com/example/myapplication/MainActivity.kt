package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    lateinit var home : TextView
    lateinit var weather : TextView
    lateinit var closet : TextView
    lateinit var community : TextView
    lateinit var home_to_mypage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home = findViewById(R.id.home)
        weather = findViewById(R.id.weather)
        closet = findViewById(R.id.closest)
        community = findViewById(R.id.community)
        home_to_mypage = findViewById(R.id.home_to_mypage)


        closet.setOnClickListener {
            val intent = Intent(this, ClosetActivity::class.java)
            startActivity(intent)
        }

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        weather.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

//        community.setOnClickListener {
//            val intent = Intent(this, ::class.java)
//            startActivity(intent)
//        }

        home_to_mypage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



    }


}