package com.rvakva.travel.devkit.expend

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import com.rvakva.travel.devkit.widget.MyRecyclerView
import kotlinx.android.synthetic.main.my_empty_view.view.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午6:19
 */
fun <T> MyRecyclerView.initialize(
    adapter: BaseQuickAdapter<T, BaseViewHolder>,
    onEmptyClickBlock: (() -> Unit)? = null,
    onRefreshBlock: (() -> Unit)? = null,
    onLoadMoreBlock: (() -> Unit)? = null,
    initializeBlock: MyRecyclerView.() -> Unit = {},
    emptyString: String? = null
) =
    let {
        it.newBuilder(adapter)
        it.onEmptyClickBlock = onEmptyClickBlock
        it.onRefreshBlock = onRefreshBlock
        it.onLoadMoreBlock = onLoadMoreBlock
        it.initializeBlock()
        it.build(emptyString)
    }


fun <T> MyRecyclerView.onDataSuccessAndEmpty(data: MutableList<T>?, code: Int = 0) {
    setEmptyAndErrorCode(code)
    onDataSuccess(data)
}

fun MyRecyclerView.onDataErrorAndException(e: Exception) {
    setErrorText(e.handleExceptionDesc())
    setEmptyAndErrorCode((e as? ApiException)?.code ?: 0)
    onDataError()
}

fun <T> MyRecyclerView.onPageDataSuccessAndEmpty(
    data: MutableList<T>?,
    isLastPage: Boolean,
    gone: Boolean,
    code: Int = 0
) {
    setEmptyAndErrorCode(code)
    onPageDataSuccess(data, isLastPage, gone)
}

fun MyRecyclerView.onPageDataErrorAndException(e: Exception) {
    setErrorText(e.handleExceptionDesc())
    setEmptyAndErrorCode((e as? ApiException)?.code ?: 0)
    onPageDataError()
}

fun MyRecyclerView.setEmptyRes(res: Int?) {
    myEmptyViewTvError.setImageResource(topRes = res)
}