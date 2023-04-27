package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R

class WeatherFragment : Fragment() {

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

}
