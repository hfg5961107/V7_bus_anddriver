package com.rvakva.travel.devkit.expend

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:01
 */
fun showKeyBoard() {
    getSystemService<InputMethodManager>()
        ?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun View.hideKeyBoard() {
    getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(windowToken, 0)
//        ?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun AppCompatActivity.hideKeyBoard() {
    currentFocus?.let {
        getSystemService<InputMethodManager>()
            ?.hideSoftInputFromWindow(
                it.applicationWindowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }
}

fun AppCompatActivity.dispatchHideKeyboardEvent(ev: MotionEvent?) {
    if (ev?.action == MotionEvent.ACTION_DOWN) {
        val v = currentFocus
        (currentFocus as? EditText)?.let {
            val l = intArrayOf(0, 0)
            it.getLocationInWindow(l)
            if (!(ev.x > l[0]
                        && ev.x < l[0] + it.width
                        && ev.y > l[1]
                        && ev.y < l[1] + it.height)
            ) {
                hideKeyBoard()
            }
        }

    }
}