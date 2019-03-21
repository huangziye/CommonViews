package com.hzy.commonviews

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hzy.views.loading.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_loading.setOnClickListener(this)
        btn_recycler.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_loading -> {
                val dialog = LoadingDialog.Builder(this).cancelOutside(true).create()
                dialog.show()
            }
            R.id.btn_recycler -> {
                startActivity(Intent(this, RecyclerViewActivity::class.java))
            }
        }
    }
}
