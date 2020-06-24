package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.common.base.PayActivity
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.viewmodel.BillActivityViewModel
import com.rvakva.bus.personal.viewmodel.WalletActivityViewModel
import com.rvakva.bus.personal.widget.ChargeDialog
import com.rvakva.bus.personal.widget.WalletCloseDialog
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.expend.numberFormat
import com.rvakva.travel.devkit.observer.request.RequestEmResultObserver
import com.rvakva.travel.devkit.retrofit.exception.SpecialApiException
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午11:44
 */
@Route(path = Config.USER_WALLET)
class WalletActivity : PayActivity(R.layout.activity_wallet) {

    private val walletActivityViewModel by viewModels<WalletActivityViewModel>()
    private val billActivityViewModel by viewModels<BillActivityViewModel>()

    override fun initTitle() {
        walletMtb.let {
            it.centerText.text = "我的钱包"
            it.rightTv.text = "明细"
            it.leftTv.setOnClickListener {
                onBackPressed()
            }
            //明细
            it.rightTv.setOnClickListener {
                jumpTo<BillListActivity>()
            }
        }
    }

    private fun createDialog(block: (String, String) -> Unit) {
        ChargeDialog.newInstance(block).show(supportFragmentManager)
    }

    override fun initView(savedInstanceState: Bundle?) {

        //充值
        walletTvCharge.setOnClickListener {
            createDialog() { it, payType ->
                payViewModel.charge(it, payType)
            }
        }

        //申请结算
        walletTvClose.setOnClickListener {
            createCloseDialog {
                billActivityViewModel.applyClose(it)
            }
        }
    }

    private fun createCloseDialog(block: (String) -> Unit) {
        WalletCloseDialog.newInstance(block).show(supportFragmentManager)
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

        //结算回调处理
        billActivityViewModel.applyCloseLiveData.observe(
                this, RequestEmResultObserver(
                successBlock = {
                    it?.let {
                        ToastBar.show("申请结算成功")
                    }
                }, failBlock = {
            it.message?.let {
                ToastBar.show(it)
            }
        }, fragmentManager = supportFragmentManager
        )
        )

    }
}
