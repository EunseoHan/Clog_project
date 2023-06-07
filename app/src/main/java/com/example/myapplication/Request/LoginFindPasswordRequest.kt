package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class LoginFindPasswordRequest(
    userID: String,
    userNAME: String,
    userEMAIL: String,
    listener: Response.Listener<String>
) : StringRequest(Method.POST, URL, listener, null) {

    private val params: MutableMap<String, String>

    init {
        params = HashMap()
        params["userID"] = userID
        params["userNAME"] = userNAME
        params["userEMAIL"] = userEMAIL
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return params
    }

    companion object {
        // 서버 URL 설정 ( PHP 파일 연동 )
        private const val URL = "http://192.168.200.167:8080/LoginFindPassword.php"
    }
}