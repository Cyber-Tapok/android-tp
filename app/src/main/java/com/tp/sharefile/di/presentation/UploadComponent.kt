package com.tp.sharefile.di.presentation

import com.tp.sharefile.di.components.AppComponent
import com.tp.sharefile.di.scopes.Presenter
import com.tp.sharefile.di.utils.PresentationComponent
import com.tp.sharefile.presentation.UploadPresenter
import dagger.Component

@Presenter
@Component(dependencies = [AppComponent::class])
interface UploadComponent : PresentationComponent<UploadPresenter> {

    override fun providePresenter(): UploadPresenter

    companion object {
        @JvmStatic
        fun build(component: AppComponent): UploadComponent =
            DaggerUploadComponent.builder().appComponent(component).build()
    }
}