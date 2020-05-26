package com.rvakva.travel.devkit.expend

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.R

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午9:51
 */
enum class NotificationEnum(
    val channelId: String,
    val contentTitle: String,
    val contentText: String,
    val channelCode: Int,
    val importance: Int = NotificationManager.IMPORTANCE_LOW
) {
    LOCATION("CHANNEL_ID_LOCATION", "定位服务", "正在后台运行", 0x0001),
    MSG_NEW_ORDER(
        "CHANNEL_ID_MSG",
        "订单信息",
        "您有新的订单信息，请及时查看",
        0x0002,
        NotificationManager.IMPORTANCE_HIGH
    ),
    MSG_NEW_ASSIGN(
        "CHANNEL_ID_MSG",
        "信息服务",
        "您有新的指派单，请及时查看",
        0x0002,
        NotificationManager.IMPORTANCE_HIGH
    ),
}

fun NotificationEnum.buildNotification(intent: Intent? = null): Notification? {
    var builder: NotificationCompat.Builder? = null
    if (Build.VERSION.SDK_INT >= 26) {
        getSystemService<NotificationManager>()?.let { manager ->
            val currentChannel = try {
                manager.getNotificationChannel(channelId)
            } catch (e: Exception) {
                null
            }
            currentChannel ?: manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    contentTitle,
                    importance
                ).apply {
                    enableLights(true) //是否在桌面icon右上角展示小圆点
                    lightColor = Color.BLUE //小圆点颜色
                    setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
                })
            builder =
                NotificationCompat.Builder(Ktx.getInstance().app, channelId)
        }
    } else {
        builder = NotificationCompat.Builder(Ktx.getInstance().app)
    }

    return builder?.setSmallIcon(R.mipmap.ic_launcher)
        ?.setContentTitle(contentTitle)
        ?.setContentText(contentText)
        ?.setWhen(System.currentTimeMillis())
        ?.setAutoCancel(true)
        ?.apply {
            intent?.let {
                setContentIntent(
                    PendingIntent.getActivity(
                        Ktx.getInstance().app, 0, it,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
            }
        }
        ?.build()
}


fun Notification?.showNotification(id: Int) {
    this?.let {
        getSystemService<NotificationManager>()?.notify(id, it)
    }
}

fun hideNotification() {
    getSystemService<NotificationManager>()?.cancelAll()
}