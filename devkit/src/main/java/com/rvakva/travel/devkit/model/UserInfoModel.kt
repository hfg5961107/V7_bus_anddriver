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
    NON_IDENTITY(1),PROGRESSING(2), SUCCESS(3), FAIL(4)
}

@Entity(tableName = "userInfo")
class UserInfoModel(
    @PrimaryKey @ColumnInfo(name = "innerId") var innerId: Long = 0,
    @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "account") var account: Long = 0,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "idCard") var idCard: String? = null,
    /**
     * 身份证正面
     */
    @ColumnInfo(name = "idCardFrontPath") var idCardFrontPath: String? = null,
    /**
     * 身份证背面
     */
    @ColumnInfo(name = "idCardBackPath") var idCardBackPath: String? = null,
    /**
     * 驾驶证
     */
    @ColumnInfo(name = "driverLicensePath") var driverLicensePath: String? = null,

    /**
     * 从业资格证
     */
    @ColumnInfo(name = "qualificationsPath") var qualificationsPath: String? = null,

    /**
     * 其他附件地址
     */
    @ColumnInfo(name = "attachmentPath") var attachmentPath: String? = null,
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
     * 审核状态 1-待认证，2-认证中，3-已认证，4-认证失败
     */
    @ColumnInfo(name = "auditStatus") var applyStatus: Int = 0,

    /**
     * 审核时间
     */
    @ColumnInfo(name = "applyTime") var applyTime: Long = 0,

    /**
     * 拒绝原因
     */
    @ColumnInfo(name = "auditRemark") var refusalReason: String? = null

) : IModel
