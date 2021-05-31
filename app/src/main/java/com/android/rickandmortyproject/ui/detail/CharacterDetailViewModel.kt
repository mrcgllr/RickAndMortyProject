package com.android.rickandmortyproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.model.episode.EpisodeItem
import com.android.rickandmortyproject.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    private val _characterDetailLD: MutableLiveData<CharacterDto> = MutableLiveData()
    val characterDetailLD: LiveData<CharacterDto> get() = _characterDetailLD

    private val _episodeDetail: MutableLiveData<EpisodeItem> = MutableLiveData()
    val episodeDetail: LiveData<EpisodeItem> get() = _episodeDetail

    fun setCharacterDetail(item: CharacterDto) = viewModelScope.launch {
        _characterDetailLD.value = item
        getEpisodeDetail(Integer.parseInt(item.characterLastEpisode))
    }

    private fun getEpisodeDetail(episode: Int) = viewModelScope.launch {
        val response = repository.getEpisodeDetail(episode)
        _episodeDetail.value = response
    }
}