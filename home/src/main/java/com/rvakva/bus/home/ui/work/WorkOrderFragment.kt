package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.common.base.Strings.isNullOrEmpty
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.observer.EventObserver
import com.rvakva.travel.devkit.observer.request.RequestResultObserver

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/13 下午7:07
 */
enum class OrderTipEnum(val value: Boolean) {
    ORDER_TIP_TYPE_TRUE(true), ORDER_TIP_TYPE_FALSE(false)
}

enum class OrderStatusTypeEnum(val value: Int) {
    ORDER_TYPE_POOL(Config.ORDER_TYPE_POOL),
    ORDER_TYPE_ASSIGN(Config.ORDER_TYPE_ASSIGN),
    ORDER_TYPE_ING(Config.ORDER_TYPE_ING)
}

enum class OrderDistanceTypeEnum(val value: Int) {
    ORDER_DISTANCE_TAKE(1), ORDER_DISTANCE_SEND(2)
}

class WorkOrderFragment private constructor() : KtxFragment(R.layout.fragment_work_order) {

    private var orderStatusType: OrderStatusTypeEnum =
        OrderStatusTypeEnum.ORDER_TYPE_ING


    override fun initObserver() {

//        Ktx.getInstance().userDataSource.userInfoLiveData.observe(viewLifecycleOwner, Observer {
//            mainFragmentViewModel.userInfo = it
//        })
//
//        if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_POOL) {
//            XViewModel.newOrderLiveData.observe(
//                viewLifecycleOwner,
//                EventObserver(::checkRequestAndAction)
//            )
//
//        } else if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_ASSIGN) {
//            mainFragmentViewModel.countDownOrderListLiveData.observe(this, Observer {
//                mainMrv.getData<OrderDataModel>()?.toMutableList().apply {
//                    if (isNullOrEmpty()) {
//                        mainFragmentViewModel.cancelJob()
//                    }
//                }?.filter {
//                    it.orderAssignResponseTime.parseSecondsOffsetToSecond() > 0
//                }?.let {
//                    //                    it.firstOrNull { it.id == dialog?.orderId && dialog?.isAdded == true }
////                        ?: dialog?.dismiss()
//                    sendAssignOrderCount(it.size)
//                    mainMrv.onDataSuccessAndEmpty(
//                        it.toMutableList(),
//                        orderStatusType.value
//                    )
//                }
//            })
//            XViewModel.assignOrderLiveData.observe(
//                viewLifecycleOwner,
//                EventObserver(::checkRequestAndAction)
//            )
//
//        }
//        addExtraLiveDataObserver()
//
//        mainFragmentViewModel.orderListLiveData.observe(
//            viewLifecycleOwner,
//            RequestResultObserver(
//                successBlock = {
//                    sendAssignOrderCount(it.total)
//                    mainMrv.onDataSuccessAndEmpty(it.data?.toMutableList(), orderStatusType.value)
//                    mainFragmentViewModel.countDownTimer(orderStatusType)
//                }, failBlock = {
//                    sendAssignOrderCount()
//                    mainFragmentViewModel.checkError(it)
//                    mainMrv.onDataErrorAndException(it)
//                }, completeBlock = {
//                    mainFragmentViewModel.let {
//                        it.cancelJob()
//                        it.isRequest = false
//                    }
//                    changeIconVisible()
//                }
//            )
//        )
//
//        mainFragmentViewModel.grabLiveData.observe(
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
//        mainFragmentViewModel.denyConfigRequestLiveData.observe(
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
//        KtxViewModel.emptyClickLiveData.observe(viewLifecycleOwner, EventObserver {
//            if (it == OrderStatusTypeEnum.ORDER_TYPE_POOL.value) {
//                checkRequestAndAction()
//            } else {
//                mainActivitySharedViewModel.viewpagerChangeLiveData.postEventValue(true)
//            }
//        })
//
//        mainActivitySharedViewModel.filterTypeChangeLiveData.observe(
//            viewLifecycleOwner,
//            EventObserver {
//                mainFragmentViewModel.checkRequest {
//                    if (mainActivitySharedViewModel.fragmentFilterType[orderStatusType] != it) {
//                        mainActivitySharedViewModel.fragmentFilterType[orderStatusType] = it
//                        checkRequestAndAction()
//                    }
//                }
//            })
//
//        mainActivitySharedViewModel.refreshDataLiveData.observe(viewLifecycleOwner, EventObserver {
//            checkRequestAndAction()
//        })
    }

//    private fun changeIconVisible() {
//        // null -> false
//        // isEmpty -> false
//        // isNotEmpty ->true
//        mainActivitySharedViewModel.iconVisibleLiveData.postEventValue(
//            mainFragmentViewModel.orderListLiveData.value?.data?.data?.isNotEmpty() == true
//        )
//    }
//
//    private fun sendAssignOrderCount(count: Int = 0) {
//        if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_ASSIGN) {
//            mainActivitySharedViewModel.assignOrderLiveData.postValue(count)
//        }
//    }

    private fun addExtraLiveDataObserver() {
//        when (orderStatusType) {
//            OrderStatusTypeEnum.ORDER_TYPE_POOL,
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
//
//        if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_ASSIGN) {
//            XViewModel.denyOrderLiveData.observe(viewLifecycleOwner, EventObserver {
//                removeFromRv(mutableListOf(it))
//            })
//        }
    }

