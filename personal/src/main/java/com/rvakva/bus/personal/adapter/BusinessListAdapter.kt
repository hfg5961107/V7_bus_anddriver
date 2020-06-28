package com.rvakva.bus.personal.adapter

import com.rvakva.bus.personal.model.BusinessListModel
import com.sherloki.simpleadapter.adapter.SimpleMultipleProAdapter

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author: lch
 * @Date: 2020/6/28 09:49
 **/
class BusinessListAdapter : SimpleMultipleProAdapter<BusinessListModel.DataBean>() {

    override fun addItemProvider() {
        addItemProvider(BusinessListProvider())
    }

    override fun getItemType(data: List<BusinessListModel.DataBean>, position: Int) = 0

}