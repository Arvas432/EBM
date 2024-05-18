package com.example.ebm.di

import AndroidMediaPlayerRepositoryImpl
import MediaPlayerRepository
import SearchHistoryRepository
import SearchHistoryRepositoryImpl
import TracksRepository
import TracksRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchHistoryRepository>{
        SearchHistoryRepositoryImpl(get())
    }
    single<TracksRepository>{
        TracksRepositoryImpl(get())
    }
    factory<MediaPlayerRepository>{
        AndroidMediaPlayerRepositoryImpl(get())
    }
}