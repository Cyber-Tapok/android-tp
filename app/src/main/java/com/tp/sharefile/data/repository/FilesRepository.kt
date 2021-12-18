package com.tp.sharefile.data.repository

import com.tp.sharefile.network.websocket.FileWebSocketService
import javax.inject.Inject

class FilesRepository @Inject constructor(private val webSocketService: FileWebSocketService) {

    fun observeFiles() = webSocketService.observeFiles()

    fun observeWebSocketEvent() = webSocketService.observeWebSocketEvent()

    fun sendSubscribe() = webSocketService.sendSubscribe()
}