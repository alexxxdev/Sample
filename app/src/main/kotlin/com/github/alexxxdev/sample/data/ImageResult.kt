package com.github.alexxxdev.sample.data

import android.os.AsyncTask
import androidx.work.State
import java.io.File
import java.util.*

data class ImageResult(val type: Type, var file:String, val id:UUID, var status: State = State.ENQUEUED) {

    enum class Type {
        NONE,
        ROTATE,
        GAMMA,
        MIRROR
    }
}
