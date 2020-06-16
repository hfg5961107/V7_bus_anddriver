package com.rvakva.bus.home.viewmodel.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.route.*
import com.amap.api.services.route.RouteSearch.*
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.HomeService
import com.rvakva.bus.home.R
import com.rvakva.bus.home.model.CompleteModel
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.expend.initMarker
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.retrofit.result.EmResult


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/2 下午4:49
 */
class OrderOperationViewModel(application: Application) : AndroidViewModel(application) {

    val scheduleDetailLiveData by RequestLiveData<EmResult<ScheduleDataModel>>()

    val operationLiveData by RequestLiveData<EmResult<CompleteModel>>()

    val orderLiveData by RequestLiveData<EmResult<MutableList<PassengerModel>>>()

    val changeSortLiveData by RequestLiveData<BaseResult>()

    var latLng: LatLng? = null
    var locationMarker: Marker? = null

    fun changeLatLng() {
        KtxViewModel.locationLiveData.value?.let {
            latLng = LatLng(it.latitude,it.longitude)
        }
    }

    val multipleLivedata =
        MediatorLiveData<LatLng?>().apply {
            addSource(KtxViewModel.locationLiveData) {
                postValue(latLng)
            }
        }

    fun addMyLocationMarker(block: (MarkerOptions) -> Marker) {
        locationMarker?.let {
            it.position = latLng
        } ?: initMarker(latLng, R.drawable.common_icon_location_blue).let {
            locationMarker = block(it)
        }
    }

    /**
     * 查询班次
     */
    fun qureyScheduleById(scheduleId: Long) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .queryScheduleById(scheduleId)
                    .requestMap()
            }, requestLiveData = scheduleDetailLiveData, showToastBar = true
        )
    }

    /**
     * 站点检票
     */
    fun checkTicket(orderCheck: String, orderDriverId: Long) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .checkTicket(orderCheck, orderDriverId)
                    .requestMap()
            }, requestLiveData = operationLiveData, showToastBar = true
        )
    }

    /**
     * 开始行程/送人
     */
    fun gotoDestination(driverId: Long, orderDriverId: Long, orderId: Long?) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .gotoDestination(driverId, orderDriverId, orderId)
                    .requestMap()
            }, requestLiveData = operationLiveData, showToastBar = true
        )
    }

    /**
     * 完成订单/完成班次
     */
    fun finishOrder(driverId: Long, orderDriverId: Long, orderId: Long?) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .finishOrder(driverId, orderDriverId, orderId)
                    .requestMap()
            }, requestLiveData = operationLiveData, showToastBar = true
        )
    }

    /**
     * 查询接人送人顺序
     */
    fun qureyOrderList(orderDriverId: Long) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .qureyOrderList(orderDriverId)
                    .requestMap()
            }, requestLiveData = orderLiveData, showToastBar = true
        )
    }

    /**
     * 修改订单排序
     */
    fun changeOrderList(driverId: Long, orderDriverId: Long, orderSort: String) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .changeOrderList(driverId,orderDriverId,orderSort)
                    .requestMap()
            }, requestLiveData = changeSortLiveData, showToastBar = true
        )
    }

    /**
     * 接人/前往预约地
     */
    fun gotoBookPlace(driverId: Long, orderDriverId: Long, orderId: Long?) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .gotoBookPlace(driverId, orderDriverId, orderId)
                    .requestMap()
            }, requestLiveData = operationLiveData, showToastBar = true
        )
    }

    /**
     * 到达预约地
     */
    fun arriveBookPlace(driverId: Long, orderDriverId: Long, orderId: Long?) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .arriveBookPlace(driverId, orderDriverId, orderId)
                    .requestMap()
            }, requestLiveData = operationLiveData, showToastBar = true
        )
    }

    /**
     * 乘客上车信息
     */
    fun takeOverCheck(driverId: Long, orderDriverId: Long, orderId: Long,loadType: Int) {
        launchRequest(
            block = {
                ApiManager.getInstance().createService(HomeService::class.java)
                    .takeOverCheck(driverId, orderDriverId, orderId,loadType)
                    .requestMap()
            }, requestLiveData = operationLiveData, showToastBar = true
        )
    }





}