package com.example.w_jetpack_compose

import android.app.Application
import com.example.w_jetpack_compose.core.di.appModule
import com.example.w_jetpack_compose.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}
