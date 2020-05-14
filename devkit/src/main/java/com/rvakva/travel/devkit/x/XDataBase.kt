package com.rvakva.travel.devkit.x

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.rvakva.travel.devkit.dao.UserConfigModelDao
import com.rvakva.travel.devkit.dao.UserInfoModelDao
import com.rvakva.travel.devkit.model.UserConfigModel
import com.rvakva.travel.devkit.model.UserInfoModel
import java.lang.Exception
import java.lang.NullPointerException

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:    数据库创建基类
 * @Author:         胡峰
 * @CreateDate:     2020/4/27 下午4:03
 */
@Database(entities = [UserInfoModel::class, UserConfigModel::class],version = 1, exportSchema = false)
abstract class XDataBase : RoomDatabase() {

    abstract fun userInfoModelDao(): UserInfoModelDao

    abstract fun userConfigModelDao(): UserConfigModelDao

    companion object {
        @Volatile
        private var INSTANCE : XDataBase? = null
        //internal 同一模块下可见   A ?: B  A!=null -->A   A==null -->B
        internal fun initialize(context: Context){
            INSTANCE ?: synchronized(this){
                INSTANCE ?: createDataBase(context).let { INSTANCE = it }
            }
        }


        private fun createDataBase(context: Context) =
                Room.databaseBuilder(context,XDataBase::class.java,"v5DataBase").build()

        fun getInstance() = INSTANCE ?: throw NullPointerException("Have you invoke initialize() before111111?")
    }



}