package com.github.alexxxdev.sample.data

import com.ironz.binaryprefs.serialization.serializer.persistable.Persistable
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataInput
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataOutput
import java.util.*

class History(var currentFile:String = "", var data: MutableMap<UUID, ImageResult> = mutableMapOf<UUID, ImageResult>()) :Persistable {

    companion object {
        val KEY = "Coupon"
    }

    override fun readExternal(`in`: DataInput) {
        currentFile = `in`.readString()
        val size = `in`.readInt()
        for (i in 0 until size) {
            val imageResult = ImageResult()
            imageResult.readExternal(`in`)
            data[imageResult.id] = imageResult
        }
    }

    override fun deepClone(): Persistable {
        val history = History()
        history.currentFile = currentFile

        for (obj in data.entries) {
            val imageResult = obj.value
            history.data[imageResult.id] = imageResult
        }

        return history
    }

    override fun writeExternal(out: DataOutput) {
        out.writeString(currentFile)

        val size = data.size
        out.writeInt(size)

        for (obj in data.entries) {
            obj.value.writeExternal(out)
        }
    }
}