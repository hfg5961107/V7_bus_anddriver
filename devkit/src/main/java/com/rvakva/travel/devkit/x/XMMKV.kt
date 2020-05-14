package com.rvakva.travel.devkit.x

import android.content.Context
import com.tencent.mmkv.MMKV
import java.lang.NullPointerException

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/7 下午3:07
 */
class XMMKV {

    companion object{
        @Volatile
        private var INSTANCE : XMMKV? = null

        fun initialize(context: Context){
            INSTANCE ?: synchronized(this){
                MMKV.initialize(context)
                XMMKV().apply {
                    INSTANCE = this
                }
            }
        }

        internal fun getInstance() = INSTANCE?.let { it } ?: throw NullPointerException("Have you invoke initialize() before?")
    }

    private val mmkv = MMKV.defaultMMKV()

    fun <T> put(key:String,value:T?):XMMKV{
        value?.let {
            mmkv.run {
                when(it){
                    is Int -> mmkv.putInt(key,it)
                    is Float -> mmkv.putFloat(key,it)
                    is Long -> mmkv.putLong(key,it)
                    is String -> mmkv.putString(key,it)
                    is Boolean -> mmkv.putBoolean(key,it)
                    else -> throw RuntimeException("Unsupported Type")
                }
            }
        } ?: mmkv.remove(key)
        return this
    }

    fun remove(key: String):XMMKV{
        mmkv.remove(key)
        return this
    }


    fun clear():XMMKV{
        mmkv.clear()
        return this
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    fun <T> get(key:String, defaultValue:T) = mmkv.run {
        when(defaultValue){
            is Int -> mmkv.getInt(key,defaultValue)
            is Float -> mmkv.getFloat(key,defaultValue)
            is Long -> mmkv.getLong(key,defaultValue)
            is String -> mmkv.getString(key,defaultValue)
            is Boolean -> mmkv.getBoolean(key,defaultValue)
            else -> throw RuntimeException("Unsupported Type")
        } as T
    }
}