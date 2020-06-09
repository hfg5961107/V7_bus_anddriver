package com.rvakva.bus.home.ui.order.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.work.OrderFragment
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.observer.request.RequestResultObserver

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/9 上午9:30
 */
class OrderSortFragment : KtxFragment(R.layout.fragment_order_sort) {


    val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    companion object {
        @JvmStatic
        fun newInstance(orderDriverId: Long) =
            OrderSortFragment().apply {
                arguments = bundleOf(
                    "orderDriverId" to orderDriverId
                )
            }
    }

    override fun initObserver() {
        orderOperationViewModel.orderLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let { list->
                        showData(list)
                    }
                },
                failBlock = {

                }
            )
        )
    }

    fun showData(list: List<PassengerModel>){
        if (checkStatus(list)){

        }else{

        }
    }

    /**
     * 判断上车还是下车
     */
    fun checkStatus(list: List<PassengerModel>): Boolean {
        list.forEach {
            if (it.status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                return false
            }
        }
        return true
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        arguments?.getLong("orderDriverId", 0)?.let {
            orderOperationViewModel.qureyOrderList(it)
        }
    }

    override fun initData(isFirstInit: Boolean) {

    }
}