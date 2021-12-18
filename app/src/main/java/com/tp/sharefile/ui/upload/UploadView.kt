package com.tp.sharefile.ui.upload

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UploadView : MvpView {

    fun onUploadIdle()
    fun onFinish()
    fun onUploadStart()
    fun onProgressUpdate(percents: Int)
    fun onError(e: Throwable)
}