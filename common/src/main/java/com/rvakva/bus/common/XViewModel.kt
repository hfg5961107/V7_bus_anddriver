package com.rvakva.bus.common

import com.rvakva.travel.devkit.livedata.ResumeStateEventLiveData

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午9:25
 */
object XViewModel {

    val newOrderLiveData = ResumeStateEventLiveData<Boolean>()
    val newDetailsOrderLiveData = ResumeStateEventLiveData<Boolean>()


}