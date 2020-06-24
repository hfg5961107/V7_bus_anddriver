package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.common.base.PayActivity
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.viewmodel.WalletActivityViewModel
import com.rvakva.bus.personal.widget.ChargeDialog
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.numberFormat
import com.rvakva.travel.devkit.observer.request.RequestEmResultObserver
import com.rvakva.travel.devkit.retrofit.exception.SpecialApiException
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_wallet.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午11:44
 */
@Route(path = Config.USER_WALLET)
class WalletActivity : PayActivity(R.layout.activity_wallet) {

    private val walletActivityViewModel by viewModels<WalletActivityViewModel>()

    override fun initTitle() {
        walletMtb.let {
            it.centerText.text = "我的钱包"
            it.leftTv.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun createDialog( block: (String,String) -> Unit) {
        ChargeDialog.newInstance(block).show(supportFragmentManager)

    }

    override fun initView(savedInstanceState: Bundle?) {

        walletTvCharge.setOnClickListener {
            createDialog(){ it,payType ->
                payViewModel.charge(it,payType)
            }
        }
        walletTvDetail.setOnClickListener {
            jumpTo<BillListActivity>()
        }

//        walletTvWithdraw.setOnClickListener {
//            createDialog(WalletType.CASH_OUT) {
//                walletActivityViewModel.cashOut(it)
//            }
//        }
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            getUserBalance(true)
        }
    }

    private fun getUserBalance(isFirst: Boolean) {
        walletActivityViewModel.getBalance(isFirst)
    }

    override fun paySuc() {
        ToastBar.show("支付成功")
        getUserBalance(false)
    }

    override fun payFail() {
        ToastBar.show("支付失败，请重试")
    }

    override fun initObserver() {
        super.initObserver()
        walletActivityViewModel.userConfigLiveData.observe(
            this, RequestEmResultObserver(
                successBlock = {
                    it?.let {
                        walletTvBalance.text = it.balance.numberFormat()
                    }
                }, failBlock = {
                    (it as? SpecialApiException)?.let {
                    } ?: finish()
                }, fragmentManager = supportFragmentManager
            )
        )

        Ktx.getInstance().userDataSource.userConfigLiveData.observe(this, Observer {
            walletTvDesc.text = "请保持至少${it?.balanceLimit}元余额，否则将无法接单"
        })

    }
}
