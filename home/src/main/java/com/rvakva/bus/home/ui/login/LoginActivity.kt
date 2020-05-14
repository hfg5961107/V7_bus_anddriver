package com.rvakva.bus.home.ui.login

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.rvakva.bus.home.R
import com.rvakva.bus.home.viewmodel.login.LoginActivityViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.addFragment
import com.rvakva.travel.devkit.expend.dispatchHideKeyboardEvent
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:35
 */
class LoginActivity  : KtxActivity(R.layout.activity_login) {

    val loginActivityViewModel by viewModels<LoginActivityViewModel>()

    override fun initTitle() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragment(LoginFragment.newInstance(), R.id.loginFl)
    }

    override fun initObserver() {
        loginActivityViewModel.loginTitleLiveData.observe(this, Observer {

        })
    }

    override fun initData(isFirstInit: Boolean) {
        Config.LOGIN_NAME = this::class.java.name
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        dispatchHideKeyboardEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        loginActivityViewModel.loginTitleLiveData.postValue("欢迎登录")
        super.onBackPressed()
    }

}