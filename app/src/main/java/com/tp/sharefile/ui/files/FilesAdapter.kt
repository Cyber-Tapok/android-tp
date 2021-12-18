package com.tp.sharefile.ui.files

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.databinding.FileItemBinding
import com.tp.sharefile.ui.utils.BaseViewHolder

class FilesAdapter : ListAdapter<FileInfo, FileHolder>(FileItemDiffCallback()) {

    var clickListener: ((FileInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FileItemBinding.inflate(layoutInflater, parent, false)
        return FileHolder(binding).apply {
            setClickListener(clickListener)
        }
    }

    override fun onBindViewHolder(holder: FileHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FileItemDiffCallback : DiffUtil.ItemCallback<FileInfo>() {
    override fun areItemsTheSame(oldItem: FileInfo, newItem: FileInfo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FileInfo, newItem: FileInfo): Boolean =
        oldItem == newItem
}

class FileHolder(private val binding: FileItemBinding) : BaseViewHolder<FileInfo>(binding.root) {

    fun setClickListener(click: ((file: FileInfo) -> Unit)?) {
        itemView.setOnClickListener { item?.let { click?.invoke(it) } }
    }

    override fun bind(item: FileInfo) {
        super.bind(item)
        with(binding) {
            name.text = item.fileName
            extension.text = item.fileType
            author.text = item.author
            createAt.text = item.createAt
        }
    }
}