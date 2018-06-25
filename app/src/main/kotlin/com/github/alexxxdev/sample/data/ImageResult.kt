package com.github.alexxxdev.sample.data

import java.io.File

class ImageResult(val type: Type, val file:File) {


    enum class Type {
        ROTATE,
        GAMMA,
        MIRROR
    }
}
