package com.rvakva.bus.personal.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author: lch
 * @Date: 2020/6/28 11:08
 **/

@SuppressLint("SimpleDateFormat")
fun parseTime(format: String?, time: String?): Long {
    var date: Date? = null
    try {
        if (time != null) {
            date = SimpleDateFormat(format).parse(time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date?.time ?: System.currentTimeMillis()
}

@SuppressLint("SimpleDateFormat")
fun getTime(format: String?, time: Long): String? {
    return SimpleDateFormat(format).format(Date(time))
}

fun isExpired(savedExprieTime: Long): Boolean {
    if (savedExprieTime == 0x0L) {
        return true
    }
    val curTime =
        parseTime(
            "yyyyMMdd",
            getTime(
                "yyyyMMdd",
                System.currentTimeMillis()
            )
        )
    return if (savedExprieTime <= curTime) {
        false
    } else true
}

fun isExpired(checkTime: String?): Boolean {
    if (TextUtils.isEmpty(checkTime)) {
        return true
    }
    val time = parseTime("yyyyMMdd", checkTime)
    val now = getTime(
        "yyyyMMdd",
        System.currentTimeMillis()
    )
    val curTime = parseTime("yyyyMMdd", now)
    return if (time <= curTime) {
        false
    } else true
}

fun getTimeSpan(seconds: Int): String? {
    val buffer = StringBuilder()
    val min = seconds / 0x3c % 0x3c
    val hours = seconds / 0x3c / 0x3c % 0x3c
    val day = seconds / 0x3c / 0x3c / 0x18 % 0x18
    if (day > 0) {
        buffer.append(day.toString() + "\u5929")
    }
    if (hours > 0) {
        buffer.append(hours.toString() + "\u5c0f\u65f6")
    }
    if (min > 0) {
        buffer.append(min.toString() + "\u5206\u949f")
    }
    return buffer.toString()
}

fun getTimeFromMinutes(minutes: Int): String? {
    val buffer = StringBuilder()
    val min = minutes % 0x3c
    val hours = minutes / 0x3c % 0x3c
    if (hours > 0) {
        buffer.append(hours.toString() + "\u5c0f\u65f6")
    }
    if (min >= 0) {
        buffer.append(min.toString() + "\u5206\u949f")
    }
    return buffer.toString()
}

fun getTimeSpanSeconds(seconds: Int): String? {
    val buffer = StringBuilder()
    val sec = seconds % 0x3c
    val min = seconds / 0x3c % 0x3c
    val hours = seconds / 0x3c / 0x3c % 0x3c
    val day = seconds / 0x3c / 0x3c / 0x18 % 0x18
    if (day > 0) {
        buffer.append(day.toString() + "D")
    }
    if (hours > 0) {
        buffer.append(hours.toString() + "h")
    }
    if (min > 0) {
        buffer.append(min.toString() + "m")
    }
    if (sec >= 0) {
        buffer.append(sec.toString() + "s")
    }
    return buffer.toString()
}

fun getTimeSpanMinutes(seconds: Int): String? {
    val buffer = StringBuilder()
    val min = seconds / 0x3c % 0x3c
    val hours = seconds / 0x3c / 0x3c % 0x3c
    val day = seconds / 0x3c / 0x3c / 0x18 % 0x18
    if (day > 0) {
        buffer.append(day.toString() + "D")
    }
    if (hours > 0) {
        buffer.append(hours.toString() + "h")
    }
    if (min >= 0) {
        buffer.append(min.toString() + "m")
    }
    return buffer.toString()
}

fun getTime(date: String, hour: Int, minute: Int): Long {
    return parseTime(
        "yyyy-MM-dd HH:mm",
        date + " " + String.format("%1$,02d:%2$,02d", hour, minute)
    )
}

fun isHourValid(hour: String?): Boolean {
    if (null == hour) {
        return false
    }
    try {
        val h = hour.toInt()
        return if (h < 0 || h > 23) {
            false
        } else true
    } catch (e: Exception) {
    }
    return false
}

fun isMinuteValid(minute: String?): Boolean {
    if (null == minute) {
        return false
    }
    try {
        val m = minute.toInt()
        return if (m < 0 || m > 59) {
            false
        } else true
    } catch (e: Exception) {
    }
    return false
}

fun getNextSevenHourCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    val hourOfDay = calendar[0xb]
    if (hourOfDay >= 0x7) {
        calendar[0x6] = calendar[0x6] + 0x1
    }
    calendar[0xb] = 0x7
    calendar[0xc] = 0x0
    calendar[0xd] = 0x0
    return calendar
}

fun getPreviewSevenHourCalendar(): Calendar? {
    val sevenClockCal = Calendar.getInstance()
    val hourOfDay = sevenClockCal[0xb]
    if (hourOfDay < 0x7) {
        sevenClockCal[0x6] = sevenClockCal[0x6] - 0x1
    }
    sevenClockCal[0xb] = 0x7
    sevenClockCal[0xc] = 0x0
    sevenClockCal[0xd] = 0x0
    return sevenClockCal
}

fun getNextSevenHourOfDayDelayMillis(): Long {
    val calendar = getNextSevenHourCalendar()
    return calendar.timeInMillis - System.currentTimeMillis()
}

fun convertMillisTime(millisSeconds: Long): String? {
    return convertSecondsTime(millisSeconds / 0x3e8)
}

@SuppressLint("DefaultLocale")
fun convertSecondsTime(seconds: Long): String? {
    var minute = (seconds / 0x3c).toInt()
    val hour = minute / 0x3c
    val second = (seconds % 0x3c).toInt()
    minute = minute % 0x3c
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

fun converSeconds2Minutes(seconds: Int): Int {
    return seconds / 0x3c
}

fun convertMinutes2Seconds(minutes: Int): Int {
    return minutes * 0x3c
}

fun getTimeSpanMinuteSeconds(seconds: Int): String? {
    val sec = seconds % 0x3c
    val min = seconds / 0x3c
    val buffer = StringBuffer()
    buffer.append(min).append("\u5206").append(sec).append("\u79d2")
    return buffer.toString()
}

fun convertSeconds2MinutesRound(seconds: Int): Int {
    var min = seconds / 0x3c
    val sec = seconds % 0x3c
    if (sec >= 0x1e) {
        min = min + 0x1
    }
    return min
}

fun now(): String? {
    return System.currentTimeMillis().toString()
}

//获取当天的开始时间
fun getDayBegin(): Date? {
    val cal: Calendar = GregorianCalendar()
    cal[Calendar.HOUR_OF_DAY] = 0
    cal[Calendar.MINUTE] = 0
    cal[Calendar.SECOND] = 0
    cal[Calendar.MILLISECOND] = 0
    return cal.time
}

//获取当天的结束时间
fun getDayEnd(): Date? {
    val cal: Calendar = GregorianCalendar()
    cal[Calendar.HOUR_OF_DAY] = 23
    cal[Calendar.MINUTE] = 59
    cal[Calendar.SECOND] = 59
    return cal.time
}

//获取昨天的开始时间
fun getBeginDayOfYesterday(): Date? {
    val cal: Calendar = GregorianCalendar()
    cal.time = getDayBegin()
    cal.add(Calendar.DAY_OF_MONTH, -1)
    return cal.time
}

//获取昨天的结束时间
fun getEndDayOfYesterDay(): Date? {
    val cal: Calendar = GregorianCalendar()
    cal.time = getDayEnd()
    cal.add(Calendar.DAY_OF_MONTH, -1)
    return cal.time
}

//获取明天的开始时间
fun getBeginDayOfTomorrow(): Date? {
    val cal: Calendar = GregorianCalendar()
    cal.time = getDayBegin()
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

//获取明天的结束时间
fun getEndDayOfTomorrow(): Date? {
    val cal: Calendar = GregorianCalendar()
    cal.time = getDayEnd()
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

//获取本周的开始时间
fun getBeginDayOfWeek(): Date? {
    val date = Date()
    val cal = Calendar.getInstance()
    cal.time = date
    var dayofweek = cal[Calendar.DAY_OF_WEEK]
    if (dayofweek == 1) {
        dayofweek += 7
    }
    cal.add(Calendar.DATE, 2 - dayofweek)
    return getDayStartTime(cal.time)
}

//获取本周的结束时间
fun getEndDayOfWeek(): Date? {
    val cal = Calendar.getInstance()
    cal.time = getBeginDayOfWeek()
    cal.add(Calendar.DAY_OF_WEEK, 6)
    val weekEndSta = cal.time
    return getDayEndTime(weekEndSta)
}

//获取本月的开始时间
fun getBeginDayOfMonth(): Date? {
    val calendar = Calendar.getInstance()
    calendar[getNowYear()!!, getNowMonth() - 1] = 1
    return getDayStartTime(calendar.time)
}

//获取本月的结束时间
fun getEndDayOfMonth(): Date? {
    val calendar = Calendar.getInstance()
    calendar[getNowYear()!!, getNowMonth() - 1] = 1
    val day = calendar.getActualMaximum(5)
    calendar[getNowYear()!!, getNowMonth() - 1] = day
    return getDayEndTime(calendar.time)
}

//获取本年的开始时间
fun getBeginDayOfYear(): Date? {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = getNowYear()!!
    // cal.set
    cal[Calendar.MONTH] = Calendar.JANUARY
    cal[Calendar.DATE] = 1
    return getDayStartTime(cal.time)
}

//获取本年的结束时间
fun getEndDayOfYear(): Date? {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = getNowYear()!!
    cal[Calendar.MONTH] = Calendar.DECEMBER
    cal[Calendar.DATE] = 31
    return getDayEndTime(cal.time)
}

//获取某个日期的开始时间
fun getDayStartTime(d: Date?): Timestamp? {
    val calendar = Calendar.getInstance()
    if (null != d) calendar.time = d
    calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH], 0, 0] =
        0
    calendar[Calendar.MILLISECOND] = 0
    return Timestamp(calendar.timeInMillis)
}

//获取某个日期的结束时间
fun getDayEndTime(d: Date?): Timestamp? {
    val calendar = Calendar.getInstance()
    if (null != d) calendar.time = d
    calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH], 23, 59] =
        59
    calendar[Calendar.MILLISECOND] = 999
    return Timestamp(calendar.timeInMillis)
}

//获取今年是哪一年
fun getNowYear(): Int? {
    val date = Date()
    val gc = Calendar.getInstance() as GregorianCalendar
    gc.time = date
    return Integer.valueOf(gc[1])
}

//获取本月是哪一月
fun getNowMonth(): Int {
    val date = Date()
    val gc = Calendar.getInstance() as GregorianCalendar
    gc.time = date
    return gc[2] + 1
}

//两个日期相减得到的天数
fun getDiffDays(beginDate: Date?, endDate: Date?): Int {
    require(!(beginDate == null || endDate == null)) { "getDiffDays param is null!" }
    val diff = ((endDate.time - beginDate.time)
            / (1000 * 60 * 60 * 24))
    return diff.toInt()
}

//两个日期相减得到的毫秒数
fun dateDiff(beginDate: Date, endDate: Date): Long {
    val date1ms = beginDate.time
    val date2ms = endDate.time
    return date2ms - date1ms
}

//获取两个日期中的最大日期
fun max(beginDate: Date?, endDate: Date?): Date? {
    if (beginDate == null) {
        return endDate
    }
    if (endDate == null) {
        return beginDate
    }
    return if (beginDate.after(endDate)) {
        beginDate
    } else endDate
}

//获取两个日期中的最小日期
fun min(beginDate: Date?, endDate: Date?): Date? {
    if (beginDate == null) {
        return endDate
    }
    if (endDate == null) {
        return beginDate
    }
    return if (beginDate.after(endDate)) {
        endDate
    } else beginDate
}

//返回某月该季度的第一个月
fun getFirstSeasonDate(date: Date?): Date? {
    val SEASON = intArrayOf(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4)
    val cal = Calendar.getInstance()
    cal.time = date
    val sean = SEASON[cal[Calendar.MONTH]]
    cal[Calendar.MONTH] = sean * 3 - 3
    return cal.time
}

//返回某个日期下几天的日期
fun getNextDay(date: Date?, i: Int): Date? {
    val cal: Calendar = GregorianCalendar()
    cal.time = date
    cal[Calendar.DATE] = cal[Calendar.DATE] + i
    return cal.time
}

//返回某个日期前几天的日期
fun getFrontDay(date: Date?, i: Int): Date? {
    val cal: Calendar = GregorianCalendar()
    cal.time = date
    cal[Calendar.DATE] = cal[Calendar.DATE] - i
    return cal.time
}

/**
 * 获取一个时间戳是星期几.
 *
 * @param calendar calendar
 * @return 该时间为星期几
 */
@SuppressLint("SwitchIntDef")
fun getWeekDay(calendar: Calendar): String? {
    var dayStr: String? = null
    val wd = calendar[Calendar.DAY_OF_WEEK]
    when (wd) {
        Calendar.MONDAY -> dayStr = "周一"
        Calendar.TUESDAY -> dayStr = "周二"
        Calendar.WEDNESDAY -> dayStr = "周三"
        Calendar.THURSDAY -> dayStr = "周四"
        Calendar.FRIDAY -> dayStr = "周五"
        Calendar.SATURDAY -> dayStr = "周六"
        Calendar.SUNDAY -> dayStr = "周日"
        else -> {
        }
    }
    return dayStr
}

/**
 * 是否是星期天
 *
 * @param year
 * @param month
 * @param day
 * @return
 */
fun isSunDay(year: Int, month: Int, day: Int): Boolean {
    val calendar = Calendar.getInstance()
    val date = Date(
        parseTime(
            "yyyy-MM-dd",
            "$year-$month-$day"
        )
    )
    calendar.time = date
    return calendar[Calendar.DAY_OF_WEEK] === Calendar.SUNDAY
}

/**
 * 是否是星期六
 *
 * @param year
 * @param month
 * @param day
 * @return
 */
fun isSaturDay(year: Int, month: Int, day: Int): Boolean {
    val calendar = Calendar.getInstance()
    val date = Date(
        parseTime(
            "yyyy-MM-dd",
            "$year-$month-$day"
        )
    )
    calendar.time = date
    return calendar[Calendar.DAY_OF_WEEK] === Calendar.SATURDAY
}

//两个日期间隔的天数
fun getDiffDays(
    startYear: Int, startMonth: Int, startDay: Int,
    endYear: Int, endMonth: Int, endDay: Int
): Long {
    val start =
        parseTime(
            "yyyy-MM-dd",
            "$startYear-$startMonth-$startDay"
        )
    val end =
        parseTime(
            "yyyy-MM-dd",
            "$endYear-$endMonth-$endDay"
        )
    return (end - start) / (24 * 60 * 60 * 1000) + 1
}

fun makeStatusBarTransparent(activity: Activity) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        return
    }
    val window: Window = activity.window
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val option: Int = window.getDecorView()
            .getSystemUiVisibility() or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.getDecorView().setSystemUiVisibility(option)
        window.setStatusBarColor(Color.TRANSPARENT)
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}