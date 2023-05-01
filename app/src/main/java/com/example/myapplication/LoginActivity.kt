package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.databinding.ActivityLoginBinding
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.LoginRequest
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        /*find_phone_button.setOnClickListener {
            val intent = Intent(this, MypageMainActivity::class.java)
            startActivity(intent)
        }*/

//        sign_in.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }

        setContentView(binding.root)

        binding.back.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.findPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, LoginFindPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.signIn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.LoginButton.setOnClickListener(View.OnClickListener { // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
            val userID = binding.id.text.toString()
            val userPASSWORD = binding.password.text.toString()

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
                                val userID = jsonObject.getString("userID")
                                val userPASSWORD = jsonObject.getString("userPASSWORD")
                                Toast.makeText(
                                    this@LoginActivity,
                                    "로그인에 성공하였습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val intent =
                                    Intent(this@LoginActivity, MypageMainActivity::class.java)
                                //로그인하면서 사용자 정보 넘기기
                                intent.putExtra("ID", userID)
                                intent.putExtra("PW", userPASSWORD)
                                startActivity(intent)
                            } else { // 로그인에 실패한 경우
                                println("실패")
                                Toast.makeText(
                                    this@LoginActivity,
                                    "로그인에 실패하였습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return@Listener
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                }

            if (userID == "") {
                Toast.makeText(
                    this@LoginActivity,
                    "아이디를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userPASSWORD == "") {
                Toast.makeText(
                    this@LoginActivity,
                    "비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                println(userID)
                println(userPASSWORD)

                val loginRequest = LoginRequest(userID, userPASSWORD, responseListener)
                val queue = Volley.newRequestQueue(this@LoginActivity)
                //queue.add<Any>(loginRequest)
                queue.add(loginRequest)
            }
        })
    }
}