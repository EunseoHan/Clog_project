package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommunityList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_list)

        val rv_board = findViewById<RecyclerView>(R.id.rv_board)


        val itemList = ArrayList<ListItem>()

        itemList.add(ListItem("ㅤ", "제목", "작성자"))
        itemList.add(ListItem("1", "ㅤ", "ㅤ"))
        itemList.add(ListItem("2", "ㅤ", "ㅤ"))
        itemList.add(ListItem("3", "ㅤ", "ㅤ"))
        itemList.add(ListItem("4", "ㅤ", "ㅤ"))

        val listAdapter = ListAdapter(itemList)
        listAdapter.notifyDataSetChanged()

        rv_board.adapter = listAdapter
        rv_board.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}