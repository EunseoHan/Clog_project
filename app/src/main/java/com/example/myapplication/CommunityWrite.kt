package com.example.myapplication

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
//import com.example.myapplication.Request.AddClosetRequest
import com.example.myapplication.Request.CommunityWriteRequest
import com.example.myapplication.Request.LoginRequest
import org.json.JSONException
import org.json.JSONObject

class CommunityWrite : AppCompatActivity() {


    lateinit var back: ImageView
    lateinit var commu_title: TextView
    lateinit var commu_write: EditText
    lateinit var pic_plus : ImageView
    lateinit var btnAddCloset:Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)

        back = findViewById(R.id.back)
        commu_title = findViewById(R.id.commu_title)
        commu_write = findViewById(R.id.commu_write)
        pic_plus = findViewById(R.id.pic_plus)
        btnAddCloset = findViewById(R.id.btnAddCloset)


        var intent = getIntent()
        var str_id = intent.getStringExtra("ID")
        println("communitywrite")
        println(str_id)


        back.setOnClickListener {
            val intent = Intent(this@CommunityWrite, MainActivity::class.java)
            intent.putExtra("ID", str_id)
            startActivity(intent)
        }

        commu_write.movementMethod = ScrollingMovementMethod()

        commu_write.post{
            val scrollAmount = commu_write.layout.getLineTop(commu_write.lineCount) - commu_write.height
            if (scrollAmount > 0){
                commu_write.scrollTo(0,scrollAmount)
            }
            else{
                commu_write.scrollTo(0,0)
            }
        }

        pic_plus.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
            println(activityResult)
        }

        btnAddCloset.setOnClickListener {
            val commutitle = commu_title.text.toString()
            val commudetail = commu_write.text.toString()
            println("commutitle&detail")
            println(commutitle)
            println(commudetail)

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
                        if (success) { // 로그인에 성공한 경우
                            println("성공")
                            Toast.makeText(
                                this@CommunityWrite,
                                "글 등록에 성공하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent =
                                Intent(this@CommunityWrite, MainActivity::class.java)
                            //로그인하면서 사용자 정보 넘기기
                            intent.putExtra("ID", str_id)
                            startActivity(intent)
                        } else { // 로그인에 실패한 경우
                            println("실패")
                            Toast.makeText(
                                this@CommunityWrite,
                                "글 등록에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            if (str_id == null) {
                Toast.makeText(
                    this@CommunityWrite,
                    "로그인을 먼저 해주세요",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@CommunityWrite, LoginActivity::class.java)
                startActivity(intent)
            } else {
                if(commutitle==""){
                    Toast.makeText(
                        this@CommunityWrite,
                        "글 제목을 입력 해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if(commudetail==""){
                    Toast.makeText(
                        this@CommunityWrite,
                        "글 내용을 입력 해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                } else{
                    println(commutitle)
                    println(commudetail)

                    val communitywriterequest = CommunityWriteRequest(str_id, commutitle,commudetail, responseListener)
                    val queue = Volley.newRequestQueue(this@CommunityWrite)
                    //queue.add<Any>(loginRequest)
                    queue.add(communitywriterequest)
                }
                

            }
        }
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK && it.data != null){
            val uri = it.data!!.data

            Glide.with(this)
                .load(uri)
                .into(pic_plus)
        }
    }

}