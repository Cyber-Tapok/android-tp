package com.tp.sharefile.ui.utils

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open var item: T? = null

    @CallSuper
    open fun bind(item: T) {
        this.item = item
    }
}