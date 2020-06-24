package com.rvakva.bus.home.ui.login

import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.rvakva.travel.devkit.expend.jumpTo
import androidx.fragment.app.viewModels
import com.rvakva.bus.common.extent.jumpToWebActivity
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.RegisterFragment
import com.rvakva.bus.home.ui.register.RegisterActivity
import com.rvakva.bus.home.ui.work.WorkActivity
import com.rvakva.bus.home.viewmodel.login.LoginFragmentViewModel
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.addTextChangedListenerDsl
import com.rvakva.travel.devkit.expend.getColor
import com.rvakva.travel.devkit.expend.resetData
import com.rvakva.travel.devkit.observer.request.RequestEmResultObserver
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:44
 */
class LoginFragment private constructor() : KtxFragment(R.layout.fragment_login) {

//    val loginActivityViewModel by activityViewModels<LoginActivityViewModel>()

    var isSee: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    private val loginFragmentViewModel by viewModels<LoginFragmentViewModel>()


    private fun checkPhoneAndPswAndAgreement() {
        loginTvAction.isEnabled =
            loginEtPhone.text.length == 11 && loginEtPsw.text.length > 5
//                    && loginTvAgreement.isSelected
    }

    override fun initObserver() {
        loginFragmentViewModel.loginLiveData.observe(
            this, RequestEmResultObserver(
                successBlock = {
                    requireActivity().run {
                        jumpTo<WorkActivity>()
                        finish()
                    }
                }, failBlock = {
                    resetData()
                }, fragmentManager = parentFragmentManager
            )
        )
    }


    override fun initView(view: View, savedInstanceState: Bundle?) {
        loginEtPsw.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                if (s.toString().isNotEmpty()) {
                    logonPasswordSee.visibility = View.VISIBLE
                } else {
                    logonPasswordSee.visibility = View.GONE
                }
                checkPhoneAndPswAndAgreement()
            }
        }

        loginEtPhone.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                if (s.toString().isNotEmpty()) {
                    logonPhoneClear.visibility = View.VISIBLE
                } else {
                    logonPhoneClear.visibility = View.GONE
                }
                checkPhoneAndPswAndAgreement()
            }
        }

        loginTvAction.setOnClickListener {
            loginFragmentViewModel.login(loginEtPhone.text.toString(), loginEtPsw.text.toString())
//            requireActivity().run {
//                jumpTo<WorkActivity>()
//                finish()
//            }
        }

        logonPhoneClear.setOnClickListener {
            loginEtPhone.setText("")
            logonPhoneClear.visibility = View.GONE
        }

        logonPasswordSee.setOnClickListener {
            if (!isSee) {
                isSee = true
                logonPasswordSee.setImageResource(R.drawable.home_log_in_display)
                loginEtPsw.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                isSee = false
                logonPasswordSee.setImageResource(R.drawable.home_log_in_hide)
                loginEtPsw.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        loginTvRegister.setOnClickListener {
            requireActivity().run {
                jumpTo<RegisterActivity>()
            }
        }

        loginTvAgreement.let {
            it.setOnClickListener {
                it.isSelected = !it.isSelected
                checkPhoneAndPswAndAgreement()
            }
            it.highlightColor = android.R.color.transparent.getColor()
            it.text = SpannableStringBuilder("我已同意并阅读《驿跃骑手用户协议》").apply {
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        requireActivity().jumpToWebActivity(
                            "http://eym.rvakva.com/customer.html",
                            "用户协议"
                        )
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.isUnderlineText = false
                        ds.color = R.color.blue.getColor()
                    }
                }, length - 10, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            it.movementMethod = LinkMovementMethod.getInstance()
        }

    }

    val registerFragment =
        RegisterFragment.newInstance()

    override fun initData(isFirstInit: Boolean) {
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (transit != 0) {
            if (enter) {
                return AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_in)
            } else {
                return AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left_out)
            }
        }
        return null
    }

}