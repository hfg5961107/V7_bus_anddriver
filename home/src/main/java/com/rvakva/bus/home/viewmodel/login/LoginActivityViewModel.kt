package com.rvakva.bus.home.viewmodel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:42
 */
class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

    val loginTitleLiveData= MutableLiveData<String>()

}