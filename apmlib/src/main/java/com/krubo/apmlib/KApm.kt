package com.krubo.apmlib

import android.annotation.SuppressLint
import android.content.Context
import com.krubo.apmlib.block.KLooperPrinter

@SuppressLint("StaticFieldLeak")
object KApm {

    lateinit var context: Context

    fun init(c: Context) {
        context = c.applicationContext
        KLooperPrinter.init()
    }
}