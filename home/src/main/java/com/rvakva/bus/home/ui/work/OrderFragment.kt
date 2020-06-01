package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.bind
import com.rvakva.travel.devkit.expend.getPrivateValue
import kotlinx.android.synthetic.main.fragment_order.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午9:40
 */
class OrderFragment: KtxFragment(R.layout.fragment_order) {

    companion object {

        @JvmStatic
        fun newInstance() =
            OrderFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun initObserver() {

    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initTabLayout()
    }

    override fun initData(isFirstInit: Boolean) {

    }

    val fragmentList = mutableListOf(
        WorkOrderFragment.newInstance(
            OrderStatusTypeEnum.ORDER_TYPE_ING
        ),
        WorkOrderFragment.newInstance(
            OrderStatusTypeEnum.ORDER_TYPE_COMPLETE
        ),
        WorkOrderFragment.newInstance(
            OrderStatusTypeEnum.ORDER_TYPE_CANCEL
        )
    )

    private fun initTabLayout() {
        orderVp.let {
            (it.getPrivateValue(
                "mRecyclerView",
                ViewPager2::class.java
            ) as? RecyclerView)?.overScrollMode =
                View.OVER_SCROLL_NEVER

            it.adapter = object : FragmentStateAdapter(this) {

                override fun getItemCount() = fragmentList.size

                override fun createFragment(position: Int) = fragmentList[position]
            }
            it.offscreenPageLimit = 1
            mainMi.bind(mutableListOf("进行中", "已完成","已取消"), it)
        }
    }

}