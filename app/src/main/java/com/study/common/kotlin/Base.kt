package com.study.common.kotlin

fun topLevelFuncion() {

}

open class Base(var name: String) {
    var id = 0

    init {
        this.name = "Base"
        this.id=1
    }

    constructor(name: String, id: Int) : this(name) {
    }

    constructor(name: String, id: Int, age: Int) : this(name, id) {

    }

    constructor(test: Test) : this(test.name, test.id) {

    }
}