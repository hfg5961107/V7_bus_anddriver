package com.rvakva.travel.devkit.event

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:11
 */
open class Event<T>(var data: T?) {
    var isHandle = false
}