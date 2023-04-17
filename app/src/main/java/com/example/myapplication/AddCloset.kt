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
import com.example.myapplication.databinding.ActivityAddClosetBinding
import com.example.myapplication.databinding.ActivityMainBinding


class AddCloset : BaseActivity() {

    lateinit var pic_plus: ImageView
    lateinit var back: ImageView

    lateinit var outer: Button
    lateinit var top: Button
    lateinit var bottom: Button
    lateinit var onepiece: Button
    lateinit var shoes: Button

    lateinit var type_detail_outer: LinearLayout
    lateinit var type_detail_top: LinearLayout
    lateinit var type_detail_bottom: LinearLayout
    lateinit var type_detail_onepiece: LinearLayout
    lateinit var type_detail_shoes: LinearLayout

    lateinit var lining: LinearLayout
    lateinit var thickness: LinearLayout
    lateinit var length: LinearLayout

    lateinit var cardigan: RadioButton
    lateinit var jacket: RadioButton
    lateinit var field_jacket: RadioButton
    lateinit var leather_jacket: RadioButton
    lateinit var fleece: RadioButton
    lateinit var hoodie: RadioButton
    lateinit var coat: RadioButton
    lateinit var padded_coat: RadioButton

    lateinit var non_sleeve: RadioButton
    lateinit var short_sleeve: RadioButton
    lateinit var shirt: RadioButton
    lateinit var mtm: RadioButton
    lateinit var hoodie_top: RadioButton
    lateinit var sweater: RadioButton

    lateinit var denim: RadioButton
    lateinit var leggings: RadioButton
    lateinit var skirt: RadioButton
    lateinit var slacks: RadioButton
    lateinit var jogger: RadioButton
    lateinit var cotton: RadioButton

    lateinit var non_sleeve_onpiece: RadioButton
    lateinit var short_sleeve_onepiece: RadioButton
    lateinit var long_sleeve_onepiece: RadioButton

    lateinit var sneakers: RadioButton
    lateinit var sandals: RadioButton
    lateinit var slippers: RadioButton
    lateinit var boots: RadioButton
    lateinit var loafer: RadioButton
    lateinit var rubber_boots: RadioButton

    lateinit var outer_gr1: RadioGroup
    lateinit var outer_gr2: RadioGroup
    lateinit var top_gr1: RadioGroup
    lateinit var top_gr2: RadioGroup
    lateinit var bottom_gr1: RadioGroup
    lateinit var bottom_gr2: RadioGroup
    lateinit var onepiece_gr: RadioGroup
    lateinit var shoes_gr1: RadioGroup
    lateinit var shoes_gr2: RadioGroup

    lateinit var lining_gr: RadioGroup
    lateinit var thickness_gr: RadioGroup
    lateinit var length_gr: RadioGroup

    lateinit var length_3: RadioButton
    lateinit var length_5: RadioButton
    lateinit var length_9: RadioButton

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY =12

    val binding by lazy { ActivityAddClosetBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_closet)

        setContentView(binding.root)
        pic_plus = findViewById<ImageView>(R.id.pic_plus)
        back = findViewById(R.id.back)

        outer = findViewById<Button>(R.id.outer)
        top = findViewById<Button>(R.id.top)
        bottom = findViewById<Button>(R.id.bottom)
        onepiece = findViewById<Button>(R.id.onepiece)
        shoes = findViewById<Button>(R.id.shoes)

        type_detail_outer = findViewById(R.id.type_detail_outer)
        type_detail_top = findViewById(R.id.type_detail_top)
        type_detail_bottom = findViewById(R.id.type_detail_bottom)
        type_detail_onepiece = findViewById(R.id.type_detail_onepiece)
        type_detail_shoes = findViewById(R.id.type_detail_shoes)

        lining = findViewById(R.id.lining)
        thickness = findViewById(R.id.thickness)
        length = findViewById(R.id.length)

        cardigan = findViewById(R.id.cardigan)
        jacket = findViewById(R.id.jacket)
        field_jacket = findViewById(R.id.field_jacket)
        leather_jacket = findViewById(R.id.leather_jacket)
        fleece = findViewById(R.id.fleece)
        hoodie = findViewById(R.id.hoodie)
        coat = findViewById(R.id.coat)
        padded_coat = findViewById(R.id.padded_coat)

        non_sleeve = findViewById(R.id.non_sleeve)
        short_sleeve = findViewById(R.id.short_sleeve)
        shirt = findViewById(R.id.shirt)
        mtm = findViewById(R.id.mtm)
        hoodie_top = findViewById(R.id.hoodie_top)
        sweater = findViewById(R.id.sweater)

        denim = findViewById(R.id.denim)
        leggings = findViewById(R.id.leggings)
        skirt = findViewById(R.id.skirt)
        slacks = findViewById(R.id.slacks)
        jogger = findViewById(R.id.jogger)
        cotton = findViewById(R.id.cotton)

        non_sleeve_onpiece = findViewById(R.id.non_sleeve_onepiece)
        short_sleeve_onepiece = findViewById(R.id.short_sleeve_onepiece)
        long_sleeve_onepiece = findViewById(R.id.long_sleeve_onepiece)

