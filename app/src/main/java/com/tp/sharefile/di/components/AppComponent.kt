package com.tp.sharefile.di.components

import android.content.Context
import com.github.terrakok.cicerone.Router
import com.tp.sharefile.MainActivity
import com.tp.sharefile.di.modules.NavigationModule
import com.tp.sharefile.di.modules.NetworkModule
import com.tp.sharefile.di.modules.WebSocketModule
import com.tp.sharefile.network.api.FilesService
import com.tp.sharefile.network.websocket.FileWebSocketService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, NavigationModule::class, WebSocketModule::class])
interface AppComponent {
    fun router(): Router
    fun context(): Context
    fun webSocketService(): FileWebSocketService
    fun service(): FilesService

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}
