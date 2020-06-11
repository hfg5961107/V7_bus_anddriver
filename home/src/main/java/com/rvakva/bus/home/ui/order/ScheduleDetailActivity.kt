package com.rvakva.bus.home.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.home.R
import com.rvakva.bus.home.dialog.CheckTicketsDialog
import com.rvakva.bus.home.ui.adapter.OrderPassengerAdapter
import com.rvakva.bus.home.ui.work.WorkActivity
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.formatDate
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_schedule_detail.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午4:36
 */
@Route(path = Config.HOME_SCHEDULE_DETAIL)
class ScheduleDetailActivity : KtxActivity(R.layout.activity_schedule_detail),
    CheckTicketsDialog.OnDialogClickListener {

    private val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    var scheduleId: Long = -1

    lateinit var adapter: OrderPassengerAdapter

    private var dialog: CheckTicketsDialog? = null

    override fun initTitle() {
        detailMtb.let {
            it.leftTv.setOnClickListener { finish() }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        scheduleId = intent?.getLongExtra(Config.SCHEDULE_ID_KEY, -1) ?: -1
        if (scheduleId == -1L) {
            ToastBar.show(ApiConstant.COMMON_ERROR)
            finish()
        }

        adapter = OrderPassengerAdapter(this)
        val layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerviewPassenger.adapter = adapter
        recyclerviewPassenger.layoutManager = layoutManager;

    }

    override fun initObserver() {
        orderOperationViewModel.scheduleDetailLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let { model ->
                        showData(model)
                        adapter.setData(model.order, model.lineType)
                    } ?: ToastBar.show("查询信息为空")
                },
                failBlock = {
                    ToastBar.show("查询失败")
                    finish()
                }
            )
        )

        orderOperationViewModel.operationLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    if (it.data != null) {
                        it.data?.let { model ->
                            if (model.isFinish) {

                                jumpTo<ServiceCompleteActivity> {
                                    putExtra("serviceTime", model.totalTime)
                                }
                                finish()
                            } else {
                                orderOperationViewModel.qureyScheduleById(scheduleId)
                                dialog?.dismiss()
                            }
                        }
                    } else {
                        orderOperationViewModel.qureyScheduleById(scheduleId)
                        dialog?.dismiss()
                    }
                },
                failBlock = {
                    dialog?.dismiss()
                }
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {

    }

    private fun requestScheduleData() {
        orderOperationViewModel.qureyScheduleById(scheduleId)
    }

    /**
     * 展示数据
     */
    private fun showData(model: ScheduleDataModel) {
        orderDriverId = model.id

        detailCarNumberSeatTv.text = "${model.licenseNo} / ${model.vehicleSeat}座"
        detailRestSeatTv.text = "余座 ${model.remainingSeat}"

        model.station?.forEach {
            if (it.type == 1) {
                detailStartSiteTv.text = it.address
            } else if (it.type == 2) {
                detailEndSiteTv.text = it.address
            }
        }
        detailBeginTimeTv.text = "${formatDate(
            model.schedulingTime * 1000,
            Config.PATTERN_YYYY_MM_DD
        )} · 检票${model.startLineTime} · 出发${model.endLineTime}"

        model.schedulingNo?.let {
            detailOrderNoTv.text = it
        }

        detailCancelTimeTv.text = formatDate(model.finishTime * 1000, Config.PATTERN_YYYY_MM_DD)
        detailCompleteTimeTv.text = formatDate(model.finishTime * 1000, Config.PATTERN_YYYY_MM_DD)

        detailIncomeTv.text = "¥ ${model.realTotalFee}"

