package com.github.alexxxdev.sample.ui.main

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.State
import androidx.work.WorkManager
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.data.MainRepository
import com.github.alexxxdev.sample.utils.BitmapProcessingWorker
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainInteractor @Inject constructor(val workManager: WorkManager, val repository: MainRepository) {
    private var currentFile:String? = null
    private var currentActionImageResult:ImageResult? = null
    private var listFiles = mutableMapOf<UUID, ImageResult>()

    private var function: ((List<ImageResult>) -> Unit)? = null

    init {

        workManager.cancelAllWorkByTag("processing")
        workManager.pruneWork()

        observeJobs()
    }

    private fun observeJobs() {
        workManager.getStatusesByTag("processing").observeForever { list ->

            if (list != null && list.isNotEmpty()) {
                list.forEach { status ->
                    val id = status.id
                    listFiles[id]?.apply {

                        if (status.state == State.SUCCEEDED) {
                            listFiles[id] = this.copy(status = status.state, file = status.outputData.getString("file", this.file)
                                    ?: this.file)
                            repository.update(listFiles)
                        } else {
                            listFiles[id] = this.copy(status = status.state)
                        }
                        function?.invoke(listFiles.map { it.value }.toList().toMutableList())
                    }
                }
            }
        }
    }

    fun setImage(file: String) {
        currentFile = file
        repository.update(currentFile)
    }

    fun processing(type: ImageResult.Type, function: (List<ImageResult>) -> Unit) {
        this.function = function
        currentFile?.let {

            val myWorkRequest = OneTimeWorkRequest.Builder(BitmapProcessingWorker::class.java)
                    .addTag("processing")
                    .setInputData(Data.Builder()
                            .putString("file", it)
                            .putString("type", type.name)
                            .build())
                    .build()
            observeJobs()
            val imageResult = ImageResult(type = type, file = it, id = myWorkRequest.id)
            listFiles.plusAssign(imageResult.id to imageResult)
            function(listFiles.map { it.value }.toList().toMutableList())
            workManager.enqueue(myWorkRequest)
        }
    }

    fun setCurrentAction(id: UUID) {
        currentActionImageResult = listFiles[id]
    }

    fun getCurrentActionImageResult() = currentActionImageResult

    fun deleteCurrentItem(function: (List<ImageResult>) -> Unit) {
        currentActionImageResult?.let {
            listFiles.minusAssign(it.id)
            currentActionImageResult = null
            function(listFiles.map { it.value }.toList())
        }
    }

    fun getHistory(function: (String, List<ImageResult>) -> Unit) {
        repository.getHistory{ file, map ->
            currentFile = file
            listFiles = map
            function(file, listFiles.map { it.value }.toList())
        }
    }
}