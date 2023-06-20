package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class PasswordChangeRequest (
    userID: String,
    newPassword: String,
    listener: Response.Listener<String>
) : StringRequest(Method.POST, URL, listener, null) {

    private val params: MutableMap<String, String>

    init {
        params = HashMap()
        params["userID"] = userID
        params["newPassword"] = newPassword
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return params
    }

    companion object {
        // 서버 URL 설정 ( PHP 파일 연동 )
        private const val URL = "http://218.159.194.63/PasswordChange.php"
    }
}