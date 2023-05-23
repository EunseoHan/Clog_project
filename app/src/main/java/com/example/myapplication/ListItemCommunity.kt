package com.example.myapplication

import android.graphics.Bitmap
import android.widget.ImageView
import java.io.Serializable
import com.google.gson.annotations.SerializedName


data class ListItemCommunity(@SerializedName("image")val image: Bitmap, @SerializedName("title") val title: String, @SerializedName("context") val context: String):
    Serializable
