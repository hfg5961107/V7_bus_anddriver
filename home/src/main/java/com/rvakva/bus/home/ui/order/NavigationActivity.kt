package com.rvakva.bus.home.ui.order

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviListener
import com.amap.api.navi.AMapNaviViewListener
import com.amap.api.navi.enums.NaviType
import com.amap.api.navi.model.*
import com.autonavi.tbt.TrafficFacilityInfo
import com.rvakva.bus.common.model.OrderStatusTypeEnum
import com.rvakva.bus.common.model.PassengerModel
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.common.model.StationModel
import com.rvakva.bus.home.R
import com.rvakva.bus.home.viewmodel.order.OrderOperationViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.callPhone
import com.rvakva.travel.devkit.expend.glideWithRoundInto
import com.rvakva.travel.devkit.expend.jumpTo
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_navigation.*


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 下午5:10
 */
class NavigationActivity : KtxActivity(R.layout.activity_navigation), AMapNaviListener,
        AMapNaviViewListener {

    var mAMapNavi: AMapNavi? = null

    var mEndLatlng: NaviLatLng? = null
    var mStartLatlng: NaviLatLng? = null
    val sList: MutableList<NaviLatLng> = mutableListOf()
    val eList: MutableList<NaviLatLng> = mutableListOf()

    /**
     * 导航模式  默认1
     */
    private val naviMode = 1

    override fun initTitle() {

    }

    private val orderOperationViewModel by viewModels<OrderOperationViewModel>()

    override fun initView(savedInstanceState: Bundle?) {

        naviView.onCreate(savedInstanceState)
        naviView.setAMapNaviViewListener(this)
        mAMapNavi = AMapNavi.getInstance(applicationContext)
        mAMapNavi?.addAMapNaviListener(this)
        mAMapNavi?.setUseInnerVoice(true)

        KtxViewModel.locationLiveData.value?.let {
            mStartLatlng = NaviLatLng(it.latitude, it.longitude)

        }

        //1 接人或者送人  2站点
        var type = intent.getIntExtra("type", 0)
        if (type == 1) {
            (intent.getSerializableExtra("order") as PassengerModel).let {model->

                var stationModel: StationModel? = null

                if (model.status > OrderStatusTypeEnum.ORDER_STATUS_SKIP.value) {
                    model.orderAddress?.forEach {
                        if (it.type == 2) {
                            stationModel = it

                            naviOperationBtn.text = "已到达乘客下车点"
                            naviOperationBtn.setOnClickListener {
                                orderOperationViewModel.finishOrder(
                                        Ktx.getInstance().userDataSource.userId,
                                        model.orderDriverId,
                                        stationModel?.orderId
                                )
                            }
                        }
                    }
                    naviSiteIv.setImageResource(R.drawable.com_icon_end)
                } else if (model.status < OrderStatusTypeEnum.ORDER_STATUS_WAITING.value) {
                    model.orderAddress?.forEach {
                        if (it.type == 1) {
                            stationModel = it

                            naviOperationBtn.text = "已到达乘客上车点"
                            naviOperationBtn.setOnClickListener {
                                orderOperationViewModel.arriveBookPlace(
                                        Ktx.getInstance().userDataSource.userId,
                                        model!!.orderDriverId,
                                        stationModel?.orderId
                                )
                            }
                        }
                    }
                    naviSiteIv.setImageResource(R.drawable.com_icon_start)
                }

                stationModel?.address.loge()

                mEndLatlng = NaviLatLng(stationModel!!.latitude, stationModel!!.longitude)
                naviSiteTv.text = stationModel?.address

                naviPassengerLin.visibility = View.VISIBLE
                model.customerAvatar?.let {
                    if (it.contains("http") || it.contains("https")) {
                        naviHeaderIv.glideWithRoundInto(it, 10)
                    } else {
                        naviHeaderIv.glideWithRoundInto(Config.IMAGE_SERVER + it, 10)
                    }
                } ?: naviHeaderIv.setImageResource(R.drawable.com_icon_passenger)
                naviNameTv.text = model.customerName
                naviPhoneIv.setOnClickListener {
                    if (!model.customerPhone.isNullOrBlank()) {
                        callPhone(model.customerPhone)
                    }
                }
            }
        } else if (type == 2) {
            (intent.getSerializableExtra("schedule") as ScheduleDataModel)?.let {model->

                model.station.forEach {

                    if (it.type == 2) {
                        mEndLatlng = NaviLatLng(it.latitude, it.longitude)
                        naviPassengerLin.visibility = View.GONE
                        naviSiteIv.setImageResource(R.drawable.com_icon_end)
                        naviSiteTv.text = it.address

                        naviOperationBtn.text = "到达目的地"
                        naviOperationBtn.setOnClickListener {
                            naviOperationBtn.setOnClickListener {
                                orderOperationViewModel.finishOrder(
                                        Ktx.getInstance().userDataSource.userId,
                                        model!!.id,
                                        null
                                )
                            }
                        }
                        return
                    }
                }
            }
        } else {
            finish()
            ToastBar.show("数据出现错误,请重试...")
            return
        }
    }

    override fun onResume() {
        super.onResume()
        naviView.onResume()
    }

    override fun onPause() {
        super.onPause()
        naviView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (naviView != null) {
            naviView.onDestroy();
        }
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        //mAMapNavi是全局的，执行订单页面还需要用，所以这里不能销毁资源
        mAMapNavi?.let {
            mAMapNavi?.stopNavi()
            mAMapNavi?.destroy()
        }
    }

    override fun initObserver() {
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
                                        finish()
                                    }
                                }
                            } else {
                                finish()
                            }
                        },
                        failBlock = {
                           finish()
                        }
                )
        )
    }

    override fun initData(isFirstInit: Boolean) {

    }

    override fun onNaviInfoUpdate(p0: NaviInfo?) {

    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {
        mAMapNavi?.startNavi(NaviType.GPS);//驾车导航
    }

    override fun onCalculateRouteSuccess(p0: AMapCalcRouteResult?) {

    }

    override fun onCalculateRouteFailure(p0: Int) {

    }

    override fun onCalculateRouteFailure(p0: AMapCalcRouteResult?) {

    }

    override fun onServiceAreaUpdate(p0: Array<out AMapServiceAreaInfo>?) {

    }

    override fun onEndEmulatorNavi() {

    }

    override fun onArrivedWayPoint(p0: Int) {

    }

    override fun onArriveDestination() {

    }

    override fun onPlayRing(p0: Int) {

    }

    override fun onTrafficStatusUpdate() {

    }

    override fun onGpsOpenStatus(p0: Boolean) {

    }

    override fun updateAimlessModeCongestionInfo(p0: AimLessModeCongestionInfo?) {

    }

    override fun showCross(p0: AMapNaviCross?) {

    }

    override fun onGetNavigationText(p0: Int, p1: String?) {

    }

    override fun onGetNavigationText(p0: String?) {

    }

    override fun updateAimlessModeStatistics(p0: AimLessModeStat?) {

    }

    override fun hideCross() {

    }

    override fun onInitNaviFailure() {
        "初始化导航失败".loge("NaviActivity")
    }

    override fun onInitNaviSuccess() {
        mStartLatlng?.let {
            sList.add(it)
        }
        mEndLatlng?.let {
            eList.add(it)
        }
        mAMapNavi?.calculateDriveRoute(sList, eList, null, 2)
    }

    override fun onReCalculateRouteForTrafficJam() {

    }

    override fun updateIntervalCameraInfo(
            p0: AMapNaviCameraInfo?,
            p1: AMapNaviCameraInfo?,
            p2: Int
    ) {

    }

    override fun hideLaneInfo() {

    }

    override fun onNaviInfoUpdated(p0: AMapNaviInfo?) {

    }

    override fun showModeCross(p0: AMapModelCross?) {

    }

    override fun updateCameraInfo(p0: Array<out AMapNaviCameraInfo>?) {

    }

    override fun hideModeCross() {

    }

    override fun onLocationChange(p0: AMapNaviLocation?) {

    }

    override fun onReCalculateRouteForYaw() {

    }

    override fun onStartNavi(p0: Int) {

    }

    override fun notifyParallelRoad(p0: Int) {

    }

    override fun OnUpdateTrafficFacility(p0: Array<out AMapNaviTrafficFacilityInfo>?) {

    }

    override fun OnUpdateTrafficFacility(p0: AMapNaviTrafficFacilityInfo?) {

    }

    override fun OnUpdateTrafficFacility(p0: TrafficFacilityInfo?) {

    }

    override fun onNaviRouteNotify(p0: AMapNaviRouteNotifyData?) {

    }

    override fun showLaneInfo(p0: Array<out AMapLaneInfo>?, p1: ByteArray?, p2: ByteArray?) {

    }

    override fun showLaneInfo(p0: AMapLaneInfo?) {

    }

    override fun onNaviTurnClick() {

    }

    override fun onScanViewButtonClick() {

    }

    override fun onLockMap(p0: Boolean) {

    }

    override fun onMapTypeChanged(p0: Int) {

    }

    override fun onNaviCancel() {
        finish();
    }

    override fun onNaviViewLoaded() {

    }

    override fun onNaviBackClick(): Boolean {
        return false;
    }

    override fun onNaviMapMode(p0: Int) {

    }

    override fun onNextRoadClick() {

    }

    override fun onNaviViewShowMode(p0: Int) {

    }

    override fun onNaviSetting() {

    }


}