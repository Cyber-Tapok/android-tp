package com.tp.sharefile.di.presentation

import com.tp.sharefile.di.components.AppComponent
import com.tp.sharefile.di.scopes.Presenter
import com.tp.sharefile.di.utils.PresentationComponent
import com.tp.sharefile.presentation.DownloadPresenter
import dagger.Component

@Presenter
@Component(dependencies = [AppComponent::class])
interface DownloadComponent : PresentationComponent<DownloadPresenter> {

    override fun providePresenter(): DownloadPresenter

    companion object {
        @JvmStatic
        fun build(component: AppComponent): DownloadComponent =
            DaggerDownloadComponent.builder().appComponent(component).build()
    }
}