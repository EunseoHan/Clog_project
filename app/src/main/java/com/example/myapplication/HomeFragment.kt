package com.example.myapplication

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
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

        val lm = mainActivity.getSystemService(LOCATION_SERVICE) as LocationManager



        // 날씨 받기
//        CurrentWeatherCall()

        val button = view.findViewById<TextView>(R.id.newData)
        button.setOnClickListener {

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
                val altitude = location?.altitude

                println("else 안 provider" + provider)

//                txtResult!!.text = """
//                    위치정보 : $provider
//                    위도 : $longitude
//                    경도 : $latitude
//                    고도  :
//                    """.trimIndent() + altitude

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

            CurrentWeatherCall()

        }
        }




    val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val provider = location.provider
            val longitude = location.longitude
            val latitude = location.latitude
            val altitude = location.altitude

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
        val url =
//            "http://api.openweathermap.org/data/2.5/weather?q=seoul&appid=e7a531d94bf0d7e2bff8881247a4b70f"
            "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=e7a531d94bf0d7e2bff8881247a4b70f"
        println(url)

        val request: StringRequest =
            object : StringRequest(Method.GET, url, Response.Listener { response ->
                try {
                    val now = System.currentTimeMillis()
                    val date = Date(now)
                    val simpleDateFormatDay = SimpleDateFormat("yyyy-MM-dd")
                    val simpleDateFormatTime = SimpleDateFormat("HH:mm:ss")
                    val getDay = simpleDateFormatDay.format(date)
                    val getTime = simpleDateFormatTime.format(date)
//                    val getDate = """
//                      $getDay
//                      $getTime
//                      """.trimIndent()
                    val getDate = "$getDay $getTime"
//                    val dateView: TextView
//                    dateView = view.findViewById(R.id.dateView)
                    val dateView = view?.findViewById<TextView>(R.id.dateView)
                    if (dateView != null) {
                        dateView.text = getDate
                    }
                    val jsonObject = JSONObject(response)
                    val city = jsonObject.getString("name")
                    if (cityView != null) {
                        cityView.text = city
                    }
                    val weatherJson = jsonObject.getJSONArray("weather")
                    val weatherObj = weatherJson.getJSONObject(0)
                    val weather = weatherObj.getString("description")
                    if (weatherView != null) {
                        weatherView.text = weather
                    }
                    val tempK = JSONObject(jsonObject.getString("main"))
                    val tempDo = Math.round((tempK.getDouble("temp") - 273.15) * 100) / 100
                    if (tempView != null) {
                        tempView.text = "$tempDo°"
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