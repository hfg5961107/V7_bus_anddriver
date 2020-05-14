package com.rvakva.bus.common.viewmodel

import android.app.Application
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import com.rvakva.bus.common.CommonService
import com.rvakva.travel.devkit.expend.launchRequest
import com.rvakva.travel.devkit.expend.writeIn
import com.rvakva.travel.devkit.livedata.RequestLiveData
import com.rvakva.travel.devkit.retrofit.ApiManager
import java.io.File

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:29
 */
class UpdateDialogViewModel(application: Application) : AndroidViewModel(application) {

    val downloadLiveData by RequestLiveData<File>()

    fun download(url: String) {
        launchRequest(block = {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                url.substringAfterLast("/")
            ).let { targetFile ->
                if (targetFile.exists()) {
                    targetFile
                } else {
                    ApiManager.getInstance().createService(CommonService::class.java).download(url)
                        .let {
                            targetFile.writeIn(it.byteStream())
                            targetFile
                        }
                }
            }
        }, requestLiveData = downloadLiveData)
    }
}