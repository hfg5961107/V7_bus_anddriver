package com.rvakva.travel.devkit.expend

import com.google.gson.JsonParseException
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import com.rvakva.travel.devkit.retrofit.exception.SpecialApiException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午3:22
 */
fun Exception.handleExceptionDesc() =
    when (this) {
        is ApiException -> {
            if (!message.isNullOrBlank()) {
                message!!
            } else {
                ApiConstant.COMMON_ERROR
            }
        }
        is SocketTimeoutException, is SocketException, is UnknownHostException ->
            ApiConstant.REQUEST_TIMEOUT_ERROR

        is JsonParseException, is JSONException, is ParseException ->
            ApiConstant.JSON_FORMAT_ERROR

        is HttpException -> {
            when (code()) {
                400 -> ApiConstant.REQUEST_PARAMS_ERROR
                500 -> ApiConstant.RESPONSE_ERROR
                else -> ApiConstant.COMMON_ERROR
            }
        }

        else -> {
            ApiConstant.COMMON_ERROR
        }
    }


suspend fun <T> handleSpecialException(
    id: Long = 1L,
    block: suspend () -> T,
    handlerErrorBlock: (Exception) -> Unit = {}
) =
    try {
        block()
    } catch (e: Exception) {
        handlerErrorBlock(e)
        (e as? ApiException)?.let {
            throw SpecialApiException(it.message, it.code, id)
        } ?: throw SpecialApiException(e.handleExceptionDesc(), -1, id)
    }