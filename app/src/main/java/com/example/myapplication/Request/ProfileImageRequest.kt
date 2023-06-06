package com.example.myapplication.Request

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.AuthFailureError
import java.io.*

//프로필 이미지 저장하는
class ProfileImageRequest(userID: String?,IMGPATH: String?,IMG : String?,
                          listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null){
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        map.put("userID",userID.toString())
        map.put("IMGPATH",IMGPATH.toString()) //이미지 경로
        map.put("IMG",IMG.toString()) //이미지 자체
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    companion object {
        private const val URL = "http://192.168.45.230/ProfileImage.php"
    }
}