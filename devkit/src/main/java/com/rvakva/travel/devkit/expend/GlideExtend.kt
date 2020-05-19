package com.rvakva.travel.devkit.expend

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.rvakva.travel.devkit.transform.GlideRoundTransform

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午3:55
 */
fun ImageView.glideInto(
    url: String,
    block: RequestBuilder<Drawable>.() -> RequestBuilder<Drawable> = { this }
) {
    Glide.with(this.context).load(url).block().into(this)
}

fun ImageView.glideWithRoundInto(
    url: String,
    dp: Int
) {
    glideInto(url) {
        apply(RequestOptions().transform(CenterCrop(), GlideRoundTransform(dp)))
    }
}