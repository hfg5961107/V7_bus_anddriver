package com.rvakva.bus.personal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.personal.R
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.base.KtxActivity
import kotlinx.android.synthetic.main.activity_income_details.*


//@Route(path = Config.USER_INCOME_DETAILS)
class IncomeDetailsActivity : KtxActivity(R.layout.activity_income_details) {
    // 是否是收入类型  默认收入详情
    var isIncomeType: Boolean = true


    override fun initTitle() {
        isIncomeType = intent.getBooleanExtra("isIncomeType", true)

        hisScheduleMtb?.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            if (isIncomeType) {
                it.centerText.text = "收入详情"
            } else {
                it.centerText.text = "流水详情"
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {

        if (!isIncomeType) {
            realFeeLayout.visibility = View.GONE
            commissionRatioLayout.visibility = View.GONE
        }
        var textSpan: SpannableString = SpannableString("90元");
        textSpan.setSpan( AbsoluteSizeSpan(24), 0, textSpan.length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        textSpan.setSpan( AbsoluteSizeSpan(16), textSpan.length - 1, textSpan.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        incomeLabel.text = textSpan;

    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {
        if (isFirstInit) {
            requestData()
        }
    }

    fun requestData() {

    }

}