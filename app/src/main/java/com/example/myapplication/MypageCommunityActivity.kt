package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MypageCommunityActivity : AppCompatActivity() {

    lateinit var back: ImageView

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypagecommu)

        var intent = getIntent()
        var str_id = intent.getStringExtra("ID")
        back = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, MypageMainActivity::class.java)
            intent.putExtra("ID", str_id)
            startActivity(intent)
        }

        var imgPathf : String = ""
        var title : String = ""
        var path : String = ""
        var detail : String = ""
        var id : String = ""
        var path1 : String = ""
        var bitmapDecode : Bitmap
        var number : Int

        val itemList = ArrayList<ListItemCommunity>()
        val rv_board = findViewById<RecyclerView>(R.id.rv_board)

        val responseListener =
            Response.Listener<String> { response ->
                println("여기는 타?")

                try {
                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                    println(response)
                    val jsonObject = JSONObject(response)
                    val jsonid = jsonObject.getJSONObject("id")
                    val jsonpath = jsonObject.getJSONObject("path")
                    val jsontitle = jsonObject.getJSONObject("title")
                    val jsondetail = jsonObject.getJSONObject("detail")
                    val jsonnumber = jsonObject.getJSONObject("number")
                    val jsoni = jsonObject.getInt("i")
                    println("json")
                    println("id:$jsonid")
                    println("path:$jsonpath")
                    println("title:$jsontitle")
                    println("detail:$jsondetail")
                    println("number:$jsonnumber")
                    println("i:$jsoni")

                    for (i in 1..jsoni){
                        id = jsonid.getString(i.toString())
                        path = jsonpath.getString(i.toString())
                        title = jsontitle.getString(i.toString())
                        detail = jsondetail.getString(i.toString())
                        number = jsonnumber.getInt(i.toString())

                        if(!path.isEmpty()){
                            val responseListener = Response.Listener<String> { response ->
                                try {
                                    println(response)
                                    val jsonObjectt = JSONObject(response)
                                    val success = jsonObjectt.getBoolean("success")
                                    if (success) {
                                        //디코딩
                                        var IMG = jsonObjectt.getString("IMG")
                                        val decoder: Base64.Decoder = Base64.getDecoder()
                                        val encodeByte = decoder.decode(IMG)
                                        bitmapDecode = BitmapFactory.decodeByteArray(
                                            encodeByte,
                                            0,
                                            encodeByte.size
                                        )
                                        title = jsontitle.getString(i.toString())
                                        id = jsonid.getString(i.toString())
                                        path = jsonpath.getString(i.toString())
                                        detail = jsondetail.getString(i.toString())
                                        number = jsonnumber.getInt(i.toString())
                                        itemList.add(ListItemCommunity(bitmapDecode,title,id,detail,number,path))

                                        val listAdapter = ListAdapterMypage(itemList)
                                        listAdapter.notifyDataSetChanged()

                                        rv_board.adapter = listAdapter
                                        rv_board.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


                                    } else {
                                        return@Listener
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            path1 = path.split("/").last().toString()
                            val communityimagerequest = CommunityImageRequest(path1, responseListener)
                            val queue = Volley.newRequestQueue(this)
                            queue.add(communityimagerequest)
                        }
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        val mypagecommulistrequest = MypageCommuListRequest(str_id,responseListener)
        val queue = Volley.newRequestQueue(this)
        println("큐 $queue")
        queue.add(mypagecommulistrequest)

    }
}
