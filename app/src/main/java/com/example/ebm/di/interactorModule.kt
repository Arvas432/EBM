package com.example.ebm.di

import PlayerInteractor
import PlayerInteractorImpl
import SearchHistoryInteractor
import SearchHistoryInteractorImpl
import TracksInteractor
import TracksInteractorImpl
import org.koin.dsl.module

val interactorModule = module{
    factory<SearchHistoryInteractor>{
        SearchHistoryInteractorImpl(get())
    }
    factory<TracksInteractor>{
        TracksInteractorImpl(get())
    }
    factory<PlayerInteractor>{
        PlayerInteractorImpl(get())
    }
}