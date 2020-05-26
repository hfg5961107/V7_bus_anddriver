package com.rvakva.travel.devkit.mqtt

import com.rvakva.travel.devkit.model.IModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午9:57
 */
class MqttConfigModel(
    var userName: String? = null,
    var password: String? = null,
    var broker: String? = null,
    var portTcp: Int = 0,
    var topic: String? = null
) : IModel {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MqttConfigModel

        if (userName != other.userName) return false
        if (password != other.password) return false
        if (broker != other.broker) return false
        if (portTcp != other.portTcp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userName?.hashCode() ?: 0
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (broker?.hashCode() ?: 0)
        result = 31 * result + portTcp
        return result
    }
}