package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.ProfileChangeRequest
import com.example.myapplication.databinding.ActivityProfileChangeBinding
import org.json.JSONException
import org.json.JSONObject


class ProfileChangeActivity : AppCompatActivity() {

    val binding by lazy { ActivityProfileChangeBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var character = 0
        var red = 0
        var blue = 0
        var brown = 0
        var purple = 0
        var yellow = 0
        val id = intent.extras!!.getString("ID")

        binding.buttonChange.setOnClickListener {
            val password = binding.password.text.toString()
            val passwordCheck = binding.passwordCheck.text.toString()
            val phone = binding.phone.text.toString()
            //val id = id
            println(id)
            if(id is String) println("good")

            if (binding.checkBoxRed.isChecked){
                red=1
            }else if(binding.checkBoxBlue.isChecked){
                blue=1
            }else if(binding.checkBoxBrown.isChecked){
                brown=1
            }else if(binding.checkBoxPurple.isChecked){
                purple=1
            }else if(binding.checkBoxYellow.isChecked){
                yellow=1
            }

            if(binding.radioButtonHot.isChecked){
                character=1
            }else if(binding.radioButtonMiddle.isChecked){
                character=2
            }else if(binding.radioButtonCold.isChecked){
                character=3
            }

            val responseListener: Response.Listener<String> = Response.Listener<String> {
                    response->
                try {
                    println("나 타고 있니?")
                    val jsonObject = JSONObject(response)
                    println(response)
                    val success = jsonObject.getBoolean("success")

                    if(success){
                        println("성공 탔니?")
                        Toast.makeText(
                            this@ProfileChangeActivity,
                            "회원 정보가 수정되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@ProfileChangeActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        println("실패 탔니?")
                        Toast.makeText(
                            this@ProfileChangeActivity,
                            "회원 정보 수정에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            if(password==""){
                Toast.makeText(
                    this@ProfileChangeActivity,
                    "비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(passwordCheck==""){
                Toast.makeText(
                    this@ProfileChangeActivity,
                    "비밀번호를 입력하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(password != passwordCheck){
                Toast.makeText(
                    this@ProfileChangeActivity,
                    "비밀번호가 다릅니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(red==0 && blue==0 && brown==0 && purple==0 && yellow==0){
                Toast.makeText(
                    this@ProfileChangeActivity,
                    "선호하는 색을 선택하세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(character==0){
                Toast.makeText(
                    this@ProfileChangeActivity,
                    "체질을 선택하세요",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                val profileChangeRequest =
                    ProfileChangeRequest(id ,password, phone, red, blue, brown, purple, yellow, character, responseListener)
                val queue = Volley.newRequestQueue(this@ProfileChangeActivity)
                queue.add(profileChangeRequest)
//                Toast.makeText(
//                    this@ProfileChangeActivity,
//                    "수정이 완료되었습니다.",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }

        binding.buttonArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
}
