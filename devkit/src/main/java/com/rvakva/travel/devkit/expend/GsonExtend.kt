package com.rvakva.travel.devkit.expend

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:50
 */
inline fun <reified T> String.toJsonModel() = Gson().fromJson(this, T::class.java)

inline fun <reified T> String.toTypeTokenJsonModel() =
    Gson().fromJson(this, object : TypeToken<T>() {}.type) as? T