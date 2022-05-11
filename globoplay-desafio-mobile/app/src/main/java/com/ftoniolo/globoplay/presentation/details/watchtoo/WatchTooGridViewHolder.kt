package com.ftoniolo.globoplay.presentation.details.watchtoo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftoniolo.core.domain.model.WatchToo
import com.ftoniolo.globoplay.databinding.ItemWatchTooBinding
import com.ftoniolo.globoplay.framework.imageLoader.ImageLoader

class WatchTooGridViewHolder (
    itemWatchTooBinding: ItemWatchTooBinding,
    private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(itemWatchTooBinding.root) {

    private val imageItem = itemWatchTooBinding.imageCardWatchToo

    fun bind(watchToo: WatchToo) {
        imageLoader.load(
            imageItem, watchToo.imageUrl
        )
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader): WatchTooGridViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemWatchTooBinding.inflate(inflater, parent, false)
            return WatchTooGridViewHolder(itemBinding, imageLoader)
        }
    }
}