package com.rvakva.bus.home.ui.order

import android.os.Bundle
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.base.KtxActivity
import kotlinx.android.synthetic.main.activity_service_complete.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/8 下午6:23
 */
class ServiceCompleteActivity : KtxActivity(R.layout.activity_service_complete) {


    override fun initTitle() {
        completeMtb.let {
            it.centerText.text = ""
            it.leftTv.setOnClickListener {
                finish()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        completeTimeTv.text = "本次总共耗时：${intent.getStringExtra("serviceTime")}"
        completeBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }
}