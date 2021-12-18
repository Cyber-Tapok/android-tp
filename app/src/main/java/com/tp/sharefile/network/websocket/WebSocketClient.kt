package com.tp.sharefile.network.websocket

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient

object WebSocketClient {

    fun scarletInstance(okHttpClient: OkHttpClient) = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory("ws://10.0.2.2:8080/getAll"))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .build()
}