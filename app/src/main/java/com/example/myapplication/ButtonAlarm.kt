package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.myapplication.databinding.AlarmButtonBinding
import java.util.*


class ButtonAlarm : AppCompatActivity() {

    val binding by lazy {AlarmButtonBinding.inflate(layoutInflater)}
    lateinit var setting: SharedPreferences

    companion object {
        const val REQUEST_CODE = 101 //요청 코드 상수
    }
    object view {
        lateinit var instance: ButtonAlarm // ButtonAlarm 인스턴스를 담는 객체
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root) //액티비티의 레이아웃을 설정
        view.instance = this // ButtonAlarm의 인스턴스를 view.instance에 할당

        setting = getSharedPreferences("setting", MODE_PRIVATE) //"setting" 이름의 SharedPreferences 인스턴스를 가져옴
        //binding.switch01.isChecked = setting.getBoolean("alarm", false) // switch01 스위치의 상태를 SharedPreferences에서 가져와 설정
        val isAlarmEnabled=setting.getBoolean("alarm",false)

        val alarmManager = binding.root.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager // AlarmManager 인스턴스를 가져옴
        val pendingIntent = Intent(binding.root.context, MyAlarmReceiver::class.java).let {  // MyAlarmReceiver로의 암시적 인텐트를 생성
            it.putExtra("code", REQUEST_CODE) //"code" 키로 REQUEST_CODE 값을 추가
            it.putExtra("count", 10) //"count"키로 10 값 추가
            PendingIntent.getBroadcast(
                binding.root.context,
                REQUEST_CODE,
                it,
                PendingIntent.FLAG_IMMUTABLE
            ) // BroadcastReceiver로 보내는 PendingIntent 생성
        }
        binding.switch01.setOnCheckedChangeListener { _, isChecked ->
            setting.edit {
                putBoolean("alarm", isChecked)//"alarm" 키로 isChecked 값을 SharePreference에 저장
            }
            if (isChecked) {
//                alarmManager.set(
//                    // 알림 on 하고 10초 뒤에 알림 옴 테스트
//                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    SystemClock.elapsedRealtime() + 1000 * 10, //1000이 1초
//                    pendingIntent
//                )
//                //오후 9시  (Interval: Day)
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY,23)
                    set(Calendar.MINUTE,10)
                }
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            } else {
                alarmManager.cancel(pendingIntent) // 알림을 취소
            }
        }
    }

}