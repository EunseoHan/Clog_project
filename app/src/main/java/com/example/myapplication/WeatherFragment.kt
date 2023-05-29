package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*


class WeatherFragment : Fragment() {

    val IP = "172.30.1.9:8080"

    lateinit var location_editText: EditText
    lateinit var webView: WebView



    companion object {
        var requestQueue: RequestQueue? = null
    }

    // 1. Context를 할당할 변수를 프로퍼티로 선언(어디서든 사용할 수 있게)
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 2. Context를 액티비티로 형변환해서 할당
        mainActivity = context as MainActivity
    }

    var lat:Double = 0.0
    var lng:Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //MainActivty에서 넘긴 ID 값 send로 받음&잘 넘어왔는지 print로 확인
        // Inflate the layout for this fragment
        val send = arguments?.getString("ID")
        println("weatherFragment")
        println(send)
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        location_editText = view.findViewById(R.id.location_editText)
        webView = view.findViewById(R.id.webView)

        location_editText.setOnClickListener {
            showKakaoAddressWebView()
        }

        // list view
        val outer_board = view.findViewById<RecyclerView>(R.id.outer_board)
        val top_board = view.findViewById<RecyclerView>(R.id.top_board)
        val bottom_board = view.findViewById<RecyclerView>(R.id.bottom_board)
        val onepiece_board = view.findViewById<RecyclerView>(R.id.onepiece_board)
        val shoes_board = view.findViewById<RecyclerView>(R.id.shoes_board)

        // listview
        // Outer
        val itemListOuter = ArrayList<ListItemCloset>()

        //itemListOuter.add(ListItemCloset(R.drawable.sample))

        val listAdapterOuter = ListAdapterCloset(itemListOuter)
        listAdapterOuter.notifyDataSetChanged()

        outer_board.adapter = listAdapterOuter
        outer_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)

        // Top
        val itemListTop = ArrayList<ListItemCloset>()

        //itemListTop.add(ListItemCloset(R.drawable.sample))
        //itemListTop.add(ListItemCloset(R.drawable.sample))

        val listAdapterTop = ListAdapterCloset(itemListTop)
        listAdapterTop.notifyDataSetChanged()

        top_board.adapter = listAdapterTop
        top_board.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)






        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(MyApplication.ApplicationContext())
        }

        getGPS()
        // 날씨 받기
        CurrentWeatherCall()

        val button = view.findViewById<TextView>(R.id.getWeatherTime)
        button.setOnClickListener {
            getGPS()
            CurrentWeatherCall()
            location_editText.setText("\uD83D\uDCCD 현재 위치")

        }
    }

    // 위치 검색
    @SuppressLint("JavascriptInterface")
    private fun showKakaoAddressWebView() {

        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        webView.apply {
            addJavascriptInterface(WebViewData(), "Leaf")
            webViewClient = client
            webChromeClient = chromeClient
            loadUrl("http://$IP/daum_address.php")
        }
    }

    private val client: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }

    private inner class WebViewData {
        @JavascriptInterface
        fun getAddress(zoneCode: String, roadAddress: String, buildingName: String) {

            CoroutineScope(Dispatchers.Default).launch {

                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
                //우편번호:($zoneCode)

                    location_editText.setText("\uD83D\uDCCD $roadAddress $buildingName")
                    var geo_lat_lng = geoCoding("$roadAddress").toString()
                    lat = geo_lat_lng.substring(10 until 19).toDouble()
                    lng = geo_lat_lng.substring(20 until 30).toDouble()

                    CurrentWeatherCall()

                }
            }
        }
    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {

            val newWebView = WebView(mainActivity)

            newWebView.settings.javaScriptEnabled = true

            val dialog = Dialog(mainActivity)

            dialog.setContentView(newWebView)

            val params = dialog.window!!.attributes

            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.attributes = params
            dialog.show()

            newWebView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                    super.onJsAlert(view, url, message, result)
                    return true
                }

                override fun onCloseWindow(window: WebView?) {
                    dialog.dismiss()
                }
            }

            (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
            resultMsg.sendToTarget()

            return true
        }
    }

    // 현재 위치 + 날씨
    fun getGPS() {

        val lm = mainActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                mainActivity.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
            println("if 안")

        } else {
            println("else 안 provider")

            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val provider = location?.provider
            val longitude = location?.longitude
            val latitude = location?.latitude

            println("else 안 provider" + provider)


            if (latitude != null) {
                lat = latitude.toDouble()
            }
            if (longitude != null) {
                lng = longitude.toDouble()
            }

            println("lat=" + lat + ", lng=" + lng)

            lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1f,
                gpsLocationListener
            )
            lm.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000,
                1f,
                gpsLocationListener
            )
        }

    }

    val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val longitude = location.longitude
            val latitude = location.latitude

            lat = latitude
            lng = longitude
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun CurrentWeatherCall() {
        val wImage01 = view?.findViewById<ImageView>(R.id.wImage01)
        val wImage02 = view?.findViewById<ImageView>(R.id.wImage02)
        val wImage03 = view?.findViewById<ImageView>(R.id.wImage03)
        val wImage04 = view?.findViewById<ImageView>(R.id.wImage04)
        val wImage05 = view?.findViewById<ImageView>(R.id.wImage05)
        val wImage06 = view?.findViewById<ImageView>(R.id.wImage06)
        val wImage07 = view?.findViewById<ImageView>(R.id.wImage07)
        val wImage08 = view?.findViewById<ImageView>(R.id.wImage08)


        val time01 = view?.findViewById<TextView>(R.id.time01)
        val time02 = view?.findViewById<TextView>(R.id.time02)
        val time03 = view?.findViewById<TextView>(R.id.time03)
        val time04 = view?.findViewById<TextView>(R.id.time04)
        val time05 = view?.findViewById<TextView>(R.id.time05)
        val time06 = view?.findViewById<TextView>(R.id.time06)
        val time07 = view?.findViewById<TextView>(R.id.time07)
        val time08 = view?.findViewById<TextView>(R.id.time08)

        val temp01 = view?.findViewById<TextView>(R.id.temp01)
        val temp02 = view?.findViewById<TextView>(R.id.temp02)
        val temp03 = view?.findViewById<TextView>(R.id.temp03)
        val temp04 = view?.findViewById<TextView>(R.id.temp04)
        val temp05 = view?.findViewById<TextView>(R.id.temp05)
        val temp06 = view?.findViewById<TextView>(R.id.temp06)
        val temp07 = view?.findViewById<TextView>(R.id.temp07)
        val temp08 = view?.findViewById<TextView>(R.id.temp08)

        val textView00 = view?.findViewById<TextView>(R.id.textView00)


        val url =
            "http://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lng&appid=e7a531d94bf0d7e2bff8881247a4b70f"
        println(url)


        val request: StringRequest =
            object : StringRequest(Method.GET, url, Response.Listener { response ->
                try {
//                    println("url:"+url)
                    val now = System.currentTimeMillis()
                    val date = Date(now)
                    val simpleDateFormatTime = SimpleDateFormat("HH")
                    val getTime = simpleDateFormatTime.format(date)
                    val getTimeInt = (getTime.toInt())
//
//                    if (getTimeInt < 13) {
//                        if (textView00 != null) {
//                            textView00.text = "오전 ${getTimeInt + 1}시, 내 옷장에서 추천받기"
//                        }
//                    }
//                    else {
//                        if (textView00 != null) {
//                            textView00.text = "오후 ${getTimeInt - 11}시, 내 옷장에서 추천받기"
//                        }
//                    }





                    val jsonObject = JSONObject(response)
                    val weatherJson = jsonObject.getJSONArray("list")

                    for (i: Int in 0..8) {
                        val weatherObj = weatherJson.getJSONObject(i)

                        // 온도 받아오기
                        val main = weatherObj.getJSONObject("main")
                        val temp = Math.round((main.getDouble("temp") - 273.15) * 100) / 100
                        // 시간 받아오기
                        val dt_txt = weatherObj.getString("dt_txt")
                        var dt_txt_sub = dt_txt.substring(11 until 13).toInt()
                        // 날씨 이미지로 받아오기
                        val weather = weatherObj.getJSONArray("weather")
                        val iconObj = weather.getJSONObject(0)
                        val icon = iconObj.getString("icon")


                        val imageStr = "https://openweathermap.org/img/wn/" + icon + "@2x.png"

                        val dt_txt_cal = (dt_txt_sub + 8)%24

                        if (i==0) {
                            if (temp01 != null) { temp01.text = "$temp°" }
                            if (time01 != null) { time01.text = "$dt_txt_cal" + "시" }
                            if (wImage01 != null) { Glide.with(this).load(imageStr).into(wImage01) }
                            if (textView00 != null) {
                                if (dt_txt_cal < 13)
                                    textView00.text = "오전 ${dt_txt_cal}시, 내 옷장에서 추천받기"
                                else
                                    textView00.text = "오후 ${dt_txt_cal-12}시, 내 옷장에서 추천받기"
                            }
                        } else if (i==1) {
                            if (temp02 != null) { temp02.text = "$temp°" }
                            if (time02 != null) { time02.text = "$dt_txt_cal" + "시" }
                            if (wImage02 != null) { Glide.with(this).load(imageStr).into(wImage02) }
                        } else if (i==2) {
                            if (temp03 != null) { temp03.text = "$temp°" }
                            if (time03 != null) { time03.text = "$dt_txt_cal" + "시" }
                            if (wImage03 != null) { Glide.with(this).load(imageStr).into(wImage03) }
                        } else if (i==3) {
                            if (temp04 != null) { temp04.text = "$temp°" }
                            if (time04 != null) { time04.text = "$dt_txt_cal" + "시" }
                            if (wImage04 != null) { Glide.with(this).load(imageStr).into(wImage04) }
                        } else if (i==4) {
                            if (temp05 != null) { temp05.text = "$temp°" }
                            if (time05 != null) { time05.text = "$dt_txt_cal" + "시" }
                            if (wImage05 != null) { Glide.with(this).load(imageStr).into(wImage05) }
                        } else if (i==5) {
                            if (temp06 != null) { temp06.text = "$temp°" }
                            if (time06 != null) { time06.text = "$dt_txt_cal" + "시" }
                            if (wImage06 != null) { Glide.with(this).load(imageStr).into(wImage06) }
                        } else if (i==6) {
                            if (temp07 != null) { temp07.text = "$temp°" }
                            if (time07 != null) { time07.text = "$dt_txt_cal" + "시" }
                            if (wImage07 != null) { Glide.with(this).load(imageStr).into(wImage07) }
                        } else if (i==7) {
                            if (temp08 != null) { temp08.text = "$temp°" }
                            if (time08 != null) { time08.text = "$dt_txt_cal" + "시" }
                            if (wImage08 != null) { Glide.with(this).load(imageStr).into(wImage08) }
                        }

                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    return HashMap()
                }
            }
        request.setShouldCache(false)
        requestQueue!!.add(request)
    }

    fun geoCoding(address: String): Location {
        return try {
            Geocoder(mainActivity, Locale.KOREA).getFromLocationName(address, 1)?.let{
                Location("").apply {
                    latitude =  it[0].latitude
                    longitude = it[0].longitude
                }
            }?: Location("").apply {
                latitude = 0.0
                longitude = 0.0
            }
        }catch (e:Exception) {
            e.printStackTrace()
            geoCoding(address) //재시도
        }
    }

}
