package com.tp.sharefile.data.repository

import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.network.CountingRequestBody
import com.tp.sharefile.network.api.FilesService
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadRepository @Inject constructor(private val service: FilesService) {

    fun createUploadRequest(
        filename: String,
        file: File,
        progressEmitter: PublishSubject<Double>
    ): Single<FileInfo> {
        val requestBody = createUploadRequestBody(file, progressEmitter)
        return service.upload(
            MultipartBody.Part.createFormData(
                name = "files[]",
                filename = filename,
                body = requestBody
            )
        )
    }

    private fun createUploadRequestBody(
        file: File,
        progressEmitter: PublishSubject<Double>
    ): RequestBody {
        val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return CountingRequestBody(fileRequestBody) { bytesWritten, contentLength ->
            val progress = 1.0 * bytesWritten / contentLength
            progressEmitter.onNext(progress)

            if (progress >= 1.0) {
                progressEmitter.onComplete()
            }
        }
    }
}