package com.android.rickandmortyproject.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.rickandmortyproject.api.ApiService
import com.android.rickandmortyproject.database.AppDatabase
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.database.entity.FavoriteCharacterDto
import com.android.rickandmortyproject.enum.ListType
import com.android.rickandmortyproject.mediator.CharacterMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRepository @Inject constructor(
    private val network: ApiService,
    private val database: AppDatabase
) {

    suspend fun getCharactersList(name: String, status: String): Flow<PagingData<CharacterDto>> {
        val dao = database.getCharacterDao()
        dao.clearAll()
        val pager = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = 1,
            remoteMediator = CharacterMediator(
                network = network,
                database = database,
                dao = dao,
                name = name,
                status = status
            ),
            pagingSourceFactory = {
                dao.loadAllItems()
            }
        )

        return pager.flow
    }

    suspend fun getEpisodeDetail(episode: Int) =
        withContext(Dispatchers.IO) {
            network.getDetailEpisode(episode)
        }

    suspend fun updateCharacterFavorite(characterId: Int) = withContext(Dispatchers.IO) {
        val dao = database.getCharacterDao()
        val character = dao.getSingleCharacter(characterId)
        when (character.isFavorite) {
            true -> {
                dao.updateCharacterDtoFavorite(characterId, false)
                dao.deleteFavItem(characterId)
            }
            false -> {
                dao.updateCharacterDtoFavorite(characterId, true)
                dao.insert(FavoriteCharacterDto(characterId, true))
            }
        }
    }

    suspend fun updateViewType(viewType: ListType) = withContext(Dispatchers.Main) {
        val dao = database.getCharacterDao()
        dao.loadAllList().map {
            dao.updateViewType(it.id, viewType.id)
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}