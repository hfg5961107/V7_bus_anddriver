package com.rvakva.bus.personal.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.col.n3.it
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.utils.Utils
import com.rvakva.bus.personal.viewmodel.QrCodeActivityViewModel
import com.rvakva.bus.personal.viewmodel.WalletActivityViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.activity_qr_code.*
import kotlinx.android.synthetic.main.activity_qr_code.walletMtb
import kotlinx.android.synthetic.main.activity_wallet.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:极速下单二维码
 * @Author: lch
 * @Date: 2020/7/27 15:59
 **/
@Route(path = Config.USER_QR_CODE)
class QrCodeActivity : KtxActivity(R.layout.activity_qr_code){

    var vehicleId:Long=0L

    private val qrCodeActivityViewModel by viewModels<QrCodeActivityViewModel>()

    override fun initTitle() {
        walletMtb.let {
            it.centerText.text = "极速下单二维码"
            it.leftTv.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(isFirstInit: Boolean) {
        Ktx.getInstance().userDataSource.userInfoLiveData.observe(this, Observer {
            vehicleId=it.vehicleId
        })
        getCode(vehicleId)
    }

    private fun getCode(vehicleId:Long) {
        qrCodeActivityViewModel.getCode(vehicleId)
    }

    override fun initObserver() {
        //结算回调处理
        qrCodeActivityViewModel.getCodeLiveData.observe(
                this, RequestResultObserver(
                successBlock = {
                    val mBitmap: Bitmap = Utils.createQRCodeBitmap(it.data, 600, 600)
                    imgCode.setImageBitmap(mBitmap)
                }
        )
        )
    }

}