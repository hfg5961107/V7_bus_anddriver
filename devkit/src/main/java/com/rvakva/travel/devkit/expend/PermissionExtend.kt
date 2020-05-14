package com.rvakva.travel.devkit.expend

import android.content.Context
import androidx.annotation.NonNull
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.PermissionDef
import com.yanzhenjie.permission.runtime.PermissionRequest

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:11
 */
fun Context.requestPermission(
    @NonNull @PermissionDef vararg permissions: String,
    block: PermissionRequest.() -> PermissionRequest
) {
    AndPermission.with(this)
        .runtime()
        .permission(permissions)
        .block()
        .start()
}