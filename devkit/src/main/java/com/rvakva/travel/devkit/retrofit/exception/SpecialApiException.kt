package com.rvakva.travel.devkit.retrofit.exception

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午4:40
 */
class SpecialApiException(msg: String?, code: Int, val id: Long) : ApiException(msg, code)