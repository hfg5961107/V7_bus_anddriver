package com.rvakva.travel.devkit.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rvakva.travel.devkit.model.UserConfigModel

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午2:48
 */
@Dao
interface UserConfigModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userConfig: UserConfigModel)

    @Query("SELECT * FROM userConfig LIMIT 1 OFFSET 0")
    fun getUserConfig(): LiveData<UserConfigModel>

    @Query("DELETE FROM userConfig")
    fun deleteAllConfigs()
}