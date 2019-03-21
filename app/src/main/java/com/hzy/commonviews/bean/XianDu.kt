package com.hzy.commonviews.bean

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/21
 */
data class XianDu(
    val _id: String,
    val content: String,
    val cover: String,
    val crawled: String,
    val created_at: String,
    val deleted: Boolean,
    val published_at: String,
    val raw: String,
    val title: String,
    val uid: String,
    val url: String,
    val site: Site
)