package com.github.alexxxdev.sample.ui.main

import com.arellomobile.mvp.InjectViewState
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.ui.base.BasePresenter
import java.io.File
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    private lateinit var file: File

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showInitView()
    }

    fun onImagesPicked(imagesFiles: List<File>) {
        if(imagesFiles.isNotEmpty()) {
            file = imagesFiles[0]
            viewState.showImage(file)
        }
    }

    fun onRotateImage() {
        viewState.add(ImageResult(ImageResult.Type.ROTATE, file))
    }

    fun onTranslationGammaImage() {
        viewState.add(ImageResult(ImageResult.Type.GAMMA, file))
    }

    fun onMirrorReflectionImage() {
        viewState.add(ImageResult(ImageResult.Type.MIRROR, file))
    }

    fun onItemClick(it: ImageResult) {

    }

}