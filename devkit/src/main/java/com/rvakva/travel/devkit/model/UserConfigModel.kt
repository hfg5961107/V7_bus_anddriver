package com.rvakva.travel.devkit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午2:49
 */
@Entity(tableName = "userConfig")
class UserConfigModel(
    @PrimaryKey @ColumnInfo(name = "innerId") var innerId: Long = 0,
    @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "receiptLimit") var receiptLimit: Long = 0,
    @ColumnInfo(name = "companyName") var companyName: String? = "",
    @ColumnInfo(name = "driverHelpPhone") var driverHelpPhone: String? = "",
    @ColumnInfo(name = "balance") var balance: Double = 0.0,
    @ColumnInfo(name = "limitDistance") var limitDistance: Double = 0.0
) : IModel