package com.github.alexxxdev.sample.ui.main

import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.ui.base.BasePresenter
import java.io.File
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
            interactor.setImage(imagesFiles[0])
            viewState.showImage(imagesFiles[0])
        }
    }

    fun onRotateImage() {
        interactor.processing(ImageResult.Type.ROTATE){
            viewState.add(it)
        }
    }

    fun onTranslationGammaImage() {
        interactor.processing(ImageResult.Type.GAMMA){
            viewState.add(it)
        }
    }

    fun onMirrorReflectionImage() {
        interactor.processing(ImageResult.Type.MIRROR){
            viewState.add(it)
        }
    }

    fun onItemClick(pos:Int, imageResult: ImageResult) {
        viewState.showSubMenu(pos)
        interactor.setCurrentAction(pos, imageResult)
    }

    fun onDeleteItem() {
        viewState.deleteItem(interactor.getCurrentActionPos())
        interactor.deleteCurrentItem()
    }

    fun onUseItem() {
        interactor.getCurrentActionImageResult()?.let {
            interactor.setImage(it.file)
            viewState.useImage(it.file)
        }
    }

}