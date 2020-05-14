package com.rvakva.travel.devkit.expend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rvakva.travel.devkit.event.RequestEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:58
 */

fun <T> ViewModel.launchRequest(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    requestLiveData: MutableLiveData<RequestEvent<T>>? = null,
    block: suspend (CoroutineScope) -> T,
    delayTimeMillis: Long = 0,
    showToastBox: Boolean = true,
    showToastBar: Boolean = true,
    toastBoxDesc: String = "加载中..."
) =
    viewModelScope.launch(context, start) {
        if (delayTimeMillis > 0) {
            delay(delayTimeMillis)
        }
        try {
            requestLiveData?.postValue(RequestEvent.createStart(showToastBox, toastBoxDesc))
            withContext(Dispatchers.IO) {
                block(this)
            }.let {
                requestLiveData?.postValue(
                    RequestEvent.createSuccess(it)
                )
            }
        } catch (e: Exception) {
            requestLiveData?.postValue(RequestEvent.createFail(e, showToastBar))
        }
    }

fun ViewModel.launchRepeat(
    delayTimeMillis: Long = 0,
    times: Int = Int.MAX_VALUE,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend (Int) -> Unit
) =
    viewModelScope.launch(context, start) {
        repeat(times) { index ->
            try {
                block(index)
            } catch (e: Exception) {
                e.fillInStackTrace()
                e.loge()
            }
            if (delayTimeMillis > 0) {
                delay(delayTimeMillis)
            }
        }
    }


fun <T> ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend () -> T
) {
    viewModelScope.launch(context, start) {
        try {
            block()
        } catch (e: Exception) {
            e.fillInStackTrace()
            e.loge()
//            ToastBar.show(e.handleExceptionDesc())
        }
    }
}