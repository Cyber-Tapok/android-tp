package com.tp.sharefile.di

import android.app.Application
import android.content.Context
import com.tp.sharefile.di.components.AppComponent
import com.tp.sharefile.di.components.DaggerAppComponent

class MainApp: Application() {

    private val _appComponent by lazy {
        DaggerAppComponent.builder().context(context = this).build()
    }

    val appComponent get() = _appComponent



}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }
