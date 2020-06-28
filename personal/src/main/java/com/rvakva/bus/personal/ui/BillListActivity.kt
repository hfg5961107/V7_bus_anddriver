package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.adapter.BillListAdapter
import com.rvakva.bus.personal.model.BillModel
import com.rvakva.bus.personal.viewmodel.BillActivityViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.activity_bill_list.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午2:51
 */
class BillListActivity : KtxActivity(R.layout.activity_bill_list) {

    val billActivityViewModel by viewModels<BillActivityViewModel>()


    override fun initTitle() {
        billListMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = "余额明细"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerviewBill.initialize(
            adapter = BillListAdapter(),
            onEmptyClickBlock = ::requestData,
            onRefreshBlock = ::requestData,
            initializeBlock = {
                setOnItemClickBlock<BillModel> { adapter, view, position ->
                    adapter?.let {
                        it.data[position].let { data ->
                            when (view.id) {
                                R.id.myBillList -> {
                                    //跳转收入详情
                                    jumpByARouter(Config.USER_INCOME_DETAILS) {
                                        withInt("orderId", data.id)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            emptyString = "暂无余额明细"
        )
    }

    override fun initObserver() {
        billActivityViewModel.billListLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    recyclerviewBill.onDataSuccessAndEmpty(it.data?.toMutableList())
                    recyclerviewBill.setEmptyRes(R.drawable.user_icon_no_bill)
                }, failBlock = {
                    recyclerviewBill.onDataErrorAndException(it)
                }
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        }
    }


    private fun requestData() {
        billActivityViewModel.getBillList(recyclerviewBill.currentPage + 1)
    }
}