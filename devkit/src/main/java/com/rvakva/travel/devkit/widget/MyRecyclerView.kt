package com.rvakva.travel.devkit.widget

import android.content.Context
import android.util.AttributeSet
import com.rvakva.travel.devkit.R
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.sherloki.simpleadapter.widget.IBaseRecyclerView3

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午10:38
 */
class MyRecyclerView : IBaseRecyclerView3 {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.my_recycler_view

    override fun getRecyclerViewId() = R.id.myRecyclerViewRv

    override fun getRefreshViewId() = R.id.myRecyclerViewSrl

    override fun initLoadMoreView() = MyLoadMoreView()

    override fun initEmptyView() = MyEmptyView(context)

    override val defaultEmptyText: String
        get() = ApiConstant.RESPONSE_EMPTY_ERROR

    override val defaultErrorText: String
        get() = ApiConstant.COMMON_ERROR

    var emptyRes : Int? = null
}