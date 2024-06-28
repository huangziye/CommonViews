package com.hzy.views.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hzy.views.util.ActivityUtil

/**
 * Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        ActivityUtil.onActivityCreated(this)
        if (initLayout() != 0) {
            setContentView(initLayout())
            initView(savedInstanceState)
        }
    }

    protected open fun beforeOnCreate() {}


    /**
     * 设置布局 layout
     */
    protected abstract fun initLayout(): Int

    /**
     * 初始化 view
     */
    protected abstract fun initView(bundle: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtil.onActivityDestroyed(this)
    }


}