package com.rvakva.travel.devkit

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.rvakva.travel.devkit.datasource.AppKeyDataSource
import com.rvakva.travel.devkit.datasource.UserDataSource
import com.rvakva.travel.devkit.exception.CrashHandler
import com.rvakva.travel.devkit.retrofit.ApiManager
import com.rvakva.travel.devkit.x.*
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:    初始化配置
 * @Author:         胡峰
 * @CreateDate:     2020/4/27 下午3:18
 */
class Ktx private constructor() {

    object SingletonHolder {
        val INSTANCE = Ktx()
    }

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    val activityManager by lazy {
        XActivityManager.getInstance()
    }

    val mmkv by lazy {
        XMMKV.getInstance()
    }

    val userDataSource by lazy {
        UserDataSource.getInstance();
    }
    val appKeyDataSource by lazy {
        AppKeyDataSource.getInstance()
    }

    lateinit var weChatApi: IWXAPI
        private set

    lateinit var app: Application

    fun initialize(application: Application) {
        application.let {
            app = it

            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            if (BuildConfig.DEBUG) {
                // 打印日志
                ARouter.openLog()
                // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                ARouter.openDebug()
            }
            ARouter.init(it)
            XMMKV.initialize(it)
            XDataBase.initialize(it)
            ApiManager.getInstance().initialize()

            //注册ActManager
            it.registerActivityLifecycleCallbacks(XActivityLifeCycleCallback())
            weChatApi = WXAPIFactory.createWXAPI(application, Config.WECHAT_ID, false)

            //注册APP生命周期管理
            ProcessLifecycleOwner.get().lifecycle.addObserver(XAppLifeCycleObserver())

            CrashHandler.getInstance().init(it)
        }
    }

}