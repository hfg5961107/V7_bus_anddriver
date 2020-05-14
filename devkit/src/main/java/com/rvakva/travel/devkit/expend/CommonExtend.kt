package com.rvakva.travel.devkit.expend

import android.util.Log
import androidx.core.content.ContextCompat
import com.rvakva.travel.devkit.BuildConfig
import com.rvakva.travel.devkit.Ktx
import kotlin.random.Random

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午5:32
 */
fun <T> T.loge(tag: String = "defaultTag") {
    if (BuildConfig.DEBUG) {
        Log.e(tag, this.toString())
    }
}

fun getRandomString(model: String = "0123456789", size: Int = 4): String {
    val randomString = StringBuilder()
    for (i in 0 until size) {
        model.toCharArray().let {
            randomString.append(it[Random.nextInt(it.size)])
        }
    }
    return randomString.toString()
}

fun <T> T.getPrivateValue(fieldName: String, clazz: Class<T>) =
    try {
        clazz.getDeclaredField(fieldName).let {
            it.isAccessible = true
            it.get(this)
        }
    } catch (e: Exception) {
        null
    }

fun String?.isNullOrEmptyThen(default: String) =
    if (isNullOrEmpty())
        default
    else this

inline fun <reified T> getSystemService() =
    ContextCompat.getSystemService(Ktx.getInstance().app, T::class.java)

