package com.example.myapplication

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.myapplication.Request.CommunityEditRequest
import com.example.myapplication.Request.DeleteCommuRequest
import com.example.myapplication.Request.MypageOutRequest
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import kotlin.properties.Delegates

class MypageCommunityDetailActivity : AppCompatActivity() {

    lateinit var commuimage: ImageView
    lateinit var commutext: TextView
    lateinit var commutitle: TextView
    lateinit var commuid: TextView
    lateinit var data1:String
    lateinit var data2: String
    lateinit var data3: ByteArray
    lateinit var data4: Bitmap
    lateinit var data5: String
    lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_detail)

        val back = findViewById<ImageView>(R.id.back)
        val editbutton = findViewById<Button>(R.id.editbutton)
        val deletebutton = findViewById<Button>(R.id.deletebutton)

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
        path = intent.getStringExtra("path").toString()

        println(number.toInt())
        // datas = intent.getSerializableExtra("data") as ListItemCommunity
        Glide.with(this).load(data4).into(commuimage)
        commuid.text = data2
        commutitle.text = data1
        commutext.text = data5

        back.setOnClickListener {
            val intent =
                Intent(this@MypageCommunityDetailActivity, MainActivity::class.java)
            //로그인하면서 사용자 정보 넘기기
            intent.putExtra("ID", data2)
            startActivity(intent)
        }

        editbutton.setOnClickListener {
            Toast.makeText(
                this@MypageCommunityDetailActivity,
                "제목, 내용만 수정 가능합니다.",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this@MypageCommunityDetailActivity, EditCommunityActivity::class.java)
            intent.putExtra("ID", data2)
            intent.putExtra("title", data1)
            intent.putExtra("detail", data5)
            intent.putExtra("number", number)
            intent.putExtra("image", data3)
            startActivity(intent)
        }

        deletebutton.setOnClickListener {
            //삭제 php파일 response 넣기
            val builder = AlertDialog.Builder(this)
            builder.setMessage("정말로 삭제하시겠습니까??")
                .setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, which ->

                    val responseListener =
                        Response.Listener<String> { response ->
                            println("여기는 타?")

                            try {
                                // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                println("hongchul$response")
                                val jsonObject = JSONObject(response)
                                println("여기는 왔니")
                                println(response)
                                val success = jsonObject.getBoolean("success")
                                if (success) { // 삭제에 성공한 경우
                                    println("성공")
                                    Toast.makeText(
                                        this@MypageCommunityDetailActivity,
                                        "삭제되었습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(this@MypageCommunityDetailActivity, MypageCommunityActivity::class.java)
                                    startActivity(intent)
                                } else { // 삭제에 실패한 경우
                                    println("삭제되지 않았습니다. 다시 시도해주세요.")
                                    val intent =
                                        Intent(this@MypageCommunityDetailActivity, MypageCommunityActivity::class.java)
                                    startActivity(intent)
                                    return@Listener
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }


                    val deletecommurequest = DeleteCommuRequest(number.toInt(), data2, path, responseListener)
                    val queue = Volley.newRequestQueue(this@MypageCommunityDetailActivity)
                    queue.add(deletecommurequest)

//                    val intent = Intent(this, MypageOutActivity::class.java)
//                    intent.putExtra("ID", str_id)
//                    intent.putExtra("PW", str_pw)
//                    startActivity(intent)
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener{ dialog, which ->
                    Toast.makeText(
                        this@MypageCommunityDetailActivity,
                        "취소되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            builder.show()

        }


    }

    fun byteArrayToBitmap(bytearray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
    }

}