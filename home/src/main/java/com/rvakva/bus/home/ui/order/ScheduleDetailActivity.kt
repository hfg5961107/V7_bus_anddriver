package com.rvakva.bus.home.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.adapter.OrderPassengerAdapter
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_schedule_detail.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午4:36
 */
@Route(path = Config.HOME_SCHEDULE_DETAIL)
class ScheduleDetailActivity : KtxActivity(R.layout.activity_schedule_detail){

    private val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    var scheduleId: Long = -1

    lateinit var adapter : OrderPassengerAdapter


    override fun initTitle() {
        detailMtb.let {
            it.leftTv.setOnClickListener { finish() }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        scheduleId = intent?.getLongExtra(Config.SCHEDULE_ID_KEY, -1) ?: -1
        if (scheduleId == -1L) {
            ToastBar.show(ApiConstant.COMMON_ERROR)
            finish()
        }

        adapter = OrderPassengerAdapter(this)
        val layoutManager = object : LinearLayoutManager(this){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerviewPassenger.adapter = adapter
        recyclerviewPassenger.layoutManager = layoutManager;

    }

    override fun initObserver() {
        orderOperationViewModel.scheduleDetailLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let {
                        adapter.setData(it.order,it.lineType)
                    } ?: ToastBar.show("查询失败")
                },
                failBlock = {
                    ToastBar.show("订单信息错误")
                    finish()
                }
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit){
            requestScheduleData()
        }
    }

    private fun requestScheduleData(){
        orderOperationViewModel.qureyScheduleById(scheduleId)
    }


}