package com.rvakva.bus.common.model

import com.google.gson.annotations.SerializedName
import com.rvakva.travel.devkit.model.IModel
import java.io.Serializable

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午2:54
 */
class AppInfoModel(
    val appVersionCode: Int = 0,
    val appVersionName: String = "",
    @SerializedName("name") val url: String = "",
    @SerializedName("log") val content: String = "",
    val type: Int = 0
) : IModel, Serializable