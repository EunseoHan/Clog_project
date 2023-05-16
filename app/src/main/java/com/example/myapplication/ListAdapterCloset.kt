package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ListAdapterCloset(val itemList: ArrayList<ListItemCloset>) :
    RecyclerView.Adapter<ListAdapterCloset.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_recycler_closet, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        //holder.closetImage.setImageResource(itemList[position].ImageResource)
        holder.closetImage.setImageBitmap(itemList[position].ImageResource)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val closetImage = itemView.findViewById<ImageView>(R.id.closet_image)
    }
}
