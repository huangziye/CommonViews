package com.hzy.views.recycler

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListAdapter
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/21
 */
abstract class RecyclerAdapter<T>(
    private var list: MutableList<T>,
    @LayoutRes private var layoutId: Int,
    private var listener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<RecyclerViewHolder>(), ListAdapter {

    private var mLastPosition = -1
    private var mOpenAnimationEnable = true

    init {
        setHasStableIds(false)
        setOnItemClickListener(listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        onBindViewHolder(holder, list[position], position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        addAnimate(holder, holder.layoutPosition)
    }

    fun refresh(data: MutableList<T>): RecyclerAdapter<T> {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
        mLastPosition = -1
        return this
    }

    fun loadMore(data: MutableList<T>): RecyclerAdapter<T> {
        list.addAll(data)
        notifyDataSetChanged()
        return this
    }

    fun insert(data: MutableList<T>): RecyclerAdapter<T> {
        list.addAll(0, data)
        notifyDataSetChanged()
        return this
    }


    /* ListAdapter start */

    private val mDataSetObservable = DataSetObservable()

    override fun registerDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.registerObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.unregisterObserver(observer)
    }

    fun notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated()
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun isEnabled(position: Int): Boolean {
        return true
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: RecyclerViewHolder? = null
        var tempView = convertView
        if (null == holder) {
            holder = onCreateViewHolder(parent!!, getItemViewType(position))
            tempView = holder.itemView
            tempView.tag = holder
        } else {
            holder = tempView?.tag as RecyclerViewHolder
        }
        holder.position = position
        onBindViewHolder(holder, position)
        addAnimate(holder, position)
        return tempView
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return count == 0
    }

    override fun getItem(position: Int): T {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    /* ListAdapter end */

    private fun addAnimate(holder: RecyclerViewHolder, position: Int) {
        if (mOpenAnimationEnable && mLastPosition < position) {
            holder.itemView.alpha = 0f
            holder.itemView.animate().alpha(1f).start()
            mLastPosition = position
        }
    }

    fun get(position: Int): T {
        return list[position]
    }

    fun setOpenAnimationEnable(enabled: Boolean) {
        mOpenAnimationEnable = enabled
    }

    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener): RecyclerAdapter<T> {
        this.listener = listener
        return this
    }

    protected abstract fun onBindViewHolder(holder: RecyclerViewHolder, model: T, position: Int)
}