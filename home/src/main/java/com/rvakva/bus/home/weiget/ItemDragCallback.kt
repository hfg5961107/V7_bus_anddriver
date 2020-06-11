package com.rvakva.bus.home.weiget

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/10 上午11:06
 */
class ItemDragCallback(listener: OnItemTouchListener) : ItemTouchHelper.Callback() {

    val mListener: OnItemTouchListener
    private var sort = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = 0
        val dragFlags =
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        //其实只有UP、DOWN即可完成排序，加上LEFT、RIGHT只是为了滑动更飘逸
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        var fromPosition = viewHolder.adapterPosition
        var toPosition = target.adapterPosition
        return mListener.onMove(fromPosition, toPosition)
    }

    /**
     * 滑动删除
     * @return
     */
    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    fun setSort(sort: Boolean) {
        this.sort = sort
    }

    /**
     * 长按 拖拽
     * @return
     */
    override fun isLongPressDragEnabled(): Boolean {
        return sort //true 开启拖动排序，false关闭拖动排序
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mListener.onSwiped(viewHolder.adapterPosition)
    }

    init {
        mListener = listener
    }
}