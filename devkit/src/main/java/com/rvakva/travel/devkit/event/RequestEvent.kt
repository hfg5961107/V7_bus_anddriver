package com.rvakva.travel.devkit.event

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:17
 */
enum class RequestStatus {
    START, SUCCESS, FAIL,
}

class RequestEvent<T> private constructor(
    var status: RequestStatus? = null,
    var requestException: Exception? = null,
    var showToastBox: Boolean = true,
    var toastBoxDesc: String = "加载中...",
    var showToastBar: Boolean = true,
    data: T? = null
) : Event<T>(data) {

    companion object {

        fun <T> createStart(needShowToastBox: Boolean = true, toastBoxDesc: String = "加载中...") =
            RequestEvent<T>(
                status = RequestStatus.START,
                showToastBox = needShowToastBox,
                toastBoxDesc = toastBoxDesc
            )

        fun <T> createSuccess(result: T) =
            RequestEvent<T>(
                status = RequestStatus.SUCCESS,
                data = result
            )

        fun <T> createFail(
            exception: Exception?,
            showToastBar: Boolean = true
        ) =
            RequestEvent<T>(
                status = RequestStatus.FAIL,
                showToastBar = showToastBar,
                requestException = exception
            )

    }
}