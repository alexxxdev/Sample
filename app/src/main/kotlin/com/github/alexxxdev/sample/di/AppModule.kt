package com.github.alexxxdev.sample.di

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesWorkManager(): WorkManager {
        return WorkManager.getInstance()?: throw Exception("Not Found WorkManager")
    }
}