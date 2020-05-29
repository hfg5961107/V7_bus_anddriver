package com.rvakva.bus.home.viewmodel.work

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.rvakva.bus.common.XViewModel
import com.rvakva.travel.devkit.livedata.ResumeStateEventLiveData
import com.rvakva.travel.devkit.observer.EventObserver

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午9:19
 */
class WorkActivitySharedViewModel(application: Application) : AndroidViewModel(application) {

    val newScheduledCountLiveData = MediatorLiveData<Boolean>().apply {
//        addSource(assignOrderLiveData) {
//            postValue(it)
//        }
        addSource(XViewModel.newOrderLiveData, EventObserver {
            postValue(it)
        })
    }
}