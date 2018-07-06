package com.github.alexxxdev.sample.utils

import android.util.Log
import androidx.work.Worker
import android.R.attr.path
import android.graphics.*
import android.os.Environment
import android.support.media.ExifInterface
import androidx.work.Data
import com.github.alexxxdev.sample.data.ImageResult
import java.io.FileOutputStream
import java.util.*


class BitmapProcessingWorker:Worker() {
    override fun doWork(): Result {

        val filePath = inputData.getString("file", "")?:""
        val type = inputData.getString("type", "")?:""

        if(filePath.isEmpty()) return Result.FAILURE

        try {
            var bitmap = BitmapFactory.decodeFile(filePath)
            val matrix = Matrix()
            when(ImageResult.Type.valueOf(type)){
                ImageResult.Type.ROTATE -> {
                    matrix.postRotate(90f)
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                }
                ImageResult.Type.MIRROR -> {
                    matrix.preScale(-1f,1f)
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                }
                ImageResult.Type.GAMMA -> {
                    val bitmapGrayscale = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
                    val c = Canvas(bitmapGrayscale)
                    val paint = Paint()
                    val cm = ColorMatrix()
                    cm.setSaturation(0f)
                    val f = ColorMatrixColorFilter(cm)
                    paint.colorFilter = f
                    c.drawBitmap(bitmap, 0f, 0f, paint)
                    bitmap = bitmapGrayscale
                }
                ImageResult.Type.NONE -> return Result.FAILURE
            }

            val fOut = FileOutputStream(applicationContext.filesDir.absolutePath + "/" + id)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
            outputData = Data.Builder()
                    .putString("file", applicationContext.filesDir.absolutePath + "/" + id)
                    .build()
        }catch (e:Exception){
            return Result.FAILURE
        }

        Thread.sleep(Random().nextInt(25000)+5000L)
        return Result.SUCCESS
    }
}