package com.rvakva.travel.devkit

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午2:52
 */
object Config{
    //value
    const val HOST_UP_PIC = "http://up-z2.qiniu.com"
    const val CHANNEL_APP_ALI = "CHANNEL_APP_ALI"
    const val CHANNEL_APP_WECHAT = "CHANNEL_APP_WECHAT"
    const val ORDER_TYPE_POOL = 100001
    const val ORDER_TYPE_ASSIGN = 100002
    const val ORDER_TYPE_ING = 100003

    var WECHAT_ID: String? = null
    var BASE_URL = ""
    var IMAGE_SERVER = ""

    var VERSION_NAME = ""
    var VERSION_CODES = 0

    var LOGIN_NAME = ""
    var IS_FOREGROUND = false

    //pattern
    const val PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"
    const val PATTERN_MM_DD_HH_MM = "MM-dd HH:mm"
    const val PATTERN_YYYY_MM_DD = "yyyy-MM-dd"
    const val PATTERN_NUMBER_BASE = "0.00"

    //key
    const val USER_TOKEN_KEY = "USER_TOKEN_KEY"
    const val USER_ID_KEY = "USER_ID_KEY"
    const val USER_APP_KEY_KEY = "USER_APP_KEY_KEY"
    const val USER_NEED_VOICE_NOTIFY_KEY = "USER_NEED_VOICE_NOTIFY_KEY"
    const val ORDER_ID_KEY = "ORDER_ID_KEY"

    //path
    const val USER_ORDER_MANAGE = "/user/orderManage/"
    const val USER_WALLET = "/personal/wallet/"
    const val USER_ORDER_DETAIL = "/user/order/detail"
    const val USER_SYSTEM_SETTING = "/personal/system/set"
    const val USER_ORDER_SETTING = "/user/order/setting"
    const val USER_CENTER = "/user/center/"
    const val USER_IDENTITY = "/user/identity/"

    const val APP_SPLASH = "/app/splash/"
}