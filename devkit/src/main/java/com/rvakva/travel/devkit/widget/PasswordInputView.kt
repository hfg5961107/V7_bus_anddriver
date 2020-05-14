package com.rvakva.travel.devkit.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import com.rvakva.travel.devkit.R

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:    密码、验证码输入框
 * @Author:         胡峰
 * @CreateDate:     2020/5/14 上午10:42
 */
class PasswordInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var paint: Paint? = null
    private var maxLength = 0
    private var borderColor = 0
    private var pwdColor = 0
    private var inputBorderColor = 0
    private var radius = 0
    private var spacing = 0
    private var pwdStyle = 0
    private var borderStyle = 0
    private var asterisk: String? = null
    private var path: Path? = null
    private var rectF: RectF? = null
    private var xfermode: Xfermode? = null
    private var strokeWidth = 0f
    private var boxWidth = 0f
    private var textLength = 0
    private val linesArray = FloatArray(12)
    private val radiusArray = FloatArray(8)
    private var metrics: Paint.FontMetrics? = null
    private var inputListener: InputListener? = null

    /**
     * 边框风格
     */
    enum class BorderStyle(val value: Int) {
        BOX(0), LINE(1)
    }

    /**
     * 密码风格
     */
    enum class PwdStyle(val value: Int) {
        CIRCLE(0), ASTERISK(1), PLAINTEXT(2)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        initAttribute(context, attrs)
        textLength = if (text == null) 0 else text!!.length
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.strokeWidth = strokeWidth
        paint!!.isAntiAlias = true
        paint!!.textAlign = Paint.Align.CENTER
        paint!!.textSize = textSize
        metrics = paint!!.fontMetrics
        path = Path()
        rectF = RectF()
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.background = null
        } else {
            setBackgroundDrawable(null)
        }
        this.isCursorVisible = false
        this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    private fun initAttribute(
        context: Context,
        attrs: AttributeSet?
    ) {
        val t =
            context.obtainStyledAttributes(attrs, R.styleable.passwordInputView)
        maxLength = t.getInt(R.styleable.passwordInputView_pwv_maxLength, 6)
        borderColor = t.getColor(
            R.styleable.passwordInputView_pwv_borderColor,
            Color.GRAY
        )
        pwdColor = t.getColor(
            R.styleable.passwordInputView_pwv_pwdColor,
            Color.GRAY
        )
        inputBorderColor = t.getColor(
            R.styleable.passwordInputView_pwv_haveInputBorderColor,
            borderColor
        )
        asterisk = t.getString(R.styleable.passwordInputView_pwv_asterisk)
        if (asterisk == null || asterisk!!.length == 0) asterisk =
            "*" else if (asterisk!!.length > 1) asterisk = asterisk!!.substring(0, 1)
        radius =
            t.getDimensionPixelSize(R.styleable.passwordInputView_pwv_radius, 0)
        strokeWidth = t.getDimensionPixelSize(
            R.styleable.passwordInputView_pwv_strokeWidth,
            2
        ).toFloat()
        spacing = t.getDimensionPixelSize(
            R.styleable.passwordInputView_pwv_spacing,
            0
        )
        borderStyle = t.getInt(
            R.styleable.passwordInputView_pwv_borderStyle,
            BorderStyle.BOX.value
        )
        pwdStyle = t.getInt(
            R.styleable.passwordInputView_pwv_pwdStyle,
            PwdStyle.CIRCLE.value
        )
        t.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val availableWidth = w - paddingLeft - paddingRight
        val availableHeight = h - paddingTop - paddingBottom
        checkSpacing(availableWidth)
        checkRadius(availableWidth, availableHeight)
    }

    // 计算boxWidth并检查圆角大小是否过大
    private fun checkRadius(availableWidth: Int, availableHeight: Int) {
        // 每个盒子的宽度 = （可用宽度 - 间隔宽度）/ 盒子个数
        boxWidth = (availableWidth - (maxLength - 1f) * spacing) / maxLength
        val availableRadius = Math.min(availableHeight / 2f, boxWidth / 2)
        if (radius > availableRadius) {
            Log.d(TAG, "radius is too large, reset it")
            radius = availableRadius.toInt()
        } else if (radius < 0) {
            radius = 0
        }
    }

    // 检查间距是否过大
    private fun checkSpacing(availableWidth: Int) {
        if (spacing < 0 || (maxLength - 1) * spacing >= availableWidth) {
            Log.d(
                TAG,
                "spacing is too large, reset it to zero"
            )
            spacing = 0
        }
    }

    override fun onDraw(canvas: Canvas) {
////        super.onDraw(canvas); // 去掉EditText默认的绘制
        val top = paddingTop
        val bottom = height - paddingBottom
        val start = paddingLeft
        var left: Float
        for (i in 0 until maxLength) {
            left = start + (boxWidth + spacing) * i
            rectF!![left, top.toFloat(), left + boxWidth] = bottom.toFloat()
            drawBorder(canvas, i)
            if (i >= textLength) continue
            drawPassword(canvas, i)
        }
    }

    private fun drawBorder(canvas: Canvas, index: Int) {
        paint!!.color = if (index < textLength) inputBorderColor else borderColor
        paint!!.style = Paint.Style.STROKE
        when (borderStyle) {
            BorderStyle.BOX.value -> if (radius == 0) {
                // 圆角为0，判断间距
                // 间距为0时第一个绘制方框，后边的每一个只绘制上、右、下三条边，避免重复绘制一条边
                // 如果间距不为0，直接绘制方框
                if (spacing == 0) {
                    if (index == 0) {
                        canvas.drawRect(rectF!!, paint!!)
                    } else {
                        fillLinesArray()
                        canvas.drawLines(linesArray, paint!!)
                    }
                } else {
                    canvas.drawRect(rectF!!, paint!!)
                }
            } else {
                // 圆角!=0
                // 策略同上，只是增加了圆角，没有间距并且有圆角的情况只绘制第一个和最后一个圆角
                if (spacing == 0) {
                    if (index == 0) {
                        fillRadiusArray(true)
                        path!!.reset()
                        path!!.addRoundRect(rectF, radiusArray, Path.Direction.CCW)
                        canvas.drawPath(path!!, paint!!)
                    } else if (index == maxLength - 1) {
                        // 这里绘制最后一个密码框的三条边，带圆角
                        // 先绘制一个带两个圆角的方框，然后用xfermode合成去掉左边的一条边
                        val layer =
                            canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
                        fillRadiusArray(false)
                        path!!.reset()
                        path!!.addRoundRect(rectF, radiusArray, Path.Direction.CCW)
                        canvas.drawPath(path!!, paint!!)
                        paint!!.xfermode = xfermode
                        canvas.drawLine(
                            rectF!!.left,
                            rectF!!.top,
                            rectF!!.left,
                            rectF!!.bottom,
                            paint!!
                        )
                        paint!!.xfermode = null
                        canvas.restoreToCount(layer)
                    } else {
                        fillLinesArray()
                        canvas.drawLines(linesArray, paint!!)
                    }
                } else {
                    path!!.reset()
                    path!!.addRoundRect(
                        rectF,
                        radius.toFloat(),
                        radius.toFloat(),
                        Path.Direction.CCW
                    )
                    canvas.drawPath(path!!, paint!!)
                }
            }
            BorderStyle.LINE.value -> canvas.drawLine(
                rectF!!.left,
                rectF!!.bottom,
                rectF!!.right,
                rectF!!.bottom,
                paint!!
            )
        }
    }

    private fun drawPassword(canvas: Canvas, index: Int) {
        paint!!.color = pwdColor
        paint!!.style = Paint.Style.FILL
        when (pwdStyle) {
            PwdStyle.CIRCLE.value -> canvas.drawCircle(
                (rectF!!.left + rectF!!.right) / 2,
                (rectF!!.top + rectF!!.bottom) / 2,
                8f,
                paint!!
            )
            PwdStyle.ASTERISK.value -> canvas.drawText(
                asterisk!!, (rectF!!.left + rectF!!.right) / 2,
                (rectF!!.top + rectF!!.bottom - metrics!!.ascent - metrics!!.descent) / 2, paint!!
            )
            PwdStyle.PLAINTEXT.value -> canvas.drawText(
                text!![index].toString(), (rectF!!.left + rectF!!.right) / 2,
                (rectF!!.top + rectF!!.bottom - metrics!!.ascent - metrics!!.descent) / 2, paint!!
            )
        }
    }

    // 间距为0,并且有圆角时，只绘制第一个和最后一个的圆角，这里填充圆角的数组
    private fun fillRadiusArray(firstOrLast: Boolean) {
        if (firstOrLast) {
            radiusArray[0] = radius.toFloat()
            radiusArray[1] = radius.toFloat()
            radiusArray[2] = 0F
            radiusArray[3] = 0F
            radiusArray[4] = 0F
            radiusArray[5] = 0F
            radiusArray[6] = radius.toFloat()
            radiusArray[7] = radius.toFloat()
        } else {
            radiusArray[0] = 0F
            radiusArray[1] = 0F
            radiusArray[2] = radius.toFloat()
            radiusArray[3] = radius.toFloat()
            radiusArray[4] = radius.toFloat()
            radiusArray[5] = radius.toFloat()
            radiusArray[6] = 0F
            radiusArray[7] = 0F
        }
    }

    // 间距为0时，第一个绘制方框，后边的每一个框均只绘制上右下三条边，这里是添加这三条边的数组
    private fun fillLinesArray() {
        linesArray[0] = rectF!!.left
        linesArray[1] = rectF!!.top
        linesArray[2] = rectF!!.right
        linesArray[3] = rectF!!.top
        linesArray[4] = rectF!!.right
        linesArray[5] = rectF!!.top
        linesArray[6] = rectF!!.right
        linesArray[7] = rectF!!.bottom
        linesArray[8] = rectF!!.right
        linesArray[9] = rectF!!.bottom
        linesArray[10] = rectF!!.left
        linesArray[11] = rectF!!.bottom
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        textLength = text.toString().length
        invalidate()
        if (textLength == maxLength && inputListener != null) {
            inputListener!!.onInputCompleted(text.toString())
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(if (text == null) 0 else text!!.length)
        }
    }

    fun setRadius(radius: Int) {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        this.radius = radius
        checkRadius(availableWidth, availableHeight)
        invalidate()
    }

    fun setSpacing(spacing: Int) {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        this.spacing = spacing
        checkSpacing(availableWidth)
        checkRadius(availableWidth, availableHeight)
        invalidate()
    }

    fun setMaxLength(maxLength: Int) {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        this.maxLength = maxLength
        checkSpacing(availableWidth)
        checkRadius(availableWidth, availableHeight)
        invalidate()
    }

    fun setBorderColor(borderColor: Int) {
        this.borderColor = borderColor
        invalidate()
    }

    fun setPwdColor(pwdColor: Int) {
        this.pwdColor = pwdColor
        invalidate()
    }

    /**
     * 设置星号字符
     *
     * @param asterisk
     */
    fun setAsterisk(asterisk: String?) {
        if (asterisk == null || asterisk.length == 0) return
        if (asterisk.length > 1) this.asterisk = asterisk.substring(0, 1) else this.asterisk =
            asterisk
        invalidate()
    }

    fun setPwdStyle(pwdStyle: PwdStyle) {
        this.pwdStyle = pwdStyle.value
        invalidate()
    }

    fun setBorderStyle(borderStyle: BorderStyle) {
        this.borderStyle = borderStyle.value
        invalidate()
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        invalidate()
    }

    fun setInputListener(inputListener: InputListener?) {
        this.inputListener = inputListener
    }

    interface InputListener {
        fun onInputCompleted(text: String?)
    }

    companion object {
        private const val TAG = "PasswordInputView"
    }

    init {
        init(context, attrs)
    }
}