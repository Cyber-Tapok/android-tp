package com.tp.sharefile.data.repository

import android.os.Environment
import android.util.Log
import com.tp.sharefile.network.CountingResponseSink
import com.tp.sharefile.network.api.FilesService
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import okhttp3.ResponseBody
import okio.buffer
import okio.sink
import retrofit2.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject


class DownloadRepository @Inject constructor(private val service: FilesService) {

    fun download(id: String, progressEmitter: PublishSubject<Double>): Observable<File> =
        service.download(id)
            .flatMap { saveFile(it, progressEmitter) }

    private fun saveFile(
        response: Response<ResponseBody>,
        progressEmitter: PublishSubject<Double>
    ): Observable<File> {
        return try {
            val header = response.headers()["Content-Disposition"]
            val fileName =
                header?.replace("attachment; filename=", "") ?: throw IOException("Файл не найден")
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile,
                fileName
            )
            val sink = file.sink()
            val responseSink =
                CountingResponseSink(sink, response.body()!!) { bytesWritten, contentLength ->
                    val progress = 1.0 * bytesWritten / contentLength
                    progressEmitter.onNext(progress)

                    if (progress >= 1.0) {
                        progressEmitter.onComplete()
                    }
                    Log.e("TTT1", progress.toString())
                }.buffer()
            responseSink.writeAll(response.body()!!.source())
            responseSink.close()
            Observable.just(file)
        } catch (e: Exception) {
            Observable.error(e)
        }
    }
}