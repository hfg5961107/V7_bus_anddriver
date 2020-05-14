package com.rvakva.travel.devkit.x

import android.app.Activity
import java.util.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/4/28 上午10:20
 */
class XActivityManager private constructor(){

    private object SingletonHolder{
        var INSTANCE = XActivityManager()
    }

    companion object{
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private val activityStack:Stack<Activity> = Stack()

    /**
     * activity入栈.
     *
     * @param activity activity实例
     */
    fun push(activity: Activity) {
        activityStack.push(activity)
    }

    /**
     * 将activity从栈中移除.
     */
    fun remove(activity: Activity) {
        activityStack.remove(activity)
    }

    /**
     * finish栈顶activity.
     */
    fun finishTop() {
        if (!activityStack.isEmpty()) {
            activityStack.lastElement()?.finish()
        }
    }

    /**
     * 某个activity是否存在
     */
    fun exist(clazz: Class<*>) =
        activityStack
            .filter { it::class.java == clazz }.isNotEmpty()


    /**
     * finish指定的类.
     */
    fun existByName(clazzName: String) =
        activityStack
            .filter { it::class.java.name == clazzName }.isNotEmpty()


    /**
     * finish指定的类.
     */
    fun finish(clazz: Class<*>) {
        activityStack
            .filter { it::class.java == clazz }
            .forEach { it.finish() }
    }

    /**
     * finish指定的类.
     */
    fun finishByName(clazzName: String) {
        activityStack
            .filter { it::class.java.name == clazzName }
            .forEach { it?.finish() }
    }

    /**
     * finish除了指定的类.
     */
    fun finishBut(clazz: Class<*>) {
        activityStack
            .filter { it::class.java != clazz }
            .forEach { it?.finish() }
    }

    /**
     * finish除了指定的类.
     */
    fun finishButByName(clazzName: String) {
        activityStack
            .filter {
                it::class.java.name != clazzName
            }
            .forEach { it?.finish() }
    }

    /**
     * 关闭所有.
     */
    fun finishAll() {
        activityStack
            .toMutableList()
            .forEach { it?.finish() }
    }
}