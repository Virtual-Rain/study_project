package com.study.common.entity

class User {
    var name = "Mike"
        get() {
            return field + "nb"
        }
        set(value) {
            field = "Cute$value"
        }
    var arry: IntArray = intArrayOf(1, 2)
}