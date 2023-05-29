package com.example.myapplication

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyAlarmReceiver: BroadcastReceiver() {

    val CHANNEL_ID = "Test"
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.extras?.get("code") == ButtonAlarm.REQUEST_CODE) { //인텐트의 code키와 ButtonAlarm.REQUEST_CODE값 비교하여 알람코드 확인
            var count = intent.getIntExtra("count", 0) // 인텐트에서 count 키로 값을 가져옴
            Log.d("myLog", "$count")

            createNotificationChannel(context) //알림 채널 생성

            val mainActivityintent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            //PendingIntent.getActivity를 사용하여 액티비티를 시작하는 PendingIntent 생성
            val pendingIntent = PendingIntent.getActivity(context, 101, mainActivityintent, 0)
            val contents = "내일 입을 옷 정하셨나요? Clog와 함께 코디해보아요!"

            // Notification
            var builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_clog)
                setContentTitle("clog")  // 제목
                setContentText(contents)   // 알림 내용
                priority = NotificationCompat.PRIORITY_DEFAULT  // 우선순위 설정
                setContentIntent(pendingIntent) // 알림 클릭 이벤트 설정
                setAutoCancel(true) // 클릭하면 알림 삭제
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build()) //알림표시
        }
    }

    private fun createNotificationChannel(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Notification_Ch"
            val descriptionText = "Test Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
