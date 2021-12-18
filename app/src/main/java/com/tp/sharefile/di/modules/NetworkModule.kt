package com.tp.sharefile.di.modules

import com.google.gson.Gson
import com.tp.sharefile.network.api.FilesService
import com.tp.sharefile.network.api.RetrofitClient
import com.tp.sharefile.network.interceptors.LoginInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideClient(loginInterceptor: LoginInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loginInterceptor)
            .build()
    }

    @Provides
    fun provideConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
        adapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return RetrofitClient.getRetrofit(gsonConverterFactory, okHttpClient, adapterFactory)
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): FilesService {
        return retrofit.create(FilesService::class.java)
    }
}