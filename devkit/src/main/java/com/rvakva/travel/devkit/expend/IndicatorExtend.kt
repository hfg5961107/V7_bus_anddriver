package com.rvakva.travel.devkit.expend

import android.content.Context
import android.graphics.Typeface
import androidx.viewpager2.widget.ViewPager2
import com.rvakva.travel.devkit.R
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/13 下午7:05
 */
fun MagicIndicator.bind(
    titleList: MutableList<String>,
    viewPager2: ViewPager2,
    isAdjustMode: Boolean = false
) {
    this.let { indicator ->
        navigator = CommonNavigator(context).also { navigator ->
            navigator.isAdjustMode = isAdjustMode
            navigator.scrollPivotX = 0.25F
            navigator.adapter = object : CommonNavigatorAdapter() {
                override fun getTitleView(context: Context?, index: Int): IPagerTitleView? =
                    context?.let {
                        object : SimplePagerTitleView(it) {
                            override fun onSelected(index: Int, totalCount: Int) {
                                super.onSelected(index, totalCount)
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            }

                            override fun onDeselected(index: Int, totalCount: Int) {
                                super.onDeselected(index, totalCount)
                                typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                            }

                        }.apply {
                            text = titleList[index]
                            textSize = 16F
                            if (!isAdjustMode) {
                                val padding = 8.dpToPx<Int>()
                                setPadding(padding, 0, padding, 0)
                            }
                            normalColor = R.color.black_sub.getColor()
                            selectedColor = R.color.black.getColor()
                            setOnClickListener {
                                viewPager2.currentItem = index
                            }
                        }
                    }

                override fun getCount() = titleList.size

                override fun getIndicator(context: Context?): IPagerIndicator? =
                    context?.let {
                        LinePagerIndicator(it).apply {
                            mode = LinePagerIndicator.MODE_EXACTLY
                            yOffset = 4.dpToPx()
                            lineWidth = 25.dpToPx()
                            lineHeight = 3.dpToPx()
                            roundRadius = 2F
                            setColors(R.color.blue.getColor())
                        }
                    }
            }
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                this@bind.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                this@bind.onPageSelected(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                this@bind.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

}