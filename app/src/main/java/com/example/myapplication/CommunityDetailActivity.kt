package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlin.properties.Delegates

class CommunityDetailActivity : AppCompatActivity() {

//    lateinit var titledata : ListItemCommunity
//    lateinit var imagedata : ListItemCommunity
//    lateinit var textdata : ListItemCommunity

    lateinit var commuimage: ImageView
    lateinit var commutext: TextView
    lateinit var commutitle: TextView
    lateinit var commuid: TextView
    lateinit var datas:ListItemCommunity
    lateinit var data1:String
    lateinit var data2: String
    lateinit var data3: ByteArray
    lateinit var data4: Bitmap
    lateinit var data5: String
    var number by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_detail)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            val intent =
                Intent(this@CommunityDetailActivity, MainActivity::class.java)
            //로그인하면서 사용자 정보 넘기기
            intent.putExtra("ID", data2)
            startActivity(intent)
        }


        commuimage = findViewById(R.id.commuimage)
        commutext = findViewById(R.id.commutext)
        commutitle = findViewById(R.id.commutitle)
        commuid = findViewById(R.id.commuid)

        data1 = intent.getStringExtra("title").toString()
        //data2 = id값
        data2 = intent.getStringExtra("context").toString()
        data3 = intent.getByteArrayExtra("image")!!
        data4 = byteArrayToBitmap(data3)
        data5 = intent.getStringExtra("detail").toString()
        var number = intent.getStringExtra("number").toString()
        println(number.toInt())
        // datas = intent.getSerializableExtra("data") as ListItemCommunity
        Glide.with(this).load(data4).into(commuimage)
        commutitle.text = data1
        commutext.text = data5
        commuid.text = data2

    }
    fun byteArrayToBitmap(bytearray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
    }
}