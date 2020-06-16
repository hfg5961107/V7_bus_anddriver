package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rvakva.bus.common.X
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.common.util.MyMediaPlayerType
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.adapter.OrderAdapter
import com.rvakva.bus.home.viewmodel.work.WorkActivitySharedViewModel
import com.rvakva.bus.home.viewmodel.work.WorkFragmentViewModel
import com.rvakva.bus.home.viewmodel.work.WorkViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.observer.EventObserver
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.fragment_work_order.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/13 下午7:07
 */



class WorkOrderFragment private constructor() : KtxFragment(R.layout.fragment_work_order) {

    val workViewModel by activityViewModels<WorkViewModel>()

    val workActivitySharedViewModel by activityViewModels<WorkActivitySharedViewModel>()

    private var orderStatusType: ScheduleStatusTypeEnum =
        ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW


    override fun initObserver() {

        Ktx.getInstance().userDataSource.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            workFragmentViewModel.userInfo = it
        })


        workFragmentViewModel.orderListLiveData.observe(
            viewLifecycleOwner,
            RequestResultObserver(
                successBlock = {
                    sendAssignOrderCount(it.total)
                    mainMrv.onDataSuccessAndEmpty(it.data?.toMutableList(), orderStatusType.value)
                    workFragmentViewModel.countDownTimer(orderStatusType)
                }, failBlock = {
                    sendAssignOrderCount()
                    workFragmentViewModel.checkError(it)
                    mainMrv.onDataErrorAndException(it)
                }, completeBlock = {
                    workFragmentViewModel.let {
                        it.cancelJob()
                        it.isRequest = false
                    }
                    changeIconVisible()
                }
            )
        )

//        workFragmentViewModel.grabLiveData.observe(
//            viewLifecycleOwner,
//            RequestEmResultObserver(
//                successBlock = {
//                    it?.let {
//                        jumpToOrderDetail(it)
//                    }
//                }, failBlock = {
//                    (it as? ApiException)?.let {
//                        AlertDialog.newInstance(it.code)?.show(parentFragmentManager) ?: showToast(
//                            it
//                        )
//                    } ?: showToast(it)
//                }, fragmentManager = parentFragmentManager
//            )
//        )
//
//        workFragmentViewModel.denyConfigRequestLiveData.observe(
//            viewLifecycleOwner,
//            RequestEmResultObserver(
//                successBlock = {
//                    denyList = it?.toMutableList() ?: mutableListOf()
//                }, failBlock = {
//                    (it as? ApiException)?.let {
//                        AlertDialog.newInstance(it.code)?.show(parentFragmentManager) ?: showToast(
//                            it
//                        )
//                    } ?: showToast(it)
//                }, fragmentManager = parentFragmentManager
//            )
//        )
//
        KtxViewModel.emptyClickLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it == ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW.value) {
                X.getInstance().myMediaPlayer.play(MyMediaPlayerType.ONLINE)
                workViewModel.changeStatus(1)
            }
        })

