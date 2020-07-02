package com.rvakva.travel.devkit.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.Observer
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.R
import com.rvakva.travel.devkit.expend.jumpByARouter
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.expend.setImageResource
import com.rvakva.travel.devkit.model.UserAuditEnum
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.sherloki.simpleadapter.widget.IBaseEmptyView
import kotlinx.android.synthetic.main.my_empty_view.view.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午10:39
 */
class MyEmptyView : IBaseEmptyView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override val layoutId: Int
        get() = R.layout.my_empty_view

    override val loadingId: Int
        get() = R.id.myEmptyViewFlLoading

    override val errorId: Int
        get() = R.id.myEmptyViewLlError

    override val clickLayoutId: Int
        get() = R.id.myEmptyViewTvError

    var emptyRes: Int? = null

    override fun changeView(status: Int) {
        myEmptyViewTvError.setImageResource(topRes = R.drawable.common_empty_base)
        myEmptyViewTvAction.visibility = View.GONE
        when (status) {
            EMPTY -> {
                bindEmpty()
            }
            ERROR -> {
                bindError()
            }
            LOADING -> {
            }
        }
    }

    private fun bindError() {
        myEmptyViewTvError.text = errorText
        var res = 0
        var textContent = ""
        when (emptyViewCode) {
            ApiConstant.ERROR_CODE_DRIVER_STATUS_NOT_AUDIT -> {
                res = R.drawable.common_identity
                textContent = "立即认证"
            }
            ApiConstant.ERROR_CODE_DRIVER_STATUS_IN_AUDIT -> {
                res = R.drawable.common_identity_processing
                textContent = "前往查看"
            }
            ApiConstant.ERROR_CODE_ORDER_IS_TAKEN -> {
                res = R.drawable.common_empty_base
                textContent = "点击返回"
            }
            ApiConstant.ERROR_CODE_DRIVER_STATUS_AUDIT_REFUSE -> {
                res = R.drawable.common_identity_fail
                textContent = "前往查看"
            }
        }
        if (textContent.isNotEmpty() && res != 0) {
            myEmptyViewTvAction.let {
                it.text = textContent
                it.visibility = View.VISIBLE
                it.setOnClickListener {
                    if (emptyViewCode == ApiConstant.ERROR_CODE_ORDER_IS_TAKEN) {
                        (context as? Activity)?.finish()
                    } else {
                        jumpByARouter(Config.USER_IDENTITY)
                    }
                }
            }
            myEmptyViewTvError.setImageResource(topRes = res)
        }
    }

    private fun bindEmpty() {
        myEmptyViewTvError.text = emptyText
        var textContent = ""
        Ktx.getInstance().userDataSource.userInfoLiveData.observeForever(Observer {
            "~~~~~~~~~~~~`applyStatus:${it?.applyStatus}".loge()
            when (it?.applyStatus) {
                UserAuditEnum.NON_IDENTITY.status -> {
                    textContent = "前往认证"
                    myEmptyViewTvError.text = "账户还未认证"
                    myEmptyViewTvError.setImageResource(topRes = R.drawable.common_empty_base)
                }
                UserAuditEnum.PROGRESSING.status -> {
                    textContent = "前往查看"
                    myEmptyViewTvError.text = "账户正在审核"
                    myEmptyViewTvError.setImageResource(topRes = R.drawable.common_identity_processing)
                }
                UserAuditEnum.SUCCESS.status -> {
                    textContent=""
                    myEmptyViewTvAction.visibility=View.GONE
                    myEmptyViewTvError.text = emptyText
                    myEmptyViewTvError.setImageResource(topRes = R.drawable.common_empty_base)
                }
                UserAuditEnum.FAIL.status -> {
                    textContent = "前往查看"
                    myEmptyViewTvError.text = "账户审核未通过"
                    myEmptyViewTvError.setImageResource(topRes = R.drawable.common_identity_fail)
                }
            }
        })
        if (textContent.isNotEmpty()) {
            myEmptyViewTvAction.let {
                it.text = textContent
                it.visibility = View.VISIBLE
                it.setOnClickListener {
                    KtxViewModel.emptyClickLiveData.postEventValue(emptyViewCode)
                }
            }
        }
    }

}