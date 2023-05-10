package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import org.json.JSONObject
import java.util.*


class WeatherFragment : Fragment() {

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
        }
    }

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
//                        var icon_sub = icon.substring(0 until 2)


                        val imageStr = "https://openweathermap.org/img/wn/" + icon + "@2x.png"

                        val timeMinus = 24-getTimeInt
                        val dt_txt_cal = (dt_txt_sub + 8)%24

                        if (i==0) {
                            if (temp01 != null) { temp01.text = "$temp°" }
                            if (time01 != null) { time01.text = "$dt_txt_cal" + "시" }
                            if (wImage01 != null) { Glide.with(this).load(imageStr).into(wImage01) }
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

                        // 집어넣기
//                        if (dt_txt_cal in 0..2) {
//                            if (temp01 != null) { temp01.text = "$temp°" }
//                            if (time01 != null) { time01.text = "$dt_txt_sub" + "시" }
//                            if (wImage01 != null) { Glide.with(this).load(imageStr).into(wImage01) }
//                        } else if (dt_txt_cal in 3..5) {
//                            if (temp02 != null) { temp02.text = "$temp°" }
//                            if (time02 != null) { time02.text = "$dt_txt_sub" + "시" }
//                            if (wImage02 != null) { Glide.with(this).load(imageStr).into(wImage02) }
//                        } else if (dt_txt_cal in 6..8) {
//                            if (temp03 != null) { temp03.text = "$temp°" }
//                            if (time03 != null) { time03.text = "$dt_txt_sub" + "시" }
//                            if (wImage03 != null) { Glide.with(this).load(imageStr).into(wImage03) }
//                        } else if (dt_txt_cal in 9..11) {
//                            if (temp04 != null) { temp04.text = "$temp°" }
//                            if (time04 != null) { time04.text = "$dt_txt_sub" + "시" }
//                            if (wImage04 != null) { Glide.with(this).load(imageStr).into(wImage04) }
//                        } else if (dt_txt_cal in 12..14) {
//                            if (temp05 != null) { temp05.text = "$temp°" }
//                            if (time05 != null) { time05.text = "$dt_txt_sub" + "시" }
//                            if (wImage05 != null) { Glide.with(this).load(imageStr).into(wImage05) }
//                        } else if (dt_txt_cal in 15..17) {
//                            if (temp06 != null) { temp06.text = "$temp°" }
//                            if (time06 != null) { time06.text = "$dt_txt_sub" + "시" }
//                            if (wImage06 != null) { Glide.with(this).load(imageStr).into(wImage06) }
//                        } else if (dt_txt_cal in 18..20) {
//                            if (temp07 != null) { temp07.text = "$temp°" }
//                            if (time07 != null) { time07.text = "$dt_txt_sub" + "시" }
//                            if (wImage07 != null) { Glide.with(this).load(imageStr).into(wImage07) }
//                        } else if (dt_txt_cal in 21..23) {
//                            if (temp08 != null) { temp08.text = "$temp°" }
//                            if (time08 != null) { time08.text = "$dt_txt_sub" + "시" }
//                            if (wImage08 != null) { Glide.with(this).load(imageStr).into(wImage08) }
//                        }
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


}
