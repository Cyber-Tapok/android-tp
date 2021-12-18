package com.tp.sharefile.di.utils

interface PresentationComponent<T> {

    abstract fun providePresenter(): T
}