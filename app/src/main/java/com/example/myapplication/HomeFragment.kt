package com.example.myapplication

import android.Manifest
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {


    companion object {
        var requestQueue: RequestQueue? = null

        var widgetTemp: String = ""
        var widgetCity: String = ""
        var widgetWeather: String = ""
        var widgetTempMm: String = ""
        var widgetText: String = ""
        var widgetImage: String = ""
//        var widgetTime: String = ""
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
        // Inflate the layout for this fragment
        val send = arguments?.getString("ID")
        println("homeFragment")
        println(send)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(MyApplication.ApplicationContext())
        }

        getGPS()
        // 날씨 받기
        CurrentWeatherCall()

        val button = view.findViewById<TextView>(R.id.newData)
        button.setOnClickListener {
            getGPS()
            CurrentWeatherCall()
        }
        print("widgetImage: "+widgetImage)
    }





    //GPS
    fun getGPS() {

        val lm = mainActivity.getSystemService(LOCATION_SERVICE) as LocationManager

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

        } else {
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val longitude = location?.longitude
            val latitude = location?.latitude

            if (latitude != null) {
                lat = latitude.toDouble()
            }
            if (longitude != null) {
                lng = longitude.toDouble()
            }

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
        val cityView = view?.findViewById<TextView>(R.id.cityView)

        val weatherView = view?.findViewById<TextView>(R.id.weatherView)
        val tempView = view?.findViewById<TextView>(R.id.tempView)
        val tempMn = view?.findViewById<TextView>(R.id.tempMm)
        val weatherImage = view?.findViewById<LinearLayout>(R.id.weatherImage)

        val url =
//            "http://api.openweathermap.org/data/2.5/weather?q=seoul&appid=e7a531d94bf0d7e2bff8881247a4b70f"
            "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=e7a531d94bf0d7e2bff8881247a4b70f"

        val request: StringRequest =
            object : StringRequest(Method.GET, url, Response.Listener { response ->
                try {
                    val now = System.currentTimeMillis()
                    val date = Date(now)
                    val simpleDateFormatDay = SimpleDateFormat("yyyy-MM-dd")
                    val simpleDateFormatTime = SimpleDateFormat("HH:mm:ss")
                    val getDay = simpleDateFormatDay.format(date)
                    val getTime = simpleDateFormatTime.format(date)

                    val getDate = "$getDay $getTime"

                    val dateView = view?.findViewById<TextView>(R.id.dateView)
                    if (dateView != null) {
                        dateView.text = getDate
//                        widgetTime = getDate
                    }
                    val jsonObject = JSONObject(response)
                    val city = jsonObject.getString("name")
                    if (cityView != null) {
                        cityView.text = city
                        widgetCity = city
                    }
                    val weatherJson = jsonObject.getJSONArray("weather")
                    val weatherObj = weatherJson.getJSONObject(0)
                    val weather = weatherObj.getString("description")
                    val weatherId = weatherObj.getInt("id")
                    val weatherIcon = weatherObj.getString("icon")
//                    widgetImage = "https://openweathermap.org/img/wn/" + weatherIcon + "@2x.png"
                    widgetImage = "https://openweathermap.org/img/wn/01n@2x.png"


                    println("widgetImage_HOME= " + widgetImage)


                    if (weatherView != null) {
                        if (wDescEngToKor(weatherId) != "null") {
                            weatherView.text = wDescEngToKor(weatherId)
                            widgetWeather = wDescEngToKor(weatherId)
                        }
                        else {
                            weatherView.text = weather
                            widgetWeather = wDescEngToKor(weatherId)
                        }
                    }
                    if (weatherId < 300) {  // 2xx: Thunderstorm
                        weatherImage?.setBackgroundResource(R.drawable.thunderstorm)
                        widgetText = "오늘처럼 흐린 날 이런 옷은 어때요?"
                    } else if (weatherId < 600) {   // 3xx, 5xx: Rain
                        weatherImage?.setBackgroundResource(R.drawable.rain)
                        widgetText = "비 오는 날은 이렇게 입어보세요!"
                    } else if (weatherId < 700){    // 6xx: Snow
                        weatherImage?.setBackgroundResource(R.drawable.snow)
                        widgetText = "눈 오는 날은 이렇게 입어보세요!"
                    } else if (weatherId < 800) {   // Atmosphere (주로 안개)
                        weatherImage?.setBackgroundResource(R.drawable.atmosphere)
                        widgetText = ""
                    } else if (weatherId == 800) {   // clear sky
                        weatherImage?.setBackgroundResource(R.drawable.clearsky)
                        widgetText = "이렇게 맑은 날 이런 옷은 어때요?"
                    } else if (weatherId == 804) {   // overcastclouds
                        weatherImage?.setBackgroundResource(R.drawable.overcastclouds)
                        widgetText = ""
                    } else if (weatherId == 803) {   // broken clouds
                        weatherImage?.setBackgroundResource(R.drawable.brokenclouds)
                        widgetText = ""
                    } else if (weatherId < 900) {   // clouds
                        weatherImage?.setBackgroundResource(R.drawable.clouds)
                        widgetText = ""
                    }



                    val tempK = JSONObject(jsonObject.getString("main"))
                    val tempDo = Math.round((tempK.getDouble("temp") - 273.15) * 100) / 100
                    val temp_m = Math.round((tempK.getDouble("temp_min") - 273.15) * 100) / 100
                    val temp_M = Math.round((tempK.getDouble("temp_max") - 273.15) * 100) / 100
                    if (tempView != null) {
                        tempView.text = "$tempDo°"
                        widgetTemp = "$tempDo°"
                    }
                    if (tempMn != null) {
                        tempMn.text = "최고: $temp_M° 최저: $temp_m°"
                        widgetTempMm = "최고: $temp_M° 최저: $temp_m°"
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

    fun wDescEngToKor(w_id:Int):String {
        var w_id_arr = arrayOf(201,200,202,210,211,212,221,230,231,232,
            300,301,302,310,311,312,313,314,321,500,
            501,502,503,504,511,520,521,522,531,600,
            601,602,611,612,615,616,620,621,622,701,
            711,721,731,741,751,761,762,771,781,800,
            801,802,803,804,900,901,902,903,904,905,
            906,951,952,953,954,955,956,957,958,959,
            960,961,962)
        var w_kor_arr = arrayOf("가벼운 비를 동반한 천둥구름","비를 동반한 천둥구름","폭우를 동반한 천둥구름","약한 천둥구름",
            "천둥구름","강한 천둥구름","불규칙적 천둥구름","약한 연무를 동반한 천둥구름","연무를 동반한 천둥구름",
            "강한 안개비를 동반한 천둥구름","가벼운 안개비","안개비","강한 안개비","가벼운 적은비","적은비",
            "강한 적은비","소나기와 안개비","강한 소나기와 안개비","소나기","악한 비","중간 비","강한 비",
            "매우 강한 비","극심한 비","우박","약한 소나기 비","소나기 비","강한 소나기 비","불규칙적 소나기 비",
            "가벼운 눈","눈","강한 눈","진눈깨비","소나기 진눈깨비","약한 비와 눈","비와 눈","약한 소나기 눈",
            "소나기 눈","강한 소나기 눈","박무","연기","연무","모래 먼지","안개","모래","먼지","화산재","돌풍",
            "토네이도","구름 없는 맑은 하늘","약간의 구름이 낀 하늘","드문드문 구름이 낀 하늘","구름이 거의 없는 하늘",
            "구름으로 뒤덮인 흐린 하늘","토네이도","태풍","허리케인","한랭","고온","바람부는","우박","바람이 거의 없는",
            "약한 바람","부드러운 바람","중간 세기 바람","신선한 바람","센 바람","돌풍에 가까운 센 바람","돌풍",
            "심각한 돌풍","폭풍","강한 폭풍","허리케인")
        for(i: Int in 1..73) {
            if(w_id_arr[i] == w_id) {
                return w_kor_arr[i]
                break
            }
        }
        return "none"
    }
}