//        * 1 站点-站点
//        * 2 站点-送人
//        * 3 接人-送人
//        * 4 接人-站点
        when (model.lineType) {
            1 -> {
                detailStartSiteLl.visibility = View.VISIBLE
                detailEndSiteLl.visibility = View.VISIBLE
            }
            2 -> {
                detailStartSiteLl.visibility = View.VISIBLE
                detailEndSiteLl.visibility = View.GONE
            }
            3 -> {
                detailStartSiteLl.visibility = View.GONE
                detailEndSiteLl.visibility = View.GONE
            }
            4 -> {
                detailStartSiteLl.visibility = View.GONE
                detailEndSiteLl.visibility = View.VISIBLE
            }
        }

        detailMtb.let {
            it.rightTv.visibility = View.VISIBLE
            it.rightTv.text = "查看规划"
            it.rightTv.setOnClickListener {
                jumpTo<OrderSortActivity> {
                    putExtra("type", 1)
                    putExtra("orderDriverId", model.id)
                }
            }
        }
        when (model.status) {
            //行程未开始
            ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW.value -> {
                detailMtb.let { it.centerText.text = "等待行程开始" }
                detailIncomeRl.visibility = View.VISIBLE
                detailCompleteTimeRl.visibility = View.GONE
                detailCancelTimeRl.visibility = View.GONE

                detailRestSeatTv.visibility = View.VISIBLE

                orderDetailBottomLin.visibility = View.VISIBLE

                orderDetailNavigationLin.visibility = View.GONE

                if (model.lineType == 1 || model.lineType == 2) {
                    detailCheckTicketTv.visibility = View.VISIBLE

                    detailMtb.let { it.rightTv.visibility = View.GONE }

                    if (!checkOver(model)) {
                        orderDetailOperationBtn.text = "请完成检票"
                        orderDetailOperationBtn.background =
                            resources.getDrawable(R.drawable.cor4_com_btn_ccc_bg)

                        detailCheckTicketTv.text = "检票"

                    } else {
                        orderDetailOperationBtn.text = "开始行程"
                        orderDetailOperationBtn.background =
                            resources.getDrawable(R.drawable.cor4_com_btn_blue_bg)

                        //站点的开始行程
                        orderDetailOperationBtn.setOnClickListener {
                            orderOperationViewModel.gotoDestination(
                                Ktx.getInstance().userDataSource.userId,
                                model.id,
                                null
                            )
                        }

                        detailCheckTicketTv.text = "重新检票"
                    }
                } else {
                    detailCheckTicketTv.visibility = View.GONE

                    orderDetailOperationBtn.text = "开始行程"
                    orderDetailOperationBtn.background =
                        resources.getDrawable(R.drawable.cor4_com_btn_blue_bg)

                    orderDetailOperationBtn.setOnClickListener {
                        jumpTo<OrderRunActivity> {
                            putExtra("orderDriverId", model.id)
                        }
                    }
                }
            }
            //行程中
            ScheduleStatusTypeEnum.SCHEDULE_TYPE_ING.value -> {
                detailMtb.let { it.centerText.text = "行程中" }
                detailIncomeRl.visibility = View.VISIBLE
                detailCompleteTimeRl.visibility = View.GONE
                detailCancelTimeRl.visibility = View.GONE

                detailRestSeatTv.visibility = View.VISIBLE
                detailCheckTicketTv.visibility = View.GONE

                orderDetailBottomLin.visibility = View.VISIBLE

                when (model.lineType) {
                    1 -> {
                        orderDetailOperationBtn.text = "到达目的地"
                        orderDetailOperationBtn.background =
                            resources.getDrawable(R.drawable.cor4_com_btn_blue_bg)

                        orderDetailNavigationLin.visibility = View.VISIBLE

                        //站点的完成班次
                        orderDetailOperationBtn.setOnClickListener {
                            orderOperationViewModel.finishOrder(
                                Ktx.getInstance().userDataSource.userId,
                                model.id,
                                null
                            )
                        }
                    }
                    2 -> {
                        if (checkSending(model.order)){
                            orderDetailOperationBtn.text = "继续行程"
                        }else{
                            orderDetailOperationBtn.text = "开始送人"
                        }
                        orderDetailOperationBtn.background =
                            resources.getDrawable(R.drawable.cor4_com_btn_blue_bg)
                        orderDetailOperationBtn.setOnClickListener {
                            jumpTo<OrderRunActivity> {
                                putExtra("orderDriverId", model.id)
                            }
                        }
                    }
                    3 -> {
                        if (!checkStatus(model.order)){
                            orderDetailOperationBtn.text = "继续接人"

                            orderDetailOperationBtn.setOnClickListener {
                                jumpTo<OrderRunActivity> {
                                    putExtra("orderDriverId", model.id)
                                }
                            }

                        }else{
                            if (checkSending(model.order)){
                                orderDetailOperationBtn.text = "继续行程"
                            }else{
                                orderDetailOperationBtn.text = "开始送人"
                            }
                            orderDetailOperationBtn.background =
                                resources.getDrawable(R.drawable.cor4_com_btn_blue_bg)
                            orderDetailOperationBtn.setOnClickListener {
                                jumpTo<OrderRunActivity> {
                                    putExtra("orderDriverId", model.id)
                                }
                            }
                        }
                    }
                    4 -> {
                        if (!checkStatus(model.order)){
                            orderDetailOperationBtn.text = "继续接人"

                            orderDetailOperationBtn.setOnClickListener {
                                jumpTo<OrderRunActivity> {
                                    putExtra("orderDriverId", model.id)
                                }
                            }

                        }else{

                            if (checkSending(model.order)){
                                orderDetailOperationBtn.text = "达到目的地"

                                orderDetailOperationBtn.setOnClickListener {
                                    orderOperationViewModel.finishOrder(
                                        Ktx.getInstance().userDataSource.userId,
                                        model.id,
                                        null
                                    )
                                }
                            }else{
                                orderDetailOperationBtn.text = "开始跨城出行"

                                orderDetailOperationBtn.setOnClickListener {
                                    orderOperationViewModel.gotoDestination(
                                        Ktx.getInstance().userDataSource.userId,
                                        model.id,
                                        null
                                    )
                                }
                            }
                        }
                    }

                }
            }
            //行程完成
            ScheduleStatusTypeEnum.SCHEDULE_TYPE_COMPLETE.value -> {
                detailMtb.let { it.centerText.text = "已完成" }
                detailIncomeRl.visibility = View.VISIBLE
                detailCompleteTimeRl.visibility = View.VISIBLE
                detailCancelTimeRl.visibility = View.GONE

                detailRestSeatTv.visibility = View.GONE
                detailCheckTicketTv.visibility = View.GONE

                orderDetailBottomLin.visibility = View.GONE
            }
            //行程取消
            ScheduleStatusTypeEnum.SCHEDULE_TYPE_CANCEL.value -> {
                detailMtb.let { it.centerText.text = "已取消" }
                detailIncomeRl.visibility = View.GONE
                detailCompleteTimeRl.visibility = View.GONE
                detailCancelTimeRl.visibility = View.VISIBLE

                detailRestSeatTv.visibility = View.GONE
                detailCheckTicketTv.visibility = View.GONE

                orderDetailBottomLin.visibility = View.GONE
            }
        }

        detailCheckTicketTv.setOnClickListener {
            model.order?.let { it ->
                dialog = CheckTicketsDialog.newInstance(it)
                dialog?.show(supportFragmentManager)
            }
        }
    }

    /**
     * 判断接人中还是送人中  ture 下车中 false上车中
     */
    private fun checkStatus(list: List<PassengerModel>): Boolean {
        for (i in list.indices) {
            if (list[i].status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                return false
            }
        }
        return true
    }

    /**
     * 判断是刚送人还是已经送了至少一个人了
     */
    private fun checkSending(list: List<PassengerModel>): Boolean {
        for (i in list.indices) {
            if (list[i].status == OrderStatusTypeEnum.ORDER_STATUS_SENDING.value
                || list[i].status == OrderStatusTypeEnum.ORDER_STATUS_COMPLETE.value) {
                return true
            }
        }
        return false
    }

    /**
     * 是否全部检票/全部上车或者跳过
     */
    private fun checkOver(model: ScheduleDataModel): Boolean = run {
        model.order?.forEach {
            if (it.loadType == 1) {
                return false
            }
        }
        return true
    }

    var orderDriverId: Long = 0

    override fun onDialogClick(data: String) {
        orderOperationViewModel.checkTicket(data, orderDriverId)
    }


    override fun onResume() {
        super.onResume()
        requestScheduleData()
    }
}