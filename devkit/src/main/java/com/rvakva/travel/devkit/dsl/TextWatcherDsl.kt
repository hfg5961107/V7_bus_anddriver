package com.rvakva.travel.devkit.dsl

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/11 下午2:19
 */
class TextWatcherDsl {

    private var afterTextChangedMethod: ((Editable?) -> Unit)? = null
    private var beforeTextChangedMethod: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var onTextChangedMethod: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    fun afterTextChanged(method: (s: Editable?) -> Unit) {
        afterTextChangedMethod = method
    }

    fun beforeTextChanged(method: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit) {
        beforeTextChangedMethod = method
    }

    fun onTextChanged(method: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
        onTextChangedMethod = method
    }

    fun addTextWatcher(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChangedMethod?.invoke(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeTextChangedMethod?.invoke(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChangedMethod?.invoke(s, start, before, count)
            }
        })
    }
}