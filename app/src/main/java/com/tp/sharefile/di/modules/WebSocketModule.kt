package com.tp.sharefile.di.modules

import com.tinder.scarlet.Scarlet
import com.tp.sharefile.network.websocket.FileWebSocketService
import com.tp.sharefile.network.websocket.WebSocketClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class WebSocketModule {
    @Provides
    @Singleton
    @WebSocketOkHttpClient
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideScarlet(@WebSocketOkHttpClient okHttpClient: OkHttpClient): Scarlet {
        return WebSocketClient.scarletInstance(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideFilesService(scarlet: Scarlet): FileWebSocketService {
        return scarlet.create<FileWebSocketService>()
    }

}

@Qualifier
annotation class WebSocketOkHttpClient