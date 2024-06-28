package com.hzy.commonviews.activity

import android.content.Intent
import android.os.Bundle
import com.hzy.commonviews.R
import com.hzy.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_1.*

class Activity1 : BaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_1
    }

    override fun initView(bundle: Bundle?) {
        btn.text = "1"
        btn.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
        }
    }
}