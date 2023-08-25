package com.orafaelsc.robots.extensions

fun <T> List<T>.randomItem(): T? {
    return try {
        val randomIndex = (indices).random()
        get(randomIndex)
    } catch (e: NoSuchElementException) {
        null
    }
}
