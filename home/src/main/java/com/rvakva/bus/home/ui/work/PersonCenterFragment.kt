package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.IndexActivity
import com.rvakva.bus.home.viewmodel.work.WorkViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.model.UserAuditEnum
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.fragment_person_center.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午9:41
 */
class PersonCenterFragment : KtxFragment(R.layout.fragment_person_center) {

    private val workViewModel by activityViewModels<WorkViewModel>()

    var headAvater: String? = null

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
            if (it.name.isNullOrEmpty()) {
                pcDriverNameTv.text = "先生/女士"
            } else {
                pcDriverNameTv.text = it.name
            }

            pcDriverPhoneTv.text = it.phone?.let {
                it.substring(0, 3) + "****" + it.substring(7, it.length)
            }

            pcServicePhoneTv.text = it.driverServicePhone
            it.headPortrait?.let {
                if (headAvater == null || !headAvater.equals(it)){
                    pcHeaderIv.glideWithOvalInto(Config.IMAGE_SERVER + it)
                    headAvater = it
                }
            } ?: pcHeaderIv.setImageResource(R.drawable.home_personal_profile_photo)

            when (it.applyStatus) {
                UserAuditEnum.NON_IDENTITY.status -> {
                    myTextApproveState.text = "待认证"
                    myTextApproveState.setTextColor(resources.getColor(R.color.black_desc))
                }
                UserAuditEnum.PROGRESSING.status -> {
                    myTextApproveState.text = "认证中"
                    myTextApproveState.setTextColor(resources.getColor(R.color.color_yellow))
                }
                UserAuditEnum.SUCCESS.status -> {
                    myTextApproveState.text = "已认证"
                    myTextApproveState.setTextColor(resources.getColor(R.color.black_desc))
                }
                UserAuditEnum.FAIL.status -> {
                    myTextApproveState.text = "认证失败"
                    myTextApproveState.setTextColor(resources.getColor(R.color.color_red))
                }
            }

            //历史班次
            pcHistoryScheduleLl.setOnClickListener { view ->
                if (it.applyStatus == UserAuditEnum.SUCCESS.status) {
                    jumpByARouter(Config.USER_HISTORY_SCHEDULE)
                } else {
                    ToastBar.show("只有账户通过审核后，才能查看")
                }
            }

            //我的钱包
            pcWalletLl.setOnClickListener { view ->
                if (it.applyStatus == UserAuditEnum.SUCCESS.status) {
                    jumpByARouter(Config.USER_WALLET)
                } else {
                    ToastBar.show("只有账户通过审核后，才能查看")
                }
            }

            //业务流水
            pcBusinessList.setOnClickListener { view ->
                if (it.applyStatus == UserAuditEnum.SUCCESS.status) {
                    jumpByARouter(Config.USER_BUSINESS_LIST)
                } else {
                    ToastBar.show("只有账户通过审核后，才能查看")
                }
            }
        })

        Ktx.getInstance().userDataSource.userConfigLiveData.observe(this, Observer {
            if (it.balance != null) {
                myTextMony.text = "¥${it.balance.checkIsInt()}"
            } else {
                myTextMony.text = "¥0"
            }
        })
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        setOnClick()
    }

    override fun initData(isFirstInit: Boolean) {
        workViewModel.getUserInfo()
    }

    var name: String? = null

    private fun setOnClick() {
        //账户认证
        pcAccountApprove.setOnClickListener {
            jumpByARouter(Config.USER_IDENTITY)
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