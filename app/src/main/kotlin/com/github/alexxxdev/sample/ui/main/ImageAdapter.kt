package com.github.alexxxdev.sample.ui.main

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.alexxxdev.sample.R
import com.github.alexxxdev.sample.data.ImageResult
import kotlinx.android.synthetic.main.item_list_image.view.*
import kotlinx.android.synthetic.main.main_screen.*

class ImageAdapter(val onClick: (Int, ImageResult) -> Unit) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var data = ArrayList<ImageResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_image, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun addItem(imageResult: ImageResult) {
        data.add(imageResult)
        notifyItemInserted(data.size - 1)
    }

    fun removeItem(pos: Int) {
        data.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, data.size)
    }

    class ViewHolder(itemView: View?, val onClick: (Int, ImageResult) -> Unit) : RecyclerView.ViewHolder(itemView){
        private lateinit var imageResult:ImageResult

        init {
            itemView?.setOnClickListener { onClick(adapterPosition, imageResult) }
        }

        fun bind(ir: ImageResult) {
            setIsRecyclable(true)
            imageResult = ir
            itemView.typeTitle.text = imageResult.type.name

            Glide.with(itemView)
                    .load(ir.file)
                    .apply(RequestOptions().centerCrop())
                    .into(itemView.imageView)

            if (adapterPosition % 2 == 0) {
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.colorBackground1))
            } else {
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.colorBackground2))
            }
        }
    }
}
