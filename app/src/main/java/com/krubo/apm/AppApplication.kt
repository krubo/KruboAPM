package com.krubo.apm

import android.app.Application
import android.content.Context
import com.time.cost1.Test
import com.time.cost2.Test1
import com.time.cost2.Test2

class AppApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Test1().test()
        Test().test()
    }

    override fun onCreate() {
        super.onCreate()
        Test2().test1()
        Test2().test2()
    }
}