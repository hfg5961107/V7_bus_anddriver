package com.rvakva.bus.home.ui.register

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.work.WorkActivity
import com.rvakva.bus.home.viewmodel.register.RegisterActivityViewModel
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.addTextChangedListenerDsl
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.expend.resetData
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.aty_register.*
import kotlinx.android.synthetic.main.aty_register.registerEtPhone
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:司机注册
 * @Author: lch
 * @Date: 2020/6/22 16:50
 **/
class RegisterActivity : KtxActivity(R.layout.aty_register) {

    var isSee : Boolean = false//是否明文显示密码

    var mRandom:String?=null//随机数

    private val registerActivityViewModel by viewModels<RegisterActivityViewModel>()

    override fun initTitle() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        registerEtPhone.addTextChangedListenerDsl { onTextChanged { s, start, before, count ->
            if (s.toString().isNotEmpty()){
                registerImgX.visibility = View.VISIBLE
            }else{
                registerImgX.visibility = View.GONE
            }
            checkInpont()
        } }
        registerEtCode.addTextChangedListenerDsl { onTextChanged { s, start, before, count ->
            checkInpont()
        } }
        registerEtPwd.addTextChangedListenerDsl { onTextChanged { s, start, before, count ->
            if (s.toString().isNotEmpty()){
                registerImgDisplay.visibility = View.VISIBLE
            }else{
                registerImgDisplay.visibility = View.GONE
            }
            checkInpont()
        } }

        //清空手机号
        registerImgX.setOnClickListener {
            registerEtPhone.setText("")
            registerImgDisplay.visibility = View.GONE
        }

        //获取验证码
        registerTextGetCode.setOnClickListener {

            if (! isMobileNO(registerEtPhone.text.toString())){
                Toast.makeText(this,"请输入正确格式的手机号",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mRandom=getRandom()
            registerActivityViewModel.sendSmsCode(registerEtPhone.text.toString(), mRandom!!)
        }

        //密码显示或隐藏
        registerImgDisplay.setOnClickListener {
            if (!isSee){
                isSee = true
                registerImgDisplay.setImageResource(R.drawable.home_register_display)
                registerEtPwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }else{
                isSee = false
                registerImgDisplay.setImageResource(R.drawable.home_register_hide)
                registerEtPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        //注册
        registerTextRegister.setOnClickListener{
            if (! isMobileNO(registerEtPhone.text.toString())){
                Toast.makeText(this,"请输入正确格式的手机号",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerActivityViewModel.register(
                registerEtPhone.text.toString(),
                registerEtPwd.text.toString(),
                registerEtCode.text.toString(),
                mRandom!!
            )
        }

        //去登陆
        registerTextGoLogin.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {
        registerActivityViewModel.sendSmsCodeLiveData.observe(
                this, RequestResultObserver(
                        successBlock = {
                            //计时
                            timer.start()
                        },
                        fragmentManager = supportFragmentManager, handleResult = true
                )
        )

        registerActivityViewModel.registerLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                        jumpTo<WorkActivity>()
                        finish()
                }, failBlock = {
                    resetData()
                }, fragmentManager = supportFragmentManager, handleResult = true
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {

    }

    /**
     *动态改变 注册/获取验证码 按钮点击状态
     */
    private fun checkInpont() {
        registerTextRegister.isEnabled =
            registerEtPhone.text.length == 11 && registerEtPwd.text.length >= 6 && registerEtCode.text.length == 6

        registerTextGetCode.isEnabled=registerEtPhone.text.length == 11
    }

    /**
     * 验证手机格式
     */
    fun isMobileNO(mobiles: String): Boolean {
        val telRegex = "[1]\\d{10}"
        return if (mobiles.isEmpty()) false else Regex(telRegex).containsMatchIn(mobiles)
    }

    //当获取验证码成功的时候倒计时开始
    var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            registerTextGetCode.isEnabled = false
            var time=millisUntilFinished / 1000
            registerTextGetCode.text="${time}s"
        }

        override fun onFinish() {
            registerTextGetCode.isEnabled = true
            registerTextGetCode.text="获取验证码"
        }
    }

    fun getRandom():String{
        return "" + System.currentTimeMillis() + ((Math.random() * 9 + 1) * 100).toInt()
    }

}