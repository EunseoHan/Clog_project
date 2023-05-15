package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class MainActivity : AppCompatActivity() {


//    lateinit var home_to_mypage : ImageView
    lateinit var binding: ActivityMainBinding
    lateinit var tab1:HomeFragment
    lateinit var tab2:WeatherFragment
    lateinit var tab3:ClosetFragment
    lateinit var tab4:CommunityFragment


    companion object {
        const val REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //각 tab에 해당하는 fragment 선언 및 제일 처음 tab으로 나올 화면 frameLayout에 출력
        tab1 = HomeFragment()
        tab2 = WeatherFragment()
        tab3 = ClosetFragment()
        tab4 = CommunityFragment()
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, tab1).commit()

        //LoginActivty에서 받아온 ID,PW 값
        var intent = getIntent()
        var str_id = intent.getStringExtra("ID")
        var str_pw = intent.getStringExtra("PW")
        println("mainactivity")
        println(str_id)
        println(str_pw)

//        home_to_mypage = findViewById(R.id.home_to_mypage)
//
//        home_to_mypage.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment1 = HomeFragment()


        binding.homeToMypage.setOnClickListener {
            //ID값이 없으면 Login화면, 있으면(로그인 한 상태) Mypage로 이동
            if (str_id==null||!intent.hasExtra("ID")){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else{
                val intent = Intent(this, MypageMainActivity::class.java)
                intent.putExtra("ID", str_id)
                intent.putExtra("PW", str_pw)
                startActivity(intent)
            }
        }

        // 탭 설정
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            // 기존에 사용했던 ViewPagerAdapter 사용안하고 MainActivity에서만 화면 전환 처리(어댑터 사용하면 bundle로 값 전달 안됨)
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 탭이 선택 되었을 때
                when(tab?.position){
                    0 -> {
                        //전체 fragment에 id값 보내주고 tab1 화면으로 보여줌
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab1)
                    }
                    1 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab2)
                    }
                    2 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab3)
                    }
                    3 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab4)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택되지 않은 상태로 변경 되었을 때
                when(tab?.position){
                    0 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab1)
                    }
                    1 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab2)
                    }
                    2 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab3)
                    }
                    3 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab4)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택 되었을 때
                when(tab?.position){
                    0 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab1)
                    }
                    1 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab2)
                    }
                    2 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab3)
                    }
                    3 -> {
                        val bundle = Bundle()
                        bundle.putString("ID", str_id)
                        tab1.arguments = bundle
                        tab2.arguments = bundle
                        tab3.arguments = bundle
                        tab4.arguments = bundle
                        replaceView(tab4)
                    }
                }
            }

            private fun replaceView(tab:Fragment){
                //frameLayout에 출력되는 fragment를 변경하여 보여줌
                var selectedFragment: Fragment? = null
                selectedFragment = tab
                selectedFragment?.let{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, it).commit()
                }
            }
        })

        // 뷰페이저에 어댑터 연결
        //binding.pager.adapter = ViewPagerAdapter(this)

        /* 탭과 뷰페이저를 연결, 여기서 새로운 탭을 다시 만드므로 레이아웃에서 꾸미지말고
        여기서 꾸며야함
         */
//        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, position ->
//            when(position) {
//                0 -> {
//                    tab.text = "HOME"
//                    HomeFragment()
//                }
//                1 -> {
//                    tab.text = "weather"
//                    WeatherFragment()
//                }
//                2 -> {
//                    tab.text = "closet"
//                    ClosetFragment()
//                }
//                3 -> {
//                    tab.text = "community"
//                    CommunityFragment()
//                }
//            }
//        }
    }


}