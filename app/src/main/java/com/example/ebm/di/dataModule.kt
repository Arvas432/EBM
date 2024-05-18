package com.example.ebm.di

import ITunesApi
import LocalTrackStorageHandler
import NetworkClient
import RetrofitNetworkClient
import SharedPreferencesLocalTrackStorageHandler
import android.content.Context
import android.media.MediaPlayer
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module{
    val iTunesBaseUrl = "https://itunes.apple.com"
    val EBM_PREFERENCES = "EBM_PREFERENCES"
    single<ITunesApi>{
        Retrofit.Builder()
            .baseUrl(iTunesBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApi::class.java)
    }
    single{
        androidContext().getSharedPreferences(EBM_PREFERENCES, Context.MODE_PRIVATE)
    }
    single{
        Gson()
    }
    single<LocalTrackStorageHandler>{
        SharedPreferencesLocalTrackStorageHandler(get(), get())
    }
    single<NetworkClient>{
        RetrofitNetworkClient(get())
    }
    factory{
        MediaPlayer()
    }
}