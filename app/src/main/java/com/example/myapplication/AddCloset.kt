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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.loader.content.CursorLoader
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.AddClosetRequest
import com.example.myapplication.databinding.ActivityAddClosetBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.*

//import javax.xml.bind.DatatypeConverter
//import org.apache.commons.codec.binary.Base64


class AddCloset : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY = 12

    val binding by lazy {ActivityAddClosetBinding.inflate(layoutInflater) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_closet)

        //ClosetActivity에서 ID값 받아옴
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
        binding.btnAddCloset.setOnClickListener {

            //로그인 했을 때
            val responseListener: Response.Listener<String> =
                Response.Listener<String> { response ->
                    try {
                        println("나 타니?")
                        println(namere)
                        println(typere)
                        println(detailre)
                        println(lengthre)
                        println(thicknessre)
                        println(brushedre)
                        println(weatherre)
                        println("리얼 유알아이$imgPath")
                        println("hongchul$response")
                        val jsonObject = JSONObject(response)
                        println(response)
                        val success = jsonObject.getBoolean("success")

                        if (success) {
                            println("hey 성공?")
                            Toast.makeText(
                                this@AddCloset,
                                "옷이 등록되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@AddCloset, MainActivity::class.java)
                            intent.putExtra("ID", idre)
                            startActivity(intent)
                        } else {
                            println("hey 실패?")
                            Toast.makeText(
                                this@AddCloset,
                                "옷 등록에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            //로그인하지 않았을 경우
            if (idre == null) {
                Toast.makeText(
                    this@AddCloset,
                    "옷을 등록하기 위해서 로그인이 필요합니다.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@AddCloset, LoginActivity::class.java)
                startActivity(intent)
            } else {
                //로그인 했을 경우
                namere = binding.clothesName.editableText.toString()
                //가디건(아우터)
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                                this@AddCloset,
                                "데님팬츠의 기모여부를 선택하세요!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@AddCloset,
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
                                this@AddCloset,
                                "레깅스의 기모여부를 선택하세요!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@AddCloset,
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
                            this@AddCloset,
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
                                this@AddCloset,
                                "슬랙스의 두께를 선택하세요!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@AddCloset,
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
                                this@AddCloset,
                                "츄리닝의 기모여부를 선택하세요!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                            this@AddCloset,
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
                        this@AddCloset,
                        "상세 종류를 선택하세요!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        // 1. 공용저장소 권한이 있는지 확인
        //외부(공용) 저장소에 권한 필요, 동적 퍼미션
        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
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
        val queue = Volley.newRequestQueue(this@AddCloset)
        queue.add(addClosetRequest)
    }

    // 대분류 -> 중분류
    // 버튼(대분류) 눌렀을 때 아래 레이아웃(중분류) 표시
    //(아우터, 상의, 하의, 원피스, 신발 버튼 누를 때 detail 나오게 하는 메소드)
    fun clickBtn(btn: Button, layout: LinearLayout) {
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
    fun clickRadio(rd: RadioButton, layout: LinearLayout) {
        rd.setOnClickListener {
            radio_clear()
            visibleGone_radio()
            layout.visibility = View.VISIBLE
            rd.isChecked = true
        }
    }


    // 라디오 버튼 클릭 시 레이아웃 표시할 게 없는 경우 (위 함수와 같은 함수. input이 다름)
    //(각 디테일(라디오버튼) 누를 때 그에 맞는 두께감, 기모, 길이가 딱히 없을 때 불러오는 메소드)
    fun clickRadio(rd: RadioButton) {
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
        //5. 버튼을 클릭하면 갤러리를 여는
        binding.picPlus.setOnClickListener {
            openGallery()
        }
    }

    //이렇게 하려면 카메라 버튼하고 갤러리 버튼 따로 있어야 할 듯
    //2. 카메라 요청 시 권한을 먼저 체크하고 승인되었으면 카메라를 연다.
//    fun initViews() {
//        binding.picPlus.setOnClickListener {
//            requirePermissions(arrayOf(android.Manifest.permission.CANMERA), PERM_CANMERA)
//        }
//    //5. 버튼을 클릭하면 갤러리를 여는
//    binding.picPlus.setOnClickListener {
//        openGallery()
//    }
//    }

    //원본 이미지의 주소를 저장할 변수
    var realUri = null

    //3.카메라에 찍은 사진을 저장하기 위한 Uri를 넘겨준다.
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //카메라를 연다.
//        createImageUri(newFileName(),"image/jpg")?.let{ uri->
//            realUri = uri
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
//            startActivityForResult(intent, REQ_CAMERA)
//        }
        startActivityForResult(intent, REQ_CAMERA) //위에 살리면 이거 지워야됨
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK) //ACTION_PICK은 선택할 수 있는 ~
        intent.type = MediaStore.Images.Media.CONTENT_TYPE //이미지에 대한 타입이다
        startActivityForResult(intent, REQ_GALLERY)
    }


    //원본 이미지를 저장할 Uri를 MediaStore(데이터베이스)에 생성하는 메서드(파일이름, 타입이 필요)
    fun createImageUri(filename: String, mimeType: String): Uri? {
        val values = ContentValues() //(파일)이름과 타입을 넘겨주기 위한
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename) //파일이름 넘겨주는
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType) //타입을 넘겨주는

        return contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) //파일을 저장하는 Uri를 넘겨줌(return)
    }

    //파일이름을 생성하는 메서드
    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }

    // 원본 이미지 불러오는 메서드
    // Uri를 이용해서 미디어스토어에 저장된 이미지를 읽어오는 메서드
    // 파라미터로 Uri를 받아 결과값을 Bitmap으로 반환
    fun loadBitmap(photoUri: Uri): Bitmap? {
        try {
            return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                val source = ImageDecoder.createSource(contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, photoUri)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    //요청 승인된 경우
    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> initViews() //스토리지 권한 요청인 경우
            PERM_CAMERA -> openCamera()
        }
    }

    //요청 거부된 경우
    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> {
                Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            //PERM_CAMERA -> Toast.makeText(this, "카메라 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    var imgPath: String? = ""
    var IMG: String? = ""

    //카메라를 찍거나 갤러리에서 가져오면 이 결과를 넘겨주는 함수
    //4.카메라를 찍은 후에 호출된다
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) { //액티비티에서 데이터를 가지고 왔을 경우
            when (requestCode) {
                REQ_CAMERA -> { //카메라에서 // REQ_CAMERA : 카메라 요청에서 사용했던 코드
                    val bitmap =
                        data?.extras?.get("data") as Bitmap //data?.extras?.get("data") 안에 미리보기 이미지 , get은 오브젝트 따라서 Bitmap으로 형변환함.
                    binding.picPlus.setImageBitmap(bitmap)
                    realUri?.let { uri -> //realUri가 null이 아닐 때만
                        val bitmap = loadBitmap(uri)
                        binding.picPlus.setImageBitmap(bitmap) // 이미지 이미지뷰에 비트맵으로 등록

                        realUri = null
                    }
                }
                REQ_GALLERY -> { // 갤러리에서
                    data?.data?.let { uri -> //data의 data에 이미지에 대한 uri가 o
                        binding.picPlus.setImageURI(uri) //이미지 뷰에 선택한 이미지 출력
                        //갤러리앱에서 관리하는 DB정보가 있는데, 그것이 나온다 [실제 파일 경로가 아님!!]
                        //얻어온 Uri는 Gallery앱의 DB번호임. (content://-----/2854)
                        //업로드를 하려면 이미지의 절대경로(실제 경로: file:// -------/aaa.png 이런식)가 필요함
                        //Uri -->절대경로(String)로 변환
                        println("갤러리1$uri")

                        //임의로 만든 메소드 (절대경로를 가져오는 메소드)
                        imgPath = getRealPathFromUri(uri)
                        //imgPath = createCopyAndReturnRealPath("",uri)
                        println("갤러리2$imgPath")

                        //추가
                        // Base64 인코딩부분
                        val inps: InputStream? = uri?.let {
                            applicationContext.contentResolver.openInputStream(it)
                        }
                        val img: Bitmap = BitmapFactory.decodeStream(inps)
                        inps?.close()
                        binding.picPlus.setImageBitmap(img)

                        //비트맵을 바이트로 변환
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

                        // 바이트 형식에서 스트링 형식으로 변환
                        val encoder: Base64.Encoder = Base64.getEncoder()
                        IMG = encoder.encodeToString(byteArray)
                        println("인코딩 $IMG")
                    }

                }
            }
        }
    }

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    fun getRealPathFromUri(uri: Uri): String? {
        var cursor: Cursor? = null
        println("getRealPathFromUri 안에 들어왔니?")
        return try {
            println("getRealPathFromUri try 들어왔니?")
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            println("getRealPathFromUri proj $proj")
            val loader = CursorLoader(this, uri, proj, null, null, null)
            var cursor: Cursor? = loader.loadInBackground()
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            println("getRealPathFromUri column_index $column_index")
            cursor.moveToFirst()
            println("getRealPathFromUri cursor $cursor")
            println(cursor.moveToFirst())
            println(cursor.getString(column_index))
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }
}
