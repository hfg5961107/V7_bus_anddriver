package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.rvakva.bus.home.R
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.bind
import com.rvakva.travel.devkit.expend.getPrivateValue
import kotlinx.android.synthetic.main.activity_main_indicator.*
import kotlinx.android.synthetic.main.activity_work.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/13 下午1:50
 */
class WorkActivity : KtxActivity(R.layout.activity_work) {

    private var currentFragment: Fragment = Fragment()

    override fun initTitle() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        setOnClick()
        replaceResuoce(1)
        switchFragment(workFragment).commit()
    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }

    private fun setOnClick(){
        workBottomTaiLl.setOnClickListener {
            replaceResuoce(1)
            switchFragment(workFragment).commit()
        }
        workBottomOrderLl.setOnClickListener {
            replaceResuoce(2)
            switchFragment(orderFragment).commit()
        }
        workBottomPersonLl.setOnClickListener {
            replaceResuoce(3)
            switchFragment(personFragment).commit()
        }
    }


    var workFragment : WorkFragment =  WorkFragment.newInstance()
    var orderFragment : OrderFragment =  OrderFragment.newInstance()
    var personFragment : PersonCenterFragment =  PersonCenterFragment.newInstance()

    private fun switchFragment(targetFragment: Fragment): FragmentTransaction {
        var transaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded){
            if (currentFragment!=null){
                transaction.hide(currentFragment)
            }
            transaction.add(R.id.flWork,targetFragment,targetFragment.javaClass.name)
        }else{
            transaction.hide(currentFragment).show(targetFragment)
        }
        currentFragment = targetFragment
        return transaction
    }

    /**
     * 替换资源 1工作台 2订单 3个人中心
     */
    private fun replaceResuoce(index: Int){
        when(index){
            1 -> {
                workBottomTaiIcon.setImageResource(R.drawable.home_icon_home_workbench_selected)
                workBottomTaiTv.setTextColor(resources.getColor(R.color.blue))

                workBottomOrderIcon.setImageResource(R.drawable.home_icon_home_order)
                workBottomOrderTv.setTextColor(resources.getColor(R.color.black))

                workBottomPersonIcon.setImageResource(R.drawable.home_icon_home_personal)
                workBottomPersonTv.setTextColor(resources.getColor(R.color.black))
            }
            2 -> {
                workBottomTaiIcon.setImageResource(R.drawable.home_icon_home_workbench)
                workBottomTaiTv.setTextColor(resources.getColor(R.color.black))

                workBottomOrderIcon.setImageResource(R.drawable.home_icon_home_order_selected)
                workBottomOrderTv.setTextColor(resources.getColor(R.color.blue))

                workBottomPersonIcon.setImageResource(R.drawable.home_icon_home_personal)
                workBottomPersonTv.setTextColor(resources.getColor(R.color.black))
            }
            3 -> {
                workBottomTaiIcon.setImageResource(R.drawable.home_icon_home_workbench)
                workBottomTaiTv.setTextColor(resources.getColor(R.color.black))

                workBottomOrderIcon.setImageResource(R.drawable.home_icon_home_order)
                workBottomOrderTv.setTextColor(resources.getColor(R.color.black))

                workBottomPersonIcon.setImageResource(R.drawable.home_icon_home_personal_selected)
                workBottomPersonTv.setTextColor(resources.getColor(R.color.blue))
            }
        }
    }

}