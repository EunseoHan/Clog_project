package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Radio
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.LayoutInflaterFactory
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.databinding.ActivityAddClosetBinding
import com.example.myapplication.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject


class AddCloset : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY =12

    val binding by lazy { ActivityAddClosetBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_closet)

        //ClosetFragment에서 ID값 받아옴
        var idre = intent.getStringExtra("ID")
        //잘 넘어왔는지 print로 확인
        println("AddCloset")
        println(idre)

        var namere = ""
        var typere = 0
        var detailre = 0
        var lengthre = 0
        var thicknessre = 0
        var brushedre = 0
        var weatherre = 0

        setContentView(binding.root)

        clickBtn(binding.outer, binding.typeDetailOuter)
        clickBtn(binding.top, binding.typeDetailTop)
        clickBtn(binding.bottom, binding.typeDetailBottom)
        clickBtn(binding.onepiece, binding.typeDetailOnepiece)
        clickBtn(binding.shoes, binding.typeDetailShoes)

        clickRadio(binding.cardigan, binding.thickness)
        clickRadio(binding.jacket, binding.thickness)
        clickRadio(binding.fieldJacket)
        clickRadio(binding.leatherJacket)
        clickRadio(binding.fleece)
        clickRadio(binding.hoodie, binding.lining)
        clickRadio(binding.coat, binding.thickness)
        clickRadio(binding.paddedCoat)

        clickRadio(binding.nonSleeve)
        clickRadio(binding.shortSleeve)
        clickRadio(binding.shirt)
        clickRadio(binding.mtm, binding.lining)
        clickRadio(binding.hoodieTop, binding.lining)
        clickRadio(binding.sweater, binding.thickness)

        clickRadio(binding.denim, binding.length)
        clickRadio(binding.leggings, binding.length)
        clickRadio(binding.skirt, binding.length)
        clickRadio(binding.slacks, binding.length)
        clickRadio(binding.jogger, binding.length)
        clickRadio(binding.cotton, binding.length)

        clickRadio(binding.nonSleeveOnepiece, binding.length)
        clickRadio(binding.shortSleeveOnepiece, binding.length)
        clickRadio(binding.longSleeveOnepiece, binding.length)

        clickRadio(binding.sneakers)
        clickRadio(binding.sandals)
        clickRadio(binding.slippers)
        clickRadio(binding.boots)
        clickRadio(binding.loafer)
        clickRadio(binding.rubberBoots)

        bottom_length9()

        binding.back.setOnClickListener {
            finish()
        }

        //옷 등록 버튼 눌렀을 때
        binding.btnAddCloset.setOnClickListener{

            //로그인하지 않았을 경우
            if(idre==null){
                Toast.makeText(
                    this@AddCloset,
                    "옷을 등록하기 위해서 로그인이 필요합니다.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@AddCloset, LoginActivity::class.java)
                startActivity(intent)
            }

            //로그인 했을 때
            val responseListener: Response.Listener<String> = Response.Listener<String> { response->
                try {
                    println("나 타니?")
                    println(namere)
                    println(typere)
                    println(detailre)
                    println(lengthre)
                    println(thicknessre)
                    println(brushedre)
                    println(weatherre)
                    println("hongchul$response")
                    val jsonObject = JSONObject(response)
                    println(response)
                    val success = jsonObject.getBoolean("success")

                    if(success){
                        println("hey 성공?")
                        Toast.makeText(
                            this@AddCloset,
                            "옷이 등록되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@AddCloset, ClosetActivity::class.java)
                        intent.putExtra("ID",idre)
                        startActivity(intent)
                    }else{
                        println("hey 실패?")
                        Toast.makeText(
                            this@AddCloset,
                            "회원 등록에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            //가디건(아우터)
            if(binding.cardigan.isChecked){
                typere = 1
                detailre = 11
                if(binding.thin.isChecked){
                    thicknessre=1
                    for(i: Int in 5..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.normal.isChecked){
                    thicknessre=2
                    for(i: Int in 3..4){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.thick.isChecked){
                    thicknessre=3
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,3,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "가디건의 두께를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.jacket.isChecked){ //자켓(아우터)
                typere = 1
                detailre = 12
                if(binding.thin.isChecked){
                    thicknessre=1
                    for(i: Int in 4..5){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.normal.isChecked){
                    thicknessre=2
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,4,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }else if(binding.thick.isChecked){
                    thicknessre=3
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,3,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "자켓의 두께를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.fieldJacket.isChecked){ //야상(아우터)
                typere = 1
                detailre = 13
                for(i: Int in 3..4){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.leatherJacket.isChecked){ //레더자켓(아우터)
                typere = 1
                detailre = 14
                for(i: Int in 3..4){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.fleece.isChecked){ //플리스(아우터)
                typere = 1
                detailre = 15
                for(i: Int in 2..3){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.hoodie.isChecked){ //후드집업(아우터)
                typere = 1
                detailre = 16
                if(binding.liningX.isChecked){
                    brushedre=1
                    for(i: Int in 3..4){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.liningO.isChecked){
                    brushedre=2
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,2,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "후드집업의 기모여부를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.coat.isChecked){ //코트(아우터)
                typere = 1
                detailre = 17
                if(binding.thin.isChecked){
                    thicknessre=1
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,4,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }else if(binding.normal.isChecked){
                    thicknessre=2
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,3,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }else if(binding.thick.isChecked){
                    thicknessre=3
                    for(i: Int in 1..2){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "코트의 두께를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.paddedCoat.isChecked){ //패딩(아우터)
                typere = 1
                detailre = 18
                for(i: Int in 1..2){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.nonSleeve.isChecked){ //민소매 티셔츠(상의)
                typere = 2
                detailre = 21
                val addClosetRequest1 =
                    AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                val queue1 = Volley.newRequestQueue(this@AddCloset)
                queue1.add(addClosetRequest1)
            }else if(binding.shortSleeve.isChecked){ //반소매 티셔츠(상의)
                typere = 2
                detailre = 22
                for(i: Int in 7..8){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.shirt.isChecked){ //블라우스/셔츠 (상의)
                typere = 2
                detailre = 23
                for(i: Int in 3..7){
                    val addClosetRequest1 =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue1 = Volley.newRequestQueue(this@AddCloset)
                    queue1.add(addClosetRequest1)
                }
            }else if(binding.mtm.isChecked){ //맨투맨 (상의)
                typere = 2
                detailre = 24
                if(binding.liningX.isChecked){
                    brushedre=1
                    for(i: Int in 2..6){
                        val addClosetRequest1 =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue1 = Volley.newRequestQueue(this@AddCloset)
                        queue1.add(addClosetRequest1)
                    }
                }else if(binding.liningO.isChecked){
                    brushedre=2
                    for(i: Int in 1..2){
                        val addClosetRequest1 =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue1 = Volley.newRequestQueue(this@AddCloset)
                        queue1.add(addClosetRequest1)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "맨투맨의 기모여부를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.hoodieTop.isChecked){ //후드티 (상의)
                typere = 2
                detailre = 25
                if(binding.liningX.isChecked){
                    brushedre=1
                    for(i: Int in 2..5){
                        val addClosetRequest1 =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue1 = Volley.newRequestQueue(this@AddCloset)
                        queue1.add(addClosetRequest1)
                    }
                }else if(binding.liningO.isChecked){
                    brushedre=2
                    for(i: Int in 1..2){
                        val addClosetRequest1 =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue1 = Volley.newRequestQueue(this@AddCloset)
                        queue1.add(addClosetRequest1)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "후드티의 기모여부를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.sweater.isChecked){ //니트 (상의)
                typere = 2
                detailre = 26
                if(binding.thin.isChecked){
                    thicknessre=1
                    for(i: Int in 5..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.normal.isChecked){
                    thicknessre=2
                    for(i: Int in 2..4){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.thick.isChecked){
                    thicknessre=3
                    for(i: Int in 1..2){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "니트의 두께감을 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.denim.isChecked){ //데님팬츠 (하의)
                typere = 3
                detailre = 31
                if(binding.length3.isChecked){
                    lengthre=3
                    brushedre=1
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length5.isChecked){
                    lengthre=5
                    brushedre=1
                    for(i: Int in 5..7){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length9.isChecked){
                    lengthre=9
                    if(binding.liningX.isChecked){
                        brushedre=1
                        for(i: Int in 2..7){
                            val addClosetRequest =
                                AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                            val queue = Volley.newRequestQueue(this@AddCloset)
                            queue.add(addClosetRequest)
                        }
                    }else if(binding.liningO.isChecked){
                        brushedre=2
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,1,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }else{
                        Toast.makeText(
                            this@AddCloset,
                            "데님팬츠의 기모여부를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "데님팬츠의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.leggings.isChecked){ //레깅스 (하의)
                typere = 3
                detailre = 32
                if(binding.length3.isChecked){
                    lengthre=3
                    brushedre=1
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length5.isChecked){
                    lengthre=5
                    brushedre=1
                    for(i: Int in 6..7){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length9.isChecked){
                    lengthre=9
                    if(binding.liningX.isChecked){
                        brushedre=1
                        for(i: Int in 3..7){
                            val addClosetRequest =
                                AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                            val queue = Volley.newRequestQueue(this@AddCloset)
                            queue.add(addClosetRequest)
                        }
                    }else if(binding.liningO.isChecked){
                        brushedre=2
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,2,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }else{
                        Toast.makeText(
                            this@AddCloset,
                            "레깅스의 기모여부를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "레깅스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.skirt.isChecked){ //스커트 (하의)
                typere = 3
                detailre = 33
                if(binding.length3.isChecked){
                    lengthre=3
                    for(i: Int in 7..8){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length5.isChecked){
                    lengthre=5
                    for(i: Int in 4..7){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length9.isChecked){
                    lengthre=9
                    for(i: Int in 2..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "스커트의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.slacks.isChecked){ //슬랙스 (하의)
                typere = 3
                detailre = 34
                if(binding.length3.isChecked){
                    lengthre=3
                    thicknessre=2
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length5.isChecked){
                    lengthre=5
                    thicknessre=2
                    for(i: Int in 6..7){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length9.isChecked){
                    lengthre=9
                    if(binding.thin.isChecked){
                        thicknessre=1
                        for(i: Int in 5..7){
                            val addClosetRequest =
                                AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                            val queue = Volley.newRequestQueue(this@AddCloset)
                            queue.add(addClosetRequest)
                        }
                    }else if(binding.normal.isChecked){
                        thicknessre=2
                        for(i: Int in 2..6){
                            val addClosetRequest =
                                AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                            val queue = Volley.newRequestQueue(this@AddCloset)
                            queue.add(addClosetRequest)
                        }
                    }else if(binding.thick.isChecked){
                        thicknessre=3
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,1,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }else{
                        Toast.makeText(
                            this@AddCloset,
                            "슬랙스의 두께를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "슬랙스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.jogger.isChecked){ //츄리닝 (하의)
                typere = 3
                detailre = 35
                if(binding.length3.isChecked){
                    lengthre=3
                    brushedre=1
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length5.isChecked){
                    lengthre=5
                    brushedre=1
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,7,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length9.isChecked){
                    lengthre=9
                    if(binding.liningX.isChecked){
                        brushedre=1
                        for(i: Int in 2..7){
                            val addClosetRequest =
                                AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                            val queue = Volley.newRequestQueue(this@AddCloset)
                            queue.add(addClosetRequest)
                        }
                    }else if(binding.liningO.isChecked){
                        brushedre=2
                        for(i: Int in 1..2){
                            val addClosetRequest =
                                AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                            val queue = Volley.newRequestQueue(this@AddCloset)
                            queue.add(addClosetRequest)
                        }
                    }else{
                        Toast.makeText(
                            this@AddCloset,
                            "츄리닝의 기모여부를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "츄리닝의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.cotton.isChecked){ //코튼팬츠 (하의)
                typere = 3
                detailre = 36
                if(binding.length3.isChecked){
                    lengthre=3
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length5.isChecked){
                    lengthre=5
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,7,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length9.isChecked){
                    lengthre=9
                    for(i: Int in 2..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "코튼팬츠의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.nonSleeveOnepiece.isChecked){ //민소매 원피스 (원피스)
                typere = 4
                detailre = 41
                if(binding.length3.isChecked){
                    lengthre=3
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length5.isChecked){
                    lengthre=5
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length9.isChecked){
                    lengthre=9
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,8,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "민소매 원피스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.shortSleeveOnepiece.isChecked){ //반소매 원피스 (원피스)
                typere = 4
                detailre = 42
                if(binding.length3.isChecked){
                    lengthre=3
                    for(i: Int in 7..8){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length5.isChecked){
                    lengthre=5
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,7,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else if(binding.length9.isChecked){
                    lengthre=9
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,7,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "반소매 원피스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.longSleeveOnepiece.isChecked){ //긴소매 원피스 (원피스)
                typere = 4
                detailre = 43
                if(binding.length3.isChecked){
                    lengthre=3
                    for(i: Int in 5..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length5.isChecked){
                    lengthre=5
                    for(i: Int in 4..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else if(binding.length9.isChecked){
                    lengthre=9
                    for(i: Int in 2..6){
                        val addClosetRequest =
                            AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                        val queue = Volley.newRequestQueue(this@AddCloset)
                        queue.add(addClosetRequest)
                    }
                }else{
                    Toast.makeText(
                        this@AddCloset,
                        "긴소매 원피스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(binding.sneakers.isChecked) { //스니커즈 (신발)
                typere = 5
                detailre = 51
                for(i: Int in 1..8){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.sandals.isChecked) { //샌들 (신발)
                typere = 5
                detailre = 52
                for(i: Int in 7..8){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.slippers.isChecked) { //슬리퍼 (신발)
                typere = 5
                detailre = 53
                for(i: Int in 7..8){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.boots.isChecked) { //부츠 (신발)
                typere = 5
                detailre = 54
                for(i: Int in 1..5){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.loafer.isChecked) { //로퍼 (신발)
                typere = 5
                detailre = 55
                for(i: Int in 3..6){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else if(binding.rubberBoots.isChecked) { //장화 (신발)
                typere = 5
                detailre = 56
                for(i: Int in 1..8){
                    val addClosetRequest =
                        AddClosetRequest(idre,namere,typere,detailre,lengthre,thicknessre,brushedre,i,responseListener)
                    val queue = Volley.newRequestQueue(this@AddCloset)
                    queue.add(addClosetRequest)
                }
            }else{
                Toast.makeText(
                    this@AddCloset,
                    "상세 종류를 선택하세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        // 1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
    }


    // 대분류 -> 중분류
    // 버튼(대분류) 눌렀을 때 아래 레이아웃(중분류) 표시
    //(아우터, 상의, 하의, 원피스, 신발 버튼 누를 때 detail 나오게 하는 메소드)
    fun clickBtn(btn:Button, layout: LinearLayout) {
        btn.setOnClickListener {
            if (layout.visibility == VISIBLE) { // 이미 버튼이 눌려있을 때
                backgroundClear()   // 모든 버튼 색 변경
                visibleGone()   // 중분류 레이아웃 모두 없애기
            } else {
                backgroundClear()   // 모든 버튼 색 초기화
                visibleGone()   // 레이아웃 모두 없애기
                radio_clear()   // 라디오 버튼 초기화
                layout.visibility = View.VISIBLE    // 해당 중분류 레이아웃 보이기
//                btn.setBackgroundColor(Color.parseColor("#BFCBE5"))
                btn.setBackgroundResource(R.drawable.button_roud2)  // 버튼색 변경
            }
        }
    }

    // 눌려져 있는 다른 레이아웃(중분류) 없애기
    //(디테일(ex.가디건, 자켓 등)들 안보이게 하는 메소드)
    fun visibleGone() {
        binding.typeDetailOuter.visibility = View.GONE
        binding.typeDetailTop.visibility = View.GONE
        binding.typeDetailBottom.visibility = View.GONE
        binding.typeDetailOnepiece.visibility = View.GONE
        binding.typeDetailShoes.visibility = View.GONE

        visibleGone_radio()
    }

    // 버튼색 초기화
    fun backgroundClear() {
        binding.outer.setBackgroundResource(R.drawable.button_roud)
        binding.top.setBackgroundResource(R.drawable.button_roud)
        binding.bottom.setBackgroundResource(R.drawable.button_roud)
        binding.onepiece.setBackgroundResource(R.drawable.button_roud)
        binding.shoes.setBackgroundResource(R.drawable.button_roud)
    }

    // 중분류 -> 세부(길이, 기모, 두께)
    // 라디오 버튼(중분류) 클릭 시 레이아웃(세부) 표시
    //(각 디테일(라디오버튼) 누를 때마다 그에 맞는 두께감, 기모, 길이 나오게 하는 메소드)
    fun clickRadio(rd:RadioButton, layout:LinearLayout) {
        rd.setOnClickListener {
            radio_clear()
            visibleGone_radio()
            layout.visibility = View.VISIBLE
            rd.isChecked = true
        }
    }


    // 라디오 버튼 클릭 시 레이아웃 표시할 게 없는 경우 (위 함수와 같은 함수. input이 다름)
    //(각 디테일(라디오버튼) 누를 때 그에 맞는 두께감, 기모, 길이가 딱히 없을 때 불러오는 메소드)
    fun clickRadio(rd:RadioButton) {
        rd.setOnClickListener {
            radio_clear()
            visibleGone_radio()
            rd.isChecked = true
        }
    }

    // 라디오 버튼 클린된 것 모두 초기화
    //(라디오버튼 모두 해제)
    fun radio_clear() {
        binding.outerGr1.clearCheck()
        binding.outerGr2.clearCheck()
        binding.topGr1.clearCheck()
        binding.topGr2.clearCheck()
        binding.bottomGr1.clearCheck()
        binding.bottomGr2.clearCheck()
        binding.onepieceGr.clearCheck()
        binding.shoesGr1.clearCheck()
        binding.shoesGr2.clearCheck()

        binding.liningGr.clearCheck()
        binding.thicknessGr.clearCheck()
        binding.lengthGr.clearCheck()
    }

    // (레이아웃(세부: 기모 두께 길이) 안보이기)
    //기모, 두께, 길이 라디오버튼 안보이게 하는 메소드
    fun visibleGone_radio() {
        binding.lining.visibility = View.GONE
        binding.thickness.visibility = View.GONE
        binding.length.visibility = View.GONE
    }

    //하의 선택 && 9부 선택만 다르게 ((하의)길이에 따라 기모나 두께감 나오게 하는 메소드)
    fun bottom_length9() {
        binding.length9.setOnClickListener {
            if (binding.denim.isChecked || binding.leggings.isChecked || binding.jogger.isChecked)
                binding.lining.visibility = VISIBLE
            if (binding.slacks.isChecked)
                binding.thickness.visibility = VISIBLE
        }
        binding.length3.setOnClickListener {
            binding.lining.visibility = GONE
            binding.thickness.visibility = GONE
        }
        binding.length5.setOnClickListener {
            binding.lining.visibility = GONE
            binding.thickness.visibility = GONE
        }
    }


    // 여기부턴 사진 관련
    // 지금은 앨범만 활성화. 카메라 사용은 비활성화 (함수만 있음)
    fun initViews() {
        binding.picPlus.setOnClickListener {
            openGallery()
        }
    }

    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_GALLERY)
    }

    fun createImageUri(filename:String, mimeType:String) : Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }

    // 원본 이미지 불러오는 메서드
    fun loadBitmap(photoUri: Uri) : Bitmap? {
        try {
            return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                val source  = ImageDecoder.createSource(contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, photoUri)

            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return null
    }


    override fun permissionGranted(requestCode: Int) {
        when(requestCode) {
            PERM_STORAGE -> initViews()
            PERM_CAMERA -> openCamera()
        }
    }

    override fun permissionDenied(requestCode: Int) {
        when(requestCode) {
            PERM_STORAGE -> {
                Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    var realUri = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_CAMERA -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding.picPlus.setImageBitmap(bitmap)
                    realUri?.let { uri ->
                        val bitmap = loadBitmap(uri)
                        binding.picPlus.setImageBitmap(bitmap)

                        realUri = null

                    }
                }
                REQ_GALLERY -> {
                    data?.data?.let {uri ->
                        binding.picPlus.setImageURI(uri)
                    }
                }
            }
        }
    }


}