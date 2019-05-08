package com.zhu.bishopscores

import android.view.View

abstract class CalcController(view: View) {
    val context = view.context

    abstract fun updateView()

    abstract fun updateValue()

}