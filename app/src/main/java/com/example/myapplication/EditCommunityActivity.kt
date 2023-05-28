package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.myapplication.Request.CommunityEditRequest
import com.example.myapplication.Request.CommunityWriteRequest
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

class EditCommunityActivity : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10
    val REQ_CAMERA = 11
    val REQ_GALLERY = 12
    var commu_title=""
    var commu_write=""

    lateinit var commuimage: ImageView
    var commutext = ""
    var commutitle = ""
    lateinit var id: String
    lateinit var title: String
    lateinit var image: ByteArray
    lateinit var imagebitmap: Bitmap
    lateinit var detail: String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_edit)

        val back = findViewById<ImageView>(R.id.back)
        var commu_title = findViewById<EditText>(R.id.commu_title)
        var commu_write = findViewById<EditText>(R.id.commu_write)
        val pic_plus = findViewById<ImageView>(R.id.pic_plus)
        val btnEditCommunity = findViewById<Button>(R.id.btnEditCommunity)

        id = intent.getStringExtra("ID").toString()
        //data2 = id값
        title = intent.getStringExtra("title").toString()
        detail = intent.getStringExtra("detail").toString()
        var number = intent.getStringExtra("number").toString()
        image = intent.getByteArrayExtra("image")!!
        imagebitmap = byteArrayToBitmap(image)
        println(number.toInt())
        // datas = intent.getSerializableExtra("data") as ListItemCommunity
        Glide.with(this).load(imagebitmap).into(pic_plus)

        back.setOnClickListener {
            val intent = Intent(this@EditCommunityActivity, MypageCommunityActivity::class.java)
            //로그인하면서 사용자 정보 넘기기
            intent.putExtra("ID", id)
            startActivity(intent)
        }

        btnEditCommunity.setOnClickListener {
            //request
            commutitle = commu_title.editableText.toString()
            commutext = commu_write.editableText.toString()

            val responseListener: Response.Listener<String> =
                Response.Listener<String> { response ->
                    Toast.makeText(
                        this@EditCommunityActivity,
                        "게시글 수정이 완료되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Toast 추가
                    val intent = Intent(this@EditCommunityActivity, MypageCommunityActivity::class.java)
                    //로그인하면서 사용자 정보 넘기기
                    intent.putExtra("ID", id)
                    startActivity(intent)
                }
                if(commutitle==""){
                    Toast.makeText(
                        this@EditCommunityActivity,
                        "글 제목을 입력 해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if(commutext==""){
                    Toast.makeText(
                        this@EditCommunityActivity,
                        "글 내용을 입력 해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    println(commutitle)
                    println(commutext)
                    imgPath = imgPath?.split("/")?.last()
                    val communityeditrequest = CommunityEditRequest(number.toInt(), id, imgPath, commutitle, commutext, IMG, responseListener)
                    val queue = Volley.newRequestQueue(this@EditCommunityActivity)
                    queue.add(communityeditrequest)
                }


        }
        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERM_STORAGE)
    }

    fun initViews() {
        val pic_plus = findViewById<ImageView>(R.id.pic_plus)
        //5. 버튼을 클릭하면 갤러리를 여는
        pic_plus.setOnClickListener {
            openGallery()
        }
    }


    //원본 이미지의 주소를 저장할 변수
    var realUri = null

    //3.카메라에 찍은 사진을 저장하기 위한 Uri를 넘겨준다.
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //카메라를 연다.
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
        val pic_plus = findViewById<ImageView>(R.id.pic_plus)
        if (resultCode == RESULT_OK) { //액티비티에서 데이터를 가지고 왔을 경우
            when (requestCode) {
                REQ_CAMERA -> { //카메라에서 // REQ_CAMERA : 카메라 요청에서 사용했던 코드
                    val bitmap =
                        data?.extras?.get("data") as Bitmap //data?.extras?.get("data") 안에 미리보기 이미지 , get은 오브젝트 따라서 Bitmap으로 형변환함.
                    pic_plus.setImageBitmap(bitmap)
                    realUri?.let { uri -> //realUri가 null이 아닐 때만
                        val bitmap = loadBitmap(uri)
                        pic_plus.setImageBitmap(bitmap) // 이미지 이미지뷰에 비트맵으로 등록

                        realUri = null
                    }
                }
                REQ_GALLERY -> { // 갤러리에서
                    data?.data?.let { uri -> //data의 data에 이미지에 대한 uri가 o
                        pic_plus.setImageURI(uri) //이미지 뷰에 선택한 이미지 출력
                        //갤러리앱에서 관리하는 DB정보가 있는데, 그것이 나온다 [실제 파일 경로가 아님!!]
                        //얻어온 Uri는 Gallery앱의 DB번호임. (content://-----/2854)
                        //업로드를 하려면 이미지의 절대경로(실제 경로: file:// -------/aaa.png 이런식)가 필요함
                        //Uri -->절대경로(String)로 변환
                        println("commu$uri")

                        //임의로 만든 메소드 (절대경로를 가져오는 메소드)
                        imgPath = getRealPathFromUri(uri)
                        //imgPath = createCopyAndReturnRealPath("",uri)
                        println("절대경로$imgPath")

                        //추가
                        // Base64 인코딩부분
                        val inps: InputStream? = uri?.let {
                            applicationContext.contentResolver.openInputStream(it)
                        }
                        val img: Bitmap = BitmapFactory.decodeStream(inps)
                        inps?.close()
                        pic_plus.setImageBitmap(img)

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
        println("getRealPathFromUri-commu 안에 들어왔니?")
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

    fun byteArrayToBitmap(bytearray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
    }
}