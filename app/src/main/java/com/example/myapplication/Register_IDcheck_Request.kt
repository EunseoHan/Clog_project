package com.example.myapplication

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class Register_IDcheck_Request(userID: String, listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>
    init {
        map = HashMap()
        map.put("userID",userID) //userID를 "userID"에 담는다는 의미
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    companion object {
        private const val URL = "http://218.159.194.233/IDcheck.php"
    }
}