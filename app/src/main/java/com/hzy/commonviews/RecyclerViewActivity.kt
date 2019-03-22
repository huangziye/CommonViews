package com.hzy.commonviews

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.hzy.commonviews.bean.ResponseEntity
import com.hzy.commonviews.bean.XianDu
import com.hzy.commonviews.service.ApiService
import com.hzy.request.Request
import com.hzy.views.recycler.RecyclerAdapter
import com.hzy.views.recycler.RecyclerViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recyclerview.*
import okhttp3.ResponseBody
import retrofit2.HttpException

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/21
 */
class RecyclerViewActivity : AppCompatActivity() {

    private var pageIndex = 1
    private var pageSize = 20
    private lateinit var mAdapter: MyAdapter
    lateinit var msg: TextView

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.black)//全局设置主题颜色
            ClassicsHeader(context).setFinishDuration(100)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setFinishDuration(100).setDrawableSize(20f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //设置增加或删除条目的动画
        recyclerView.itemAnimator = DefaultItemAnimator()

        refreshLayout.autoRefresh()
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false)//取消内容不满一页时开启上拉加载功能
        refreshLayout.setOnRefreshListener { refreshlayout -> onRefreshData(refreshlayout) }
        refreshLayout.setOnLoadMoreListener { refreshlayout -> onLoadMoreData(refreshlayout) }

        mAdapter = MyAdapter(mutableListOf(), R.layout.recycler_item,
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(
                    this,
                    mAdapter.getItem(position).title,
                    Toast.LENGTH_SHORT
                ).show()
            })
        recyclerView.adapter = mAdapter

        msg = empty_layout.findViewById(R.id.tv_msg)
        empty_layout.setOnClickListener {
            onRefreshData(refreshLayout)
        }
    }

    /**
     * 下拉刷新
     */
    fun onRefreshData(refreshlayout: RefreshLayout) {
        pageIndex = 1
        pageSize = 20
        val apiService = Request.Builder().baseUrl("http://gank.io/").create(ApiService::class.java)
        apiService.getData(pageIndex, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ResponseBody) {
                    val result = Gson().fromJson<ResponseEntity<XianDu>>(t.string(), ResponseEntity::class.java).results
                    val data = fromJsonToList(Gson().toJson(result), XianDu::class.java)
                    mAdapter.refresh(data)
                    if (data.size == 0) {
                        empty_layout.visibility = View.VISIBLE
                        msg.text = "暂无数据点击刷新"
                    } else {
                        empty_layout.visibility = View.GONE
                    }
                    refreshlayout.finishRefresh(true)//传入false表示刷新失败
                }

                override fun onError(e: Throwable) {
                    refreshlayout.finishRefresh(false)//传入false表示刷新失败
                    if (e is HttpException) {
                        val body = e.response().errorBody()
                        val result = body?.string()
                        Toast.makeText(this@RecyclerViewActivity, result, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RecyclerViewActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    /**
     * 上拉加载更多
     */
    fun onLoadMoreData(refreshlayout: RefreshLayout) {
        pageIndex++
        val apiService = Request.Builder().baseUrl("http://gank.io/").create(ApiService::class.java)
        apiService.getData(pageIndex, pageSize).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ResponseBody) {
                    val result = Gson().fromJson<ResponseEntity<XianDu>>(t.string(), ResponseEntity::class.java).results
                    val data = fromJsonToList(Gson().toJson(result), XianDu::class.java)
                    mAdapter.loadMore(data)
                    if (data.size != pageSize) {
                        refreshlayout.setNoMoreData(true)
                    }
                    refreshlayout.finishLoadMore(true)//传入false表示加载失败
                }

                override fun onError(e: Throwable) {
                    refreshlayout.finishLoadMore(false)//传入false表示加载失败
                    if (e is HttpException) {
                        val body = e.response().errorBody()
                        val result = body?.string()
                        Toast.makeText(this@RecyclerViewActivity, result, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RecyclerViewActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    /**
     * 在转换为实体对象时，如果带有泛型，会报 java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to model ， 可以采用该方法
     */
    fun <T> fromJsonToList(json: String, clazz: Class<T>): MutableList<T> {
        val list = arrayListOf<T>()
        try {
            val arrays = JsonParser().parse(json).asJsonArray
            for (array in arrays) {
                list.add(Gson().fromJson(array, clazz))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    class MyAdapter(list: MutableList<XianDu>, layoutId: Int, listener: AdapterView.OnItemClickListener) :
        RecyclerAdapter<XianDu>(list, layoutId, listener) {
        override fun onBindViewHolder(holder: RecyclerViewHolder, model: XianDu, position: Int) {
            holder.text(R.id.tv_title, model.title)
        }

    }
}