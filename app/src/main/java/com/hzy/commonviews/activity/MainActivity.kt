package com.hzy.commonviews.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hzy.commonviews.R
import com.hzy.commonviews.dialog.PromptDialog
import com.hzy.views.base.BaseActivity
import com.hzy.views.loading.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(bundle: Bundle?) {
        btn_loading.setOnClickListener(this)
        btn_prompt.setOnClickListener(this)
        btn_recycler.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_loading -> {
                val dialog = LoadingDialog.Builder(this).cancelOutside(true).isShowMessage(true).setMessage("请稍候...").create()
                dialog.show()
            }
            R.id.btn_prompt -> {
                val dialog = PromptDialog()
                dialog.isCancelable = false
                dialog.setTitle("提示")
                dialog.setCancel("取消")
                dialog.setConfirm("确定")
                dialog.setMessage("是否确认要退出？")
                dialog.isCenterShowMessage(true)
                dialog.setCancelClickListener(View.OnClickListener {
                    dialog.dismiss()
                })
                dialog.setConfirmClickListener(View.OnClickListener {
                    dialog.dismiss()
                })
                dialog.show(supportFragmentManager, "prompt")
            }
            R.id.btn_recycler -> {
//                startActivity(Intent(this, RecyclerViewActivity::class.java))
                startActivity(Intent(this, Activity1::class.java))
            }
        }
    }
}
