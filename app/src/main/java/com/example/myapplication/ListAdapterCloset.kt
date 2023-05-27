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


class ListAdapterCloset(val itemList: ArrayList<ListItemCloset>) :
    RecyclerView.Adapter<ListAdapterCloset.BoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_recycler_closet, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        //holder.closetImage.setImageResource(itemList[position].ImageResource)
        //holder.closetImage.setImageBitmap(itemList[position].image)
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val closetImage = itemView.findViewById<ImageView>(R.id.closet_image)

        fun bind(item: ListItemCloset) {
            Glide.with(itemView).load(item.image).into(closetImage)

            fun bitmapToByteArray(bitmap: Bitmap): ByteArray? {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                return stream.toByteArray()
            }
            val imagedata = item.image?.let { bitmapToByteArray(it) }
            println("edit 넘어가라 1")
            closetImage.setOnClickListener {
                //Toast.makeText(itemView.context, "${item.title}",Toast.LENGTH_SHORT).show()
                val intent = Intent(itemView.context, EditCloset::class.java)
//                intent.putExtra("data", itemList)
                intent.putExtra("clothesName", item.clothesName)
                intent.putExtra("image", imagedata)
                intent.putExtra("imageName", item.imageName)
                intent.putExtra("id", item.id)

                println("edit 넘어가라 2")

                itemView.context.startActivity(intent)
            }
        }
    }
}
