package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    companion object {
        var requestQueue: RequestQueue? = null
    }

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

        CurrentWeatherCall()

        val button = view.findViewById<TextView>(R.id.newData)
        button.setOnClickListener { CurrentWeatherCall() }
    }

    private fun CurrentWeatherCall() {
        val cityView = view?.findViewById<TextView>(R.id.cityView)

        val weatherView = view?.findViewById<TextView>(R.id.weatherView)
        val tempView = view?.findViewById<TextView>(R.id.tempView)
        val url =
            "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=e7a531d94bf0d7e2bff8881247a4b70f"
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