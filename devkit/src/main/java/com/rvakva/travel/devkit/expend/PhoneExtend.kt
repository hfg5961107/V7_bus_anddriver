package com.rvakva.travel.devkit.expend

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.widget.ToastBar

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:33
 */
fun Context.callPhone(phoneNumber: String?) {
    phoneNumber?.let {
        if (!it.isBlank()) {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it")))
        } else {
            ToastBar.show(ApiConstant.COMMON_ERROR)
        }
    }
}
