package com.rvakva.travel.devkit.retrofit

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:34
 */
object ApiConstant {

    const val ERROR_CODE = 1
    const val ERROR_CODE_DATA_IS_EMPTY = -1

    const val COMMON_ERROR = "发生错误，请重试"

    const val REQUEST_PARAMS_ERROR = "参数错误，请重试"
    const val REQUEST_TIMEOUT_ERROR = "网络连接超时,请检查网络连接"

    const val RESPONSE_ERROR = "返回数据错误，请重试"
    const val RESPONSE_EMPTY_ERROR = "暂无数据，请重试"

    const val JSON_FORMAT_ERROR = "JSON格式化出错，请重试"

    const val ERROR_CODE_TOKEN_EXPIRE = 10007
    const val ERROR_CODE_USER_OCCUPY = 50015
    const val ERROR_CODE_SYSTEM_HAS_EXPIRED= 50024

    const val ERROR_CODE_DRIVER_STATUS_NOT_AUDIT = 50006
    const val ERROR_CODE_DRIVER_STATUS_IN_AUDIT = 50007
    const val ERROR_CODE_DRIVER_STATUS_AUDIT_REFUSE = 50008

    const val ERROR_CODE_ORDER_IS_TAKEN = 20002
    const val ERROR_CODE_ORDER_CANCELED = 20003

    const val ERROR_CODE_DRIVER_FROZEN = 50002
    const val ERROR_CODE_NOT_ENOUGH = 50003
    const val ERROR_CODE_OUT_OF_LIMIT = 50020
}