package com.rvakva.bus.personal.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.adapter.BusinessListAdapter
import com.rvakva.bus.personal.model.BusinessListModel
import com.rvakva.bus.personal.utils.*
import com.rvakva.bus.personal.viewmodel.BillActivityViewModel
import com.rvakva.bus.personal.widget.MenuPopupWindow
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.aty_business_list.*
import kotlinx.android.synthetic.main.aty_business_list.recyclerviewBill

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:业务流水
 * @Author: lch
 * @Date: 2020/6/24 15:54
 **/
@Route(path = Config.USER_BUSINESS_LIST)
class BusinessListActivity : KtxActivity(R.layout.aty_business_list), RecyclerItemClickListenner {

    val billActivityViewModel by viewModels<BillActivityViewModel>()

    private var menuPopupWindow: MenuPopupWindow? = null

    var startTime: Long = getBeginDayOfMonth()?.time!!//默认本月
    var endTime: Long = getEndDayOfMonth()?.time!!//默认本月

    override fun initTitle() {
        //设置透明状态栏
        makeStatusBarTransparent(this)
        titleBarImgBack.setOnClickListener {
            finish()
        }

        //时间选择
        titleBarTimeSelect.setOnClickListener {
            menuPopupWindow = MenuPopupWindow(this, 250, 623)
            menuPopupWindow?.OnItemClickLitener(this)
            menuPopupWindow?.showPopupAtViewBottom(titleBarTimeSelect)
        }
    }

    /**
     * 根据选择的时间类型获取相应的时间戳
     *@param timeType 0今日 1昨日 2本周 3本月 4本年 默认本月
     * */
    fun selectTime(timeType: Int) {
        when (timeType) {
            0 -> {
                startTime = getDayBegin()?.time!!
                endTime = getDayEnd()?.time!!
                businessTvThisTime.text = "今日流水总额（元）"
                titleBarTvTime.text = "今日"
            }
            1 -> {
                startTime = getBeginDayOfYesterday()?.time!!
                endTime = getEndDayOfYesterDay()?.time!!
                businessTvThisTime.text = "昨日流水总额（元）"
                titleBarTvTime.text = "昨日"
            }
            2 -> {
                startTime = getBeginDayOfWeek()?.time!!
                endTime = getEndDayOfWeek()?.time!!
                businessTvThisTime.text = "本周流水总额（元）"
                titleBarTvTime.text = "本周"
            }
            3 -> {
                startTime = getBeginDayOfMonth()?.time!!
                endTime = getEndDayOfMonth()?.time!!
                businessTvThisTime.text = "本月流水总额（元）"
                titleBarTvTime.text = "本月"
            }
            4 -> {
                startTime = getBeginDayOfYear()?.time!!
                endTime = getEndDayOfYear()?.time!!
                businessTvThisTime.text = "本年流水总额（元）"
                titleBarTvTime.text = "本年"
            }
        }
        menuPopupWindow?.dismiss()
        requestData()
    }

    override fun initView(savedInstanceState: Bundle?) {
        recyclerviewBill.initialize(
            adapter = BusinessListAdapter(),
            onEmptyClickBlock = ::requestData,
            onRefreshBlock = ::requestData,
            initializeBlock = {
                setOnItemClickBlock<BusinessListModel.DataBean> { adapter, view, position ->
                    adapter?.let {
                        it.data[position].let { data ->
                            when (view.id) {
                                R.id.myBillList -> {
                                    //跳转收入详情
                                    jumpByARouter(Config.USER_INCOME_DETAILS) {
                                        withInt("orderId", data.orderId)
                                        withBoolean("isIncomeType", false)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            emptyString = "暂无业务流水"
        )
    }

    override fun initObserver() {
        billActivityViewModel.businessListLiveData.observe(
            this,
            RequestResultObserver(
                successBlock = {
                    businessTvMoney.text = it.data?.getAmountMoney().toString()
                    recyclerviewBill.onDataSuccessAndEmpty(it.data?.getData()?.toMutableList())
                }, failBlock = {
                    recyclerviewBill.onDataErrorAndException(it)
                }
            )
        )
    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        }
    }

    private fun requestData() {
        billActivityViewModel.getFinance(
            recyclerviewBill.currentPage + 1,
            startTime / 1000,
            endTime / 1000
        )
    }

    /**
     * 日期选择回调
     * */
    override fun onRecyclerViewItemClick(position: Int) {
        selectTime(position)
    }


}