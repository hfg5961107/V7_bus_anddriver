package com.rvakva.travel.devkit.expend

import com.rvakva.travel.devkit.Config
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午2:05
 */
fun <T : Number> T.numberFormat(pattern: String = Config.PATTERN_NUMBER_BASE) =
    DecimalFormat(pattern).format(this.toDouble()) ?: ""

fun formatDate(
    date: Long = System.currentTimeMillis(),
    pattern: String = Config.PATTERN_YYYY_MM_DD
) = SimpleDateFormat(pattern, Locale.getDefault()).format(
    Date(date)
) ?: ""

fun parseDate(pattern: String = Config.PATTERN_YYYY_MM_DD, dateString: String): Long {
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).parse(dateString).time
    } catch (e: Exception) {
        e.fillInStackTrace()
        -1
    }
}

fun Long.parseSecondsOffsetToSecond() = this - System.currentTimeMillis() / 1000
fun Long.parseMillisOffsetToSecond() = (this - System.currentTimeMillis()) / 1000

fun Long.parseSecondToMinute() = StringBuilder().apply {
    val minute = this@parseSecondToMinute / 60
    val second = this@parseSecondToMinute % 60
    if (minute > 0) {
        append("${minute}分")
    }
    append("${second}秒")
}
