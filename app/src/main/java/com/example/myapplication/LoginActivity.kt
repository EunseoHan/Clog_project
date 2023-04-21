package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.databinding.ActivityLoginBinding
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    lateinit var find_password: TextView
    lateinit var find_phone_button: Button
    lateinit var sign_in: TextView

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

        setContentView(binding.root)

        find_phone_button.setOnClickListener(View.OnClickListener { // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
            val userID = binding.id.text.toString()
            val userPASSWORD = binding.password.text.toString()

            val responseListener =
                Response.Listener<String> { response ->
                    println("여기는 타?")
                    //fun onResponse(response: String) {
                    try {
                        // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                        println("hongchul$response")
                        val jsonObject = JSONObject(response)
                        println("여기는 왔니")
                        println(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) { // 로그인에 성공한 경우
                            println("성공")
                            //val userNAME = jsonObject.getString("userNAME")
                            val userID = jsonObject.getString("userID")
                            //val userEMAIL = jsonObject.getString("userEMAIL")
                            val userPASSWORD = jsonObject.getString("userPASSWORD")
                            //val userRED = jsonObject.getInt("userRED")
                            //val userBLUE = jsonObject.getInt("userBLUE")
                            //val userBROWN = jsonObject.getInt("userBROWN")
                            //val userPURPLE = jsonObject.getInt("userPURPLE")
                            //val userYELLOW = jsonObject.getInt("userYELLOW")
                            //val userCHARACTER = jsonObject.getInt("userCHARACTER")
                            //Toast.makeText(applicationContext, userNAME+"님 로그인에 성공하였습니다.", Toast.LENGTH_SHORT)
                            Toast.makeText(this@LoginActivity, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            //로그인하면서 사용자 정보 넘기기
                            //intent.putExtra("userNAME", userNAME)
                            intent.putExtra("userID", userID)
                            //intent.putExtra("userEMAIL", userEMAIL)
                            intent.putExtra("userPASSWORD", userPASSWORD)
                            //intent.putExtra("userRED", userRED)
                            //intent.putExtra("userBLUE", userBLUE)
                            //intent.putExtra("userBROWN", userBROWN)
                            //intent.putExtra("userPURPLE", userPURPLE)
                            //intent.putExtra("userYELLOW", userYELLOW)
                            //intent.putExtra("userCHARACTER", userCHARACTER)
                            startActivity(intent)
                        } else { // 로그인에 실패한 경우
                            println("실패")
                            //Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT)
                            Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT)
                                .show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    //}
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

                Toast.makeText(
                    this@LoginActivity,
                    "로그인 되었습니다.",
                    Toast.LENGTH_SHORT
                ).show()

                val loginRequest = LoginRequest(userID, userPASSWORD, responseListener)
                val queue = Volley.newRequestQueue(this@LoginActivity)
                //queue.add<Any>(loginRequest)
                queue.add(loginRequest)
            }
        })
    }
}