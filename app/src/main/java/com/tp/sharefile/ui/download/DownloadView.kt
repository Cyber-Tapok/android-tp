package com.tp.sharefile.ui.download

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.io.File

@StateStrategyType(AddToEndSingleStrategy::class)
interface DownloadView: MvpView {
    fun downloadState(isDownload: Boolean)
    fun onProgress(percents: Int)
    fun showMessage(message: String?)
}