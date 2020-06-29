package com.rvakva.travel.devkit.model

import java.lang.Exception

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/29 上午11:11
 */
class DriverStatusPojo(
    /**
     * 状态类型
     * 类型1进入后台
     * 类型2进入前台
     * 类型3应用崩溃
     */
    var statusType: Int? = null,
    /**
     * 司机id
     */
    var driverId: Long? = null,
    /**
     * 系统key
     */
    var appKey: String? = null,
    /**
     * 公司id
     */
    var companyId: Long? = null,
    /**
     * 真实名字
     */
    var realName: String? = null,
    /**
     * 司机电话
     */
    var phone: String? = null,

    /**
     * 异常日志
     */
    var exceptionDetail : String? = null
)