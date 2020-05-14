package com.rvakva.travel.devkit.model

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:41
 */
class WxPayResultModel(
    var wx_app_id: String? = null,
    var wx_mch_id: String? = null,
    var wx_pre_id: String? = null,
    var wx_app_nonce: String? = null,
    var wx_app_ts: String? = null,
    var wx_app_pkg: String? = null,
    var wx_app_sign: String? = null
) : IModel