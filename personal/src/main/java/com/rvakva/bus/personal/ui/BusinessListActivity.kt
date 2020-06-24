package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.viewmodel.BillActivityViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import kotlinx.android.synthetic.main.aty_business_list.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:业务流水
 * @Author: lch
 * @Date: 2020/6/24 15:54
 **/
@Route(path = Config.USER_BUSINESS_LIST)
class BusinessListActivity : KtxActivity(R.layout.aty_business_list) {

    val billActivityViewModel by viewModels<BillActivityViewModel>()

    override fun initTitle() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        titleBarImgBack.setOnClickListener {
            finish()
        }

        //时间选择
        titleBarTimeSelect.setOnClickListener {

        }
    }

    override fun initObserver() {
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        }
    }

    private fun requestData() {
//        billActivityViewModel.getFinance(recyclerviewBill.currentPage + 1)
    }


}