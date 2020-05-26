package com.rvakva.travel.devkit.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.StateLiveData
import com.rvakva.travel.devkit.event.Event

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午10:01
 */
class CreateStateEventLiveData<T> : StateLiveData<Event<T>>(Lifecycle.State.CREATED) {

    fun postEventValue(value: T) {
        postValue(Event(value))
    }
}