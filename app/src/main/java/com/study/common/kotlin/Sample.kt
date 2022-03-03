package com.study.common.kotlin

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import kotlin.String as String1

const val CONSNT_NUMBER = 1


@SuppressLint("StaticFieldLeak")
object Sample {
    var name: String1? = "Mike"
    var view: View? = null
    var code: String1 = "00"

    val id: Int
        get() {
            return 100
        }
    val strs: Array<String1> = arrayOf("a", "b", "c")
    val strList = listOf("a", "b", "a")
    val anys: List<Any> = strList
    val strSet = setOf("a", 'b', "c")
    val map = mapOf("key1" to 1, "key2" to 2, "key3" to 2)
    val mapt = mutableMapOf("key1" to 1, "key2" to 2)
    /*lambda表达式，负责生成第二个及以后的元素，it表示前一个元素*/
    val sequence = generateSequence(0) { it + 1 }

    val range: IntRange = 0 until 1000
    val sequence1 = sequenceOf(1, 2, 3, 4)
    val result = sequence
            .map { i ->
                println("Map $i")
                i * 2
            }
            .filter { i ->
                println("Filter $i")
                i % 3 == 0
            }
    var max = if (a > b) a else b

    init {
        code += "1"
    }

    internal fun test() {
        name = null
        view?.setBackgroundColor(Color.RED)
        view!!.setBackgroundColor(Color.RED)
        map.get("key1")
        mapt.put("key4", 2)
        map["key1"]
        val mapc = map.toMutableMap()
        mapc["key4"] = 4
        val inner = Inner()

        strs.forEach { i -> print(i + " ") }

        for (i in 4 downTo 1) {
            print("$1i, ")
        }
        println(result.first())
    }

    class Inner {
        private val number = 1
    }

}