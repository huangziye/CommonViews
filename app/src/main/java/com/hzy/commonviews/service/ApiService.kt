package com.hzy.commonviews.service

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/20
 */
interface ApiService {
    @GET("api/xiandu/data/id/appinn/count/{pageSize}/page/{pageIndex}")
    fun getData(@Path("pageIndex") pageIndex: Int, @Path("pageSize") pageSize: Int): Observable<ResponseBody>
}