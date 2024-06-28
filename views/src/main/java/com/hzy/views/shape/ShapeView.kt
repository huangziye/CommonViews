package com.hzy.views.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.hzy.views.R
import kotlin.math.sqrt

open class ShapeView : View {

    private var mCurrentShape = Shape.Circle
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPath: Path? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //只保证是正方形
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas?) {
        when (mCurrentShape) {
            Shape.Circle -> {
                var radius = width / 2
                mPaint.color = ContextCompat.getColor(context, R.color.shapeview_circle)
                canvas?.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), mPaint)
            }
            Shape.Square -> {
                mPaint.color = ContextCompat.getColor(context, R.color.shapeview_square)
                canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
            }
            Shape.Triangle -> {
                mPaint.color = ContextCompat.getColor(context, R.color.shapeview_triangle)
                if (null == mPath) {
                    mPath = Path()
                    mPath!!.moveTo((width / 2).toFloat(), 0f)
                    mPath!!.lineTo(0f, (width / 2 * sqrt(3.0)).toFloat())
                    mPath!!.lineTo(width.toFloat(), (width / 2 * sqrt(3.0)).toFloat())
                    mPath!!.close()
                }
                canvas?.drawPath(mPath!!, mPaint)
            }
        }
    }

    /**
     * Exchange shape.
     */
    fun exchange() {
        mCurrentShape = when (mCurrentShape) {
            Shape.Circle -> Shape.Square
            Shape.Square -> Shape.Triangle
            Shape.Triangle -> Shape.Circle
        }
        //不断重新绘制形状
        invalidate()
    }

    /**
     * Gets the current shape.
     */
    fun getCurrentShape(): Shape {
        return mCurrentShape
    }

    enum class Shape {
        Circle, Square, Triangle
    }
}


