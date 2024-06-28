package com.hzy.commonviews.activity

import android.content.Intent
import android.os.Bundle
import com.hzy.commonviews.R
import com.hzy.views.base.BaseActivity
import com.hzy.views.util.ActivityUtil
import kotlinx.android.synthetic.main.activity_1.*
import kotlinx.android.synthetic.main.empty_layout.*

class Activity5: BaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_1
    }

    override fun initView(bundle: Bundle?) {
        btn.text = "5"
        btn.setOnClickListener {
//            ActivityUtil.finishTagActivity()
            ActivityUtil.finishAllActivitiesExceptBottom()
//            startActivity(Intent(this, Activity5::class.java))
        }
    }
}