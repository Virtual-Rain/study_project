package com.study.common.kotlin

class Test {
    var name: String = "Test"
    var id = 0

    fun area(width: Int, height: Int) = width * height

    fun sayHi(name: String = "world", age: Int = 10) = println("Hi" + name)

    fun test() {
        sayHi("kaixue.io")
        sayHi(age = 50)
    }
}