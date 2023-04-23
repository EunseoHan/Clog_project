package com.example.myapplication

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest


class LoginRequest(userID: String, userPASSWORD: String, listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        map.put("userID",userID)
        map.put("userPASSWORD",userPASSWORD)
        println(userID)
        println(userPASSWORD)
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }

    companion object {
        // 서버 URL 설정 ( PHP 파일 연동 )
<<<<<<< HEAD
        private const val URL = "http://172.30.1.69:8080/Login.php"
=======
        private const val URL = "http://192.168.35.190/Login.php"
>>>>>>> a6c571543f6480b7bfce9c441e4113622ec850c1
    }
}