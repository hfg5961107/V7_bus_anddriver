package com.rvakva.bus.common.base

import androidx.activity.viewModels
import com.google.gson.Gson
import com.rvakva.bus.common.viewmodel.PayViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxPayActivity
import com.rvakva.travel.devkit.expend.toJsonModel
import com.rvakva.travel.devkit.model.WxPayResultModel
import com.rvakva.travel.devkit.observer.request.RequestEmResultObserver
import org.json.JSONObject

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 下午1:37
 */
abstract class PayActivity(layoutId: Int) : KtxPayActivity(layoutId) {

    protected val payViewModel by viewModels<PayViewModel>()

    override fun initObserver() {
        super.initObserver()
        payViewModel.chargeLiveData.observe(
            this, RequestEmResultObserver(
                successBlock = {
                    it?.let {
                        Gson().toJson(it).let { json ->
                            JSONObject(json).let { obj ->
                                when (obj.optString("channel_name")) {
                                    Config.CHANNEL_APP_ALI -> {
                                        launchZfbPay(obj.optString("ali_app_url"))
                                    }
                                    Config.CHANNEL_APP_WECHAT -> {
                                        launchWxPay(json.toJsonModel<WxPayResultModel>())
                                    }
                                }
                            }
                        }
                    }
                }, fragmentManager = supportFragmentManager
            )
        )
    }
}