package com.github.alexxxdev.sample.utils

import android.support.v7.util.DiffUtil
import com.github.alexxxdev.sample.data.ImageResult

class ImageResultsDiffCallback(private val oldList: List<ImageResult>,
                               private val newList: List<ImageResult>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].type == newList[newItemPosition].type
                && oldList[oldItemPosition].file == newList[newItemPosition].file
                && oldList[oldItemPosition].status == newList[newItemPosition].status
                && oldItemPosition%2 == newItemPosition%2
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newList[newItemPosition].status to newList[newItemPosition].file
    }
}