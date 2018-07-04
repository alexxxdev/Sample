package com.github.alexxxdev.sample.ui.main

import com.arellomobile.mvp.InjectViewState
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.ui.base.BasePresenter
import java.io.File
import java.util.*
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val interactor: MainInteractor
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showInitView()
    }

    fun onImagesPicked(imagesFiles: List<File>) {
        if(imagesFiles.isNotEmpty()) {
            interactor.setImage(imagesFiles[0].path)
            viewState.showImage(imagesFiles[0].path)
        }
    }

    fun onRotateImage() {
        interactor.processing(ImageResult.Type.ROTATE){
            viewState.setListImageResult(it)
        }
    }

    fun onTranslationGammaImage() {
        interactor.processing(ImageResult.Type.GAMMA){
            viewState.setListImageResult(it)
        }
    }

    fun onMirrorReflectionImage() {
        interactor.processing(ImageResult.Type.MIRROR){
            viewState.setListImageResult(it)
        }
    }

    fun onItemClick(pos: Int, id: UUID) {
        viewState.showSubMenu(pos)
        interactor.setCurrentAction(id)
    }

    fun onDeleteItem() {
        interactor.deleteCurrentItem {
            viewState.setListImageResult(it)
            viewState.deleteItem()
        }
    }

    fun onUseItem() {
        interactor.getCurrentActionImageResult()?.let {
            interactor.setImage(it.file)
            viewState.useImage(it.file)
        }
    }

}