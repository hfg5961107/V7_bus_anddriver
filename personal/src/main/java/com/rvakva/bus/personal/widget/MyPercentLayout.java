package com.rvakva.bus.personal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.rvakva.bus.personal.R;

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 *
 * @Description: 宽高比例显示控件
 * @Author: lch
 * @Date: 2020/7/27 16:56
 **/
public class MyPercentLayout extends RelativeLayout {

    private float proportion= (float) 5.43;
    public MyPercentLayout(Context context) {
        super(context);
    }

    public MyPercentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.PercentAppBarLayout);
        proportion=a.getFloat(R.styleable.PercentAppBarLayout_proportion,5.43f);
        a.recycle();

    }

    public MyPercentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.PercentAppBarLayout);
        proportion=a.getFloat(R.styleable.PercentAppBarLayout_proportion,5.43f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int turespec=MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec)/proportion),MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, turespec);
    }
}
