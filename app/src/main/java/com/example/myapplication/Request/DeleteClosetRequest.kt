package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.io.*

class DeleteClosetRequest (userID: String, clothesNAME: String, clothesIMGPATH: String?, listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null){
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        map.put("userID",userID)
        map.put("clothesNAME",clothesNAME)
        map.put("clothesIMGPATH",clothesIMGPATH.toString())
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    companion object {
        private const val URL = "http://192.168.200.167:8080/DeleteCloset.php"
    }
}