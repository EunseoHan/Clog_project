package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CommunityActivity : AppCompatActivity() {

    lateinit var go_to_community_write : TextView

    lateinit var ex_community1 : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        go_to_community_write = findViewById(R.id.go_to_community_write)
//        ex_community1 = findViewById(R.id.ex_community1)

        go_to_community_write.setOnClickListener {
            val intent = Intent(this, CommunityWrite::class.java)
            startActivity(intent)
        }
//        ex_community1.setOnClickListener {
//            val intent = Intent(this, CommunityDetailActivity::class.java)
//            startActivity(intent)
//        }


    }
}