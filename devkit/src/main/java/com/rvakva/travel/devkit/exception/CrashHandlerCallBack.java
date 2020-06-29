package com.rvakva.travel.devkit.exception;

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 *
 * @Description:
 * @Author: 胡峰
 * @CreateDate: 2020-05-07 16:51
 */
public interface CrashHandlerCallBack {

    void uncaughtException(Thread thread, Throwable throwable);
}
