package com.example.myapplication

import android.graphics.Bitmap
import android.net.Uri
import java.io.Serializable
import com.google.gson.annotations.SerializedName

//data class ListItemCloset(val ImageResource: Bitmap)
data class ListItemCloset(@SerializedName("image")val image: Bitmap, @SerializedName("imageName")val imageName: String, @SerializedName("clothesName") val clothesName: String, @SerializedName("id") val id: String?):
    Serializable
//data class ListItemCloset(val ImageResource: Uri)