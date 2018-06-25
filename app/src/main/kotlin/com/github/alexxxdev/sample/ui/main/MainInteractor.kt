package com.github.alexxxdev.sample.ui.main

import com.github.alexxxdev.sample.data.ImageResult
import java.io.File
import javax.inject.Inject

class MainInteractor @Inject constructor(){
    private var currentFile:File? = null
    private var currentActionPos:Int = -1
    private var currentActionImageResult:ImageResult? = null

    fun setImage(file: File) {
        currentFile = file
    }

    fun processing(type: ImageResult.Type, function: (ImageResult) -> Unit) {
        currentFile?.let {
            function(ImageResult(type, it))
        }
    }

    fun setCurrentAction(pos: Int, imageResult: ImageResult) {
        currentActionPos = pos
        currentActionImageResult = imageResult
    }

    fun getCurrentActionPos() = currentActionPos

    fun getCurrentActionImageResult() = currentActionImageResult

    fun deleteCurrentItem() {
        currentActionPos = -1
        currentActionImageResult = null
    }
}