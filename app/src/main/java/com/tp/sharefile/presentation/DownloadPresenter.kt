package com.tp.sharefile.presentation

import com.tp.sharefile.domain.DownloadInteractor
import com.tp.sharefile.network.CountingResult
import com.tp.sharefile.presentation.utils.RxPresenter
import com.tp.sharefile.ui.download.DownloadView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class DownloadPresenter @Inject constructor(
    private val downloadInteractor: DownloadInteractor
) : RxPresenter<DownloadView>() {

    override fun onFirstViewAttach() {
        viewState.downloadState(false)
    }

    fun download(id: String) {
        compositeDisposable.add(
            downloadInteractor.download(id)
                .doOnSubscribe { viewState.downloadState(true) }
                .doFinally { viewState.downloadState(false) }
                .subscribe(
                    { value ->
                        when (value) {
                            is CountingResult.Completed -> viewState.showMessage("Файл скачан в ${value.result.absoluteFile}")
                            is CountingResult.Progress -> viewState.onProgress(value.percents)
                        }
                    },
                    { e -> viewState.showMessage(e.localizedMessage) }
                )
        )
    }
}