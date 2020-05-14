package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.common.extent.jumpToWebActivity
import com.rvakva.bus.common.widget.AlertDialog
import com.rvakva.bus.common.widget.OnAlertDialogClickListener
import com.rvakva.bus.common.widget.UpdateDialog
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.viewmodel.SystemSettingViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.callPhone
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.resetApplication
import com.rvakva.travel.devkit.observer.request.RequestEmResultObserver
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_system_set.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午11:55
 */
@Route(path = Config.USER_SYSTEM_SETTING)
class SystemSetActivity : KtxActivity(R.layout.activity_system_set), OnAlertDialogClickListener {

    private val systemSettingViewModel by viewModels<SystemSettingViewModel>()

    override fun initTitle() {
        systemSettingMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = "系统设置"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        systemSettingTvExit.setOnClickListener {
            AlertDialog.newInstance(
                "您确定要退出登录吗？",
                rightContent = "取消"
            ).show(supportFragmentManager)
        }

        systemSettingTvUserAssignment.setOnClickListener {
            jumpToWebActivity(
                "http://eym.rvakva.com/customer.html",
                "用户协议"
            )
        }

        systemSettingTvPrivacy.setOnClickListener {
            jumpToWebActivity("http://eym.rvakva.com/privacy.html", "隐私政策")
        }

        systemSettingTvRemindSetting.setOnClickListener {
//            jumpTo<RemindSettingActivity>()
        }

        systemSettingTvCurrentVersion.text = Config.VERSION_NAME

        systemSettingLlContact.setOnClickListener {
            if (!systemSettingTvPhone.text.isNullOrBlank()) {
                callPhone(systemSettingTvPhone.text.toString())
            }
        }
        systemSettingTvUpdateCheck.setOnClickListener {
            systemSettingViewModel.getAppInfo()
        }
    }

    override fun initObserver() {
        systemSettingViewModel.appInfoLiveData.observe(
            this, RequestEmResultObserver(
                successBlock = {
                    it?.let {
                        if (it.appVersionCode > Config.VERSION_CODES) {
                            UpdateDialog.newInstance(it).show(supportFragmentManager)
                        } else {
                            ToastBar.show("当前已是最新版本")
                        }
                    }
                },
                fragmentManager = supportFragmentManager
            )
        )
        Ktx.getInstance().userDataSource.userConfigLiveData.observe(this, Observer {
            systemSettingTvPhone.text = it.driverHelpPhone
        })
    }

    override fun initData(isFirstInit: Boolean) {
    }

    override fun onAlertDialogLeftClick(code: Int) {
        resetApplication()
    }

    override fun onAlertDialogRightClick(code: Int) {
    }
}