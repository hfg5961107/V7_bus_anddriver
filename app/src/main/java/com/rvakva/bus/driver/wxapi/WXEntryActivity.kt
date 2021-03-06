package com.easymin.yiyue.worker.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.rvakva.travel.devkit.Config
import com.rvakva.travel.devkit.Ktx
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

class WXEntryActivity : Activity(), IWXAPIEventHandler {

    private var api = Ktx.getInstance().weChatApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api.let {
            it.registerApp(Config.WECHAT_ID)
            it.handleIntent(intent, this)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api.handleIntent(intent, this)
    }

    override fun onResp(p0: BaseResp?) {
        p0?.let {
            if (it.errCode == BaseResp.ErrCode.ERR_OK) {
                //success
            } else {
                //fail
            }
        }
        finish()
    }

    override fun onReq(p0: BaseReq?) {
    }
}