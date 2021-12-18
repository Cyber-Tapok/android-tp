package com.tp.sharefile.network

import okhttp3.ResponseBody
import okio.Buffer
import okio.ForwardingSink
import okio.Sink

class CountingResponseSink(
    sink: Sink,
    private val responseBody: ResponseBody,
    private val onProgressUpdate: CountingRequestListener
) : ForwardingSink(sink) {
    private var bytesWritten = 0L

    override fun write(source: Buffer, byteCount: Long) {
        super.write(source, byteCount)

        bytesWritten += byteCount
        onProgressUpdate(bytesWritten, responseBody.contentLength())
    }
}