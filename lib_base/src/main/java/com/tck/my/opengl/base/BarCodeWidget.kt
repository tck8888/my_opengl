package com.tck.my.opengl.base

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *
 * description:

 * @date 2020/12/23 20:53

 * @author tck88
 *
 * @version v1.0.0
 *
 */
class BarCodeWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private lateinit var paint: Paint
    private lateinit var paint2: Paint
    private var radius = 0F
    private var cornerWidth = 0F
    private var cornerLength = 0F
    private var borderWidth = 0F

    init {
        radius = dpToPx(4f)
        cornerWidth = dpToPx(3f)
        cornerLength = dpToPx(21.5f)
        borderWidth = dpToPx(1.5f)

        paint = Paint().apply {
            color = Color.RED
            strokeWidth = cornerWidth
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        paint2 = Paint().apply {
            color = Color.BLUE
            strokeWidth = borderWidth
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            canvas?.clipOutRect(
                cornerLength,
                0f,
                width - cornerLength,
                height.toFloat()
            )
            canvas?.clipOutRect(
                0f,
                cornerLength,
                width.toFloat(),
                height.toFloat() - cornerLength)

        }else{
            canvas?.clipRect(
                cornerLength,
                0f,
                width - cornerLength,
                height.toFloat(),
                Region.Op.DIFFERENCE
            )
            canvas?.clipRect(
                0f,
                cornerLength,
                width.toFloat(),
                height.toFloat() - cornerLength,
                Region.Op.DIFFERENCE
            )
        }


        val center = cornerWidth / 2f

        canvas?.drawRoundRect(
            center,
            center,
            width - center,
            height - center,
            radius,
            radius,
            paint
        )
        canvas?.restore()

        canvas?.drawLine(cornerLength, center, width - cornerLength, center, paint2)
        canvas?.drawLine(
            cornerLength,
            height - center,
            width - cornerLength,
            height - center,
            paint2
        )
        canvas?.drawLine(center, cornerLength, center, height - cornerLength, paint2)
        canvas?.drawLine(
            width - center,
            cornerLength,
            width - center,
            height - cornerLength,
            paint2
        )
    }

    fun dpToPx(dpValue: Float): Float { //dp转换为px
        val scale = context.resources.displayMetrics.density //获得当前屏幕密度
        return (dpValue * scale + 0.5f)
    }
}