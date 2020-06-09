package com.rvakva.bus.home.ui.order.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.R
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.observer.request.RequestResultObserver

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/9 上午10:36
 */
class OrderRunFragment : KtxFragment(R.layout.fragment_order_run) {

    val orderOperationViewModel by viewModels<OrderOperationViewModel>()


    companion object {
        @JvmStatic
        fun newInstance(orderDriverId: Long) =
            OrderRunFragment().apply {
                arguments = bundleOf(
                    "orderDriverId" to orderDriverId
                )
            }
    }

    override fun initObserver() {
        orderOperationViewModel.scheduleDetailLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let { model ->
                        showData(model)
                    }
                },
                failBlock = {

                }
            )
        )
    }

    fun showData(model: ScheduleDataModel) {
        model.order.forEach {

        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        arguments?.getLong("orderDriverId",0)?.let{
            orderOperationViewModel.qureyScheduleById(it)
        }

    }

    override fun initData(isFirstInit: Boolean) {

    }


}