package com.rvakva.travel.devkit.expend

import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.rvakva.travel.devkit.dsl.DrawerListenerDsl
import com.rvakva.travel.devkit.dsl.TextWatcherDsl

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:08
 */
fun TextView.setTextColorRes(res: Int) {
    this.setTextColor(res.getColor())
}

fun EditText.bindPrizeFilters(maxLength: Int = 10) {
    arrayOf(object : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {
            if (source == "." && dest?.length == 0) {
                return "0.";
            }
            if (source == "0" && dest?.length == 0) {
                return "";
            }
            if (dest?.contains(".") == true) {
                val index = dest.toString().indexOf(".");
                val mlength = dest.toString().substring(index).length;
                if (mlength == 3) {
                    return "";
                }
            }
            return source ?: "";
        }
    }, object : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {
            if (dest?.contains(".") == true) {

                if (source!! == "."){
                    return ""
                }

                var intString = dest.toString().split(".")[0]

                if (dest.length <= intString.length+2) {
                    return source ?: ""
                } else {
                    return ""
                }
            } else {
                dest?.let {
                    if (it.length < maxLength) {
                        return source ?: ""
                    } else {
                        return ""
                    }
                }
            }
            return source ?: ""
        }
    }).let {
        filters = it
    }
}


fun EditText.addTextChangedListenerDsl(block: TextWatcherDsl.() -> Unit) {
    TextWatcherDsl().let {
        it.block()
        it.addTextWatcher(this)
    }
}

fun DrawerLayout.addDrawerListenerDsl(block: DrawerListenerDsl.() -> Unit) =
    DrawerListenerDsl().let {
        it.block()
        it.addDrawerListener(this)
        it.drawerListener
    }


fun TextView.setImageResource(
    leftRes: Int? = null,
    topRes: Int? = null,
    rightRes: Int? = null,
    bottomRes: Int? = null,
    padding: Int? = null
) {
    val leftDrawable = leftRes?.getDrawable()
    val topDrawable = topRes?.getDrawable()
    val rightDrawable = rightRes?.getDrawable()
    val bottomDrawable = bottomRes?.getDrawable()

    setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable)
    padding?.let {
        compoundDrawablePadding = it
    }
}