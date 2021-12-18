package com.tp.sharefile.presentation

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.domain.FilesInteractor
import com.tp.sharefile.navigation.Screens
import com.tp.sharefile.presentation.utils.RxPresenter
import com.tp.sharefile.ui.files.FilesView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class FilesPresenter @Inject constructor(
    private val router: Router,
    private val filesInteractor: FilesInteractor
) : RxPresenter<FilesView>() {

//    fun navigateToUpload() = router.navigateTo(Screens.uploadScreen())

//    fun navigateToDownload(file: FileInfo) = router.navigateTo(Screens.downloadScreen(file))

    override fun onFirstViewAttach() {
        observeFiles()
        filesInteractor.sendSubscribe()
    }

    private fun observeFiles() {
        compositeDisposable.add(
            filesInteractor.observeFiles()
                .map { it.files }
                .doOnNext {
                    Log.e("TTT", it.toString())
                    viewState.submitData(it)
                }
                .subscribe()
        )
    }

    fun uploadToServer() {

    }
}