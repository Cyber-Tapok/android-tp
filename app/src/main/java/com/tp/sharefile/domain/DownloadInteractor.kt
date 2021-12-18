package com.tp.sharefile.domain

import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.data.repository.DownloadRepository
import com.tp.sharefile.network.CountingResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.File
import javax.inject.Inject

class DownloadInteractor @Inject constructor(
    private val downloadRepository: DownloadRepository
) {

    fun download(id: String): Observable<DownloadResult> = downloadAttachment(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    private fun downloadAttachment(id: String): Observable<DownloadResult> {
        val progressEmitter = PublishSubject.create<Double>()
        val downloadResult = downloadRepository.download(id, progressEmitter)
        val uploadResult = downloadResult
            .map<DownloadResult> { CountingResult.Completed(it) }

        val progressResult = progressEmitter
            .map<DownloadResult> { CountingResult.Progress(it) }

        return progressResult.mergeWith(uploadResult)
    }
}


typealias DownloadResult = CountingResult<File>