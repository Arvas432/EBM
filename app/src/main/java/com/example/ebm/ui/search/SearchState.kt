package com.example.ebm.ui.search

import Track


sealed class SearchState{
    object Default: SearchState()
    object Loading: SearchState()
    object NetworkError: SearchState()
    object EmptyResults: SearchState()
    data class SearchHistory(val tracks: List<Track>) : SearchState()
    data class Content(val tracks: List<Track>): SearchState()
}
