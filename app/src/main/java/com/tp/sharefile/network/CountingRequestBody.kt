package com.tp.sharefile.network

import okhttp3.RequestBody
import okio.*
import java.io.IOException

class CountingRequestBody(
    private val requestBody: RequestBody,
    private val onProgressUpdate: CountingRequestListener
) : RequestBody() {
    override fun contentType() = requestBody.contentType()

    @Throws(IOException::class)
    override fun contentLength() = requestBody.contentLength()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink, this, onProgressUpdate)
        val bufferedSink = countingSink.buffer()

        requestBody.writeTo(bufferedSink)

        bufferedSink.flush()
    }
}

typealias CountingRequestListener = (bytesWritten: Long, contentLength: Long) -> Unit

class CountingSink(
    sink: Sink,
    private val requestBody: RequestBody,
    private val onProgressUpdate: CountingRequestListener
) : ForwardingSink(sink) {
    private var bytesWritten = 0L

    override fun write(source: Buffer, byteCount: Long) {
        super.write(source, byteCount)

        bytesWritten += byteCount
        onProgressUpdate(bytesWritten, requestBody.contentLength())
    }
}

sealed class CountingResult<ResultT> {
    data class Progress<ResultT>(
        val progressFraction: Double
    ) : CountingResult<ResultT>() {
        val percents: Int get() = (progressFraction * 100).toInt()
    }

    data class Completed<ResultT>(
        val result: ResultT
    ) : CountingResult<ResultT>()
}