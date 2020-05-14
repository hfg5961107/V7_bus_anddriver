package com.rvakva.bus.common.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.rvakva.bus.common.R
import com.rvakva.bus.common.model.AppInfoModel
import com.rvakva.bus.common.viewmodel.UpdateDialogViewModel
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.observer.EventObserver
import com.rvakva.travel.devkit.observer.request.RequestResultObserver
import com.sherloki.commonwidget.BaseDialogFragment
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.update_dialog.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:27
 */
interface OnUpdateDialogClickListener {
    fun onUpdateDialogClick()
}

class UpdateDialog private constructor() : BaseDialogFragment() {

    companion object {
        private const val DATA = "DATA"

        fun newInstance(appInfo: AppInfoModel) =
            UpdateDialog().apply {
                arguments = bundleOf(
                    DATA to appInfo
                )
            }
    }

    override fun onAttach(context: Context) {
        onUpdateDialogClickListener = context as? OnUpdateDialogClickListener
        super.onAttach(context)
    }

    private var onUpdateDialogClickListener: OnUpdateDialogClickListener? = null

    private val updateDialogViewModel by activityViewModels<UpdateDialogViewModel>()

    override fun getViewHeight() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getViewWidth() = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun getWindowAnimation() = 0

    override fun getLayoutId() = R.layout.update_dialog

    override fun initView(view: View?) {
        (arguments?.get(DATA) as? AppInfoModel)?.let { appInfo ->
            resetView(appInfo)
            updateDialogViewModel.downloadLiveData.observe(viewLifecycleOwner,
                RequestResultObserver(
                    successBlock = {
                        AndPermission.with(this)
                            .install()
                            .file(it)
                            .start()
                    }, completeBlock = {
                        resetView(appInfo)
                    }
                ))

            KtxViewModel.progressLiveData.observe(viewLifecycleOwner, EventObserver {
                if (it.url == appInfo.url) {
                    updateDialogPb.progress = it.progress
                    updateDialogTvProgress.text = "${it.progress}%"
                }
            })
        }
    }

    private fun resetView(appInfo: AppInfoModel) {
        updateDialogTvDesc.text = appInfo.appVersionName
        updateDialogTvTitle.text = "发现新版本"
        updateDialogTvContent.text = appInfo.content
        updateDialogLlAction.visibility = View.VISIBLE
        updateDialogLlProgress.visibility = View.GONE
        updateDialogTvCancel.let {
            it.visibility = if (appInfo.type == 1) View.GONE else View.VISIBLE
            it.setOnClickListener {
                dismiss()
                onUpdateDialogClickListener?.onUpdateDialogClick()
            }
        }
        updateDialogTvUpdate.setOnClickListener {
            showProgress()
            updateDialogViewModel.download(appInfo.url)
        }
    }

    private fun showProgress() {
        updateDialogPb.progress = 0
        updateDialogLlAction.visibility = View.GONE
        updateDialogLlProgress.visibility = View.VISIBLE
        updateDialogTvTitle.text = "正在更新"
        updateDialogTvDesc.text = "正在下载更新包\n请耐心等待"

    }

}