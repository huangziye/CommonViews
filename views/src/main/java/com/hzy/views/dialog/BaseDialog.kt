package com.hzy.views.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import com.hzy.views.R

/**
 * 对话框基类
 * @author: ziye_huang
 * @date: 2019/1/30
 */
abstract class BaseDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置Style
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }

    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val window = dialog.window
        //去掉边框
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(setWidth(), setHeigh())
        if (-1 != setAnimation()) {
            if (0 == setAnimation()) {
                window.setWindowAnimations(R.style.BottomDialogAnimation)
            } else {
                window.setWindowAnimations(setAnimation())
            }
        }
        window.setGravity(setGravity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //去除标题栏
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return createView(inflater, container)
    }

    /**
     * 设置对话框动画效果
     * 0 使用默认效果
     * -1 不适用动画效果
     * 大于0 使用自定义效果
     */
    @StyleRes
    protected fun setAnimation(): Int {
        return -1
    }

    /**
     * 设置对话框的宽度
     */
    fun setWidth(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 设置对话框的高度
     */
    fun setHeigh(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 对话框对齐方式
     */
    fun setGravity(): Int {
        return Gravity.CENTER
    }

    /**
     * 获取屏幕的宽度
     */
    fun getScreenWidth(): Int {
        val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕的高度
     */
    fun getScreenHeight(): Int {
        val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 设置布局文件
     */
    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?): View
}