package com.tp.sharefile.presentation

import com.tp.sharefile.domain.UploadInteractor
import com.tp.sharefile.network.CountingResult
import com.tp.sharefile.presentation.utils.RxPresenter
import com.tp.sharefile.ui.upload.UploadView
import moxy.InjectViewState
import java.io.File
import javax.inject.Inject

@InjectViewState
class UploadPresenter @Inject constructor(
    private val uploadInteractor: UploadInteractor
) : RxPresenter<UploadView>() {

    override fun onFirstViewAttach() {
        viewState.onUploadIdle()
    }

    fun upload(file: File) = compositeDisposable.add(
        uploadInteractor.upload(file)
            .doOnSubscribe {
                viewState.onUploadStart()
            }
            .subscribe({ value ->
                when (value) {
                    is CountingResult.Completed -> viewState.onFinish()
                    is CountingResult.Progress -> viewState.onProgressUpdate(value.percents)
                }
            }, { e -> viewState.onError(e) })

    )
}