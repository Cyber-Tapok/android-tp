package com.tp.sharefile.data.model

import com.google.gson.annotations.SerializedName

data class Files(
    @SerializedName("files") val files: List<FileInfo>
)
