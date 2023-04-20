package com.example.myapplication

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
//import com.android.volley.RequestQueue

//RegisterRequest 클래스는 URL에 POST방식으로 파라미터들을 전송하는 역할
//즉,여기서는 회원가입정보를 PHP 서버에 보내는 여할
//RegisterRequest 클래스를 StringRequest를 상속해 만듦.
class RegisterRequest(
    userNAME: String,
    userID: String,
    userEMAIL: String,
    userPASSWORD: String,
    userPASSWORDCHECK: String,
    userPHONE: String,
    userRED: Int,
    userBLUE: Int,
    userBROWN: Int,
    userPURPLE: Int,
    userYELLOW: Int,
    userCHARACTER: Int,
    //listener: Response.Listener<String?>?
    listener: Response.Listener<String>
) :
    StringRequest(Method.POST, URL, listener, null) { //HTTP 메서드(Method.POST), 서버 URL(URL), 서버로부터 결과를 받을 때 호출할 콜백(listener), 서버 연동 실패 시 호출할 콜백(null)
    private val map: MutableMap<String, String> //Map:불변 / MutableMap:가변 ->key 값을 통해 value값을 얻는 것  / <String, String>은 Key와 Value

    init {
        map = HashMap() //HashMap은 코틀린에서 key , value 형태로 데이터를 저장
        map.put("userNAME",userNAME) //put은 HashMap에서 데이터 추가할 때 사용
        map.put("userID",userID) //userID를 "userID"에 담는다는 의미
        map.put("userEMAIL",userEMAIL)
        map.put("userPASSWORD",userPASSWORD)
        map.put("userPASSWORDCHECK",userPASSWORDCHECK)
        map.put("userPHONE",userPHONE)
        map.put("userRED",userRED.toString())
        map.put("userBLUE",userBLUE.toString())
        map.put("userBROWN",userBROWN.toString())
        map.put("userPURPLE",userPURPLE.toString())
        map.put("userYELLOW",userYELLOW.toString())
        map.put("userCHARACTER",userCHARACTER.toString())

        println(userNAME)

        /*map["userNAME"] = userNAME
        map["userID"] = userID
        map["userEMAIL"] = userEMAIL
        map["userPASSWORD"] = userPASSWORD
        map["userPHONE"] = userPHONE
        map["userRED"] = userRED.toString()
        map["userBLUE"] = userBLUE.toString()
        map["userBROWN"] = userBROWN.toString()
        map["userPURPLE"] = userPURPLE.toString()
        map["userYELLOW"] = userYELLOW.toString()
        map["userCHARACTER"] = userCHARACTER.toString()*/
    }

    /*@get:Throws(AuthFailureError::class)
    protected val params: Map<String, String>?
        protected get() = map

    companion object {
        // 서버 URL 설정 ( PHP 파일 연동 )
        private const val URL = "http://localhost:8080/Register.php"*/

    //추후 사용을 위한 부분//getParms()가 POST방식으로 보내질 매개변수들을 해시맵 형태로 모아서 보내준다.
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    companion object {
        // 서버 url 설정 (php 파일 연동)
        //private const val URL = "http://172.18.8.33:8080/Register.php" // "http:// 퍼블릭 DNS 주소/Register.php"
        private const val URL = "http://192.168.200.167:8080/Register.php"
    }
}