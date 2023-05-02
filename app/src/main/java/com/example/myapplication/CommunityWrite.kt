package com.example.myapplication

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CommunityWrite : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var commu_write: EditText
    lateinit var pic_plus : ImageView
    lateinit var btnAddCloset:Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write)

        back = findViewById(R.id.back)
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