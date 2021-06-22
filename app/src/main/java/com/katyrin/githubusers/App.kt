package com.katyrin.githubusers

import android.app.Application
import com.katyrin.githubusers.di.AppComponent
import com.katyrin.githubusers.di.modules.AppModule
import com.katyrin.githubusers.di.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}