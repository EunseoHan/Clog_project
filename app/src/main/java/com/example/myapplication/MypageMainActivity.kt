package com.example.myapplication

import android.content.ContentValues
import android.content.DialogInterface
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
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.AddClosetRequest
import com.example.myapplication.Request.MypageOutRequest
import com.example.myapplication.Request.ProfileImageRequest
import com.example.myapplication.Request.ProfileImage_Request
import com.example.myapplication.databinding.ActivityMypageMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*
import java.io.*

class MypageMainActivity : BaseActivity() { //AppCompatActivity()

    lateinit var Editprofile: Button
    lateinit var writelist: Button
    lateinit var mypagelogout: Button
    lateinit var mypageout: Button
    lateinit var mypagealarm : Button

    lateinit var back: ImageView

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY = 12

    val binding by lazy { ActivityMypageMainBinding.inflate(layoutInflater) }

    var check : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_main)
        setContentView(binding.root)

        var intent = getIntent()
        var str_id = intent.getStringExtra("ID")
        var str_pw = intent.getStringExtra("PW")
        println("mypagemainactivity")
        println(str_id)
        println(str_pw)

        var bitmapDecode : Bitmap

        val responseListener: Response.Listener<String> =
            Response.Listener<String> { response ->
                try {
                    println("hongchul$response")
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")

                    if (success) {
                        println("디코딩을 해라")
                        var IMG = jsonObject.getString("IMG")
                        val decoder: Base64.Decoder = Base64.getDecoder()
                        val encodeByte = decoder.decode(IMG)
                        bitmapDecode = BitmapFactory.decodeByteArray(
                            encodeByte,
                            0,
                            encodeByte.size
                        )
                        binding.mypageperson.setImageBitmap(bitmapDecode)
                    } else {
                        println("실패")
                    }

//                    //이미지가 있는 상태인지 아닌지 확인
//                    val check_image_ox = jsonObject.getString("check_image_ox")

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        val profileImage_Request = ProfileImage_Request(str_id, responseListener)
        println("아이디 : $str_id")
        val queue = Volley.newRequestQueue(this@MypageMainActivity)
        queue.add(profileImage_Request)

        Editprofile = findViewById(R.id.Editprofile)
        writelist = findViewById(R.id.writelist)
        mypagelogout = findViewById(R.id.mypagelogout)
        mypageout = findViewById(R.id.mypageout)
        mypagealarm = findViewById(R.id.mypagealarm)
        back = findViewById(R.id.back)

        Editprofile.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            intent.putExtra("ID",str_id)
            startActivity(intent)
        }
        writelist.setOnClickListener {
            val intent = Intent(this, MypageCommunityActivity::class.java)
            intent.putExtra("ID", str_id)
            startActivity(intent)
        }

        mypagealarm.setOnClickListener {
            val intent = Intent(this, ButtonAlarm::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ID", str_id)
            intent.putExtra("PW", str_pw)
            startActivity(intent)
        }

        mypageout.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("정말로 탈퇴하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener{dialog, which ->

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
                                if (success) { // 탈퇴에 성공한 경우
                                    println("성공")
                                    Toast.makeText(
                                        this@MypageMainActivity,
                                        "탈퇴되었습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(this@MypageMainActivity, MainActivity::class.java)
                                    getIntent().removeExtra("ID");
                                    getIntent().removeExtra("PW");
                                    var str_id1 = intent.getStringExtra("ID")
                                    var str_pw1 = intent.getStringExtra("PW")
                                    intent.putExtra("ID",str_id1)
                                    intent.putExtra("PW",str_pw1)
                                    println("탈퇴성공")
                                    println(str_id1)
                                    println(str_pw1)
                                    startActivity(intent)
                                } else { // 탈퇴에 실패한 경우
                                    println("실패")
                                    val intent =
                                        Intent(this@MypageMainActivity, MypageMainActivity::class.java)
                                    startActivity(intent)
                                    return@Listener
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                    val mypageoutrequest = str_id?.let { str_pw?.let { it1 ->
                        MypageOutRequest(it,
                            it1, responseListener)
                    } }
                    val queue = Volley.newRequestQueue(this@MypageMainActivity)
                    //queue.add<Any>(loginRequest)
                    queue.add(mypageoutrequest)
                
//                    val intent = Intent(this, MypageOutActivity::class.java)
//                    intent.putExtra("ID", str_id)
//                    intent.putExtra("PW", str_pw)
//                    startActivity(intent)
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener{ dialog, which ->
                    Toast.makeText(
                        this@MypageMainActivity,
                        "취소되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            builder.show()
        }

        mypagelogout.setOnClickListener {
            Toast.makeText(
                this@MypageMainActivity,
                "로그아웃되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
            val intent =
                Intent(this@MypageMainActivity, MainActivity::class.java)
            getIntent().removeExtra("ID");
            getIntent().removeExtra("PW");
            var str_id2 = intent.getStringExtra("ID")
            var str_pw2 = intent.getStringExtra("PW")
            intent.putExtra("ID",str_id2)
            intent.putExtra("PW",str_pw2)
            println("로그아웃성공")
            println(str_id2)
            println(str_pw2)
            startActivity(intent)
        }

        binding.mypageperson.setOnClickListener {
            openGallery()
            println("mypageperson 눌려 ?")
        }

        binding.profileIMG.setOnClickListener {
            if(check==0){
                Toast.makeText(
                    this@MypageMainActivity,
                    "등록할 사진을 선택해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(check==1) {
                val responseListener: Response.Listener<String> =
                    Response.Listener<String> { response ->
                        try {
                            println("hongchul$response")
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getBoolean("success")

                            if (success) {
                                println("hey 성공?")
                                Toast.makeText(
                                    this@MypageMainActivity,
                                    "프로필 사진이 등록되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                println("hey 실패?")
                                Toast.makeText(
                                    this@MypageMainActivity,
                                    "프로필 사진에 실패하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                val profileImageRequest =
                    ProfileImageRequest(str_id, imgPath, IMG, responseListener)
                val queue = Volley.newRequestQueue(this@MypageMainActivity)
                queue.add(profileImageRequest)

            }
        }

        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
    }

    fun initViews() {
        //5. 버튼을 클릭하면 갤러리를 여는
        binding.mypageperson.setOnClickListener {
            openGallery()
            println("mypageperson 갤러리 오픈")
        }
    }
    var realUri = null

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

    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> initViews() //스토리지 권한 요청인 경우
            PERM_CAMERA -> openCamera()
        }
    }

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) { //액티비티에서 데이터를 가지고 왔을 경우
            when (requestCode) {
                REQ_CAMERA -> { //카메라에서 // REQ_CAMERA : 카메라 요청에서 사용했던 코드
                    val bitmap =
                        data?.extras?.get("data") as Bitmap //data?.extras?.get("data") 안에 미리보기 이미지 , get은 오브젝트 따라서 Bitmap으로 형변환함.
                    binding.mypageperson.setImageBitmap(bitmap)
                    realUri?.let { uri -> //realUri가 null이 아닐 때만
                        val bitmap = loadBitmap(uri)
                        binding.mypageperson.setImageBitmap(bitmap) // 이미지 이미지뷰에 비트맵으로 등록

                        realUri = null
                    }
                }
                REQ_GALLERY -> { // 갤러리에서
                    data?.data?.let { uri -> //data의 data에 이미지에 대한 uri가 o
                        check = 1
                        binding.mypageperson.setImageURI(uri) //이미지 뷰에 선택한 이미지 출력
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
                        binding.mypageperson.setImageBitmap(img)

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