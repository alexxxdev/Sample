package com.github.alexxxdev.sample.ui.main

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.State
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.alexxxdev.sample.R
import com.github.alexxxdev.sample.data.ImageResult
import com.github.alexxxdev.sample.utils.ImageResultsDiffCallback
import kotlinx.android.synthetic.main.item_list_image.view.*

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.bindPayloads(data[position], payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setItems(imageResult: List<ImageResult>) {
        val diffUtilCallback = ImageResultsDiffCallback(data, imageResult)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        data = ArrayList(imageResult)
        diffResult.dispatchUpdatesTo(this)
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

            itemView.progressBar.visibility = if (imageResult.status==State.SUCCEEDED) View.GONE else View.VISIBLE

            updateImage(ir.file, imageResult.status)

            updateBackground()
        }

        fun bindPayloads(imageResult: ImageResult, payloads: MutableList<Any>) {
            val payload:Pair<State, String>? = payloads[0] as? Pair<State, String>
            payload?.let {
                itemView.progressBar.visibility = if (payload.first == State.SUCCEEDED) View.GONE else View.VISIBLE

                updateImage(payload.second, payload.first)
            }

            updateBackground()
        }

        private fun updateImage(path: String, state: State) {
            if(state == State.SUCCEEDED) {
                itemView.imageView.visibility = View.VISIBLE
                Glide.with(itemView)
                        .load(path)
                        .apply(RequestOptions().centerCrop())
                        .into(itemView.imageView)
            } else {
                itemView.imageView.visibility = View.GONE
            }
        }

        private fun updateBackground() {
            if (adapterPosition % 2 == 0) {
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.colorBackground1))
            } else {
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.colorBackground2))
            }
        }
    }
}
