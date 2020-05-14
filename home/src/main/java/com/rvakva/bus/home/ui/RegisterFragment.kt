package com.rvakva.bus.home.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.work.WorkActivity
import com.rvakva.bus.home.viewmodel.RegisterFragmentViewModel
import com.rvakva.bus.home.viewmodel.login.LoginActivityViewModel
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.addTextChangedListenerDsl
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.resetData
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:28
 */
class RegisterFragment private constructor() : KtxFragment(R.layout.fragment_register) {

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }

    override fun initObserver() {
        registerFragmentViewModel.countDownLiveData.observe(viewLifecycleOwner, Observer { count ->
            registerTvGetIdentity.let {
                if (count > 0) {
                    it.text = "$count s"
                } else {
                    it.text = "获取验证码"
                    registerFragmentViewModel.isCountDownFinish = true
                    setGetIdentityEnable()
                }
            }
        })
        registerFragmentViewModel.sendSmsCodeLiveData.observe(
            viewLifecycleOwner,
            RequestResultObserver(
                successBlock = {
                    registerTvGetIdentity.isEnabled = false
                    registerFragmentViewModel.countDownTimer()
                },
                fragmentManager = parentFragmentManager, handleResult = true
            )
        )
        registerFragmentViewModel.registerLiveData.observe(
            viewLifecycleOwner,
            RequestResultObserver(
                successBlock = {
                    requireActivity().run {
                        jumpTo<WorkActivity>()
                        finish()
                    }
                }, failBlock = {
                    resetData()
                }, fragmentManager = parentFragmentManager, handleResult = true
            )
        )
    }

    private val registerFragmentViewModel by activityViewModels<RegisterFragmentViewModel>()
    private val loginActivityViewModel by activityViewModels<LoginActivityViewModel>()

    private fun setActionEnable() {
        registerTvAction.isEnabled =
            registerEtPhone.text?.length == 11 &&
                    registerEtPassword.text?.length ?: 0 > 5 &&
                    registerEtSmsCode.text?.length == 4 &&
                    registerEtInvite.text?.length == 4
    }

    private fun setGetIdentityEnable() {
        if (registerFragmentViewModel.isCountDownFinish) {
            registerTvGetIdentity.isEnabled =
                registerEtPhone.text?.length == 11 && registerEtInvite.text?.length == 4
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        registerTvBack.setOnClickListener {
            loginActivityViewModel.loginTitleLiveData.postValue("欢迎登录")
            parentFragmentManager.popBackStack()
        }

        registerEtPhone.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                setGetIdentityEnable()
                setActionEnable()
            }
        }

        registerEtPassword.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                setActionEnable()
            }
        }

        registerEtSmsCode.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                setActionEnable()
            }
        }
        registerEtInvite.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                setActionEnable()
                setGetIdentityEnable()
            }
        }

        registerTvGetIdentity.setOnClickListener {
            registerFragmentViewModel.sendSmsCode(
                registerEtPhone.text.toString(), registerEtInvite.text.toString()
            )
        }

        registerTvAction.setOnClickListener {
            registerFragmentViewModel.register(
                registerEtPhone.text.toString(),
                registerEtPassword.text.toString(),
                registerEtSmsCode.text.toString(),
                registerEtInvite.text.toString()
            )
        }
    }

    override fun initData(isFirstInit: Boolean) {
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            return AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_in)
        } else {
            return AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_out)
        }
    }

}