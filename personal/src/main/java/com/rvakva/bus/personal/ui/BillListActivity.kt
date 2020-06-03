package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.adapter.BillListAdapter
import com.rvakva.bus.personal.adapter.HistoryScheduleAdapter
import com.rvakva.bus.personal.model.BillModel
import com.rvakva.bus.personal.viewmodel.BillActivityViewModel
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.initialize
import com.rvakva.travel.devkit.expend.onDataErrorAndException
import com.rvakva.travel.devkit.expend.onDataSuccessAndEmpty
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_bill_list.*
import kotlinx.android.synthetic.main.activity_history_schedule.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午2:51
 */
class BillListActivity : KtxActivity(R.layout.activity_bill_list){

    val billActivityViewModel by viewModels<BillActivityViewModel>()

    override fun initTitle() {
        billListMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = "钱包明细"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerviewBill.initialize(
            adapter = BillListAdapter(),
            onEmptyClickBlock = ::requestData,
            onRefreshBlock = ::requestData,
            emptyString = "暂无明细列表"
        )
    }

    override fun initObserver() {
        billActivityViewModel.billListLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    recyclerviewBill.onDataSuccessAndEmpty(it.data?.toMutableList())
                }, failBlock = {
                    recyclerviewBill.onDataErrorAndException(it)
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
//        Ktx.getInstance().userDataSource.userInfoLiveData.observe(this, Observer {
            billActivityViewModel.getBillList(recyclerviewBill.currentPage+1)
//        })
    }
}