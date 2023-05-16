package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ListAdapterCommunity(val itemList: ArrayList<ListItemCommunity>) :
    RecyclerView.Adapter<ListAdapterCommunity.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_recycler_community, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
//        holder.title.text = itemList[position].title
//        holder.context.text = itemList[position].context
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commuImage = itemView.findViewById<ImageView>(R.id.commuImage)
        val title = itemView.findViewById<TextView>(R.id.title)
        val context = itemView.findViewById<TextView>(R.id.context)

        fun bind(item: ListItemCommunity) {
            title.text = item.title
            context.text = item.context
            Glide.with(itemView).load(item.image).into(commuImage)

        }
    }
}
