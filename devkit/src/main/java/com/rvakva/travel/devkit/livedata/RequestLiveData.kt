package com.rvakva.travel.devkit.livedata

import androidx.lifecycle.MutableLiveData
import com.rvakva.travel.devkit.event.RequestEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:52
 */
class RequestLiveData<T> : ReadOnlyProperty<Any, MutableLiveData<RequestEvent<T>>> {

    private val liveData by lazy {
        MutableLiveData<RequestEvent<T>>()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>) = liveData

}