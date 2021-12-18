package com.tp.sharefile.di.presentation

import com.tp.sharefile.di.components.AppComponent
import com.tp.sharefile.di.scopes.Presenter
import com.tp.sharefile.di.utils.PresentationComponent
import com.tp.sharefile.presentation.FilesPresenter
import dagger.Component

@Presenter
@Component(dependencies = [AppComponent::class])
interface FilesComponent : PresentationComponent<FilesPresenter> {

    override fun providePresenter(): FilesPresenter

    companion object {
        @JvmStatic
        fun build(component: AppComponent): FilesComponent =
            DaggerFilesComponent.builder().appComponent(component).build()
    }
}