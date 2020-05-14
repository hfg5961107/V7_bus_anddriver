package com.rvakva.travel.devkit.widget

import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rvakva.travel.devkit.R
import com.sherloki.simpleadapter.widget.IBaseLoadMoreView

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午10:39
 */
class MyLoadMoreView : IBaseLoadMoreView() {

    override fun convert(holder: BaseViewHolder, position: Int, loadMoreStatus: LoadMoreStatus) {
        super.convert(holder, position, loadMoreStatus)
        holder.setText(getLoadFailViewId(), errorText)
    }

    override fun getLayoutId() = R.layout.my_load_more_view

    override fun getLoadCompleteViewId() = R.id.loadMoreSp

    override fun getLoadingViewId() = R.id.loadMorePbLoading

    override fun getLoadEndViewId() = R.id.loadMoreTvFinish

    override fun getLoadFailViewId() = R.id.loadMoreTvError
}