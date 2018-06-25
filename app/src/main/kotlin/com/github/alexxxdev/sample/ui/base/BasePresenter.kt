package com.github.alexxxdev.sample.ui.base

import com.arellomobile.mvp.MvpPresenter

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>()
