package com.rvakva.bus.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.rvakva.bus.home.ui.IndexActivity
import com.rvakva.bus.home.ui.work.WorkActivity
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.expend.jumpTo

@Route(path = Config.APP_SPLASH)
class SplashActivity : KtxActivity(R.layout.activity_splash) {


    override fun initTitle() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        //防止重复打开
        intent?.let {
            if (it.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
                finish()
                return
            }
        }
        jumpTo<IndexActivity>()
        finish()
    }

    override fun initObserver() {
    }

    override fun initData(isFirstInit: Boolean) {
    }
}