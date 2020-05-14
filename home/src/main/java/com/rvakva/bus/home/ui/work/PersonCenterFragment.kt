package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.jumpByARouter
import com.rvakva.travel.devkit.expend.callPhone
import kotlinx.android.synthetic.main.fragment_person_center.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午9:41
 */
class PersonCenterFragment : KtxFragment(R.layout.fragment_person_center) {

    companion object {

        @JvmStatic
        fun newInstance() =
            PersonCenterFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun initObserver() {
        Ktx.getInstance().userDataSource.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            pcDriverNameTv.text = it.name
            pcDriverPhoneTv.text = it.phone
            pcServicePhoneTv.text = it.driverServicePhone
        })
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        setOnClick()
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit){

        }
    }

    private fun setOnClick(){
        pcWalletLl.setOnClickListener {
            jumpByARouter(Config.USER_WALLET)
        }
        pcSysSetLl.setOnClickListener {
            jumpByARouter(Config.USER_SYSTEM_SETTING)
        }
        pcServicePhoneLl.setOnClickListener {
            if (!pcServicePhoneTv.text.isNullOrBlank()) {
                context?.callPhone(pcServicePhoneTv.text.toString())
            }
        }
    }

}