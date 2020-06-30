package com.rvakva.travel.devkit.expend

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/30 下午3:20
 */
fun Double.checkIsInt() : String{
    var isInt  = if (this.toString().contains(".")){
        this.toString().split(".")[1].toInt() == 0
    }else{
        true
    }

    return if (isInt){
        (this.toInt()).toString()
    }else{
        this.numberFormat()
    }

}