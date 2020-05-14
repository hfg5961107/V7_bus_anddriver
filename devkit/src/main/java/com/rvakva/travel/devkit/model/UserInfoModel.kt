package com.rvakva.travel.devkit.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/4/27 下午4:07
 */
enum class UserAuditEnum(val status: Int) {
    PROGRESSING(1), SUCCESS(2), FAIL(3), NON_IDENTITY(4)
}

@Entity(tableName = "userInfo")
class UserInfoModel(
    @PrimaryKey @ColumnInfo(name = "innerId") var innerId: Long = 0,
    @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "account") var account: Long = 0,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "idCard") var idCard: String? = null,
    @ColumnInfo(name = "idCardFront") var idCardFront: String? = null,
    @ColumnInfo(name = "idCardBack") var idCardBack: String? = null,

    /**
     * 余额
     */
    @ColumnInfo(name = "balance") var balance: Double = 0.0,
    /**
     *  1-听单中 2-休息中
     */
    @ColumnInfo(name = "status") var status: Int = 0,
    /**
     * 头像
     */
    @ColumnInfo(name = "headPortrait") var headPortrait: String? = null,
    /**
     * 司机服务热线
     */
    @ColumnInfo(name = "driverServicePhone") var driverServicePhone: String? = null,

    /**
     * 审核状态 1-审核中 2-审核通过 3-审核拒绝 4-未认证
     */
    @ColumnInfo(name = "auditStatus") var auditStatus: Int = 0,
    @ColumnInfo(name = "auditRemark") var auditRemark: String? = null
) : IModel
