package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ListAdapter(val itemList: ArrayList<ListItem>) :
    RecyclerView.Adapter<ListAdapter.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.`listitem_recycler_view`, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.tv_num.text = itemList[position].num
        holder.tv_name.text = itemList[position].name
        holder.tv_writer.text = itemList[position].writer
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_num = itemView.findViewById<TextView>(R.id.tv_num)
        val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        val tv_writer = itemView.findViewById<TextView>(R.id.tv_writer)
    }
}
