package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class MypageOutRequest(userID: String?, userPASSWORD: String?, listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        if (userID != null) {
            map.put("userID",userID)
        }
        if (userPASSWORD != null) {
            map.put("userPASSWORD",userPASSWORD)
        }
        println(userID)
        println(userPASSWORD)
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }

    companion object {
        // 서버 URL 설정 ( PHP 파일 연동 )
        private const val URL = "http://121.129.163.76/appout.php"
    }
}