package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class WeatherFragmentRequest(userID: String?, listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        map.put("userID",userID.toString())
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }

    companion object {
        // 서버 URL 설정 ( PHP 파일 연동 )
        private const val URL = "http://172.18.10.176:8080/Weather.php"
    }
}