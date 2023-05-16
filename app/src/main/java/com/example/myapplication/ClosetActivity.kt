package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.LoginRequest
import com.example.myapplication.databinding.ActivityClosetBinding
import org.json.JSONException
import org.json.JSONObject

class ClosetActivity : AppCompatActivity() {

    val binding by lazy { ActivityClosetBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_closet)

        setContentView(binding.root)

        binding.addCloth.setOnClickListener {
            val intent = Intent(this, AddCloset::class.java)
            startActivity(intent)
        }
        binding.exOuter.setOnClickListener {
            val intent = Intent(this, EditCloset::class.java)
            startActivity(intent)
        }
    }
}