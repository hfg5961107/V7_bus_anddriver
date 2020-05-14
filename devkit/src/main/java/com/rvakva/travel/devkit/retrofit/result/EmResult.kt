package com.rvakva.travel.devkit.retrofit.result

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:58
 */
class EmResult<T>(
    var data: T? = null
) : BaseResult() {
    override fun toString(): String {
        return "EmResult(data=$data)" + super.toString()
    }
}