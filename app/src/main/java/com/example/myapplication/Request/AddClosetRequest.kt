package com.example.myapplication.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.io.*

class AddClosetRequest (
    userID: String?,
    clothesNAME: String,
    clothesIMGPATH: String?,
    type: Int,
    detail: Int,
    length: Int,
    thickness: Int,
    brushed: Int,
    weather: Int,
    IMG : String?,
    listener: Response.Listener<String>
) :
    StringRequest(Method.POST, URL, listener, null) { //HTTP 메서드(Method.POST), 서버 URL(URL), 서버로부터 결과를 받을 때 호출할 콜백(listener), 서버 연동 실패 시 호출할 콜백(null)
    private val map: MutableMap<String, String> //Map:불변 / MutableMap:가변 ->key 값을 통해 value값을 얻는 것  / <String, String>은 Key와 Value

    init {
        map = HashMap() //HashMap은 코틀린에서 key , value 형태로 데이터를 저장
        map.put("userID",userID.toString()) //userID를 "userID"에 담는다는 의미
        map.put("clothesNAME",clothesNAME)
        map.put("clothesIMGPATH",clothesIMGPATH.toString())
        map.put("type",type.toString())
        map.put("detail",detail.toString())
        map.put("length",length.toString())
        map.put("thickness",thickness.toString())
        map.put("brushed",brushed.toString())
        map.put("weather",weather.toString())
        map.put("IMG",IMG.toString())
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    companion object {
        private const val URL = "http://121.129.163.76/AddCloset.php"
    }
}