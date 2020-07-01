package com.rvakva.bus.home.viewmodel.work

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.common.model.ScheduleDataModel
import com.rvakva.bus.home.HomeService
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserAuditEnum
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import com.rvakva.travel.devkit.retrofit.result.EmResult
import kotlinx.coroutines.Job
import java.lang.Exception

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午6:01
 */
class WorkFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val orderListLiveData by RequestLiveData<EmResult<List<ScheduleDataModel>>>()

    val grabLiveData by RequestLiveData<EmResult<ScheduleDataModel>>()

    val countDownOrderListLiveData = MutableLiveData<Int>()

    var userInfo: UserInfoModel? = null

    var isRequest = false

    fun checkRequest(block: () -> Unit) {
        if (!isRequest) {
            block()
        }
    }


    private var job: Job? = null


    fun cancelJob() = job?.cancel()

    fun countDownTimer(scheduleStatusTypeEnum: ScheduleStatusTypeEnum) {
        if (scheduleStatusTypeEnum == ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW) {
            cancelJob()
            job = launchRepeat(delayTimeMillis = 1_000) { count ->
                countDownOrderListLiveData.postValue(0)
            }
        }
    }


    suspend fun changeUserAuditStatus(userAuditEnum: UserAuditEnum) {
        userInfo?.let {
            it.applyStatus = userAuditEnum.status
            Ktx.getInstance().userDataSource.insertUserInfo(it)
        }
    }

    fun checkError(e: Exception) {
        launch {
            (e as? ApiException)?.let {
                when (it.code) {
                    ApiConstant.ERROR_CODE_DRIVER_STATUS_NOT_AUDIT -> {
                        changeUserAuditStatus(UserAuditEnum.NON_IDENTITY)
                    }
                    ApiConstant.ERROR_CODE_DRIVER_STATUS_IN_AUDIT -> {
                        changeUserAuditStatus(UserAuditEnum.PROGRESSING)
                    }
                    ApiConstant.ERROR_CODE_DRIVER_STATUS_AUDIT_REFUSE -> {
                        changeUserAuditStatus(UserAuditEnum.FAIL)
                    }
                }
            }
        }
    }

//    val actionRepository = ActionRepository()
//
//    fun grabOrder(id: Long) {
//        launchRequest(block = {
//            actionRepository.grabOrder(id)
//        }, requestLiveData = grabLiveData, showToastBar = false)
//    }

    fun getOrderList(
        scheduleStatusTypeEnum: ScheduleStatusTypeEnum? = null,
        page:Int
    ) {
        launchRequest(block = {
            when (scheduleStatusTypeEnum) {
                ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW ->
                    ApiManager.getInstance().createService(HomeService::class.java)
                        .getOrderList(
                            Ktx.getInstance().userDataSource.userId,
                            page,
                            10,
                            scheduleStatusTypeEnum.value
                        )
                ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW ->
                    ApiManager.getInstance().createService(HomeService::class.java)
                        .getOrderList(
                            Ktx.getInstance().userDataSource.userId,
                            page,
                            10,
                            scheduleStatusTypeEnum.value
                        )

                else -> {
                    ApiManager.getInstance().createService(HomeService::class.java)
                        .getOrderList(
                            Ktx.getInstance().userDataSource.userId,
                            page,
                            10,
                            scheduleStatusTypeEnum!!.value
                        )
                }
            }
                .requestMap()
        }, requestLiveData = orderListLiveData, showToastBar = false)
    }

}