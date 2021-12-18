package com.tp.sharefile.di.presentation

import com.tp.sharefile.di.components.AppComponent
import com.tp.sharefile.di.scopes.Presenter
import com.tp.sharefile.di.utils.PresentationComponent
import com.tp.sharefile.presentation.LoginPresenter
import dagger.Component

@Presenter
@Component(dependencies = [AppComponent::class])
interface LoginComponent: PresentationComponent<LoginPresenter> {

    override fun providePresenter(): LoginPresenter

    companion object {
        @JvmStatic
        fun build(component: AppComponent): LoginComponent =
            DaggerLoginComponent.builder().appComponent(component).build()
    }
}