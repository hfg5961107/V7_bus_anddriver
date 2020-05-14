package com.rvakva.travel.devkit.retrofit.exception

import java.lang.Exception

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:36
 */
open class ApiException constructor(msg:String? = "",val code : Int) : Exception(msg) {

    override fun toString(): String {
        return "ApiException(code=$code msg=${super.message})"
    }
}