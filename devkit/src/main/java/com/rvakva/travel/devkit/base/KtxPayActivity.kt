package com.rvakva.travel.devkit.base

import android.os.Handler
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.model.WxPayResultModel
import com.rvakva.travel.devkit.model.ZFBPayResultModel
import com.rvakva.travel.devkit.observer.EventObserver
import com.tencent.mm.opensdk.modelpay.PayReq
import org.json.JSONException
import kotlin.concurrent.thread

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午5:37
 */
abstract class KtxPayActivity(layoutId : Int) : KtxActivity(layoutId){

    override fun initObserver() {
        KtxViewModel.payStatusLiveData.observe(this,
            EventObserver {
                if (it) {
                    paySuc()
                } else {
                    payFail()
                }
            })
    }

    fun launchWxPay(wxPay: WxPayResultModel?) {
        try {
            wxPay?.let {
                Ktx.getInstance().weChatApi.sendReq(
                    PayReq().apply {
                        appId = it.wx_app_id
                        partnerId = it.wx_mch_id
                        prepayId = it.wx_pre_id
                        nonceStr = it.wx_app_nonce
                        timeStamp = it.wx_app_ts
                        packageValue = it.wx_app_pkg
                        sign = it.wx_app_sign
                        extData = "appData"
                    })
            }
        } catch (e: JSONException) {
            e.fillInStackTrace()
        }
    }

    fun launchZfbPay(jsonString: String) {
        thread {
            PayTask(this).let {
                handler.obtainMessage(ZFB_PAY_FLAG, it.payV2(jsonString, true)).sendToTarget()
            }
        }
    }

    private val handler = Handler(Handler.Callback {

        (it.obj as? Map<String, String>)?.let {
            ZFBPayResultModel(it).apply {
                KtxViewModel.payStatusLiveData.postEventValue(
                    TextUtils.equals(
                        resultStatus,
                        ZFB_PAY_SUCCESS_FLAG
                    )
                )
            }
        }
        true
    })

    private val ZFB_PAY_FLAG = 1
    private val ZFB_PAY_SUCCESS_FLAG = "9000"

    abstract fun paySuc()
    abstract fun payFail()
}