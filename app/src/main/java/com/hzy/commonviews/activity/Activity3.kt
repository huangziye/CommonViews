package com.hzy.commonviews.activity

import android.content.Intent
import android.os.Bundle
import com.hzy.commonviews.R
import com.hzy.views.base.BaseActivity
import com.hzy.views.util.ActivityUtil
import kotlinx.android.synthetic.main.activity_1.*
import kotlinx.android.synthetic.main.empty_layout.*

class Activity3: BaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_1
    }

    override fun initView(bundle: Bundle?) {
        ActivityUtil.setTagActivity(this)
        btn.text = "3"
        btn.setOnClickListener {
            startActivity(Intent(this, Activity4::class.java))
        }
    }
}