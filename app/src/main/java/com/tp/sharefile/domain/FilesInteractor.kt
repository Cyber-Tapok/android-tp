package com.tp.sharefile.domain

import com.tinder.scarlet.WebSocket
import com.tp.sharefile.data.model.Files
import com.tp.sharefile.data.repository.FilesRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilesInteractor @Inject constructor(
    private val filesRepository: FilesRepository
) {

    fun observeFiles(): Observable<Files> = filesRepository.observeFiles()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun observeWebSocketEvent(): Flowable<WebSocket.Event> = filesRepository.observeWebSocketEvent()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun sendSubscribe() = filesRepository.sendSubscribe()
}