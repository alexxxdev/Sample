package com.github.alexxxdev.sample

import android.app.Activity
import android.app.Application
import com.github.alexxxdev.sample.di.AppComponent
import com.github.alexxxdev.sample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()

        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}