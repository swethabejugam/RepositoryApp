package com.swetha.repoapp

import android.app.Application
import com.swetha.repoapp.injection.AppComponent
import com.swetha.repoapp.injection.DaggerAppComponent


open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}