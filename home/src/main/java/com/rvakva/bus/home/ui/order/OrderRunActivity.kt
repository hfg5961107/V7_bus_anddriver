package com.rvakva.bus.home.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.route.*
import com.rvakva.bus.common.base.MapActivity
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.common.model.StationModel
import com.rvakva.bus.common.widget.overlay.DrivingRouteOverlay
import com.rvakva.bus.home.R
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.callPhone
import com.rvakva.travel.devkit.expend.dpToPx
import com.rvakva.travel.devkit.expend.glideWithRoundInto
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.activity_order_run.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/10 上午11:18
 */
class OrderRunActivity : MapActivity(R.layout.activity_order_run) {

    var orderDriverId: Long = 0

    val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    override val mapView: MapView
        get() = orderRunMv

    override fun initTitle() {
        orderRunMtb.let {
            it.leftTv.setOnClickListener { finish() }
        }
    }

    override fun initObserver() {
        orderOperationViewModel.scheduleDetailLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let { model ->
                        showData(model)
                    }
                },
                failBlock = {
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
                                orderOperationViewModel.qureyScheduleById(orderDriverId)
                            }
                        }
                    } else {
                        orderOperationViewModel.qureyScheduleById(orderDriverId)
                    }
                },
                failBlock = {
                    finish()
                }
            )
        )

        orderOperationViewModel.multipleLivedata.observe(this, Observer { latLng ->
            amap?.let { map ->
                orderOperationViewModel.changeLatLng()
                orderOperationViewModel.addMyLocationMarker {
                    map.addMarker(it)
                }
            }
        })
    }

    /**
     * 区域缩放
     *
     * @param latLngs
     */
    fun boundsZoom(latLngs: MutableList<LatLng>?) {
        if (latLngs == null) {
            return
        }

        val builder = LatLngBounds.Builder()
        for (latLng in latLngs) {
            builder.include(latLng)
        }
        val bounds = builder.build()
        val left: Int = 50.dpToPx()
        val bottom: Int = 280.dpToPx()
        val top: Int = 100.dpToPx()
        amap?.let {
            it.animateCamera(
                CameraUpdateFactory.newLatLngBoundsRect(
                    builder.build(),
                    left,
                    left,
                    top,
                    bottom
                )
            )
        }
    }

    private fun showData(model: ScheduleDataModel) {
        //执行下标
        var index = checkIndex(model.order)
        //接人还是送人
        var type = checkStatus(model.order)

        if (!type) {
            orderRunMtb.centerText.text = "开始接第${index + 1}位"
        } else {
            orderRunMtb.centerText.text = "开始送第${index + 1}位"
        }

        var passengerModel = model.order[index]

        passengerModel.customerAvatar?.let {
            if (it.contains("http") || it.contains("https")) {
                orderHeaderIv.glideWithRoundInto(it, 10)
            } else {
                orderHeaderIv.glideWithRoundInto(Config.IMAGE_SERVER + it, 10)
            }
        } ?: orderHeaderIv.setImageResource(R.drawable.com_icon_passenger)

        orderNameTv.text = "${passengerModel.customerName} ${passengerModel.passengerNum}人"

        orderPhoneIv.setOnClickListener {
            if (!passengerModel.customerPhone.isNullOrBlank()) {
                callPhone(passengerModel.customerPhone.toString())
            }
        }

        if (!type) {
            orderSiteIv.setImageResource(R.drawable.com_icon_start)
        } else {
            orderSiteIv.setImageResource(R.drawable.com_icon_end)
        }

        val stationModel = getSite(type, passengerModel.orderAddress)

        orderSiteTv.text = stationModel?.address

        gotoSiteBtn.visibility = View.GONE
        naviSiteLin.visibility = View.GONE
        loadTypeLin.visibility = View.GONE

        if (passengerModel.status == OrderStatusTypeEnum.ORDER_STATUS_WAIT_START.value ||
            passengerModel.status == OrderStatusTypeEnum.ORDER_STATUS_START.value
        ) {
            gotoSiteBtn.visibility = View.VISIBLE

            gotoSiteBtn.setOnClickListener {
                orderOperationViewModel.gotoBookPlace(
                    Ktx.getInstance().userDataSource.userId,
                    model.id,
                    passengerModel.orderId
                )
            }
        } else if (passengerModel.status == OrderStatusTypeEnum.ORDER_STATUS_PICKUP.value) {
            naviSiteLin.visibility = View.VISIBLE

            orderNavigationLin.setOnClickListener {
                jumpTo<NavigationActivity>{
                    putExtra("order",passengerModel)
                    //1 接人或者送人
                    putExtra("type",1)
                }
            }

            arriveSiteBtn.setOnClickListener {
                orderOperationViewModel.arriveBookPlace(
                    Ktx.getInstance().userDataSource.userId,
                    model.id,
                    passengerModel.orderId
                )
            }

        } else if (passengerModel.status == OrderStatusTypeEnum.ORDER_STATUS_WAITING.value) {
            loadTypeLin.visibility = View.VISIBLE

            loadTypeHasBtn.setOnClickListener {
                orderOperationViewModel.takeOverCheck(
                    Ktx.getInstance().userDataSource.userId,
                    model.id,
                    passengerModel.orderId,
                    3
                )
            }

            loadTypeNoBtn.setOnClickListener {
                orderOperationViewModel.takeOverCheck(
                    Ktx.getInstance().userDataSource.userId,
                    model.id,
                    passengerModel.orderId,
                    2
                )
            }
        } else if (passengerModel.status == OrderStatusTypeEnum.ORDER_STATUS_HAS_CAR.value) {
            gotoSiteBtn.visibility = View.VISIBLE

            gotoSiteBtn.text = "开始送人"

            gotoSiteBtn.setOnClickListener {
                orderOperationViewModel.gotoDestination(
                    Ktx.getInstance().userDataSource.userId,
                    model.id,
                    passengerModel.orderId
                )
            }
        } else if (passengerModel.status == OrderStatusTypeEnum.ORDER_STATUS_SENDING.value) {
            naviSiteLin.visibility = View.VISIBLE

            arriveSiteBtn.text = "已到达乘客下车点"

            orderNavigationLin.setOnClickListener {
                jumpTo<NavigationActivity>{
                    putExtra("order",passengerModel)
                    //1 接人或者送人
                    putExtra("type",1)
                }
            }

            arriveSiteBtn.setOnClickListener {
                orderOperationViewModel.finishOrder(
                    Ktx.getInstance().userDataSource.userId,
                    model.id,
                    passengerModel.orderId
                )
            }
        }

        routePlanByRouteSearch(
            orderOperationViewModel.latLng, null,
            LatLng(stationModel!!.latitude, stationModel!!.longitude)
        )

        addMarker(LatLng(stationModel!!.latitude, stationModel!!.longitude),passengerModel.customerPhone,null)
    }

    /**
     * 获取上车点 或者下车点
     */
    fun getSite(type: Boolean, list: MutableList<StationModel>?): StationModel? {

        var stationModel: StationModel? = null

        list?.forEach {
            if (it.type == 1 && !type) {
                stationModel = it
            } else if (it.type == 2 && type) {
                stationModel = it
            }
        }
        return stationModel
    }

    /**
     * 判断上车 或者下车下标
     */
    fun checkIndex(list: List<PassengerModel>): Int {
        for (i in list.indices) {
            if (list[i].status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                return i
            }
        }
        for (i in list.indices) {
            if (list[i].status > OrderStatusTypeEnum.ORDER_STATUS_SKIP.value && list[i].status < OrderStatusTypeEnum.ORDER_STATUS_COMPLETE.value) {
                return i
            }
        }
        return -1
    }

    /**
     * 判断上车还是下车  ture 下车 false上车
     */
    private fun checkStatus(list: List<PassengerModel>): Boolean {
        for (i in list.indices) {
            if (list[i].status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                return false
            }
        }
        return true
    }

    override fun initData(isFirstInit: Boolean) {

    }



    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        orderDriverId = intent.getLongExtra("orderDriverId", 0)

    }


    fun addMarker(
        latLng: LatLng?,
        phone: String,
        distance: Double?
    ) {
        val markerOption = MarkerOptions()
        markerOption.position(latLng)
        //设置Marker可拖动
        markerOption.draggable(false)
        val view: View = LayoutInflater.from(this).inflate(R.layout.layout_passenger_maker, null)
        var makerPhoneTV = view.findViewById<TextView>(R.id.makerPhoneTV)
        makerPhoneTV.text = "尾号${phone.substring(phone.length - 4, phone.length)}"

        markerOption.icon(BitmapDescriptorFactory.fromView(view))
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.isFlat = true
        amap?.let {
            it.addMarker(markerOption)
        }
    }

    var routeSearch : RouteSearch? =null

    /**
     * 路径规划
     */
    fun routePlanByRouteSearch(
        start: LatLng?,
        latLngs: MutableList<LatLng>?,
        end: LatLng?
    ) {
        if (start == null || end == null) {
            return
        }
        if (routeSearch == null){
            routeSearch = RouteSearch(application)
            routeSearch?.setRouteSearchListener(object : RouteSearch.OnRouteSearchListener {
                override fun onBusRouteSearched(busRouteResult: BusRouteResult, i: Int) {}
                override fun onDriveRouteSearched(
                    driveRouteResult: DriveRouteResult,
                    code: Int
                ) {
                    if (code == 1000) {
                        showPath(driveRouteResult)
                    }
                }

                override fun onWalkRouteSearched(
                    walkRouteResult: WalkRouteResult,
                    i: Int
                ) {
                }

                override fun onRideRouteSearched(
                    rideRouteResult: RideRouteResult,
                    i: Int
                ) {
                }
            })
        }

        val startPoint = LatLonPoint(start.latitude, start.longitude)
        val endPoint = LatLonPoint(end.latitude, end.longitude)
        val latLonPoints: MutableList<LatLonPoint> = ArrayList()
        if (latLngs != null && latLngs.isNotEmpty()) {
            for (latLng in latLngs) {
                val point = LatLonPoint(latLng.latitude, latLng.longitude)
                latLonPoints.add(point)
            }
        }
        val fromAndTo = RouteSearch.FromAndTo(startPoint, endPoint)
        val query = RouteSearch.DriveRouteQuery(
            fromAndTo,
            RouteSearch.DRIVING_MULTI_STRATEGY_FASTEST_SHORTEST_AVOID_CONGESTION,
            latLonPoints,
            null,
            ""
        )
        routeSearch?.calculateDriveRouteAsyn(query)

        var latLngs = mutableListOf<LatLng>()
        latLngs.add(start)
        latLngs.add(end)
        boundsZoom(latLngs)
    }

     var drivingRouteOverlay: DrivingRouteOverlay? = null

    fun showPath(result: DriveRouteResult) {
        if (drivingRouteOverlay != null) {
            drivingRouteOverlay?.removeFromMap()
        }
        drivingRouteOverlay = DrivingRouteOverlay(
            this, amap,
            result.paths[0], result.startPos
            , result.targetPos, null
        )
        drivingRouteOverlay?.setIsColorfulline(false)
        drivingRouteOverlay?.setNodeIconVisibility(false) //隐藏转弯的节点

        drivingRouteOverlay?.addToMap()

        var dis = 0f
        var time = 0f

        for (step in result.paths[0].steps) {
            dis += step.distance
            time += step.duration
        }
//        showLeft(dis.toInt(), time.toInt())
    }

    override fun onResume() {
        super.onResume()
        orderOperationViewModel.qureyScheduleById(orderDriverId)
    }
}