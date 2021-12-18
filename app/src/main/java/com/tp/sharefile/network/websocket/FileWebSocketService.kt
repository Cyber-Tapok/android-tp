package com.tp.sharefile.network.websocket

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.data.model.Files
import io.reactivex.Flowable
import io.reactivex.Observable

interface FileWebSocketService {

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendSubscribe(stub: String = "Stub")

    @Receive
    fun observeFiles(): Observable<Files>
}