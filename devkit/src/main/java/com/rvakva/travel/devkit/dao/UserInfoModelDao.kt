package com.rvakva.travel.devkit.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rvakva.travel.devkit.model.UserInfoModel


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/4/27 下午4:48
 */
@Dao
interface UserInfoModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userInfo: UserInfoModel)

    @Query("SELECT * FROM userInfo LIMIT 1 OFFSET 0")
    fun getUserInfo(): LiveData<UserInfoModel>

    @Query("DELETE FROM userInfo")
    fun deleteAllUsers()

}