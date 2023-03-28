package com.wsy.customviewbattery

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 *  author : wsy
 *  date   : 2023/3/28
 *  desc   : 自定义电量view
 */
class BatteryProgressView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    var endPercent: Int = 16 //结束百分比

    private val paintHead = Paint()//电池头部
    var paintColorHead = Color.BLACK //电池头部画笔颜色
    private var paintStyleHead = Paint.Style.FILL  //电池头部画笔样式（填充）
    private val paintHeadWidth: Float
        get() = paintStrokeWidthOut * 4

    private val paintOut = Paint() //电池外部画笔
    var paintColorOut = Color.BLACK //电池外部画笔颜色
    private var paintStyleOut = Paint.Style.STROKE  //电池外部画笔样式（描边）
    var paintStrokeWidthOut = 1F //电池外部画笔宽度

    private val outPaddingIn: Float //电池外部的内边距（内外view的距离）
        get() = paintStrokeWidthOut * 2

    private val paintIn = Paint() //电池内部画笔
    var paintColorIn = Color.BLACK  //电池内部画笔颜色
    private var paintStyleIn = Paint.Style.FILL  //电池内部画笔样式（填充）

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paintHead.apply {
            color = paintColorHead
            style = paintStyleHead
            isAntiAlias = true//抗锯齿
        }
        val rectHead = RectF(
            width - paintHeadWidth,
            (height - paintStrokeWidthOut) / 4,
            width.toFloat(),
            (height - paintStrokeWidthOut) / 4 * 3
        )
        canvas?.drawRoundRect(rectHead, 20F, 20F, paintHead)

        paintOut.apply {
            color = paintColorOut
            style = paintStyleOut
            strokeWidth = paintStrokeWidthOut
            isAntiAlias = true//抗锯齿
        }
        val rectOut = RectF(
            paintStrokeWidthOut,
            paintStrokeWidthOut,
            width - paintStrokeWidthOut - paintHeadWidth / 2,
            height - paintStrokeWidthOut
        )
        canvas?.drawRoundRect(rectOut, 5F, 5F, paintOut)

        paintIn.apply {
            color = paintColorIn
            style = paintStyleIn
            isAntiAlias = true//抗锯齿
        }
        val rectIn = RectF(
            paintStrokeWidthOut + outPaddingIn,
            paintStrokeWidthOut + outPaddingIn,
            when (endPercent) {
                in 0..15 -> (width - paintStrokeWidthOut - outPaddingIn - paintHeadWidth / 2) * 15 / 100
                else -> (width - paintStrokeWidthOut - outPaddingIn - paintHeadWidth / 2) * endPercent / 100
            },
            height - paintStrokeWidthOut - outPaddingIn
        )
        canvas?.drawRoundRect(rectIn, 3F, 3F, paintIn)

    }
}