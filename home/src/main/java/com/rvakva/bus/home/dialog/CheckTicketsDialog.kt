package com.rvakva.bus.home.dialog

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.widget.AlertDialog
import com.rvakva.bus.common.widget.AlertExceptionEnum
import com.rvakva.bus.common.widget.OnAlertDialogClickListener
import com.rvakva.bus.common.widget.OnAlertDialogDismissListener
import com.rvakva.bus.home.R
import com.rvakva.bus.home.model.CheckModel
import com.rvakva.bus.home.ui.adapter.CheckTicketsAdapter
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.expend.dpToPx
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.widget.ToastBar
import com.sherloki.commonwidget.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_check_tickets.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/5 上午10:38
 */
class CheckTicketsDialog private constructor() : BaseDialogFragment() {

    lateinit var adapter: CheckTicketsAdapter

    private val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    companion object {
        fun newInstance(listData: MutableList<PassengerModel>) =
            CheckTicketsDialog().apply {
                arguments = bundleOf(
                    LIST_DATA to listData
                )
            }

        private const val LIST_DATA = "LIST_DATA"

    }


    override fun getViewWidth() =
        Resources.getSystem().displayMetrics.widthPixels - 48.dpToPx<Int>()

    override fun getWindowAnimation() = R.style.BottomDialog

    override fun getViewHeight() = 400.dpToPx<Int>()

    override fun getLayoutId() = R.layout.dialog_check_tickets

    override fun initView(view: View?) {
        checkTicketsCloseIv.setOnClickListener {
            dismiss()
        }

        (arguments?.getSerializable(LIST_DATA) as MutableList<PassengerModel>).let { listdata ->

            adapter = CheckTicketsAdapter(context!!, listdata)
            myRecyclerViewTicket.layoutManager = LinearLayoutManager(context)
            myRecyclerViewTicket.adapter = adapter

            checkTicketsBtn.setOnClickListener {
                var datas : MutableList<CheckModel> =  mutableListOf()
                var isComplate = true
                listdata.forEach {
                    if (it.loadType != 1){
                        var model  = CheckModel()
                        model.orderId = it.orderId
                        model.loadType = it.loadType
                        datas.add(model)
                    }else{
                        isComplate = false
                    }
                }
                if (isComplate){
                    onDialogClickListener?.onDialogClick(Gson().toJson(datas))
                }else{
                    ToastBar.show("请全部检票后再提交")
                }
            }
        }
    }


    interface OnDialogClickListener {
        fun onDialogClick(data: String)
    }
    override fun onAttach(context: Context) {
        onDialogClickListener = context as? OnDialogClickListener
        super.onAttach(context)
    }

     var onDialogClickListener: OnDialogClickListener? = null
}