package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.personal.PersonalService
import com.rvakva.bus.personal.model.BillModel
import com.rvakva.bus.personal.model.BusinessListModel
import com.rvakva.travel.devkit.Ktx
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
 * @CreateDate:     2020/6/2 下午3:01
 */
class BillActivityViewModel(application: Application) : AndroidViewModel(application) {

    val billListLiveData by RequestLiveData<EmResult<List<BillModel>>>()

    val applyCloseLiveData by RequestLiveData<BaseResult>()

    val businessListLiveData by RequestLiveData<EmResult<BusinessListModel>>()

    /**
     * 获取流水明细
     */
    fun getBillList(page: Int) {
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .getBillList(page, 20)
                .requestMap()
        }, requestLiveData = billListLiveData, showToastBar = true)
    }

    /**
     * 司机申请结算
     */
    fun applyClose( fee: String) {
        launchRequest(block = {
                    ApiManager.getInstance().createService(PersonalService::class.java)
                        .applyClose(Ktx.getInstance().userDataSource.userId , fee)
                        .requestMap()
        }, requestLiveData = applyCloseLiveData)
    }

    /**
     * 获取业务流水列表
     */
    fun getFinance(page: Int,startTime: Long,endTime: Long) {
        launchRequest(block = {
            ApiManager.getInstance().createService(PersonalService::class.java)
                .getFinance(page, 20,startTime,endTime)
                .requestMap()
        }, requestLiveData = businessListLiveData, showToastBar = true)
    }


}