package com.hzy.views.loading

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.hzy.views.R

class LoadingDialog : Dialog {

    constructor(@NonNull context: Context) : super(context)

    constructor(@NonNull context: Context, themeResId: Int) : super(context, themeResId)

    protected constructor(@NonNull context: Context, cancelable: Boolean, @Nullable cancelListener: DialogInterface.OnCancelListener) : super(
        context,
        cancelable,
        cancelListener
    )

    class Builder(private val context: Context) {
        private var mMessage: String? = null
        private var mIsShowMessage = true
        private var mIsCancelable = false
        private var mIsCancelOutside = false
        private var mBgColor = -1
        private var mTextColor = -1
        private var mTextSize = -1
        private var mView: View? = null

        /**
         * 设置提示信息
         *
         * @param message
         * @return
         */

        fun setMessage(message: String): Builder {
            this.mMessage = message
            return this
        }

        /**
         * 设置是否显示提示信息
         *
         * @param isShowMessage
         * @return
         */
        fun isShowMessage(isShowMessage: Boolean): Builder {
            this.mIsShowMessage = isShowMessage
            return this
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable
         * @return
         */

        fun cancelable(isCancelable: Boolean): Builder {
            this.mIsCancelable = isCancelable
            return this
        }

        /**
         * 设置是否可以取消
         *
         * @param isCancelOutside
         * @return
         */
        fun cancelOutside(isCancelOutside: Boolean): Builder {
            this.mIsCancelOutside = isCancelOutside
            return this
        }

        /**
         * 设置对话框背景
         *
         * @param bgColor
         * @return
         */
        fun bgColor(@DrawableRes bgColor: Int): Builder {
            this.mBgColor = bgColor
            return this
        }

        /**
         * 设置字体大小
         *
         * @param textSize
         * @return
         */
        fun textSize(@DimenRes textSize: Int): Builder {
            this.mTextSize = textSize
            return this
        }

        /**
         * 设置字体颜色
         *
         * @param textColor
         * @return
         */
        fun textColor(@IntegerRes textColor: Int): Builder {
            this.mTextColor = textColor
            return this
        }

        /**
         * 设置对话框View
         */
        fun setView(view: View) {
            mView = view
        }

        fun create(): LoadingDialog {
            val loadingDailog = LoadingDialog(context, R.style.LoadingDialogStyle)
            if (null == mView) {
                val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
                val llLoadingDialog = view.findViewById<LinearLayout>(R.id.ll_loading_dialog)
                val msgText = view.findViewById<TextView>(R.id.tipTextView)
                llLoadingDialog.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        if (-1 == mBgColor) R.drawable.bg_loading_dialog else mBgColor
                    )
                )
                if (mIsShowMessage) {
                    if (-1 != mTextSize) {
                        msgText.textSize = mTextSize.toFloat()
                    }
                    if (-1 != mTextColor) {
                        msgText.setTextColor(mTextColor)
                    }
                    msgText.text = mMessage
                } else {
                    msgText.visibility = View.GONE
                }
                loadingDailog.setContentView(view)
            } else {
                loadingDailog.setContentView(mView)
            }
            loadingDailog.setCancelable(mIsCancelable)
            loadingDailog.setCanceledOnTouchOutside(mIsCancelOutside)
            return loadingDailog
        }

    }
}