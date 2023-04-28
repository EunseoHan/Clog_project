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

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY =12

    val binding by lazy { ActivityAddClosetBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_closet)

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

        // 1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
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
    fun clickRadio(rd:RadioButton, layout:LinearLayout) {
        rd.setOnClickListener {
            radio_clear()   // 라디오 버튼 클린된 것 모두 초기화
            visibleGone_radio() // 레이아웃(세부) 모두 없애기
            layout.visibility = View.VISIBLE    // 클릭된 레이아웃만 보이기
            rd.isChecked = true
        }
    }
    // 라디오 버튼 클릭 시 레이아웃 표시할 게 없는 경우 (위 함수와 같은 함수. input이 다름)
    fun clickRadio(rd:RadioButton) {
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