package com.tp.sharefile.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileInfo(
    val id: String,
    val fileName: String,
    val fileType: String,
    val author: String,
    val createAt: String,
): Parcelable