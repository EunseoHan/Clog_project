package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream


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

            fun bitmapToByteArray(bitmap: Bitmap): ByteArray? {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                return stream.toByteArray()
            }
            val imagedata = item.image?.let { bitmapToByteArray(it) }
            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, "${item.title}",Toast.LENGTH_SHORT).show()
                val intent = Intent(itemView.context, CommunityDetailActivity::class.java)
//                intent.putExtra("data", itemList)
                intent.putExtra("title", item.title)
                intent.putExtra("image", imagedata)
                intent.putExtra("context", item.context)
//                intent.putExtra("outputX", 100)
//                intent.putExtra("outputY",100)
                itemView.context.startActivity(intent)
            }




        }
    }
}
