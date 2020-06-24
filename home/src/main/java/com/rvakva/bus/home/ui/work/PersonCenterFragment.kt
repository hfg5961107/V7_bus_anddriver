package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.*
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
            pcDriverPhoneTv.text = it.phone?.let {
                it.substring(0,3)+"****"+it.substring(7,it.length)
            }

            pcServicePhoneTv.text = it.driverServicePhone
            it.headPortrait?.let {
                pcHeaderIv.glideWithOvalInto(Config.IMAGE_SERVER + it)
            } ?: pcHeaderIv.setImageResource(R.drawable.home_personal_profile_photo)
        })

        Ktx.getInstance().userDataSource.userConfigLiveData.observe(this, Observer {
            myTextMony.text="¥${it?.balance}"
        })
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        setOnClick()
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {

        }
    }

    private fun setOnClick() {
        //历史班次
        pcHistoryScheduleLl.setOnClickListener {
            jumpByARouter(Config.USER_HISTORY_SCHEDULE)
        }
        //我的钱包
        pcWalletLl.setOnClickListener {
            jumpByARouter(Config.USER_WALLET)
        }
        //业务流水
        pcBusinessList.setOnClickListener {
            jumpByARouter(Config.USER_BUSINESS_LIST)
        }
        //账户认证
        pcAccountApprove.setOnClickListener {
            // TODO: 2020/6/24  账户认证
        }
        //系统设置
        pcSysSetLl.setOnClickListener {
            jumpByARouter(Config.USER_SYSTEM_SETTING)
        }
        //联系客服
        pcServicePhoneLl.setOnClickListener {
            if (!pcServicePhoneTv.text.isNullOrBlank()) {
                context?.callPhone(pcServicePhoneTv.text.toString())
            }
        }
    }

}