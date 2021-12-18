package com.tp.sharefile.ui.files

import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.data.model.Files
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FilesView : MvpView {

    fun submitData(data: List<FileInfo>)
}