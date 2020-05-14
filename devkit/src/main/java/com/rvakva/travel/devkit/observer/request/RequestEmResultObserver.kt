package com.rvakva.travel.devkit.observer.request

import androidx.fragment.app.FragmentManager
import com.rvakva.travel.devkit.retrofit.result.EmResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:19
 */
class RequestEmResultObserver<T> constructor(
    private val successBlock: (T?) -> Unit,
    failBlock: (Exception) -> Unit = {},
    completeBlock: () -> Unit = {},
    fragmentManager: FragmentManager? = null,
    handleResult: Boolean = false

) : RequestBaseObserver<EmResult<T>>(
    failBlock,
    completeBlock,
    fragmentManager,
    handleResult
) {
    override fun handleSuc(t: EmResult<T>) {
        successBlock(t.data)
    }

}