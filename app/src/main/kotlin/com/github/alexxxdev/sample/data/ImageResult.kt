package com.github.alexxxdev.sample.data

import android.os.AsyncTask
import androidx.work.State
import com.ironz.binaryprefs.serialization.serializer.persistable.Persistable
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataInput
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataOutput
import java.io.File
import java.util.*

data class ImageResult(var type: Type, var file:String, var id:UUID, var status: State = State.ENQUEUED):Persistable {
    constructor() : this(Type.NONE, "", UUID.randomUUID(), State.ENQUEUED)

    override fun readExternal(`in`: DataInput) {
        type = Type.valueOf(`in`.readString())
        file = `in`.readString()
        id = UUID.fromString(`in`.readString())
        status = State.valueOf(`in`.readString())
    }

    override fun deepClone(): Persistable {
        val imageResult = ImageResult()
        imageResult.type = type
        imageResult.file = file
        imageResult.id = id
        imageResult.status = status
        return imageResult
    }

    override fun writeExternal(out: DataOutput) {
        out.writeString(type.name)
        out.writeString(file)
        out.writeString(id.toString())
        out.writeString(status.name)
    }

    enum class Type {
        NONE,
        ROTATE,
        GAMMA,
        MIRROR
    }
}
