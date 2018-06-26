package com.github.alexxxdev.sample.ui.main

import android.view.View
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.ui.base.BaseView
import com.github.alexxxdev.sample.utils.AddToEndSingleTagStrategy
import java.io.File

interface MainView : BaseView {
    fun showInitView()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showImage(file: File)

    fun add(imageResult: ImageResult)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = POPUP_MENU_TAG)
    fun showSubMenu(pos: Int)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = POPUP_MENU_TAG)
    fun deleteItem(pos: Int)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = POPUP_MENU_TAG)
    fun useImage(file: File)

    companion object {
        const val POPUP_MENU_TAG = "POPUP_MENU"
    }
}