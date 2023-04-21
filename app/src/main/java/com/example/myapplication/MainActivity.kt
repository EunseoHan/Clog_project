package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import layout.ViewPagerAdapter
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {


    lateinit var home_to_mypage : ImageView
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home_to_mypage = findViewById(R.id.home_to_mypage)

        home_to_mypage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.homeToMypage.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        // 탭 설정
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 탭이 선택 되었을 때
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택되지 않은 상태로 변경 되었을 때
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택 되었을 때
            }
        })

        // 뷰페이저에 어댑터 연결
        binding.pager.adapter = ViewPagerAdapter(this)

        /* 탭과 뷰페이저를 연결, 여기서 새로운 탭을 다시 만드므로 레이아웃에서 꾸미지말고
        여기서 꾸며야함
         */
        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, position ->
            when(position) {
                0 -> tab.text = "HOME"
                1 -> tab.text = "weather"
                2 -> tab.text = "closet"
                3 -> tab.text = "community"
            }
        }.attach()
    }

}