        sneakers = findViewById(R.id.sneakers)
        sandals = findViewById(R.id.sandals)
        slippers = findViewById(R.id.slippers)
        boots = findViewById(R.id.boots)
        loafer = findViewById(R.id.loafer)
        rubber_boots = findViewById(R.id.rubber_boots)

        outer_gr1 = findViewById(R.id.outer_gr1)
        outer_gr2 = findViewById(R.id.outer_gr2)
        top_gr1 = findViewById(R.id.top_gr1)
        top_gr2 = findViewById(R.id.top_gr2)
        bottom_gr1 = findViewById(R.id.bottom_gr1)
        bottom_gr2 = findViewById(R.id.bottom_gr2)
        onepiece_gr = findViewById(R.id.onepiece_gr)
        shoes_gr1 = findViewById(R.id.shoes_gr1)
        shoes_gr2 = findViewById(R.id.shoes_gr2)

        lining_gr = findViewById(R.id.lining_gr)
        thickness_gr = findViewById(R.id.thickness_gr)
        length_gr = findViewById(R.id.length_gr)

        length_3 = findViewById(R.id.length_3)
        length_5 = findViewById(R.id.length_5)
        length_9 = findViewById(R.id.length_9)



        clickBtn(outer, type_detail_outer)
        clickBtn(top, type_detail_top)
        clickBtn(bottom, type_detail_bottom)
        clickBtn(onepiece, type_detail_onepiece)
        clickBtn(shoes, type_detail_shoes)

        clickRadio(cardigan, thickness)
        clickRadio(jacket, thickness)
        clickRadio(field_jacket)
        clickRadio(leather_jacket)
        clickRadio(fleece)
        clickRadio(hoodie, lining)
        clickRadio(coat, thickness)
        clickRadio(padded_coat)

        clickRadio(non_sleeve)
        clickRadio(short_sleeve)
        clickRadio(shirt)
        clickRadio(mtm, lining)
        clickRadio(hoodie_top, lining)
        clickRadio(sweater, thickness)

        clickRadio(denim, length)
        clickRadio(leggings, length)
        clickRadio(skirt, length)
        clickRadio(slacks, length)
        clickRadio(jogger, length)
        clickRadio(cotton, length)

        clickRadio(non_sleeve_onpiece, length)
        clickRadio(short_sleeve_onepiece, length)
        clickRadio(long_sleeve_onepiece, length)

        clickRadio(sneakers)
        clickRadio(sandals)
        clickRadio(slippers)
        clickRadio(boots)
        clickRadio(loafer)
        clickRadio(rubber_boots)

        bottom_length9()


        back.setOnClickListener {
            finish()
        }



        // 1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
    }

    fun clickBtn(btn:Button, layout: LinearLayout) {
        btn.setOnClickListener {
            if (layout.visibility == VISIBLE) {
                backgroundClear()
                visibleGone()
            } else {
                backgroundClear()
                visibleGone()
                radio_clear()
                layout.visibility = View.VISIBLE
                btn.setBackgroundColor(Color.parseColor("#BFCBE5"))
                btn.setBackgroundResource(R.drawable.button_roud2)
            }
        }
    }

    fun visibleGone() {
        type_detail_outer.visibility = View.GONE
        type_detail_top.visibility = View.GONE
        type_detail_bottom.visibility = View.GONE
        type_detail_onepiece.visibility = View.GONE
        type_detail_shoes.visibility = View.GONE

        visibleGone_radio()
    }

    fun clickRadio(rd:RadioButton, layout:LinearLayout) {
        rd.setOnClickListener {
            radio_clear()
            visibleGone_radio()
            layout.visibility = View.VISIBLE
            rd.isChecked = true
        }
    }

    fun clickRadio(rd:RadioButton) {
        rd.setOnClickListener {
            radio_clear()
            visibleGone_radio()
            rd.isChecked = true
        }
    }
    fun radio_clear() {
        outer_gr1.clearCheck()
        outer_gr2.clearCheck()
        top_gr1.clearCheck()
        top_gr2.clearCheck()
        bottom_gr1.clearCheck()
        bottom_gr2.clearCheck()
        onepiece_gr.clearCheck()
        shoes_gr1.clearCheck()
        shoes_gr2.clearCheck()

        lining_gr.clearCheck()
        thickness_gr.clearCheck()
        length_gr.clearCheck()
    }

    fun visibleGone_radio() {
        lining.visibility = View.GONE
        thickness.visibility = View.GONE
        length.visibility = View.GONE
    }

    fun backgroundClear() {
        outer.setBackgroundResource(R.drawable.button_roud)
        top.setBackgroundResource(R.drawable.button_roud)
        bottom.setBackgroundResource(R.drawable.button_roud)
        onepiece.setBackgroundResource(R.drawable.button_roud)
        shoes.setBackgroundResource(R.drawable.button_roud)
    }

    fun bottom_length9() {
        length_9.setOnClickListener {
            if (denim.isChecked || leggings.isChecked || jogger.isChecked)
                lining.visibility = VISIBLE
            if (slacks.isChecked)
                thickness.visibility = VISIBLE
        }
        length_3.setOnClickListener {
            lining.visibility = GONE
            thickness.visibility = GONE
        }
        length_5.setOnClickListener {
            lining.visibility = GONE
            thickness.visibility = GONE
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
                        binding.picPlus.setImageURI(uri)
                    }
                }
            }
        }
    }


}