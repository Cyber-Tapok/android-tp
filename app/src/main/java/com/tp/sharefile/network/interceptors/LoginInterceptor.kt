package com.tp.sharefile.network.interceptors

import com.tp.sharefile.data.UserStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class LoginInterceptor @Inject constructor(
    private val storage: UserStorage
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("user-name", storage.loginName.toString()).build()
        return chain.proceed(authenticatedRequest)
    }
}