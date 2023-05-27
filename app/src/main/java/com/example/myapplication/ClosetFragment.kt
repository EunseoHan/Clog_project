package com.example.myapplication

import android.annotation.SuppressLint
import android.R.transition.explode
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapplication.Request.ClosetFragmentRequest
import com.example.myapplication.Request.ClosetImageRequest
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.FragmentClosetBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.net.URLDecoder


class ClosetFragment : Fragment(){

    // 1. Context를 할당할 변수를 프로퍼티로 선언(어디서든 사용할 수 있게)
    private lateinit var mainActivity: MainActivity

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
        println("closetFragment")
        println(send)
        return inflater.inflate(R.layout.fragment_closet, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val add_closet = view.findViewById<TextView>(R.id.add_cloth)
//        val ex_outer = view.findViewById<ImageView>(R.id.ex_outer)

        val outer_board = view.findViewById<RecyclerView>(R.id.outer_board)
        val top_board = view.findViewById<RecyclerView>(R.id.top_board)
        val bottom_board = view.findViewById<RecyclerView>(R.id.bottom_board)
        val dress_board = view.findViewById<RecyclerView>(R.id.onepiece_board)
        val shoes_board = view.findViewById<RecyclerView>(R.id.shoes_board)


        val send = arguments?.getString("ID")
        println("closetFragment3")
        println(send)

        var imgPathf : String = ""
        var typef : Int = 0
        //var imgPathff : String = ""
        var bitmapDecode : Bitmap
        var clothesNamef : String = ""

        val responseListener =
            Response.Listener<String> { response ->
                try {
                    println(response)
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        println("성공")
                        val imgPath = jsonObject.getJSONObject("imgPath")
                        val type = jsonObject.getJSONObject("type")
                        val clothesName = jsonObject.getJSONObject("clothesName")
                        val imgPathNum = jsonObject.getInt("i")

                        println("imgPath : $imgPath")
                        println("type : $type")
                        println("clothesName : $clothesName")
                        println("imgPathNum : $imgPathNum")

                        //리스트에서 수정
                        if(imgPathNum != 0) {
                            val itemListOuter = ArrayList<ListItemCloset>()
                            val itemListTop = ArrayList<ListItemCloset>()
                            val itemListBottom = ArrayList<ListItemCloset>()
                            val itemListDress = ArrayList<ListItemCloset>()
                            val itemListShoes = ArrayList<ListItemCloset>()
                            for (i: Int in 1..imgPathNum) {
                                println("for문")

                                imgPathf = imgPath.getString(i.toString())
                                println("imgPathf : $imgPathf")

                                typef = type.getInt(i.toString())
                                println("typef : $typef")

                                //imgPathff = imgPathf.split("/").last()
                                //println("imgPathff : $imgPathff")
                                if(typef==1){val responseListener = Response.Listener<String> { response ->
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
                                                //리스트에 넣기
                                                println("${bitmapDecode::class.simpleName}")
                                                imgPathf = imgPath.getString(i.toString())
                                                println("outer에서의 imgPathf : $imgPathf")
                                                clothesNamef = clothesName.getString(i.toString())
                                                println("outer에서 clothesNamef : $clothesNamef")
                                                itemListOuter.add(ListItemCloset(bitmapDecode,imgPathf,clothesNamef,send))
                                                //itemListOuter.add(ListItemCloset(imgPathf))
                                                //ex_outer.setImageBitmap(bitmapDecode)

                                                val listAdapterOuter = ListAdapterCloset(itemListOuter)
                                                listAdapterOuter.notifyDataSetChanged()

                                                outer_board.adapter = listAdapterOuter
                                                outer_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
                                            } else {
                                                return@Listener
                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val closetImageRequest =
                                        ClosetImageRequest(imgPathf, responseListener)
//                                val closetImageRequest =
//                                    ClosetImageRequest(imgPathf, responseListener)
                                    val queue = Volley.newRequestQueue(getActivity())
                                    queue.add(closetImageRequest)
                                }else if(typef==2){
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
                                                //리스트에 넣기
                                                println("${bitmapDecode::class.simpleName}")
                                                imgPathf = imgPath.getString(i.toString())
                                                println("top에서의 imgPathf : $imgPathf")
                                                clothesNamef = clothesName.getString(i.toString())
                                                println("top에서 clothesNamef : $clothesNamef")
                                                itemListTop.add(ListItemCloset(bitmapDecode,imgPathf,clothesNamef,send))

                                                val listAdapterTop = ListAdapterCloset(itemListTop)
                                                listAdapterTop.notifyDataSetChanged()

                                                top_board.adapter = listAdapterTop
                                                top_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)

                                            } else {
                                                return@Listener
                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val closetImageRequest =
                                        ClosetImageRequest(imgPathf, responseListener)

                                    val queue = Volley.newRequestQueue(getActivity())
                                    queue.add(closetImageRequest)
                                }else if(typef==3){
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
                                                //리스트에 넣기
                                                println("${bitmapDecode::class.simpleName}")
                                                imgPathf = imgPath.getString(i.toString())
                                                println("bottom에서의 imgPathf : $imgPathf")
                                                clothesNamef = clothesName.getString(i.toString())
                                                itemListBottom.add(ListItemCloset(bitmapDecode,imgPathf,clothesNamef,send))

                                                val listAdapterBottom = ListAdapterCloset(itemListBottom)
                                                listAdapterBottom.notifyDataSetChanged()

                                                bottom_board.adapter = listAdapterBottom
                                                bottom_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
                                            } else {
                                                return@Listener
                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val closetImageRequest =
                                        ClosetImageRequest(imgPathf, responseListener)

                                    val queue = Volley.newRequestQueue(getActivity())
                                    queue.add(closetImageRequest)
                                }else if(typef==4){
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
                                                //리스트에 넣기
                                                println("${bitmapDecode::class.simpleName}")
                                                imgPathf = imgPath.getString(i.toString())
                                                clothesNamef = clothesName.getString(i.toString())
                                                itemListDress.add(ListItemCloset(bitmapDecode,imgPathf,clothesNamef,send))

                                                val listAdapterDress = ListAdapterCloset(itemListDress)
                                                listAdapterDress.notifyDataSetChanged()

                                                dress_board.adapter = listAdapterDress
                                                dress_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
                                            } else {
                                                return@Listener
                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val closetImageRequest =
                                        ClosetImageRequest(imgPathf, responseListener)

                                    val queue = Volley.newRequestQueue(getActivity())
                                    queue.add(closetImageRequest)
                                }else if(typef==5){
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
                                                //리스트에 넣기
                                                println("${bitmapDecode::class.simpleName}")
                                                imgPathf = imgPath.getString(i.toString())
                                                clothesNamef = clothesName.getString(i.toString())
                                                itemListShoes.add(ListItemCloset(bitmapDecode,imgPathf,clothesNamef,send))

                                                val listAdapterShoes = ListAdapterCloset(itemListShoes)
                                                listAdapterShoes.notifyDataSetChanged()

                                                shoes_board.adapter = listAdapterShoes
                                                shoes_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
                                            } else {
                                                return@Listener
                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    val closetImageRequest =
                                        ClosetImageRequest(imgPathf, responseListener)

                                    val queue = Volley.newRequestQueue(getActivity())
                                    queue.add(closetImageRequest)
                                }
                            }
                        }

                    } else {
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        val closetFragmentRequest = ClosetFragmentRequest(send, responseListener)
        val queue = Volley.newRequestQueue(getActivity())
        println("프래그먼트 큐 $queue")
        queue.add(closetFragmentRequest)

        add_closet.setOnClickListener{
            //MainActivity에서 ID값 받아옴, 옷 등록하기 버튼 누른 후에는 AddCloset에서 ID값 받아옴
            val send = arguments?.getString("ID")
            //잘 넘어왔는지 print로 확인
            println("addcloset-intent")
            println(send)
            if(send==null){
                Toast.makeText(getActivity(),"옷을 등록하기 위해서 로그인이 필요합니다.",Toast.LENGTH_SHORT).show();
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(activity, AddCloset::class.java)
                intent.putExtra("ID", send)
                startActivity(intent)
            }
        }

        /*// listview
        // Outer
        val itemListOuter = ArrayList<ListItemCloset>()

        //itemListOuter.add(ListItemCloset(R.drawable.sample))
        //println("${ListItemCloset(R.drawable.sample)::class.simpleName}")
        //itemListOuter.add(ListItemCloset(bitmapDecode))

        val listAdapterOuter = ListAdapterCloset(itemListOuter)
        listAdapterOuter.notifyDataSetChanged()

        outer_board.adapter = listAdapterOuter
        outer_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)

        // Top
        val itemListTop = ArrayList<ListItemCloset>()

        //itemListTop.add(ListItemCloset(R.drawable.sample))
        //itemListTop.add(ListItemCloset(R.drawable.sample))

        val listAdapterTop = ListAdapterCloset(itemListTop)
        listAdapterTop.notifyDataSetChanged()

        top_board.adapter = listAdapterTop
        top_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)*/


    }



}