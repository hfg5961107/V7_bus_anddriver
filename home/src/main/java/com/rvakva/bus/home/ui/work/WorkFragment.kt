package com.rvakva.bus.home.ui.work

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.rvakva.bus.common.X
import com.rvakva.bus.common.model.ScheduleStatusTypeEnum
import com.rvakva.bus.common.util.MyMediaPlayerType
import com.rvakva.bus.home.R
import com.rvakva.bus.home.viewmodel.work.WorkActivitySharedViewModel
import com.rvakva.bus.home.viewmodel.work.WorkViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxFragment
import com.rvakva.travel.devkit.expend.bind
import com.rvakva.travel.devkit.expend.getPrivateValue
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import kotlinx.android.synthetic.main.activity_main_indicator.*
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/13 下午6:38
 */
class WorkFragment : KtxFragment(R.layout.fragment_work) {

    private val workViewModel by activityViewModels<WorkViewModel>()
    private val workActivitySharedViewModel by activityViewModels<WorkActivitySharedViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() =
            WorkFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun initObserver() {
        workViewModel.workStatusViewModel.observe(
            viewLifecycleOwner,
            RequestResultObserver(
                successBlock = {
                    workViewModel.getUserInfo()
                },
                fragmentManager = parentFragmentManager, handleResult = true
            )
        )
        Ktx.getInstance().userDataSource.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            it.status?.let { it1 ->
                showStatus(it1)
            }
        })


        workActivitySharedViewModel.newScheduledCountLiveData.observe(this, Observer {
            if (it && mainVp.currentItem != 0) {
                mainTv.visibility = View.VISIBLE
            } else {
                mainTv.visibility = View.GONE
            }
        })
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initTabLayout()
        setOnClick()
        Ktx.getInstance().userDataSource.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            Config.status = it.status
        })
    }

    override fun initData(isFirstInit: Boolean) {

    }

    val fragmentList = mutableListOf(
        WorkOrderFragment.newInstance(
            ScheduleStatusTypeEnum.SCHEDULE_TYPE_NEW
        ),
        WorkOrderFragment.newInstance(
            ScheduleStatusTypeEnum.SCHEDULE_TYPE_ING
        )
    )

    private fun initTabLayout() {
        mainVp.let {
            (it.getPrivateValue(
                "mRecyclerView",
                ViewPager2::class.java
            ) as? RecyclerView)?.overScrollMode =
                View.OVER_SCROLL_NEVER

            it.adapter = object : FragmentStateAdapter(this) {

                override fun getItemCount() = fragmentList.size

                override fun createFragment(position: Int) = fragmentList[position]
            }
            it.offscreenPageLimit = 1
            mainMi.bind(mutableListOf("新班次", "行程中"), it)
        }

        mainVp.registerOnPageChangeCallback(object : OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    mainTv.visibility = View.GONE
                }
            }
        })
    }

    private fun setOnClick() {
        workStatusBtn.setOnClickListener {
            if (Config.status == 1) {
                AlertDialog.Builder(context!!)
                    .setMessage("关闭听单后将收不到新订单，确认关闭吗？")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        workViewModel.changeStatus(2)
                    }
                    .setNeutralButton("取消", null)
                    .create()
                    .show()
            } else if (Config.status == 2) {
                X.getInstance().myMediaPlayer.play(MyMediaPlayerType.ONLINE)
                workViewModel.changeStatus(1)
            }
        }
    }

    private fun showStatus(status: Int) {
        if (status == 1) {
            workStatusIv.setImageResource(R.drawable.home_workbench_work)
            workStatusOnlineTv.visibility = View.VISIBLE
            workStatusRestTv.visibility = View.GONE
        } else if (status == 2) {
            workStatusIv.setImageResource(R.drawable.home_workbench_rest)
            workStatusOnlineTv.visibility = View.GONE
            workStatusRestTv.visibility = View.VISIBLE
        }
    }
}