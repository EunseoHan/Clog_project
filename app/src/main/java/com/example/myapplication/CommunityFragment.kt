package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.ClosetImageRequest
import com.example.myapplication.Request.CommunityImageRequest
import com.example.myapplication.Request.CommunityListRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class CommunityFragment : Fragment() {

    // 1. Context를 할당할 변수를 프로퍼티로 선언(어디서든 사용할 수 있게)
    private lateinit var mainActivity: MainActivity
    lateinit var splitresult : Array<String>
    lateinit var splitresult2 : Array<String>
    lateinit var splitid:Array<String>
    lateinit var splitpath:Array<String>
    lateinit var splitpath2:Array<String>
    lateinit var splittitle:Array<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 2. Context를 액티비티로 형변환해서 할당
        mainActivity = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val send = arguments?.getString("ID")
        println("communityFragment")
        println(send)
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val go_to_community_write = view.findViewById<TextView>(R.id.go_to_community_write)
        val rv_board = view.findViewById<RecyclerView>(R.id.rv_board)

        go_to_community_write.setOnClickListener{
            val send = arguments?.getString("ID")
            println("communityFragment-intent")
            println(send)
            val intent = Intent(activity, CommunityWrite::class.java)
            intent.putExtra("ID", send)
            startActivity(intent)
        }


        var bitmapDecode : Bitmap
        val IMG = ""
        val itemList = ArrayList<ListItemCommunity>()

        val responseListener =
            Response.Listener<String> { response ->
                println("여기는 타?")

                try {
                    // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                    println(response)
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    val jsonid = jsonObject.getJSONObject("id")
                    val jsonpath = jsonObject.getJSONObject("path")
                    val jsontitle = jsonObject.getJSONObject("title")
                    val jsoni = jsonObject.getInt("i")
                    println("json")
                    println("id:$jsonid")
                    println("path:$jsonpath")
                    println("title:$jsontitle")
                    println("i:$jsoni")

                    println("여기는 왔니")
//                    println(response)
                    println("성공")
                    //총 불러온 리스트의 값 = length

                    for (i in 1..jsoni){
                        val id = jsonid.getString(i.toString())
                        val path = jsonpath.getString(i.toString())
                        val title = jsontitle.getString(i.toString())

                        val lastpath = path.split("/").last()

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
                                        itemList.add(ListItemCommunity(bitmapDecode,title, id))

                                        val listAdapter = ListAdapterCommunity(itemList)
                                        listAdapter.notifyDataSetChanged()

                                        rv_board.adapter = listAdapter
                                        rv_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)


                                    } else {
                                        return@Listener
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            val communityimagerequest = CommunityImageRequest(lastpath, responseListener)
                            val queue = Volley.newRequestQueue(getActivity())
                            queue.add(communityimagerequest)
                        }
                    }



//                    for (i in 0..response.length-1){
//                        splitresult = response.split("{").toTypedArray()
//                    }




                    // 불러온 모든 데이터를 split해서 id, path, title만 가져와서 itemList에 add해줌
//                    for (j in 0..splitresult.size-1){
//                        splitid= arrayOf("","","","")
//                        splittitle= arrayOf("","","","")
//                        splitpath= arrayOf("","","","")
//                        for (i in 0 until splitresult[j].length-1){
//                            splitresult2 = splitresult[j].split(",").toTypedArray()
//                            for (i in 0..splitresult2.size-1){
//                                for (i in 0..splitresult2[0].length-1){
//                                    splitid = splitresult2[0].split("\"").toTypedArray()
//                                }
//
//                                for (i in 0..splitresult2[1].length-1){
//                                    splitpath = splitresult2[1].split("\"").toTypedArray()
//                                    splitpath2 = arrayOf(splitpath[3].split("/").toTypedArray().last())
//                                    //사진 등록되지 않았을 경우
//                                    if(splitpath2[0].isEmpty()){
//                                        splitpath2[0]= "IMG_20230513_185413.jpg"
//                                    }
//
//                                    val responseListener =
//                                        Response.Listener<String> { response ->
//                                            try {
//                                                println(response)
//                                                val jsonObjectt = JSONObject(response)
//                                                val success = jsonObjectt.getBoolean("success")
//                                                if (success) {
//                                                    //디코딩
//                                                    var IMG = jsonObjectt.getString("IMG")
//                                                    println("여기$IMG")
//                                                    if (IMG.isEmpty()) {
//                                                        IMG = ""
//                                                    }
//                                                    val decoder: Base64.Decoder = Base64.getDecoder()
//                                                    val encodeByte = decoder.decode(IMG)
//                                                    bitmapDecode = BitmapFactory.decodeByteArray(
//                                                        encodeByte,
//                                                        0,
//                                                        encodeByte.size
//                                                    )
//
//                                                    itemList.add(ListItemCommunity(bitmapDecode, splittitle[3], splitid[3]))
//                                                    //안할 경우 아무런 값이 들어가 있지 않은 List 하나가 생성됨 -> if문으로 걸러줌
//                                                    if (!splitid[3].isEmpty()) {
//                                                        itemList.add(
//                                                            ListItemCommunity(
//                                                                bitmapDecode,
//                                                                splittitle[3],
//                                                                splitid[3]
//                                                            )
//                                                        )
//                                                    }
//
//                                                } else {
//                                                    return@Listener
//                                                }
//                                            } catch (e: JSONException) {
//                                                e.printStackTrace()
//                                            }
//
//                                        }
//                                    val communityimagerequest = CommunityImageRequest(splitpath2[0], responseListener)
//                                    val queue1 = Volley.newRequestQueue(getActivity())
//                                    queue1.add(communityimagerequest)
//
//                                }
//
//                                for (i in 0..splitresult2[2].length-1){
//                                    splittitle = splitresult2[2].split("\"").toTypedArray()
//                                }
//
//                            }
//
//
//                        }
//                    }


                    val listAdapter = ListAdapterCommunity(itemList)
                    listAdapter.notifyDataSetChanged()

                    rv_board.adapter = listAdapter
                    rv_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
                } catch (e: JSONException) {
//                    e.printStackTrace()
                }
            }

        val communitylistrequest = CommunityListRequest(responseListener)
        val queue = Volley.newRequestQueue(getActivity())
        println("큐 $queue")
        queue.add(communitylistrequest)

    }

}