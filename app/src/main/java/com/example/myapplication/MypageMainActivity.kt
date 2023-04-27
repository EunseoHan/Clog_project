package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MypageMainActivity : AppCompatActivity() {

    lateinit var Editprofile: Button
    lateinit var writelist: Button
    lateinit var mypagelogout: Button
    lateinit var mypageout: Button
    lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_main)

        var intent = getIntent()
        var str_id = intent.getStringExtra("ID")
        var str_pw = intent.getStringExtra("PW")
        println("mypagemainactivity")
        println(str_id)
        println(str_pw)

        Editprofile = findViewById(R.id.Editprofile)
        writelist = findViewById(R.id.writelist)
        mypagelogout = findViewById(R.id.mypagelogout)
        mypageout = findViewById(R.id.mypageout)
        back = findViewById(R.id.back)
        val id = intent.extras!!.getString("userID")

        Editprofile.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            intent.putExtra("ID",str_id)
            startActivity(intent)
        }
        writelist.setOnClickListener {
            val intent = Intent(this, CommunityList::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ID", str_id)
            intent.putExtra("PW", str_pw)
            startActivity(intent)
        }


    }
}