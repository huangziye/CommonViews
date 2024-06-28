package com.hzy.commonviews.activity

import android.content.Intent
import android.os.Bundle
import com.hzy.commonviews.R
import com.hzy.views.base.BaseActivity
import com.hzy.views.util.ActivityUtil
import kotlinx.android.synthetic.main.activity_1.*

class Activity2 : BaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_1
    }

    override fun initView(bundle: Bundle?) {
        ActivityUtil.setTagActivity(this)
        btn.text = "2"
        btn.setOnClickListener {
            startActivity(Intent(this, Activity3::class.java))
        }
    }
}