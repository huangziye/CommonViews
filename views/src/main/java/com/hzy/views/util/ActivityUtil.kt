package com.hzy.views.util

import android.app.Activity
import java.util.*

/**
 * Activity工具类
 */
object ActivityUtil {

    /**
     * 存放Activity的集合
     */
    val activityList = Stack<Activity>()

    /**
     * 存放设置Tag的Activity集合
     */
    private val tagList = Stack<Activity>()

    /**
     * 针对特殊需求，需要关闭多个页面的情况
     */
    fun setTagActivity(activity: Activity) {
        tagList.push(activity)
    }

    /**
     * 关闭Tag Activity
     */
    fun finishTagActivity() {
        for (i in tagList.size - 1 downTo 0) {
            finishActivity(tagList[i])
            tagList.removeAt(i)
        }
    }

    /**
     * 添加Activity到栈中（通常在onCreate中调用）
     */
    fun addActivity(activity: Activity) {
        if (!activityList.contains(activity)) {
            activityList.push(activity)
        }
    }

    /**
     * 从栈中移除Activity（通常在onDestroy中调用）
     */
    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * 获取当前栈顶的Activity（如果栈不为空）
     */
    fun getCurrentActivity(): Activity? {
        return if (activityList.isEmpty()) null else activityList.last()
    }

    /**
     * 获取Activity栈的大小
     */
    fun getActivityCount(): Int {
        return activityList.size
    }


    /**
     * 关闭栈顶的Activity（如果栈不为空）
     */
    fun finishTopActivity() {
        if (!activityList.isEmpty()) {
            activityList.last().finish()
            activityList.removeAt(activityList.size - 1)
        }
    }

    /**
     * 关闭所有Activity（除了栈底的Activity）
     */
    fun finishAllActivitiesExceptBottom() {
        for (i in activityList.size - 1 downTo 1) {
            activityList[i].finish()
            activityList.removeAt(i)
        }
    }

    /**
     * 关闭所有Activity（除了栈顶和栈底的Activity）
     */
    fun finishAllActivitiesExceptTopAndBottom() {
        for (i in activityList.size - 2 downTo 1) {
            activityList[i].finish()
            activityList.removeAt(i)
        }
    }

    /**
     * 关闭指定的Activity（如果存在于栈中）
     */
    fun finishActivity(activity: Activity) {
        if (activityList.contains(activity)) {
            activity.finish()
            removeActivity(activity)
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(clz: Class<Any>) {
        for (activity in activityList) {
            if (activity::class == clz) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 在Activity的onCreate回调中调用此方法
     */
    fun onActivityCreated(activity: Activity) {
        addActivity(activity)
    }

    /**
     * 在Activity的onDestroy回调中调用此方法
     */
    fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }

}