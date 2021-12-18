package com.tp.sharefile.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideCiceroneRouter(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideRouter(routerCicerone: Cicerone<Router>): Router {
        return routerCicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigationHolder(routerCicerone: Cicerone<Router>): NavigatorHolder {
        return routerCicerone.getNavigatorHolder()
    }

}
