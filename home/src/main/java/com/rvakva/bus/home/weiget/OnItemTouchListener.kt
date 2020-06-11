package com.rvakva.bus.home.weiget

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/10 上午10:02
 */
open interface OnItemTouchListener {
    /**
     * 移动监听
     * @param fromPosition
     * @param toPosition
     * @return
     */
    fun onMove(fromPosition: Int, toPosition: Int): Boolean

    /**
     * 滑动监听
     * @param position
     */
    fun onSwiped(position: Int)
}
