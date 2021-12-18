package com.tp.sharefile.domain

import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.data.repository.UploadRepository
import com.tp.sharefile.network.CountingResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.File
import javax.inject.Inject

class UploadInteractor @Inject constructor(
    private val uploadRepository: UploadRepository
) {
    fun upload(file: File): Observable<AttachmentUploadRemoteResult> =
        uploadAttachment(file, file.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun uploadAttachment(
        file: File,
        filename: String
    ): Observable<AttachmentUploadRemoteResult> {
        val progressEmitter = PublishSubject.create<Double>()
        val uploadRequest = uploadRepository.createUploadRequest(filename, file, progressEmitter)
        val uploadResult = uploadRequest
            .map<AttachmentUploadRemoteResult> {
                CountingResult.Completed(it)
            }
            .toObservable()

        val progressResult = progressEmitter
            .map<AttachmentUploadRemoteResult> {
                CountingResult.Progress(it)
            }

        return progressResult.mergeWith(uploadResult)
    }

}

typealias AttachmentUploadRemoteResult = CountingResult<FileInfo>