package com.github.alexxxdev.sample.di

import com.github.alexxxdev.sample.ui.main.MainScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    fun bindMainScreen(): MainScreen
}