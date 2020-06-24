package com.rvakva.bus.personal.ui

import android.os.Bundle
import com.rvakva.bus.personal.R
import com.rvakva.travel.devkit.base.KtxActivity
import kotlinx.android.synthetic.main.activity_user_apply.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 上午9:17
 */
class UserApplyActivity : KtxActivity(R.layout.activity_user_apply){


    override fun initTitle() {
        applyMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = "账户认证"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }

}