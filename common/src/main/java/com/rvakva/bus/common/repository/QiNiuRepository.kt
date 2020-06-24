package com.rvakva.bus.common.repository

import com.rvakva.bus.common.CommonService
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.expend.requestMap
import com.rvakva.travel.devkit.retrofit.ApiManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 下午5:02
 */
class QiNiuRepository {

    suspend fun getQiNiuToken() =
        ApiManager.getInstance().createService(CommonService::class.java)
            .getQnyToken()
            .requestMap()

    suspend fun uploadPic(
        token: String,
        path: String,
        mediaType: String = "image/jpg"
    ) =
        ApiManager.getInstance().createService(CommonService::class.java)
            .uploadPic(Config.HOST_UP_PIC, MultipartBody.Builder().apply {
                setType(MultipartBody.FORM)
                addFormDataPart("token", token)
                File(path).let {
                    addFormDataPart(
                        "file",
                        it.name,
                        it.asRequestBody(mediaType.toMediaType())
                    )
                }
            }.build())
            .requestMap(errorCode = 0)

}