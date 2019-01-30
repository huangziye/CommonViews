package com.hzy.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.loading_view.view.*

open class LoadingView : LinearLayout {
    private var mTranslationDistance = 150
    /**
     * 动画执行的时间
     */
    private var mAnimatorDuration = 500L
    /**
     * 是否停止动画
     */
    private var mNeedStopAnimator = false

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun initLayout() {
        View.inflate(context, R.layout.loading_view, this)
        setShapeViewMargins(0, 0, 0, mTranslationDistance)
        post { startFallAnimator() }
    }

    /**
     * 开始下落动画
     */
    private fun startFallAnimator() {
        if (mNeedStopAnimator) return
        //下落位移动画
        var translationAnimator = ObjectAnimator.ofFloat(shapeView, "translationY", 0f, mTranslationDistance.toFloat())
        //中间阴影缩小
        var scaleAnimator = ObjectAnimator.ofFloat(shadowView, "scaleX", 1f, 0.3f)
        var animatorSet = AnimatorSet()
        animatorSet.duration = mAnimatorDuration
        //下落的速度应该是越来越快
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.playTogether(translationAnimator, scaleAnimator)
        animatorSet.start()
        //监听动画执行完毕，下落完之后就开始上抛
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //改变形状
                shapeView.exchange()
                //下落玩之后就上抛
                startUpAnimator()
            }
        })
    }

    /**
     * 开始上抛动画
     */
    private fun startUpAnimator() {
        if (mNeedStopAnimator) return
        //下落位移动画
        var translationAnimator = ObjectAnimator.ofFloat(shapeView, "translationY", mTranslationDistance.toFloat(), 0f)
        //中间阴影缩小
        var scaleAnimator = ObjectAnimator.ofFloat(shadowView, "scaleX", 0.3f, 1f)
        var animatorSet = AnimatorSet()
        animatorSet.duration = mAnimatorDuration
        //下落的速度应该是越来越快
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.playTogether(translationAnimator, scaleAnimator)
        animatorSet.start()
        //监听动画执行完毕，上抛完之后就开始下落
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                //开始旋转动画
                startRotationAnimator()
            }

            override fun onAnimationEnd(animation: Animator?) {
                //上抛完之后就下落
                startFallAnimator()
            }
        })
    }

    /**
     * 上抛的时候需要旋转
     */
    private fun startRotationAnimator() {
        var rotationAnimator = when (shapeView.getCurrentShape()) {
            ShapeView.Shape.Circle, ShapeView.Shape.Square -> ObjectAnimator.ofFloat(shapeView, "rotation", 0f, 180f)
            ShapeView.Shape.Triangle -> ObjectAnimator.ofFloat(shapeView, "rotation", 0f, -120f)
        }
        rotationAnimator.duration = mAnimatorDuration
        rotationAnimator.interpolator = DecelerateInterpolator()
        rotationAnimator.start()
    }

    override fun setVisibility(visibility: Int) {
        // 不要再去排放和计算，少走一些系统的源码（View的绘制流程）
        super.setVisibility(View.INVISIBLE)
        // 清理动画
        shapeView.clearAnimation()
        shadowView.clearAnimation()
        // 把LoadingView从父布局移除
        parent?.let {
            // 从父布局移除
            (parent as ViewGroup).removeView(this)
            // 移除自己所有的View
            removeAllViews()
        }
        mNeedStopAnimator = true
    }

    /**
     * 设置LoadingView的背景
     */
    fun setLoadingViewBackground(@ColorInt backgroundColor: Int) {
        loadingViewRoot.background = ColorDrawable(backgroundColor)
    }

    /**
     * 设置LoadingView的背景
     */
    fun setLoadingViewBackground(@DrawableRes drawable: Drawable) {
        loadingViewRoot.background = drawable
    }

    /**
     * 设置LoadingView padding
     */
    fun setLoadingViewPadding(left: Int, top: Int, right: Int, bottom: Int) {
        loadingViewRoot.setPadding(left, top, right, bottom)
    }

    /**
     * 设置ShapeView margins
     */
    fun setShapeViewMargins(left: Int, top: Int, right: Int, bottom: Int) {
        if (bottom > 0) mTranslationDistance = bottom
        val params = shapeView.layoutParams as LayoutParams
        params.leftMargin = left
        params.topMargin = top
        params.rightMargin = right
        params.bottomMargin = bottom
        shapeView.layoutParams = params
    }

    /**
     * 设置LoadingView msg 文本
     */
    fun setLoadingMsgText(loadingMsg: String) {
        msg.text = loadingMsg
    }

    /**
     * 设置LoadingView msg 文本颜色
     */
    fun setLoadingMsgTextColor(@ColorInt loadingMsgTextColor: Int) {
        msg.setTextColor(loadingMsgTextColor)
    }

    /**
     * 设置LoadingView msg 文本字体大小
     */
    fun setLoadingMsgTextSize(loadingMsgTextSize: Float) {
        msg.textSize = loadingMsgTextSize
    }

    /**
     * 设置LoadingView msg margin
     */
    fun setLoadingMsgMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val params = msg.layoutParams as LinearLayout.LayoutParams
        params.leftMargin = left
        params.topMargin = top
        params.rightMargin = right
        params.bottomMargin = bottom
        msg.layoutParams = params
    }

}