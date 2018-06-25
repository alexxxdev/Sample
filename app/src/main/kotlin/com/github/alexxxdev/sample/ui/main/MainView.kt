package com.github.alexxxdev.sample.ui.main

import android.view.View
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.ui.base.BaseView
import java.io.File

interface MainView : BaseView {
    fun showInitView()
    fun showImage(file: File)
    fun add(imageResult: ImageResult)
    fun showSubMenu(parent: View)
    fun deleteItem(pos: Int)
}