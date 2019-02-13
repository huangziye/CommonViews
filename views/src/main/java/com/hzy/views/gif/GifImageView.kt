package com.hzy.views.gif

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Movie
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.hzy.views.R

/**
 * GifImageView既能支持ImageView控件原生的所有功能，同时还可以播放GIF图片。
 * Created by ziye_huang on 2019/1/11.
 */
class GifImageView : ImageView, View.OnClickListener {

    /**
     * 播放GIF动画的关键类
     */
    private var mMovie: Movie? = null

    /**
     * 开始播放按钮图片
     */
    private var mStartPlayBitmap: Bitmap? = null

    /**
     * 记录动画开始的时间
     */
    private var mAnimStartTime: Long = 0

    /**
     * GIF图片的宽度
     */
    private var mImageWidth = 0

    /**
     * GIF图片的高度
     */
    private var mImageHeight = 0

    /**
     * 图片是否正在播放
     */
    private var isPlaying = false

    /**
     * 是否允许自动播放
     */
    private var isAutoPlay = false

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        //使用软解码，不用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.GifImageView)
        val resourceId = getResourceId(attrs)

        if (resourceId != 0) {
            // 当资源id不等于0时，就去获取该资源的流
            val inputStream = resources.openRawResource(resourceId)
            // 使用Movie类对流进行解码
            mMovie = Movie.decodeStream(inputStream)
            if (mMovie != null) {
                // 如果返回值不等于null，就说明这是一个GIF图片，下面获取是否自动播放的属性
                isAutoPlay = ta.getBoolean(R.styleable.GifImageView_auto_play, false)
                inputStream.reset()
                /**
                 * 第一次decodeStream时已经操作过inputStream了，这时候流的操作位置已经移动了，如果再次decodeStream则不是从流的起始位置解析，所以无法解析出Bitmap对象。
                 * 只需要在再次解码之前使流读写位置恢复为起始位置即可 inputStream.reset()
                 */
                val bitmap = BitmapFactory.decodeStream(inputStream)
                mImageWidth = bitmap.width
                mImageHeight = bitmap.height
                bitmap.recycle()
                if (!isAutoPlay) {
                    // 当不允许自动播放的时候，得到开始播放按钮的图片，并注册点击事件
                    mStartPlayBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_stop)
                    setOnClickListener(this)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v!!.id == id) {
            // 当用户点击图片时，开始播放GIF动画
            isPlaying = true
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas?) {
        if (mMovie == null) {
            // mMovie等于null，说明是张普通的图片，则直接调用父类的onDraw()方法
            super.onDraw(canvas)
        } else {
            // mMovie不等于null，说明是张GIF图片
            if (isAutoPlay) {
                // 如果允许自动播放，就调用playMovie()方法播放GIF动画
                playMovie(canvas!!)
                invalidate()
            } else {
                // 不允许自动播放时，判断当前图片是否正在播放
                if (isPlaying) {
                    // 正在播放就继续调用playMovie()方法，一直到动画播放结束为止
                    if (playMovie(canvas!!)) {
                        isPlaying = false
                    }
                    invalidate()
                } else {
                    // 还没开始播放就只绘制GIF图片的第一帧，并绘制一个开始按钮
                    mMovie!!.setTime(0)
                    mMovie!!.draw(canvas, 0f, 0f)
                    val offsetW = (mImageWidth - mStartPlayBitmap!!.width) / 2
                    val offsetH = (mImageHeight - mStartPlayBitmap!!.height) / 2
                    canvas!!.drawBitmap(mStartPlayBitmap!!, offsetW.toFloat(), offsetH.toFloat(), null)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mMovie != null) {
            // 如果是GIF图片则重写设定GifImageView的大小
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
    }

    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false。
     *
     * @param canvas
     * @return 播放完成返回true，未完成返回false。
     */
    private fun playMovie(canvas: Canvas): Boolean {
        val now = SystemClock.uptimeMillis()
        if (mAnimStartTime == 0L) {
            mAnimStartTime = now
        }
        var duration = mMovie!!.duration()
        if (duration == 0) {
            duration = 1000
        }

        val relativeMilliseconds = ((now - mAnimStartTime) % duration).toInt()
        mMovie!!.setTime(relativeMilliseconds)
        mMovie!!.draw(canvas, 0f, 0f)
        if ((now - mAnimStartTime) >= duration) {
            mAnimStartTime = 0
            return true
        }
        return false
    }

    /**
     * 获取到src指定图片资源所对应的id。
     *
     * @param attrs
     * @return 返回布局文件中指定图片资源所对应的id，没有指定任何图片资源就返回0。
     */
    private fun getResourceId(attrs: AttributeSet): Int {
        for (i in 0 until attrs.attributeCount) {
            if (attrs.getAttributeName(i) == "src") {
                return attrs.getAttributeResourceValue(i, 0)
            }
        }
        return 0
    }

}