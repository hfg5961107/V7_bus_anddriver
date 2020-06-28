package com.rvakva.bus.personal.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.common.extent.getImageUrl
import com.rvakva.bus.personal.R
import com.rvakva.bus.personal.viewmodel.UserApplyActivityViewModel
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.*
import com.rvakva.travel.devkit.model.UserAuditEnum
import com.yanzhenjie.permission.runtime.Permission
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_user_apply.*
import java.util.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/6/24 上午9:17
 */
@Route(path = Config.USER_IDENTITY)
class UserApplyActivity : KtxActivity(R.layout.activity_user_apply) {

    private val userApplyActivityViewModel by viewModels<UserApplyActivityViewModel>()

    private val photoFrontRequestCode = 0x0001
    private val photoBackRequestCode = 0x0002
    private val photoOtherOneRequestCode = 0x0003
    private val photoOtherTwoRequestCode = 0x0004


    private var otherOne: String? = null
    private var otherTwo: String? = null

    override fun initTitle() {
        applyMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = "账户认证"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

        userApplyNameEt.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                checkBtnStatus()
            }
        }

        userApplyIdCardEt.addTextChangedListenerDsl {
            onTextChanged { s, start, before, count ->
                checkBtnStatus()
            }
        }

        Ktx.getInstance().userDataSource.userInfoLiveData.observe(this, Observer {

            userApplyActivityViewModel.userInfoModel = it

            it.name?.let { name ->
                userApplyNameEt.setText(name)
                userApplyNameTv.text = name
            }
            it.idCard?.let { idCard ->
                userApplyIdCardEt.setText(idCard)
                userApplyIdCardTv.text = idCard
            }


            if (it.attachmentPath.isNullOrEmpty()) {
                userApplyOtherOneIv.setImageResource(R.drawable.user_certification_other)
                userApplyOtherTwoIv.setImageResource(R.drawable.user_certification_other)
            } else {
                if (it.attachmentPath!!.contains(",")) {
                    var other = it.attachmentPath
                    other!!.split(",")[0]?.let { path ->
                        otherOne = path
                        userApplyOtherOneIv.glideInto(path.getImageUrl())
                        userApplyOtherOneRl.visibility = View.VISIBLE
                    }
                    other.split(",")[1]?.let { path ->
                        otherTwo = path
                        userApplyOtherTwoIv.glideInto(path.getImageUrl())
                        userApplyOtherTwoRl.visibility = View.VISIBLE
                    }
                } else {
                    otherOne = it.attachmentPath
                    userApplyOtherOneIv.glideInto(otherOne.getImageUrl())
                    userApplyOtherOneRl.visibility = View.VISIBLE
                }
            }
            it.idCardFrontPath?.let { path ->
                if (path.isNullOrEmpty()) {
                    userApplyIdCardIv.setImageResource(R.drawable.user_certification_id_card)
                } else {
                    userApplyIdCardIv.glideInto(path.getImageUrl())
                    userApplyIdCardRl.visibility = View.VISIBLE
                }
            } ?: userApplyIdCardIv.setImageResource(R.drawable.user_certification_id_card)

            it.idCardBackPath?.let { path ->
                if (path.isNullOrEmpty()) {
                    userApplyIdCardBackIv.setImageResource(R.drawable.user_certification_id_card_back)
                } else {
                    userApplyIdCardBackIv.glideInto(path.getImageUrl())
                    userApplyIdCardBackRl.visibility = View.VISIBLE
                }
            } ?: userApplyIdCardBackIv.setImageResource(R.drawable.user_certification_id_card_back)

            when (it.applyStatus) {
                UserAuditEnum.NON_IDENTITY.status -> {
                    userApplyHintTv.visibility = View.VISIBLE
                    userApplyStatusLin.visibility = View.GONE

                    userApplyHintTv.text = "· 请使用本人身份证，如实填写身份信息。 \n· 身份信息仅用于账户认证，请放心上传。"

                    userApplyInfoLinOne.visibility = View.VISIBLE
                    userApplyInfoLinTwo.visibility = View.GONE

                    userApplyIdCardRl.visibility = View.GONE
                    userApplyIdCardBackRl.visibility = View.GONE
                    userApplyOtherOneRl.visibility = View.GONE
                    userApplyOtherTwoRl.visibility = View.GONE
                }
                UserAuditEnum.PROGRESSING.status -> {
                    userApplyHintTv.visibility = View.GONE
                    userApplyStatusLin.visibility = View.VISIBLE

                    userApplyStatusIv.setImageResource(R.drawable.user_certification_waiting)
                    userApplyStatusTv.text = "平台正在认证，请耐心等候"
                    userApplyStatusTv.setTextColor(resources.getColor(R.color.black_desc))
                    userApplyNoPassRemarkTv.visibility = View.GONE

                    userApplyInfoLinOne.visibility = View.GONE
                    userApplyInfoLinTwo.visibility = View.VISIBLE

                    userApplyIdCardRl.visibility = View.GONE
                    userApplyIdCardBackRl.visibility = View.GONE
                    userApplyOtherOneRl.visibility = View.GONE
                    userApplyOtherTwoRl.visibility = View.GONE
                }
                UserAuditEnum.SUCCESS.status -> {
                    userApplyHintTv.visibility = View.GONE
                    userApplyStatusLin.visibility = View.VISIBLE

                    userApplyStatusIv.setImageResource(R.drawable.user_certification_pass)
                    userApplyStatusTv.text = "审核通过"
                    userApplyStatusTv.setTextColor(resources.getColor(R.color.green))
                    userApplyNoPassRemarkTv.visibility = View.GONE

                    userApplyInfoLinOne.visibility = View.GONE
                    userApplyInfoLinTwo.visibility = View.VISIBLE

                    userApplyIdCardRl.visibility = View.GONE
                    userApplyIdCardBackRl.visibility = View.GONE
                    userApplyOtherOneRl.visibility = View.GONE
                    userApplyOtherTwoRl.visibility = View.GONE
                }
                UserAuditEnum.FAIL.status -> {
                    userApplyHintTv.visibility = View.GONE
                    userApplyStatusLin.visibility = View.VISIBLE

                    userApplyStatusIv.setImageResource(R.drawable.user_certification_waiting)
                    userApplyStatusTv.text = "认证失败"
                    userApplyStatusTv.setTextColor(resources.getColor(R.color.red))
                    userApplyNoPassRemarkTv.visibility = View.VISIBLE
                    userApplyNoPassRemarkTv.text = it.refusalReason

                    userApplyInfoLinOne.visibility = View.VISIBLE
                    userApplyInfoLinTwo.visibility = View.GONE

                    userApplyOtherOneRl.visibility = View.VISIBLE
                    userApplyOtherTwoRl.visibility = View.VISIBLE
                }
            }
        })

        userApplyIdCardIv.setOnClickListener {
            goPhotoSelect(photoFrontRequestCode)
        }
        userApplyIdCardBackIv.setOnClickListener {
            goPhotoSelect(photoBackRequestCode)
        }
        userApplyOtherOneIv.setOnClickListener {
            goPhotoSelect(photoOtherOneRequestCode)
        }
        userApplyOtherTwoIv.setOnClickListener {
            goPhotoSelect(photoOtherTwoRequestCode)
        }

        userApplyBtn.setOnClickListener {
            userApplyActivityViewModel.submitUserInfo(
                userApplyNameEt.text.toString(),
                userApplyIdCardEt.text.toString(),
                userApplyActivityViewModel.userInfoModel?.idCardFrontPath ?: "",
                userApplyActivityViewModel.userInfoModel?.idCardBackPath ?: "",
                otherOne ?: "",
                otherTwo ?: ""
            )
        }
    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }

    private fun goPhotoSelect(requestCode: Int) {
        requestPermission(
            Permission.READ_EXTERNAL_STORAGE,
            Permission.WRITE_EXTERNAL_STORAGE
        ) {
            onGranted {
                Matisse.from(this@UserApplyActivity)
                    .choose(
                        EnumSet.of(
                            MimeType.JPEG,
                            MimeType.PNG
                        )
                    )
                    //显示一种类型
                    .showSingleMediaType(true)
                    //可照相
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, "com.rvakva.bus.driver.provider"))
                    .maxSelectable(1)
                    .thumbnailScale(0.85f)
                    .imageEngine(GlideEngine())
                    .showPreview(true)
                    .forResult(requestCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Matisse.obtainPathResult(data).getOrNull(0)?.let {
                it.loge("hufeng")
                if (requestCode == photoFrontRequestCode) {
                    userApplyActivityViewModel.idCardFrontPath = it
                    userApplyIdCardIv.glideWithRoundInto(it,4)
                } else if (requestCode == photoBackRequestCode) {
                    userApplyActivityViewModel.idCardBackPath = it
                    userApplyIdCardBackIv.glideInto(it)
                } else if (requestCode == photoOtherOneRequestCode) {
                    userApplyActivityViewModel.otherOnePath = it
                    userApplyOtherOneIv.glideInto(it)
                } else if (requestCode == photoOtherTwoRequestCode) {
                    userApplyActivityViewModel.idCardFrontPath = it
                    userApplyOtherTwoIv.glideInto(it)
                }
            }
        }
    }

    private fun checkBtnStatus() {
        userApplyBtn.isEnabled =
            userApplyNameEt.text.length >= 2 && userApplyIdCardEt.text.length == 18
    }
}