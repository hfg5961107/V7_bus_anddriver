package com.rvakva.bus.personal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.extent.uploadTo
import com.rvakva.bus.common.repository.QiNiuRepository
import com.rvakva.bus.common.repository.UserRepository
import com.rvakva.bus.personal.PersonalService
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.model.UserInfoModel
import com.rvakva.travel.devkit.retrofit.ApiConstant
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.retrofit.exception.ApiException
import com.rvakva.travel.devkit.retrofit.result.BaseResult
import com.rvakva.travel.devkit.widget.ToastBar

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午5:26
 */
class UserApplyActivityViewModel(application: Application) : AndroidViewModel(application) {

    var idCardFrontPath = ""
    var idCardBackPath = ""
    var otherOnePath = ""
    var otherTwoPath = ""

    var userInfoModel: UserInfoModel? = null

    val uploadPicLiveData by RequestLiveData<BaseResult>()
    private val qiNiuRepository = QiNiuRepository()
    private val userRepository = UserRepository()

    fun submitUserInfo(
            userName: String,
            idCard: String,
            defaultFrontPath: String = "",
            defaultBackPath: String = "",
            defaultOtherOnePath: String? = null,
            defaultOtherTwoPath: String? = null
    ) {
        val uploadList = mutableListOf<String>()

        var frontPath = if (idCardFrontPath.isNotEmpty()) {
            uploadList.add(idCardFrontPath)
            null
        } else defaultFrontPath

        var backPath = if (idCardBackPath.isNotEmpty()) {
            uploadList.add(idCardBackPath)
            null
        } else defaultBackPath

        var otherOne = if (otherOnePath.isNotEmpty()) {
            uploadList.add(otherOnePath)
            null
        } else defaultOtherOnePath

        var otherTwo = if (otherTwoPath.isNotEmpty()) {
            uploadList.add(otherTwoPath)
            null
        } else defaultOtherTwoPath

        when {
            frontPath?.isEmpty() == true -> {
                ToastBar.show("请上传身份证正面")
            }
            backPath?.isEmpty() == true -> {
                ToastBar.show("请上传身份证背面")
            }

            else ->
                if (uploadList.size > 0) {
                    launchRequest(block = { scope ->
                        qiNiuRepository.getQiNiuToken().data?.let { token ->

                            uploadList.uploadTo(scope) {
                                qiNiuRepository.uploadPic(token, it)
                            }.map {
                                it.hash
                            }.toMutableList()

                        }?.let {
                            if (frontPath == null) {
                                frontPath = it[0]
                                it.removeAt(0)
                            }
                            if (backPath == null) {
                                backPath = it[0]
                                it.removeAt(0)
                            }
                            if (otherOne == null) {
                                if (it.size>0){
                                    otherOne = it[0]
                                    it.removeAt(0)
                                }
                            }
                            if (otherTwo == null) {
                                if (it.size>0) {
                                    otherTwo = it[0]
                                    it.removeAt(0)
                                }
                            }
                            "frontPath:$frontPath,\nbackPath:$backPath,\notherOne:$otherOne,\notherTwo:$otherTwo".loge()
                            commitDriverInfo(
                                    userName, idCard, frontPath, backPath, otherOne, otherTwo
                            )
                        } ?: throw ApiException(
                                ApiConstant.RESPONSE_EMPTY_ERROR,
                                ApiConstant.ERROR_CODE_DATA_IS_EMPTY
                        )
                    }, requestLiveData = uploadPicLiveData, showToastBox = true, toastBoxDesc = "正在提交...")

                } else {
                    launchRequest(block = {
                        commitDriverInfo(
                                userName, idCard, frontPath, backPath, otherOne, otherTwo
                        )
                    }, requestLiveData = uploadPicLiveData, showToastBox = true, toastBoxDesc = "正在提交...")
                }
        }
    }

    var attachmentPat: String? = null

    private suspend fun commitDriverInfo(
            userName: String,
            idCard: String,
            frontPath: String?,
            backPath: String?,
            otherOne: String?,
            otherTwo: String?
    ): BaseResult {
         if ( otherTwo != null) {
            attachmentPat = otherTwo
        }else if ( otherTwo == null){
            attachmentPat = null
        }
        return ApiManager.getInstance().createService(PersonalService::class.java)
                .commitDriverInfo(userName, idCard, frontPath ?: "", backPath ?: "", otherOne ?: "",attachmentPat ?: "")
                .requestMap()
    }
}