package com.rvakva.bus.home.ui.order

import android.os.Bundle
import com.amap.api.navi.AMapNaviListener
import com.amap.api.navi.AMapNaviViewListener
import com.amap.api.navi.model.*
import com.autonavi.tbt.TrafficFacilityInfo
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.base.KtxActivity

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/1 下午5:10
 */
class NavigationActivity : KtxActivity(R.layout.activity_navigation), AMapNaviListener, AMapNaviViewListener {

    override fun initTitle() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }

    override fun onNaviInfoUpdate(p0: NaviInfo?) {

    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {

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

    }

    override fun onInitNaviSuccess() {

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