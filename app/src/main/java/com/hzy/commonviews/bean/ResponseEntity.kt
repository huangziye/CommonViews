package com.hzy.commonviews.bean

/**
 *
 * @author: ziye_huang
 * @date: 2019/3/21
 */
data class ResponseEntity<T>(val error: Boolean, val results: MutableList<T>)