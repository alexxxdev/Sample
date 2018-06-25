package com.github.alexxxdev.sample.ui.main

import com.arellomobile.mvp.InjectViewState
import com.github.alexxxdev.sample.ui.base.BasePresenter
import java.io.File
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showInitView()
    }

    fun onImagesPicked(imagesFiles: List<File>) {
        if(imagesFiles.isNotEmpty()) {
            viewState.showImage(imagesFiles[0])
        }
    }

}