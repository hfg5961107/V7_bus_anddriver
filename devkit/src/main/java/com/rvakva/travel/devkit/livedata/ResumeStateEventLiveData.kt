package com.rvakva.travel.devkit.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.StateLiveData
import com.rvakva.travel.devkit.event.Event

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:09
 */
class ResumeStateEventLiveData<T> : StateLiveData<Event<T>>(Lifecycle.State.RESUMED) {

    fun postEventValue(value: T) {
        postValue(Event(value))
    }
}