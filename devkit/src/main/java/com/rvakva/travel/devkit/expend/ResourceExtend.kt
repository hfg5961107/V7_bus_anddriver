package com.rvakva.travel.devkit.expend

import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.rvakva.travel.devkit.Ktx

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:38
 */

inline fun <reified T> Int.dpToPx(): T {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).let {
        when (T::class) {
            Int::class -> it.toInt()
            Float::class -> it
            else -> throw IllegalStateException("unsupported Type")
        } as T
    }
}

fun Int.getDrawable() =
    ContextCompat.getDrawable(Ktx.getInstance().app, this)?.apply {
        setBounds(0, 0, minimumWidth, minimumHeight)
    }

fun Int.getColor() = ContextCompat.getColor(Ktx.getInstance().app, this)

fun Int.getString() = Ktx.getInstance().app.getString(this)