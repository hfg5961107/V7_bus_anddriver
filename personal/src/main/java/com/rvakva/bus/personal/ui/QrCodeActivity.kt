package com.rvakva.bus.personal.ui

import android.graphics.Bitmap
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.utils.Utils
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import kotlinx.android.synthetic.main.activity_qr_code.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:极速下单二维码
 * @Author: lch
 * @Date: 2020/7/27 15:59
 **/
@Route(path = Config.USER_QR_CODE)
class QrCodeActivity : KtxActivity(R.layout.activity_qr_code){

    override fun initTitle() {
        walletMtb.let {
            it.centerText.text = "极速下单二维码"
            it.leftTv.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        //todo 需要更换正式二维码
        val mBitmap: Bitmap = Utils.createQRCodeBitmap("https://www.baidu.com", 600, 600)
        imgCode.setImageBitmap(mBitmap)
    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }

}