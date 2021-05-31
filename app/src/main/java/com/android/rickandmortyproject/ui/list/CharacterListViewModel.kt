package com.android.rickandmortyproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.enum.ListType
import com.android.rickandmortyproject.enum.StatusType
import com.android.rickandmortyproject.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    private val _characterLiveData: MutableLiveData<PagingData<CharacterDto>> = MutableLiveData()
    val characterLiveData: LiveData<PagingData<CharacterDto>> = _characterLiveData

    private var _statusType: StatusType = StatusType.NOT_SELECT
    val statusType get() = _statusType


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCharacterList(name: String, status: String) = viewModelScope.launch {
        repository.getCharactersList(name = name, status = status).cachedIn(this)
            .collectLatest {
                _characterLiveData.value = it
            }
    }

    fun setStatus(status: StatusType) = viewModelScope.launch {
        _statusType = status
    }

    fun updateCharacterFavorite(characterId: Int) {
        viewModelScope.launch {
            repository.updateCharacterFavorite(characterId)
        }
    }

    fun updateViewType(viewType: ListType) {
        viewModelScope.launch {
            repository.updateViewType(viewType)
        }
    }
}