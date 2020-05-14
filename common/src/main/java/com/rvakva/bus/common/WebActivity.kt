package com.rvakva.bus.common

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rvakva.bus.common.extent.bindWebLifeCycle
import com.rvakva.travel.devkit.base.KtxActivity
import com.rvakva.travel.devkit.widget.ToastBar
import kotlinx.android.synthetic.main.activity_web.*

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午5:16
 */
class WebActivity : KtxActivity(R.layout.activity_web) {

    companion object {
        internal const val TITLE = "title"
        internal const val URL = "url"
    }

    override fun initTitle() {
        webMtb.let {
            it.leftTv.setOnClickListener {
                finish()
            }
            it.centerText.text = intent.getStringExtra(TITLE) ?: ""
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        intent.getStringExtra(URL)?.let {
            webWv.run {
                bindWebLifeCycle(this)
                setLayerType(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.LAYER_TYPE_HARDWARE else View.LAYER_TYPE_SOFTWARE,
                    null
                );
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    pluginState = WebSettings.PluginState.ON
                    allowFileAccess = false
                    javaScriptCanOpenWindowsAutomatically = true
                    layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                    useWideViewPort = true;
                    loadWithOverviewMode = true;
                    allowContentAccess = true;
                    savePassword = false;
                    setSupportMultipleWindows(true)
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            p0: WebView?,
                            p1: WebResourceRequest?
                        ): Boolean {
                            return false
                        }
                    }
                }
                removeJavascriptInterface("searchBoxJavaBridge_")
                removeJavascriptInterface("accessibility")
                removeJavascriptInterface("accessibilityTraversal")
                loadUrl(it)
            }

        } ?: let {
            ToastBar.show("数据发生错误,请稍候再试...")
            finish()
        }
    }

    override fun initObserver() {

    }

    override fun initData(isFirstInit: Boolean) {

    }
}