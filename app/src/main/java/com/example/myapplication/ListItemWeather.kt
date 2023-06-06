package com.example.myapplication

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ListItemWeather(
        @SerializedName("image") val image: Bitmap,
        @SerializedName("imageName") val imageName: String,
        @SerializedName("clothesName") val clothesName: String,
        @SerializedName("id") val id: String?):
        Serializable
