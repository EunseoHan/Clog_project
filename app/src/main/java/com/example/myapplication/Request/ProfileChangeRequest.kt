package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class ProfileChangeRequest(userID : String?,
                           userPASSWORD: String,
                           userPHONE: String,
                           userRED: Int,
                           userBLUE: Int,
                           userBROWN: Int,
                           userPURPLE: Int,
                           userYELLOW: Int,
                           userCHARACTER: Int,
                           listener: Response.Listener<String>) :
    StringRequest(Method.POST, URL, listener, null)
{
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        map.put("userID", userID.toString())
        map.put("userPASSWORD", userPASSWORD)
        map.put("userPHONE", userPHONE)
        map.put("userRED", userRED.toString())
        map.put("userBLUE", userBLUE.toString())
        map.put("userBROWN", userBROWN.toString())
        map.put("userPURPLE", userPURPLE.toString())
        map.put("userYELLOW", userYELLOW.toString())
        map.put("userCHARACTER", userCHARACTER.toString())
    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    companion object {
        private const val URL = "http://192.168.200.167:8080/ProfileChange.php"
    }
}