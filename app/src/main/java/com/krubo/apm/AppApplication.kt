package com.krubo.apm

import android.app.Application
import android.content.Context

class AppApplication:Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}