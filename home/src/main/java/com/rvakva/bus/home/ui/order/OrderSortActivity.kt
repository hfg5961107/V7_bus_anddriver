package com.rvakva.bus.home.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.route.*
import com.google.gson.Gson
import com.rvakva.bus.common.base.MapActivity
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.StationModel
import com.rvakva.bus.common.widget.overlay.DrivingRouteOverlay
import com.rvakva.bus.home.R
import com.rvakva.bus.home.model.SortModel
import com.rvakva.bus.home.ui.adapter.SequenceAdapter
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.bus.home.weiget.ItemDragCallback
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.dpToPx
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_order_sort.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/10 上午11:22
 */
class OrderSortActivity : MapActivity(R.layout.activity_order_sort) {


    val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    var orderDriverId: Long = 0

    lateinit var adapter: SequenceAdapter

    override val mapView: MapView
        get() = orderSortMv

    override fun initTitle() {
        orderSortMtb.let {
            it.leftTv.setOnClickListener { finish() }
        }
    }

    override fun initObserver() {
        orderOperationViewModel.orderLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    it.data?.let { list ->
                        showData(list)
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

        orderOperationViewModel.changeSortLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    finish()
                },
                failBlock = {

                }
            )
        )
    }

    private fun showData(list: MutableList<PassengerModel>) {
        var type = checkStatus(list)

        if (!type) {
            orderSortHintTv.text = "拖动以调整接人顺序"
            orderSortMtb.centerText.text = "接人行程规划"
        } else {
            orderSortHintTv.text = "拖动以调整送人顺序"
            orderSortMtb.centerText.text = "送人行程规划"
        }

        adapter = SequenceAdapter(this)
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        orderSortRv.layoutManager = manager
        orderSortRv.adapter = adapter

        val touchHelper = ItemDragCallback(adapter)
        var itemTouchHelper = ItemTouchHelper(touchHelper)
        itemTouchHelper.attachToRecyclerView(orderSortRv)

        adapter.setItemTouchHelper(itemTouchHelper)

        routeSearchByLatLng(type, list)

        addCarItem(list)

        adapter.setDataList(list)

        adapter.setOnItemMoveListener(object : SequenceAdapter.OnItemMoveListener {
            override fun onMove() {
                var listData = mutableListOf<PassengerModel>()
                adapter.data.forEach {
                    if (it.type != 1) {
                        listData.add(it)
                    }
                }
                routeSearchByLatLng(type, listData)

                sorts.clear()
                for (i in listData.indices) {
                    var model = SortModel()
                    model.orderId = listData[i].orderId
                    model.sort = i
                    sorts.add(model)
                }
            }
        })
        orderSortBtn.setOnClickListener {
            Gson().toJson(sorts).loge()
            orderOperationViewModel.changeOrderList(
                Ktx.getInstance().userDataSource.userId,
                orderDriverId,
                Gson().toJson(sorts)
            )
        }
    }

    var sorts = mutableListOf<SortModel>()

    private fun addCarItem(list: MutableList<PassengerModel>) {
        if (!checkStatus(list)) {
            for (i in list.indices) {
                if (list[i].status < OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                    var passengerModel = PassengerModel()
                    passengerModel.type = 1
                    passengerModel.status = OrderStatusTypeEnum.ORDER_STATUS_SKIP.value
                    list.add(i, passengerModel)
                    return
                }
            }
        } else {
            for (i in list.indices) {
                if (list[i].status > OrderStatusTypeEnum.ORDER_STATUS_SKIP.value && list[i].status < OrderStatusTypeEnum.ORDER_STATUS_COMPLETE.value) {
                    var passengerModel = PassengerModel()
                    passengerModel.type = 1
                    passengerModel.status = OrderStatusTypeEnum.ORDER_STATUS_SKIP.value
                    list.add(i, passengerModel)
                    return
                }
            }
        }
    }

    /**
     * 添加maker 规划路径 设置缩放
     */
    fun routeSearchByLatLng(type: Boolean, list: MutableList<PassengerModel>) {
        val latLngs: MutableList<LatLng> = ArrayList()
        for (passenger in list) {
            var latLng = LatLng(
                getSite(type, passenger.orderAddress)!!.latitude,
                getSite(type, passenger.orderAddress)!!.longitude
            )
            addMarker(
                latLng,
                passenger.shiftNo,
                passenger.passengerNum,
                passenger.customerPhone
            )
            latLngs.add(latLng)
        }
        //规划
        routePlanByRouteSearch(
            orderOperationViewModel.latLng,
            latLngs,
            latLngs[latLngs.size - 1]
        )
        //缩放
        orderOperationViewModel.latLng?.let {
            latLngs.add(it)
        }
        boundsZoom(latLngs)
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

        orderOperationViewModel.qureyOrderList(orderDriverId)
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


    /**
     * 添加数字标号种类的marker
     *
     * @param latLng
     * @param flag
     */
    fun addMarker(
        latLng: LatLng?,
        num: Int,
        ticketNumber: Int,
        phone: String
    ) {
        val markerOption = MarkerOptions()
        markerOption.position(latLng)
        //设置Marker可拖动
        markerOption.draggable(false)
        val view: View = LayoutInflater.from(this).inflate(R.layout.layout_custom_maker, null)
        val makerNumTv = view.findViewById<TextView>(R.id.makerNumTv)
        val makerNumNameTV = view.findViewById<TextView>(R.id.makerNumNameTV)
        val makerTicketNumTv = view.findViewById<TextView>(R.id.makerTicketNumTv)

        makerNumTv.text = "$num"
        makerNumNameTV.text = "尾号${phone.substring(phone.length - 4, phone.length)}"
        makerTicketNumTv.text = "/ ${ticketNumber}人"

        makerNumTv.setBackgroundResource(R.drawable.com_oval_blue_bg)

        markerOption.icon(BitmapDescriptorFactory.fromView(view))
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.isFlat = true
        amap?.let {
            it.addMarker(markerOption)
        }
    }

    var routeSearch: RouteSearch? = null

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
        if (routeSearch == null) {
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

        for (step in result.getPaths().get(0).getSteps()) {
            dis += step.distance
            time += step.duration
        }
    }
}