package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.MypageOutRequest
import org.json.JSONException
import org.json.JSONObject

class MypageMainActivity : AppCompatActivity() {

    lateinit var Editprofile: Button
    lateinit var writelist: Button
    lateinit var mypagelogout: Button
    lateinit var mypageout: Button
    lateinit var mypagealarm : Button

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
        mypagealarm = findViewById(R.id.mypagealarm)

        back = findViewById(R.id.back)

        Editprofile.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            intent.putExtra("ID",str_id)
            startActivity(intent)
        }
        writelist.setOnClickListener {
            val intent = Intent(this, MypageCommunityActivity::class.java)
            intent.putExtra("ID", str_id)
            startActivity(intent)
        }

        mypagealarm.setOnClickListener {
            val intent = Intent(this, ButtonAlarm::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ID", str_id)
            intent.putExtra("PW", str_pw)
            startActivity(intent)
        }

        mypageout.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("정말로 탈퇴하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener{dialog, which ->

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
                                if (success) { // 탈퇴에 성공한 경우
                                    println("성공")
                                    Toast.makeText(
                                        this@MypageMainActivity,
                                        "탈퇴되었습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(this@MypageMainActivity, MainActivity::class.java)
                                    getIntent().removeExtra("ID");
                                    getIntent().removeExtra("PW");
                                    var str_id1 = intent.getStringExtra("ID")
                                    var str_pw1 = intent.getStringExtra("PW")
                                    intent.putExtra("ID",str_id1)
                                    intent.putExtra("PW",str_pw1)
                                    println("탈퇴성공")
                                    println(str_id1)
                                    println(str_pw1)
                                    startActivity(intent)
                                } else { // 탈퇴에 실패한 경우
                                    println("실패")
                                    val intent =
                                        Intent(this@MypageMainActivity, MypageMainActivity::class.java)
                                    startActivity(intent)
                                    return@Listener
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                    val mypageoutrequest = str_id?.let { str_pw?.let { it1 ->
                        MypageOutRequest(it,
                            it1, responseListener)
                    } }
                    val queue = Volley.newRequestQueue(this@MypageMainActivity)
                    //queue.add<Any>(loginRequest)
                    queue.add(mypageoutrequest)
                
//                    val intent = Intent(this, MypageOutActivity::class.java)
//                    intent.putExtra("ID", str_id)
//                    intent.putExtra("PW", str_pw)
//                    startActivity(intent)
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener{ dialog, which ->
                    Toast.makeText(
                        this@MypageMainActivity,
                        "취소되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            builder.show()
        }

        mypagelogout.setOnClickListener {
            Toast.makeText(
                this@MypageMainActivity,
                "로그아웃되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            val intent =
                Intent(this@MypageMainActivity, MainActivity::class.java)
            getIntent().removeExtra("ID");
            getIntent().removeExtra("PW");
            var str_id2 = intent.getStringExtra("ID")
            var str_pw2 = intent.getStringExtra("PW")
            intent.putExtra("ID",str_id2)
            intent.putExtra("PW",str_pw2)
            println("로그아웃성공")
            println(str_id2)
            println(str_pw2)
            startActivity(intent)
        }


    }
}