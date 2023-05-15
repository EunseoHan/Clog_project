package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        val onepiece_board = view.findViewById<RecyclerView>(R.id.onepiece_board)
        val shoes_board = view.findViewById<RecyclerView>(R.id.shoes_board)


        add_closet.setOnClickListener{
            //MainActivity에서 ID값 받아옴, 옷 등록하기 버튼 누른 후에는 AddCloset에서 ID값 받아옴
            val send = arguments?.getString("ID")
            //잘 넘어왔는지 print로 확인
            println("addcloset-intent")
            println(send)
            val intent = Intent(activity, AddCloset::class.java)
            intent.putExtra("ID", send)
            startActivity(intent)
        }
//        ex_outer.setOnClickListener{
//            val send = arguments?.getString("ID")
//            println("exouter-intent")
//            println(send)
//            val intent = Intent(activity, EditCloset::class.java)
//            intent.putExtra("ID", send)
//            startActivity(intent)
//        }


        // listview
        // Outer
        val itemListOuter = ArrayList<ListItemCloset>()

        itemListOuter.add(ListItemCloset(R.drawable.sample))

        val listAdapterOuter = ListAdapterCloset(itemListOuter)
        listAdapterOuter.notifyDataSetChanged()

        outer_board.adapter = listAdapterOuter
        outer_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)

        // Top
        val itemListTop = ArrayList<ListItemCloset>()

        itemListTop.add(ListItemCloset(R.drawable.sample))
        itemListTop.add(ListItemCloset(R.drawable.sample))

        val listAdapterTop = ListAdapterCloset(itemListTop)
        listAdapterTop.notifyDataSetChanged()

        top_board.adapter = listAdapterTop
        top_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)


    }



}