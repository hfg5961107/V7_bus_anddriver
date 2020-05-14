package com.rvakva.travel.devkit.observer.request

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.rvakva.travel.devkit.event.RequestEvent
import com.rvakva.travel.devkit.event.RequestStatus
import com.rvakva.travel.devkit.expend.handleExceptionDesc
import com.rvakva.travel.devkit.expend.resetApplication
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import com.rvakva.travel.devkit.widget.ToastBar
import com.rvakva.travel.devkit.widget.ToastBox

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:19
 */
abstract class RequestBaseObserver<T> constructor(
    private val failBlock: (Exception) -> Unit,
    private val completeBlock: () -> Unit,
    private val fragmentManager: FragmentManager?,
    private val handleResult: Boolean
) : Observer<RequestEvent<T>> {

    override fun onChanged(t: RequestEvent<T>?) {
        t?.let { event ->
            if (event.isHandle) {
                return
            }
            when (event.status) {
                RequestStatus.START -> onStart(event)
                RequestStatus.SUCCESS -> {
                    onComplete(event)
                    handleSuc(event.data!!)
                }
                RequestStatus.FAIL -> {
                    onComplete(event)
                    handleFail(event)
                }
                else -> {

                }
            }
        }
    }


    private var toastBox: ToastBox? = null

    private fun onStart(t: RequestEvent<T>) {
        if (t.showToastBox && fragmentManager != null) {
            ToastBox.newInstance(t.toastBoxDesc).let {
                it.show(fragmentManager)
                toastBox = it
            }
        }
    }

    private fun handleFail(t: RequestEvent<T>) {
        t.requestException?.let {
            if (it is ApiException &&
                (it.code == ApiConstant.ERROR_CODE_TOKEN_EXPIRE ||
                        it.code == ApiConstant.ERROR_CODE_USER_OCCUPY ||
                        it.code == ApiConstant.ERROR_CODE_SYSTEM_HAS_EXPIRED)
            ) {
                it.handleExceptionDesc().let {
                    ToastBar.show(it)
                }
                resetApplication()
            } else {
                if (t.showToastBar) {
                    it.handleExceptionDesc().let {
                        ToastBar.show(it)
                    }
                }
                failBlock(it)
            }
        }
    }


    private fun onComplete(t: RequestEvent<T>) {
        if (handleResult) {
            t.isHandle = true
        }
        if (fragmentManager != null && toastBox?.isAdded == true) {
            toastBox?.dismiss()
        }
        completeBlock()
    }

    abstract fun handleSuc(t: T)

}
