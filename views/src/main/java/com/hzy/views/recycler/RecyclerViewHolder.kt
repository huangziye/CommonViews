package com.hzy.views.recycler

import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView ViewHolder
 * @author: ziye_huang
 * @date: 2019/3/21
 */
open class RecyclerViewHolder(
    itemView: View,
    private var onItemClickListener: AdapterView.OnItemClickListener? = null
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var mPosition = -1

    init {
        itemView.setOnClickListener(this)
        //设置水波纹背景
        if (itemView.background == null) {
            val typedValue = TypedValue()
            val theme = itemView.context.theme
            val left = itemView.paddingLeft
            val top = itemView.paddingTop
            val right = itemView.paddingRight
            val bottom = itemView.paddingBottom
            if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                itemView.setBackgroundResource(typedValue.resourceId)
            }
            itemView.setPadding(left, top, right, bottom)
        }
    }

    override fun onClick(v: View?) {
        val position = adapterPosition
        if (position >= 0) {
            onItemClickListener?.onItemClick(null, v, position, itemId)
        } else {
            onItemClickListener?.onItemClick(null, v, mPosition, itemId)
        }
    }

    fun setPosition(position: Int) {
        mPosition = position
    }

    private fun findViewById(id: Int): View {
        return if (0 == id) itemView else itemView.findViewById(id)
    }

    fun text(id: Int, sequence: CharSequence): RecyclerViewHolder {
        val view = findViewById(id)
        if (view is TextView) {
            view.text = sequence
        }
        return this
    }

    fun text(id: Int, @StringRes stringRes: Int): RecyclerViewHolder {
        val view = findViewById(id)
        if (view is TextView) {
            view.setText(stringRes)
        }
        return this
    }

    fun textColorId(id: Int, colorId: Int): RecyclerViewHolder {
        val view = findViewById(id)
        if (view is TextView) {
            view.setTextColor(ContextCompat.getColor(view.context, colorId))
        }
        return this
    }

    fun image(id: Int, imageId: Int): RecyclerViewHolder {
        val view = findViewById(id)
        if (view is ImageView) {
            view.setImageResource(imageId)
        }
        return this
    }

    fun gone(id: Int): RecyclerViewHolder {
        val view = findViewById(id)
        view.visibility = View.GONE
        return this
    }

    fun visible(id: Int): RecyclerViewHolder {
        val view = findViewById(id)
        view.visibility = View.VISIBLE
        return this
    }
}