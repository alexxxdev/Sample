package com.github.alexxxdev.sample.ui.base

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject
import javax.inject.Provider

abstract class BaseScreen<T : MvpPresenter<*>> : MvpAppCompatActivity() {

    @Inject
    lateinit var presenterProvider: Provider<T>

    protected abstract fun providePresenter(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}