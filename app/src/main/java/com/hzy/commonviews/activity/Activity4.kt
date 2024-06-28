package com.hzy.commonviews.activity

import android.content.Intent
import android.os.Bundle
import com.hzy.commonviews.R
import com.hzy.views.base.BaseActivity
import com.hzy.views.util.ActivityUtil
import kotlinx.android.synthetic.main.activity_1.*

class Activity4 : BaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_1
    }

    override fun initView(bundle: Bundle?) {
        ActivityUtil.setTagActivity(this)
        btn.text = "4"
        btn.setOnClickListener {
//            val a2 = ActivityUtil.activityList.find { it is Activity2 } as Activity2
//            ActivityUtil.finishActivity(a2)
//            val a3 = ActivityUtil.activityList.find { it is Activity3 } as Activity3
//            val a4 = ActivityUtil.activityList.find { it is RecyclerViewActivity } as RecyclerViewActivity?
//            ActivityUtil.finishActivity(a3)

            // 注意：通常我们不从栈中移除Activity，因为Android会自行管理
            // 除非你有特殊需求，否则不建议在这里调用ActivityUtil.removeActivity(a2)
//            a2.finish()
//            a3.finish()
//            a4?.finish()
            startActivity(Intent(this, Activity5::class.java))
        }
    }
}