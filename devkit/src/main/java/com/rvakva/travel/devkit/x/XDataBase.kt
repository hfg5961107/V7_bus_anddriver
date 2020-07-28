package com.rvakva.travel.devkit.x

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rvakva.travel.devkit.dao.UserConfigModelDao
import com.rvakva.travel.devkit.dao.UserInfoModelDao
import com.rvakva.travel.devkit.model.UserConfigModel
import com.rvakva.travel.devkit.model.UserInfoModel


/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:    数据库创建基类
 * @Author:         胡峰
 * @CreateDate:     2020/4/27 下午4:03
 */
@Database(
    entities = [UserInfoModel::class, UserConfigModel::class],
    version = 3,
    exportSchema = false
)
abstract class XDataBase : RoomDatabase() {

    abstract fun userInfoModelDao(): UserInfoModelDao

    abstract fun userConfigModelDao(): UserConfigModelDao

    companion object {
        @Volatile
        private var INSTANCE: XDataBase? = null

        //internal 同一模块下可见   A ?: B  A!=null -->A   A==null -->B
        internal fun initialize(context: Context) {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDataBase(context).let { INSTANCE = it }
            }
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(context, XDataBase::class.java, "v5DataBase")
                .addMigrations(Migration_1_2())
                .addMigrations(Migration_2_3())
                .build()

        fun getInstance() =
            INSTANCE ?: throw NullPointerException("Have you invoke initialize() before?")

        fun Migration_1_2(): Migration? {
            return object : Migration(1, 2) {
                override fun migrate(@NonNull db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE userInfo ADD COLUMN qualificationsPath TEXT")
                }
            }
        }

        fun Migration_2_3(): Migration? {
            return object : Migration(2, 3) {
                override fun migrate(@NonNull db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE userInfo ADD COLUMN driverRecharge INTEGER NOT NULL DEFAULT 2")
                    db.execSQL("ALTER TABLE userInfo ADD COLUMN vehicleId INTEGER NOT NULL DEFAULT 0")
                    db.execSQL("ALTER TABLE userInfo ADD COLUMN licenseNo TEXT")
                    db.execSQL("ALTER TABLE userInfo ADD COLUMN licenseColor TEXT")
                }
            }
        }
    }


}