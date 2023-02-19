package com.jorgetargz.tmdbcompose.di

import com.jorgetargz.tmdbcompose.BuildConfig
import com.jorgetargz.tmdbcompose.data.remote.network.AuthInterceptor
import com.jorgetargz.tmdbcompose.data.remote.network.Config
import com.jorgetargz.tmdbcompose.data.remote.network.services.MoviesService
import com.jorgetargz.tmdbcompose.data.remote.network.services.TVShowsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    val baseUrl = Config.BASE_URL

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(MoviesService::class.java) }
    single { get<Retrofit>().create(TVShowsService::class.java) }
}