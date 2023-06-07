package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.LoginFindPasswordRequest
import com.example.myapplication.Request.LoginRequest
import com.example.myapplication.Request.PasswordChangeRequest
import com.example.myapplication.databinding.ActivityLoginFindPasswordBinding
import org.json.JSONException
import org.json.JSONObject

class LoginFindPasswordActivity : AppCompatActivity() {

    val binding by lazy  {ActivityLoginFindPasswordBinding.inflate(layoutInflater)}

    var ID : String =""

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_find_password)

        println("LoginFindPasswordActivity")

        setContentView(binding.root)

        binding.back.setOnClickListener {
            val intent = Intent(this@LoginFindPasswordActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.showButton.setOnClickListener {

            //입력된 값 가져옴
            val userID = binding.findId.text.toString()
            val userNAME = binding.findName.text.toString()
            val userEMAIL = binding.findEmail.text.toString()

            val responseListener: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        println("타니")
                        val jsonObject = JSONObject(response)
                        println(response)
                        val success = jsonObject.getBoolean("success")

                        if (success) {
                            ID = jsonObject.getString("userID") //비밀번호 수정하려고 받아옴
                            Toast.makeText(
                                this@LoginFindPasswordActivity,
                                "회원정보가 일치합니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            binding.newPasswordText.visibility = View.VISIBLE
                            binding.newPassword.visibility = View.VISIBLE
                            binding.passwordCheckText.visibility = View.VISIBLE
                            binding.passwordCheck.visibility = View.VISIBLE
                            binding.findButton.visibility = View.VISIBLE

                            //아이디 이메일 이름 입력 못하게 함.
                            binding.findId.setEnabled(false)
                            binding.findEmail.setEnabled(false)
                            binding.findName.setEnabled(false)

                        } else {
                            Toast.makeText(
                                this@LoginFindPasswordActivity,
                                "회원정보를 다시 확인해주십시오.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        println("JSON 파싱 예외 발생: ${e.message}")
                    }
                }

            if (userID == "") {
                Toast.makeText(
                    this@LoginFindPasswordActivity,
                    "아이디를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (userEMAIL == "") {
                Toast.makeText(
                    this@LoginFindPasswordActivity,
                    "이메일을 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userNAME == "") {
                Toast.makeText(
                    this@LoginFindPasswordActivity,
                    "이름을 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                println(userID)
                println(userNAME)
                println(userEMAIL)

                val loginFindPasswordRequest = LoginFindPasswordRequest(userID, userNAME, userEMAIL, responseListener)
                val queue = Volley.newRequestQueue(this@LoginFindPasswordActivity)
                queue.add(loginFindPasswordRequest)
            }

        }

        binding.findButton.setOnClickListener(View.OnClickListener {

            val nPassword = binding.newPassword.text.toString()
            val nPasswordCheck = binding.passwordCheck.text.toString()

            val responseListener: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        println("타니")
                        val jsonObject = JSONObject(response)
                        println(response)
                        val success = jsonObject.getBoolean("success")

                        if (success) {
                            Toast.makeText(
                                this@LoginFindPasswordActivity,
                                "비밀번호가 변경되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent =
                                Intent(this@LoginFindPasswordActivity, LoginActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this@LoginFindPasswordActivity,
                                "비밀번호 변경에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        println("JSON 파싱 예외 발생: ${e.message}")
                    }
                }

            if(nPassword==""){
                Toast.makeText(
                    this@LoginFindPasswordActivity,
                    "비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(nPasswordCheck==""){
                Toast.makeText(
                    this@LoginFindPasswordActivity,
                    "비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(nPassword==nPasswordCheck){
                val passwordChangeRequest = PasswordChangeRequest(ID, nPassword, responseListener)
                val queue = Volley.newRequestQueue(this@LoginFindPasswordActivity)
                queue.add(passwordChangeRequest)
            }else {
                Toast.makeText(
                    this@LoginFindPasswordActivity,
                    "비밀번호가 일치하지 않습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.register.setOnClickListener {
            val intent = Intent(this@LoginFindPasswordActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.logIn.setOnClickListener {
            val intent = Intent(this@LoginFindPasswordActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}