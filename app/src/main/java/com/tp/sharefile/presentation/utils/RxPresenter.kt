package com.tp.sharefile.presentation.utils

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import moxy.MvpView

open class RxPresenter<T : MvpView> : MvpPresenter<T>() {
    protected val compositeDisposable = CompositeDisposable()

    fun disposeAll() {
        compositeDisposable.dispose()
    }

    override fun onDestroy() {
        disposeAll()
    }
}