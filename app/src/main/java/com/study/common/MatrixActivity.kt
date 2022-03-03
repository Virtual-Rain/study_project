package com.study.common

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.study.common.kotlin.Base
import com.study.common.kotlin.Impl
import com.study.common.kotlin.Sample
import com.study.common.kotlin.topLevelFuncion
import kotlinx.android.synthetic.main.activity_matrix.*
import kotlinx.android.synthetic.main.layout_fold_matrix.*
import java.lang.IllegalArgumentException


class MatrixActivity : AppCompatActivity(), Impl {


    var banner: String? = "Mike"
    val size = 18
    lateinit var view: View

    val strs: Array<String> = arrayOf("a", "b", "c")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fold_matrix)
        view = findViewById(R.id.iv_home_banner)
        cook(banner)
        topLevelFuncion()
    }

    override fun food() {
        banner += "zhu"
    }

    private fun cook(name: String?): Unit {
        var activity: MatrixActivity = MatrixActivity()
    }

    private fun change(name: String): String {
        return "${name.length}:dog"
    }

    fun login(user: String, password: String, illegalStr: String) {
        require(user.isNotEmpty()){illegalStr}
        require(password.isNotEmpty()){illegalStr}
    }
}
