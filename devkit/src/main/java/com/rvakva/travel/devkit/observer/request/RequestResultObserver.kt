package com.rvakva.travel.devkit.observer.request

import androidx.fragment.app.FragmentManager

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:19
 */
class RequestResultObserver<T> constructor(
    private val successBlock: (T) -> Unit,
    failBlock: (Exception) -> Unit = {},
    completeBlock: () -> Unit = {},
    fragmentManager: FragmentManager? = null,
    handleResult: Boolean = false
) : RequestBaseObserver<T>(
    failBlock,
    completeBlock,
    fragmentManager,
    handleResult
) {

    override fun handleSuc(t: T) {
        successBlock(t)
    }
}