package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.loader.content.CursorLoader
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.myapplication.Request.AddClosetRequest
import com.example.myapplication.Request.DeleteClosetRequest
import com.example.myapplication.Request.EditClosetRequest
import com.example.myapplication.databinding.ActivityEditClosetBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*


class EditCloset : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY =12

    val binding by lazy { ActivityEditClosetBinding.inflate(layoutInflater) }

    lateinit var data3: ByteArray
    lateinit var data4: Bitmap

    var imgPath: String? = ""
    var IMG: String? = ""
    var check : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_closet)

        var id = intent.getStringExtra("id")
        var name = intent.getStringExtra("clothesName")
        var imageName = intent.getStringExtra("imageName")
        data3 = intent.getByteArrayExtra("image")!!
        data4 = byteArrayToBitmap(data3)
        Glide.with(this).load(data4).into(binding.picPlus)
        println("EditCloset")
        println(id)
        println(name)
        println(imageName)
        println(data3)
        println(data4)
        binding.clotheName.text=name

        var idre : String = id.toString()
        var namere : String = name.toString()
        var typere = 0
        var detailre = 0
        var lengthre = 0
        var thicknessre = 0
        var brushedre = 0
        var weatherre = 0
        println("check : $check")
        if(check==0){
            //clothes_imgPath
            imgPath = imageName!!.split("-").last()

            //uploads에 저장할 이미지
            val encoder: Base64.Encoder = Base64.getEncoder()
            IMG = encoder.encodeToString(data3)
        }

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

        //이전 정보들 표시
        val responseListener: Response.Listener<String> =
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    println(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        //표시하도록
                        val type = jsonObject.getInt("type")
                        val detail = jsonObject.getInt("detail")
                        val length = jsonObject.getInt("length")
                        val thickness = jsonObject.getInt("thickness")
                        val brushed = jsonObject.getInt("brushed")

                        if(type==1){
                            println("타입1")
                            //대분류
                            binding.outer.isSelected = true
                            binding.outer.setBackgroundResource(R.drawable.button_roud2)
                            //중분류
                            binding.typeDetailOuter.visibility = View.VISIBLE
                            //두께감 있는 경우
                            if(detail==11||detail==12||detail==17){
                                //중분류
                                if(detail==11){
                                    binding.cardigan.isChecked = true
                                }else if(detail==12){
                                    binding.jacket.isChecked = true
                                }else if(detail==17){
                                    binding.coat.isChecked = true
                                }
                                //두께감
                                Thickness(thickness)
                            }
                            //기모여부 있는 경우
                            else if(detail==16){
                                binding.hoodie.isChecked = true
                                //기모여부
                                Brushed(brushed)
                            }
                            //나머지
                            else if(detail==13){binding.fieldJacket.isChecked = true}
                            else if(detail==14){binding.leatherJacket.isChecked = true}
                            else if(detail==15){binding.fleece.isChecked = true}
                            else if(detail==18){binding.paddedCoat.isChecked = true}
                        }else if(type==2){
                            binding.top.isSelected = true
                            binding.outer.setBackgroundResource(R.drawable.button_roud2)
                            //중분류
                            binding.typeDetailTop.visibility = View.VISIBLE
                            //두께감 있는 경우
                            if(detail==26){
                                binding.sweater.isChecked = true
                                //두께감
                                Thickness(thickness)
                            }
                            //기모여부 있는 경우
                            else if(detail==24||detail==25){
                                //중분류
                                if(detail==24){
                                    binding.mtm.isChecked = true
                                }else if(detail==25){
                                    binding.hoodieTop.isChecked = true
                                }
                                //기모여부
                                Brushed(brushed)
                            }
                            //나머지
                            else if(detail==21){binding.nonSleeve.isChecked = true}
                            else if(detail==22){binding.shortSleeve.isChecked = true}
                            else if(detail==23){binding.shirt.isChecked = true}
                        }else if(type==3){
                            binding.bottom.isSelected = true
                            binding.bottom.setBackgroundResource(R.drawable.button_roud2)
                            //중분류
                            binding.typeDetailBottom.visibility = View.VISIBLE
                            //기장(전체)
                            Length(length)
                            //두께감 있는 경우
                            if(detail==34){
                                binding.slacks.isChecked = true
                                //두께감
                                Thickness(thickness)
                            }
                            //기모여부 있는 경우
                            else if(detail==31||detail==32||detail==35){
                                //중분류
                                if(detail==31){
                                    binding.denim.isChecked = true
                                }else if(detail==32){
                                    binding.leggings.isChecked = true
                                }else if(detail==35){
                                    binding.jogger.isChecked = true
                                }
                                //기모여부
                                Brushed(brushed)
                            }
                            //나머지
                            else if(detail==33){binding.skirt.isChecked = true}
                            else if(detail==36){binding.cotton.isChecked = true}
                        }else if(type==4){
                            binding.onepiece.isSelected = true
                            binding.onepiece.setBackgroundResource(R.drawable.button_roud2)
                            //중분류
                            binding.typeDetailOnepiece.visibility = View.VISIBLE
                            //기장(전체)
                            Length(length)

                            if(detail==41){binding.nonSleeveOnepiece.isChecked = true}
                            else if(detail==42){binding.shortSleeveOnepiece.isChecked = true}
                            else if(detail==43){binding.longSleeveOnepiece.isChecked = true}
                        }else if(type==5){
                            binding.shoes.isSelected
                            binding.shoes.setBackgroundResource(R.drawable.button_roud2)
                            //중분류
                            binding.typeDetailShoes.visibility = View.VISIBLE
                            if(detail==51){binding.sneakers.isChecked = true}
                            else if(detail==52){binding.sandals.isChecked = true}
                            else if(detail==53){binding.slippers.isChecked = true}
                            else if(detail==54){binding.boots.isChecked = true}
                            else if(detail==55){binding.loafer.isChecked = true}
                            else if(detail==56){binding.rubberBoots.isChecked = true}
                        }else{
                            println("선택할 수 없어 type이 없어")
                        }


                    } else {
                        println("옷 정보 받아오기 실패")
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        println("보낸 아이디 옷이름 : $idre, $namere")
        val editclosetRequest = EditClosetRequest(idre, namere, responseListener)
        val queue = Volley.newRequestQueue(this@EditCloset)
        queue.add(editclosetRequest)


        binding.back.setOnClickListener {
            finish()
        }

        //옷 수정하기
        binding.btnChange.setOnClickListener {
            //1.원래 정보 삭제
            val responseListenerD: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            println("원래 정보 삭제 성공")
                        } else {
                            println("원래 정보 삭제 실패")
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            val deleteRequest = DeleteClosetRequest(idre, namere, imageName, responseListenerD)
            val queue = Volley.newRequestQueue(this@EditCloset)
            queue.add(deleteRequest)

            //2.새로운 정보 삽입
            val responseListener: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        println("나 타니?")
                        println(imgPath)
                        println("hongchul$response")
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")

                        if (success) {
                            println("hey 성공?")
                            Toast.makeText(
                                this@EditCloset,
                                "옷 정보가 수정되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@EditCloset, MainActivity::class.java)
                            intent.putExtra("ID", idre)
                            startActivity(intent)
                        } else {
                            println("hey 실패?")
                            Toast.makeText(
                                this@EditCloset,
                                "옷 정보 수정에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            if (binding.cardigan.isChecked) {
                typere = 1
                detailre = 11
                if (binding.thin.isChecked) {
                    thicknessre = 1
                    for (i: Int in 5..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        println("addDB탔어?")
                    }
                } else if (binding.normal.isChecked) {
                    thicknessre = 2
                    for (i: Int in 3..4) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.thick.isChecked) {
                    thicknessre = 3
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 3, IMG, responseListener)
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "가디건의 두께를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.jacket.isChecked) { //자켓(아우터)
                typere = 1
                detailre = 12
                if (binding.thin.isChecked) {
                    thicknessre = 1
                    for (i: Int in 4..5) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.normal.isChecked) {
                    thicknessre = 2
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 4, IMG, responseListener)
                } else if (binding.thick.isChecked) {
                    thicknessre = 3
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 3, IMG, responseListener)
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "자켓의 두께를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.fieldJacket.isChecked) { //야상(아우터)
                typere = 1
                detailre = 13
                for (i: Int in 3..4) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.leatherJacket.isChecked) { //레더자켓(아우터)
                typere = 1
                detailre = 14
                for (i: Int in 3..4) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.fleece.isChecked) { //플리스(아우터)
                typere = 1
                detailre = 15
                for (i: Int in 2..3) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.hoodie.isChecked) { //후드집업(아우터)
                typere = 1
                detailre = 16
                if (binding.liningX.isChecked) {
                    brushedre = 1
                    for (i: Int in 3..4) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.liningO.isChecked) {
                    brushedre = 2
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 2, IMG, responseListener)
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "후드집업의 기모여부를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.coat.isChecked) { //코트(아우터)
                typere = 1
                detailre = 17
                if (binding.thin.isChecked) {
                    thicknessre = 1
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 4, IMG, responseListener)
                } else if (binding.normal.isChecked) {
                    thicknessre = 2
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 3, IMG, responseListener)
                } else if (binding.thick.isChecked) {
                    thicknessre = 3
                    for (i: Int in 1..2) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "코트의 두께를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.paddedCoat.isChecked) { //패딩(아우터)
                typere = 1
                detailre = 18
                for (i: Int in 1..2) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.nonSleeve.isChecked) { //민소매 티셔츠(상의)
                typere = 2
                detailre = 21
                addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
            } else if (binding.shortSleeve.isChecked) { //반소매 티셔츠(상의)
                typere = 2
                detailre = 22
                for (i: Int in 7..8) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.shirt.isChecked) { //블라우스/셔츠 (상의)
                typere = 2
                detailre = 23
                for (i: Int in 3..7) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.mtm.isChecked) { //맨투맨 (상의)
                typere = 2
                detailre = 24
                if (binding.liningX.isChecked) {
                    brushedre = 1
                    for (i: Int in 2..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.liningO.isChecked) {
                    brushedre = 2
                    for (i: Int in 1..2) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "맨투맨의 기모여부를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.hoodieTop.isChecked) { //후드티 (상의)
                typere = 2
                detailre = 25
                if (binding.liningX.isChecked) {
                    brushedre = 1
                    for (i: Int in 2..5) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.liningO.isChecked) {
                    brushedre = 2
                    for (i: Int in 1..2) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "후드티의 기모여부를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.sweater.isChecked) { //니트 (상의)
                typere = 2
                detailre = 26
                if (binding.thin.isChecked) {
                    thicknessre = 1
                    for (i: Int in 5..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.normal.isChecked) {
                    thicknessre = 2
                    for (i: Int in 2..4) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.thick.isChecked) {
                    thicknessre = 3
                    for (i: Int in 1..2) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "니트의 두께감을 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.denim.isChecked) { //데님팬츠 (하의)
                typere = 3
                detailre = 31
                if (binding.length3.isChecked) {
                    lengthre = 3
                    brushedre = 1
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    brushedre = 1
                    for (i: Int in 5..7) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    if (binding.liningX.isChecked) {
                        brushedre = 1
                        for (i: Int in 2..7) {
                            addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        }
                    } else if (binding.liningO.isChecked) {
                        brushedre = 2
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 1, IMG, responseListener)
                    } else {
                        Toast.makeText(
                            this@EditCloset,
                            "데님팬츠의 기모여부를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "데님팬츠의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.leggings.isChecked) { //레깅스 (하의)
                typere = 3
                detailre = 32
                if (binding.length3.isChecked) {
                    lengthre = 3
                    brushedre = 1
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    brushedre = 1
                    for (i: Int in 6..7) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    if (binding.liningX.isChecked) {
                        brushedre = 1
                        for (i: Int in 3..7) {
                            addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        }
                    } else if (binding.liningO.isChecked) {
                        brushedre = 2
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 2, IMG, responseListener)
                    } else {
                        Toast.makeText(
                            this@EditCloset,
                            "레깅스의 기모여부를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "레깅스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.skirt.isChecked) { //스커트 (하의)
                typere = 3
                detailre = 33
                if (binding.length3.isChecked) {
                    lengthre = 3
                    for (i: Int in 7..8) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    for (i: Int in 4..7) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    for (i: Int in 2..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "스커트의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.slacks.isChecked) { //슬랙스 (하의)
                typere = 3
                detailre = 34
                if (binding.length3.isChecked) {
                    lengthre = 3
                    thicknessre = 2
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    thicknessre = 2
                    for (i: Int in 6..7) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    if (binding.thin.isChecked) {
                        thicknessre = 1
                        for (i: Int in 5..7) {
                            addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        }
                    } else if (binding.normal.isChecked) {
                        thicknessre = 2
                        for (i: Int in 2..6) {
                            addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        }
                    } else if (binding.thick.isChecked) {
                        thicknessre = 3
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 1, IMG, responseListener)
                    } else {
                        Toast.makeText(
                            this@EditCloset,
                            "슬랙스의 두께를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "슬랙스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.jogger.isChecked) { //츄리닝 (하의)
                typere = 3
                detailre = 35
                if (binding.length3.isChecked) {
                    lengthre = 3
                    brushedre = 1
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    brushedre = 1
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 7, IMG, responseListener)
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    if (binding.liningX.isChecked) {
                        brushedre = 1
                        for (i: Int in 2..7) {
                            addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        }
                    } else if (binding.liningO.isChecked) {
                        brushedre = 2
                        for (i: Int in 1..2) {
                            addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                        }
                    } else {
                        Toast.makeText(
                            this@EditCloset,
                            "츄리닝의 기모여부를 선택하세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "츄리닝의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.cotton.isChecked) { //코튼팬츠 (하의)
                typere = 3
                detailre = 36
                if (binding.length3.isChecked) {
                    lengthre = 3
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 7, IMG, responseListener)
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    for (i: Int in 2..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "코튼팬츠의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.nonSleeveOnepiece.isChecked) { //민소매 원피스 (원피스)
                typere = 4
                detailre = 41
                if (binding.length3.isChecked) {
                    lengthre = 3
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 8, IMG, responseListener)
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "민소매 원피스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.shortSleeveOnepiece.isChecked) { //반소매 원피스 (원피스)
                typere = 4
                detailre = 42
                if (binding.length3.isChecked) {
                    lengthre = 3
                    for (i: Int in 7..8) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 7, IMG, responseListener)
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, 7, IMG, responseListener)
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "반소매 원피스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.longSleeveOnepiece.isChecked) { //긴소매 원피스 (원피스)
                typere = 4
                detailre = 43
                if (binding.length3.isChecked) {
                    lengthre = 3
                    for (i: Int in 5..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length5.isChecked) {
                    lengthre = 5
                    for (i: Int in 4..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else if (binding.length9.isChecked) {
                    lengthre = 9
                    for (i: Int in 2..6) {
                        addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                    }
                } else {
                    Toast.makeText(
                        this@EditCloset,
                        "긴소매 원피스의 길이를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (binding.sneakers.isChecked) { //스니커즈 (신발)
                typere = 5
                detailre = 51
                for (i: Int in 1..8) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.sandals.isChecked) { //샌들 (신발)
                typere = 5
                detailre = 52
                for (i: Int in 7..8) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.slippers.isChecked) { //슬리퍼 (신발)
                typere = 5
                detailre = 53
                for (i: Int in 7..8) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.boots.isChecked) { //부츠 (신발)
                typere = 5
                detailre = 54
                for (i: Int in 1..5) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.loafer.isChecked) { //로퍼 (신발)
                typere = 5
                detailre = 55
                for (i: Int in 3..6) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else if (binding.rubberBoots.isChecked) { //장화 (신발)
                typere = 5
                detailre = 56
                for (i: Int in 1..8) {
                    addDB(idre, namere, imgPath, typere, detailre, lengthre, thicknessre, brushedre, i, IMG, responseListener)
                }
            } else {
                Toast.makeText(
                    this@EditCloset,
                    "상세 종류를 선택하세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.btnDelete.setOnClickListener {
            val responseListenerD: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            println("옷 정보 삭제 성공")
                            Toast.makeText(
                                this@EditCloset,
                                "선택한 옷 정보가 삭제되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@EditCloset, MainActivity::class.java)
                            intent.putExtra("ID", idre)
                            startActivity(intent)
                        } else {
                            println("옷 정보 삭제 실패")
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            val deleteRequest = DeleteClosetRequest(idre, namere, imageName, responseListenerD)
            val queue = Volley.newRequestQueue(this@EditCloset)
            queue.add(deleteRequest)
        }

        // 1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
    }

    fun byteArrayToBitmap(bytearray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
    }

    fun Thickness(thickness:Int){
        binding.thickness.visibility = View.VISIBLE
        if(thickness==1){
            binding.thin.isChecked = true
        }else if(thickness==2){
            binding.normal.isChecked = true
        }else if(thickness==3){
            binding.thick.isChecked = true
        }
    }

    fun Brushed(brushed:Int){
        binding.lining.visibility = View.VISIBLE
        if(brushed==1){
            binding.liningX.isChecked = true
        }else if(brushed==2) {
            binding.liningO.isChecked = true
        }
    }

    fun Length(length:Int){
        binding.length.visibility = View.VISIBLE
        if(length==3){
            binding.length3.isChecked = true
        }else if(length==5){
            binding.length5.isChecked = true
        }else if(length==9){
            binding.length9.isChecked = true
        }
    }

    fun addDB(userID: String?,
              clothesNAME: String,
              clothesIMGPATH: String?,
              type: Int,
              detail: Int,
              length: Int,
              thickness: Int,
              brushed: Int,
              weather: Int,
              IMG : String?,
              listener: Response.Listener<String>){
        val addClosetRequest = AddClosetRequest(
            userID,
            clothesNAME,
            clothesIMGPATH,
            type,
            detail,
            length,
            thickness,
            brushed,
            weather,
            IMG,
            listener
        )
        val queue = Volley.newRequestQueue(this@EditCloset)
        queue.add(addClosetRequest)
    }

    // 대분류 -> 중분류
    // 버튼(대분류) 눌렀을 때 아래 레이아웃(중분류) 표시
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
    fun clickRadio(rd: RadioButton, layout:LinearLayout) {
        rd.setOnClickListener {
            radio_clear()   // 라디오 버튼 클린된 것 모두 초기화
            visibleGone_radio() // 레이아웃(세부) 모두 없애기
            layout.visibility = View.VISIBLE    // 클릭된 레이아웃만 보이기
            rd.isChecked = true
        }
    }
    // 라디오 버튼 클릭 시 레이아웃 표시할 게 없는 경우 (위 함수와 같은 함수. input이 다름)
    fun clickRadio(rd: RadioButton) {
        rd.setOnClickListener {
            radio_clear()   // 라디오 버튼 클린된 것 모두 초기화
            visibleGone_radio()     // 레이아웃 안보이게
            rd.isChecked = true
        }
    }
    // 라디오 버튼 클린된 것 모두 초기화
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
    // 레이아웃(세부: 기모 두께 길이) 안보이기
    fun visibleGone_radio() {
        binding.lining.visibility = View.GONE
        binding.thickness.visibility = View.GONE
        binding.length.visibility = View.GONE
    }


    // 하의 선택 && 9부 선택만 다르게
    fun bottom_length9() {
        binding.length9.setOnClickListener {
            if (binding.denim.isChecked || binding.leggings.isChecked || binding.jogger.isChecked)
                binding.lining.visibility = VISIBLE
            if (binding.slacks.isChecked)
                binding.thickness.visibility = VISIBLE
        }
        binding.length3.setOnClickListener {
            binding.lining.visibility = View.GONE
            binding.thickness.visibility = View.GONE
        }
        binding.length5.setOnClickListener {
            binding.lining.visibility = View.GONE
            binding.thickness.visibility = View.GONE
        }
    }
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
                        check = 1
                        binding.picPlus.setImageURI(uri)
                        imgPath = getRealPathFromUri(uri)

                        val inps: InputStream? = uri?.let {
                            applicationContext.contentResolver.openInputStream(it)
                        }
                        val img: Bitmap = BitmapFactory.decodeStream(inps)
                        inps?.close()
                        binding.picPlus.setImageBitmap(img)

                        val byteArrayOutputStream = ByteArrayOutputStream()
                        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

                        // 바이트 형식에서 스트링 형식으로 변환
                        val encoder: Base64.Encoder = Base64.getEncoder()
                        IMG = encoder.encodeToString(byteArray)
                    }
                }
            }
        }
    }

    fun getRealPathFromUri(uri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(this, uri, proj, null, null, null)
            var cursor: Cursor? = loader.loadInBackground()
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }
}