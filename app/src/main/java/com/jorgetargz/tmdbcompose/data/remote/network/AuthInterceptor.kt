package com.jorgetargz.tmdbcompose.data.remote.network


import com.jorgetargz.tmdbcompose.data.remote.network.common.Constantes
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Intercepts and adds API Key to all requests using a query parameter
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter(Constantes.API_KEY, apiKey)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}