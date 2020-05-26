package com.rvakva.travel.devkit.livedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.StateLiveData

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午10:46
 */
class CreateStateLiveData<T> : StateLiveData<T>(Lifecycle.State.CREATED)