package com.rvakva.bus.common.extent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午6:06
 */
suspend fun <T> MutableList<String>.uploadTo(
    coroutineScope: CoroutineScope,
    block: suspend (String) -> T
): MutableList<T> {
    val deferredList = mutableListOf<Deferred<T>>()
    forEach {
        deferredList.add(coroutineScope.async {
            block(it)
        })
    }
    val resultList = mutableListOf<T>()
    deferredList.forEach {
        resultList.add(it.await())
    }
    return resultList
}