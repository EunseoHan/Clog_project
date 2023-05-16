package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class CommunityFragment : Fragment() {

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

        val itemList = ArrayList<ListItemCommunity>()

        itemList.add(ListItemCommunity(R.drawable.arrow,"제목01", "내용01"))
        itemList.add(ListItemCommunity(R.drawable.arrow,"제목02", "내용02"))
        itemList.add(ListItemCommunity(R.drawable.arrow,"제목03", "내용03"))
        itemList.add(ListItemCommunity(R.drawable.arrow,"제목04", "내용04"))
        itemList.add(ListItemCommunity(R.drawable.arrow,"제목05", "내용05"))

        val listAdapter = ListAdapterCommunity(itemList)
        listAdapter.notifyDataSetChanged()

        rv_board.adapter = listAdapter
        rv_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)

    }

}