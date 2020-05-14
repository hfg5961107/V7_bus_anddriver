package com.rvakva.bus.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.rvakva.bus.common.widget.AlertDialog
import com.rvakva.bus.common.widget.UpdateDialog
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.login.LoginActivity
import com.rvakva.bus.home.ui.work.WorkActivity
import com.rvakva.bus.home.viewmodel.IndexActivityViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.requestPermission
import com.rvakva.travel.devkit.observer.request.RequestEmResultObserver
import com.yanzhenjie.permission.runtime.Permission

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午1:54
 */
class IndexActivity : KtxActivity(R.layout.activity_index) {


    private val indexActivityViewModel by viewModels<IndexActivityViewModel>()

    private val REQ_CODE_PERMISSION = 1

    override fun initTitle() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initObserver() {
        indexActivityViewModel.appInfoLiveData.observe(
            this, RequestEmResultObserver(
                successBlock = {
                    it?.let {
                        if (it.appVersionCode > Config.VERSION_CODES) {
                            UpdateDialog.newInstance(it).show(supportFragmentManager)
                        } else {
                            jumpToActivity()
                        }
                    }
                }, failBlock = {
                    finish()
                },
                fragmentManager = supportFragmentManager
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            checkPermission()
        }
    }

    private fun checkPermission() {
        requestPermission(
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE
        ) {
            onGranted {
//                indexActivityViewModel.getAppInfo()
                jumpToActivity()
            }
            onDenied {
                AlertDialog.newInstance(
                    "请赋予${Permission.transformText(this@IndexActivity, it)
                        .joinToString(separator = "、")}权限",
                    rightContent = "关闭APP"
                ).show(supportFragmentManager)
            }
        }
    }

    private fun jumpToActivity() {
        if (Ktx.getInstance().userDataSource.userToken.isEmpty() || Ktx.getInstance().userDataSource.userId == 0L) {
            jumpTo<LoginActivity>()
        } else {
            jumpTo<WorkActivity>()
        }
        finish()
    }
}