//        mainActivitySharedViewModel.filterTypeChangeLiveData.observe(
//            viewLifecycleOwner,
//            EventObserver {
//                workFragmentViewModel.checkRequest {
//                    if (mainActivitySharedViewModel.fragmentFilterType[orderStatusType] != it) {
//                        mainActivitySharedViewModel.fragmentFilterType[orderStatusType] = it
//                        checkRequestAndAction()
//                    }
//                }
//            })
//
        workActivitySharedViewModel.newScheduledCountLiveData.observe(this, Observer {
            requestData()
        })
    }


    private fun changeIconVisible() {
//        null -> false
//        isEmpty -> false
//        isNotEmpty ->true
//        mainActivitySharedViewModel.iconVisibleLiveData.postEventValue(
//            workFragmentViewModel.orderListLiveData.value?.data?.data?.isNotEmpty() == true
//        )
    }

    private fun sendAssignOrderCount(count: Int = 0) {
        if (orderStatusType == ScheduleStatusTypeEnum.SCHEDULE_TYPE_COMPLETE) {
//            mainActivitySharedViewModel.assignOrderLiveData.postValue(count)
        }
    }

    private fun addExtraLiveDataObserver() {
//        when (orderStatusType) {
//            OrderStatusTypeEnum.ORDER_TYPE_NEW,
//            OrderStatusTypeEnum.ORDER_TYPE_ASSIGN -> mutableListOf(
//                XViewModel.grabOrderLiveData
//            )
//            else -> mutableListOf(
//                XViewModel.finishOrderLiveData
//            )
//        }.let {
//            it.add(XViewModel.cancelOrderLiveData)
//            it.forEach {
//                it.observe(viewLifecycleOwner, Observer {
//                    removeFromRv(it)
//                })
//            }
//        }

        if (orderStatusType == ScheduleStatusTypeEnum.SCHEDULE_TYPE_COMPLETE) {
//            XViewModel.denyOrderLiveData.observe(viewLifecycleOwner, EventObserver {
//                removeFromRv(mutableListOf(it))
//            })
        }
    }

    private fun removeFromRv(it: MutableList<Long>) {
        it.forEach { id ->
            //            mainMrv.getData<OrderDataModel>()?.firstOrNull {
//                it.id == id
//            }?.let {
//                mainMrv.removeData(it)
//            }
        }
    }


    private fun showToast(e: Exception) {
        e.handleExceptionDesc().let {
            ToastBar.show(it)
        }
    }

    private val workFragmentViewModel by viewModels<WorkFragmentViewModel>()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        orderStatusType = arguments?.getSerializable(ORDER_TYPE) as? ScheduleStatusTypeEnum
            ?: ScheduleStatusTypeEnum.SCHEDULE_TYPE_ING

        mainMrv.initialize(
            adapter = OrderAdapter(orderStatusType),
            onEmptyClickBlock = ::requestData,
            onRefreshBlock = ::requestData,
            initializeBlock = {
                addHeaderView(
                    layoutInflater.inflate(
                        R.layout.fragment_main_order_item_header,
                        mainMrv,
                        false
                    )
                )
                setOnItemClickBlock<ScheduleDataModel> { adapter, view, position ->
                    adapter?.let {
                        it.data[position].let { data ->
                            when (view.id) {
                                R.id.homeOrderItemNew -> {
                                    jumpToOrderDetail(data)
                                }
                            }
                        }
                    }
                }
            },
            emptyString =
            when (orderStatusType) {
                ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW -> "休息中，开启工作后可接单"
                ScheduleStatusTypeEnum.SCHEDULE_TYPE_ING -> "暂无进行中订单"
                ScheduleStatusTypeEnum.SCHEDULE_TYPE_COMPLETE -> "暂无已完成订单"
                ScheduleStatusTypeEnum.SCHEDULE_TYPE_CANCEL -> "暂无已取消订单"
            }
        )
    }

    private fun jumpToOrderDetail(dataModel: ScheduleDataModel) {
        jumpByARouter(Config.HOME_SCHEDULE_DETAIL) {
            withLong(Config.SCHEDULE_ID_KEY, dataModel.id)
        }
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        } else {
            checkRequestAndAction(false)
        }
    }

    private fun checkRequestAndAction(refreshEnable: Boolean = true) {
        workFragmentViewModel.checkRequest {
            requestWithAction(refreshEnable)

        }
    }

    private fun requestData() {
        workFragmentViewModel.isRequest = true
        Ktx.getInstance().userDataSource.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            workFragmentViewModel.getOrderList(
                orderStatusType,
                mainMrv.currentPage + 1
            )
        })
    }

    private fun requestWithAction(refreshEnable: Boolean = true) {
        if (mainMrv.getData<ScheduleDataModel>().isNullOrEmpty()) {
            mainMrv.performEmptyClick()
        } else if (refreshEnable) {
            mainMrv.performRefreshPull()
        }
    }


    companion object {

        private const val ORDER_TYPE = "ORDER_TYPE"

        @JvmStatic
        fun newInstance(orderStatusType: ScheduleStatusTypeEnum) =
            WorkOrderFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ORDER_TYPE, orderStatusType)
                }
            }
    }


    override fun onResume() {
        super.onResume()
        requestData()
    }
}
