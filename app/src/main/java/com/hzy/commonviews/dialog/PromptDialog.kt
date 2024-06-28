package com.hzy.commonviews.dialog

import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hzy.commonviews.R
import com.hzy.views.dialog.BaseDialog

class PromptDialog : BaseDialog() {
    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mCancel: String? = null
    private var mConfirm: String? = null
    private var mCancelClickListener: View.OnClickListener? = null
    private var mConfirmClickListener: View.OnClickListener? = null
    private var mCenterShwoMessage: Boolean = false
    private var mMessageCharSequence: CharSequence? = null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.dialog_prompt, container, false)
        view.findViewById<TextView>(R.id.tv_cancel).setOnClickListener(mCancelClickListener)
        view.findViewById<TextView>(R.id.tv_confirm).setOnClickListener(mConfirmClickListener)
        view.findViewById<TextView>(R.id.tv_title).text = mTitle
        view.findViewById<TextView>(R.id.tv_message).text =
            if (!TextUtils.isEmpty(mMessage)) mMessage else if (!TextUtils.isEmpty(mMessageCharSequence)) {
                view.findViewById<TextView>(R.id.tv_message).movementMethod = LinkMovementMethod.getInstance()
                mMessageCharSequence
            } else ""
        view.findViewById<TextView>(R.id.tv_cancel).text = mCancel
        view.findViewById<TextView>(R.id.tv_confirm).text = mConfirm
        if (mCenterShwoMessage) {
            view.findViewById<TextView>(R.id.tv_message).gravity = Gravity.CENTER
        }
        return view
    }

    fun setTitle(title: String) {
        this.mTitle = title
    }

    fun setMessage(message: String) {
        this.mMessage = message
    }

    fun setCancel(cancel: String) {
        this.mCancel = cancel
    }

    fun setConfirm(confirm: String) {
        this.mConfirm = confirm
    }

    fun isCenterShowMessage(center: Boolean) {
        this.mCenterShwoMessage = center
    }

    fun setCancelClickListener(listener: View.OnClickListener) {
        this.mCancelClickListener = listener
    }

    fun setConfirmClickListener(listener: View.OnClickListener) {
        this.mConfirmClickListener = listener
    }

    fun setMessageCharSequence(ss: CharSequence) {
        this.mMessageCharSequence = ss
    }

    override fun setWidth(): Int {
        return getScreenWidth() * 3 / 5
    }
}