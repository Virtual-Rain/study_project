package com.study.common.kotlin

import java.lang.Exception

/**
Author:zx on 2019/11/2713:41
 */
val PI = 3.14
var a: Int = 1
var b: Int = 2
var x = 5

var str: String? = "hello"
var length: Int = str?.length ?: -1

fun main() {
    sun(a)
    println("hell world")
}

fun sun(b: Int): Int {
    a += 1
    return a + b
}

fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

fun minOf(a: Int, b: Int) = if (a < b) a else b
val list = listOf("a", "b", "c")


fun range(color: String) {
    for (x in 1..10) {
        for (x in 9 downTo 0 step 3) {

        }
    }
    when {
        "a" in list -> println("juicy")
        "b" in list -> println("no")
    }
    list.filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { println("Name" + it) }
    when (color) {
        "red", " " -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> 100
    }
    val result = if (color == "red") {
        "one"
    } else if (color == "Green") {
        "two"
    } else {
        "three"
    }
    when (x) {
        in 1..10 -> print("x 在1到10之间")
        in listOf(1, 2) -> print("x 在集合中")
        !in 10..20 -> print("x 不在10..20之间")
        else -> print("不在任何区间上")
    }
    var str = " "
    val isString = when (str) {
        is String -> true
        else -> false
    }
    var array = intArrayOf(1, 2, 3, 4)
    try {
        for (item in array) {
            for (i in 1..10) {

            }
        }
    } catch (e: Exception) {

    } finally {

    }
}

