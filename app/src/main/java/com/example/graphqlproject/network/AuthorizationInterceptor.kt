package com.example.graphqlproject.network

import com.example.graphqlproject.utilities.AUTH_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", AUTH_HEADER )
            .build()

        return chain.proceed(request)
    }
}