package com.github.alexxxdev.sample.ui.main

import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.ui.base.BaseView
import java.io.File

interface MainView : BaseView {
    fun showInitView()
    fun showImage(file: File)
    fun add(imageResult: ImageResult)
}