    private fun removeFromRv(it: MutableList<Long>) {
//        it.forEach { id ->
//            mainMrv.getData<OrderDataModel>()?.firstOrNull {
//                it.id == id
//            }?.let {
//                mainMrv.removeData(it)
//            }
//        }
    }


    private fun showToast(e: Exception) {
//        e.handleExceptionDesc().let {
//            ToastBar.show(it)
//        }
    }

//    private val mainFragmentViewModel by viewModels<MainFragmentViewModel>()
//    private val mainActivitySharedViewModel by activityViewModels<MainActivitySharedViewModel>()

    override fun initView(view: View, savedInstanceState: Bundle?) {
//        orderStatusType = arguments?.getSerializable(ORDER_TYPE) as? OrderStatusTypeEnum
//            ?: OrderStatusTypeEnum.ORDER_TYPE_ING
//
//        mainActivitySharedViewModel.fragmentFilterType[orderStatusType] = null
//
//        mainMrv.initialize(
//            adapter = OrderAdapter(orderStatusType),
//            onEmptyClickBlock = ::requestData,
//            onRefreshBlock = ::requestData,
//            initializeBlock = {
//                addHeaderView(
//                    layoutInflater.inflate(
//                        R.layout.fragment_main_order_item_header,
//                        mainMrv,
//                        false
//                    )
//                )
//                setOnItemClickBlock<OrderDataModel> { adapter, view, position ->
//                    adapter?.let {
//                        it.data[position].let { data ->
//                            when (view.id) {
//                                R.id.fragmentMainOrderItemPoolLl,
//                                R.id.fragmentMainOrderItemAssignLl,
//                                R.id.fragmentMainOrderItemIngFl -> {
//                                    jumpToOrderDetail(data)
//                                }
//                            }
//                        }
//                    }
//                }
//                addChildClickViewIds(
//                    R.id.fragmentMainOrderItemContentTvTakeDistance,
//                    R.id.fragmentMainOrderItemContentTvSendDistance,
//                    R.id.fragmentMainOrderItemPoolTvAction,
//                    R.id.fragmentMainOrderItemAssignLlAction,
//                    R.id.fragmentMainOrderItemAssignTvDeny
//                )
//                setOnItemChildClickBlock<OrderDataModel> { adapter, view, position ->
//                    adapter?.let {
//                        it.data[position].let { data ->
//                            when (view.id) {
//                                R.id.fragmentMainOrderItemPoolTvAction, R.id.fragmentMainOrderItemAssignLlAction -> mainFragmentViewModel.grabOrder(
//                                    data.id
//                                )
//                                R.id.fragmentMainOrderItemContentTvTakeDistance -> {
//                                    if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_ING) {
//                                        context.callPhone(data.orderAddressVoList?.getOrNull(0)?.phone)
//                                    }
//                                }
//                                R.id.fragmentMainOrderItemContentTvSendDistance -> {
//                                    if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_ING) {
//                                        context.callPhone(data.orderAddressVoList?.getOrNull(1)?.phone)
//                                    }
//                                }
//                                R.id.fragmentMainOrderItemAssignTvDeny -> {
//                                    DenyDialog.newInstance(data.id,denyList).let {
//                                        it.show(parentFragmentManager)
//                                        dialog = it
//                                    }
//                                }
//                                else -> {
//                                }
//                            }
//                        }
//                    }
//                }
//            },
//            emptyString =
//            if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_POOL)
//                "一大波订单正在赶到"
//            else if (orderStatusType == OrderStatusTypeEnum.ORDER_TYPE_ASSIGN)
//                "暂无指派单，去抢单池接单吧"
//            else "暂无进行中订单，快去抢单池接单吧"
//        )
    }

//    var dialog: DenyDialog? = null
//
//    private fun jumpToOrderDetail(orderData: OrderDataModel) {
//        jumpByARouter(Config.USER_ORDER_DETAIL) {
//            withLong(Config.ORDER_ID_KEY, orderData.id)
//        }
//    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        } else {
            checkRequestAndAction(false)
        }
//        changeIconVisible()
    }

    private fun checkRequestAndAction(refreshEnable: Boolean = true) {
//        mainFragmentViewModel.checkRequest {
//            requestWithAction(refreshEnable)
//        }
    }

    private fun requestData() {
//        mainFragmentViewModel.isRequest = true
//        mainFragmentViewModel.getOrderList(
//            orderStatusType,
//            mainActivitySharedViewModel.fragmentFilterType[orderStatusType]
//        )
//        mainFragmentViewModel.getDenyConfig()
    }

    private fun requestWithAction(refreshEnable: Boolean = true) {
//        if (mainMrv.getData<OrderDataModel>().isNullOrEmpty()) {
//            mainMrv.performEmptyClick()
//        } else if (refreshEnable) {
//            mainMrv.performRefreshPull()
//        }
    }


    companion object {

        private const val ORDER_TYPE = "ORDER_TYPE"

        @JvmStatic
        fun newInstance(orderStatusType: OrderStatusTypeEnum) =
            WorkOrderFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ORDER_TYPE, orderStatusType)
                }
            }
    }
}
