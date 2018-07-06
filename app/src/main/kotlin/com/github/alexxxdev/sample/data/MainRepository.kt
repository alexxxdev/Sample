package com.github.alexxxdev.sample.data

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import java.util.*
import javax.inject.Inject

class MainRepository @Inject constructor(context: Context) {

    private val preferences: Preferences = BinaryPreferencesBuilder(context)
            .registerPersistable(History.KEY, History::class.java)
            .build()

    fun getHistory(function: (String, MutableMap<UUID, ImageResult>) -> Unit) {
        preferences.getPersistable(History.KEY, History())?.apply {
            function(this.currentFile, this.data)
        }
    }

    fun update(imageResults: MutableMap<UUID, ImageResult>) {
        preferences.getPersistable(History.KEY, History())?.apply {
            preferences.edit().putPersistable(History.KEY, History(this.currentFile, imageResults)).commit()
        }
    }

    fun update(currentFile: String?) {
        currentFile?.let {
            preferences.getPersistable(History.KEY, History())?.apply {
                preferences.edit().putPersistable(History.KEY, History(it, this.data)).commit()
            }
        }
    }
}