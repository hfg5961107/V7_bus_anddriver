package com.rvakva.travel.devkit.observer

import androidx.lifecycle.Observer
import com.rvakva.travel.devkit.event.Event

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:18
 */
class EventObserver<T>(val block: (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(t: Event<T>) {
        if (!t.isHandle) {
            t.data?.let {
                block(it)
                t.isHandle = true
            }
        }
    }
}