package com.example.ebm.ui.search.activity

import Track
import TrackListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ebm.R
import com.example.ebm.databinding.ActivitySearchBinding
import com.example.ebm.ui.search.SearchState
import com.example.ebm.ui.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private val viewModel by viewModel<SearchViewModel>()
    private var searchFieldEmpty: Boolean = true
    private var tracks = ArrayList<Track>()
    private lateinit var trackListAdapter: TrackListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getScreenStateLiveData().observe(this){
            renderState(it)
        }
        trackListAdapter = TrackListAdapter(tracks, viewModel)
        binding.favoritesRv.adapter = trackListAdapter
        binding.favoritesRv.layoutManager = LinearLayoutManager(this)
        binding.favoritesRv.isVisible = true

        val searchFieldTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()){
                    if(binding.searchField.hasFocus()){
                    }
                    else{
                        setDefaultScreenState()
                    }
                    binding.clearButton.isVisible = false
                }
                else{
                    setDefaultScreenState()
                    searchFieldEmpty = false
                    binding.clearButton.isVisible = true
                    viewModel.setSearchData(s.toString())
                    viewModel.searchDebounce()
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        }
        binding.searchField.addTextChangedListener(searchFieldTextWatcher)
        binding.searchField.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus && binding.searchField.text?.isEmpty() != false){
                viewModel.showHistory()
            }
        }
        binding.backBtn.setOnClickListener {
            viewModel.showHistory()
        }
        binding.clearButton.setOnClickListener {
            tracks.clear()
            trackListAdapter.notifyDataSetChanged()
            currentFocus?.let {
                val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
            binding.searchField.setText("")
        }

        binding.searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.immediateSearch()
            }
            false
        }
    }
    private fun setNetworkErrorScreenState(){
        setDefaultScreenState()
    }
    private fun setLoadingScreenState(){
        setDefaultScreenState()
        binding.favoritesRv.isVisible = false
        binding.backBtn.isVisible = false
        binding.welcomeTv.isVisible = false
        binding.welcomeTextTv.isVisible = false
        binding.tabLayout.isVisible = false
        binding.songswitchRecyclerview.isVisible = false
        binding.favoritesTv.text = getString(R.string.search_results)
        binding.searchPb.isVisible = true
    }
    private fun setEmptyResultsScreenState(){
        setDefaultScreenState()
    }
    private fun setDefaultScreenState(){
        binding.favoritesRv.isVisible = true
        binding.searchPb.isVisible = false
        binding.backBtn.isVisible = false
        binding.welcomeTv.isVisible = true
        binding.welcomeTextTv.isVisible = true
        binding.tabLayout.isVisible = true
        binding.songswitchRecyclerview.isVisible = true
        binding.favoritesTv.text = getString(R.string.search_history)
    }
    private fun setSearchHistoryScreenState(searchHistory: List<Track>){
        binding.favoritesRv.isVisible = true
        tracks.clear()
        tracks.addAll(searchHistory)
        trackListAdapter.notifyDataSetChanged()
    }
    private fun setContentScreenState(results: List<Track>){
        setDefaultScreenState()
        binding.favoritesRv.isVisible = true
        binding.searchPb.isVisible = false
        binding.backBtn.isVisible = true
        binding.welcomeTv.isVisible = false
        binding.welcomeTextTv.isVisible = false
        binding.tabLayout.isVisible = false
        binding.songswitchRecyclerview.isVisible = false
        binding.favoritesTv.text = getString(R.string.search_results)
        tracks.clear()
        tracks.addAll(results)
        trackListAdapter.notifyDataSetChanged()
    }
    private fun renderState(state: SearchState){
        when(state){
            is SearchState.Default ->{setDefaultScreenState()}
            is SearchState.Loading ->{setLoadingScreenState()}
            is SearchState.NetworkError ->{setNetworkErrorScreenState()}
            is SearchState.EmptyResults ->{setEmptyResultsScreenState()}
            is SearchState.SearchHistory ->{setSearchHistoryScreenState(state.tracks)}
            is SearchState.Content ->{setContentScreenState(state.tracks)}
        }

    }
}