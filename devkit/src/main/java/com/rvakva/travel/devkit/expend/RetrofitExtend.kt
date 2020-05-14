package com.rvakva.travel.devkit.expend

import android.util.Log
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import com.rvakva.travel.devkit.retrofit.result.BaseResult

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:09
 */
fun <T : BaseResult> T?.requestMap(
    errorCode: Int = ApiConstant.ERROR_CODE
) =
    this?.let {
        if (it.code != errorCode) {
            throw ApiException(
                it.msg ?: ApiConstant.COMMON_ERROR,
                it.code
            )
        } else {
            it
        }
    } ?: throw ApiException(
        ApiConstant.RESPONSE_EMPTY_ERROR,
        ApiConstant.ERROR_CODE_DATA_IS_EMPTY
    )