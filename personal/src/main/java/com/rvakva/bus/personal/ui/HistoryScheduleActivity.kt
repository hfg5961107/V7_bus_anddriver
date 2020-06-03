package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.adapter.HistoryScheduleAdapter
import com.rvakva.bus.personal.viewmodel.PersonScheduleViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.initialize
import com.rvakva.travel.devkit.expend.onDataErrorAndException
import com.rvakva.travel.devkit.expend.onDataSuccessAndEmpty
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_history_schedule.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 上午9:59
 */
@Route(path = Config.USER_HISTORY_SCHEDULE)
class HistoryScheduleActivity
    : KtxActivity(R.layout.activity_history_schedule){

    private val personScheduleViewModel by viewModels<PersonScheduleViewModel>()

    override fun initTitle() {
        hisScheduleMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = "历史订单"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerviewHistory.initialize(
            adapter = HistoryScheduleAdapter(),
            onEmptyClickBlock = ::requestData,
            onRefreshBlock = ::requestData,
            initializeBlock = {
                setOnItemClickBlock<ScheduleDataModel> { adapter, view, position ->
                    adapter?.let {
                        it.data[position].let { data ->
                            when (view.id) {
                                R.id.pcHistoryItem,
                                R.id.pcHistoryItem -> {
//                                    jumpToOrderDetail(data)
                                    ToastBar.show("点击了列表")
                                }
                            }
                        }
                    }
                }
            },
            emptyString = "暂无历史订单"
        )
    }

    override fun initObserver() {
        personScheduleViewModel.orderListLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    recyclerviewHistory.onDataSuccessAndEmpty(it.data?.toMutableList())
                }, failBlock = {
                    recyclerviewHistory.onDataErrorAndException(it)
                }
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit){
            requestData()
        }
    }

    private fun requestData() {
        Ktx.getInstance().userDataSource.userInfoLiveData.observe(this, Observer {
            personScheduleViewModel.getOrderList(OrderStatusTypeEnum.ORDER_TYPE_COMPLETE,recyclerviewHistory.currentPage+1)
        })
    }
}