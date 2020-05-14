package com.rvakva.travel.devkit.model

import android.text.TextUtils

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:41
 */
class ZFBPayResultModel(rawResult: Map<String, String>?) {
    var resultStatus: String? = null
    var result: String? = null
    var memo: String? = null

    override fun toString(): String {
        return ("resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}")
    }

    init {
        rawResult?.let {
            for (key in it.keys) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = it[key]
                } else if (TextUtils.equals(key, "result")) {
                    result = it[key]
                } else if (TextUtils.equals(key, "memo")) {
                    memo = it[key]
                }
            }
        }

    }
}