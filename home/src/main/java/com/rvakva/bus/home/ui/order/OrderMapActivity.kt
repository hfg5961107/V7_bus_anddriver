package com.rvakva.bus.home.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import com.amap.api.maps.MapView
import com.rvakva.bus.common.base.MapActivity
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.R
import com.rvakva.bus.home.ui.order.fragment.OrderRunFragment
import com.rvakva.bus.home.ui.order.fragment.OrderSortFragment
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.expend.addFragment
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import kotlinx.android.synthetic.main.activity_order_map.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/9 上午8:52
 */
class OrderMapActivity : MapActivity(R.layout.activity_order_map) {

    /**
     *  //1 排序  2操作
     */
    var type: Int = 0

    var orderDriverId :Long = 0

    val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    lateinit var orderSortFragment: OrderSortFragment
    lateinit var orderRunFragment: OrderRunFragment

    override val mapView: MapView get() = orderMapMv

    override fun initTitle() {
        mapMtb.let {
            it.leftTv.setOnClickListener { finish() }
        }
    }

    override fun initObserver() {
        orderOperationViewModel.scheduleDetailLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let { model->
                        showData(model)
                    }
                },
                failBlock = {
                    finish()
                }
            )
        )
    }

    private fun  showData(model:ScheduleDataModel){
        //展示头部
    }

    override fun initData(isFirstInit: Boolean) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        type = intent.getIntExtra("type", 0)
        orderDriverId = intent.getLongExtra("orderDriverId", 0)

        if (type == 1) {
            orderSortFragment = OrderSortFragment.newInstance(orderDriverId)
            supportFragmentManager.addFragment(orderSortFragment, R.id.mapFrameLayout)
        } else if (type == 2) {
            orderRunFragment = OrderRunFragment.newInstance(orderDriverId)
            supportFragmentManager.addFragment(orderRunFragment, R.id.mapFrameLayout)
        }

        amap?.let { map ->
            orderOperationViewModel.changeLatLng()
            orderOperationViewModel.addMyLocationMarker {
                map.addMarker(it)
            }
        }
    }
}