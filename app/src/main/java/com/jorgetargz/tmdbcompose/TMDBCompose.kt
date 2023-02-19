package com.jorgetargz.tmdbcompose

import android.app.Application
import com.jorgetargz.tmdbcompose.di.appModule
import com.jorgetargz.tmdbcompose.di.networkModule
import com.jorgetargz.tmdbcompose.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class TMDBCompose : Application(){
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@TMDBCompose)
            modules(listOf(appModule, roomModule, networkModule))
        }
        Timber.plant(Timber.DebugTree())
    }
}